package com.example.ProyectoIntegrador.Service;

import com.example.ProyectoIntegrador.Dto.UserDto;
import com.example.ProyectoIntegrador.entity.UserMongoEntity;
import com.example.ProyectoIntegrador.repository.UserMongoRepository;
import jakarta.xml.bind.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserMongoRepository userMongoRepository;

    @Test
    public void findAll(){
        UserMongoEntity user1 = new UserMongoEntity();
        user1.setId("1");
        user1.setName("Ivan");
        user1.setEmail("ivan@gmail.com");

        UserMongoEntity user2 = new UserMongoEntity();
        user2.setId("2");
        user2.setName("jhonatan");
        user2.setEmail("jhonatan@gmail.com");

        when(userMongoRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        List<UserDto> users = userService.getAllUsers();

        assertNotNull(users);
        assertEquals(2, users.size());
        assertEquals("Ivan", users.get(0).getName());
        assertEquals("jhonatan", users.get(1).getName());
    }

    @Test
    public void findByID() {
        UserMongoEntity user = new UserMongoEntity();
        user.setId("1");
        user.setName("Ivan");
        user.setEmail("ivan@gmail.com");

        when(userMongoRepository.findById("1")).thenReturn(Optional.of(user));

        UserDto userDto = userService.getUserById("1");

        assert userDto.getId().equals("1");
        assert userDto.getName().equals("Ivan");
        assert userDto.getEmail().equals("ivan@gmail.com");
    }

    @Test
    public void findByIDNotFound() {
        when(userMongoRepository.findById("1")).thenReturn(Optional.empty());

        UserDto userDto = userService.getUserById("1");

        assertNull(userDto);
    }

    @Test
    public void findUsersByName() {
        UserMongoEntity user1 = new UserMongoEntity();
        user1.setId("1");
        user1.setName("Ivan");
        user1.setEmail("ivan@gmail.com");

        UserMongoEntity user2 = new UserMongoEntity();
        user2.setId("2");
        user2.setName("Ivan");
        user2.setEmail("ivan2@gmail.com");

        when(userMongoRepository.findByName("Ivan")).thenReturn(Arrays.asList(user1, user2));

        List<UserDto> users = userService.findUsersByName("Ivan");

        assertNotNull(users);
        assertEquals(2, users.size());
        assertEquals("Ivan", users.get(0).getName());
        assertEquals("Ivan", users.get(1).getName());
    }

    @Test
    public void getAllUsersEmpty() {
        when(userMongoRepository.findAll()).thenReturn(Collections.emptyList());

        List<UserDto> users = userService.getAllUsers();

        assertNotNull(users);
        assertTrue(users.isEmpty());
    }

    @Test
    public void createUser() {
        UserDto userDto = new UserDto(null, "ivan", "ivan@gmail.com");
        UserMongoEntity user = new UserMongoEntity();
        user.setName("Ivan");
        user.setEmail("ivan@gmail.com");

        UserMongoEntity savedUser = new UserMongoEntity();
        savedUser.setId("1");
        savedUser.setName("Ivan");
        savedUser.setEmail("ivan@gmail.com");

        when(userMongoRepository.save(any(UserMongoEntity.class))).thenReturn(savedUser);

        UserDto savedUserDto = userService.createUser(userDto);

        assertNotNull(savedUserDto);
        assertEquals("1", savedUserDto.getId());
        assertEquals("Ivan", savedUserDto.getName());
        assertEquals("ivan@gmail.com", savedUserDto.getEmail());
    }

    @Test
    public void createWithId(){
        UserDto userDto = new UserDto("3", "ivan", "ivan@gmail.com");
        UserMongoEntity user = new UserMongoEntity();
        user.setName("Ivan");
        user.setEmail("ivan@gmail.com");

        UserMongoEntity savedUser = new UserMongoEntity();
        savedUser.setId("1");
        savedUser.setName("Ivan");
        savedUser.setEmail("ivan@gmail.com");

        when(userMongoRepository.save(any(UserMongoEntity.class))).thenReturn(savedUser);

        UserDto savedUserDto = userService.createUser(userDto);

        assertNotNull(savedUserDto);
        assertEquals("1", savedUserDto.getId());
        assertEquals("Ivan", savedUserDto.getName());
        assertEquals("ivan@gmail.com", savedUserDto.getEmail());
    }

    @Test
    public void createException(){
        UserDto userDto = new UserDto(null, "ivan", "ivan@gmail.com");
        UserMongoEntity user = new UserMongoEntity();
        when(userMongoRepository.save(any(UserMongoEntity.class))).thenThrow(new RuntimeException("Database error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.createUser(userDto);
        });
    }

    @Test
    public void createUserNull() {
        UserDto userDto = new UserDto(null, null, null);
        UserMongoEntity user = new UserMongoEntity();
        user.setName(null);
        user.setEmail(null);

        UserMongoEntity savedUser = new UserMongoEntity();
        savedUser.setId("1");
        savedUser.setName(null);
        savedUser.setEmail(null);

        when(userMongoRepository.save(any(UserMongoEntity.class))).thenReturn(savedUser);

        UserDto savedUserDto = userService.createUser(userDto);

        assertNotNull(savedUserDto);
        assertEquals("1", savedUserDto.getId());
        assertNull(savedUserDto.getName());
        assertNull(savedUserDto.getEmail());
    }

    @Test
    public void createUserEmpty() {
        UserDto userDto = new UserDto(null, "", "");
        UserMongoEntity user = new UserMongoEntity();
        user.setName("");
        user.setEmail("");

        UserMongoEntity savedUser = new UserMongoEntity();
        savedUser.setId("1");
        savedUser.setName("");
        savedUser.setEmail("");

        when(userMongoRepository.save(any(UserMongoEntity.class))).thenReturn(savedUser);

        UserDto savedUserDto = userService.createUser(userDto);

        assertNotNull(savedUserDto);
        assertEquals("1", savedUserDto.getId());
        assertEquals("", savedUserDto.getName());
        assertEquals("", savedUserDto.getEmail());
    }

    @Test
    public void update(){
        UserDto userDto = new UserDto(null, "Ivan", "ivan@gmail.com");
        UserMongoEntity user = new UserMongoEntity();

        user.setId("1");
        user.setName("a");
        user.setEmail("a@gmail.com");

        when(userMongoRepository.findById("1")).thenReturn(Optional.of(user));
        when(userMongoRepository.save(any(UserMongoEntity.class))).thenReturn(user);

        UserDto updateUser = userService.updateUser(userDto, "1");

        assertNotNull(updateUser);
        assertEquals("1", updateUser.getId());
        assertEquals("Ivan", updateUser.getName());
        assertEquals("ivan@gmail.com", updateUser.getEmail());
    }

    @Test
    public void updateUserNotFound() {
        UserDto userDto = new UserDto(null, "Ivan", "ivan@gmail.com");

        when(userMongoRepository.findById("1")).thenReturn(Optional.empty());

        UserDto updateUser = userService.updateUser(userDto, "1");

        assertNull(updateUser);
    }

    @Test
    public void updateException(){
        UserDto userDto = new UserDto(null, "ivan", "ivan@gmail.com");
        UserMongoEntity user = new UserMongoEntity();

        when(userMongoRepository.findById("1")).thenReturn(Optional.of(user));
        when(userMongoRepository.save(any(UserMongoEntity.class))).thenThrow(new RuntimeException("Database error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.updateUser(userDto, "1");
        });

        assertEquals("Database error", exception.getMessage());
    }

    @Test
    public void updateUserNull() {
        UserDto userDto = new UserDto(null, null, null);
        UserMongoEntity user = new UserMongoEntity();

        user.setId("1");
        user.setName("a");
        user.setEmail("a@gmail.com");

        when(userMongoRepository.findById("1")).thenReturn(Optional.of(user));
        when(userMongoRepository.save(any(UserMongoEntity.class))).thenReturn(user);

        UserDto updateUser = userService.updateUser(userDto, "1");

        assertNotNull(updateUser);
        assertEquals("1", updateUser.getId());
        assertNull(updateUser.getName());
        assertNull(updateUser.getEmail());
    }

    @Test
    public void updateUserEmpty() {
        UserDto userDto = new UserDto(null, "", "");
        UserMongoEntity user = new UserMongoEntity();

        user.setId("1");
        user.setName("a");
        user.setEmail("a@gmail.com");

        when(userMongoRepository.findById("1")).thenReturn(Optional.of(user));
        when(userMongoRepository.save(any(UserMongoEntity.class))).thenReturn(user);

        UserDto updateUser = userService.updateUser(userDto, "1");

        assertNotNull(updateUser);
        assertEquals("1", updateUser.getId());
        assertEquals("", updateUser.getName());
        assertEquals("", updateUser.getEmail());
    }

    @Test
    public void deleteUserNotFound() {
        when(userMongoRepository.findById("1")).thenReturn(Optional.empty());

        userService.deleteUser("1");

        verify(userMongoRepository, times(0)).delete(any(UserMongoEntity.class));
    }

    @Test
    public void deleteByID(){
        UserMongoEntity user = new UserMongoEntity();
        user.setId("1");
        user.setName("Ivan");
        user.setEmail("ivan@gmail.com");
        when(userMongoRepository.findById("1")).thenReturn(Optional.of(user));

        userService.deleteUser("1");

        verify(userMongoRepository, times(1)).delete(user);
    }

    @Test
    public void deleteUserNullId() {
        assertThrows(IllegalArgumentException.class, () -> userService.deleteUser(null));
        verify(userMongoRepository, never()).delete(any(UserMongoEntity.class));
    }

    @Test
    public void deleteUserWhenDatabaseIsEmpty() {
        when(userMongoRepository.findById(anyString())).thenReturn(Optional.empty());

        userService.deleteUser("1");

        verify(userMongoRepository, times(0)).delete(any(UserMongoEntity.class));
    }

    @Test
    public void deleteUserAndVerifyRemoval() {
        UserMongoEntity user = new UserMongoEntity();
        user.setId("1");
        user.setName("Ivan");
        user.setEmail("ivan@gmail.com");
        when(userMongoRepository.findById("1")).thenReturn(Optional.of(user));

        userService.deleteUser("1");

        verify(userMongoRepository, times(1)).delete(user);
        verify(userMongoRepository, times(1)).findById("1");
    }
}