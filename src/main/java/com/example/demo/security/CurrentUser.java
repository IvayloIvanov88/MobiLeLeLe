//package com.example.demo.security;
//
//import com.example.demo.model.entity.enums.UserRoleEnum;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.annotation.SessionScope;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//@SessionScope
//public class CurrentUser {
//todo delete me

//    private static final String ANONYMOUS_NAME = "anonymous";
//    private boolean isLoggedIn;
//    private String userName = ANONYMOUS_NAME;
//    private String firstName;
//    private String lastName;
//    private boolean isAnonymous = true;
//    private List<UserRoleEnum> userRoles = new ArrayList<>();
//
//    public CurrentUser setAnonymous(boolean anonymous) {
//        if (anonymous) {
//            this.userName = ANONYMOUS_NAME;
//            this.userRoles.clear();
//        }
//        isAnonymous = anonymous;
//        return this;
//    }
//
//    public CurrentUser setUserRoles(List<UserRoleEnum> newUserRoles) {
//        userRoles.clear();
//        userRoles.addAll(newUserRoles);
//        return this;
//    }
//
//    public CurrentUser addRole(UserRoleEnum role) {
//        this.userRoles.add(role);
//        return this;
//    }
//
//    public CurrentUser clearRoles() {
//        this.userRoles.clear();
//        return this;
//    }
//
//
//    public String getUserName() {
//        return userName;
//    }
//
//    public CurrentUser setUserName(String userName) {
//        this.userName = userName;
//        return this;
//    }
//
//
//    public boolean isAnonymous() {
//        return isAnonymous;
//    }
//
//    public boolean isLoggedIn() {
//        return !isAnonymous();
//    }
//
//
//    public String getFirstName() {
//        return firstName;
//    }
//
//    public CurrentUser setFirstName(String firstName) {
//        this.firstName = firstName;
//        return this;
//    }
//
//    public String getLastName() {
//        return lastName;
//    }
//
//    public CurrentUser setLastName(String lastName) {
//        this.lastName = lastName;
//        return this;
//    }
//
//    public List<UserRoleEnum> getUserRoles() {
//        return userRoles;
//    }
//
//    public CurrentUser setLoggedIn(boolean loggedIn) {
//        isLoggedIn = loggedIn;
//        return this;
//    }
//
//
//
//    public boolean isAdmin() {
//        return userRoles.contains(UserRoleEnum.ADMIN);
//    }
//}
