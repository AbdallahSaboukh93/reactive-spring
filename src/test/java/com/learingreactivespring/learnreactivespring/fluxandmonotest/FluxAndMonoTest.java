package com.learingreactivespring.learnreactivespring.fluxandmonotest;

import static org.junit.jupiter.api.Assertions.*;

import javax.management.RuntimeErrorException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class FluxAndMonoTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void test() {
		// fail("Not yet implemented");
	}

	@Test
	public void fluxAndMonoTest() {
		Flux<String> stringFlux = Flux.just("spring", "spring boot", "reactive spring")
				// .concatWith(Flux.error(new RuntimeException("exceptionOccurred")))
				.concatWith(Flux.just("after error")).log();

		stringFlux.subscribe(System.out::println, (e) -> System.err.println("" + e),
				() -> System.out.println("completed"));
	}
	
	@Test
	public void testElements_withoutError() {
		Flux<String> stringFlux = Flux.just("spring", "spring boot", "reactive spring")
				.log();

		StepVerifier.create(stringFlux).expectNext("spring", "spring boot", "reactive spring").verifyComplete();
	}

	@Test
	public void testElements_withError() {
		Flux<String> stringFlux = Flux.just("spring", "spring boot", "reactive spring")
				 .concatWith(Flux.error(new RuntimeException("exceptionOccurred")))
				.log();

		StepVerifier.create(stringFlux).expectNext("spring", "spring boot", "reactive spring").verifyComplete();
	}

	@Test
	public void testElementsCount_withError() {
		Flux<String> stringFlux = Flux.just("spring", "spring boot", "reactive spring")
			 .concatWith(Flux.error(new RuntimeException("exceptionOccurred")))
				.log();

		StepVerifier.create(stringFlux).expectNextCount(3)
		.expectErrorMessage("exceptionOccurred")
		.verify();
	}
	
	@Test
	public void monotest() {
		Mono<String> stringMono = Mono.just("spring");

		StepVerifier.create(stringMono.log())
		.expectNext("spring")
		.verifyComplete();
	}
	
	@Test
	public void monotest_error() {

		StepVerifier.create(Mono.just( new RuntimeException("exception")).log())
		.expectError(RuntimeException.class)
		.verify();
	}





}
