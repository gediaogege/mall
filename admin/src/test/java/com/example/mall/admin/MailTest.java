package com.example.mall.admin;

import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MailTest {
    @Value("${mail.pass}")
    private String pass;

    @Value("${mail.host}")
    private String host;

    @Value("${mail.port}")
    private String port;

    @Value("${mail.from}")
    private String from;

    @Value("${mail.user}")
    private String user;

    @Test
    public void sendMail() {
        MailAccount mailAccount = new MailAccount();
        mailAccount.setFrom(from);
        mailAccount.setHost(host);
        mailAccount.setPass(pass);
        mailAccount.setUser(user);
        mailAccount.setPort(Integer.valueOf(port));
        MailUtil.send(mailAccount, "756017069@qq.com", "这是一封测试邮件", "这是一封测试邮件的content", false);
    }
}
