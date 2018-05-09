package com.nio.webfluxdemo.dao;

import com.nio.webfluxdemo.pojo.MyEvent;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import reactor.core.publisher.Flux;

public interface MyEventRepository extends ReactiveMongoRepository<MyEvent, Long> {
    @Tailable// 1
    Flux<MyEvent> findBy();
}