package org.third.spring.security.auth;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.web.context.WebApplicationContext;

public class CasAuthenticationFilter
        extends org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter {
    public static final String FILTER_PARAM = "/security/j_spring_idm_security_check*";
    public static final String STATEFUL_IDENTIFIER = "IDM_STATEFUL_IDENTIFIER";

    public CasAuthenticationFilter() {
        super(FILTER_PARAM);
    }

    public CasAuthenticationFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
        setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler());

    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        // username=rod,
        // password=koala,
        // submit=Login,
        // _csrf=a3bd0011-7879-49c0-af8b-9c911f661072,

        String principal = request.getParameter("username");
        String crenditial = request.getParameter("password");
        final UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(principal,
                crenditial);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    @Override
    // org.springframework.web.filter.GenericFilterBean
    protected void initFilterBean() throws ServletException {
        // TODO Auto-generated method stub
        super.initFilterBean();
    }

    @Override // org.springframework.web.filter.GenericFilterBean
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        // WebApplicationContext springContext = WebApplicationContextUtils
        // .getWebApplicationContext(req.getServletContext());
        // Object obj = springContext.getBean("StudentAddListener");
        WebApplicationContext webApplicationContext1 = (WebApplicationContext) (((javax.servlet.http.HttpServletRequest) req)
                .getSession().getServletContext())
                        .getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);

        super.doFilter(req, res, chain);
    }

    @Override // org.springframework.web.filter.GenericFilterBean

    public void destroy() {
        // TODO Auto-generated method stub
        super.destroy();
    }
}
