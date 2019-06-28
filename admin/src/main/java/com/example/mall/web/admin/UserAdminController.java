package com.example.mall.web.admin;


import com.example.mall.comment.CommentResult;
import com.example.mall.entity.admin.UserAdmin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author qmt
 * @since 2019-06-28
 */
@Controller
@RequestMapping("mall/admin")
@Slf4j
public class UserAdminController {

    /**
     * 用户登录
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/login")
    public CommentResult login(@RequestBody UserAdmin userAdmin) {//post请求
        log.info("用户开始登录。。。");
        System.out.println(userAdmin.getUsername());
        System.out.println(userAdmin.getPassword());
        Map<String, Object> map = new HashMap<>();
        map.put("token", "55656");
        map.put("tokenHead", "dsdasdawe");
        return CommentResult.success(map);
    }

}
