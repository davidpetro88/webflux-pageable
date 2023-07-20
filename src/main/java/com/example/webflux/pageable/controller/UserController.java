package com.example.webflux.pageable.controller;


import com.example.webflux.pageable.entity.UserEntity;
import com.example.webflux.pageable.entity.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RequestMapping("/v1/customers")
@RestController
@AllArgsConstructor
public class UserController {

    private final UserRepository repository;

    @GetMapping
    public Mono<Page<UserEntity>> findAll(
            Pageable pageable
    ) {
        return repository.findAllWithPageable(pageable).collectList()
                .zipWith(repository.countAllWithPageable(pageable))
                .map(tuple -> new PageImpl<>(tuple.getT1(), pageable, tuple.getT2()))
                .map(page -> page);
    }

    @GetMapping("/{id}")
    public Mono<UserEntity> findById(
            @PathVariable String id
    ) {
        return repository.findById(id);
    }

    @PutMapping("/{id}")
    public Mono<UserEntity> update(
            @PathVariable String id,
            @RequestBody UserEntity user
    ) {
        return repository.findById(id)
                .flatMap(userEntity -> repository.save(
                        UserEntity.builder()
                                .id(userEntity.id())
                                .name(user.name())
                                .age(user.age())
                                .email(user.email())
                                .build()
                ))
                .switchIfEmpty(Mono.defer(() -> Mono.error(
                        new RuntimeException("User Not found")
                )));
    }

    @PostMapping()
    public Mono<UserEntity> save(
            @RequestBody UserEntity user
    ) {
        return repository.save(user);
    }
}
