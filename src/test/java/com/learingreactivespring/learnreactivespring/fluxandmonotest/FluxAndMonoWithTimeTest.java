package com.learingreactivespring.learnreactivespring.fluxandmonotest;

import java.time.Duration;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxAndMonoWithTimeTest {

	@Test
	public void infiniteSequence() throws InterruptedException {

		Flux<Long> timFlux = Flux.interval(Duration.ofMillis(200)).log();
		timFlux.subscribe((elment) -> System.out.println("Value is " + elment));
		Thread.sleep(3000);
	}

	@Test
	public void infiniteSequenceMap() throws InterruptedException {

		Flux<Integer> timFlux = Flux.interval(Duration.ofMillis(100))
				.map(l ->  new Integer(l.intValue()))
				.take(3)
				.log();
		
		StepVerifier.create(timFlux).expectSubscription().expectNext(1,2,3).verifyComplete() ;
	}
}
