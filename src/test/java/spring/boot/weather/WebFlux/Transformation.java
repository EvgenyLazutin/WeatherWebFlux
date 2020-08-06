package spring.boot.weather.WebFlux;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;

import static java.time.Duration.ofMillis;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Transformation {

    @Test
    public void mapTest() {
        Flux.range(1, 20)
                .map(i -> i * 10)
                .subscribe(i -> System.out.printf("i=%s%n", i));
    }

    @Test
    public void flatMapTest() {
        Flux.range(1, 15)
                .flatMap(item -> Flux.just(item).delayElements(ofMillis(1)))
                .subscribe(i -> System.out.printf("i=%s%n", i));
    }

    @Test
    public void filterTest() {
        Flux.range(1, 15)
                .filter(item -> item > 10)
                .subscribe(i -> System.out.printf("i=%s%n", i));
    }

    @Test
    public void reduceTest() {
        Flux.range(1, 5)
                .reduce(Integer::sum)
                .subscribe(i -> System.out.printf("i=%s%n", i));
    }
}
