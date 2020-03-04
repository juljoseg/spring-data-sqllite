package com.mystore.demo.dao;

import com.mystore.demo.model.Customer;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer,Integer> {

    @Override
    @CacheEvict(value = "byCountry", key = "#p0.country")
    <S extends Customer> S save(S entity);

    @Cacheable("byCountry")
    List<Customer> findByCountry(String country);
}
