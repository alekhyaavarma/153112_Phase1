package com.capgemini.paytm.service;
import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import com.capgemini.paytm.beans.Customer;
import com.capgemini.paytm.beans.Wallet;
import com.capgemini.paytm.exception.InsufficientBalanceException;
import com.capgemini.paytm.exception.InvalidInputException;
import com.capgemini.paytm.repo.WalletRepo;
import com.capgemini.paytm.repo.WalletRepoImpl;
public class WalletServiceImpl implements WalletService {
public WalletRepo repo;
	
	public WalletServiceImpl(){
		repo= new WalletRepoImpl();
	}
	public WalletServiceImpl(Map<String, Customer> data){
		repo= new WalletRepoImpl(data);
	}
	public WalletServiceImpl(WalletRepo repo) {
		super();
		this.repo = repo;
	}
	
	WalletRepoImpl obj=new WalletRepoImpl();
	
	public Customer createAccount(String name, String mobileNo, BigDecimal amount) {
		
		if(isValidPhoneNumber(mobileNo) && isValidName(name) && amount.compareTo(new BigDecimal(0)) > 0) {
		Customer cust=new Customer(name,mobileNo,new Wallet(amount));		
		
		repo.save(cust);
		return cust;
		}
		else throw new InvalidInputException("Invalid Input Details");
			
		}


	public Customer showBalance(String mobileNo) {
		if(mobileNo.length()<10) 
			throw new InvalidInputException("InvalidPhone Number");
		Customer customer=repo.findOne(mobileNo);		
		if(customer!=null)
			return customer;
		else
			throw new InvalidInputException("Invalid Mobile Number  ");
	}
	public Customer depositAmount(String mobileNo, BigDecimal amount) {
		if(isValidPhoneNumber(mobileNo)&&amount.compareTo(new BigDecimal(0)) <= 0) 
			throw new InvalidInputException("Enter valid amount");
		if(isValidPhoneNumber(mobileNo)) {
		Customer cust=new Customer();
		Wallet wallet=new Wallet();
		cust=repo.findOne(mobileNo);
		if(cust!=null){
			BigDecimal amtAdd=cust.getWallet().getBalance().add(amount);
			wallet.setBalance(amtAdd);
			cust.setWallet(wallet);
			obj.getData().put(mobileNo, cust);
		}
		return cust;
		}
		else throw new InvalidInputException("Please enter valid mobile number");
	}

	public Customer withdrawAmount(String mobileNo, BigDecimal amount) {

		if(amount.compareTo(new BigDecimal(0)) <= 0) 
			throw new InvalidInputException("Enter valid amount");
		
		if(isValidPhoneNumber(mobileNo)) 
		{
		Customer cust=new Customer();
		Wallet wallet=new Wallet();
		cust=repo.findOne(mobileNo);
		if(cust!=null){
			BigDecimal bal=cust.getWallet().getBalance();
			BigDecimal amtSub;
			if(bal.compareTo(amount)>0){
				amtSub=bal.subtract(amount);
				wallet.setBalance(amtSub);
				cust.setWallet(wallet);
				obj.getData().put(mobileNo, cust);
			}else{
				System.out.println("Insufficient Balance");
			}
		}
		return cust;
		}
		else throw new InvalidInputException("Enter valid mobile number");
	}
	public Customer fundTransfer(String sourceMobileNo, String targetMobileNo, BigDecimal amount) {	
		if(isValidPhoneNumber(sourceMobileNo) == false || isValidPhoneNumber(targetMobileNo) == false) throw new InvalidInputException("Enter valid Mobile Number");
		Customer sourceCust=new Customer();
		Customer targetCust=new Customer();
		Wallet sourceWallet=new Wallet();
		Wallet targetWallet=new Wallet();
		sourceCust=repo.findOne(sourceMobileNo);
		targetCust=repo.findOne(targetMobileNo);
		if(sourceCust!=null && targetCust!=null){		
			BigDecimal bal=sourceCust.getWallet().getBalance();
		if(bal.compareTo(amount)>0){
			BigDecimal diff=bal.subtract(amount);
			sourceWallet.setBalance(diff);
			sourceCust.setWallet(sourceWallet);
			BigDecimal baladd=targetCust.getWallet().getBalance();
			BigDecimal sum=baladd.add(amount);			
			targetWallet.setBalance(sum);
			targetCust.setWallet(targetWallet);
			obj.getData().put(targetMobileNo, targetCust);
			obj.getData().put(sourceMobileNo, sourceCust);
		}else{
			System.out.println("Insufficient Balance");
		}
				
		}else{
			throw new InvalidInputException("Account does not exist");
		}	
		return targetCust;
	}

	public boolean isValidPhoneNumber(String mobileNo) {
		if(mobileNo.matches("[1-9][0-9]{9}")) 
		{
			return true;
		}		
		else 
			return false;
	}

	private boolean isValidName(String name) {
		if( name == null || name.trim().isEmpty() )
			return false;
		return true;
	}
	
	
}
