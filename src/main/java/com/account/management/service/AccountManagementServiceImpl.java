package com.account.management.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.account.management.dto.AccountManagementDto;
import com.account.management.entity.Account;
import com.account.management.entity.Customer;
import com.account.management.exception.CustomerNotFoundException;
import com.account.management.repository.AccountManagementRepository;
import com.account.management.repository.CustomerRepository;

/**
 * 
 * @author Suganya
 * @version 0.1
 * @since 0.1
 */
@Service
public class AccountManagementServiceImpl implements AccountManagementService {

	@Autowired
	private AccountManagementRepository accountManagementRepository;
	
	@Autowired
	private CustomerRepository customerRepository;

	private static final Logger logger = LogManager.getLogger(AccountManagementServiceImpl.class);

	/**
	 * Method to add Customer details in DB
	 * 
	 * @param customerDto
	 * @return String(success/failed)
	 * @throws CustomerNotFoundException 
	 */
	@Override
	public AccountManagementDto addAccount(AccountManagementDto accountManagementDto) throws CustomerNotFoundException {
		// Convert accountManagementDto to Account Entity
		Account account = convertAccountManagementDtoToAccount(accountManagementDto);
		
		if(Objects.isNull(customerRepository.findByCustomerId(
				Long.valueOf(accountManagementDto.getCustomerId())))) {
			throw new CustomerNotFoundException("Customer not found");
			
		}

		try {
			// Save Account details in DB
			Account accountNew = accountManagementRepository.save(account);
			logger.info("Account added successfully. Account Id is " + accountNew.getAccountId());
			return convertAccountToAccountManagementDto(accountNew);
		} catch (Exception ex) {
			// If exception occurred print exception message
			logger.error("Exception occurred in addAccount. Exception message is " + ex.getMessage());
			return null;
		}
	}

	@Override
	public AccountManagementDto updateAccount(AccountManagementDto accountManagementDto)
			throws ResourceNotFoundException,CustomerNotFoundException {
		Account accountOld = accountManagementRepository
				.findByAccountId(Long.valueOf(accountManagementDto.getAccountId()));
		if (Objects.isNull(accountOld)) {
			throw new ResourceNotFoundException("Account not found");
		}
		
		if(Objects.isNull(customerRepository.findByCustomerId(
				Long.valueOf(accountManagementDto.getCustomerId())))) {
			throw new CustomerNotFoundException("Customer not found");
			
		}

		Account accountNew = convertAccountManagementDtoToAccount(accountManagementDto);		
		accountNew.setAccountId(accountOld.getAccountId());
		accountNew.setRecordCreationDate(accountOld.getRecordCreationDate());
		accountNew.setRecordUpdatedDate(LocalDateTime.now());

		accountNew = accountManagementRepository.save(accountNew);
		return convertAccountToAccountManagementDto(accountNew);
	}

	@Override
	public void deleteAccount(Long id) throws ResourceNotFoundException, CustomerNotFoundException {
		Account accountOld = accountManagementRepository.findByAccountId(id);
		if (Objects.isNull(accountOld)) {
			throw new ResourceNotFoundException("Account not found");
		}
		Customer customer = customerRepository.findByCustomerId(
				Long.valueOf(accountOld.getCustomer().getCustomerId()));
		if(Objects.isNull(customer)) {
			throw new CustomerNotFoundException("Customer not found");
			
		}		
		
		accountManagementRepository.delete(accountOld);
	}

	/***
	 * Method to convert Account to AccountDto
	 * 
	 * @param accountNew
	 * @return accountManagementDto
	 */
	private AccountManagementDto convertAccountToAccountManagementDto(Account accountNew) {
		final AccountManagementDto accountManagementDto = new AccountManagementDto();
		accountManagementDto.setAccountId(accountNew.getAccountId().toString());
		accountManagementDto.setAccountType(accountNew.getAccountType());
		accountManagementDto.setAccountBalance(accountNew.getAccountBalance());
		accountManagementDto.setInterest(accountNew.getInterest());
		accountManagementDto.setStatus(accountNew.getStatus());
		accountManagementDto.setCustomerId(accountNew.getCustomer().getCustomerId().toString());
		return accountManagementDto;
	}

	/**
	 * Method to convert accountManagementDto to Account
	 * 
	 * @param accountManagmentDto
	 * @return account
	 */
	private Account convertAccountManagementDtoToAccount(AccountManagementDto accountManagementDto) {
		final Account account = new Account();

		account.setAccountType(accountManagementDto.getAccountType());
		account.setAccountBalance(accountManagementDto.getAccountBalance());
		account.setInterest(accountManagementDto.getInterest());
		account.setStatus(accountManagementDto.getStatus());
		Customer customer = new Customer();
		customer.setCustomerId(Long.valueOf(accountManagementDto.getCustomerId()));
		account.setCustomer(customer);
		return account;
	}

}
