package com.account.management.service;

import java.util.List;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import com.account.management.dto.AccountManagementDto;
import com.account.management.exception.CustomerNotFoundException;

public interface AccountManagementService {

	/**
	 * 
	 * @param AccountManagementDto
	 * @return String(success/failed)
	 * @throws CustomerNotFoundException 
	 */
	public AccountManagementDto addAccount(AccountManagementDto accountManagementDto) throws CustomerNotFoundException;

	/**
	 * 
	 * @param customerDto
	 * @return CustomerDto - with updated details
	 * @throws CustomerNotFoundException 
	 */
	public AccountManagementDto updateAccount(AccountManagementDto accountManagementDto)
			throws ResourceNotFoundException, CustomerNotFoundException;

	/**
	 * 
	 * @param id
	 * @throws ResourceNotFoundException
	 * @throws CustomerNotFoundException 
	 */
	public void deleteAccount(Long id) throws ResourceNotFoundException, CustomerNotFoundException;

}
