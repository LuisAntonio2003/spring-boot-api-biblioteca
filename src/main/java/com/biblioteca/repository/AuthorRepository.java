package com.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.biblioteca.model.Author;

//Se elimina el @NamedStoredProcedureQuery de aquí y se mueve al DTO
@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

	// Llama a un stored procedure que cuenta los libros de un autor
	@Procedure(name = "count_books_by_author")
	int countBooksByAuthor(@Param("p_author_id") Long authorId);

}
