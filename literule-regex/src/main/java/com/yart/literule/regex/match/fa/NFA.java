package com.yart.literule.regex.match.fa;


import com.yart.literule.regex.common.Constant;

public class NFA {
    public NFAState start;
    public NFAState end;

    public NFA(NFAState start, NFAState end) {
        this.start = start;
        this.end = end;
    }

    // |
    public void addParallelGraph(NFA nfa) {
        NFAState newStart = new NFAState();
        NFAState newEnd = new NFAState();
        newStart.addNext(Constant.EPSILON, this.start);
        newStart.addNext(Constant.EPSILON, nfa.start);
        this.end.addNext(Constant.EPSILON, newEnd);
        nfa.end.addNext(Constant.EPSILON, newEnd);
        this.start = newStart;
        this.end = newEnd;
    }

    //
    public void addSeriesGraph(NFA NFAGraph) {
        this.end.addNext(Constant.EPSILON, NFAGraph.start);
        this.end = NFAGraph.end;
    }

    // * 重复0-n次
    public void repeatStar() {
        repeatPlus();
        addSToE(); // 重复0
    }

    // ? 重复0次哦
    public void addSToE() {
        start.addNext(Constant.EPSILON, end);
    }

    // + 重复1-n次
    public void repeatPlus() {
        NFAState newStart = new NFAState();
        NFAState newEnd = new NFAState();
        newStart.addNext(Constant.EPSILON, this.start);
        end.addNext(Constant.EPSILON, newEnd);
        end.addNext(Constant.EPSILON, start);
        this.start = newStart;
        this.end = newEnd;
    }

}
