package com.example.demo.service.impl;


import com.example.demo.model.entity.enums.UserRoleEnum;
import com.example.demo.model.entity.service.RoleServiceModel;
import com.example.demo.repository.RoleRepository;
import com.example.demo.service.RoleService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;


    @Override
    public RoleServiceModel findByName(UserRoleEnum name) {
        return modelMapper.map(roleRepository.findByRole(name), RoleServiceModel.class);
    }

}
