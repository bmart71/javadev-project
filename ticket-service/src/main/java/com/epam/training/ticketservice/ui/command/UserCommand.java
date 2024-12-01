package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.user.UserService;
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
    @ShellMethod(key = "listusers", value = "List users")
    public String listUsers() {
        try {
            return userService.getUsers().toString();
        } catch (Exception e) {
            return "Failed to list users";
        }
    }
}
