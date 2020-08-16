package com.github.nielsonrocha.sales.customer.resource;

import com.github.nielsonrocha.sales.customer.entity.Customer;
import com.github.nielsonrocha.sales.customer.messaging.CustomerProducer;
import com.github.nielsonrocha.sales.customer.repository.CustomerRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@AllArgsConstructor
@Tag(name = "Customer")
@RequestMapping("/customers")
public class CustomerResource {

    private final CustomerRepository customerRepository;

    private final CustomerProducer customerProducer;

    @GetMapping
    public ResponseEntity<Page<Customer>> retrieveAll(Pageable pageable) {
        return ResponseEntity.ok(customerRepository.findAll(pageable));
    }

    @GetMapping("{id}")
    public ResponseEntity<Customer> retrieve(@PathVariable long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        return customer
                .map(e -> new ResponseEntity<>(e, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Customer> create(@RequestBody Customer customer) {
        Customer savedCustomer = customerRepository.save(customer);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedCustomer.getId()).toUri();

        customerProducer.send(savedCustomer);

        return ResponseEntity.created(location).build();

    }

    @PutMapping("{id}")
    public ResponseEntity<Customer> update(@RequestBody Customer customer, @PathVariable long id) {

        Optional<Customer> studentOptional = customerRepository.findById(id);

        if (studentOptional.isEmpty())
            return ResponseEntity.notFound().build();

        customer.setId(id);
        customerRepository.save(customer);

        customerProducer.send(customer);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    public void deleteStudent(@PathVariable long id) {
        customerRepository.deleteById(id);
    }
}
