package com.example.accounts;

import static org.junit.Assert.*;

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
import org.springframework.web.context.WebApplicationContext;

import com.example.DemoApplication;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DemoApplication.class)
@WebAppConfiguration
public class AccountControllerTest {
	
	@Autowired
	WebApplicationContext wac;

	@Autowired
	ObjectMapper objectMapper;
	
	@Test
	public void createAccount() throws JsonProcessingException, Exception {
		MockMvc mockMvc = new MockMvcBuilders().webAppContextSetup(wac)
											.build();
		
		AccountDto.Create create = new AccountDto.Create();
		create.setUsername("jaehyuk");
		create.setPassword("springboot");
		
		ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/accounts")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(create)));
		
		result.andDo(MockMvcResultHandlers.print());
		result.andExpect(MockMvcResultMatchers.status().isCreated());
	}

}
