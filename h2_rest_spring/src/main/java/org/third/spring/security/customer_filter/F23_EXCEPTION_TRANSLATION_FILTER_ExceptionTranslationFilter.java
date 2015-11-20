package org.third.spring.security.customer_filter;

import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.ExceptionTranslationFilter;

public class F23_EXCEPTION_TRANSLATION_FILTER_ExceptionTranslationFilter extends ExceptionTranslationFilter {

    public F23_EXCEPTION_TRANSLATION_FILTER_ExceptionTranslationFilter(
            AuthenticationEntryPoint authenticationEntryPoint) {
        super(authenticationEntryPoint);
    }

    @Override
    public void setAccessDeniedHandler(AccessDeniedHandler accessDeniedHandler) {
        // TODO Auto-generated method stub
        super.setAccessDeniedHandler(accessDeniedHandler);
    }

}
