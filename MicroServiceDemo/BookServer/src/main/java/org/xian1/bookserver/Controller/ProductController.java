package org.xian1.bookserver.Controller;

import org.springframework.web.bind.annotation.*;
import org.xian1.bookserver.model.Book;
import org.xian1.bookserver.repository.ProductRepository;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public List<Book> findAll() {
        return productRepository.findAll();
    }

    @GetMapping("/{id}")
    public Book findById(@PathVariable Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Book create(@RequestBody Book product) {
        return productRepository.save(product);
    }
}
