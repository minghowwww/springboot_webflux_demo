package com.nio.webfluxdemo.controller;

import com.nio.webfluxdemo.dao.MyEventRepository;
import com.nio.webfluxdemo.pojo.MyEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/events")
public class MyEventController {

    @Autowired
    private MyEventRepository myEventRepository;

    @PostMapping(path = "")
    public Mono<Void> loadEvents(@RequestBody Flux<MyEvent> events) {   // 1
        return this.myEventRepository.insert(events).then();    // 2
    }

    @GetMapping(path = "", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<MyEvent> getEvents() {  // 2
        return this.myEventRepository.findBy();
    }
}
