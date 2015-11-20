package org.third.spring.rest.rest;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.third.spring.dto.UserInfo;

public class UserInfoManager {
    static final UserInfoManager instnace = new UserInfoManager();
    Map<Integer, UserInfo> users = new ConcurrentHashMap<Integer, UserInfo>();

    private UserInfoManager() {
    }

    public static final UserInfoManager getInstance() {
        return instnace;
    }

    public UserInfo getUser(Integer id) {
        return users.get(id);
    }

    public void save(UserInfo userInfo) {
        users.put(userInfo.getId(), userInfo);
    }

    public Collection<UserInfo> getUsers() {
        return users.values();
    }
}
