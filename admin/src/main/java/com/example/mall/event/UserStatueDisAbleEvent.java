package com.example.mall.event;

import org.springframework.context.ApplicationEvent;

/**
 * 账户状态停用时间
 */
public class UserStatueDisAbleEvent extends ApplicationEvent {
    public UserStatueDisAbleEvent(Object source) {
        super(source);
    }
}
