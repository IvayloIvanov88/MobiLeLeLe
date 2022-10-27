package com.example.demo.model.entity.service;

import com.example.demo.model.entity.UserRoleEntity;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleServiceModel extends BaseServiceModel {
//    private List<UserRoleEntity> userRolesName;
    private UserRoleEntity role;
}
