package com.example.ProyectoIntegrador.Service;

import com.example.ProyectoIntegrador.Dto.UserDto;
import com.example.ProyectoIntegrador.entity.UserMongoEntity;
import com.example.ProyectoIntegrador.repository.UserMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;
import java.util.*;
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMongoRepository userMongoRepository;

    public List<UserDto> getAllUsers() {
        return this.userMongoRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    public UserDto getUserById(String id) {
        return this.userMongoRepository.findById(id)
                .map(this::toDto)
                .orElse(null);
    }

    public UserDto createUser(UserDto user) {
        UserMongoEntity entity = new UserMongoEntity();
        entity.setName(user.getName());
        entity.setEmail(user.getEmail());
        UserMongoEntity Entitysaved = this.userMongoRepository.save(entity);
        UserDto saved = this.toDto(Entitysaved);
        return saved;
    }

    public UserDto updateUser (UserDto user, String id){
        UserMongoEntity entity = this.userMongoRepository.findById(id)
                .orElse(null);
        if (entity == null) {
            return null;  // o lanza una excepción personalizada si prefieres
        }
        entity.setEmail(user.getEmail());
        entity.setName(user.getName());
        UserMongoEntity Entitysaved = this.userMongoRepository.save(entity);
        UserDto saved = this.toDto(Entitysaved);
        return saved;
    }

    public void deleteUser(String id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        UserMongoEntity entity = this.userMongoRepository.findById(id)
                .orElse(null);
        if (entity != null) {
            this.userMongoRepository.delete(entity);
        }
    }

    public List<UserDto> findUsersByName(String name) {
        return this.userMongoRepository.findByName(name).stream()
                .map(this::toDto)
                .toList();
    }

    private UserDto toDto(UserMongoEntity entity) {
        return new UserDto(entity.getId(), entity.getName(), entity.getEmail());
    }
}
