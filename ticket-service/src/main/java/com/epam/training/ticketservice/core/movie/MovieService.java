package com.epam.training.ticketservice.core.movie;

import java.util.List;
import java.util.Optional;

public interface MovieService {
    void saveMovie(Movie movie);
    List<Movie> getAllMovies();
    void updateMovie(Movie movie);
    void deleteMovie(String name);
    Optional<Movie> getMovieByTitle(String title);
}
