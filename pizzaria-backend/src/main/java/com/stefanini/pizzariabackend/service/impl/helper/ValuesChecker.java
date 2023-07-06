package com.stefanini.pizzariabackend.service.impl.helper;

import com.stefanini.pizzariabackend.domain.enums.Category;
import com.stefanini.pizzariabackend.domain.enums.Subcategory;
import com.stefanini.pizzariabackend.service.impl.exception.EnumNotFoundException;
import com.stefanini.pizzariabackend.service.impl.exception.InvalidIdException;
import com.stefanini.pizzariabackend.service.impl.exception.InvalidPageValueException;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class ValuesChecker {

    public static void verifyPaginatingValuesAndThrowExceptionIfInvalidValues(String category, String subcategory,
                                                                              int currentPage, int pageSize) {
        verifyCategoryIfExists(category);
        verifySubcategoryIfExists(subcategory);
        verifyPageValues(currentPage, pageSize);
    }

    public static void verifyPaginatingValuesAndThrowExceptionIfInvalidValues(int currentPage, int pageSize) {
        verifyPageValues(currentPage, pageSize);
    }

    public static void verifyIdAndIfInvalidThrowException(Long id) throws InvalidIdException {
        if (id == 0) {
            log.error("Id can't be zero");
            throw new InvalidIdException("Id can't be zero");
        } else if (id < 0) {
            log.error("Id can't be negative");
            throw new InvalidIdException("Id can't be negative");
        }
    }

    private static void verifyCategoryIfExists(String category) throws EnumNotFoundException {
        if (!Category.contains(category)) {
            log.error("Category " + category + " not found");
            throw new EnumNotFoundException("Category " + category + " not found");
        }
    }

    private static void verifySubcategoryIfExists(String subcategory) {
        Subcategory foundSubcategory = Arrays.stream(Subcategory.values())
                .filter(subcategoryEnum -> subcategory.toUpperCase().equals(subcategoryEnum.toString()))
                .findAny()
                .orElse(null);

        if (foundSubcategory == null) {
            log.error("Subcategory " + subcategory + " not found");
            throw new EnumNotFoundException("Subcategory " + subcategory + " not found");
        }
    }

    private static void verifyPageValues(int currentPage, int pageSize) throws InvalidPageValueException {
        if (currentPage < 0) {
            log.error("Current page can't be negative");
            throw new InvalidPageValueException("Current page can't be negative");
        }

        if (pageSize < 0) {
            log.error("Page size can't be negative");
            throw new InvalidPageValueException("Page size can't be negative");
        } else if (pageSize == 0) {
            log.error("Page size can't be zero");
            throw new InvalidPageValueException("Page size can't be zero");
        }
    }
}
