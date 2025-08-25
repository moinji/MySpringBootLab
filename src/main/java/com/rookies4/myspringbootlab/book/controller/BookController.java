package com.rookies4.myspringbootlab.book.controller;

import com.rookies4.myspringbootlab.book.dto.BookDTO;
import com.rookies4.myspringbootlab.book.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {
    
    private final BookService bookService;
    
    @PostMapping
    public ResponseEntity<BookDTO.BookResponse> createBook(@Valid @RequestBody BookDTO.BookCreateRequest request) {
        BookDTO.BookResponse response = bookService.createBook(request);
        URI location = URI.create("/api/books/" + response.getId());
        return ResponseEntity.created(location).body(response);
    }
    
    @GetMapping
    public ResponseEntity<List<BookDTO.BookResponse>> getAllBooks() {
        List<BookDTO.BookResponse> responses = bookService.getAllBooks();
        return ResponseEntity.ok(responses);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<BookDTO.BookResponse> getBookById(@PathVariable Long id) {
        BookDTO.BookResponse response = bookService.getBookById(id);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<BookDTO.BookResponse> getBookByIsbn(@PathVariable String isbn) {
        BookDTO.BookResponse response = bookService.getBookByIsbn(isbn);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/author")
    public ResponseEntity<List<BookDTO.BookResponse>> getBooksByAuthor(@RequestParam String author) {
        List<BookDTO.BookResponse> responses = bookService.getBooksByAuthor(author);
        return ResponseEntity.ok(responses);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<BookDTO.BookResponse> updateBook(
            @PathVariable Long id,
            @Valid @RequestBody BookDTO.BookUpdateRequest request
    ) {
        BookDTO.BookResponse response = bookService.updateBook(id, request);
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}