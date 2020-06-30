package com.ungmydieu.bookmanagement.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookPage {
    List<BookDTO> booksDto;
    int currentPage;
    int totalPages;
}
