package com.stefanini.pizzariabackend.service.impl;

import com.stefanini.pizzariabackend.domain.User;
import com.stefanini.pizzariabackend.repo.UserRepository;
import com.stefanini.pizzariabackend.service.UserService;
import com.stefanini.pizzariabackend.service.impl.exception.NotFoundException;
import com.stefanini.pizzariabackend.service.impl.helper.ValuesChecker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Long deleteUserById(Long id) {
        ValuesChecker.verifyIdAndIfInvalidThrowException(id);
        verifyIfUserExistsById(id);
        userRepository.deleteById(id);
        return id;
    }

    private void verifyIfUserExistsById(Long id) throws NotFoundException {
        boolean doesUserExist = userRepository.existsById(id);
        if (!doesUserExist) {
            log.error("User with id {} not found", id);
            throw new NotFoundException("User with id " + id + " not found");
        }
    }
}
