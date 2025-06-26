package com.sasika.salon.booking.service.impl;

import com.sasika.salon.booking.entity.Customer;
import com.sasika.salon.booking.repository.CustomerRepository;
import com.sasika.salon.booking.service.CustomerService;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    @Override
    public List<Customer> getAllCustomers() {
        logger.info("Fetching all customers");
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomer(Long id) {
        logger.info("Fetching customer with id {}", id);
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + id));
    }

    @Override
    public Customer createCustomer(Customer customer) {
        logger.info("Creating new customer: {}", customer.getName());
        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(Long id, Customer customer) {
        logger.info("Updating customer with ID: {}", id);
        return customerRepository.findById(id)
                .map(existingCustomer -> {
                    existingCustomer.setName(customer.getName());
                    existingCustomer.setPhoneNumber(customer.getPhoneNumber());
                    existingCustomer.setEmail(customer.getEmail());
                    existingCustomer.setPassword(customer.getPassword());
                    logger.info("Customer with ID {} updated successfully", id);
                    return customerRepository.save(existingCustomer);
                })
                .orElseThrow(() -> {
                    logger.warn("Customer with ID {} not found for update", id);
                    return new RuntimeException("Customer not found with ID: " + id);
                });
    }


    @Override
    public void deleteCustomer(Long id) {
        logger.info("Deleting customer with ID: {}", id);
        customerRepository.deleteById(id);
    }
}
