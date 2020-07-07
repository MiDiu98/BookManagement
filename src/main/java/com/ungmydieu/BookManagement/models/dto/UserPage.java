package com.ungmydieu.bookmanagement.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPage {
    List<UserDTO> usersDto;
    int currentPage;
    int totalPages;
}
