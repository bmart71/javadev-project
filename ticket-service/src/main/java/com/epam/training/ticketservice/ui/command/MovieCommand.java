package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.movie.Movie;
import com.epam.training.ticketservice.core.movie.MovieService;
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
public class MovieCommand {

    private final UserService userService;
    private final MovieService movieService;

    public MovieCommand(UserService userService, MovieService movieService) {
        this.userService = userService;
        this.movieService = movieService;
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "create movie", value = "Create movies")
    public String createMovie(String name, String genre, int length) {
        try {
            movieService.saveMovie(new Movie(name, genre, length));
            return "Movie created";
        } catch (Exception e) {
            return "Failed to create movie";
        }
    }

    @ShellMethod(key = "list movies", value = "Lists available movies")
    public String listMovies() {
        try {
            List<Movie> movies = movieService.getAllMovies();
            if (movies.isEmpty())
                return "There are no movies at the moment";
            StringBuilder builder = new StringBuilder();
            for (Movie movie : movies)
                builder.append(movie).append("\n");
            return builder.substring(0, builder.length() - 1);
        } catch (Exception e) {
            return "Failed to list movies";
        }
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "update movie", value = "Update movie")
    public String updateMovie(String title, String genre, int length) {
        try {
            movieService.updateMovie(new Movie(title, genre, length));
            return "Movie titled: " + title + " updated";
        } catch (Exception e) {
            return "Failed to update movie";
        }
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "delete movie", value = "Delete movie by name")
    public String deleteMovie(String title){
        try {
            movieService.deleteMovie(title);
            return "Movie titled: " + title + " deleted";
        } catch (Exception e) {
            return "Failed to delete movie";
        }
    }

    private Availability isAvailable() {
        Optional<UserDTO> user = userService.describe();
        return user.map(
            userDTO -> userDTO.role() == User.Role.ADMIN
                ? Availability.available()
                : Availability.unavailable("Permission denied"))
            .orElseGet(() -> Availability.unavailable("You are not logged in"));
    }
}
