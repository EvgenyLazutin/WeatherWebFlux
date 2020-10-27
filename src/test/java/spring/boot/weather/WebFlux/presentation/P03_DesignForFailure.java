package spring.boot.weather.WebFlux.presentation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.stream.IntStream;


public class P03_DesignForFailure {

    private int checkNumber(int value) {
        if (value == 5) {
            throw new RuntimeException("Booom!!!!");
        }
        return value;
    }

    /*
     * Try-catch example
     * */
    @Test
    public void tryCatchTest() {
        try {
            IntStream.range(1, 10)
                    .map(this::checkNumber)
                    .forEach(i -> System.out.printf("i=%s%n", i));
        } catch (Exception ex) {
            System.out.println("Ups!");
        }
    }

    @Test
    public void doOnErrorTest() {
        Flux.range(1, 10)
                .map(this::checkNumber)
                .doOnError(error -> System.out.println("Ups!"))
                .subscribe(i -> System.out.printf("i=%s%n", i));
    }















































    /*
     * onErrorReturn, onErrorResume example
     * */
    @Test
    public void onErrorReturnTest() {
        Flux.range(1, 10)
                .map(this::checkNumber)
                .onErrorReturn(20)
                .subscribe(i -> System.out.printf("i=%s%n", i));

    }

    @Test
    public void onErrorResumeTest() {
        Flux.range(1, 10)
                .map(this::checkNumber)
                .onErrorResume(i -> cachedNumber())
                .subscribe(i -> System.out.printf("i=%s%n", i));
    }


























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

    private Flux<Integer> cachedNumber() {
        return Flux.range(5, 6);
    }
}
