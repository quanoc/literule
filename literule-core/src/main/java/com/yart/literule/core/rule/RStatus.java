package com.yart.literule.core.rule;

public enum RStatus {
    // 0-草稿;2-实验;1-生效;3-试运行;4-下线.
    Draft(0),
    Stage(2),
    Up(1),
    Test(3),
    Down(4);
    private final int id;

    public int getId() {
        return id;
    }

    public static RStatus findById(int id){
        switch (id) {
            case 0 : return Draft;
            case 2 : return Stage;
            case 1 : return Up;
            case 3 : return Test;
            case 4: return Down;
            default:{
                return Draft;
            }
        }
    }

    RStatus(int id){
        this.id = id;
    }
}
