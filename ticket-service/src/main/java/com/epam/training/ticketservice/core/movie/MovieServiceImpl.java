package com.epam.training.ticketservice.core.movie;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public void saveMovie(Movie movie) {
        movieRepository.save(movie);
    }

    @Override
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @Override
    public void updateMovie(Movie movie) {
        movieRepository.save(movie);
    }

    @Override
    public void deleteMovie(String name) {
        movieRepository.findByTitle(name).ifPresent(movieRepository::delete);
    }

    @Override
    public Optional<Movie> getMovieByTitle(String title) {
        return movieRepository.findByTitle(title);
    }
}
