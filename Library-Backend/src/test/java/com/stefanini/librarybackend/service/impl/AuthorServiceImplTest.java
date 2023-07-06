package com.stefanini.librarybackend.service.impl;

import com.stefanini.librarybackend.dao.impl.AuthorDAOImpl;
import com.stefanini.librarybackend.dao.impl.BookDAOImpl;
import com.stefanini.librarybackend.domain.Author;
import com.stefanini.librarybackend.domain.Book;
import com.stefanini.librarybackend.service.AuthorService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.util.ArrayList;

import static com.stefanini.librarybackend.domain.enums.BookStatus.AVAILABLE;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


class AuthorServiceImplTest {

    @Mock
    private AuthorDAOImpl authorDAO;
    @Mock
    private BookDAOImpl bookDAO;
    private AutoCloseable autoCloseable;
    private AuthorService underTest;

    @BeforeEach
    void setup() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new AuthorServiceImpl(authorDAO, bookDAO);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }


    /**
     * Unit test for {@link AuthorService#addAuthor(Author) addAuthor} method
     */
    @Test
    void shouldCreateAuthor() {
        Author author = new Author(
                "John",
                "Shelby",
                Date.valueOf("1997-03-10"),
                "He is very famous"
        );
        underTest.addAuthor(author);

        ArgumentCaptor<Author> authorArgumentCaptor = ArgumentCaptor.forClass(Author.class);

        verify(authorDAO)
                .create(authorArgumentCaptor.capture());

        Author capturedAuthor = authorArgumentCaptor.getValue();
        assertThat(capturedAuthor).isEqualTo(author);
    }

    /**
     * Unit test for {@link AuthorService#update(int, Author)} updateAuthor} method
     */
    @Test
    void shouldUpdateAuthorData() {
        Author oldAuthor = new Author(
                1,
                "John",
                "Shelby",
                Date.valueOf("1997-03-10"),
                "He is very famous"
        );

        Author newAuthor = new Author(
                1,
                "Denis",
                "Shelby",
                Date.valueOf("1997-03-10"),
                "He is very famous"
        );

        given(authorDAO.getById(oldAuthor.getId()))
                .willReturn(oldAuthor);

        underTest.update(oldAuthor.getId(), newAuthor);

        ArgumentCaptor<Author> authorArgumentCaptor = ArgumentCaptor.forClass(Author.class);

        verify(authorDAO)
                .update(authorArgumentCaptor.capture());

        Author capturedAuthor = authorArgumentCaptor.getValue();
        assertThat(capturedAuthor.getFirstName()).isEqualTo(newAuthor.getFirstName());
    }

    /**
     * Unit test for {@link AuthorService#deleteAuthor(int)} deleteAuthor} method
     */
    @Test
    void shouldDeleteAuthor() {
        int authorIdToDelete = 21;

        underTest.deleteAuthor(authorIdToDelete);

        ArgumentCaptor<Integer> authorArgumentCaptor = ArgumentCaptor.forClass(Integer.class);

        verify(authorDAO)
                .removeById(authorArgumentCaptor.capture());

        int capturedId = authorArgumentCaptor.getValue();

        assertThat(authorIdToDelete).isEqualTo(capturedId);
    }

    /**
     * Unit test for {@link AuthorService#getAllPaginatedAndSortedAuthors(int, int, String, String) getAllAuthors} method
     */
    @Test
    @Disabled
    void shouldGetAllAuthors() {
    }

    /**
     * Unit test for {@link AuthorService#addBookToAuthor(int, int)} addBookToAuthor} method
     */
    @Test
    void shouldAssignBookToAuthor() {
        Book book = new Book(
                1,
                "The Lord of Rings",
                "Cool book",
                "25B",
                AVAILABLE
        );
        Author author = new Author(
                1,
                "John",
                "Tolkien",
                Date.valueOf("1892-01-03"),
                "Cool writer",
                new ArrayList<Book>()
        );

        given(bookDAO.getById(book.getId()))
                .willReturn(book);

        given(authorDAO.getById(author.getId()))
                .willReturn(author);

        underTest.addBookToAuthor(book.getId(), author.getId());

        ArgumentCaptor<Author> authorArgumentCaptor = ArgumentCaptor.forClass(Author.class);

        verify(authorDAO)
                .update(authorArgumentCaptor.capture());

        Author capturedAuthor = authorArgumentCaptor.getValue();

        assertThat(capturedAuthor.getBooks().get(0)).isEqualTo(book);
    }
}