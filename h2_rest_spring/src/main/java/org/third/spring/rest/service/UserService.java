package org.third.spring.rest.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.third.spring.dto.ListBean;
import org.third.spring.dto.UserInfo;
import org.third.spring.utils.DateUtil;

@Service("userService")
public class UserService {

    @Autowired
    UserInfo defaultUserInfo;

    List<UserInfo> userInfos = null;

    public UserService() {
        init();
    }

    private void init() {
        userInfos = new ArrayList<UserInfo>();
        UserInfo user1 = new UserInfo(1);
        user1.setBirthday(DateUtil.getDate(1985, 1, 1));
        user1.setUserName("A");
        user1.setVip(true);
        user1.setSalary(new BigDecimal(5000.00d));
        List<String> hobbyNames = new ArrayList<String>();
        hobbyNames.add("music");
        hobbyNames.add("movie");
        user1.setHobbies(new ListBean(hobbyNames));
        userInfos.add(user1);

        UserInfo user2 = new UserInfo(2);
        user2.setBirthday(DateUtil.getDate(1988, 10, 2));
        user2.setUserName("B");
        user2.setVip(false);
        user2.setSalary(new BigDecimal(8000.60d));
        userInfos.add(user2);

    }

    public UserInfo getUserInfo(int userId) {
        for (UserInfo u : userInfos) {
            if (u.getId() == userId) {
                return u;
            }
        }
        return defaultUserInfo;

    }

    public ListBean getAllUsers() {
        return new ListBean(userInfos);
    }
}
