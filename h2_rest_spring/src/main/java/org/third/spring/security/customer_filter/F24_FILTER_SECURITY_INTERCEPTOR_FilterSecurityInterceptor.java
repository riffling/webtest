package org.third.spring.security.customer_filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.intercept.RunAsManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

public class F24_FILTER_SECURITY_INTERCEPTOR_FilterSecurityInterceptor extends FilterSecurityInterceptor {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // TODO Auto-generated method stub
        super.doFilter(request, response, chain);
    }

    @Override
    public AccessDecisionManager getAccessDecisionManager() {
        // TODO Auto-generated method stub
        return super.getAccessDecisionManager();
    }

    @Override
    public AuthenticationManager getAuthenticationManager() {
        // TODO Auto-generated method stub
        return super.getAuthenticationManager();
    }

    @Override
    public RunAsManager getRunAsManager() {
        // TODO Auto-generated method stub
        return super.getRunAsManager();
    }

}
