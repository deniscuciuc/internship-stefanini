package org.dcuciuc.dto.filtres;

import org.dcuciuc.user.enums.UserRole;


public class UserSearchDTO {
    private int currentPage;
    private int pageSize;
    private UserRole userRole;
    private String searchValue;
    private Long searchByIdValue;

    public UserSearchDTO(int currentPage, int pageSize, UserRole userRole, String searchValue) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.userRole = userRole;
        this.searchValue = searchValue;
    }

    public UserSearchDTO(int currentPage, int pageSize, UserRole userRole, Long searchByIdValue) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.userRole = userRole;
        this.searchByIdValue = searchByIdValue;
    }

    public UserSearchDTO(int currentPage, int pageSize, UserRole userRole) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.userRole = userRole;
    }

    public UserSearchDTO(String searchValue) {
        this.searchValue = searchValue;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    public Long getSearchByIdValue() {
        return searchByIdValue;
    }

    public void setSearchByIdValue(Long searchByIdValue) {
        this.searchByIdValue = searchByIdValue;
    }

    public int getOffset() {
        return (currentPage - 1) * pageSize;
    }
}
