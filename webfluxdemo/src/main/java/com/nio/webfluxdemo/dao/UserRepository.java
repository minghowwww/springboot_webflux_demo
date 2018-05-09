package com.nio.webfluxdemo.dao;

import com.nio.webfluxdemo.pojo.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User, String> {

    Mono<User> findByUsername(String username);
    Mono<Long> deleteByUsername(String username);
}
