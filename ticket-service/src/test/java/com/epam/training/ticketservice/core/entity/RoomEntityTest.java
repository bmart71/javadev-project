package com.epam.training.ticketservice.core.entity;

import com.epam.training.ticketservice.core.room.Room;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoomEntityTest {

    private static final Room SAMPLE_ROOM = new Room("Test", 10, 10);

    @Test
    void testRoomNameGetter() {
        //Given
        String expected = "Test";
        //When
        String actual = SAMPLE_ROOM.getName();
        //Then
        assertEquals(expected, actual);
    }

    @Test
    void testRoomRowsGetter() {
        //Given
        int expected = 10;
        //When
        int actual = SAMPLE_ROOM.getRows();
        //Then
        assertEquals(expected, actual);
    }

    @Test
    void testRoomColsGetter() {
        //Given
        int expected = 10;
        //When
        int actual = SAMPLE_ROOM.getCols();
        //Then
        assertEquals(expected, actual);
    }

    @Test
    void testRoomToString() {
        //Given
        String expected = "Room Test with 100 seats, 10 rows and 10 columns";
        //When
        String actual = SAMPLE_ROOM.toString();
        //Then
        assertEquals(expected, actual);
    }
}
