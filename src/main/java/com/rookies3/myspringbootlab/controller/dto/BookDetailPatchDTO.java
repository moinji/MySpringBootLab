package com.rookies3.myspringbootlab.controller.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDetailPatchDTO {
    
    private String description;
    
    private String language;
    
    private Integer pageCount;
    
    private String publisher;
    
    private String coverImageUrl;
    
    private String edition;
}