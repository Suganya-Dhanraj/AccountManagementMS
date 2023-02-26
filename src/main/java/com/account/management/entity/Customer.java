package com.account.management.entity;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;

import lombok.Data;

@Entity
@Table(name = "CUSTOMER")
@Data
public class Customer {

	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long customerId;
	@Column(name = "NAME")
	private String name;
	@Column(name = "PHONE_NUMBER")
	private String phoneNumber;
	@Column(name = "ADDRESS1")
	private String address1;
	@Column(name = "ADDRESS2")
	private String address2;
	@Column(name = "EMAIL_ID")
	private String emailId;
	@Column(name = "PAN_CARD")
	private String panCard;
	@Column(name = "AADHAR")
	private String aadhar;
	@Column(name = "DOB")
	private String dob;
	@Column(name = "GENDER")
	private String gender;
	
	@OneToMany(mappedBy="customer")
    private Set<Account> accounts;
	
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
