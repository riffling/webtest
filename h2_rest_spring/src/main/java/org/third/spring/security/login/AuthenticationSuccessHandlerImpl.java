package org.third.spring.security.login;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;

/*
*
 * Created by Administrator on 2014/12/5.
*/

public class AuthenticationSuccessHandlerImpl
        implements org.springframework.security.web.authentication.AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        response.sendRedirect("index.jsp");
    }
}