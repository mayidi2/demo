package org.xian1.bookserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.xian1.bookserver.model.Book;

public interface ProductRepository extends JpaRepository<Book, Long> {
}
