package com.account.management.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

import lombok.Data;

@Entity
@Table(name = "ACCOUNT")
@Data
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ACCOUNT_ID")
	private Long accountId;
	@Column(name = "ACCOUNT_TYPE")
	private String accountType;
	@Column(name = "ACCOUNT_BALANCE")
	private Long accountBalance;	
	@Column(name = "STATUS")
	private String status;
	@Column(name = "INTEREST")
	private int interest;	
	@ManyToOne
    @JoinColumn(name="CUSTOMER_ID", nullable=false)
    private Customer customer;

	@CreatedDate
	@Column(name = "RECORD_CREATION_DATE", nullable = false)
	private LocalDateTime recordCreationDate;
	@Column(name = "RECORD_UPDATED_DATE")
	private LocalDateTime recordUpdatedDate;

	@PrePersist
	public void setRecordCreationDate() {
		this.recordCreationDate = LocalDateTime.now();
	}

}