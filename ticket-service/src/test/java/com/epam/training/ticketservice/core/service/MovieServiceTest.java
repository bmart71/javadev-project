package com.epam.training.ticketservice.core.service;

import com.epam.training.ticketservice.core.movie.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class MovieServiceTest {
    private final MovieRepository movieRepository = mock(MovieRepository.class);
    private final MovieService underTest = new MovieServiceImpl(movieRepository);

    private static final Movie sample = new Movie("sample-title", "drama", 150);

    @Test
    void testSaveMovieShouldInvokeMovieRepositorySaveMethod() {
        //When
        underTest.saveMovie(sample);
        //Then
        verify(movieRepository).save(sample);
    }

    @Test
    void testGetAllMoviesShouldInvokeMovieRepositoryGetAllMethod() {
        //When
        underTest.getAllMovies();
        //Then
        verify(movieRepository).findAll();
    }

    @Test
    void testGetMoviesShouldReturnOptionalEmptyWhenMovieNotFound() {
        //Given
        when(movieRepository.findByTitle(sample.getTitle())).thenReturn(Optional.empty());
        //When
        Optional<Movie> actual = underTest.getMovieByTitle(sample.getTitle());
        //Then
        assertEquals(actual, Optional.empty());
        verify(movieRepository).findByTitle(sample.getTitle());
    }

    @Test
    void testGetMoviesShouldReturnMovieWhenMovieFound() {
        //Given
        when(movieRepository.findByTitle(sample.getTitle())).thenReturn(Optional.of(sample));
        Optional<Movie> expected = Optional.of(sample);
        //When
        Optional<Movie> actual = movieRepository.findByTitle(sample.getTitle());
        //Then
        assertEquals(expected, actual);
        verify(movieRepository).findByTitle(sample.getTitle());
    }

    @Test
    void testUpdateMovieShouldInvokeMovieRepositorySaveMethod() {
        //When
        underTest.updateMovie(sample);
        //Then
        verify(movieRepository).save(sample);
    }

    @Test
    void testDeleteMovieShouldInvokeMovieRepositoryFindByTitle() {
        //Given
        underTest.saveMovie(sample);
        //When
        underTest.deleteMovie(sample.getTitle());
        //Then
        verify(movieRepository).findByTitle(sample.getTitle());
    }

    @Test
    void testGetMoviesShouldReturnEmptyListAfterMovieGotDeleted() {
        //Given
        List<Movie> expected = new ArrayList<>();
        underTest.saveMovie(sample);
        //When
        underTest.deleteMovie(sample.getTitle());
        List<Movie> actual = underTest.getAllMovies();
        //Then
        assertEquals(expected, actual);
    }

    @Test
    void testGetMoviesShouldReturnListOfMoviesWhenMoviesExist() {
        //Given
        when(movieRepository.findAll()).thenReturn(List.of(sample));
        List<Movie> expected = List.of(sample);
        //When
        List<Movie> actual = underTest.getAllMovies();
        //Then
        assertEquals(expected, actual);
        verify(movieRepository).findAll();
    }
}
