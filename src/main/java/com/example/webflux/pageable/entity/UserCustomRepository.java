package com.example.webflux.pageable.entity;

import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserCustomRepository {

    Flux<UserEntity> findAllWithPageable(final Pageable pageable);

    Mono<Long> countAllWithPageable(final Pageable pageable);
}
