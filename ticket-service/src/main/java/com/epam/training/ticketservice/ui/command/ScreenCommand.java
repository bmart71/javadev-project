package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.exceptions.ScreeningServiceException;
import com.epam.training.ticketservice.core.movie.Movie;
import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.room.Room;
import com.epam.training.ticketservice.core.room.RoomService;
import com.epam.training.ticketservice.core.screening.Screening;
import com.epam.training.ticketservice.core.screening.ScreeningService;
import com.epam.training.ticketservice.core.user.User;
import com.epam.training.ticketservice.core.user.UserDTO;
import com.epam.training.ticketservice.core.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ShellComponent
public class ScreenCommand {

    private final UserService userService;
    private final ScreeningService screeningService;
    private final MovieService movieService;
    private final RoomService roomService;

    public ScreenCommand(UserService userService, ScreeningService screeningService, MovieService movieService, RoomService roomService) {
        this.userService = userService;
        this.screeningService = screeningService;
        this.movieService = movieService;
        this.roomService = roomService;
    }

    @ShellMethod(key = "create screening", value = "Create screening")
    @ShellMethodAvailability("isAvailable")
    public String createScreening(String movieTitle, String roomName, String screeningTime) {
        try {
            Optional<Movie> movie = movieService.getMovieByTitle(movieTitle);
            Optional<Room> room = roomService.getRoomByName(roomName);
            String timeString = screeningTime.replace(" ", "T");
            if (movie.isEmpty())
                return "Movie not found";
            if (room.isEmpty())
                return "Room not found";
            screeningService.createScreening(
                new Screening(
                    new Movie(movie.get().getTitle(), movie.get().getGenre(), movie.get().getLength()),
                    new Room(room.get().getName(), room.get().getRows(), room.get().getCols()),
                    LocalDateTime.parse(timeString + ":00")
                )
            );
        } catch (ScreeningServiceException e) {
            return e.getMessage();
        }
        return "Screening created";
    }

    @ShellMethod(key = "list screenings", value = "List screenings")
    public String listScreenings() {
        List<Screening> screenings = screeningService.getAllScreenings();
        if (screenings.isEmpty())
            return "There are no screenings";
        StringBuilder builder = new StringBuilder();
        for (Screening screening : screenings)
            builder.append(screening).append("\n");

        return builder.substring(0, builder.length() - 1);
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "delete screening", value = "Delete screening")
    public String deleteScreening(String movieTitle, String roomName, String screeningTime) {
        try {
            Optional<Movie> Omovie = movieService.getMovieByTitle(movieTitle);
            Optional<Room> Oroom = roomService.getRoomByName(roomName);
            String timeString = screeningTime.replace(" ", "T");

            Movie movie = Omovie.orElseThrow(() -> new ScreeningServiceException("Movie not found"));
            Room room = Oroom.orElseThrow(() -> new ScreeningServiceException("Room not found"));

            Screening screening = screeningService.getScreeningByMovieTitleAndRoomNameAndScreeningTime(movie, room, LocalDateTime.parse(timeString + ":00"));
            Long screeningId = screening.getId();
            screeningService.deleteScreening(screeningId);
        } catch (ScreeningServiceException e) {
            return e.getMessage();
        } catch (Exception e) {
            return "Failed to delete movie" + e.getMessage();
        }
        return "Screening deleted";
    }

    private Availability isAvailable() {
        Optional<UserDTO> user = userService.describe();
        return user.map(
    userDTO -> userDTO.role() == User.Role.ADMIN
            ? Availability.available()
            : Availability.unavailable("Permission denied")).orElseGet(
                () -> Availability.unavailable("You are not logged in"));
    }

}
