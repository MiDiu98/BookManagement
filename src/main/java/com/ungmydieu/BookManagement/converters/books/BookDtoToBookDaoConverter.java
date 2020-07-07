package com.ungmydieu.bookmanagement.converters.books;

import com.ungmydieu.bookmanagement.converters.bases.Converter;
import com.ungmydieu.bookmanagement.models.dao.Book;
import com.ungmydieu.bookmanagement.models.dto.BookDTO;
import com.ungmydieu.bookmanagement.repositories.BookRepository;
import com.ungmydieu.bookmanagement.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class BookDtoToBookDaoConverter extends Converter<BookDTO, Book> {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Book convert(BookDTO source) {
//        Book book = new Book(source.getTitle(), source.getAuthor(), source.getCreateAt(), source.getUpdateAt(),
//                source.isEnabled());
//
//        book.setDescription(source.getDescription());
//        book.setImage(source.getImage());
//
//        if(source.getUserId() > 0){
//            Optional<User> user = userRepository.findById(source.getUserId());
//
//            if(user.isPresent()){
//                book.setUser(user.get());
//            }else{
//                throw new BadRequestException("Invalid author id " + source.getUserId());
//            }
//        }
        Book book = bookRepository.getOne(source.getId());
        book.setTitle(source.getTitle());
        book.setAuthor(source.getAuthor());
        book.setUpdateAt(LocalDateTime.now());
        book.setDescription(source.getDescription());
        book.setImage(source.getImage());

        System.out.println("book: " + book.toString());
        return book;
    }
}
