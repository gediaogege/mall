package com.example.mall.web.admin;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.example.mall.comment.CommentResult;
import com.example.mall.entity.admin.UserAdmin;
import com.example.mall.service.admin.UserAdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

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
    @Autowired
    private UserAdminService userAdminService;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    /**
     * 用户登录
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/login")
    public CommentResult login(@RequestBody UserAdmin userAdmin) {//post请求
        log.info("用户开始登录。。。,登录用户：{}", userAdmin.getUsername());
        Map<String, Object> map = new HashMap<>(2);
        String token = userAdminService.login(userAdmin);
        map.put("token", token);
        map.put("tokenHead", tokenHead);
        return CommentResult.success(map);

    }

    /**
     * 获取登录用户的信息
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public CommentResult info() {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal.getUsername();
        return CommentResult.success(username);
    }

    /**
     * 注销
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public CommentResult logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
        return CommentResult.success();
    }

}
