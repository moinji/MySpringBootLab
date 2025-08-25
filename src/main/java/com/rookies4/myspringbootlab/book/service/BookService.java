package com.rookies4.myspringbootlab.book.service;

import com.rookies4.myspringbootlab.book.dto.BookDTO;
import com.rookies4.myspringbootlab.book.entity.Book;
import com.rookies4.myspringbootlab.book.repository.BookRepository;
import com.rookies4.myspringbootlab.common.error.BusinessException;
import com.rookies4.myspringbootlab.common.error.ErrorObject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookService {
    
    private final BookRepository bookRepository;
    
    @Transactional
    public BookDTO.BookResponse createBook(BookDTO.BookCreateRequest request) {
        Book book = Book.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .isbn(request.getIsbn())
                .price(request.getPrice())
                .publishDate(request.getPublishDate())
                .build();
        
        Book savedBook = bookRepository.save(book);
        return toBookResponse(savedBook);
    }
    
    public List<BookDTO.BookResponse> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .map(this::toBookResponse)
                .collect(Collectors.toList());
    }
    
    public BookDTO.BookResponse getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BusinessException(
                        new ErrorObject("BOOK_NOT_FOUND", "해당 ID의 도서를 찾을 수 없습니다.", 404)
                ));
        
        return toBookResponse(book);
    }
    
    public BookDTO.BookResponse getBookByIsbn(String isbn) {
        Book book = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BusinessException(
                        new ErrorObject("BOOK_NOT_FOUND", "해당 ISBN의 도서를 찾을 수 없습니다.", 404)
                ));
        
        return toBookResponse(book);
    }
    
    public List<BookDTO.BookResponse> getBooksByAuthor(String author) {
        List<Book> books = bookRepository.findByAuthor(author);
        return books.stream()
                .map(this::toBookResponse)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public BookDTO.BookResponse updateBook(Long id, BookDTO.BookUpdateRequest request) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BusinessException(
                        new ErrorObject("BOOK_NOT_FOUND", "해당 ID의 도서를 찾을 수 없습니다.", 404)
                ));
        
        if (request.getTitle() != null) {
            book.setTitle(request.getTitle());
        }
        if (request.getAuthor() != null) {
            book.setAuthor(request.getAuthor());
        }
        if (request.getIsbn() != null) {
            book.setIsbn(request.getIsbn());
        }
        if (request.getPrice() != null) {
            book.setPrice(request.getPrice());
        }
        if (request.getPublishDate() != null) {
            book.setPublishDate(request.getPublishDate());
        }
        
        Book updatedBook = bookRepository.save(book);
        return toBookResponse(updatedBook);
    }
    
    @Transactional
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new BusinessException(
                    new ErrorObject("BOOK_NOT_FOUND", "해당 ID의 도서를 찾을 수 없습니다.", 404)
            );
        }
        
        bookRepository.deleteById(id);
    }
    
    private BookDTO.BookResponse toBookResponse(Book book) {
        return BookDTO.BookResponse.of(book);
    }
}