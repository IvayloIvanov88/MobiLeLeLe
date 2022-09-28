package com.example.demo.model.entity.service;

import com.example.demo.model.entity.UserRoleEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RoleServiceModel extends BaseServiceModel {
//    private List<UserRoleEntity> userRolesName;
    private UserRoleEntity role;
}
