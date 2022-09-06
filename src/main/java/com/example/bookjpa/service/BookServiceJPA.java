package com.example.bookjpa.service;

import com.example.bookjpa.domain.BookEntity;
import com.example.bookjpa.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 *
 */
@Service
public class BookServiceJPA implements BookService {
    private final BookRepository repository;

    private final Logger LOG = LoggerFactory.getLogger(BookServiceJPA.class);
    @Autowired
    private Validator validator;

    @Autowired
    public BookServiceJPA(BookRepository repository) {
        this.repository = repository;
    }


    @Override
    public ResponseEntity<BookEntity> save(BookEntity bookEntity) {
        Set<ConstraintViolation<BookEntity>> violations = validator.validate(bookEntity);
        if (!violations.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            for (ConstraintViolation<BookEntity> constraintViolation : violations) {
                stringBuilder.append(constraintViolation.getMessage());
            }
            LOG.info("the model was not inserted into the database because it did not pass validation");
            throw new ConstraintViolationException("Error occurred: " + stringBuilder, violations);
        }
        LOG.info("model save to DB with id {}", bookEntity.getId());
        BookEntity result = repository.save(bookEntity);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(result.getId())
                .toUri();
        LOG.info("success created book");
        return ResponseEntity.created(location)
                .build();
    }

    @Override
    public void delete(BookEntity bookEntity) {
        repository.delete(bookEntity);
    }
    @Override
    public void deleteWithId(String id) {
        repository.deleteById(id);
    }

    @Override
    public Iterable<BookEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<BookEntity> findById(String id) {
        return repository.findById(id);
    }



//    public void deleteAccidentById(String id) {
//        service.deleteAccidentById(id);
//    }

}
