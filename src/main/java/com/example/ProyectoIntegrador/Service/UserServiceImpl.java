package com.example.ProyectoIntegrador.Service;

import com.example.ProyectoIntegrador.Dto.UserDto;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;
import java.util.*;
@Service
public class UserServiceImpl implements UserService {
    private Map<Long, UserDto> userMap = new HashMap<Long, UserDto>();
    private Long id = 1L;

    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.setId(id);
        userMap.put(userDto.getId(), userDto);
        id++;
        return userDto;
    }

    @Override
    public UserDto getUserById(long id) {
        return userMap.get(id);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return new ArrayList<UserDto>(userMap.values());
    }

    @Override
    public UserDto updateUser(UserDto userDto, Long id){
        if(userMap.containsKey(id)){
            userDto.setId(id);
            userMap.put(id, userDto);
            return userDto;
        }else{
            return null;
        }
    }

    @Override
    public void deleteUser(Long id) {
        userMap.remove(id);
    }
}
