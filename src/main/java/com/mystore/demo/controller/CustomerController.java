package com.mystore.demo.controller;


import com.mystore.demo.dao.CustomerRepository;
import com.mystore.demo.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RequestMapping("/api")
@RestController
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    /**
     * GET all customers or get customers by optional param country
     *
     * @param country
     * @return
     */
    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> getAllCustomers(@RequestParam(required = false) String country) {
        try {
            List<Customer> customerList = new ArrayList<>();

            if (country == null)
                customerRepository.findAll().forEach(customerList::add);
            else
                customerRepository.findByCountry(country).forEach(customerList::add);

            if (customerList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(customerList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * GET customer by Id
     *
     * @param id
     * @return
     */
    @GetMapping("/customers/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable("id") Integer id) {
        Optional<Customer> customerData = customerRepository.findById(id);

        if (customerData.isPresent()) {
            return new ResponseEntity<>(customerData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * POST create new customer
     *
     * @param customer
     * @return
     */
    @PostMapping("/customers")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        try {
            Customer newCustomer = customerRepository
                    .save(new Customer(customer.getId(), customer.getFirstname(), customer.getLastname(), customer.getCountry()));
            return new ResponseEntity<>(newCustomer, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

    /**
     * PUT - Update customer by id
     * @param id
     * @param customer
     * @return
     */
    @PutMapping("/customers/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable("id") Integer id, @RequestBody Customer customer) {
        Optional<Customer> dbCustomer = customerRepository.findById(id);

        if (dbCustomer.isPresent()) {
            dbCustomer.get().setCountry(customer.getCountry());
            dbCustomer.get().setFirstname(customer.getFirstname());
            dbCustomer.get().setLastname(customer.getLastname());
            return new ResponseEntity<>(customerRepository.save(dbCustomer.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    /**
     * DELETE customer by id
     * @param id
     * @return
     */
    @DeleteMapping("/customers/{id}")
    public ResponseEntity<HttpStatus> deleteCustomer(@PathVariable("id") Integer id) {
        try {
            customerRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

}
