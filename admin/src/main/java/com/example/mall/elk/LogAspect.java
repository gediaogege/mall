package com.example.mall.elk;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.marker.Markers;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 日志操作切面
 */
@Component
@Aspect
@Slf4j
public class LogAspect {
    @Autowired
    private HttpServletRequest request;

    @Pointcut("execution(public * com.example.mall.web.*.*.*(..))")
    public void doAround() {

    }

    @Around("doAround()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        LogBean logBean = new LogBean();
        logBean.setStartTime(System.currentTimeMillis());
        Object result = joinPoint.proceed();
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        logBean.setSpendTime((int) (System.currentTimeMillis() - logBean.getStartTime()));
        logBean.setMethodType(request.getMethod());
        logBean.setIp(request.getRemoteUser());
        logBean.setMethodName(method.getName());
        logBean.setUri(request.getRequestURI());
        logBean.setUrl(request.getRequestURL().toString());
        String urlStr = request.getRequestURL().toString();
        logBean.setBasePath(StrUtil.removeSuffix(urlStr, URLUtil.url(urlStr).getPath()));
        logBean.setParameter(getParameter(method, joinPoint.getArgs()));
        logBean.setResult(result);
        Map<String, Object> logMap = new HashMap<>();
        logMap.put("url", logBean.getUrl());
        logMap.put("methodType", logBean.getMethodType());
        logMap.put("methodName", logBean.getMethodName());
        logMap.put("parameter", logBean.getParameter());
        logMap.put("spendTime", logBean.getSpendTime());
        log.info(Markers.appendEntries(logMap), JSONUtil.parse(logBean).toString());
        return result;
    }

    /**
     * 根据方法和传入的参数获取请求参数
     */
    private Object getParameter(Method method, Object[] args) {
        List<Object> argList = new ArrayList<>();
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            RequestBody requestBody = parameters[i].getAnnotation(RequestBody.class);
            if (requestBody != null) {
                argList.add(args[i]);
            }
            RequestParam requestParam = parameters[i].getAnnotation(RequestParam.class);
            if (requestParam != null) {
                Map<String, Object> map = new HashMap<>();
                String key = parameters[i].getName();
                if (!StringUtils.isEmpty(requestParam.value())) {
                    key = requestParam.value();
                }
                map.put(key, args[i]);
                argList.add(map);
            }
        }
        if (argList.size() == 0) {
            return null;
        } else if (argList.size() == 1) {
            return argList.get(0);
        } else {
            return argList;
        }
    }
}
