package com.stefanini.pizzariabackend.service.impl;

import com.stefanini.pizzariabackend.domain.History;
import com.stefanini.pizzariabackend.domain.User;
import com.stefanini.pizzariabackend.repo.HistoryRepository;
import com.stefanini.pizzariabackend.repo.UserRepository;
import com.stefanini.pizzariabackend.service.HistoryService;
import com.stefanini.pizzariabackend.service.impl.exception.InvalidIdException;
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
public class HistoryServiceImpl implements HistoryService {

    private final HistoryRepository historyRepository;
    private final UserRepository userRepository;

    public HistoryServiceImpl(HistoryRepository historyRepository, UserRepository userRepository) {
        this.historyRepository = historyRepository;
        this.userRepository = userRepository;
    }

    @Override
    public History createHistory(History history) {
        return historyRepository.save(history);
    }

    @Override
    public List<History> getAllHistories() {
        return historyRepository.findAll();
    }

    @Override
    public List<History> getPaginatedHistories(Long userId, int currentPage, int pageSize) {
        ValuesChecker.verifyIdAndIfInvalidThrowException(userId);
        User user = verifyIfUserExistsByIdAndIfTrueReturnHim(userId);
        ValuesChecker.verifyPaginatingValuesAndThrowExceptionIfInvalidValues(currentPage, pageSize);

        Pageable paging = PageRequest.of(currentPage, pageSize);

        return historyRepository.findHistoriesByUser(user, paging);
    }

    private User verifyIfUserExistsByIdAndIfTrueReturnHim(Long id) throws NotFoundException {
        boolean doesUserExist = userRepository.existsById(id);
        if (!doesUserExist) {
            log.error("User with id {} not found", id);
            throw new NotFoundException("User with id " + id + " not found");
        } else {
            return userRepository.findById(id).get();
        }
    }
}
