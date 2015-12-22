package com.example.accounts;

import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.example.DemoApplication;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DemoApplication.class)
@WebAppConfiguration
@Transactional
public class AccountControllerTest {
	
	@Autowired
	WebApplicationContext wac;

	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	AccountService service;
	
	MockMvc mockMvc;
	
	@Before
	public void setUp() {
		mockMvc = new MockMvcBuilders().webAppContextSetup(wac)
				                               .build();
	}
	
	@Test
	public void createAccount() throws JsonProcessingException, Exception {
		AccountDto.Create create = createAccountDto();
		
		ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/accounts")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(create)));
		result.andDo(MockMvcResultHandlers.print());
		result.andExpect(MockMvcResultMatchers.status().isCreated());
		result.andExpect(MockMvcResultMatchers.jsonPath("$.username", Is.is("jaehyuk")));
		
		//for dup check
		result = mockMvc.perform(MockMvcRequestBuilders.post("/accounts")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(create)));
		result.andDo(MockMvcResultHandlers.print());
		result.andExpect(MockMvcResultMatchers.status().isBadRequest());
		result.andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is("username.duplicated.exception")));
	}
	
	@Test
	public void createAccount_BadRequest() throws JsonProcessingException, Exception {
		AccountDto.Create create = new AccountDto.Create();
		create.setUsername("  ");
		create.setPassword("123");
		
		ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/accounts")
				                              .contentType(MediaType.APPLICATION_JSON)
				                              .content(objectMapper.writeValueAsString(create)));
		result.andDo(MockMvcResultHandlers.print());
		result.andExpect(MockMvcResultMatchers.status().isBadRequest());
		result.andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is("bad.request")));
	}
	
	@Test
	public void getAccounts() throws Exception {
		AccountDto.Create createDto = createAccountDto();
		service.createAccount(createDto);
		
		ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/accounts"));
		
		//AccountDto.Response의 값과 pagable을 이용한 반환 값
		//{"content":
		//[{"id":1,"username":"jaehyuk","fullName":null,"joined":1447339279230,"updated":1447339279230}],
		//"totalPages":1,
		//"last":true,
		//"totalElements":1,
		//"size":20,
		//"number":0,
		//"sort":null,
		//"numberOfElements":1,
		//"first":true}
		result.andDo(MockMvcResultHandlers.print());
		result.andExpect(MockMvcResultMatchers.status().isOk());
	}

	private AccountDto.Create createAccountDto() {
		AccountDto.Create createDto = new AccountDto.Create();
		createDto.setUsername("jaehyuk");
		createDto.setPassword("springboot");
		return createDto;
	}
	
	@Test
	public void getAccount() throws Exception {
		AccountDto.Create createDto = createAccountDto();
		Account account = service.createAccount(createDto);
		ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/accounts/"+account.getId()));
		
		result.andDo(MockMvcResultHandlers.print());
		result.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void updateAccount() throws JsonProcessingException, Exception {
		AccountDto.Create createDto = createAccountDto();
		Account account = service.createAccount(createDto);
		
		AccountDto.Update updateDto = new AccountDto.Update();
		updateDto.setFullName("jae hyuk");
		updateDto.setPassword("newspring");
		
		ResultActions result = mockMvc.perform(MockMvcRequestBuilders.put("/accounts/"+account.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(updateDto)));
		
		result.andDo(MockMvcResultHandlers.print());
		result.andExpect(MockMvcResultMatchers.status().isOk());
		result.andExpect(MockMvcResultMatchers.jsonPath("$.fullName", Is.is("jae hyuk")));
	}

}
