package com.sasika.salon.booking.controller;

import com.sasika.salon.booking.dto.CustomerDto;
import com.sasika.salon.booking.entity.Customer;
import com.sasika.salon.booking.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "*")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    private CustomerDto convertToDto(Customer customer){
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(customer.getId());
        customerDto.setName(customer.getName());
        customerDto.setEmail(customer.getEmail());
        customerDto.setPhoneNumber(customer.getPhoneNumber()); // add this
        // Do NOT include password if you're returning to frontend
        return customerDto;
    }

    private Customer convertToEntity(CustomerDto customerDto){
        Customer customer = new Customer();
        customer.setId(customerDto.getId());
        customer.setName(customerDto.getName());
        customer.setEmail(customerDto.getEmail());
        customer.setPhoneNumber(customerDto.getPhoneNumber()); // add this
        customer.setPassword(customerDto.getPassword()); // only if needed
        return customer;
    }


    @GetMapping
    public ResponseEntity<List<CustomerDto>> getAllCustomers(){
        List<CustomerDto> customer = customerService.getAllCustomers()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(customer);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable Long id){
        Customer customer = customerService.getCustomer(id);
        return ResponseEntity.ok(convertToDto(customer));
    }

    @PostMapping
    public ResponseEntity<CustomerDto> createCustomer(@Valid @RequestBody CustomerDto customerDto){
        Customer customer = customerService.createCustomer(convertToEntity(customerDto));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(convertToDto(customer));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDto> updateCustomer(@Valid @PathVariable Long id, @RequestBody CustomerDto customerDto){
        Customer customer = customerService.updateCustomer(id, convertToEntity(customerDto));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(convertToDto(customer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id){
        String deleteStatus = customerService.deleteCustomer(id);
        if(deleteStatus.contains("not found")){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(deleteStatus);
        }
        return ResponseEntity.ok(deleteStatus);
    }
}