package com.uhk.moviedb.service;

import com.uhk.moviedb.model.Role;
import com.uhk.moviedb.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role getRoleByEnum(Role.RoleEnum roleEnum) {
        return roleRepository.findByRoleName(roleEnum);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}
