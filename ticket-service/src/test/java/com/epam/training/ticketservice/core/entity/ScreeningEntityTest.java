package com.epam.training.ticketservice.core.entity;

import com.epam.training.ticketservice.core.movie.Movie;
import com.epam.training.ticketservice.core.room.Room;
import com.epam.training.ticketservice.core.screening.Screening;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScreeningEntityTest {

    private static final Room SAMPLE_ROOM = new Room("Test", 10, 10);
    private static final Movie SAMPLE_MOVIE = new Movie("Test", "action", 100);
    private static final LocalDateTime SAMPLE_DATE_TIME = LocalDateTime.parse("2024-01-01T10:00:00");
    private static final Screening SAMPLE_SCREENING = new Screening(SAMPLE_MOVIE, SAMPLE_ROOM, SAMPLE_DATE_TIME);

    @Test
    void testScreeningMovieGetter() {
        //Given
        Movie expected = SAMPLE_MOVIE;
        //When
        Movie actual = SAMPLE_SCREENING.getMovie();
        //Then
        assertEquals(expected, actual);
    }

    @Test
    void testScreeningRoomGetter() {
        //Given
        Room expected = SAMPLE_ROOM;
        //When
        Room actual = SAMPLE_SCREENING.getRoom();
        //Then
        assertEquals(expected, actual);
    }

    @Test
    void testScreeningDateGetter() {
        //Given
        LocalDateTime expected = LocalDateTime.parse("2024-01-01T10:00:00");
        //When
        LocalDateTime actual = SAMPLE_SCREENING.getDate();
        //Then
        assertEquals(expected, actual);
    }

    @Test
    void testScreeningToString() {
        //Given
        String expected = "Test (action, 100 minutes), screened in room Test, at 2024-01-01 10:00";
        //When
        String actual = SAMPLE_SCREENING.toString();
        //Then
        assertEquals(expected, actual);
    }
}
