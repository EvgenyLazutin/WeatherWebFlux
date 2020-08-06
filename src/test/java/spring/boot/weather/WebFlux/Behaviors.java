package spring.boot.weather.WebFlux;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RunWith(SpringRunner.class)
@SpringBootTest
public class Behaviors {

    @Test
    public void doOnNextTest() {
        Flux.range(1, 10)
                .doOnNext(i -> System.out.printf("i=%s%n", i))
                .subscribe();
    }

    @Test
    public void doOnSuccessTest() {
        Mono.just(10)
                .doOnSuccess(i -> System.out.printf("i=%s%n", i))
                .subscribe();
    }

    @Test
    public void doOnCompleteTest() {
        Flux.range(1, 10)
                .doOnComplete(() -> System.out.print("Complete!"))
                .subscribe(i -> System.out.printf("i=%s%n", i));
    }

    @Test
    public void doOnErrorTest() {
        Flux.range(1, 10)
                .map(this::checkNumber)
                .doOnError(System.out::print)
                .subscribe(i -> System.out.printf("i=%s%n", i));
    }

    @Test
    public void doFinallyTestWithError() {
        Flux.range(1, 10)
                .map(this::checkNumber)
                .doFinally(i -> System.out.printf("Finally: %s", i))
                .subscribe(i -> System.out.printf("i=%s%n", i));
    }

    @Test
    public void doFinallyTestSuccess() {

        Flux.range(1, 10)
                .doFinally(i -> System.out.printf("Finally: %s", i))
                .subscribe(i -> System.out.printf("i=%s%n", i));
    }

    private int checkNumber(int value) {
        if (value == 5) {
            throw new RuntimeException("Booom!!!!");
        }
        return value;
    }

}
