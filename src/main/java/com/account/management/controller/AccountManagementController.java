package com.account.management.controller;

import java.util.Objects;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.account.management.dto.AccountManagementDto;
import com.account.management.exception.CustomerNotFoundException;
import com.account.management.service.AccountManagementService;

import lombok.extern.log4j.Log4j;

/**
 * 
 * @author Suganya
 * @version 0.1
 * @since 0.1
 */
@Log4j
@Controller
public class AccountManagementController {

	private static final Logger logger = LogManager.getLogger(AccountManagementController.class);

	@Autowired
	private AccountManagementService accountMS;

	/**
	 * 
	 * @param accountDto
	 * @return ResponseEntity<String>
	 */
	@PostMapping("/addAccount")
	public ResponseEntity<?> addAccount(@RequestBody AccountManagementDto accountManagementDto) {

		logger.info("addAccount - start");

		// Validate Input Date
		if (accountManagementDto.getCustomerId().isEmpty()) {
			return new ResponseEntity<String>("Input data is not valid.", HttpStatus.BAD_REQUEST);
		}
		// Invoke service method to addCustomer
		AccountManagementDto accountManagementDtoNew = null;
		try {
			accountManagementDtoNew = accountMS.addAccount(accountManagementDto);
		} catch (CustomerNotFoundException e) {
			logger.info("Customer Not Found Exception ");
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

		if (Objects.nonNull(accountManagementDtoNew)) {
			// If Account added return 200
			logger.info("Account added successfully");
			return new ResponseEntity<AccountManagementDto>(accountManagementDtoNew, HttpStatus.OK);
		} else {
			// If customer add failed return 400
			logger.info("Account added failed");
			return new ResponseEntity<String>("Account not added in the system", HttpStatus.BAD_REQUEST);
		}

	}

	/**
	 * Method to update account details
	 * 
	 * @param accountManagementDto
	 * @return ResponseEntity<String>
	 */
	@PutMapping("/updateAccount")
	public ResponseEntity<?> updateAccount(@RequestBody AccountManagementDto accountManagementDto) {
		logger.info("updateCustomer - start");
		// Check account id is available in request
		if (accountManagementDto.getCustomerId().isEmpty()) {
			return new ResponseEntity<String>("Please provide Account id ", HttpStatus.BAD_REQUEST);
		}

		AccountManagementDto accountManagementDtoUpdated;
		try {
			accountManagementDtoUpdated = accountMS.updateAccount(accountManagementDto);
			return new ResponseEntity<AccountManagementDto>(accountManagementDtoUpdated, HttpStatus.OK);
		} catch (ResourceNotFoundException | CustomerNotFoundException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
	}

	@DeleteMapping("/deleteAccount/{id}")
	public ResponseEntity<?> deleteAccount(@PathVariable("id") Long id) {
		logger.info("deleteAccount - start");
		// Delete customer Details
		try {
			accountMS.deleteAccount(id);
			return new ResponseEntity<String>("Account deleted successfully", HttpStatus.OK);
		} catch (ResourceNotFoundException | CustomerNotFoundException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

}
