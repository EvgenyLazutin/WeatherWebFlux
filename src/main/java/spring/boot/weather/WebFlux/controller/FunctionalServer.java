package spring.boot.weather.WebFlux.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@EnableWebFlux
public class FunctionalServer {

    @Bean
    public RouterFunction<ServerResponse> routerFunctionA() {
        return route()
                .GET("/userinfo", accept(APPLICATION_JSON), this::getUserRoute)
                .POST("/addUser", accept(APPLICATION_JSON), this::addUserRoute)
                .build();
    }

    private Mono<ServerResponse> getUserRoute(ServerRequest request) {
        String userName = request.queryParam("user")
                .orElse("User not found");
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).syncBody(userName + " is Good");
    }

    private Mono<ServerResponse> addUserRoute(ServerRequest request) {
        Mono<String> user = request.body(BodyExtractors.toMono(String.class));
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(user.map(userAdd->userAdd + " was added"), String.class);
    }

}
