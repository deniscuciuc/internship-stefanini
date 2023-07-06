package com.stefanini.librarybackend.service.helper;

public class ValueChecker {

    public static void verifyPagingAndSortingValues(int pageNumber, int pageSize, String sortBy, String sortOrder) {
        if (pageNumber < 1) pageNumber = 1;
        if (pageSize < 1 ) pageNumber = 1;
        if (sortBy.equals("")) sortBy = "id";
        if (sortOrder.equals("") || (!sortOrder.equals("asc") && !sortOrder.equals("desc"))) {
            sortOrder = "asc";
        }
    }
}
