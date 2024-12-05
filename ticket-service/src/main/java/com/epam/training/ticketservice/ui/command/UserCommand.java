package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.user.User;
import com.epam.training.ticketservice.core.user.UserDTO;
import com.epam.training.ticketservice.core.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.util.Optional;

@ShellComponent
@AllArgsConstructor
public class UserCommand {

    private final UserService userService;

    @ShellMethod(key = "register", value = "Register as user")
    public String register(String username, String password) {
        try {
            userService.register(username, password);
            return "Registration successful";
        } catch (Exception e) {
            return "Failed to register user";
        }
    }

    @ShellMethod(key = {"sign in privileged", "login"}, value = "Login as user")
    public String login(String username, String password) {
        return userService.login(username, password)
            .map(userDTO -> "Successful login. Welcome " + userDTO.username() + "!")
            .orElse("Login failed due to incorrect credentials");
    }

    @ShellMethod(key = "logout", value = "Logout current user")
    public String logout() {
        return userService.logout()
            .map(userDTO -> "You have successfully logged out")
            .orElse("You need to login first");
    }

    @ShellMethod(key = {"userinfo", "whoami", "describe account"}, value = "Get current logged in user info")
    public String userInfo() {
        return userService.describe()
            .map(userDTO -> userDTO.role() == User.Role.ADMIN ? "Signed in with privileged account '" + userDTO.username() + "'" : "Signed in with account '" + userDTO.username() + "'")
            .orElse("You are not logged in");
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "list users", value = "List users")
    public String listUsers() {
        try {
            return userService.getUsers().toString();
        } catch (Exception e) {
            return "Failed to list users";
        }
    }

    @ShellMethod(key = "exit", value = "Exits application")
    public void exit() {
        System.exit(0);
    }

    private Availability isAvailable() {
        Optional<UserDTO> user = userService.describe();
        return user.map(userDTO -> userDTO.role() == User.Role.ADMIN ? Availability.available() : Availability.unavailable("Permission denied")).orElseGet(() -> Availability.unavailable("You are not logged in"));
    }
}
