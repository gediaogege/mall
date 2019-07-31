package com.example.mall.comment;

public enum UserSex {
    /**
     * 男
     */
    MAN(1),
    /**
     * 女
     */
    WOMAN(2);
    private int sex;

    UserSex(int sex) {
        this.sex = sex;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }
}
