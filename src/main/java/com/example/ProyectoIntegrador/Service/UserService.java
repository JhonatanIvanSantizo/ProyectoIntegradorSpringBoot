package com.example.ProyectoIntegrador.Service;

import com.example.ProyectoIntegrador.Dto.UserDto;

import java.util.*;

public interface UserService {
    UserDto createUser(UserDto userDto);
    UserDto getUserById(long id);
    List<UserDto> getAllUsers();
    UserDto updateUser(UserDto userDto, Long id);
    void deleteUser(Long id);
}
