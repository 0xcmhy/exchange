package com.ibm.exchange.intercept;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ibm.exchange.controller.support.Anonymous;
import com.ibm.exchange.controller.support.BaseController;
import com.ibm.exchange.utils.CookieUtil;

public class LoginIntercept extends HandlerInterceptorAdapter {
    private final String ACCESS_TOKEN="access_token";

    @Autowired
    IUserCoreService userCoreService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod=(HandlerMethod)handler;

        Object action=handlerMethod.getBean();

        if(!(action instanceof BaseController)){
            throw new Exception("异常");
        }

        if(isAnonymous(handlerMethod)){
            return true;
        }
        String accessToken= CookieUtil.getCookieValue(request,ACCESS_TOKEN);
        if(StringUtils.isEmpty(accessToken)){ //没有登录的情况下的判断
            if(CookieUtil.isAjax(request)){
                JSONObject object=new JSONObject();
                object.put("code","-1");
                object.put("msg","没有登录");
                response.getWriter().write(object.toJSONString());
                return false;
            }
            response.sendRedirect("/login.shtml");
            return false;
        }
        CheckAuthRequest checkAuthRequest=new CheckAuthRequest();
        checkAuthRequest.setToken(accessToken);

        CheckAuthResponse ru=userCoreService.checkAuth(checkAuthRequest);
        if("000000".equals(ru.getCode())){
            ((BaseController)action).setUid(ru.getUid());
            return true;
        }
        return false;
    }

    private boolean isAnonymous(HandlerMethod handlerMethod){
        Object action=handlerMethod.getBean();
        Class clazz=action.getClass();
        if(clazz.getAnnotation(Anonymous.class)!=null){
            return true;
        }
        Method method=handlerMethod.getMethod();
        return method.getAnnotation(Anonymous.class)!=null;
    }

}
