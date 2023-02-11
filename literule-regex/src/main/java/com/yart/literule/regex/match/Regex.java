package com.yart.literule.regex.match;


import com.yart.literule.regex.common.Constant;
import com.yart.literule.regex.common.Reader;
import com.yart.literule.regex.common.State;
import com.yart.literule.regex.common.StateType;
import com.yart.literule.regex.match.fa.NFA;
import com.yart.literule.regex.match.fa.NFAState;
import com.yart.literule.regex.match.strategy.MatchStrategy;
import com.yart.literule.regex.match.strategy.MatchStrategyManager;

import java.util.*;

public class Regex {
    private NFA nfa;

    public static Regex compile(String regex) {
        if (regex == null || regex.length() == 0) {
            return null;
        }
        NFA nfaGraph = regex2nfa(regex);
        nfaGraph.end.setStateType(StateType.END); // 将NFA的end节点标记为终止态
        return new Regex(nfaGraph);
    }

    private Regex(NFA nfaGraph) {
        this.nfa = nfaGraph;
    }

    /**
     * 有向图的广度优先遍历
     */
    public void printNfa() {
        Queue<State> queue = new ArrayDeque<>();
        Set<Integer> addedStates = new HashSet<>();
        queue.add(nfa.start);
        addedStates.add(nfa.start.getId());
        while (!queue.isEmpty()) {
            State curState = queue.poll();
            for (Map.Entry<String, Set<State>> entry : curState.next.entrySet()) {
                String key = entry.getKey();
                Set<State> nexts = entry.getValue();
                for (State next : nexts) {
                    System.out.printf("%2d->%2d  %s\n", curState.getId(), next.getId(), key);
                    if (!addedStates.contains(next.getId())) {
                        queue.add(next);
                        addedStates.add(next.getId());
                    }
                }
            }
        }
    }

    public static NFA regex2nfa(String regex) {
        Reader reader = new Reader(regex);
        NFA nfaGraph = null;
        while (reader.hasNext()) {
            char ch = reader.next();
            String edge = null;
            switch (ch) {
                // 子表达式特殊处理
                case '(' : {
                    String subRegex = reader.getSubRegex(reader);
                    NFA newNFAGraph = regex2nfa(subRegex);
                    checkRepeat(reader, newNFAGraph);
                    if (nfaGraph == null) {
                        nfaGraph = newNFAGraph;
                    } else {
                        nfaGraph.addSeriesGraph(newNFAGraph);
                    }
                    break;
                }
                // 或表达式特殊处理
                case '|' : {
                    String remainRegex = reader.getRemainRegex(reader);
                    NFA newNFAGraph = regex2nfa(remainRegex);
                    if (nfaGraph == null) {
                        nfaGraph = newNFAGraph;
                    } else {
                        nfaGraph.addParallelGraph(newNFAGraph);
                    }
                    break;
                }
                case '[' : {
                    edge = getCharSetMatch(reader);
                    break;
                }
                // 暂时未支持零宽断言
                case '^' : {
                    break;
                }
                // 暂未支持
                case '$' : {
                    break;
                }
                case '.' : {
                    edge = ".";
                    break;
                }
                // 处理特殊占位符
                case '\\' : {
                    char nextCh = reader.next();
                    switch (nextCh) {
                        case 'd': {
                            edge = "\\d";
                            break;
                        }
                        case 'D': {
                            edge = "\\D";
                            break;
                        }
                        case 'w': {
                            edge = "\\w";
                            break;
                        }
                        case 'W': {
                            edge = "\\W";
                            break;
                        }
                        case 's': {
                            edge = "\\s";
                            break;
                        }
                        case 'S': {
                            edge = "\\S";
                            break;
                        }
                        // 转义后的字符匹配
                        default:{
                            edge = String.valueOf(nextCh);
                            break;
                        }
                    }
                    break;
                }

                default : {  // 处理普通字符
                    edge = String.valueOf(ch);
                    break;
                }
            }
            if (edge != null) {
                NFAState start = new NFAState();
                NFAState end = new NFAState();
                start.addNext(edge, end);
                NFA newNFAGraph = new NFA(start, end);
                checkRepeat(reader, newNFAGraph);
                if (nfaGraph == null) {
                    nfaGraph = newNFAGraph;
                } else {
                    nfaGraph.addSeriesGraph(newNFAGraph);
                }
            }
        }
        return nfaGraph;
    }

