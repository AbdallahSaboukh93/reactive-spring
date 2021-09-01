package com.learingreactivespring.learnreactivespring.fluxandmonotest;

import java.time.Duration;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class ColdAndHotPublisherTest {
	
@Test
public void  coldPublisherTest() throws InterruptedException {
	Flux<String> stringFlux = Flux.just("A", "B", "C","E", "F", "G").delayElements(Duration.ofSeconds(1));

	
	stringFlux.subscribe(s -> System.out.println("subscriper 1 "+s) );
	Thread.sleep(2000);
	
	stringFlux.subscribe(s -> System.out.println("subscriper 2 "+s) );
	Thread.sleep(4000);

}
	

@Test
public void  hotPublisherTest() throws InterruptedException {
	Flux<String> stringFlux = Flux.just("A", "B", "C","E", "F", "G").delayElements(Duration.ofSeconds(1));

	
	ConnectableFlux<String> connectableFlux =stringFlux.publish();
	connectableFlux.connect();
	connectableFlux.subscribe(s -> System.out.println("subscriper 1 "+s) );
	Thread.sleep(3000);
	
	connectableFlux.subscribe(s -> System.out.println("subscriper 2 "+s) );
	Thread.sleep(4000);

}
}
