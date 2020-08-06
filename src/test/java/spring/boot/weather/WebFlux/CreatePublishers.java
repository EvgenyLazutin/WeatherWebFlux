package spring.boot.weather.WebFlux;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.reactivestreams.Publisher;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CreatePublishers {

    @Test
    public void justTest() {
        Flux<String> str = Flux.just("1", "2", "3");
        str.subscribe(System.out::println);
    }

    @Test
    public void iterableTest() {
        List<String> iterable = Arrays.asList("1", "2", "3");
        Flux<String> str = Flux.fromIterable(iterable);
        str.subscribe(System.out::println);
    }

    @Test
    public void rangeTest() {
        Flux<Integer> range = Flux.range(5, 3);
        range.subscribe(System.out::println);
    }

    @Test
    public void fromPublisherTest() {
        Publisher<String> publisher = Flux.just("1", "2", "3");
        Mono<String> fromPublisher = Mono.from(publisher);
        fromPublisher.subscribe(System.out::println);
    }

    /**
     * public static <T> Mono<T> create(Consumer<MonoSink<T>> callback)
     * Bridging legacy API involves mostly boilerplate code due to the lack of standard types and methods. There are two kinds of API surfaces: 1) addListener/removeListener and 2) callback-handler.
     * To work with such API one has to instantiate the listener, call the sink from the listener then register it with the source:
     *   Mono.<String>create(sink -> {
     *             HttpListener listener = event -> {
     *                 if (event.getResponseCode() >= 400) {
     *                     sink.error(new RuntimeException("Failed"));
     *                 } else {
     *                     String body = event.getBody();
     *                     if (body.isEmpty()) {
     *                         sink.success();
     *                     } else {
     *                         sink.success(body.toLowerCase());
     *                     }
     *                 }
     *             };
     *
     *             client.addListener(listener);
     *
     *             sink.onDispose(() -> client.removeListener(listener));
     *         });
     */

}
