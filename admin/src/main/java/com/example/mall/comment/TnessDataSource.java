package com.example.mall.comment;

public enum TnessDataSource {
    /**
     * 苹果自带的app
     */
    APPLE_APP(1),
    /**
     * nickapp
     */
    NICK_APP(2);
    private int source;

    TnessDataSource(int source) {
        this.source = source;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }
}
