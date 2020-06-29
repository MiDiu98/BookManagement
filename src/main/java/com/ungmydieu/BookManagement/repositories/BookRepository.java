package com.ungmydieu.bookmanagement.repositories;

import com.ungmydieu.bookmanagement.models.dao.Book;
import com.ungmydieu.bookmanagement.models.dao.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<Book, Integer>, JpaRepository<Book, Integer>, PagingAndSortingRepository<Book, Integer> {
    List<Book> findByTitleContainingAndAuthorContainingAndEnabledTrueAllIgnoreCase(String title, String author);
    List<Book> findAllByUserAndEnabledTrue(User user);
    List<Book> findAllByUser(User user);
    List<Book> findAllByEnabled(boolean enabled);
    Page<Book> findAllByEnabledTrue(Pageable pageable);
}

