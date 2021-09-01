package com.learingreactivespring.learnreactivespring.fluxandmonotest;

import java.time.Duration;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.test.scheduler.VirtualTimeScheduler;

public class VirsualTimeTest {

	@Test
	public void testwithoutVirsualTime() {
		Flux<Long> timFlux = Flux.interval(Duration.ofMillis(100)).take(3).log();

		StepVerifier.create(timFlux.log()).expectSubscription().expectNext(0l,1l, 2l).verifyComplete();

	}
	
	@Test
	public void testWithVirsualTime() {
		VirtualTimeScheduler.getOrSet();
		Flux<Long> timFlux = Flux.interval(Duration.ofMillis(100)).take(3).log();
		StepVerifier.withVirtualTime(() -> timFlux.log()).expectSubscription().thenAwait(Duration.ofSeconds(1)).expectNext(0l,1l, 2l).verifyComplete();
	}


}
