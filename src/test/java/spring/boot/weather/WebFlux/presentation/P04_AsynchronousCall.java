package spring.boot.weather.WebFlux.presentation;


import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.stream.IntStream;

@Log4j2
public class P04_AsynchronousCall {

    /*
     * asynchronous work
     * */
    @Test
    public void asynchronousStream() {
        IntStream.range(1, 8)
                .parallel()
                .flatMap(i -> IntStream.of(i * 10, 2, 3))
                .forEach(i -> System.out.printf("i=%s%n", i));
    }

    @Test
    public void publishOnTest() throws InterruptedException {

        final Flux<String> flux = Flux
                .range(1, 2)
                .map(i -> {
                    log.info(String.format("First map, thread is:::%s, i=%s%n",Thread.currentThread().getName(),i));
                    return i * 10;
                })
                .publishOn(Schedulers.newSingle("@_2_Thread"))
                .map(i -> {
                    log.info(String.format("Second map, thread is:::%s, i=%s%n",Thread.currentThread().getName(),i));
                    return "Return value " + i;
                });

        flux.subscribe(log::info);

        Thread.sleep(2000);
    }

    @Test
    public void subscribeOnTest() throws InterruptedException {

        final Flux<String> flux = Flux
                .range(1, 2)
                .map(i -> {
                    log.info(String.format("First map, thread is:::%s, i=%s%n",Thread.currentThread().getName(),i));
                    return i * 10;
                })
                .subscribeOn(Schedulers.newSingle("@_2_Thread"))
                .map(i -> {
                    log.info(String.format("Second map, thread is:::%s, i=%s%n",Thread.currentThread().getName(),i));
                    return "Return value " + i;
                });

        flux.subscribe(log::info);

        Thread.sleep(2000);
    }
}
