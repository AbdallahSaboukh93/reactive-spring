package com.learingreactivespring.learnreactivespring.fluxandmonotest;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class FluxAndMonoFactoryTest {

	List<String> names = Arrays.asList("ahmed", "abdallah", "sayed", "morsy");

	@Test
	public void testNamesItrerable() {

		Flux<String> fluxString = Flux.fromIterable(names).log();
		StepVerifier.create(fluxString).expectNext("ahmed", "abdallah", "sayed", "morsy").verifyComplete();
	}

	@Test
	public void testNamesArray() {
		String[] namesArray = new String[] { "ahmed", "abdallah", "sayed", "morsy" };
		Flux<String> fluxString = Flux.fromArray(namesArray).log();
		StepVerifier.create(fluxString).expectNext("ahmed", "abdallah", "sayed", "morsy").verifyComplete();
	}

	@Test
	public void testNamesStream() {
		Flux<String> namesFlux = Flux.fromStream(names.stream());
		StepVerifier.create(namesFlux).expectNext("ahmed", "abdallah", "sayed", "morsy").verifyComplete();
	}

	@Test
	public void monoJustOrEmpty() {
		Mono<String> namesMono = Mono.justOrEmpty(null);
		StepVerifier.create(namesMono.log()).verifyComplete();
	}

	@Test
	public void monowithSupplier() {
		Supplier<String> stringSupplier = () -> "abdo";
		System.out.println(stringSupplier.get());
		Mono<String> namesMono = Mono.fromSupplier(stringSupplier);
		StepVerifier.create(namesMono.log()).expectNext("abdo").verifyComplete();
	}

	@Test
	public void fluxUsingRanges() {
		Flux<Integer> integerFlux = Flux.range(1, 5).log();
		StepVerifier.create(integerFlux).expectNext(1, 2, 3,4, 5).verifyComplete();

	}
}
