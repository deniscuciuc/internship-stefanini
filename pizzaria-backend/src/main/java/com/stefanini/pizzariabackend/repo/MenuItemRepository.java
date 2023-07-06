package com.stefanini.pizzariabackend.repo;

import com.stefanini.pizzariabackend.domain.MenuItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface MenuItemRepository extends PagingAndSortingRepository<MenuItem, Long> {
}