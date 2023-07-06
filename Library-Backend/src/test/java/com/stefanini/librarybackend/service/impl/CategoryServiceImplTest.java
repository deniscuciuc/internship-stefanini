package com.stefanini.librarybackend.service.impl;

import com.stefanini.librarybackend.dao.impl.BookDAOImpl;
import com.stefanini.librarybackend.dao.impl.CategoryDAOImpl;
import com.stefanini.librarybackend.domain.Book;
import com.stefanini.librarybackend.domain.Category;
import com.stefanini.librarybackend.service.CategoryService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static com.stefanini.librarybackend.domain.enums.BookStatus.AVAILABLE;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class CategoryServiceImplTest {

    @Mock
    private CategoryDAOImpl categoryDao;

    @Mock
    private BookDAOImpl bookDAO;

    private AutoCloseable autoCloseable;

    private CategoryService underTest;

    @BeforeEach
    void setup() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new CategoryServiceImpl(categoryDao, bookDAO);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    /**
     * Unit test for method {@link CategoryService#addCategory(Category) addCategory} method
     */
    @Test
    void shouldCreateCategory() {
        Category category = new Category(
                24,
                "Romance"
        );


        underTest.addCategory(category);

        ArgumentCaptor<Category> categoryArgumentCaptor = ArgumentCaptor.forClass(Category.class);

        verify(categoryDao)
                .create(categoryArgumentCaptor.capture());

        Category capturedCategory = categoryArgumentCaptor.getValue();

        assertThat(capturedCategory).isEqualTo(category);
    }

    /**
     * Unit test for method {@link CategoryService#deleteCategory(int) deleteCategory} method
     */
    @Test
    void shouldDeleteCategoryIfExists() {
        Category category = new Category(
                1,
                "Adventure"
        );

        underTest.deleteCategory(category.getId());

        ArgumentCaptor<Integer> integerArgumentCaptor = ArgumentCaptor.forClass(Integer.class);

        verify(categoryDao)
                .removeById(integerArgumentCaptor.capture());

        Integer capturedId = integerArgumentCaptor.getValue();

        assertThat(capturedId).isEqualTo(category.getId());
    }

    /**
     * Unit test for method {@link CategoryService#addBookToCategory(int, int) addBookToCategory} method
     */
    @Test
    void shouldAssignBookToCategoryIfBothExists() {
        Category category = new Category(
                23,
                "Adventure"
        );
        Book book = new Book(
                1,
                "The Lord of Rings",
                "Cool book",
                "25B",
                AVAILABLE
        );

        category.setBooks(new ArrayList<>());

        given(categoryDao.getById(category.getId()))
                .willReturn(category);
        given(bookDAO.getById(book.getId()))
                .willReturn(book);

        underTest.addBookToCategory(book.getId(), category.getId());

        ArgumentCaptor<Category> categoryArgumentCaptor = ArgumentCaptor.forClass(Category.class);

        verify(categoryDao)
                .update(categoryArgumentCaptor.capture());

        Category capturedCategory = categoryArgumentCaptor.getValue();

        assertThat(capturedCategory.getBooks().get(0)).isEqualTo(book);
    }

    /**
     * Unit test for method {@link CategoryService#getCategoryById(int) getCategoryById} method
     */
    @Test
    void shouldReturnCategoryByIdIfExists() {
        Category category = new Category(
                1,
                "Adventure"
        );

        underTest.getCategoryById(category.getId());

        ArgumentCaptor<Integer> integerArgumentCaptor = ArgumentCaptor.forClass(Integer.class);

        verify(categoryDao)
                .getById(integerArgumentCaptor.capture());

        Integer capturedId = integerArgumentCaptor.getValue();

        assertThat(capturedId).isEqualTo(category.getId());
    }
}