package com.uhk.moviedb.service;

import com.uhk.moviedb.model.Role;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleService {
    Role getRoleByEnum(Role.RoleEnum roleEnum);
    List<Role> getAllRoles();
}
