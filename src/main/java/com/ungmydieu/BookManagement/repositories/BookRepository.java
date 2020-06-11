package com.ungmydieu.bookmanagement.repositories;

import com.ungmydieu.bookmanagement.models.dao.Book;
import com.ungmydieu.bookmanagement.models.dao.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<Book, Integer>, JpaRepository<Book, Integer>, PagingAndSortingRepository<Book, Integer> {
    List<Book> findByTitleContainingAndAuthorContainingAllIgnoreCase(String title, String author);
    List<Book> findAllByUser(User user);
    List<Book> findAllByEnabledFalse();
    Slice<Book> findAllByEnabledTrue(Pageable pageable);
    int countAllByEnabledTrue();
}

