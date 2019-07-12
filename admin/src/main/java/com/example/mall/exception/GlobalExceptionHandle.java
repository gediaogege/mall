package com.example.mall.exception;

import com.example.mall.comment.CommentResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;



@ControllerAdvice
@Slf4j
public class GlobalExceptionHandle {
    @ExceptionHandler
    @ResponseBody
    public CommentResult exceptionHandler(Exception e, HandlerMethod handlerMethod) {
        //处理没有访问权限
        log.error(e.getMessage(), e);
        e.printStackTrace();
        if (e instanceof AccessDeniedException) {
            return CommentResult.forbidden(e.getMessage());
        }
        return CommentResult.failed();
    }
}
