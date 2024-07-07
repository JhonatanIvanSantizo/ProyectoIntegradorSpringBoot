package com.example.ProyectoIntegrador.repository;

import com.example.ProyectoIntegrador.entity.UserMongoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMongoRepository extends MongoRepository<UserMongoEntity, String> {

}
