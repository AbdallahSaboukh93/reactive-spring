package com.learingreactivespring.learnreactivespring.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document // entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {
	

	public Item(String id, String description, double price) {
		super();
		this.id = id;
		this.description = description;
		this.price = price;
	}
	
	@Id
	private String id ;
	private String description ;
	private double price  ;

}
