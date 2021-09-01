package com.learingreactivespring.learnreactivespring.fluxandmonotest;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxAndMonoErrorTest {

	@Test
	public void fluxErrorHandling_errorReturn() {
		Flux<String> stringFlux = Flux.just("A", "B", "C")
				.concatWith(Flux.error(new RuntimeException("exception occured "))).concatWith(Flux.just("D"))
				.onErrorReturn("default");

		StepVerifier.create(stringFlux.log()).expectSubscription().expectNext("A", "B", "C").expectNext("default")
				.verifyComplete();
	}

	@Test
	public void fluxErrorHandling_OnErrorMap() {
		Flux<String> stringFlux = Flux.just("A", "B", "C")
				.concatWith(Flux.error(new RuntimeException("exception occured "))).concatWith(Flux.just("D"))
				.onErrorMap((e) -> new CustomException(e));

		StepVerifier.create(stringFlux.log()).expectSubscription().expectNext("A", "B", "C")
				.expectError(CustomException.class).verify();
	}
	
	@Test
	public void fluxErrorHandling_withRetry() {
		Flux<String> stringFlux = Flux.just("A", "B", "C")
				.concatWith(Flux.error(new RuntimeException("exception occured "))).concatWith(Flux.just("D"))
				.onErrorMap((e) -> new CustomException(e)).retry(1);

		StepVerifier.create(stringFlux.log()).expectSubscription().expectNext("A", "B", "C")
		.expectNext("A", "B", "C")
				.expectError(CustomException.class).verify();
	}
	
	
}
