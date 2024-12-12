package com.uhk.moviedb.service;

import com.uhk.moviedb.model.Movie;
import com.uhk.moviedb.model.Profile;

import com.uhk.moviedb.repository.ProfileRepository;
import com.uhk.moviedb.repository.UserRepository;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;


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
        Set<Movie> favoriteMovies = profile.getFavoriteMovies();
        for(Movie favoriteMovie : favoriteMovies) {
            if(favoriteMovie.getId().equals(movie.getId())) {
                return true;
            }
        }
        return false;
    }

    @Override

    public void removeMovieFromFavorite(Profile profile, Movie movie) {
        profile.getFavoriteMovies().remove(movie);
        profileRepository.save(profile);
        // todo delete from profile_favorite_movies where profile_id = ? and movie_id = ?
    }
}
