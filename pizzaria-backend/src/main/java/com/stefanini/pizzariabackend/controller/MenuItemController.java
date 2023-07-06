package com.stefanini.pizzariabackend.controller;

import com.stefanini.pizzariabackend.domain.MenuItem;
import com.stefanini.pizzariabackend.service.MenuItemService;
import com.stefanini.pizzariabackend.service.impl.MenuItemServiceImpl;
import com.stefanini.pizzariabackend.service.impl.exception.InvalidIdException;
import com.stefanini.pizzariabackend.service.impl.exception.InvalidPageValueException;
import com.stefanini.pizzariabackend.service.impl.exception.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/v1/menu-items")
public class MenuItemController {

    private final MenuItemService menuItemService;

    public MenuItemController(MenuItemServiceImpl menuItemServiceImpl) {
        this.menuItemService = menuItemServiceImpl;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public MenuItem createMenuItem(@RequestBody MenuItem menuItem) {
        return menuItemService.createMenuItem(menuItem);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMenuItem(
            @PathVariable Long id,
            @RequestBody MenuItem newMenuItem
    ) {
        try {
            return ResponseEntity
                    .status(ACCEPTED)
                    .body(menuItemService.updateMenuItemById(id, newMenuItem));
        } catch (InvalidIdException exception) {
            return ResponseEntity
                    .status(exception.getResponseStatus())
                    .body(exception.getMessage());
        } catch (NotFoundException exception) {
            return ResponseEntity
                    .status(exception.getResponseStatus())
                    .body(exception.getMessage());
        }
    }

    @GetMapping
    public List<MenuItem> getAllMenuItems() {
        return menuItemService.getAllMenuItems();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMenuItem(@PathVariable Long id) {
        try {
            return ResponseEntity
                    .status(ACCEPTED)
                    .body(menuItemService.deleteMenuItemById(id));
        } catch (InvalidIdException exception) {
            return ResponseEntity
                    .status(exception.getResponseStatus())
                    .body(exception.getMessage());
        } catch (NotFoundException exception) {
            return ResponseEntity
                    .status(exception.getResponseStatus())
                    .body(exception.getMessage());
        }
    }

    @GetMapping("/pagination/{category}/{subcategory}/{current-page}/{page-size}")
    public ResponseEntity<?> getPaginatedMenuItems(
            @PathVariable String category,
            @PathVariable String subcategory,
            @PathVariable("current-page") int currentPage,
            @PathVariable("page-size") int pageSize
    ) {
        try {
            return ResponseEntity
                    .status(ACCEPTED)
                    .body(menuItemService.getPaginatedMenuItems(category, subcategory, currentPage, pageSize));
        } catch (InvalidPageValueException exception) {
            return ResponseEntity
                    .status(exception.getResponseStatus())
                    .body(exception.getMessage());
        } catch (NotFoundException exception) {
            return ResponseEntity
                    .status(exception.getResponseStatus())
                    .body(exception.getMessage());
        }
    }
}
