package com.ungmydieu.bookmanagement.converters.books;

import com.ungmydieu.bookmanagement.converters.bases.Converter;
import com.ungmydieu.bookmanagement.models.dao.Book;
import com.ungmydieu.bookmanagement.models.dto.BookDTO;
import org.springframework.stereotype.Component;

@Component
public class BookDtoToBookDaoConverter extends Converter<BookDTO, Book> {

    @Override
    public Book convert(BookDTO source) {
        Book book = new Book(source.getTitle(), source.getAuthor(), source.getCreateAt(), source.getUpdateAt(),
                source.getEnable());

        book.setDescription(source.getDescription());
        book.setImage(source.getImage());
        return null;
    }
}
