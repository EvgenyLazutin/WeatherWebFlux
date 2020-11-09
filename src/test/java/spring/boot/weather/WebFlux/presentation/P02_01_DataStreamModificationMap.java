package spring.boot.weather.WebFlux.presentation;

import org.junit.Test;
import reactor.core.publisher.Flux;

import java.util.stream.IntStream;

import static java.time.Duration.ofMillis;


public class P02_01_DataStreamModificationMap {

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
}
