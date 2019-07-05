package com.example.mall.comment;

public enum WxMenuLevel {
    /**
     * 一级菜单
     */
    ONE(1),
    /**
     * 二级菜单
     */
    TOW(2);
    private int level;

    WxMenuLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}
