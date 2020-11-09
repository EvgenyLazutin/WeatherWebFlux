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


public class P01_03_DataStreamsCreateGenerate {
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
