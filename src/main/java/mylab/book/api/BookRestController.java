package mylab.book.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mylab.book.api.dto.BookRequest;
import mylab.book.api.dto.BookResponse;
import mylab.book.entity.Book;
import mylab.book.repository.BookRepository;
import mylab.common.error.BusinessException;
import mylab.common.error.ErrorObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookRestController {
    
    private final BookRepository bookRepository;
    
    @PostMapping
    public ResponseEntity<BookResponse> createBook(@Valid @RequestBody BookRequest request) {
        Book book = new Book(
            null,
            request.getTitle(),
            request.getAuthor(),
            request.getIsbn(),
            request.getPublishDate(),
            request.getPrice()
        );
        
        Book savedBook = bookRepository.save(book);
        URI location = URI.create("/api/books/" + savedBook.getId());
        
        return ResponseEntity.created(location).body(BookResponse.of(savedBook));
    }
    
    @GetMapping
    public ResponseEntity<List<BookResponse>> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        List<BookResponse> responses = books.stream()
            .map(BookResponse::of)
            .toList();
        
        return ResponseEntity.ok(responses);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getBookById(@PathVariable Long id) {
        return bookRepository.findById(id)
            .map(book -> ResponseEntity.ok(BookResponse.of(book)))
            .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/isbn/{isbn}")
    public BookResponse getBookByIsbn(@PathVariable String isbn) {
        Book book = bookRepository.findByIsbn(isbn)
            .orElseThrow(() -> new BusinessException(
                new ErrorObject("BOOK_NOT_FOUND", "해당 ISBN의 도서를 찾을 수 없습니다.", 404)
            ));
        
        return BookResponse.of(book);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> updateBook(
        @PathVariable Long id,
        @Valid @RequestBody BookRequest request
    ) {
        return bookRepository.findById(id)
            .map(book -> {
                book.setTitle(request.getTitle());
                book.setAuthor(request.getAuthor());
                book.setIsbn(request.getIsbn());
                book.setPrice(request.getPrice());
                book.setPublishDate(request.getPublishDate());
                
                Book updatedBook = bookRepository.save(book);
                return ResponseEntity.ok(BookResponse.of(updatedBook));
            })
            .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}