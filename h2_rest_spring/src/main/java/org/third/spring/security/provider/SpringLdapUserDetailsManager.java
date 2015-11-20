package org.third.spring.security.provider;

import org.springframework.ldap.core.ContextSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.ldap.userdetails.LdapUserDetailsManager;

public class SpringLdapUserDetailsManager extends LdapUserDetailsManager {

    public SpringLdapUserDetailsManager(ContextSource contextSource) {
        super(contextSource);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return super.loadUserByUsername(username);
    }
}
