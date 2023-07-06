package com.stefanini.librarybackend.service.impl;

import com.stefanini.librarybackend.dao.impl.AuthorDAOImpl;
import com.stefanini.librarybackend.dao.impl.BookDAOImpl;
import com.stefanini.librarybackend.dao.impl.CategoryDAOImpl;
import com.stefanini.librarybackend.dao.impl.UserDAOImpl;
import com.stefanini.librarybackend.domain.*;
import com.stefanini.librarybackend.service.BookService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

import static com.stefanini.librarybackend.domain.enums.BookStatus.AVAILABLE;
import static com.stefanini.librarybackend.domain.enums.BookStatus.TAKEN;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class BookServiceImplTest {

    @Mock
    private AuthorDAOImpl authorDAO;
    @Mock
    private BookDAOImpl bookDAO;

    @Mock
    private UserDAOImpl userDAO;

    @Mock
    private CategoryDAOImpl categoryDAO;
    private AutoCloseable autoCloseable;
    private BookService underTest;

    @BeforeEach
    void setup() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new BookServiceImpl(bookDAO, authorDAO, userDAO, categoryDAO);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    /**
     * Unit test for method {@link BookService#addBook(Book)} addBook} method
     */
    @Test
    void shouldAddBook() {
        Book book = new Book(
                "The Lord of Rings",
                "Cool book",
                "25B",
                AVAILABLE
        );

        underTest.addBook(book);

        ArgumentCaptor<Book> bookArgumentCaptor = ArgumentCaptor.forClass(Book.class);

        verify(bookDAO)
                .create(bookArgumentCaptor.capture());

        Book capturedBook = bookArgumentCaptor.getValue();

        assertThat(capturedBook).isEqualTo(book);
    }

    /**
     * Unit test for method {@link BookService#getAllBooks(int, int, String, String) showAllBooks} method
     */
    @Test
    @Disabled
    void shouldGetAllBooks() {
    }

    /**
     * Unit test for method {@link BookService#update(int, Book) update} method
     */
    @Test
    void shouldUpdateBook() {
        Book oldBook = new Book(
                "The Lord of Rings",
                "Cool book",
                "25B",
                AVAILABLE
        );

        Book newBook = new Book(
                "The Lord of Rings",
                "Cool book",
                "25B",
                TAKEN
        );

        given(bookDAO.getById(oldBook.getId()))
                .willReturn(oldBook);

        underTest.update(oldBook.getId(), newBook);

        ArgumentCaptor<Book> bookArgumentCaptor = ArgumentCaptor.forClass(Book.class);

        verify(bookDAO)
                .update(bookArgumentCaptor.capture());

        Book capturedBook = bookArgumentCaptor.getValue();
        assertThat(capturedBook.getStatus()).isEqualTo(newBook.getStatus());
    }

    /**
     * Unit test for method {@link BookService#findById(int)} (Book)} addBook} method
     */
    @Test
    void shouldFindExistsBookById() {
        int id = 2;

        underTest.findById(id);

        ArgumentCaptor<Integer> integerArgumentCaptor = ArgumentCaptor.forClass(Integer.class);

        verify(bookDAO)
                .getById(integerArgumentCaptor.capture());

        int capturedId = integerArgumentCaptor.getValue();
        assertThat(capturedId).isEqualTo(id);
    }

    /**
     * Unit test for method {@link BookService#deleteBook(int) deleteBook} method
     */
    @Test
    void shouldDeleteBook() {
        int id = 3;

        underTest.deleteBook(id);

        ArgumentCaptor<Integer> integerArgumentCaptor = ArgumentCaptor.forClass(Integer.class);

        verify(bookDAO)
                .removeById(integerArgumentCaptor.capture());

        int capturedId = integerArgumentCaptor.getValue();
        assertThat(capturedId).isEqualTo(id);
    }

    /**
     * Unit test for method {@link BookService#bookTheBook(int, int) bookTheBook} addBook} method
     */
    @Test
    void shouldBookTheBookAndUpdateBook() {
        User user = new User(
                1,
                "email",
                "password"
        );
        Book book = new Book(
                1,
                "Lord of Rings",
                "Cool book",
                "25b",
                AVAILABLE
        );

        user.setHistory(new ArrayList<History>());
        user.setBook(new ArrayList<>());
        book.setHistory(new ArrayList<History>());

        given(userDAO.getById(user.getId()))
                .willReturn(user);

        given(bookDAO.getById(book.getId()))
                .willReturn(book);

        underTest.bookTheBook(book.getId(), user.getId());

        ArgumentCaptor<Book> bookArgumentCaptor = ArgumentCaptor.forClass(Book.class);

        verify(bookDAO)
                .update(bookArgumentCaptor.capture());

        Book capturedBook = bookArgumentCaptor.getValue();
        assertThat(capturedBook.getUser()).isEqualTo(user);
    }

    /**
     * Unit test for method {@link BookService#giveTheBook(int, int)} giveTheBook} method
     */
    @Test
    void shouldGiveTheBookAndUpdateUser() {
        User user = new User(
                1,
                "email",
                "password"
        );
        Book book = new Book(
                1,
                "Lord of Rings",
                "Cool book",
                "25b",
                AVAILABLE
        );

        user.setHistory(new ArrayList<History>());
        user.setBook(new ArrayList<>());
        book.setHistory(new ArrayList<History>());

        given(userDAO.getById(user.getId()))
                .willReturn(user);

        given(bookDAO.getById(book.getId()))
                .willReturn(book);

        underTest.giveTheBook(book.getId(), user.getId());

        ArgumentCaptor<Book> bookArgumentCaptor = ArgumentCaptor.forClass(Book.class);

        verify(bookDAO)
                .update(bookArgumentCaptor.capture());

        Book capturedBook = bookArgumentCaptor.getValue();
        assertThat(capturedBook.getUser()).isEqualTo(user);
    }

    /**
     * Unit test for method {@link BookService#addBook(Book)} addBook} method
     */
    @Test
    void shouldReturnBookAndSetUserToNullInBook() {
        User user = new User(
                1,
                "email",
                "password"
        );
        Book book = new Book(
                1,
                "Lord of Rings",
                "Cool book",
                "25b",
                TAKEN
        );

        user.setBook(new ArrayList<>());
        user.getBook().add(book);
        user.setHistory(new ArrayList<History>());

        book.setUser(user);
        book.setHistory(new ArrayList<History>());

        given(bookDAO.getById(book.getId()))
                .willReturn(book);

        underTest.returnTheBook(book.getId());

        ArgumentCaptor<Book> bookArgumentCaptor = ArgumentCaptor.forClass(Book.class);

        verify(bookDAO)
                .update(bookArgumentCaptor.capture());

        Book capturedBook = bookArgumentCaptor.getValue();
        assertThat(capturedBook.getUser()).isEqualTo(null);
    }

    /**
     * Unit test for method {@link BookService#findBooksByAnyCriteria(String) findBooksByAnyCriteria} method
     */
    @Test
    void shouldFindExistsBooksByAnyCriteria() {
        String criteria = "ring";

        underTest.findBooksByAnyCriteria(criteria);

        verify(bookDAO).getBooksByAnyCriteria(criteria);
    }

    /**
     * Unit test for method {@link BookService#getBookByCategory(int) getBookByCategory} method
     */
    @Test
    void shouldReturnBookByCategoryId() {
        ArrayList<Book> books = new ArrayList<>();
        books.add(new Book(
                1,
                "Lord of Rings",
                "Cool book",
                "25b",
                TAKEN
        ));

        Category category = new Category(
                1,
                "Romance",
                books
        );

        given(categoryDAO.getById(category.getId()))
                .willReturn(category);

        underTest.getBookByCategory(category.getId());

        assertThat(category.getBooks().get(0)).isEqualTo(books.get(0));
    }

    /**
     * Unit test for method {@link BookService#findBooksByAuthor(int)} findBooksByAuthor} method
     */
    @Test
    void shouldFindExistBooksByAuthorId() {
        ArrayList<Book> books = new ArrayList<>();
        books.add(new Book(
                1,
                "Lord of Rings",
                "Cool book",
                "25b",
                TAKEN
        ));

        Author author = new Author(
                23,
                "John",
                "Tolkien",
                Date.valueOf(LocalDate.now()),
                "Cool author",
                books
        );

        given(authorDAO.getById(author.getId()))
                .willReturn(author);

        underTest.findBooksByAuthor(author.getId());

        assertThat(author.getBooks().get(0)).isEqualTo(books.get(0));
    }

    /**
     * Unit test for method {@link BookService#addBookWithExistingCategoryAndAuthor(Book, int, int) addBookWithExistingCategoryAndAuthor} method
     */
    @Test
    void shouldCreateBookWithExistingCategoryAndAuthor() {
        Book book = new Book(
                "Lord of Rings",
                "Cool book",
                "25b"
        );

        Author author = new Author(
                23,
                "John",
                "Tolkien",
                Date.valueOf(LocalDate.now()),
                "Cool author"
        );

        Category category = new Category(
                1,
                "Fantasy"
        );

        book.setCategories(new ArrayList<>());
        book.setAuthors(new ArrayList<>());

        given(authorDAO.getById(author.getId()))
                .willReturn(author);
        given(categoryDAO.getById(category.getId()))
                .willReturn(category);

        underTest.addBookWithExistingCategoryAndAuthor(book, category.getId(), author.getId());

        ArgumentCaptor<Book> bookArgumentCaptor = ArgumentCaptor.forClass(Book.class);

        verify(bookDAO)
                .create(bookArgumentCaptor.capture());

        Book capturedBook = bookArgumentCaptor.getValue();

        assertThat(capturedBook.getCategories().get(0)).isEqualTo(category);
        assertThat(capturedBook.getAuthors().get(0)).isEqualTo(author);
    }
}