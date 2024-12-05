package com.epam.training.ticketservice.core.movie;

import com.epam.training.ticketservice.core.user.UserService;
import exceptions.UnauthorizedMethodException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    @Override
    public void saveMovie(String name, String genre, int length) {
        Movie movie = new Movie(name, genre, length);
        movieRepository.save(movie);
    }

    @Override
    public void updateMovie(String name, String genre, int length) {
        movieRepository.findByTitle(name).ifPresent(movie -> {
            movie.setTitle(name);
            movie.setGenre(genre);
            movie.setLength(length);
            movieRepository.save(movie);
        });
    }

    @Override
    public void deleteMovie(String name) {
        movieRepository.findByTitle(name).ifPresent(movieRepository::delete);
    }

    @Override
    public List<Movie> getAllMovies() {
        return (List<Movie>) movieRepository.findAll();
    }
}
