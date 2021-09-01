package com.learingreactivespring.learnreactivespring.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

import com.learingreactivespring.learnreactivespring.documents.Item;

import reactor.core.publisher.Flux;

public interface ItemDocumentRepository extends ReactiveMongoRepository<Item, String> {
	Flux<Item> findByDescription(String description);

}
