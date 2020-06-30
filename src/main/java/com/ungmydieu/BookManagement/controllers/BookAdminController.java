package com.ungmydieu.bookmanagement.controllers;

import com.ungmydieu.bookmanagement.constants.RoleConstants;
import com.ungmydieu.bookmanagement.converters.bases.Converter;
import com.ungmydieu.bookmanagement.models.dao.Book;
import com.ungmydieu.bookmanagement.models.dto.BookDTO;
import com.ungmydieu.bookmanagement.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("api/admin/books")
public class BookAdminController {

    @Autowired
    private Converter<Book, BookDTO> bookBookDTOConverter;

    @Autowired
    private BookService bookService;

    @GetMapping
    public List<BookDTO> getBooksByAdmin(
            @RequestParam boolean enabled,
            @RequestParam String sortBy,
            @RequestParam String order) {
        return bookBookDTOConverter.convert(bookService.getBooksByAdmin(enabled, sortBy, order));
    }

    @GetMapping("/{id}")
    public BookDTO getById(@PathVariable int id) {
        return bookBookDTOConverter.convert(bookService.getBookById(id));
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
