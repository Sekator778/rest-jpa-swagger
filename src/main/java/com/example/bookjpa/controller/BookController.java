package com.example.bookjpa.controller;

import com.example.bookjpa.domain.Book;
import com.example.bookjpa.domain.BookBuilder;
import com.example.bookjpa.repository.BookRepository;
import com.example.bookjpa.validation.BookValidationError;
import com.example.bookjpa.validation.BookValidationErrorBuilder;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class BookController {
    private BookRepository repository;

    @Autowired
    public BookController(BookRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/book")
    public ResponseEntity<Iterable<Book>> getBooks() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable String id) {
        Optional<Book> book = repository.findById(id);
        if (book.isPresent()) {
            return ResponseEntity.ok(book.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/book/{id}")
    public ResponseEntity<Book> setCompleted(@PathVariable String id) {
       Optional<Book> book = repository.findById(id);
       if (!book.isPresent()) {
           return ResponseEntity.notFound().build();
       }
       Book result = book.get();
       result.setCompleted(true);
       repository.save(result);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .buildAndExpand(result.getId())
                .toUri();

        return ResponseEntity.ok().header(
                "Location", location.toString()
        ).build();
    }

    @RequestMapping(value = "/book", method = {RequestMethod.POST, RequestMethod.PUT})
    public ResponseEntity<?> createBook(@Valid @RequestBody Book book, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest()
                    .body(BookValidationErrorBuilder.fromBindingErrors(errors));
        }
        Book result = repository.save(book);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(result.getId())
                .toUri();
        return ResponseEntity.created(location)
                .build();
    }

    @DeleteMapping("/book/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable String id) {
        repository.delete(BookBuilder.create().withId(id).build());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/book")
    public ResponseEntity<Book> deleteBook(@RequestBody Book book) {
        repository.delete(book);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public BookValidationError handleException(Exception exception) {
        return new BookValidationError(exception.getMessage());
    }
}
