package spring.boot.weather.WebFlux.presentation;

import org.junit.Test;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public class P01_02_DataStreamsCreateFromCollection {

    /*
     * Create from Collection
     * */

    @Test
    public void iterableStream() {
        Collection<String> collection = Arrays.asList("a", "b", "c");
        Stream<String> str = collection.stream();
        str.forEach(System.out::println);
    }

    @Test
    public void iterablePublisher() {
        Collection<String> collection = Arrays.asList("a", "b", "c");
        Flux<String> str = Flux.fromIterable(collection);
        str.subscribe(System.out::println);
    }
}
