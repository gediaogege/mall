package com.example.mall.comment;

/**
 * 一些共用的状态
 */
public enum ComemtSatues {
    /**
     * 正常
     */
    NORMAL(1),
    /**
     * 停用
     */
    DISABLE(0);
    private int status;

    ComemtSatues(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}

