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


@RunWith(SpringRunner.class)
@SpringBootTest
public class DataStreamsCreate {

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

    @Test
    public void publisherFromPublisher() {
        Publisher<String> publisher = Flux.just("1", "2", "3");
        Mono<String> fromPublisher = Mono.from(publisher);
        fromPublisher.subscribe(System.out::println);
    }

    @Test
    public void publisherFromSteam() {
        Stream<String> str = Stream.of("a", "b", "c");
        Flux<String> publisherFromStream = Flux.fromStream(str);
        publisherFromStream.subscribe(System.out::println);
    }


}
