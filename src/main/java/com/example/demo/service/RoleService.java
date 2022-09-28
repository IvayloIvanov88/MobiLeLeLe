package com.example.demo.service;


import com.example.demo.model.entity.enums.UserRoleEnum;
import com.example.demo.model.entity.service.RoleServiceModel;

public interface RoleService {
    RoleServiceModel findByName(UserRoleEnum name);
}
