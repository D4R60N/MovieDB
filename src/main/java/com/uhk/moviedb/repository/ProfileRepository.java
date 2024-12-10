package com.uhk.moviedb.repository;

import com.uhk.moviedb.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProfileRepository extends JpaRepository<Profile, Long> {

}
