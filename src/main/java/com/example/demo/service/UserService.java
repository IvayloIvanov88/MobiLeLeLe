package com.example.demo.service;

public interface UserService {
    /**
     *
     * @param userName
     * @param password
     * @returns true if the user authenticated successfully.
     */
    boolean authenticate(String userName, String password);

    void loginUser(String userName);

    void logOutCurrentUser();
}
