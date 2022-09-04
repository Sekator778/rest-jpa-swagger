package com.example.bookjpa.repository;

import com.example.bookjpa.domain.BookEntity;
import org.springframework.data.repository.CrudRepository;


public interface BookRepository extends CrudRepository<BookEntity, String> {
}
