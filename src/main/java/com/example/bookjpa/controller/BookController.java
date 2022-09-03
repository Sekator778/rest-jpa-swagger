package com.example.bookjpa.controller;

import com.example.bookjpa.domain.Book;
import com.example.bookjpa.domain.BookBuilder;
import com.example.bookjpa.repository.BookRepository;
import com.example.bookjpa.validation.BookValidationError;
import com.example.bookjpa.validation.BookValidationErrorBuilder;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

/**
 * for swagger use -<a href="http://localhost:port/swagger-ui/index.html">...</a>
 */
@Slf4j
@OpenAPIDefinition()
@Tag(name = "Demo rest api with jpa mentor course SHPP", description = "v: 1.0")
@RestController
@RequestMapping("/api")
public class BookController {
    private final BookRepository repository;

    @Autowired
    public BookController(BookRepository repository) {
        this.repository = repository;
        log.info("book repository was be autowired");
    }

    @GetMapping("/book")
    public ResponseEntity<Iterable<Book>> getBooks() {
        log.info("used get mapping to /book");
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable String id) {
        log.info("method get book by id used");
        Optional<Book> book = repository.findById(id);
        return book.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/book/{id}")
    public ResponseEntity<Book> setCompleted(@PathVariable String id) {
        log.info("method set or update book by id used");
        Optional<Book> book = repository.findById(id);
       if (book.isEmpty()) {
           log.warn("{} - its incorrect id check input", id);
           return ResponseEntity.notFound().build();
       }
       Book result = book.get();
       result.setCompleted(true);
       repository.save(result);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .buildAndExpand(result.getId())
                .toUri();
        log.info("method set or update book done");
        return ResponseEntity.ok().header(
                "Location", location.toString()
        ).build();
    }

    @RequestMapping(value = "/book", method = {RequestMethod.POST, RequestMethod.PUT})
    public ResponseEntity<?> createBook(@Valid @RequestBody Book book, Errors errors) {
        if (errors.hasErrors()) {
            log.warn("not created its has errors");
            return ResponseEntity.badRequest()
                    .body(BookValidationErrorBuilder.fromBindingErrors(errors));
        }
        Book result = repository.save(book);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(result.getId())
                .toUri();
        log.info("success created book");
        return ResponseEntity.created(location)
                .build();
    }

    @DeleteMapping("/book/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable String id) {
        repository.delete(BookBuilder.create().withId(id).build());
        log.info("book with id - {} has deleted", id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/book")
    public ResponseEntity<Book> deleteBook(@RequestBody Book book) {
        repository.delete(book);
        log.info("book deleted {}", book);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public BookValidationError handleException(Exception exception) {
        log.error("handleException used");
        return new BookValidationError(exception.getMessage());
    }
}
