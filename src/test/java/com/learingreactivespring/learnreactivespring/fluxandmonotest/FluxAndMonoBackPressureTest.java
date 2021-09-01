package com.learingreactivespring.learnreactivespring.fluxandmonotest;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxAndMonoBackPressureTest {

	@Test
	public void testBackPressureTest() {
		Flux<Integer> backPressureFlux = Flux.range(1, 10).log();

		StepVerifier.create(backPressureFlux)
		.expectSubscription().thenRequest(1).expectNext(2).thenRequest(1)
				.expectNext(1).thenCancel().verify();

	}
	
	@Test
	public void testBackPressure() {
		Flux<Integer> backPressureFlux = Flux.range(1, 10).log();

		backPressureFlux.subscribe(element -> System.out.println(element),
				                    e -> System.err.println(e),
				                    () -> System.out.println("done"),
				                    subscription -> subscription.request(2)) ;

	}

	@Test
	public void testBackPressure_cancel() {
		Flux<Integer> backPressureFlux = Flux.range(1, 10).log();

		backPressureFlux.subscribe(element -> System.out.println(element),
				                    e -> System.err.println(e),
				                    () -> System.out.println("done"),
				                    subscription -> subscription.cancel()) ;

	}
	

	@Test
	public void customiseBackPressure() {
		Flux<Integer> backPressureFlux = Flux.range(1, 10).log();

		backPressureFlux.subscribe(new BaseSubscriber<Integer>() {

			@Override
			protected void hookOnNext(Integer value) {
				request(1);
				System.out.println("value recived " +value);
				if(value==4) {
					cancel() ;
				}
			}
			
		}) ;

	}

}
