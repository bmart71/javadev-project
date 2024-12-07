package com.epam.training.ticketservice.core.service;

import com.epam.training.ticketservice.core.room.Room;
import com.epam.training.ticketservice.core.room.RoomRepository;
import com.epam.training.ticketservice.core.room.RoomService;
import com.epam.training.ticketservice.core.room.RoomServiceImpl;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RoomServiceTest {

    private final RoomRepository roomRepository = mock(RoomRepository.class);
    private final RoomService underTest = new RoomServiceImpl(roomRepository);

    private final static Room SAMPLE_ROOM = new Room("Test", 10, 10);

    @Test
    void testCreateRoomShouldInvokeRoomRepositorySaveMethod() {
        //Given
        //When
        underTest.createRoom(SAMPLE_ROOM);
        //Then
        verify(roomRepository).save(SAMPLE_ROOM);
    }

    @Test
    void testCreateRoomShouldSaveRoom() {
        //Given
        ArrayList<Room> actual = new ArrayList<>();
        when(roomRepository.save(SAMPLE_ROOM)).thenReturn(actual.add(SAMPLE_ROOM) ? SAMPLE_ROOM : null);
        List<Room> expected = List.of(SAMPLE_ROOM);
        //When
        underTest.createRoom(SAMPLE_ROOM);
        //Then
        assertEquals(expected, actual);
    }

    @Test
    void testGetAllRoomsShouldInvokeRoomRepositoryFindAllMethod() {
        //When
        underTest.getAllRooms();
        //Then
        verify(roomRepository).findAll();
    }

    @Test
    void testGetAllRoomsShouldReturnEmptyListIfNoRoomIsFound() {
        //Given
        List<Room> expected = new ArrayList<>();
        //When
        List<Room> actual = underTest.getAllRooms();
        //Then
        assertEquals(expected, actual);
    }

    @Test
    void testGetAllRoomsShouldReturnListOfRoomsIfRoomExists() {
        //Given
        when(roomRepository.findAll()).thenReturn(List.of(SAMPLE_ROOM));
        List<Room> expected = List.of(SAMPLE_ROOM);
        //When
        List<Room> actual = underTest.getAllRooms();
        //Then
        assertEquals(expected, actual);
    }

    @Test
    void testUpdateRoomShouldInvokeRoomRepositorySaveMethod() {
        //When
        underTest.updateRoom(SAMPLE_ROOM);
        //Then
        verify(roomRepository).save(SAMPLE_ROOM);
    }

    @Test
    void testDeleteRoomShouldInvokeRoomRepositoryFindByNameMethod() {
        //Given
        underTest.createRoom(SAMPLE_ROOM);
        //When
        underTest.deleteRoom(SAMPLE_ROOM.getName());
        //Then
        verify(roomRepository).findByName(SAMPLE_ROOM.getName());
    }

    @Test
    void testDeleteRoomShouldDeleteRoom() {
        //Given
        List<Room> actual = new ArrayList<>();
        underTest.createRoom(SAMPLE_ROOM);
        //When
        underTest.deleteRoom(SAMPLE_ROOM.getName());
        List<Room> expected = underTest.getAllRooms();
        //Then
        assertEquals(expected, actual);
    }

    @Test
    void testGetRoomByNameShouldReturnOptionalEmptyWhenNoRoomIsFound() {
        //Given
        when(roomRepository.findByName(SAMPLE_ROOM.getName())).thenReturn(Optional.empty());
        //When
        Optional<Room> actual = underTest.getRoomByName(SAMPLE_ROOM.getName());
        //Then
        assertEquals(Optional.empty(), actual);
        verify(roomRepository).findByName(SAMPLE_ROOM.getName());
    }

    @Test
    void testGetRoomByNameShouldReturnOptionalOfRoomWhenRoomExists() {
        //Given
        when(roomRepository.findByName(SAMPLE_ROOM.getName())).thenReturn(Optional.of(SAMPLE_ROOM));
        Optional<Room> actual = underTest.getRoomByName(SAMPLE_ROOM.getName());
        //When
        Optional<Room> expected = Optional.of(SAMPLE_ROOM);
        //Then
        assertEquals(expected, actual);
        verify(roomRepository).findByName(SAMPLE_ROOM.getName());
    }
}
