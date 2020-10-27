package spring.boot.weather.WebFlux.presentation;


import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Log4j2
public class P04_AsynchronousCall {
    /*
     * Lazy example
     * */
    @Test
    public void streamExecutionExample() {

        Stream<String> str = Stream.of("a", "b", "c").peek(p-> System.out.println("Output stream: " + p));
        System.out.println("Nothing!");
    }

    @Test
    public void streamExecutionWorkExample() {

        List<String> str = Stream.of("a", "b", "c").peek(p-> System.out.println("Output stream: " + p)).collect(Collectors.toList());
        System.out.println("Done!");
    }

    @Test
    public void completableFutureExample() {
        CompletableFuture<Void> future = CompletableFuture
                .runAsync(() -> System.out.println("!!!Inside CompletableFuture!!!"));
    }

    @Test
    public void publisherExecutionExample() {

        Flux<String> str = Flux.just("a", "b", "c").doOnNext(p-> System.out.println("Output publisher: " + p));
        System.out.println("Nothing!");
    }
































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
    public void publishOnTest() {

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
    }

    @Test
    public void subscribeOnTest() {

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
    }
}