    private static void checkRepeat(Reader reader, NFA newNFAGraph) {
        char nextCh = reader.peak();
        switch (nextCh) {
            case '*': {
                newNFAGraph.repeatStar();
                reader.next();
                break;
            } case '+': {
                newNFAGraph.repeatPlus();
                reader.next();
                break;
            } case '?' : {
                newNFAGraph.addSToE();
                reader.next();
                break;
            } case '{' : {
                // 暂未支持{}指定重复次数
                break;
            }  default : {
                return;
            }
        }
    }

    /**
     * 获取[]中表示的字符集,只支持字母 数字
     * */
    private static String getCharSetMatch(Reader reader) {
        String charSet = "";
        char ch;
        while ((ch = reader.next()) != ']') {
            charSet += ch;
        }
        return charSet;
    }

    private static int[] getRange(Reader reader) {
        String rangeStr = "";
        char ch;
        while ((ch = reader.next()) != '}') {
            if (ch == ' ') {
                continue;
            }
            rangeStr += ch;
        }
        int[] res = new int[2];
        if (!rangeStr.contains(",")) {
            res[0] = Integer.parseInt(rangeStr);
            res[1] = res[0];
        } else {
            String[] se = rangeStr.split(",", -1);
            res[0] = Integer.parseInt(se[0]);
            if (se[1].length() == 0) {
                res[1] = Integer.MAX_VALUE;
            } else {
                res[1] = Integer.parseInt(se[1]);
            }
        }
        return res;
    }

    // 获取Epsilon可达节点列表
    private static Set<State> getNextEStates(State curState, Set<State> stateSet) {
        if (!curState.next.containsKey(Constant.EPSILON)) {
            return Collections.emptySet();
        }
        Set<State> res = new HashSet<>();
        for (State state : curState.next.get(Constant.EPSILON)) {
            if (stateSet.contains(state)) {
                continue;
            }
            res.add(state);
            res.addAll(getNextEStates(state, stateSet));
            stateSet.add(state);
        }
        return res;
    }

    public boolean isMatch(String text) {
        return isMatch(text, 0);
    }

    public boolean isMatch(String text, int mode) {
        State start = nfa.start;
        if (mode == 1) {
            //start = dfaGraph.start;
        }
        return isMatch(text, 0, start);
    }
    /**
     * 匹配过程就是根据输入遍历图的过程, 这里DFA和NFA用了同样的代码, 但实际上因为DFA的特性是不会产生回溯的,
     * 所以DFA可以换成非递归的形式
     */
    private boolean isMatch(String text, int pos, State curState) {
        if (pos == text.length()) {
            for (State nextState : curState.next.getOrDefault(Constant.EPSILON, Collections.emptySet())) {
                if (isMatch(text, pos, nextState)) {
                    return true;
                }
            }
            if (curState.isEndState()) {
                return true;
            }
            return false;
        }

        for (Map.Entry<String, Set<State>> entry : curState.next.entrySet()) {
            String edge = entry.getKey();
            // 这个if和else的先后顺序决定了是贪婪匹配还是非贪婪匹配
            if (Constant.EPSILON.equals(edge)) {
                // 如果是DFA模式,不会有EPSILON边,所以不会进这
                for (State nextState : entry.getValue()) {
                    if (isMatch(text, pos, nextState)) {
                        return true;
                    }
                }
            } else {
                MatchStrategy matchStrategy = MatchStrategyManager.getStrategy(edge);
                if (!matchStrategy.isMatch(text.charAt(pos), edge)) {
                    continue;
                }
                // 遍历匹配策略
                for (State nextState : entry.getValue()) {
                    if (isMatch(text, pos + 1, nextState)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    // 获取正则表达式在字符串中能匹配到的结尾的位置
    private int getMatchEnd(String text, int pos, State curState) {
        int end = -1;
        if (curState.isEndState()) {
            return pos;
        }

        if (pos == text.length()) {
            for (State nextState : curState.next.getOrDefault(Constant.EPSILON, Collections.emptySet())) {
                end = getMatchEnd(text, pos, nextState);
                if (end != -1) {
                    return end;
                }
            }
        }

        for (Map.Entry<String, Set<State>> entry : curState.next.entrySet()) {
            String edge = entry.getKey();
            if (Constant.EPSILON.equals(edge)) {
                for (State nextState : entry.getValue()) {
                    end = getMatchEnd(text, pos, nextState);
                    if (end != -1) {
                        return end;
                    }
                }
            } else {
                MatchStrategy matchStrategy = MatchStrategyManager.getStrategy(edge);
                if (!matchStrategy.isMatch(text.charAt(pos), edge)) {
                    continue;
                }
                // 遍历匹配策略
                for (State nextState : entry.getValue()) {
                    end = getMatchEnd(text, pos + 1, nextState);
                    if (end != -1) {
                        return end;
                    }
                }
            }
        }
        return -1;
    }
}
