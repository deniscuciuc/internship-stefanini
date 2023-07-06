package com.stefanini.librarybackend.service.impl;

import com.stefanini.librarybackend.dao.AuthorDAO;
import com.stefanini.librarybackend.dao.BookDAO;
import com.stefanini.librarybackend.dao.CategoryDAO;
import com.stefanini.librarybackend.dao.UserDAO;
import com.stefanini.librarybackend.dao.impl.AuthorDAOImpl;
import com.stefanini.librarybackend.dao.impl.BookDAOImpl;
import com.stefanini.librarybackend.domain.*;
import com.stefanini.librarybackend.service.BookService;
import com.stefanini.librarybackend.service.helper.ValueChecker;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.stefanini.librarybackend.domain.enums.BookStatus.*;

@Service
public class BookServiceImpl implements BookService {

    private final BookDAO<Book> bookDAOImpl;
    private final AuthorDAO<Author> authorDAO;
    private final UserDAO<User> userDAOImpl;
    private final CategoryDAO<Category> categoryDAOImpl;


    public BookServiceImpl(BookDAOImpl bookDAOImpl, AuthorDAOImpl authorDAOImpl, UserDAO<User> userDAOImpl, CategoryDAO<Category> categoryDAOImpl) {
        this.bookDAOImpl = bookDAOImpl;
        this.authorDAO = authorDAOImpl;
        this.userDAOImpl = userDAOImpl;
        this.categoryDAOImpl = categoryDAOImpl;
    }


    @Override
    public Book addBook(Book book) {
        book.setStatus(AVAILABLE);
        return bookDAOImpl.create(book);
    }

    @Override
    public List<Book> getAllBooks(int pageNumber, int pageSize, String sortBy, String sortOrder) {
        ValueChecker.verifyPagingAndSortingValues(pageNumber, pageSize, sortBy, sortOrder);
        return bookDAOImpl.getAllSortedAndPaginated(pageNumber, pageSize, sortBy, sortOrder);
    }

    @Override
    public Book update(int id, Book book) {
        Book updatedBook = bookDAOImpl.getById(id);
        updatedBook.setTitle(book.getTitle());
        updatedBook.setDescription(book.getDescription());
        updatedBook.setShelfNumber(book.getShelfNumber());
        updatedBook.setStatus(book.getStatus());
        return bookDAOImpl.update(updatedBook);
    }


    @Override
    public Book findById(int id) {
        return bookDAOImpl.getById(id);
    }


    @Override
    public int deleteBook(int id) {
        return bookDAOImpl.removeById(id);

    }


    @Override
    public Book bookTheBook(int bookId, int userId) {
        User user = userDAOImpl.getById(userId);
        Book book = bookDAOImpl.getById(bookId);
        book.setStatus(BOOKED);
        book.setUser(user);
        String actionName = "The book " + book.getTitle() + " was reserved";

        updateHistory(actionName, book, user);
        List<Book> userBookList = user.getBook();
        userBookList.add(book);
        user.setBook(userBookList);
        userDAOImpl.update(user);
        return bookDAOImpl.update(book);
    }

    @Override
    public Book giveTheBook(int bookId, int userId) {
        User user = userDAOImpl.getById(userId);
        Book book = bookDAOImpl.getById(bookId);
        book.setStatus(TAKEN);
        book.setUser(user);

        String actionName = "The book " + book.getTitle() + " was taken";

        updateHistory(actionName, book, user);
        List<Book> userBookList = user.getBook();
        userBookList.add(book);
        user.setBook(userBookList);
        userDAOImpl.update(user);
        return bookDAOImpl.update(book);
    }

    @Override
    public Book returnTheBook(int bookId) {
        Book book = bookDAOImpl.getById(bookId);
        User user = book.getUser();

        String actionName = "The book " + book.getTitle() + " was returned";

        updateHistory(actionName, book, user);

        book.setStatus(AVAILABLE);
        book.setUser(null);

        userDAOImpl.update(user);
        return bookDAOImpl.update(book);
    }

    @Override
    public List<Book> findBooksByAnyCriteria(String criteria) {
        return bookDAOImpl.getBooksByAnyCriteria(criteria);
    }

    @Override
    public List<Book> getBookByCategory(int categoryId) {
        Category category = categoryDAOImpl.getById(categoryId);
        return category.getBooks();
    }

    @Override
    public List<Book> findBooksByAuthor(int authorId) {
        Author author = authorDAO.getById(authorId);
        return author.getBooks();
    }

    @Override
    public Book addBookWithExistingCategoryAndAuthor(Book book, int categoryId, int authorId) {
        book.setStatus(AVAILABLE);
        book.getCategories().add(categoryDAOImpl.getById(categoryId));
        book.getAuthors().add(authorDAO.getById(authorId));
        return bookDAOImpl.create(book);
    }

    @Override
    public Long getNumberOfBooks() {
        return bookDAOImpl.getNumberOf();
    }

    private void updateHistory(String actionName, Book book, User user) {
        History history = new History();
        history.setActionName(actionName);
        history.setBook(book);
        history.setUser(user);

        List<History> bookHistoryList = book.getHistory();
        bookHistoryList.add(history);
        book.setHistory(bookHistoryList);

        List<History> userHistoryList = user.getHistory();
        userHistoryList.add(history);
        user.setHistory(userHistoryList);
    }

}