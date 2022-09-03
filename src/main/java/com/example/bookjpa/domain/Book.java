package com.example.bookjpa.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
@Slf4j
@Entity
@Data
@NoArgsConstructor
public class Book {
    @NotNull
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;
    @NotNull
    @NotBlank
    @Column(insertable = true, updatable = false)
    private String description;
    private LocalDateTime created;
    private LocalDateTime modified;
    private boolean completed;

    public Book(String description) {
        this.description = description;
        log.info("constructor class Book has created");
    }

    @PrePersist
    void onCreate() {
        this.setCreated(LocalDateTime.now());
        this.setModified(LocalDateTime.now());
        log.info("method PrePersist done");
    }

    @PreUpdate
    void onUpdate() {
        this.setModified(LocalDateTime.now());
        log.info("method PreUpdate use");
    }
}
