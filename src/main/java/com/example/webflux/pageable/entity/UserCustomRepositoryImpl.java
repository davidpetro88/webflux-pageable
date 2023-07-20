package com.example.webflux.pageable.entity;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class UserCustomRepositoryImpl implements UserCustomRepository {

    private final ReactiveMongoTemplate mongoTemplate;

    @Override
    public Flux<UserEntity> findAllWithPageable(Pageable pageable) {
        return mongoTemplate.find(buildQueryWithFilters(pageable), UserEntity.class);
    }

    @Override
    public Mono<Long> countAllWithPageable(Pageable pageable) {
        return mongoTemplate.count(buildQueryWithFilters(pageable), UserEntity.class);
    }

    private Query buildQueryWithFilters(Pageable pageable) {
        final Query query = new Query();
        if (pageable != null) {
            query.with(pageable);
        }
        return query;
    }

}
