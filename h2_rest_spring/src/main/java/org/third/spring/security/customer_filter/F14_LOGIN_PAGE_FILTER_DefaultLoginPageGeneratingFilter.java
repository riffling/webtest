package org.third.spring.security.customer_filter;

import org.springframework.security.web.authentication.ui.DefaultLoginPageGeneratingFilter;

public class F14_LOGIN_PAGE_FILTER_DefaultLoginPageGeneratingFilter extends DefaultLoginPageGeneratingFilter {
    @Override
    public void setFailureUrl(String failureUrl) {
        // TODO Auto-generated method stub
        super.setFailureUrl(failureUrl);
    }

    @Override
    public String getLoginPageUrl() {
        // TODO Auto-generated method stub
        return super.getLoginPageUrl();
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return super.isEnabled();
    }

    @Override
    public void setLogoutSuccessUrl(String logoutSuccessUrl) {
        // TODO Auto-generated method stub
        super.setLogoutSuccessUrl(logoutSuccessUrl);
    }

    @Override
    public void setOpenIDusernameParameter(String openIDusernameParameter) {
        // TODO Auto-generated method stub
        super.setOpenIDusernameParameter(openIDusernameParameter);
    }

}
