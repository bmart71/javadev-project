package com.epam.training.ticketservice.core.room;

import java.util.List;
import java.util.Optional;

public interface RoomService {
    void createRoom(Room room);
    List<Room> getAllRooms();
    void updateRoom(Room room);
    void deleteRoom(String name);
    Optional<Room> getRoomByName(String name);
}
