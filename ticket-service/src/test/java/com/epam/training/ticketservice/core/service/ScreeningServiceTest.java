package com.epam.training.ticketservice.core.service;

import com.epam.training.ticketservice.core.exceptions.ScreeningServiceException;
import com.epam.training.ticketservice.core.movie.Movie;
import com.epam.training.ticketservice.core.room.Room;
import com.epam.training.ticketservice.core.screening.Screening;
import com.epam.training.ticketservice.core.screening.ScreeningRepository;
import com.epam.training.ticketservice.core.screening.ScreeningService;
import com.epam.training.ticketservice.core.screening.ScreeningServiceImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ScreeningServiceTest {

    private final ScreeningRepository screeningRepository = mock(ScreeningRepository.class);
    private final ScreeningService underTest = new ScreeningServiceImpl(screeningRepository);

    private static final Room SAMPLE_ROOM = new Room("Test", 10, 10);
    private static final Movie SAMPLE_MOVIE = new Movie("Test", "action", 100);
    private static final LocalDateTime SAMPLE_DATE_TIME = LocalDateTime.parse("2024-01-01T10:00:00");
    private static final Screening SAMPLE_SCREENING = new Screening(SAMPLE_MOVIE, SAMPLE_ROOM, SAMPLE_DATE_TIME);

    private static final LocalDateTime SAMPLE_DATE_TIME_OVERLAP = LocalDateTime.parse("2024-01-01T11:00:00");
    private static final Screening SAMPLE_SCREENING_OVERLAP = new Screening(SAMPLE_MOVIE, SAMPLE_ROOM, SAMPLE_DATE_TIME_OVERLAP);

    private static final LocalDateTime SAMPLE_DATE_TIME_BREAK_OVERLAP = LocalDateTime.parse("2024-01-01T11:45:00");
    private static final Screening SAMPLE_SCREENING_BREAK_OVERLAP = new Screening(SAMPLE_MOVIE, SAMPLE_ROOM, SAMPLE_DATE_TIME_BREAK_OVERLAP);

    @Test
    void testCreateScreeningShouldInvokeScreeningRepositorySaveMethod() {
        //When
        underTest.createScreening(SAMPLE_SCREENING);
        //Then
        verify(screeningRepository).save(SAMPLE_SCREENING);
    }

    @Test
    void testCreateScreeningShouldSaveScreening() {
        //Given
        ArrayList<Screening> actual = new ArrayList<>();
        when(screeningRepository.save(SAMPLE_SCREENING)).thenReturn(actual.add(SAMPLE_SCREENING) ? SAMPLE_SCREENING : null);
        List<Screening> expected = List.of(SAMPLE_SCREENING);
        //When
        underTest.createScreening(SAMPLE_SCREENING);
        //Then
        assertEquals(expected, actual);
    }

    @Test
    void testCreateScreeningShouldNotSaveScreeningIfThereIsOverlap() {
        //Given
        when(screeningRepository.findScreeningByRoom(SAMPLE_ROOM)).thenReturn(List.of(SAMPLE_SCREENING));
        when(screeningRepository.save(SAMPLE_SCREENING_OVERLAP)).thenReturn(SAMPLE_SCREENING_OVERLAP);
        //When
        assertThrows(ScreeningServiceException.class, () -> underTest.createScreening(SAMPLE_SCREENING_OVERLAP));
        //Then
        verify(screeningRepository, never()).save(SAMPLE_SCREENING_OVERLAP);
    }

    @Test
    void testCreateScreeningShouldNotSaveScreeningIfThereIsBreakOverlap() {
        //Given
        when(screeningRepository.findScreeningByRoom(SAMPLE_ROOM)).thenReturn(List.of(SAMPLE_SCREENING));
        when(screeningRepository.save(SAMPLE_SCREENING_BREAK_OVERLAP)).thenReturn(SAMPLE_SCREENING_BREAK_OVERLAP);
        //When
        assertThrows(ScreeningServiceException.class, () -> underTest.createScreening(SAMPLE_SCREENING_BREAK_OVERLAP));
        //Then
        verify(screeningRepository, never()).save(SAMPLE_SCREENING_BREAK_OVERLAP);
    }

    @Test
    void testGetAllScreeningShouldInvokeScreeningRepositoryFindAllMethod() {
        //When
        underTest.getAllScreenings();
        //Then
        verify(screeningRepository).findAll();
    }

    @Test
    void testGetAllScreeningShouldReturnEmptyListIfNoScreeningsFound() {
        //Given
        List<Screening> expected = new ArrayList<>();
        //When
        List<Screening> actual = underTest.getAllScreenings();
        //Then
        assertEquals(expected, actual);
    }

    @Test
    void testGetAllScreeningsShouldReturnListIfScreeningsFound() {
        //Given
        when(screeningRepository.findAll()).thenReturn(List.of(SAMPLE_SCREENING));
        List<Screening> expected = List.of(SAMPLE_SCREENING);
        //When
        List<Screening> actual = underTest.getAllScreenings();
        //Then
        assertEquals(expected, actual);
    }

    @Test
    void testDeleteScreeningShouldInvokeScreeningRepositoryDeleteByIdMethod() {
        //Given
        underTest.createScreening(SAMPLE_SCREENING);
        //When
        underTest.deleteScreening(SAMPLE_SCREENING.getId());
        //Then
        verify(screeningRepository).deleteById(SAMPLE_SCREENING.getId());
    }

    @Test
    void testDeleteScreeningShouldDeleteScreening() {
        //Given
        List<Screening> actual = new ArrayList<>();
        underTest.createScreening(SAMPLE_SCREENING);
        //When
        underTest.deleteScreening(SAMPLE_SCREENING.getId());
        List<Screening> expected = underTest.getAllScreenings();
        //Then
        assertEquals(expected, actual);
    }

    @Test
    void testGetScreeningByMovieTitleAndRoomNameAndScreeningTimeShouldInvokeScreeningRepositoryFindScreeningByRoomAndMovieAndDate() {
        //When
        underTest.getScreeningByMovieTitleAndRoomNameAndScreeningTime(SAMPLE_MOVIE, SAMPLE_ROOM, SAMPLE_DATE_TIME);
        //Then
        verify(screeningRepository).findScreeningByRoomAndMovieAndDate(SAMPLE_ROOM, SAMPLE_MOVIE, SAMPLE_DATE_TIME);
    }

    @Test
    void testGetScreeningByMovieTitleAndRoomNameAndScreeningTimeShouldReturnScreening() {
        //Given
        when(screeningRepository.findScreeningByRoomAndMovieAndDate(SAMPLE_ROOM, SAMPLE_MOVIE, SAMPLE_DATE_TIME)).thenReturn(SAMPLE_SCREENING);
        Screening expected = SAMPLE_SCREENING;
        //When
        Screening actual = underTest.getScreeningByMovieTitleAndRoomNameAndScreeningTime(SAMPLE_MOVIE, SAMPLE_ROOM, SAMPLE_DATE_TIME);
        //Then
        assertEquals(expected, actual);
    }
}
