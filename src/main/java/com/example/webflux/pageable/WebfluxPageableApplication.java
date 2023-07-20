package com.example.webflux.pageable;

import com.devskiller.jfairy.Fairy;
import com.devskiller.jfairy.producer.person.Person;
import com.example.webflux.pageable.entity.UserEntity;
import com.example.webflux.pageable.entity.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class WebfluxPageableApplication {

    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(WebfluxPageableApplication.class, args);
    }

    @Bean
    public ApplicationRunner seeder() {
        return args -> {
            Fairy fairy = Fairy.create();
            Flux.range(0, 200)
                    .map(i -> {
                        Person person = fairy.person();
                        return UserEntity.builder()
                                .name(person.getFullName())
                                .email(person.getEmail())
                                .age(person.getAge())
                                .build();
                    })
                    .flatMap(userRepository::save)
                    .subscribe();
        };
    }
}
