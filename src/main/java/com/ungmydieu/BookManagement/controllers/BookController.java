package com.ungmydieu.bookmanagement.controllers;

import com.ungmydieu.bookmanagement.converters.bases.Converter;
import com.ungmydieu.bookmanagement.models.dao.Book;
import com.ungmydieu.bookmanagement.models.dto.BookDTO;
import com.ungmydieu.bookmanagement.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("api/books")
@PreAuthorize("isAuthenticated()")
public class BookController {

    @Autowired
    private Converter<Book, BookDTO> bookBookDTOConverter;

    @Autowired
    private BookService bookService;

    @GetMapping
    @Secured("ROLE_ADMIN")
    public List<BookDTO> getAll(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String order)
    {
        return bookBookDTOConverter.convert(bookService.getAllBooks(pageNo, pageSize, sortBy, order));
    }

    @GetMapping("/disable")
    @Secured("ROLE_ADMIN")
    public List<BookDTO> getAllBooksDisable() {
        return bookBookDTOConverter.convert(bookService.getAllBooksDisable());
    }

    @GetMapping("/enable")
    @PreAuthorize("permitAll()")
    public List<BookDTO> getAllBookEnable(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String order)
    {
        return bookBookDTOConverter.convert(bookService.getAllBooksEnable(pageNo, pageSize, sortBy, order));
    }

    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    public BookDTO getById(@PathVariable int id) {
        return bookBookDTOConverter.convert(bookService.getBookById(id));
    }

    @GetMapping("/find")
    @PreAuthorize("permitAll()")
    public List<BookDTO> findByTitleOrAuthor(@RequestParam(defaultValue = "") String title, @RequestParam(defaultValue = "") String author) {
        return bookBookDTOConverter.convert(bookService.findByTitleAndAuthor(title, author));
    }

    @GetMapping("/find/user")
    @PreAuthorize("permitAll()")
    public List<BookDTO> findByUser(@RequestParam(defaultValue = "") int userId) {
        return bookBookDTOConverter.convert(bookService.findByUser(userId));
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

    @PutMapping("/admin/{id}")
    @Secured("ROLE_ADMIN")
    public BookDTO updateByAdmin(@PathVariable int id, @RequestBody BookDTO bookDTO) {
        return bookBookDTOConverter.convert(bookService.updateByAdmin(id, bookDTO));
    }

    @DeleteMapping("/{id}")
    @Secured("ROLE_USER")
    public void delete(Principal principal, @PathVariable int id) {
        bookService.delete(principal, id);
    }
}
