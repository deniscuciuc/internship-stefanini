package com.stefanini.pizzariabackend.service;

import com.stefanini.pizzariabackend.domain.History;

import java.util.List;

/**
 * @author dcuciuc
 * @version 0.1
 * @since 0.1
 */
public interface HistoryService {

    /**
     * Saves history item.
     *
     * @param history to be saved
     * @return saved history
     */
    History createHistory(History history);

    /**
     * Finds all histories.
     *
     * @return all histories or empty list
     */
    List<History> getAllHistories();

    List<History> getPaginatedHistories(Long userId, int currentPage, int pageSize);
}
