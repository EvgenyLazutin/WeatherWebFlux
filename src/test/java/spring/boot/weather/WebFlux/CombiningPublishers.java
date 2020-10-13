package spring.boot.weather.WebFlux;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Comparator;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CombiningPublishers {

	@Test
	public void zipTest() {
		Flux.zip(
				Flux.just(1, 5, 7, 10),
				Flux.just(2, 3, 4)
		).subscribe(System.out::println);
	}

	@Test
	public void zipArrayTest() {
		Flux.zip(
				Flux.just(1, 5, 7, 10),
				Flux.just(2, 3, 4),
				Integer::sum
		).subscribe(System.out::println);
	}

    @Test
    public void zipWithTest() {
        Flux.just("a", "b", "c")
                .zipWith(Flux.just(1, 2, 3), (word, number) -> word + number)
                .subscribe(System.out::println);
    }

    @Test
    public void concatTest() {
        Flux.concat(Flux.just("a", "b", "c"), Flux.just("1", "2", "3"))
                .subscribe(System.out::println);
    }

    @Test
    public void concatWithTest() {
        Flux.just("a", "b", "c").concatWith(Flux.just("1", "2", "3"))
                .subscribe(System.out::println);
    }

    @Test
    public void mergeTest() {
        Flux.merge(
                Flux.just("a", "b", "c").delayElements(Duration.ofNanos(1L)),
                Flux.just("1", "2", "3").delayElements(Duration.ofNanos(2L))
        ).subscribe(System.out::println);
    }

	@Test
	public void mergeSequentialTest() {
		Flux.mergeSequential(
				Flux.just("a", "b", "c").delayElements(Duration.ofNanos(1L)),
				Flux.just("1", "2", "3").delayElements(Duration.ofNanos(2L))
		).subscribe(System.out::println);
	}

	@Test
	public void mergeDelayErrorTest() {
		Flux.mergeDelayError(32,
				Flux.error(new Exception("BOOM!")),
				Flux.just("a", "b", "c").delayElements(Duration.ofNanos(1L)),
				Flux.just("1", "2", "3").delayElements(Duration.ofNanos(2L))
		).subscribe(System.out::println);
	}

	@Test
	public void mergeDelayError2Test() {
		Flux.mergeDelayError(32,
				Flux.error(new Exception("BOOM!")),
				Flux.just("a", "b", "c").delayElements(Duration.ofNanos(1L)),
				Flux.just("1", "2", "3").delayElements(Duration.ofNanos(2L)),
				Flux.error(new Exception("BOOM2!"))
		).subscribe(System.out::println);
	}

	@Test
	public void mergeOrderedTest() {
		Flux.mergeOrdered(Comparator.naturalOrder(),
				Flux.just(2, 5, 7),
				Flux.just(6, 3, 4)
		).subscribe(System.out::println);
	}

	@Test
	public void mergeOrderedWithTest() {
				Flux.just(2, 5, 7).mergeOrderedWith(Flux.just(6, 3, 4),
				Comparator.naturalOrder()
		).subscribe(System.out::println);
	}
}
