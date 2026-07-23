package org.xian1.bookserver;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.xian1.bookserver.model.Book;
import org.xian1.bookserver.repository.ProductRepository;

@SpringBootApplication
public class BookServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookServerApplication.class, args);
    }

    @Bean
    CommandLineRunner initProducts(ProductRepository productRepository) {
        return args -> {
            if (productRepository.count() == 0) {
                productRepository.save(new Book("Laptop", "14 inch lightweight laptop", 4999.00, 30));
                productRepository.save(new Book("Keyboard", "Mechanical keyboard", 399.00, 120));
                productRepository.save(new Book("Headphones", "Noise cancelling headphones", 899.00, 65));
            }
        };
    }

}
