package com.example.bookjpa.domain;

public class BookBuilder {
    private static BookBuilder instance = new BookBuilder();
    private String id = null;
    private String description = "";

    private BookBuilder() {
    }

    public static BookBuilder create() {
        return instance;
    }

    public BookBuilder withDescription(String description) {
        this.description = description;
        return instance;
    }

    public BookBuilder withId(String id) {
        this.description = id;
        return instance;
    }

    public BookEntity build() {
        BookEntity result = new BookEntity(this.description);
        if (id != null) {
            result.setId(id);
        }
        return result;
    }

}
