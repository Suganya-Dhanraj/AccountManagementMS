package com.account.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.account.management.entity.Account;

@Repository
public interface AccountManagementRepository extends JpaRepository<Account, Long> {

	public Account findByAccountId(Long accountId);

	public List<Account> findAll();

}
