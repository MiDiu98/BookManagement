package com.ungmydieu.bookmanagement.controllers;

import com.ungmydieu.bookmanagement.constants.RoleConstants;
import com.ungmydieu.bookmanagement.converters.bases.Converter;
import com.ungmydieu.bookmanagement.models.dao.Book;
import com.ungmydieu.bookmanagement.models.dto.BookDTO;
import com.ungmydieu.bookmanagement.models.dto.BookPage;
import com.ungmydieu.bookmanagement.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("api/books")
public class BookController {

    @Autowired
    private Converter<Book, BookDTO> bookBookDTOConverter;

    @Autowired
    private BookService bookService;

    @GetMapping("/enable")
    public BookPage getAllBookEnable(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String order)
    {
        return bookService.getAllBooksEnable(pageNo, pageSize, sortBy, order);
    }

    @GetMapping("/{id}")
    public BookDTO getById(@PathVariable int id) {
        return bookBookDTOConverter.convert(bookService.getBookByIdAndEnabledTrue(id));
    }

    @GetMapping("/find")
    public List<BookDTO> findByTitleOrAuthor(@RequestParam(defaultValue = "") String title, @RequestParam(defaultValue = "") String author) {
        return bookBookDTOConverter.convert(bookService.findByTitleAndAuthor(title, author));
    }

    @GetMapping("/find/user")
    public List<BookDTO> findByUser(@RequestParam(defaultValue = "") int userId) {
        return bookBookDTOConverter.convert(bookService.findByUser(userId));
    }

    @GetMapping("/my-books/{id}")
    public BookDTO getMyBookById(Principal principal, @PathVariable int id) {
        return bookBookDTOConverter.convert(bookService.getBookById(principal, id));
    }

    @GetMapping("/my-books")
    @Secured("ROLE_USER")
    public List<BookDTO> getMyBooks(Principal principal) {
        return bookBookDTOConverter.convert(bookService.getMyBooks(principal));
    }

    @PostMapping
    @Secured("ROLE_USER")
    public BookDTO create(Principal principal, @RequestBody BookDTO bookDTO) {
        return bookBookDTOConverter.convert(bookService.create(principal, bookDTO));
    }

    @PutMapping("/{id}")
    @Secured("ROLE_USER")
    public BookDTO update(Principal principal, @PathVariable int id, @RequestBody BookDTO bookDTO) {
        return bookBookDTOConverter.convert(bookService.update(principal, id, bookDTO));

    }

    @DeleteMapping("/{id}")
    @Secured("ROLE_USER")
    public void delete(Principal principal, @PathVariable int id) {
        bookService.delete(RoleConstants.USER, principal, id);
    }
}
