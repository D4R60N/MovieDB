package com.uhk.moviedb.repository;

import com.uhk.moviedb.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
