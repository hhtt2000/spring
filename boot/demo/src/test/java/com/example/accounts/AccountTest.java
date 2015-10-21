package com.example.accounts;

import org.junit.Assert;
import org.junit.Test;

public class AccountTest {

	@Test
	public void getterSetter(){
		Account account = new Account();
		account.setLoginId("Jaehyuk");
		account.setPassword("springboot");
		Assert.assertEquals(account.getLoginId(), "Jaehyuk");
	}
}
