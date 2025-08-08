package com.biblioteca.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.biblioteca.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>{

}
