package com.yart.literule.regex.match;

import com.yart.literule.regex.common.Constant;
import com.yart.literule.regex.common.State;
import com.yart.literule.regex.common.StateType;
import com.yart.literule.regex.match.fa.NFA;
import com.yart.literule.regex.match.strategy.MatchStrategy;
import com.yart.literule.regex.match.strategy.MatchStrategyManager;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

public class PinYinMatcher {
    private NFA nfaGraph;

    public static PinYinMatcher compile(String regex) {
        if (regex == null || regex.length() == 0) {
            return null;
        }
        NFA nfaGraph = Regex.regex2nfa(regex);
        nfaGraph.end.setStateType(StateType.END);
        // 将NFA的end节点标记为终止态
        return new PinYinMatcher(nfaGraph);
    }

    private PinYinMatcher(NFA nfaGraph) {
        this.nfaGraph = nfaGraph;
    }

    public boolean isMatch(String text) {
        return isMatch(text, 0);
    }

    public boolean isMatch(String text, int mode) {
        State start = nfaGraph.start;
        if (mode == 1) {
            //start = dfaGraph.start;
        }
        return isMatch(text, 0, start);
    }

    /**
     *
     * @param text 待匹配字符窜.
     * @param pos 待匹配的当前位置.
     * @param curState 状态机当前状态.
     * @return true - 命中.
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
                MatchStrategy matchStrategy = MatchStrategyManager.getPinYinStrategy(edge);
                if (!matchStrategy.isMatch(text.charAt(pos), edge)) {
                    continue;
                }
                // 遍历匹配策略
                for (State nextState : entry.getValue()) {
                    // 如果是DFA匹配模式,entry.getValue()虽然是set,但里面只会有一个元素,所以不需要回溯
//                    if (nextState instanceof DFAState) {
//                        return isMatch(text, pos + 1, nextState);
//                    }
                    if (isMatch(text, pos + 1, nextState)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
