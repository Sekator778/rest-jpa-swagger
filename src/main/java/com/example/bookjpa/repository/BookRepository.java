package com.example.bookjpa.repository;

import com.example.bookjpa.domain.Book;
import org.springframework.data.repository.CrudRepository;


public interface BookRepository extends CrudRepository<Book, String> {
}
