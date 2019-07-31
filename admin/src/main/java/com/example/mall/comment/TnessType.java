package com.example.mall.comment;

public enum TnessType {
    /**
     * 步行
     */
    WALK(1, "步行"),
    /**
     * 跑步
     */
    RUN(2, "跑步"),
    /**
     * 骑行
     */
    RIDING(3, "骑行");
    private int type;
    private String msg;

    TnessType(int type, String msg) {
        this.type = type;
        this.msg = msg;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
