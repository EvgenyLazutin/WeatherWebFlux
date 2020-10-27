package spring.boot.weather.WebFlux.presentation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;

import java.util.stream.IntStream;

import static java.time.Duration.ofMillis;


public class P02_DataStreamModification {

    /*
    * Map example
    * */
    @Test
    public void mapStream() {
        IntStream.range(5, 8)
                .map(i -> i * 10)
                .forEach(i -> System.out.printf("i=%s%n", i));
    }

    @Test
    public void mapPublisher() {
        Flux.range(5, 3)
                .map(i -> i * 10)
                .subscribe(i -> System.out.printf("i=%s%n", i));
    }

























    /*
     * FlatMap example
     * */
    @Test
    public void flatMapStream() {
        IntStream.range(5, 8)
                .flatMap(i -> IntStream.of(i * 10, 2, 3))
                .forEach(i -> System.out.printf("i=%s%n", i));
    }

    @Test
    public void flatMapPublisher() {
        Flux.range(5, 3)
                .flatMap(item -> Flux.just(item * 10, "two", "three").delayElements(ofMillis(1)))
                .subscribe(i -> System.out.printf("i=%s%n", i));
    }





















    /*
     * Filter example
     * */
    @Test
    public void filterStream() {
        IntStream.range(5, 16)
                .filter(item -> item > 10)
                .forEach(i -> System.out.printf("i=%s%n", i));
    }

    @Test
    public void filterPublisher() {
        Flux.range(5, 10)
                .filter(item -> item > 10)
                .subscribe(i -> System.out.printf("i=%s%n", i));
    }






















    /*
     * Reduce example
     * */
    @Test
    public void reduceStream() {
        IntStream.range(5, 8)
                .reduce(Integer::sum)
                .ifPresent(i -> System.out.printf("i=%s%n", i));

    }

    @Test
    public void reducePublisher() {
        Flux.range(5, 3)
                .reduce(Integer::sum)
                .subscribe(i -> System.out.printf("i=%s%n", i));
    }
}
