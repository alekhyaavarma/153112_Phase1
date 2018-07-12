package com.capgemini.paytm.junittest;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.capgemini.paytm.beans.Customer;
import com.capgemini.paytm.beans.Wallet;
import com.capgemini.paytm.exception.InvalidInputException;
import com.capgemini.paytm.service.WalletService;
import com.capgemini.paytm.service.WalletServiceImpl;

import static org.junit.Assert.*;
public class WalletTestCases {
	WalletService service;
	Customer customer1,customer2,customer3;	
		@Before
		public void initData(){
			 Map<String,Customer> data= new HashMap<String, Customer>();
			 customer1=new Customer("Amit", "9900112212",new Wallet(new BigDecimal(9000)));
			  customer2=new Customer("Ajay", "9963242422",new Wallet(new BigDecimal(6000)));
			  customer3=new Customer("Yogini", "9922950519",new Wallet(new BigDecimal(7000)));
			 
						
					
			 data.put("9900112212", customer1);
			 data.put("9963242422", customer2);	
			 data.put("9922950519", customer3);	
				service= new WalletServiceImpl(data);
		
		}
		@Test(expected=NullPointerException.class)
		public void testCreateAccount1() {
			
			service.createAccount(null,null,null);
			
			
		}
		@Test
		public void testCreateAccount2() {
			
			
			Customer cust=new Customer();
			Customer cust1=new Customer();
			cust1=service.createAccount("Amit","9900112213",new BigDecimal(7000));
			cust.setMobileNo("9900112213");
			cust.setName("Amit");
			cust.setWallet(new Wallet(new BigDecimal(7000)));
			Customer actual =cust;
			Customer expected=cust1;
			assertEquals(expected, actual);
		
			
			
		}
	@Test	
	public void testCreateAccount3() {
			
			
			
			Customer cust=new Customer();
			cust=service.createAccount("Amit","9900112213",new BigDecimal(7000));
			assertEquals("Amit", cust.getName());
		
			
			
		}
	@Test
	public void testCreateAccount4() {
		
		Customer cust=new Customer();
		cust=service.createAccount("Amit","9900112213",new BigDecimal(7000));
		assertEquals("9900112213", cust.getMobileNo());

		
		
	}


	@Test(expected=InvalidInputException.class)
	public void testShowBalance1() {
		Customer cust=new Customer();
	cust=service.showBalance("9579405744");

	}

	@Test
	public void testShowBalance2() {
		
		Customer cust=new Customer();
		
	cust=service.showBalance("9922950519");
	assertEquals(cust, customer3);

	}
	@Test
	public void testShowBalance3() {
		
		Customer cust=new Customer();
	cust=service.showBalance("9900112212");
	BigDecimal actual=cust.getWallet().getBalance();
	BigDecimal expected=new BigDecimal(9000);
	assertEquals(expected, actual);

	}

	@Test(expected=InvalidInputException.class)
	public void testFundTransfer1() {
		service.fundTransfer(null, null,new BigDecimal(7000));
	}

	@Test
	public void testFundTransfer2() {
		customer1=service.fundTransfer("9900112212","9963242422",new BigDecimal(2000));
		BigDecimal actual=customer1.getWallet().getBalance();
		BigDecimal expected=new BigDecimal(8000);
		assertEquals(expected, actual);
	}
	@Test(expected=InvalidInputException.class)
	public void testDeposit1()
	{
		service.depositAmount("900000000", new BigDecimal(2000));
	}
		
	@Test
	public void testDeposit2()
	{
		customer1=service.depositAmount("9963242422", new BigDecimal(2000));
		BigDecimal actual=customer1.getWallet().getBalance();
		BigDecimal expected=new BigDecimal(8000);
		assertEquals(expected, actual);
	}
	@Test(expected=InvalidInputException.class)
	public void testWithdraw1()
	{
		service.withdrawAmount("900000000", new BigDecimal(2000));
	}
		
	@Test
	public void testWithdraw2()
	{
		customer1=service.withdrawAmount("9963242422", new BigDecimal(2000));
		BigDecimal actual=customer1.getWallet().getBalance();
		BigDecimal expected=new BigDecimal(4000);
		assertEquals(expected, actual);
	}	
	@Test
	public void TestValidate()
	{
		Customer customer=new Customer("Vaish","8796543210",new Wallet(new BigDecimal(10)));
		service.acceptCustomerDetails(customer);
	}

	@After
	public void testAfter()
	{
		service=null;
	}

}
