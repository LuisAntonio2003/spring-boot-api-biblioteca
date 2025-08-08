package com.biblioteca.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.biblioteca.exception.ResourceNotFoundException;
import com.biblioteca.gutendex.GutendexResponse;
import com.biblioteca.model.Book;
import com.biblioteca.repository.BookRepository;

@Service
public class BookService {
	private final BookRepository bookRepository;
	private final RestTemplate restTemplate;

	@Autowired
	public BookService(BookRepository bookRepository, RestTemplate restTemplate) {
		this.bookRepository = bookRepository;
		this.restTemplate = restTemplate;
	}

	// Método para obtener la primera página de libros de Gutendex
	public GutendexResponse findFirstPageFromGutendex() {
		String gutendexUrl = "https://gutendex.com/books/";
		try {
			ResponseEntity<GutendexResponse> response = restTemplate.getForEntity(gutendexUrl, GutendexResponse.class);

			if (response.getStatusCode().is2xxSuccessful()
					&& response.getHeaders().getContentType().isCompatibleWith(MediaType.APPLICATION_JSON)) {
				return response.getBody();
			} else {
				throw new RuntimeException("Error: La API de Gutendex no devolvió un JSON válido.");
			}
		} catch (HttpClientErrorException e) {
			throw new RuntimeException("Error al conectar con Gutendex: " + e.getStatusCode(), e);
		} catch (Exception e) {
			throw new RuntimeException("Ocurrió un error inseperado al llamar a la API de Gutendex. ", e);
		}
	}

	// Método original para obtener la página 2 de libros de Gutendex
	public GutendexResponse findBookFromGutendex() {
		String gutendexUrl = "https://gutendex.com/books/?page=2";
		try {
			ResponseEntity<GutendexResponse> response = restTemplate.getForEntity(gutendexUrl, GutendexResponse.class);

			if (response.getStatusCode().is2xxSuccessful()
					&& response.getHeaders().getContentType().isCompatibleWith(MediaType.APPLICATION_JSON)) {
				return response.getBody();
			} else {
				throw new RuntimeException("Error: La API de Gutendex no devolvió un JSON válido.");
			}
		} catch (HttpClientErrorException e) {
			throw new RuntimeException("Error al conectar con Gutendex: " + e.getStatusCode(), e);
		} catch (Exception e) {
			throw new RuntimeException("Ocurrió un error inseperado al llamar a la API de Gutendex. ", e);
		}
	}

	// CRUD PARA GUARDAR Y MODIFICAR UN LIBRO
	public Book save(Book book) {
		return bookRepository.save(book);
	}

	// CRUD PARA MOSTRAR TODOS LOS LIBROS ALMACENADOS
	public List<Book> findAll() {
		return bookRepository.findAll();
	}

	// CRUD PARA FILTAR UNICAMENTE UN LIBRO DIRECTAMENTE POR SU ID
	public Book findById(Long id) {
		return bookRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("No se ha encontrado un libro con el identificador: " + id));
	}

	// CRUD PARA ELIMINAR UN LIBRO
	public void deleteById(Long id) {
		Book bookToDelete = findById(id); // Primero busca para asegurarse de que existe
		bookRepository.delete(bookToDelete);
	}
}
