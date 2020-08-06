package spring.boot.weather.WebFlux;

import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Function;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Multithreading {

    @Test
    @SneakyThrows
    public void threadTest() throws InterruptedException {

        final Mono<String> mono = Mono.just("hello ");

        Thread t = new Thread(() -> mono
                .map(msg -> msg + "thread ")
                .subscribe(v ->
                        System.out.println(v + Thread.currentThread().getName())
                ), "Second Thread"
        );
        t.start();
        t.join();
    }

    @Test
    public void publishOnTest() {

        final Flux<String> flux = Flux
                .range(1, 2)
                .map(i -> {
                    System.out.println("Inside the first map the thread is " + Thread.currentThread().getName());
                    return i * 10;
                })
                .publishOn(Schedulers.newSingle("Second Thread"))
                .map(i -> {
                    System.out.println("Inside the second map the thread is " + Thread.currentThread().getName());
                    return "value " + i;
                });

        flux.subscribe(System.out::println);
    }

    @Test
    public void twoPublishOnTest() {

        final Flux<String> flux = Flux
                .range(1, 2)
                .map(i -> {
                    System.out.println("Inside the first map the thread is " + Thread.currentThread().getName());
                    return i * 10;
                })
                .publishOn(Schedulers.newSingle("Second Thread"))
                .map(i -> {
                    System.out.println("Inside the second map the thread is " + Thread.currentThread().getName());
                    return "value " + i;
                })
                .publishOn(Schedulers.newSingle("Third Thread"))
                .map(i -> {
                    System.out.println("Inside the third map the thread is " + Thread.currentThread().getName());
                    return "value " + i;
                });

        flux.subscribe(System.out::println);
    }

    @Test
    public void subscribeOnTest() {

        final Flux<String> flux = Flux
                .range(1, 2)
                .map(i -> {
                    System.out.println("Inside the first map the thread is " + Thread.currentThread().getName());
                    return i * 10;
                })
                .subscribeOn(Schedulers.newSingle("Second Thread"))
                .map(i -> {
                    System.out.println("Inside the second map the thread is " + Thread.currentThread().getName());
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
                    System.out.println("Inside the main map the thread is " + Thread.currentThread().getName());
                    return i * 10;
                })
                .subscribeOn(Schedulers.newSingle("First Thread"))
                .map(i -> {
                    System.out.println("Inside the first map the thread is " + Thread.currentThread().getName());
                    return i * 10;
                })
                .subscribeOn(Schedulers.newSingle("Second Thread"))
                .map(i -> {
                    System.out.println("Inside the second map the thread is " + Thread.currentThread().getName());
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
                    System.out.println("Inside the first map the thread is " + Thread.currentThread().getName());
                    return i * 10;
                })
                .publishOn(Schedulers.newSingle("publishOn Thread"))
                .map(i -> {
                    System.out.println("Inside the second map the thread is " + Thread.currentThread().getName());
                    return i * 10;
                })
                .subscribeOn(Schedulers.newSingle("Second Thread"))
                .map(i -> {
                    System.out.println("Inside the third map the thread is " + Thread.currentThread().getName());
                    return "value " + i;
                });

        flux.subscribe(System.out::println);
    }

    /**
     * !!! Note that to actually perform the work in parallel, you should call ParallelFlux.runOn(Scheduler) afterward.
     */
    @Test
    public void parallelWithoutRunOnTest() {

        Flux.range(1, 10)
                .parallel(3)
                .subscribe(i -> System.out.println(String.format("Thread is %s i=%s", Thread.currentThread().getName(), i)));
    }

	@Test
	public void parallelWithRunOnTest() {
		Flux.range(1, 10)
				.parallel(3)
				.runOn(Schedulers.parallel())
				.subscribe(i -> System.out.println(String.format("Thread is %s i=%s", Thread.currentThread().getName(), i)));
	}

	/**
	 * Additionally, you can create a Scheduler out of any pre-existing ExecutorService by using
	 * Schedulers.fromExecutorService(ExecutorService).
	 * You can also create one from an Executor, although doing so is discouraged.
	 * You can also create new instances of the various scheduler types by using the newXXX methods.
	 * For example, Schedulers.newParallel(yourScheduleName) creates a new parallel scheduler named yourScheduleName.
	 */
}
