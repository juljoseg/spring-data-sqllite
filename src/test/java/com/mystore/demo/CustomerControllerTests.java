package com.mystore.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mystore.demo.controller.CustomerController;
import com.mystore.demo.dao.CustomerRepository;
import com.mystore.demo.model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

		this.mockMvc.perform(get("/api/customers"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().json(customerList.toString()) );

		verify(customerRepositoryMock, times(1)).findAll();
		verifyNoMoreInteractions(customerRepositoryMock);
	}

	@Test
	public void testGetEmptyCustomerList() throws Exception {

		List<Customer> customerList = new ArrayList<>();

		when(customerRepositoryMock.findAll()).thenReturn(customerList);

		this.mockMvc.perform(get("/api/customers"))
				.andDo(print())
				.andExpect(status().isNoContent());

		verify(customerRepositoryMock, times(1)).findAll();
		verifyNoMoreInteractions(customerRepositoryMock);
	}

	@Test
	public void testGetCustomerByCountry() throws Exception {

		List<Customer> customerList = Arrays.asList(
				new Customer(1,"fn","ln","a"));

		when(customerRepositoryMock.findByCountry("a")).thenReturn(customerList);

		this.mockMvc.perform(get("/api/customers/").param("country","a"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().json(customerList.toString()) );

		verify(customerRepositoryMock, times(1)).findByCountry("a");
		verifyNoMoreInteractions(customerRepositoryMock);
	}

	@Test
	public void testGetCustomerById() throws Exception {

		Customer customer = new Customer(1,"fn","ln","a");

		Optional<Customer> optionalCustomer = Optional.of(customer);

		when(customerRepositoryMock.findById(1)).thenReturn(optionalCustomer);

		this.mockMvc.perform(get("/api/customers/1"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().json(customer.toString()));

		verify(customerRepositoryMock, times(1)).findById(1);
		verifyNoMoreInteractions(customerRepositoryMock);
	}


	@Test
	public void testCreateCustomer() throws Exception {

		Customer customer = new Customer(1,"fn","ln","a");

		when(customerRepositoryMock.save(any(Customer.class))).thenReturn(customer);

		this.mockMvc.perform(post("/api/customers/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(customer))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(content().json(customer.toString())) ;

	}




	@Test
	public void testUpdateCustomer() throws Exception {

		Customer customer = new Customer(1,"fn","ln","a");

		Optional<Customer> optionalCustomer = Optional.of(customer);

		when(customerRepositoryMock.findById(1)).thenReturn(optionalCustomer);

		when(customerRepositoryMock.save(any(Customer.class))).thenReturn(customer);

		this.mockMvc.perform(put("/api/customers/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(customer))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().json(customer.toString()) );

		verify(customerRepositoryMock, times(1)).findById(1);
		verify(customerRepositoryMock, times(1)).save(customer);

	}

	@Test
	public void testDeleteCustomerById() throws Exception {

		this.mockMvc.perform(delete("/api/customers/1"))
				.andDo(print())
				.andExpect(status().isNoContent())
				.andExpect(content().string(""));

		verify(customerRepositoryMock, times(1)).deleteById(1);
		verifyNoMoreInteractions(customerRepositoryMock);
	}


	private String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
