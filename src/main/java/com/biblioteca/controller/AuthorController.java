package com.biblioteca.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.exception.ResourceNotFoundException;
import com.biblioteca.model.Author;
import com.biblioteca.service.AuthorService;

@RestController
@RequestMapping("/api/author")

public class AuthorController {

	@Autowired // Inyecta automacticamente un instancia del servicio AuthorService
	private AuthorService authorService;

	@GetMapping("/{id}/book-count") // Mapea las solicirudes GET a la URL
	public ResponseEntity<Integer> getBookCountByAuthorId(@PathVariable("id") Long id) {
		// Llama al servicio, y a su vez llama al stored procedure
		int bookCount = authorService.countBooksByAuthorId(id);
		return ResponseEntity.ok(bookCount);
	}

	/* PREMITE CREAR UN NUEVO AUTHOR
	 * 
	 * mapea las solicitudes POST a la URL 
	 *  */
	@PostMapping
	public ResponseEntity<Map<String, Object>> createAuthor(@RequestBody Author author) {
		try {
			
			// llama al servicio para guardar el nuevo author
			Author savedAuthor = authorService.save(author);
			//retorna el valor guardado y su respectivo msj
			return new ResponseEntity<>(Map.of("message", "Autor creado con éxito", "author", savedAuthor),
					HttpStatus.CREATED);
		} catch (Exception e) {
			// retorna el posible error que impidio que se guardara el author
			return new ResponseEntity<>(Map.of("message", "Error al crear el autor: " + e.getMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}

	/* PERMITE OBTENER TODOS LOS AUTORES REGISTRADOS */
	@GetMapping
	public ResponseEntity<List<Author>> getAllAuthors() {
		List<Author> authors = authorService.findAll();
		if (authors.isEmpty()) {
			return new ResponseEntity<>(authors, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(authors, HttpStatus.OK);
	}

	// READ (Obtener un autor por ID)
	@GetMapping("/{id}")
	public ResponseEntity<Object> getAuthorById(@PathVariable Long id) {
		try {
			Author author = authorService.findById(id);
			return new ResponseEntity<>(author, HttpStatus.OK);
		} catch (ResourceNotFoundException e) {
			return new ResponseEntity<>(Map.of("message", e.getMessage()), HttpStatus.NOT_FOUND);
		}
	}

	// PERMITE ACTUALIZAR UN AUTHOR EXISTENTE
	@PutMapping("/{id}")
	public ResponseEntity<Object> updateAuthor(@PathVariable Long id, @RequestBody Author author) {
		try {
			// findById() ya valida la existencia y lanza una excepción si no lo encuentra.
			Author existingAuthor = authorService.findById(id);
			author.setId(existingAuthor.getId());
			Author updatedAuthor = authorService.save(author);
			return new ResponseEntity<>(Map.of("message", "Autor actualizado con éxito", "author", updatedAuthor),
					HttpStatus.OK);
		} catch (ResourceNotFoundException e) {
			return new ResponseEntity<>(Map.of("message", e.getMessage()), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(Map.of("message", "Error al actualizar el autor: " + e.getMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}

	// PERMITE ELIMINAR UN AUTOR REGISTRADO
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteAuthor(@PathVariable Long id) {
		try {
			authorService.deleteById(id);
			return new ResponseEntity<>(Map.of("message", "Autor eliminado con éxito"), HttpStatus.NO_CONTENT);
		} catch (ResourceNotFoundException e) {
			return new ResponseEntity<>(Map.of("message", e.getMessage()), HttpStatus.NOT_FOUND);
		}
	}
}
