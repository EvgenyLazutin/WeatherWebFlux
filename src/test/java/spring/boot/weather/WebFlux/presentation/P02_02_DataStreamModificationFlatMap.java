package spring.boot.weather.WebFlux.presentation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;

import java.util.stream.IntStream;

import static java.time.Duration.ofMillis;

public class P02_02_DataStreamModificationFlatMap {
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
    public void flatMapPublisher() throws InterruptedException {
        Flux.range(5, 3)
                .flatMap(item -> Flux.just(item * 10, "two", "three").delayElements(ofMillis(1)))
                .subscribe(i -> System.out.printf("i=%s%n", i));

        Thread.sleep(2000);
    }

}
