package spring.boot.weather.WebFlux.presentation;

import org.junit.Test;
import reactor.core.publisher.Flux;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class P02_05_DataStreamsTerminalLazy {

    @Test
    public void streamExecutionExample() {

        Stream<String> str = Stream.of("a", "b", "c")
                .peek(p-> System.out.println("Output stream: " + p));
        System.out.println("Nothing!");
    }

    @Test
    public void streamExecutionWorkExample() {

        List<String> str = Stream.of("a", "b", "c")
                .peek(p-> System.out.println("Output stream: " + p))
                .collect(Collectors.toList());
        System.out.println("Done!");
    }

    @Test
    public void completableFutureExample() {
        CompletableFuture<Void> future = CompletableFuture
                .runAsync(() -> System.out.println("!!!Inside CompletableFuture!!!"));
    }

    @Test
    public void publisherExecutionExample() {

        Flux<String> str = Flux.just("a", "b", "c")
                .doOnNext(p-> System.out.println("Output publisher: " + p));
        System.out.println("Nothing!");
    }
}
