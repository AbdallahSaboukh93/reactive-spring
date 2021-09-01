package com.learingreactivespring.learnreactivespring.fluxandmonotest;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import static reactor.core.scheduler.Schedulers.parallel;

public class FluxAndMonoTransformTest {

	List<String> names = Arrays.asList("ahmed", "abdallah", "sayed", "morsy");

	@Test
	public void testTransformMap() {

		Flux<String> fluxMap = Flux.fromIterable(names).map(p -> p.toUpperCase()).log();
		StepVerifier.create(fluxMap).expectNext("AHMED", "ABDALLAH", "SAYED", "MORSY").verifyComplete();

	}

	@Test
	public void testTransformMap_Lenght() {

		Flux<Integer> fluxMap = Flux.fromIterable(names).map(p -> p.length()).log();
		StepVerifier.create(fluxMap).expectNext(5, 8, 5, 5).verifyComplete();

	}

	@Test
	public void testTransformMap_Lenght_repeat() {

		Flux<Integer> fluxMap = Flux.fromIterable(names).map(p -> p.length()).repeat(1).log();
		StepVerifier.create(fluxMap).expectNext(5, 8, 5, 5, 5, 8, 5, 5).verifyComplete();

	}

	@Test
	public void testTransformMap_filter() {

		Flux<String> fluxString = Flux.fromIterable(names).filter(f -> f.length() > 5).map(p -> p.toUpperCase()).log();
		StepVerifier.create(fluxString).expectNext("ABDALLAH").verifyComplete();
	}

	@Test
	public void testTransformUsingFlatMap() {

		Flux<String> fluxString = Flux.fromIterable(Arrays.asList("A", "B", "c")).flatMap(s -> {
			return Flux.fromIterable(convertToList(s));

		}).log();
		StepVerifier.create(fluxString).expectNextCount(6).verifyComplete();
	}

	private List<String> convertToList(String a) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Arrays.asList(a, "newvalue");

	}

	@Test
	public void testTransformUsingFlatMap_parallel() {

		Flux<String> fluxString = Flux.fromIterable(Arrays.asList("A", "B", "c"))
				.window(2)
//				.concatMap((s) -> s.map(this::convertToList).subscribeOn(parallel()))
				.flatMap((s) -> s.map(this::convertToList).subscribeOn(parallel()))
				.flatMap(s -> Flux.fromIterable(s))
				.log();
		StepVerifier.create(fluxString).expectNextCount(6).verifyComplete();
	}

	@Test
	public void testTransformUsingFlatMap_parallel_maintain_order() {

		Flux<String> fluxString = Flux.fromIterable(Arrays.asList("A", "B", "c"))
				.window(2)
//				.concatMap((s) -> s.map(this::convertToList).subscribeOn(parallel()))
				.flatMapSequential((s) -> s.map(this::convertToList).subscribeOn(parallel()))
				.flatMap(s -> Flux.fromIterable(s))
				.log();
		StepVerifier.create(fluxString).expectNextCount(6).verifyComplete();
	}
}
