package com.stefanini.pizzariabackend.service.impl.helper;

import com.stefanini.pizzariabackend.domain.enums.Category;
import com.stefanini.pizzariabackend.domain.enums.Subcategory;
import com.stefanini.pizzariabackend.service.impl.exception.EnumNotFoundException;
import com.stefanini.pizzariabackend.service.impl.exception.InvalidIdException;
import com.stefanini.pizzariabackend.service.impl.exception.InvalidPageValueException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ValuesCheckerTest {

    // TODO: finish this tests, for each case for page value

    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    /**
     * Unit test for {@link ValuesChecker#verifyPaginatingValuesAndThrowExceptionIfInvalidValues(String, String, int, int)
     * verifyPaginatingValuesAndThrowExceptionIfInvalidValues} method
     */
    @Test
    void shouldThrowEnumNotFoundExceptionIfSuchCategoryNotExistsOrIsInvalid() {
        String fakeCategory = "Mockito145";

        assertThrows(EnumNotFoundException.class,
                () -> ValuesChecker.verifyPaginatingValuesAndThrowExceptionIfInvalidValues(
                        fakeCategory, "Drinks", 0, 5
                ));
    }

    /**
     * Unit test for {@link ValuesChecker#verifyPaginatingValuesAndThrowExceptionIfInvalidValues(String, String, int, int)
     * verifyPaginatingValuesAndThrowExceptionIfInvalidValues} method
     */
    @Test
    void shouldThrowEnumNotFoundExceptionIfSuchSubcategoryNotExistsOrIsInvalid() {
        String realCategory = Category.PIZZA.toString();
        String fakeSubcategory = "Mockito145133";

        assertThrows(EnumNotFoundException.class,
                () -> ValuesChecker.verifyPaginatingValuesAndThrowExceptionIfInvalidValues(
                        realCategory, fakeSubcategory, 0, 5
                ));
    }

    /**
     * Unit test for {@link ValuesChecker#verifyPaginatingValuesAndThrowExceptionIfInvalidValues(String, String, int, int)
     * verifyPaginatingValuesAndThrowExceptionIfInvalidValues} method
     */
    @Test
    void shouldThrowInvalidPageValueExceptionIfPageSizeIsNegativeInFirstMethod() {
        String realCategory = Category.PIZZA.toString();
        String realSubcategory = Subcategory.TEA.toString();
        int negativePageSize = -5;
        int goodCurrentPage = 1;

        assertThrows(InvalidPageValueException.class,
                () -> ValuesChecker.verifyPaginatingValuesAndThrowExceptionIfInvalidValues(
                        realCategory, realSubcategory, goodCurrentPage, negativePageSize
                ));
    }

    /**
     * Unit test for {@link ValuesChecker#verifyPaginatingValuesAndThrowExceptionIfInvalidValues(String, String, int, int)
     * verifyPaginatingValuesAndThrowExceptionIfInvalidValues} method
     */
    @Test
    void shouldThrowInvalidPageValueExceptionIfPageSizeIsZeroInFirstMethod() {
        String realCategory = Category.PIZZA.toString();
        String realSubcategory = Subcategory.TEA.toString();
        int pageSizeZero = 0;
        int goodCurrentPage = 1;

        assertThrows(InvalidPageValueException.class,
                () -> ValuesChecker.verifyPaginatingValuesAndThrowExceptionIfInvalidValues(
                        realCategory, realSubcategory, goodCurrentPage, pageSizeZero
                ));
    }

    /**
     * Unit test for {@link ValuesChecker#verifyPaginatingValuesAndThrowExceptionIfInvalidValues(String, String, int, int)
     * verifyPaginatingValuesAndThrowExceptionIfInvalidValues} method
     */
    @Test
    void shouldThrowInvalidPageValueExceptionIfCurrentPageIsNegativeInFirstMethod() {
        String realCategory = Category.PIZZA.toString();
        String realSubcategory = Subcategory.TEA.toString();
        int negativeCurrentPage = -5;
        int goodPageSize = 1;

        assertThrows(InvalidPageValueException.class,
                () -> ValuesChecker.verifyPaginatingValuesAndThrowExceptionIfInvalidValues(
                        realCategory, realSubcategory, negativeCurrentPage, goodPageSize
                ));
    }

    /**
     * Unit test for {@link ValuesChecker#verifyPaginatingValuesAndThrowExceptionIfInvalidValues(int, int)
     * verifyPaginatingValuesAndThrowExceptionIfInvalidValues} method
     */
    @Test
    void shouldThrowInvalidPageValueExceptionIfPageSizeIsNegative() {
        int negativePageSize = -5;
        int goodCurrentPage = 1;

        assertThrows(InvalidPageValueException.class,
                () -> ValuesChecker.verifyPaginatingValuesAndThrowExceptionIfInvalidValues(
                        goodCurrentPage, negativePageSize
                ));
    }

    /**
     * Unit test for {@link ValuesChecker#verifyPaginatingValuesAndThrowExceptionIfInvalidValues(int, int)
     * verifyPaginatingValuesAndThrowExceptionIfInvalidValues} method
     */
    @Test
    void shouldThrowInvalidPageValueExceptionIfPageSizeIsZero() {
        int pageSizeZero = 0;
        int goodCurrentPage = 1;

        assertThrows(InvalidPageValueException.class,
                () -> ValuesChecker.verifyPaginatingValuesAndThrowExceptionIfInvalidValues(
                        goodCurrentPage, pageSizeZero
                ));
    }

    /**
     * Unit test for {@link ValuesChecker#verifyPaginatingValuesAndThrowExceptionIfInvalidValues(int, int)
     * verifyPaginatingValuesAndThrowExceptionIfInvalidValues} method
     */
    @Test
    void shouldThrowInvalidPageValueExceptionIfCurrentPageIsNegative() {
        int negativeCurrentPage = -5;
        int goodPageSize = 1;

        assertThrows(InvalidPageValueException.class,
                () -> ValuesChecker.verifyPaginatingValuesAndThrowExceptionIfInvalidValues(
                        negativeCurrentPage, goodPageSize
                ));
    }

    /**
     * Unit test for {@link ValuesChecker#verifyIdAndIfInvalidThrowException(Long) verifyIdAndIfInvalidThrowException} method
     */
    @Test
    void shouldThrowInvalidIdExceptionIfIdIsNegative() {
        Long negativeId = (long) -25;
        assertThrows(InvalidIdException.class, () -> ValuesChecker.verifyIdAndIfInvalidThrowException(negativeId));
    }

    /**
     * Unit test for {@link ValuesChecker#verifyIdAndIfInvalidThrowException(Long) verifyIdAndIfInvalidThrowException} method
     */
    @Test
    void shouldThrowInvalidIdExceptionIfIdIsZero() {
        Long zeroId = (long) 0;
        assertThrows(InvalidIdException.class, () -> ValuesChecker.verifyIdAndIfInvalidThrowException(zeroId));
    }
}