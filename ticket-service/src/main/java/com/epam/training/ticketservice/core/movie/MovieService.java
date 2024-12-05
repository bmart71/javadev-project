package com.epam.training.ticketservice.core.movie;

import java.util.List;

public interface MovieService {
    void saveMovie(String name, String genre, int length);
    void updateMovie(String name, String genre, int length);
    void deleteMovie(String name);
    List<Movie> getAllMovies();
}
