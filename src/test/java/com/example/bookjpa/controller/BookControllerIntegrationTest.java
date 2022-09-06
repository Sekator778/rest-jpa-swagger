package com.example.bookjpa.controller;

import com.example.bookjpa.domain.BookBuilder;
import com.example.bookjpa.domain.BookEntity;
import com.example.bookjpa.repository.BookRepository;
import com.example.bookjpa.service.BookService;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.ui.Model;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebMvcTest
@AutoConfigureMockMvc
public class BookControllerIntegrationTest {
    @MockBean
    private BookService bookService;
    @MockBean
    private BookRepository userRepository;
    @MockBean
    private BuildProperties buildProperties;

    @Autowired
    BookController userController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenPost() {
        List<BookEntity> books = Arrays.asList(
                BookBuilder.create().withDescription("desc1").build(),
                BookBuilder.create().withDescription("desc2").build()
        );
        Model model = mock(Model.class);
        BookService bookService = mock(BookService.class);
        when(bookService.findAll()).thenReturn(books);

        BookController bookController = new BookController(bookService);

//        String page = (String) bookController.addBook( BookBuilder.create().withDescription("desc2").build());
    }

    @Test
    public void whenPostRequestToUsersAndValidUser_thenCorrectResponse() throws Exception {
        MediaType textPlainUtf8 = new MediaType(MediaType.TEXT_PLAIN, StandardCharsets.UTF_8);
        String book = "{\n" +
                "  \"id\": \"string\",\n" +
                "  \"description\": \"string\",\n" +
                "  \"created\": \"2022-09-05T13:22:08.772Z\",\n" +
                "  \"modified\": \"2022-09-05T13:22:08.772Z\",\n" +
                "  \"completed\": true\n" +
                "}";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/book")
                        .content(book)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());

    }

    @Test
    public void whenPostRequestToUsersAndInValidUser_thenCorrectResponse() throws Exception {
        String book = "{}";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/book")
                        .content(book)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", Is.is("Description is mandatory")))
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }
}