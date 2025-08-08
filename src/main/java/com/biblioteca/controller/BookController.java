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
import com.biblioteca.gutendex.GutendexResponse;
import com.biblioteca.model.Book;
import com.biblioteca.model.Author;
import com.biblioteca.service.AuthorService;
import com.biblioteca.service.BookService;

@RestController
@RequestMapping("/api/book")
public class BookController {

	@Autowired
	private BookService bookService;

	@GetMapping("/gutendex/all")
	public ResponseEntity<GutendexResponse> getFirstPageFromExternalApi() {
		return ResponseEntity.ok(bookService.findFirstPageFromGutendex());
	}

	// Endpoint original para obtener la página 2 de Gutendex
	@GetMapping("/gutendex")
	public ResponseEntity<GutendexResponse> getBooksFromExternalApi() {
		return ResponseEntity.ok(bookService.findBookFromGutendex());
	}

	@Autowired
	private AuthorService authorService;

	// El request body puede tener uno de dos formatos:
	/*
	 * { "title": "Los secretos de Hitler", "isbn": "978-0321765723", "stock": 10,
	 * "autor": { "name": "Gilberto Bocon", "nationality": "Aleman" } // REGISTRO
	 * SIN AUTOR
	 * 
	 * { "title": "El Señor de los Anillos", "isbn": "978-0618055000", "stock": 10,
	 * "autor": { "id": 1 } }
	 */

	// READ (Obtener todos los libros)
	@GetMapping
	public ResponseEntity<List<Book>> getAllBooks() {
		List<Book> books = bookService.findAll();
		if (books.isEmpty()) {
			return new ResponseEntity<>(books, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(books, HttpStatus.OK);
	}

	// READ (Obtener un libro por ID)
	@GetMapping("/{id}")
	public ResponseEntity<Object> getBookById(@PathVariable Long id) {
		try {
			Book book = bookService.findById(id);
			return new ResponseEntity<>(book, HttpStatus.OK);
		} catch (ResourceNotFoundException e) {
			return new ResponseEntity<>(Map.of("message", e.getMessage()), HttpStatus.NOT_FOUND);
		}
	}

	// CREATE (Crear un nuevo libro y, opcionalmente, un nuevo autor)
	@PostMapping
	public ResponseEntity<Map<String, Object>> createBook(@RequestBody Book book) {
		try {
			// Si el objeto 'author' en el cuerpo de la solicitud tiene un 'id',
			// se busca al autor existente en la base de datos.
			if (book.getAutor() != null && book.getAutor().getId() != null) {
				Author existingAuthor = authorService.findById(book.getAutor().getId());
				book.setAutor(existingAuthor);
			}

			Book savedBook = bookService.save(book);
			return new ResponseEntity<>(Map.of("message", "Libro creado con éxito", "book", savedBook),
					HttpStatus.CREATED);
		} catch (ResourceNotFoundException e) {
			return new ResponseEntity<>(Map.of("message", "Error al crear el libro: " + e.getMessage()),
					HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(Map.of("message", "Error al crear el libro: " + e.getMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}

	// UPDATE (Actualizar un libro existente)
	@PutMapping("/{id}")
	public ResponseEntity<Object> updateBook(@PathVariable Long id, @RequestBody Book book) {
		try {
			// findById() ya valida la existencia y lanza una excepción si no lo encuentra.
			Book existingBook = bookService.findById(id);
			book.setId(existingBook.getId());
			Book updatedBook = bookService.save(book);
			return new ResponseEntity<>(Map.of("message", "Libro actualizado con éxito", "book", updatedBook),
					HttpStatus.OK);
		} catch (ResourceNotFoundException e) {
			return new ResponseEntity<>(Map.of("message", e.getMessage()), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(Map.of("message", "Error al actualizar el libro: " + e.getMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}

	// DELETE (Eliminar un libro)
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteBook(@PathVariable Long id) {
		try {
			bookService.deleteById(id);
			return new ResponseEntity<>(Map.of("message", "Libro eliminado con éxito"), HttpStatus.NO_CONTENT);
		} catch (ResourceNotFoundException e) {
			return new ResponseEntity<>(Map.of("message", e.getMessage()), HttpStatus.NOT_FOUND);
		}
	}
	/*
	 * public ResponseEntity<Book> createBook(@RequestBody Book book) { // Si el
	 * autor es nuevo y no tiene ID, JPA lo persistirá // automáticamente gracias a
	 * 'cascade = CascadeType.PERSIST' // Si el autor tiene un ID, Hibernate
	 * intentará asociarlo. return new ResponseEntity<>(bookService.save(book),
	 * HttpStatus.CREATED); }
	 * 
	 * // READ (Obtener todos los libros)
	 * 
	 * @GetMapping public List<Book> getAllBooks() { return bookService.findAll(); }
	 * 
	 * // READ (Obtener un libro por ID)
	 * 
	 * @GetMapping("/{id}") public ResponseEntity<Book> getBookById(@PathVariable
	 * Long id) { return bookService.findById(id).map(book -> new
	 * ResponseEntity<>(book, HttpStatus.OK)) .orElse(new
	 * ResponseEntity<>(HttpStatus.NOT_FOUND)); }
	 * 
	 * // UPDATE (Actualizar un libro existente)
	 * 
	 * @PutMapping("/{id}") public ResponseEntity<Book> updateBook(@PathVariable
	 * Long id, @RequestBody Book book) { return
	 * bookService.findById(id).map(existingBook -> { book.setId(id); return new
	 * ResponseEntity<>(bookService.save(book), HttpStatus.OK); }).orElse(new
	 * ResponseEntity<>(HttpStatus.NOT_FOUND)); }
	 * 
	 * // DELETE (Eliminar un libro)
	 * 
	 * @DeleteMapping("/{id}") public ResponseEntity<Void> deleteBook(@PathVariable
	 * Long id) { if (bookService.findById(id).isPresent()) {
	 * bookService.deleteById(id); return new
	 * ResponseEntity<>(HttpStatus.NO_CONTENT); } else { return new
	 * ResponseEntity<>(HttpStatus.NOT_FOUND); } }
	 */
}