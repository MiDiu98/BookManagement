package com.ungmydieu.bookmanagement.services.impl;

import com.ungmydieu.bookmanagement.exceptions.BadRequestException;
import com.ungmydieu.bookmanagement.exceptions.NotFoundException;
import com.ungmydieu.bookmanagement.models.dao.Book;
import com.ungmydieu.bookmanagement.models.dao.User;
import com.ungmydieu.bookmanagement.models.dto.BookDTO;
import com.ungmydieu.bookmanagement.repositories.BookRepository;
import com.ungmydieu.bookmanagement.repositories.UserRepository;
import com.ungmydieu.bookmanagement.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Book> getAllBooks(Integer pageNo, Integer pageSize, String sortBy, String order) {
        Pageable paging = PageRequest.of(pageNo, pageSize, order.equals("desc")? Sort.by(sortBy).descending():Sort.by(sortBy).ascending());
        Page<Book> pagedResult = bookRepository.findAll(paging);

        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<Book>();
        }
    }

    @Override
    public List<Book> getAllBooksEnable(Integer pageNo, Integer pageSize, String sortBy, String order) {
        Pageable paging = PageRequest.of(pageNo, pageSize, order.equals("desc")? Sort.by(sortBy).descending():Sort.by(sortBy).ascending());
        Slice<Book> pagedResult = bookRepository.findAllByEnabledTrue(paging);

        if(pagedResult.hasContent()) {
            System.out.println(bookRepository.countAllByEnabledTrue());
            return pagedResult.getContent();
        } else {
            return new ArrayList<Book>();
        }
    }

    @Override
    public List<Book> getAllBooksDisable() {
        return bookRepository.findAllByEnabledFalse();
    }

    @Override
    public List<Book> findByTitleAndAuthor(String title, String author) {
        return bookRepository.findByTitleContainingAndAuthorContainingAllIgnoreCase(title, author);
    }

    @Override
    public List<Book> findByUser(int userId) {
        verifyUserIdExist(userId);
        return bookRepository.findAllByUser(userRepository.getOne(userId));
    }

    @Override
    public List<Book> getMyBooks(Principal principal) {
        return bookRepository.findAllByUser(userRepository.findByEmail(principal.getName()));
    }

    @Override
    public Book getBookById(int id) {
        verifyBookIdExist(id);
        return bookRepository.findById(id).get();
    }

    @Override
    public Book create(Principal principal, BookDTO bookDTO) {
        Book book = new Book(bookDTO.getTitle(), bookDTO.getAuthor(), LocalDateTime.now(), LocalDateTime.now(),true);
        book.setDescription(bookDTO.getDescription());
        book.setImage(bookDTO.getImage());
        book.setUser(userRepository.findByEmail(principal.getName()));

        bookRepository.save(book);
        return bookRepository.findById(book.getId()).get();
    }

    @Override
    public Book update(Principal principal, int id, BookDTO bookDTO) {
        verifyBookIdExist(id);
        verifyAuthor(principal, id);

        Book book = bookRepository.getOne(id);
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setUpdateAt(LocalDateTime.now());
        book.setDescription(bookDTO.getDescription());
        book.setImage(bookDTO.getImage());
        bookRepository.save(book);
        return bookRepository.findById(id).get();
    }

    @Override
    public Book updateByAdmin(int id, BookDTO bookDTO) {
        Book book = bookRepository.getOne(id);

        book.setEnabled(bookDTO.isEnabled());
        bookRepository.save(book);
        return bookRepository.findById(id).get();
    }

    @Override
    public void delete(Principal principal, int id) {
        verifyBookIdExist(id);
        verifyAuthor(principal, id);

        bookRepository.delete(bookRepository.getOne(id));
    }

    private void verifyBookIdExist(int id) {
        if (!bookRepository.existsById(id)) {
            throw new NotFoundException(String.format("Book id %d is not found", id));
        }
    }

    private void verifyAuthor(Principal principal, int id) {
        User currentUser = userRepository.findByEmail(principal.getName());
        if (!currentUser.equals(bookRepository.findById(id).get().getUser())) {
            throw new BadRequestException(String.format("The only author can modify"));
        }
    }

    private void verifyUserIdExist(int id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException(String.format("User id %d is not found", id));
        }
    }
}
