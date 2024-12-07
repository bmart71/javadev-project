package com.epam.training.ticketservice.core.room;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public void createRoom(Room room) {
        roomRepository.save(room);
    }

    @Override
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @Override
    public void updateRoom(Room room) {
        roomRepository.save(room);
    }

    @Override
    public void deleteRoom(String name) {
        roomRepository.findByName(name).ifPresent(roomRepository::delete);
    }

    @Override
    public Optional<Room> getRoomByName(String name) {
        return roomRepository.findByName(name);
    }
}
