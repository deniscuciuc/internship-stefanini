package org.dcuciuc.dto;

import com.fasterxml.jackson.annotation.JsonGetter;

import java.util.List;

public class PageResponse<T> {

    private Integer page;
    private Integer size;
    private Boolean last;
    private Boolean first;
    private Integer totalElements;
    private Integer totalPages;
    private List<T> content;

    public PageResponse(Integer page, Integer size, Boolean last, Boolean first, Integer totalElements,
                        Integer totalPages, List<T> content) {
        this.page = page;
        this.size = size;
        this.last = last;
        this.first = first;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.content = content;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Boolean getLast() {
        return last;
    }

    public void setLast(Boolean last) {
        this.last = last;
    }

    public Boolean getFirst() {
        return first;
    }

    public void setFirst(Boolean first) {
        this.first = first;
    }

    public Integer getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Integer totalElements) {
        this.totalElements = totalElements;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    @JsonGetter
    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }
}
