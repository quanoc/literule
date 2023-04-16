package com.yart.literule.core.model.flow;


public  class NodeLoopData {
    /**
     * 循环key
     */
    private String loopKey;
    private Integer times;

    private String valueType;
    /**
     * 是否命中后就跳出结束
     */
    private boolean jump;

    /**
     * 循环是否计算
     */
    private boolean rateCalculate;

    /**
     * 循环占比别名
     */
    private String rateKey;

    public String getLoopKey() {
        return loopKey;
    }

    public void setLoopKey(String loopKey) {
        this.loopKey = loopKey;
    }

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    public boolean isJump() {
        return jump;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }

    public boolean isRateCalculate() {
        return rateCalculate;
    }

    public void setRateCalculate(boolean rateCalculate) {
        this.rateCalculate = rateCalculate;
    }

    public String getRateKey() {
        return rateKey;
    }

    public void setRateKey(String rateKey) {
        this.rateKey = rateKey;
    }
}