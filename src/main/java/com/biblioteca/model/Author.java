package com.biblioteca.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "autor") //REFERENCIA A LA TABLA 
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Author {
	
	//DECLARACIÓN DE ATRIBUTOS DEL OBJETO
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_seq_generator")
	@SequenceGenerator(name = "book_seq_generator", sequenceName = "AUTHOR_SEQ", allocationSize = 1)
	private Long id;
	private String name;
	private String nationality;
}
