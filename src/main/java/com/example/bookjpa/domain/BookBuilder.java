package com.example.bookjpa.domain;

public class BookBuilder {
    private static BookBuilder instance = new BookBuilder();
    private int id = 0;
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

    public BookBuilder withId(int id) {
        this.id = id;
        return instance;
    }

    public BookEntity build() {
        BookEntity result = new BookEntity(this.description);
        if (id != 0) {
            result.setId(id);
        }
        return result;
    }

}
