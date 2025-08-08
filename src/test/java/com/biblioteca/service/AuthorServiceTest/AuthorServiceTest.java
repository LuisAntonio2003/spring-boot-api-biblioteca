package com.biblioteca.service.AuthorServiceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.biblioteca.exception.ResourceNotFoundException;
import com.biblioteca.model.Author;
import com.biblioteca.repository.AuthorRepository;
import com.biblioteca.service.AuthorService;

@ExtendWith(MockitoExtension.class) // Habilita Mockito para esta clase de prueba
public class AuthorServiceTest {

	@Mock // Crea una versión simulada del repositorio
	private AuthorRepository authorRepository;

	@InjectMocks // Inyecta los mocks en la instancia del servicio que vamos a probar
	private AuthorService authorService;

	private Author author;

	@BeforeEach // Se ejecuta antes de cada método de prueba
	void setup() {
		author = new Author(1L, "J.R.R. Tolkien", "Británica");
	}

	@Test // Marca este método como una prueba unitaria
	void whenFindAll_thenReturnAuthorList() {
		// GIVEN: Define el comportamiento del mock
		List<Author> authorList = Arrays.asList(author, new Author(2L, "George Orwell", "Británica"));
		when(authorRepository.findAll()).thenReturn(authorList);

		// WHEN: Llama al método del servicio
		List<Author> foundAuthors = authorService.findAll();

		// THEN: Comprueba el resultado
		assertEquals(2, foundAuthors.size());
	}

	@Test
	void whenFindById_thenAuthorShouldBeFound() {
		// GIVEN: Define el comportamiento del mock
		when(authorRepository.findById(1L)).thenReturn(Optional.of(author));

		// WHEN: Llama al método del servicio
		Author foundAuthor = authorService.findById(1L);

		// THEN: Comprueba el resultado
		assertEquals("J.R.R. Tolkien", foundAuthor.getName());
	}

	@Test
	void whenFindById_withNonExistentId_thenThrowException() {
		// GIVEN: Define el comportamiento del mock para un ID que no existe
		when(authorRepository.findById(99L)).thenReturn(Optional.empty());

		// WHEN & THEN: Verifica que se lance la excepción esperada
		assertThrows(ResourceNotFoundException.class, () -> {
			authorService.findById(99L);
		});
	}

	@Test
	void whenSave_thenAuthorIsReturned() {
		// GIVEN: Define el comportamiento del mock
		when(authorRepository.save(any(Author.class))).thenReturn(author);

		// WHEN: Llama al método del servicio
		Author savedAuthor = authorService.save(author);

		// THEN: Comprueba el resultado
		assertEquals("J.R.R. Tolkien", savedAuthor.getName());
	}
}