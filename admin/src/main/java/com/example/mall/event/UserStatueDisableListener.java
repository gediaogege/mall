package com.example.mall.event;


import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 账户停用监听器
 */
@Component
public class UserStatueDisableListener implements ApplicationListener<UserStatueDisAbleEvent> {
    @Override
    public void onApplicationEvent(UserStatueDisAbleEvent userStatueDisAbleEvent) {
        HttpServletResponse response = (HttpServletResponse) userStatueDisAbleEvent.getSource();
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.print("{\"code_disable\":999}dataBefore");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
