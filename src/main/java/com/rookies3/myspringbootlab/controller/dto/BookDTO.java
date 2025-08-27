package com.rookies3.myspringbootlab.controller.dto;

import com.rookies3.myspringbootlab.entity.Book;
import com.rookies3.myspringbootlab.entity.BookDetail;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

public class BookDTO {
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {
        
        @NotBlank(message = "제목은 필수 입력값입니다.")
        private String title;
        
        @NotBlank(message = "저자는 필수 입력값입니다.")
        private String author;
        
        @NotBlank(message = "ISBN은 필수 입력값입니다.")
        private String isbn;
        
        @NotNull(message = "가격은 필수 입력값입니다.")
        @Positive(message = "가격은 양수여야 합니다.")
        private Integer price;
        
        @NotNull(message = "출간일은 필수 입력값입니다.")
        private LocalDate publishDate;
        
        @Valid
        private BookDetailRequest detailRequest;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class BookDetailRequest {
        
        private String description;
        
        private String language;
        
        private Integer pageCount;
        
        private String publisher;
        
        private String coverImageUrl;
        
        private String edition;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        
        private Long id;
        private String title;
        private String author;
        private String isbn;
        private Integer price;
        private LocalDate publishDate;
        private BookDetailResponse detail;
        
        public static Response fromEntity(Book book) {
            BookDetailResponse detailResponse = null;
            if (book.getBookDetail() != null) {
                detailResponse = BookDetailResponse.fromEntity(book.getBookDetail());
            }
            
            return Response.builder()
                    .id(book.getId())
                    .title(book.getTitle())
                    .author(book.getAuthor())
                    .isbn(book.getIsbn())
                    .price(book.getPrice())
                    .publishDate(book.getPublishDate())
                    .detail(detailResponse)
                    .build();
        }
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class BookDetailResponse {
        
        private Long id;
        private String description;
        private String language;
        private Integer pageCount;
        private String publisher;
        private String coverImageUrl;
        private String edition;
        
        public static BookDetailResponse fromEntity(BookDetail bookDetail) {
            return BookDetailResponse.builder()
                    .id(bookDetail.getId())
                    .description(bookDetail.getDescription())
                    .language(bookDetail.getLanguage())
                    .pageCount(bookDetail.getPageCount())
                    .publisher(bookDetail.getPublisher())
                    .coverImageUrl(bookDetail.getCoverImageUrl())
                    .edition(bookDetail.getEdition())
                    .build();
        }
    }
}