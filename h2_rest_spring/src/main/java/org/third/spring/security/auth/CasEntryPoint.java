package org.third.spring.security.auth;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;

public class CasEntryPoint implements org.springframework.security.web.AuthenticationEntryPoint,
        org.springframework.beans.factory.InitializingBean {

    static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CasEntryPoint.class);

    @Override
    // org.springframework.security.web.AuthenticationEntryPoint
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        //relatviePath:  specified by security:http/pattern
        RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
        dispatcher.forward(request, response);

//        response.sendError(
//                HttpServletResponse.SC_UNAUTHORIZED,
//                "Unauthorized: Authentication token was either missing or invalid.");
    }

    @Override
    // org.springframework.beans.factory.InitializingBean
    public void afterPropertiesSet() throws Exception {
        logger.info("org.springframework.beans.factory.InitializingBean.afterPropertiesSet()  is called");

    }

}
