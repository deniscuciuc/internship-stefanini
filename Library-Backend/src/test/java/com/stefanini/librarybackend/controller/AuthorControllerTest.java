package com.stefanini.librarybackend.controller;

import com.stefanini.librarybackend.domain.Author;
import com.stefanini.librarybackend.service.impl.AuthorServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class AuthorControllerTest {

    @Mock
    private AuthorServiceImpl authorService;
    private AuthorController underTest;
    private AutoCloseable autoCloseable;

    @BeforeEach
    void setup() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new AuthorController(authorService);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    /**
     * Unit test for {@link AuthorController#createAuthor(Author) createAuthor} method
     */
    @Test
    void shouldNotChangeAuthorBeforeCallingCreateMethodFromService() {
        Author author = new Author(
                "Nicolas",
                "Gary",
                Date.valueOf(LocalDate.now()),
                "Cool author"
        );

        underTest.createAuthor(author);

        ArgumentCaptor<Author> authorArgumentCaptor = ArgumentCaptor.forClass(Author.class);

        verify(authorService)
                .addAuthor(authorArgumentCaptor.capture());

        Author capturedAuthor = authorArgumentCaptor.getValue();
        assertThat(capturedAuthor).isEqualTo(author);
    }

    /**
     * Unit test for {@link AuthorController#updateAuthor(int, Author) updateAuthor} method
     */
    @Test
    @Disabled
    void shouldNotChangeNewAuthorDataAndIdBeforeCallingUpdateMethodFromService() {

    }

    /**
     * Unit test for {@link AuthorController#assignBook(int, int) assignBook} method
     */
    @Test
    @Disabled
    void assignBook() {
    }

    /**
     * Unit test for {@link AuthorController#deleteAuthor(int) deleteAuthor} method
     */
    @Test
    @Disabled
    void deleteAuthor() {
    }

    /**
     * Unit test for {@link AuthorController#getAllAuthors() getAllAuthors} method
     */
    @Test
    @Disabled
    void getAllAuthors() {
    }
}