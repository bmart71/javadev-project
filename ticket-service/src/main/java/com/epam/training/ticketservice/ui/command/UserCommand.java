package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.user.UserService;
import exceptions.NoAuthorizationException;
import exceptions.UnauthorizedMethodException;
import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

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

    @ShellMethod(key = "login", value = "Login as user")
    public String login(String username, String password) {
        return userService.login(username, password)
            .map(userDTO -> "Successful login. Welcome " + userDTO.username() + "!")
            .orElse("Authentication failed");
    }

    @ShellMethod(key = "logout", value = "Logout current user")
    public String logout() {
        return userService.logout()
            .map(userDTO -> "You have successfully logged out")
            .orElse("You need to login first");
    }

    @ShellMethod(key = {"userinfo", "whoami"}, value = "Get current logged in user info")
    public String userInfo() {
        return userService.describe()
            .map(userDTO -> "Username: " + userDTO.username() + "\nRole: " + userDTO.role())
            .orElse("You are not logged in");
    }

    @ShellMethod(key = "lu", value = "List users")
    public String listUsers() {
        try {
            return userService.getUsers().toString();
        } catch (NoAuthorizationException e) {
            return "You are not logged in";
        } catch (UnauthorizedMethodException e) {
            return "Permission denied";
        } catch (Exception e) {
            return "Failed to list users";
        }
    }
}
