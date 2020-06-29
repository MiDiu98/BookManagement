package com.ungmydieu.bookmanagement.models.dto;

import lombok.Data;

import java.util.List;

@Data
public class BookPage {
    List<BookDTO> booksDto;
    int currentPage;
    int totalPages;
}
