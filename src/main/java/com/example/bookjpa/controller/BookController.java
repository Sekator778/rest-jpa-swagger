package com.example.bookjpa.controller;

import com.example.bookjpa.domain.BookBuilder;
import com.example.bookjpa.domain.BookEntity;
import com.example.bookjpa.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * for swagger use -<a href="http://localhost:port/swagger-ui/index.html">...</a>
 */
@Slf4j
@RestController
@RequestMapping("/api")
public class BookController {
    private final BookService service;

    @Autowired
    public BookController(BookService service) {
        this.service = service;
        log.info("book service was be autowired");
    }


    @Operation(summary = "GET request")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "All good done", content = {@Content(examples = {@ExampleObject(value = "Book", summary = "subject #1")})})})
    @GetMapping("/book")
    public ResponseEntity<Iterable<BookEntity>> getBooks() {
        log.info("used get mapping to /book");
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<BookEntity> getBookById(@PathVariable String id) {
        log.info("method get book by id used");
        Optional<BookEntity> book = service.findById(id);
        return book.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/book/{id}")
    public ResponseEntity<BookEntity> setCompleted(@PathVariable String id) {
        log.info("method set or update book by id used");
        Optional<BookEntity> book = service.findById(id);
        if (book.isEmpty()) {
            log.warn("{} - its incorrect id check input", id);
            return ResponseEntity.notFound().build();
        }
        BookEntity result = book.get();
        result.setCompleted(true);
        service.save(result);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .buildAndExpand(result.getId())
                .toUri();
        log.info("method set or update book done");
        return ResponseEntity.ok().header(
                "Location", location.toString()
        ).build();
    }

    //    @RequestMapping(value = "/book", method = {RequestMethod.POST, RequestMethod.PUT})
//    public ResponseEntity<?> createBook(@Valid @RequestBody BookEntity bookEntity, Errors errors) {
//        if (errors.hasErrors()) {
//            log.warn("not created its has errors");
//            return ResponseEntity.badRequest()
//                    .body(BookValidationErrorBuilder.fromBindingErrors(errors));
//        }
//        BookEntity result = service.save(bookEntity);
//        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
//                .path("/{id}")
//                .buildAndExpand(result.getId())
//                .toUri();
//        log.info("success created book");
//        return ResponseEntity.created(location)
//                .build();
//    }
    @Operation(summary = "use post request we will be create and save model")
    @PostMapping("/book")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> addBook(@Validated @RequestBody BookEntity bookEntity) {
        return service.save(bookEntity);
    }

    @DeleteMapping("/book/{id}")
    public ResponseEntity<BookEntity> deleteBook(@PathVariable String id) {
        service.delete(BookBuilder.create().withId(id).build());
//        service.deleteWithId(id);
        log.info("book with id - {} has deleted", id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/book")
    public ResponseEntity<BookEntity> deleteBook(@RequestBody BookEntity bookEntity) {
        service.delete(bookEntity);
        log.info("book deleted {}", bookEntity);
        return ResponseEntity.noContent().build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        log.info("method handleValidationExceptions");
        return errors;
    }

//    @ExceptionHandler
//    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
//    public BookValidationError handleException(Exception exception) {
//        log.error("handleException used");
//        return new BookValidationError(exception.getMessage());
//    }
}
