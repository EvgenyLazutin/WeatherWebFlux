package spring.boot.weather.WebFlux.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;


@RestController
public class AnnotatedServer {

//
//    @GetMapping(value = "/userinfo")
//    public Mono<String> getUser(@RequestParam("user") final String name) {
//
//        return Mono.just(name + " is Good");
//    }
//
//    @PostMapping(value = "/addUser")
//    public Mono<String> addUser(@RequestBody String name) {
//
//        return Mono.just(name + " was added");
//    }
}
