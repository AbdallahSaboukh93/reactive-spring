package com.learingreactivespring.learnreactivespring.repository;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.learingreactivespring.learnreactivespring.documents.Item;
import com.learingreactivespring.learnreactivespring.repositories.ItemDocumentRepository;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@DataMongoTest
@RunWith(SpringRunner.class)
public class ItemReactiveTest {
	@Autowired
	ItemDocumentRepository itemDocumentRepository;

	List<Item> itemList = Arrays.asList(new Item(null, "sumson", 20.0), new Item(null, "lg", 40.0),
			new Item(null, "unar", 25.0));

	@Before
	public void setUp() {
		itemDocumentRepository.deleteAll()
		.thenMany(Flux.fromIterable(itemList)
		.flatMap(itemDocumentRepository::save)
	    .doOnNext(itm ->{ System.out.println("item " +itm);}))
		.blockLast();
		

	}

	@Test
	public void getAllItems() {
		itemDocumentRepository.deleteAll()
		.thenMany(Flux.fromIterable(itemList)
		.flatMap(itemDocumentRepository::save)
	    .doOnNext(itm ->{ System.out.println("item " +itm);}))
		.blockLast();
		StepVerifier.create(itemDocumentRepository.findAll().log()).expectSubscription().expectNextCount(3)
				.verifyComplete();

	}
	
	@Test
	public void updateItem() {
		double price = 66.5 ;
		Flux<Item> fluxItem =itemDocumentRepository.findByDescription("lg");
	}

}
