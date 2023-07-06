package com.stefanini.pizzariabackend.repo;

import com.stefanini.pizzariabackend.domain.History;
import com.stefanini.pizzariabackend.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HistoryRepository extends JpaRepository<History, Long> {

    List<History> findHistoriesByUser(User user, Pageable paging);
}
