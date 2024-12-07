package com.epam.training.ticketservice.core.service;

import com.epam.training.ticketservice.core.user.*;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserServiceTest {
    private final UserRepository userRepository = mock(UserRepository.class);
    private final UserService underTest = new UserServiceImpl(userRepository);

    @Test
    void testLoginShouldSetLoggedInUserWhenCredentialsCorrect() {
        //Given
        User user = new User("username", "password");
        Optional<User> expected = Optional.of(user);
        when(userRepository.findByUsernameAndPassword("username", "password")).thenReturn(Optional.of(user));
        //When
        Optional<UserDTO> actual = underTest.login("username", "password");
        //Then
        assertEquals(expected.get().getUsername(), actual.get().username());
        assertEquals(expected.get().getRole(), actual.get().role());
        verify(userRepository).findByUsernameAndPassword("username", "password");
    }

    @Test
    void testLoginShouldReturnOptionalEmptyWhenCredentialsIncorrect() {
        //Given
        Optional<UserDTO> expected = Optional.empty();
        when(userRepository.findByUsernameAndPassword("thisis", "notvalid")).thenReturn(Optional.empty());
        //When
        Optional<UserDTO> actual = underTest.login("thisis", "notvalid");
        //Then
        assertEquals(expected, actual);
        verify(userRepository).findByUsernameAndPassword("thisis", "notvalid");
    }

    @Test
    void testLogoutShouldReturnOptionalEmptyWhenUserIsNull() {
        //Given
        Optional<UserDTO> expected = Optional.empty();
        //When
        Optional<UserDTO> actual = underTest.logout();
        //Then
        assertEquals(expected, actual);
    }

    @Test
    void testLogoutShouldReturnPreviousUserWhenLoggedIn() {
        //Given
        User user = new User("username", "password");
        when(userRepository.findByUsernameAndPassword("username", "password")).thenReturn(Optional.of(user));
        Optional<UserDTO> expected = underTest.login("username", "password");
        //When
        Optional<UserDTO> actual = underTest.logout();
        //Then
        assertEquals(expected, actual);
    }

    @Test
    void testDescribeShouldReturnLoggedInUserWhenThereIsALoggedInUser() {
        //Given
        User user = new User("username", "password");
        when(userRepository.findByUsernameAndPassword("username", "password")).thenReturn(Optional.of(user));
        Optional<UserDTO> expected = underTest.login("username", "password");
        //When
        Optional<UserDTO> actual = underTest.describe();
        //Then
        assertEquals(expected, actual);
    }

    @Test
    void testDescribeShouldReturnOptionalEmptyWhenThereIsNoLoggedInUser() {
        //Given
        Optional<UserDTO> expected = Optional.empty();
        //When
        Optional<UserDTO> actual = underTest.describe();
        //Then
        assertEquals(expected, actual);
    }

    @Test
    void testRegisterUserShouldCallUserRepository() {
        //Given
        //When
        underTest.register(new User("username", "password"));
        //Then
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testGetUsersShouldCallUserRepositoryFindAll() {
        //When
        underTest.getUsers();
        //Then
        verify(userRepository).findAll();
    }
}
