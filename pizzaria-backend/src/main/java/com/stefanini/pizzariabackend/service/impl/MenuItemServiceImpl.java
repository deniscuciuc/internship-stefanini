package com.stefanini.pizzariabackend.service.impl;

import com.stefanini.pizzariabackend.domain.MenuItem;
import com.stefanini.pizzariabackend.repo.MenuItemRepository;
import com.stefanini.pizzariabackend.service.MenuItemService;
import com.stefanini.pizzariabackend.service.impl.exception.NotFoundException;
import com.stefanini.pizzariabackend.service.impl.helper.ValuesChecker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MenuItemServiceImpl implements MenuItemService {

    private final MenuItemRepository menuItemRepository;

    public MenuItemServiceImpl(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }


    @Override
    public MenuItem createMenuItem(MenuItem menuItem) {
        return menuItemRepository.save(menuItem);
    }

    @Override
    public MenuItem updateMenuItemById(Long id, MenuItem newMenuItem) {
        ValuesChecker.verifyIdAndIfInvalidThrowException(id);
        verifyIfMenuItemExists(id);
        MenuItem oldMenuItem = menuItemRepository.findById(id).get();
        updateMenuItemFields(oldMenuItem, newMenuItem);
        return menuItemRepository.save(oldMenuItem);
    }

    @Override
    public List<MenuItem> getAllMenuItems() {
        return (List<MenuItem>) menuItemRepository.findAll();
    }

    @Override
    public Long deleteMenuItemById(Long id) {
        ValuesChecker.verifyIdAndIfInvalidThrowException(id);
        verifyIfMenuItemExists(id);
        menuItemRepository.deleteById(id);
        return id;
    }

    @Override
    public List<MenuItem> getPaginatedMenuItems(String category, String subcategory, int currentPage, int pageSize) {
        ValuesChecker.verifyPaginatingValuesAndThrowExceptionIfInvalidValues(category, subcategory, currentPage, pageSize);

        Pageable paging = PageRequest.of(currentPage, pageSize);
        Page<MenuItem> pagedResult = menuItemRepository.findAll(paging);

        return pagedResult.getContent();
    }

    private void verifyIfMenuItemExists(Long id) throws NotFoundException {
        boolean doesMenuItemExist = menuItemRepository.existsById(id);
        if (!doesMenuItemExist) {
            log.error("Menu item with such id not found");
            throw new NotFoundException("Menu item with such id not found");
        }
    }

    private void updateMenuItemFields(MenuItem oldMenuItem, MenuItem newMenuItem) {
        oldMenuItem.setName(newMenuItem.getName());
        oldMenuItem.setImage(newMenuItem.getImage());
        oldMenuItem.setIngredients(newMenuItem.getIngredients());
        oldMenuItem.setCategory(newMenuItem.getCategory());
        oldMenuItem.setSubcategory(newMenuItem.getSubcategory());
        oldMenuItem.setSizesAndPrices(newMenuItem.getSizesAndPrices());
        oldMenuItem.setThickness(newMenuItem.getThickness());
        log.info("Menu item was updated");
    }
}
