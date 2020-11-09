package spring.boot.weather.WebFlux.presentation;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.stream.IntStream;


public class P03_03_DesignForFailureSwitchEmpty {

    /*
     * switchIfEmpty example
     * */
    @Test
    public void switchIfEmptyTest() {
        Mono.empty()
                .doOnNext(System.out::println)
                .switchIfEmpty(createNewPublisher())
                .subscribe(s -> System.out.printf("finish: %s", s));
    }

    private Mono<String> createNewPublisher() {
        System.out.println("Inside method createNewPublisher");
        return Mono.just("New Object");
    }
}
