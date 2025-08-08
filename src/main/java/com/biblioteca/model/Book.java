package com.biblioteca.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Table(name = "libro") //REFRENCIA A LA TABLA
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_seq_generator")
	@SequenceGenerator(name = "book_seq_generator", sequenceName = "BOOK_SEQ", allocationSize = 1) 
	private Long id;
	private String title;
	
	@NonNull 
	@Column(name = "ISBN")
	private String isbn;
	
	@Column(name = "STOCK")
	private int stock;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "author_id")
	private Author autor;
}

