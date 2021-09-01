package com.learingreactivespring.learnreactivespring.fluxandmonotest;

import java.time.Duration;

import org.junit.jupiter.api.Test;
import org.springframework.data.mongodb.core.aggregation.StringOperators.Concat;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxAndMonoCombineTest {

	
	@Test
	public void combineUsingMerge() {
		Flux<String> flux1= Flux.just("A","B","C");
		Flux<String> flux2= Flux.just("D","E","G");
		Flux<String> mergedFlux=Flux.merge(flux1 ,flux2);
		StepVerifier.create(mergedFlux.log()).expectSubscription()
		.expectNext("A","B","C","D","E","G").verifyComplete();
	}
	
	
	@Test
	public void combineUsingMerge_withDelay() {
		Flux<String> flux1= Flux.just("A","B","C").delayElements(Duration.ofSeconds(1));
		Flux<String> flux2= Flux.just("D","E","G").delayElements(Duration.ofSeconds(1));
		Flux<String> mergedFlux=Flux.merge(flux1 ,flux2);
		StepVerifier.create(mergedFlux.log()).expectSubscription()
		.expectNextCount(6).verifyComplete();
	}
	
	
	@Test
	public void combineUsingMerge_withConcat() {
		Flux<String> flux1= Flux.just("A","B","C");
		Flux<String> flux2= Flux.just("D","E","G");
		Flux<String> mergedFlux=Flux.concat(flux1 ,flux2);
		StepVerifier.create(mergedFlux.log()).expectSubscription()
		.expectNext("A","B","C","D","E","G").verifyComplete();
	}
	
	
	@Test
	public void combineUsingMerge_withZip() {
		Flux<String> flux1= Flux.just("A","B","C");
		Flux<String> flux2= Flux.just("D","E","G");
		Flux<String> mergedFlux=Flux.zip(flux1 ,flux2,(t1,t2) -> {
		return	t1.concat(t2);
		});
		StepVerifier.create(mergedFlux.log()).expectSubscription()
		.expectNext("AD","BE","CG").verifyComplete();
	}
	
	
}
