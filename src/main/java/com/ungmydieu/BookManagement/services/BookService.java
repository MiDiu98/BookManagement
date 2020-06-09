package com.ungmydieu.bookmanagement.services;

import com.ungmydieu.bookmanagement.models.dao.Book;
import com.ungmydieu.bookmanagement.models.dto.BookDTO;

import java.security.Principal;
import java.util.List;

public interface BookService {
    List<Book> getAllBooks(Integer pageNo, Integer pageSize, String sortBy);
    Book getBookById(int id);
    Book create(Principal principal, BookDTO bookDTO);
    Book update(Principal principal, int id, BookDTO bookDTO);
    Book updateByAdmin(int id, BookDTO bookDTO);
    void delete(Principal principal, int id);
}
