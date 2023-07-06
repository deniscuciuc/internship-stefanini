package com.stefanini.pizzariabackend.repo;

import com.stefanini.pizzariabackend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}