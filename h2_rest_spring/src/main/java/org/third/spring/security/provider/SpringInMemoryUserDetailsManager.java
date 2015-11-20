package org.third.spring.security.provider;

import java.util.Properties;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

public class SpringInMemoryUserDetailsManager extends InMemoryUserDetailsManager {
    public SpringInMemoryUserDetailsManager(Properties users) {
        super(users);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return super.loadUserByUsername(username);
    }
}
