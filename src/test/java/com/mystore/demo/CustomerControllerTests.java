package com.mystore.demo;

import com.mystore.demo.controller.CustomerController;
import com.mystore.demo.dao.CustomerRepository;
import com.mystore.demo.model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
class CustomerControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CustomerRepository customerRepositoryMock;

	@Test
	public void testGetAllCustomers() throws Exception {

		List<Customer> customerList = Arrays.asList(
				new Customer(1,"fn","ln","a"),
				new Customer(2,"fn2","ln2","b"));

		when(customerRepositoryMock.findAll()).thenReturn(customerList);
		this.mockMvc.perform(get("/api/customers")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().json(customerList.toString()) );

		verify(customerRepositoryMock, times(1)).findAll();
		verifyNoMoreInteractions(customerRepositoryMock);
	}

	@Test
	public void testGetCustomerByCountry() throws Exception {

		List<Customer> customerList = Arrays.asList(
				new Customer(1,"fn","ln","a"));

		when(customerRepositoryMock.findByCountry("a")).thenReturn(customerList);

		this.mockMvc.perform(get("/api/customers/").param("country","a")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().json(customerList.toString()) );

		verify(customerRepositoryMock, times(1)).findByCountry("a");
		verifyNoMoreInteractions(customerRepositoryMock);
	}

	@Test
	public void testCreateCustomer() throws Exception {

		Customer customer = new Customer(1,"fn","ln","a");

		when(customerRepositoryMock.save(any(Customer.class))).thenReturn(customer);

		this.mockMvc.perform(post("/api/customers/")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{ " +
						"    \"id\": 7, " +
						"    \"firstname\": \"Test3\", " +
						"    \"lastname\": \"LT4\", " +
						"    \"country\": \"USA\" " +
						"}")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(content().json(customer.toString()) );
	}

}
