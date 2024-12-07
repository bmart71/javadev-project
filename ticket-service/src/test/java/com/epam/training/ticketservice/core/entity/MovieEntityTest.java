package com.epam.training.ticketservice.core.entity;

import com.epam.training.ticketservice.core.movie.Movie;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MovieEntityTest {

    private static final Movie SAMPLE_MOVIE = new Movie("Test", "action", 100);

    @Test
    void testMovieTitleGetter() {
        //Given
        String expected = "Test";
        //When
        String actual = SAMPLE_MOVIE.getTitle();
        //Then
        assertEquals(expected, actual);
    }

    @Test
    void testMovieGenreGetter() {
        //Given
        String expected = "action";
        //When
        String actual = SAMPLE_MOVIE.getGenre();
        //Then
        assertEquals(expected, actual);
    }

    @Test
    void testMovieLengthGetter() {
        //Given
        int expected = 100;
        //When
        int actual = SAMPLE_MOVIE.getLength();
        //Then
        assertEquals(expected, actual);
    }

    @Test
    void testMovieToString() {
        //Given
        String expected = "Test (action, 100 minutes)";
        //When
        String actual = SAMPLE_MOVIE.toString();
        //Then
        assertEquals(expected, actual);
    }
}
