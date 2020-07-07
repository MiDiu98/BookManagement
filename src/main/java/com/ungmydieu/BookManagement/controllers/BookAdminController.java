package com.ungmydieu.bookmanagement.controllers;

import com.ungmydieu.bookmanagement.constants.RoleConstants;
import com.ungmydieu.bookmanagement.converters.bases.Converter;
import com.ungmydieu.bookmanagement.models.dao.Book;
import com.ungmydieu.bookmanagement.models.dto.BookDTO;
import com.ungmydieu.bookmanagement.models.dto.BookPage;
import com.ungmydieu.bookmanagement.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("api/admin/books")
public class BookAdminController {

    @Autowired
    private Converter<Book, BookDTO> bookBookDTOConverter;

    @Autowired
    private BookService bookService;

    @GetMapping
    public BookPage getAllBooks(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String order) {
        return bookService.getAllBooks(pageNo, pageSize, sortBy, order);
    }

    @GetMapping("/status")
    public BookPage getBooksByAdmin(
            @RequestParam boolean enabled,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String order) {
        return bookService.getBooksByAdmin(enabled, pageNo, pageSize, sortBy, order);
    }

    @GetMapping("/{id}")
    public BookDTO getById(Principal principal, @PathVariable int id) {
        return bookBookDTOConverter.convert(bookService.getBookById(principal, id));
    }

    @PutMapping("/{id}")
    public BookDTO updateByAdmin(@PathVariable int id, @RequestBody BookDTO bookDTO) {
        return bookBookDTOConverter.convert(bookService.updateByAdmin(id, bookDTO));
    }

    @DeleteMapping("/{id}")
    public void deleteByAdmin(Principal principal, @PathVariable int id) {
        bookService.delete(RoleConstants.ADMIN, principal, id);
    }
}
