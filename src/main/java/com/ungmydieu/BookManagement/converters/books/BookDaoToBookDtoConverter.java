package com.ungmydieu.bookmanagement.converters.books;

import com.ungmydieu.bookmanagement.converters.bases.Converter;
import com.ungmydieu.bookmanagement.exceptions.BadRequestException;
import com.ungmydieu.bookmanagement.models.dao.Book;
import com.ungmydieu.bookmanagement.models.dto.BookDTO;
import org.springframework.stereotype.Component;

@Component
public class BookDaoToBookDtoConverter extends Converter<Book, BookDTO> {

    @Override
    public BookDTO convert(Book source) throws BadRequestException {
        BookDTO bookDTO = new BookDTO(source.getTitle(), source.getAuthor());
        bookDTO.setId(source.getId());
        bookDTO.setDescription(source.getDescription());
        bookDTO.setCreateAt(source.getCreateAt());
        bookDTO.setUpdateAt(source.getUpdateAt());
        bookDTO.setImage(source.getImage());
        bookDTO.setEnabled(source.isEnabled());
        bookDTO.setUserId(source.getUser().getId());

        return  bookDTO;
    }
}
