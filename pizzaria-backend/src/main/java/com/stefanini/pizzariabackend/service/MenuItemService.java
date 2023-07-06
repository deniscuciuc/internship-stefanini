package com.stefanini.pizzariabackend.service;

import com.stefanini.pizzariabackend.domain.MenuItem;

import java.util.List;

/**
 * @author dcuciuc
 * @version 0.1
 * @since 0.1
 */
public interface MenuItemService {

    /**
     * Saves menu item.
     *
     * @param menuItem to be saved
     * @return saved menu item
     */
    MenuItem createMenuItem(MenuItem menuItem);

    /**
     * Updates menu item.
     *
     * @param id of old menu item
     * @param newMenuItem data to be set to old menu item and saved
     * @return updated menu item
     */
    MenuItem updateMenuItemById(Long id, MenuItem newMenuItem);

    /**
     * Finds all menu items.
     *
     * @return all menu items or empty list
     */
    List<MenuItem> getAllMenuItems();

    /**
     * Deletes menu item by its id.
     *
     * @param id of menu item to be deleted
     * @return id of deleted menu item
     */
    Long deleteMenuItemById(Long id);

    List<MenuItem> getPaginatedMenuItems(String category, String subcategory, int currentPage, int pageSize);
}
