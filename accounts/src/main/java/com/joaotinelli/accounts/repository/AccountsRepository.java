package com.joaotinelli.accounts.repository;

import com.joaotinelli.accounts.entities.Accounts;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountsRepository extends JpaRepository<Accounts, Long> {
    Optional<Accounts> findByCustomerId(Long customerId);

    @Transactional // tells Spring Data Jpa that this is a transactional (all or nothing - rollback)
    @Modifying // tells Spring Data Jpa that this method is going to modify the data
    // that's necessary because I've created this method
    void deleteByCustomerId(Long customerId);
}
