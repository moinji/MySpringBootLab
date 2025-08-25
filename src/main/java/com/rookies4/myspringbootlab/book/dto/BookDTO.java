package com.rookies4.myspringbootlab.book.dto;

import com.rookies4.myspringbootlab.book.entity.Book;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

public class BookDTO {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BookCreateRequest {
        
        @NotBlank(message = "제목은 필수 입력값입니다.")
        private String title;
        
        @NotBlank(message = "저자는 필수 입력값입니다.")
        private String author;
        
        @NotBlank(message = "ISBN은 필수 입력값입니다.")
        @Pattern(regexp = "^\\d{10}(\\d{3})?$", message = "ISBN은 10자리 또는 13자리 숫자여야 합니다.")
        private String isbn;
        
        @NotNull(message = "가격은 필수 입력값입니다.")
        @Positive(message = "가격은 양수여야 합니다.")
        private Integer price;
        
        @NotNull(message = "출간일은 필수 입력값입니다.")
        private LocalDate publishDate;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BookUpdateRequest {
        
        private String title;
        
        private String author;
        
        @Pattern(regexp = "^\\d{10}(\\d{3})?$", message = "ISBN은 10자리 또는 13자리 숫자여야 합니다.")
        private String isbn;
        
        @Positive(message = "가격은 양수여야 합니다.")
        private Integer price;
        
        private LocalDate publishDate;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BookResponse {
        
        private Long id;
        private String title;
        private String author;
        private String isbn;
        private Integer price;
        private LocalDate publishDate;
        
        public static BookResponse of(Book book) {
            return new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn(),
                book.getPrice(),
                book.getPublishDate()
            );
        }
    }
}