package com.biblioteca.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.biblioteca.exception.ResourceNotFoundException;
import com.biblioteca.model.Author;
import com.biblioteca.repository.AuthorRepository;

@Service
public class AuthorService {

	private final AuthorRepository authorRepository;

	@Autowired
	public AuthorService(AuthorRepository authorRepository) {
		this.authorRepository = authorRepository;
	}

	public int countBooksByAuthorId(Long authorId) {
		// Llama al método del repositorio que invoca el stored procedure
		return authorRepository.countBooksByAuthor(authorId);
	}

	// INICALIZACIÓN DE CRUD

	// CRUD PARA CREAR Y ACTUALIZAR EL AUTOR
	public Author save(Author author) {
		return authorRepository.save(author);
	}

	// CRUD PARA LA LECTURA DE TODOS LOS AUTORES
	public List<Author> findAll() {
		return authorRepository.findAll();
	}

	// CRUD PARA LA LECTURA DEL AUTOR POR ID
	public Author findById(Long id) {
		return authorRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("No se ha encontrado un autor con el identificador: " + id));
	}

	// CRUD PARA LA ELIMINACIÓN DEL AUTOR POR ID
	public void deleteById(Long id) {
		Author authorToDelete = findById(id);
		authorRepository.delete(authorToDelete);
	}
}
