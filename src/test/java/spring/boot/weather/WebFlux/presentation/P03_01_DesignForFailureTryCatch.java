package spring.boot.weather.WebFlux.presentation;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.stream.IntStream;


public class P03_01_DesignForFailureTryCatch {

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
}
