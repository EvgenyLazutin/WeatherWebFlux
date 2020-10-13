package spring.boot.weather.WebFlux;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;

import java.util.concurrent.atomic.AtomicBoolean;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ErrorProcessing {

    @Test
    public void onErrorReturnTest() {
        Flux.range(1, 10)
                .map(this::checkNumber)
                .onErrorReturn(20)
                .subscribe(i -> System.out.printf("i=%s%n", i));

    }


    @Test
    public void doOnNextTest2() {
        Flux.range(1, 10)
                .map(this::checkNumber)
                .onErrorResume(i->cachedNumber())
                .subscribe(i -> System.out.printf("i=%s%n", i));
    }


    @Test
    public void usingTest() {
        AtomicBoolean isDisposed = new AtomicBoolean();
        Disposable disposableInstance = new Disposable() {
            @Override
            public void dispose() {
                isDisposed.set(true);
            }

            @Override
            public String toString() {
                return "Resource is Close";
            }
        };
        Flux<String> flux =
                Flux.using(
                        () -> disposableInstance,
                        disposable -> Flux.just(disposable.toString()),
                        Disposable::dispose
                );
        flux.subscribe(System.out::println);
    }

    private int checkNumber(int value) {
        if (value == 5) {
            throw new RuntimeException("Booom!!!!");
        }
        return value;
    }

    private Flux<Integer> cachedNumber(){
       return Flux.range(5, 6);
    }
}
