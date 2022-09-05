package com.example.bookjpa.service;

import com.example.bookjpa.domain.BookEntity;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface BookService {
    Iterable<BookEntity> findAll();
    ResponseEntity<?> save(BookEntity bookEntity);
    void delete(BookEntity bookEntity);
    void deleteWithId(String id);
    Optional<BookEntity> findById(String id);
}
