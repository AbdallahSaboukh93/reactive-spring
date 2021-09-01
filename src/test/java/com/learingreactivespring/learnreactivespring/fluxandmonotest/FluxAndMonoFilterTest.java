package com.learingreactivespring.learnreactivespring.fluxandmonotest;

import static org.hamcrest.CoreMatchers.startsWith;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.scheduling.annotation.Async;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxAndMonoFilterTest {
	List<String> names = Arrays.asList("ahmed", "abdallah", "sayed", "morsy");

	@Test
	public void filterTest() {

		Flux<String> fluxString = Flux.fromIterable(names)
				.filter(f -> f.startsWith("a"))
				.log();
		StepVerifier.create(fluxString).expectNext("ahmed", "abdallah").verifyComplete();
	}
}
