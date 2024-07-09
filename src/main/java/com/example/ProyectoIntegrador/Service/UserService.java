package com.example.ProyectoIntegrador.Service;

import com.example.ProyectoIntegrador.Dto.UserDto;

import java.util.*;

public interface UserService {
    UserDto createUser(UserDto userDto);
    UserDto getUserById(String id);
    List<UserDto> getAllUsers();

    UserDto updateUser(UserDto userDto, String id);
    void deleteUser(String id);
}
