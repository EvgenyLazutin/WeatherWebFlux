package spring.boot.weather.WebFlux.presentation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.reactivestreams.Publisher;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public class P01_DataStreamsCreate {


    /*
     * Create from array
     * */
    @Test
    public void arrayStream() {
        Stream<String> str = Stream.of("a", "b", "c");
        str.forEach(System.out::println);
    }

    @Test
    public void arrayPublisher() {
        Flux<String> str = Flux.just("a", "b", "c");
        str.subscribe(System.out::println);
    }
























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





























    /*
     * Generate
     * */
    @Test
    public void rangeStream() {
        IntStream range = IntStream.range(5, 8);
        range.forEach(System.out::println);
    }

    @Test
    public void rangePublisher() {
        Flux<Integer> range = Flux.range(5, 3);
        range.subscribe(System.out::println);
    }
}
