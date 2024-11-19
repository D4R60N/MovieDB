package com.uhk.moviedb.service;

import com.uhk.moviedb.model.Role;
import org.springframework.stereotype.Service;

@Service
public interface RoleService {
    Role getRoleByEnum(Role.RoleEnum roleEnum);
}
