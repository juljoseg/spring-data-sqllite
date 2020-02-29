package com.mystore.demo.dao;

import com.mystore.demo.model.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer,Integer> {

    List<Customer> findByCountry(String country);
}
