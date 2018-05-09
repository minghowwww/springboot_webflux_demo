package com.nio.webfluxdemo;

import ch.qos.logback.core.util.TimeUtil;
import com.nio.webfluxdemo.pojo.MyEvent;
import com.nio.webfluxdemo.pojo.User;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.TimeUnit;


public class WebClientTest {


    @Test
    public void test1(){
        WebClient webClient = WebClient.create("http://localhost:8080");
        Mono<String> resp = webClient.get().uri("/hello").retrieve().bodyToMono(String.class);
        resp.subscribe(System.out::print);
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2(){
        WebClient webClient = WebClient.builder().baseUrl("http://localhost:8080").build();
        webClient.get().uri("/user").accept(MediaType.APPLICATION_STREAM_JSON).exchange()
                .flatMapMany(response -> response.bodyToMono(User.class))
                .doOnNext(System.out::print).blockLast();
    }

    @Test
    public void test3(){
        WebClient webClient = WebClient.builder().baseUrl("http://localhost:8080").build();
        webClient.get().uri("/time").accept(MediaType.TEXT_EVENT_STREAM).retrieve()
                .bodyToFlux(String.class).log().take(10).blockLast();
    }

    @Test
    public void webClientTest4() {
        Flux<MyEvent> eventFlux = Flux.interval(Duration.ofSeconds(1))
                .map(l -> new MyEvent(System.currentTimeMillis(), "message-" + l)).take(5); // 1
        WebClient webClient = WebClient.create("http://localhost:8080");
        webClient
                .post().uri("/events")
                .contentType(MediaType.APPLICATION_STREAM_JSON) // 2
                .body(eventFlux, MyEvent.class) // 3
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

}
