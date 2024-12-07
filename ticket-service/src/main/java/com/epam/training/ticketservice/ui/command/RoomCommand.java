package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.room.Room;
import com.epam.training.ticketservice.core.room.RoomService;
import com.epam.training.ticketservice.core.user.User;
import com.epam.training.ticketservice.core.user.UserDTO;
import com.epam.training.ticketservice.core.user.UserService;

import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.util.List;
import java.util.Optional;

@ShellComponent
public class RoomCommand {

    private final RoomService roomService;
    private final UserService userService;

    public RoomCommand(RoomService roomService, UserService userService) {
        this.roomService = roomService;
        this.userService = userService;
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "create room", value = "Create a room")
    public String createRoom(String name, int rows, int cols) {
        try {
            roomService.createRoom(new Room(name, rows, cols));
            return "Room created";
        } catch (Exception e) {
            return "Failed to create room";
        }
    }

    @ShellMethod(key = "list rooms", value = "List rooms")
    public String listRooms() {
        List<Room> rooms = roomService.getAllRooms();
        if (rooms.isEmpty())
            return "There are no rooms at the moment";
        StringBuilder builder = new StringBuilder();
        for (Room room : rooms)
            builder.append(room.toString()).append("\n");
        return builder.substring(0, builder.length() - 1);
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "update room", value = "Update room")
    public String updateRoom(String name, int rows, int cols) {
        try {
            roomService.updateRoom(new Room(name, rows, cols));
            return "Room updated";
        } catch (Exception e) {
            return "Failed to update room";
        }
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "delete room", value = "Delete room")
    public String deleteRoom(String name) {
        try {
            roomService.deleteRoom(name);
            return "Room deleted";
        } catch (Exception e) {
            return "Failed to delete room";
        }
    }

    private Availability isAvailable() {
        Optional<UserDTO> user = userService.describe();
        return user.map(userDTO -> userDTO.role() == User.Role.ADMIN ? Availability.available() : Availability.unavailable("Permission denied")).orElseGet(() -> Availability.unavailable("You are not logged in"));
    }
}
