package mylab.book.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookRequest {
    
    @NotBlank
    private String title;
    
    @NotBlank
    private String author;
    
    @NotBlank
    private String isbn;
    
    @Positive
    private Integer price;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate publishDate;
}