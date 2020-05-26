package com.ungmydieu.BookManagement.repositories;

import com.ungmydieu.BookManagement.models.dao.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
}
