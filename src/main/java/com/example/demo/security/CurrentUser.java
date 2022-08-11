package com.example.demo.security;

import com.example.demo.model.entity.enums.UserRoleEnum;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;

@Component
@SessionScope
public class CurrentUser {

    private static final String ANONYMOUS_NAME = "anonymous";

    private String userName = ANONYMOUS_NAME;
    private boolean isAnonymous = true;
    private List<UserRoleEnum> userRoles = new ArrayList<>();


    public String getUserName() {
        return userName;
    }

    public CurrentUser setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public CurrentUser setUserRoles(List<UserRoleEnum> newUserRoles) {
        userRoles.clear();
        userRoles.addAll(newUserRoles);
        return this;
    }

    public boolean isAnonymous() {
        return isAnonymous;
    }

    public boolean isLoggedIn() {
        return !isAnonymous();
    }

    public CurrentUser setAnonymous(boolean anonymous) {
        if (anonymous) {
            this.userName = ANONYMOUS_NAME;
            this.userRoles.clear();
        }
        isAnonymous = anonymous;
        return this;
    }

    public boolean isAdmin() {
        return userRoles.contains(UserRoleEnum.ADMIN);
    }
}
