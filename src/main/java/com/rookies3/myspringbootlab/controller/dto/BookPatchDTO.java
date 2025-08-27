package com.rookies3.myspringbootlab.controller.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookPatchDTO {
    
    private String title;
    
    private String author;
    
    private String isbn;
    
    private Integer price;
    
    private LocalDate publishDate;
    
    private BookDetailPatchDTO detailRequest;
}