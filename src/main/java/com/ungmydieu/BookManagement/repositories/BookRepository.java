package com.ungmydieu.bookmanagement.repositories;

import com.ungmydieu.bookmanagement.models.dao.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer>, PagingAndSortingRepository<Book, Integer> {
    List<Book> findByTitleContainingAndAuthorContainingAllIgnoreCase(String title, String author);
}
