package com.pztws.demo.utils.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.pztws.demo.model.User;
import com.pztws.demo.service.impl.UserServiceImpl;
import com.pztws.demo.utils.annotation.LevelAuthentication.LevelAuthentication0;
import com.pztws.demo.utils.annotation.LevelAuthentication.LevelAuthentication1;
import com.pztws.demo.utils.annotation.LevelAuthentication.LevelAuthentication2;
import com.pztws.demo.utils.annotation.LevelAuthentication.LevelAuthentication3;
import com.pztws.demo.utils.annotation.PassToken;
import com.pztws.demo.utils.annotation.UserLoginToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 拦截器，拦截fang访问，验证token（jwt）
 */
public class AuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    UserServiceImpl userService;
    static User user;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token
        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();
        //检查是否有passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }

        if (method.isAnnotationPresent(UserLoginToken.class)) {
            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
            if (userLoginToken.required()) {
                // 执行认证
                if (token == null) {
                    throw new RuntimeException("无token，请重新登录");
                }
                // 获取 token 中的 user id
                String userId;
                try {//这里判断是否是正确的token编码
                    userId = JWT.decode(token).getAudience().get(0); //JWT.decode(token):解码
                    //getAudience():Get the value of the "aud" claim, or null if it's not available.
                } catch (JWTDecodeException j) {
                    throw new RuntimeException("token值为null了！！！");
                }
                user = userService.findUserById(userId);
                if (user == null) {//这里判断此token编码有对应的userId是否有对应的user
                    throw new RuntimeException("token编码对了，但我们没有这个人");
                }
                // 验证 token
                //这里应该用id
                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getUserId())).build();
                try {
                    jwtVerifier.verify(token); //这个不一致的话，会引发JWTVerificationException
                } catch (JWTVerificationException e) {
                    throw new RuntimeException("token不一致，请不要乱改哦");
                }
            }

            //创建四个权限等级的注解判断
            //检查有没有需要用户权限的注解
            if(method.isAnnotationPresent(LevelAuthentication3.class)){
                if(user.getLevel().intValue()<4) return true;
                throw new RuntimeException("您无权限访问");
            }

            if(method.isAnnotationPresent(LevelAuthentication2.class)){
                if(user.getLevel().intValue()<3) return true;
                throw new RuntimeException("您无权限访问");
            }

            if(method.isAnnotationPresent(LevelAuthentication1.class)){
                if(user.getLevel().intValue()<2) return true;
                throw new RuntimeException("您无权限访问");
            }

            if(method.isAnnotationPresent(LevelAuthentication0.class)){
                if(user.getLevel().intValue()<1) return true;
                throw new RuntimeException("您无权限访问");
            }

        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
    }

    /**
     *自定义属性值，供其获取
     */
    public static User getUser(){
        return user;
    }
}