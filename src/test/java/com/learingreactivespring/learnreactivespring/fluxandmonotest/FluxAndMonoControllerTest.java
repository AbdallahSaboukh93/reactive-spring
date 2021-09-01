package com.learingreactivespring.learnreactivespring.fluxandmonotest;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@WebFluxTest
@AutoConfigureWebTestClient(timeout = "90000") //
public class FluxAndMonoControllerTest {

	@Autowired
	WebTestClient webTestClient;

	@Test
	public void fluxApproch1() {
		Flux<Integer> integerFlux = webTestClient.get().uri("/flux").accept(MediaType.APPLICATION_JSON_UTF8).exchange()
				.expectStatus().isOk().returnResult(Integer.class).getResponseBody();

		StepVerifier.create(integerFlux).expectSubscription().expectNext(1).expectNext(2).expectNext(3).expectNext(4)
				.verifyComplete();
	}

	@Test
	public void fluxApproch2() {
		webTestClient.get().uri("/flux").accept(MediaType.APPLICATION_JSON_UTF8).exchange().expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8).expectBodyList(Integer.class).hasSize(4);
	}

	@Test
	public void fluxApproch3() {
		List<Integer> expectedIntegerList = Arrays.asList(1, 2, 3, 4);
		EntityExchangeResult<List<Integer>> entityExchangeResult = webTestClient.get().uri("/flux")
				.accept(MediaType.APPLICATION_JSON_UTF8).exchange().expectStatus().isOk().expectBodyList(Integer.class)
				.returnResult();
		assertEquals(expectedIntegerList, entityExchangeResult.getResponseBody());
	}

	@Test
	public void fluxApproch4() {
		List<Integer> expectedIntegerList = Arrays.asList(1, 2, 3, 4);
		EntityExchangeResult<List<Integer>> entityExchangeResult = webTestClient.get().uri("/flux")
				.accept(MediaType.APPLICATION_JSON_UTF8).exchange().expectStatus().isOk().expectBodyList(Integer.class)
				.consumeWith((response) -> {
					assertEquals(expectedIntegerList, response.getResponseBody());
				});
	}
	

	@Test
	public void fluxApproch5() {
//		Flux<Long> integerFlux = 
//				webTestClient.get()
//				.uri("/fluxStream")
//				.accept((MediaType.APPLICATION_STREAM_JSON_VALUE)
//				.exchange()
//				.expectStatus().isOk().returnResult(Long.class).getResponseBody();
//
//		StepVerifier.create(integerFlux).expectSubscription()
//		.expectNext(1l).expectNext(2l)
//		.expectNext(3l).expectNext(4l)
//				.thenCancel().verify();
	}
	
	@Test
	public void mono() {
		List<Integer> expectedIntegerList = Arrays.asList(1);
          webTestClient.get().uri("/mono")
				.accept(MediaType.APPLICATION_JSON_UTF8).exchange().expectStatus().isOk().expectBodyList(Integer.class)
				.consumeWith((response) -> {
					assertEquals(expectedIntegerList, response.getResponseBody());
				});
	}


}
