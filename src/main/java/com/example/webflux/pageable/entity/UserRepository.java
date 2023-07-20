package com.example.webflux.pageable.entity;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface UserRepository extends ReactiveMongoRepository<UserEntity, String>, UserCustomRepository{
}
