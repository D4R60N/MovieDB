package com.uhk.moviedb.repository;

import com.uhk.moviedb.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    @Procedure(procedureName = "checkPassword")
    boolean checkPassword(@Param("username") String username, @Param("password") String password);

    @Procedure(procedureName = "checkUsername")
    boolean checkUsername(@Param("username") String username);
}
