package spring.boot.weather.WebFlux.presentation;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AsynchronousCall {

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
                    System.out.println("Inside the first map the thread is " + Thread.currentThread().getName() + ", i= " + i);
                    return i * 10;
                })
                .publishOn(Schedulers.newSingle("Second Thread"))
                .map(i -> {
                    System.out.println("Inside the second map the thread is " + Thread.currentThread().getName() + ", i= " + i);
                    return "value " + i;
                });

        flux.subscribe(System.out::println);
    }

    @Test
    public void twoPublishOnTest() {

        final Flux<String> flux = Flux
                .range(1, 2)
                .map(i -> {
                    System.out.println("Inside the first map the thread is " + Thread.currentThread().getName() + ", i= " + i);
                    return i * 10;
                })
                .publishOn(Schedulers.newSingle("Second Thread"))
                .map(i -> {
                    System.out.println("Inside the second map the thread is " + Thread.currentThread().getName() + ", i= " + i);
                    return 5 + i;
                })
                .publishOn(Schedulers.newSingle("Third Thread"))
                .map(i -> {
                    System.out.println("Inside the third map the thread is " + Thread.currentThread().getName() + ", i= " + i);
                    return "value " + i;
                });

        flux.subscribe(System.out::println);
    }

    @Test
    public void subscribeOnTest() {

        final Flux<String> flux = Flux
                .range(1, 2)
                .map(i -> {
                    System.out.println("Inside the first map the thread is " + Thread.currentThread().getName() + ", i= " + i);
                    return i * 10;
                })
                .subscribeOn(Schedulers.newSingle("Second Thread"))
                .map(i -> {
                    System.out.println("Inside the second map the thread is " + Thread.currentThread().getName() + ", i= " + i);
                    return "value " + i;
                });

        flux.subscribe(System.out::println);
    }

    /**
     * Only the earliest subscribeOn call in the chain is actually taken into account.
     */
    @Test
    public void withTwoSubscribeOnTest() {

        final Flux<String> flux = Flux
                .range(1, 2)
                .map(i -> {
                    System.out.println("Inside the main map the thread is " + Thread.currentThread().getName() + ", i= " + i);
                    return i * 10;
                })
                .subscribeOn(Schedulers.newSingle("First Thread"))
                .map(i -> {
                    System.out.println("Inside the first map the thread is " + Thread.currentThread().getName() + ", i= " + i);
                    return i * 10;
                })
                .subscribeOn(Schedulers.newSingle("Second Thread"))
                .map(i -> {
                    System.out.println("Inside the second map the thread is " + Thread.currentThread().getName() + ", i= " + i);
                    return "value " + i;
                });

        flux.subscribe(System.out::println);
    }

    @Test
    public void withTwoSubscribeOnAndPublishOnTest() {

        final Flux<String> flux = Flux
                .range(1, 2)
                .subscribeOn(Schedulers.newSingle("First Thread"))
                .map(i -> {
                    System.out.println("Inside the first map the thread is " + Thread.currentThread().getName() + ", i= " + i);
                    return i * 10;
                })
                .publishOn(Schedulers.newSingle("publishOn Thread"))
                .map(i -> {
                    System.out.println("Inside the second map the thread is " + Thread.currentThread().getName() + ", i= " + i);
                    return i * 10;
                })
                .subscribeOn(Schedulers.newSingle("Second Thread"))
                .map(i -> {
                    System.out.println("Inside the third map the thread is " + Thread.currentThread().getName() + ", i= " + i);
                    return "value " + i;
                });

        flux.subscribe(System.out::println);
    }
}
