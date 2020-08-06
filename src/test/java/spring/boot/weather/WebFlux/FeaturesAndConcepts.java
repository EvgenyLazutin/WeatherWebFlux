package spring.boot.weather.WebFlux;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest
public class FeaturesAndConcepts {

    private WebClient webClient;
    private WebTestClient.Builder webClientTest;

    @Before
    public void setup() {
        this.webClient = WebClient
                .builder()
                .baseUrl("http://localhost:8080")
                .defaultCookie("cookieKey", "cookieValue")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
        this.webClientTest = WebTestClient
                .bindToServer()
                .baseUrl("http://localhost:8080")
                .defaultCookie("cookieKey", "cookieValue")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
    }

    @Test
    public void contextTest() {
        String key = "message";
        Mono<String> r = Mono.just("Hello")
                .flatMap(s -> Mono.subscriberContext()
                        .map(ctx -> s + " " + ctx.get(key)))
                .subscriberContext(ctx -> ctx.put(key, "World"));

        r.subscribe(System.out::print);
    }

    @Test
    public void contextTest2() {
        String key = "message";
        Mono<String> r = Mono.just("Hello")
                .subscriberContext(ctx -> ctx.put(key, "World"))
                .flatMap(s -> Mono.subscriberContext()
                        .map(ctx -> s + " " + ctx.getOrDefault(key, "Key not found")));

        r.subscribe(System.out::print);
    }

    @Test
    public void contextTest3() {
        String key = "message";
        Mono<String> r = Mono.subscriberContext()
                .map(ctx -> ctx.put(key, "Hello"))
                .flatMap(ctx -> Mono.subscriberContext())
                .map(ctx -> ctx.getOrDefault(key, "Default"));

        r.subscribe(System.out::print);
    }

    @Test
    public void contextTest4() {
        String key = "message";
        Mono<String> r = Mono.just("Hello")
                .flatMap(s -> Mono.subscriberContext()
                        .map(ctx -> s + " " + ctx.get(key)))
                .subscriberContext(ctx -> ctx.put(key, "Reactor"))
                .subscriberContext(ctx -> ctx.put(key, "World"));

        r.subscribe(System.out::print);
    }

    @Test
    public void webClientTest() {
        String responseUser = webClient.get()
                .uri(UriBuilder->UriBuilder.path("/userinfo")
                        .queryParam("user", "Ivanov")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer")
                .retrieve().bodyToMono(String.class).block();

        System.out.printf("UserResponse=%s%n", responseUser);
    }

    @Test
    public void webClientTest2() {
        Mono<String> responseUser = webClient.get()
                .uri(UriBuilder->UriBuilder.path("/userinfo")
                        .queryParam("user", "Ivanov")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer")
                .retrieve().bodyToMono(String.class);

        StepVerifier.create(responseUser)
                .assertNext(userRes->assertEquals("Ivanov is Good", userRes))
                .verifyComplete();
    }

    @Test
    public void webClientTest3() {
        this.webClientTest.build().get()
                .uri(UriBuilder->UriBuilder.path("/userinfo")
                .queryParam("user", "Ivanov")
                .build())
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer")
                .exchange()
                .expectBody(String.class)
                .consumeWith(user->assertEquals("Ivanov is Good", user.getResponseBody()));
    }

    @Test
    public void webClientTest4() {
        String newUser = "Petrov";
        this.webClientTest.build().post()
                .uri("/addUser")
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer")
                .syncBody(newUser)
                .exchange()
                .expectBody(String.class)
                .consumeWith(user->assertEquals("Petrov was added", user.getResponseBody()));
    }
}
