package com.example.demo.Bootstrap;

import com.example.demo.Dao.CustomerRepository;
import com.example.demo.Dao.DivisionRepository;
import com.example.demo.Entities.Customer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootStrapData implements CommandLineRunner {

    private final CustomerRepository customerRepository;

    private final DivisionRepository divisionRepository;

    public BootStrapData(CustomerRepository customerRepository, DivisionRepository divisionRepository) {

        this.customerRepository = customerRepository;
        this.divisionRepository = divisionRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        if(customerRepository.count() > 1) {
            return;
        }

        Customer customer1 = new Customer("Gary", "Smith", "101 First Avenue", "12345", "123-456-7890", 8L);
        customerRepository.save(customer1);

        Customer customer2 = new Customer("Tod", "Smith", "102 First Avenue", "12346", "123-456-7891", 8L);
        customerRepository.save(customer2);

        Customer customer3 = new Customer("Ken", "Smith", "103 First Avenue", "12347", "123-456-7892", 8L);
        customerRepository.save(customer3);

        Customer customer4 = new Customer("Sue", "Smith", "104 First Avenue", "12348", "123-456-7893", 8L);
        customerRepository.save(customer4);

        Customer customer5 = new Customer("Mary", "Smith", "105 First Avenue", "12349", "123-456-7894", 8L);
        customerRepository.save(customer5);

        customerRepository.findAll();
    }




}
