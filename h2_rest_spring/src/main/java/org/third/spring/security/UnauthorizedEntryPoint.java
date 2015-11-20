package org.third.spring.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * entry-point-ref属性，英文的意思是入口点引用。为什么需要这个入口点呢。
 * 这个入口点其实仅仅是被ExceptionTranslationFilter引用的。
 * 前面已经介绍过ExceptionTranslationFilter过滤器的作用是异常翻译，在出现认证异常、访问异常时，通过入口点决定redirect、
 * forward的操作。比如现在是form-login的认证方式，
 * 如果没有通过UsernamePasswordAuthenticationFilter的认证就直接访问某个被保护的url，
 * 那么经过ExceptionTranslationFilter过滤器处理后，先捕获到访问拒绝异常，并把跳转动作交给入口点来处理。form-
 * login的对应入口点类为LoginUrlAuthenticationEntryPoint，
 * 这个入口点类的commence方法会redirect或forward到指定的url（form-login标签的login-page属性）
 * 
 * 我使用的是restfull风格，所以entry-point-ref所参照的类如下
 * 
 * @author gongyo
 *
 */
public class UnauthorizedEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                "Unauthorized: Authentication token was either missing or invalid.");
    }
}
