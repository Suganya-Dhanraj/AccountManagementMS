package com.account.management.dto;

import lombok.Data;

@Data

public class AccountManagementDto {
	String accountId;
	String accountType;
	String customerId;
	Long accountBalance;
	String status;
	int interest;

}
