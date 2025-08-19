package mylab.book.api.dto;

import mylab.book.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookResponse {
    
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