package com.exercise.bank;

import com.exercise.bank.data.model.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

@SpringBootApplication
public class BankApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankApplication.class, args);
	}

	@Bean(name = "member")
	@Scope(value = "prototype")
	Member getMember() {
		return new Member();
	}

	@Bean(name = "userModel")
	@Scope(value = "prototype")
	UserModel getUserModel() {
		return new UserModel();
	}

	@Bean(name = "account")
	@Scope(value = "prototype")
	Account getAccount() {
		return new Account();
	}

	@Bean(name = "role")
	@Scope(value = "prototype")
	Role getRole() {
		return new Role();
	}

	@Bean(name = "transferModel")
	@Scope(value = "prototype")
	TransferModel getTransferModel() {
		return new TransferModel();
	}

	@Bean(name = "transactionHistory")
	@Scope(value = "prototype")
	TransactionHistory getTransactionHistory() {
		return new TransactionHistory();
	}

	@Bean(name = "editProfileModel")
	@Scope(value = "prototype")
	EditProfileModel getEditProfileModel() {
		return new EditProfileModel();
	}
}
