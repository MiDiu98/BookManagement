package com.ungmydieu.bookmanagement.services;

import com.ungmydieu.bookmanagement.models.dao.Book;
import com.ungmydieu.bookmanagement.models.dto.BookDTO;
import com.ungmydieu.bookmanagement.models.dto.BookPage;

import java.security.Principal;
import java.util.List;

public interface BookService {
    List<Book> getAllBooks(Integer pageNo, Integer pageSize, String sortBy, String order);
    BookPage getAllBooksEnable(Integer pageNo, Integer pageSize, String sortBy, String order);
    List<Book> getBooksByAdmin(boolean enabled, String sortBy, String order);
    List<Book> findByTitleAndAuthor(String title, String author);
    List<Book> findByUser(int userId);
    List<Book> getMyBooks(Principal principal);
    Book getBookById(int id);
    Book create(Principal principal, BookDTO bookDTO);
    Book update(Principal principal, int id, BookDTO bookDTO);
    Book updateByAdmin(int id, BookDTO bookDTO);
    void delete(String role, Principal principal, int id);
}
