package com.stefanini.librarybackend.controller;

import com.stefanini.librarybackend.domain.Book;
import com.stefanini.librarybackend.domain.History;
import com.stefanini.librarybackend.domain.User;
import com.stefanini.librarybackend.domain.enums.Role;
import com.stefanini.librarybackend.email.EmailSenderService;
import com.stefanini.librarybackend.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Slf4j
@RequestMapping(value = "/api/user")
public class UserController {
    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService, EmailSenderService emailSenderService) {
        this.userService = userService;
    }


    @PostMapping(value = "/create")
    @PreAuthorize("hasAnyAuthority('LIBRARIAN', 'ADMIN')")
    public User addUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping(value = "/forgotPassword/{email}")
    public ResponseEntity<?> forgotPassword(@PathVariable String email) {
        User user = userService.findByEmail(email);
        if (user != null) {
            userService.sendLinkForChangePassword(user);
            return ResponseEntity
                    .status(HttpStatus.ACCEPTED)
                    .body(user);
        } else return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body("Invalid email");
    }


    @GetMapping("/find/{id}")
    public User findById(@PathVariable int id) {
        return userService.findById(id);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyAuthority('USER','LIBRARIAN', 'ADMIN')")
    public User updateUser(@PathVariable int id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    @PutMapping("/forgotPassword/changePassword/{id}")
    public User updatePassword(@PathVariable int id, @RequestBody User user) {
        return userService.changePassword(id, user.getPassword());
    }

    @PutMapping("/assignRole/{id}/{role}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public User assignRole(@PathVariable int id, @PathVariable Role role) {
        return userService.assignRole(id, role);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('LIBRARIAN', 'ADMIN')")
    public int deleteById(@PathVariable int id) {
        return userService.deleteById(id);
    }

    @GetMapping("/users/{pageNumber}/{pageSize}/{sortBy}/{sortOrder}")
    @PreAuthorize("hasAnyAuthority('LIBRARIAN', 'ADMIN')")
    public List<User> getAllUsers(
            @PathVariable int pageNumber,
            @PathVariable int pageSize,
            @PathVariable String sortBy,
            @PathVariable String sortOrder
    ) {
        return userService.getAllUsers(pageNumber, pageSize, sortBy, sortOrder);
    }

    @GetMapping("/numberOf")
    @PreAuthorize("hasAnyAuthority('LIBRARIAN', 'ADMIN')")
    public Long getNumberOfUsers() {
        return userService.getNumberOfUsers();
    }


    @GetMapping("/find-by-email/{email}")
    @PreAuthorize("hasAnyAuthority('LIBRARIAN', 'ADMIN')")
    public ResponseEntity<?> findByEmail(@PathVariable String email) {
        User user = userService.findByEmail(email);
        if (user != null) {
            return ResponseEntity
                    .status(HttpStatus.ACCEPTED)
                    .body(user);
        } else return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body("Invalid email");

    }

    @GetMapping("/usersBooks/{userId}")
    @PreAuthorize("hasAnyAuthority('USER')")
    public List<Book> getUserBooks(@PathVariable int userId) {
        return userService.getUserBooks(userId);
    }

    @GetMapping("/usersHistory/{userId}")
    @PreAuthorize("hasAnyAuthority('USER')")
    public List<History> getUserHistory(@PathVariable int userId) {
        return userService.getUserHistory(userId);
    }

    @PutMapping("/change-password/{id}")
    @PreAuthorize("hasAnyAuthority('USER', 'LIBRARIAN', 'ADMIN')")
    public User updateUserPassword(@PathVariable int id, @RequestBody User user) {
        return userService.changePassword(id, user.getPassword());
    }


    @GetMapping("/find_users_by_criteria/{criteria}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public List<User> findBooksByCriteria(@PathVariable String criteria) {
        return userService.findUserByAnyCriteria(criteria);
    }

}



