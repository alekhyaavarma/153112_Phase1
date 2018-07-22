
package com.capgemini.paytm.ui;

import java.math.BigDecimal;
import java.util.Scanner;

import com.capgemini.paytm.beans.Customer;
import com.capgemini.paytm.beans.Wallet;
import com.capgemini.paytm.exception.InsufficientBalanceException;
import com.capgemini.paytm.exception.InvalidInputException;
import com.capgemini.paytm.service.WalletService;
import com.capgemini.paytm.service.WalletServiceImpl;

public class Client {

	private WalletService walletService;
	public Client()
	{
		walletService=new WalletServiceImpl();
	}
	public void menu() {	
		Scanner sc=new Scanner(System.in);
		Customer customer=new Customer();
			System.out.println("1. Create Account ");
			System.out.println("2. Show Balance");
			System.out.println("3. Deposit amount");
			System.out.println("4. Withdraw amount");
			System.out.println("5. Fund Transfer");
			System.out.println("6. Exit");			
			System.out.println("\nPlease Select an option:");
			int choice=sc.nextInt();
			
			switch (choice) {
			case 1:
				System.out.println("Enter Name: ");
				String name=sc.next();
				System.out.println("Enter Mobile Number: ");
				String mobileNo=sc.next();
				System.out.println("Enter Balance");
				BigDecimal amount=sc.nextBigDecimal();
				try {
				customer=walletService.createAccount(name, mobileNo, amount);
				if(customer!=null){
				System.out.println(customer);
				}
				}
				catch(InvalidInputException e) {
					System.out.println("Enter Valid Details");
				}
				break;				
			case 2:				
				System.out.println("Enter mobile number: ");
				String mobileNo2=sc.next();
				try {
				customer=walletService.showBalance(mobileNo2);
				System.out.println("Available Balance in your mobile number is: "+mobileNo2+" is "+customer.getWallet());
				}
				catch(InvalidInputException e) {
					System.out.println("Enter Valid Mobile Number");
				}
				break;
			case 3:
				System.out.println("Enter Mobile Number: ");
				String mobileNo3=sc.next();
				System.out.println("Enter Amount to deposit");
				BigDecimal amount3=sc.nextBigDecimal();			
				try {
				customer=walletService.depositAmount(mobileNo3, amount3);
				System.out.println(customer);
				}
				catch(InvalidInputException e) {
					System.out.println("Enter Valid mobile Number");
				}
				catch (InsufficientBalanceException e1) {
					System.out.println("Insufficient Balance");
				}
				break;
				
			case 4:
				System.out.println("Enter Mobile Number: ");
				String mobileNo4=sc.next();
				System.out.println("Enter Amount to withdraw");
				BigDecimal amount4=sc.nextBigDecimal();	
				try {
				customer=walletService.withdrawAmount(mobileNo4, amount4);
				System.out.println(customer);
				}
				catch(InvalidInputException e) {
					System.out.println("Enter Valid mobile Number");
				}
				catch (InsufficientBalanceException e1) {
					System.out.println("Insufficient Balance");
				}
				break;
			case 5:
				System.out.println("Enter Source Mobile Number: ");
				String sourceMobileNo=sc.next();
				System.out.println("Enter Target Mobile Number: ");
				String targetMobileNo=sc.next();
				System.out.println("Enter amount to transfer");
				BigDecimal amount1=sc.nextBigDecimal();	
				try {
				customer=walletService.fundTransfer(sourceMobileNo, targetMobileNo, amount1);
				System.out.println(customer);
				}
				catch(InvalidInputException e) {
					System.out.println("Enter Valid mobile Number");
				}
				catch (InsufficientBalanceException e1) {
					System.out.println("Insufficient Balance");
				}
				break;
			case 6:
				System.out.println("Thank You for using our services");
				System.exit(0);
				break;
			default:
				System.out.println("Invalid options");
				break;
			}			
			
	}
	public static void main(String[] args) {
		Client client=new Client();
		while(true)
		client.menu();

	}

}
