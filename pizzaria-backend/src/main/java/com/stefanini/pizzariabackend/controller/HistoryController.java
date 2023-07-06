package com.stefanini.pizzariabackend.controller;

import com.stefanini.pizzariabackend.domain.History;
import com.stefanini.pizzariabackend.service.HistoryService;
import com.stefanini.pizzariabackend.service.impl.HistoryServiceImpl;
import com.stefanini.pizzariabackend.service.impl.exception.InvalidIdException;
import com.stefanini.pizzariabackend.service.impl.exception.InvalidPageValueException;
import com.stefanini.pizzariabackend.service.impl.exception.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/histories")
public class HistoryController {

    private final HistoryService historyService;

    public HistoryController(HistoryServiceImpl historyServiceImpl) {
        this.historyService = historyServiceImpl;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public History createHistory(@RequestBody History history) {
        return historyService.createHistory(history);
    }

    @GetMapping("/{user-id}")
    public List<History> getAllHistories(@PathVariable("user-id") Long userId) {
        return historyService.getAllHistories();
    }

    @GetMapping("/pagination/{user-id}/{current-page}/{page-size}")
    public ResponseEntity<?> getPaginatedHistories(
            @PathVariable("user-id") Long userId,
            @PathVariable("current-page") int currentPage,
            @PathVariable("page-size") int pageSize
    ) {
        try {
            return ResponseEntity
                    .status(ACCEPTED)
                    .body(historyService.getPaginatedHistories(userId, currentPage, pageSize));
        } catch (InvalidIdException exception) {
            return ResponseEntity
                    .status(exception.getResponseStatus())
                    .body(exception.getMessage());
        } catch (NotFoundException exception) {
            return ResponseEntity
                    .status(exception.getResponseStatus())
                    .body(exception.getMessage());
        } catch (InvalidPageValueException exception) {
            return ResponseEntity
                    .status(exception.getResponseStatus())
                    .body(exception.getMessage());
        }
    }
}
