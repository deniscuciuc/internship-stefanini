package com.stefanini.pizzariabackend.repo;

import com.stefanini.pizzariabackend.domain.EmailConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailConfirmationTokenRepository extends JpaRepository<EmailConfirmationToken, Long> {
}
