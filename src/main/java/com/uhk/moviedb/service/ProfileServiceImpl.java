package com.uhk.moviedb.service;

import com.uhk.moviedb.model.Movie;
import com.uhk.moviedb.model.Profile;

import com.uhk.moviedb.repository.ProfileRepository;
import com.uhk.moviedb.repository.UserRepository;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;


@Service
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;

    public ProfileServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public void save(Profile profile) {
        profileRepository.save(profile);
    }

    @Override
    public void delete(Profile profile) {
        profileRepository.delete(profile);
    }


    @Override
    public void addMovieToFavorite(Profile profile, Movie movie) {
        profile.getFavoriteMovies().add(movie);
        profileRepository.save(profile);
    }

    @Override
    public boolean isMovieFavorite(Profile profile, Movie movie) {
        if(profile.getFavoriteMovies() == null) {
            return false;
        }
        return profile.getFavoriteMovies().contains(movie);
    }

    @Override
    public void removeMovieFromFavorite(Profile profile, Movie movie) {
        profile.getFavoriteMovies().remove(movie);
        profileRepository.save(profile);
    }
}
