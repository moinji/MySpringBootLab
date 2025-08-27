package com.rookies3.myspringbootlab.repository;

import com.rookies3.myspringbootlab.entity.Book;
import com.rookies3.myspringbootlab.entity.BookDetail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class BookRepositoryTest {
    
    @Autowired
    private TestEntityManager entityManager;
    
    @Autowired
    private BookRepository bookRepository;
    
    @Autowired
    private BookDetailRepository bookDetailRepository;
    
    private Book testBook;
    private BookDetail testBookDetail;
    
    @BeforeEach
    void setUp() {
        testBook = Book.builder()
                .title("Clean Code")
                .author("Robert C. Martin")
                .isbn("9780132350884")
                .price(35000)
                .publishDate(LocalDate.of(2008, 8, 1))
                .build();
        
        testBookDetail = BookDetail.builder()
                .description("A Handbook of Agile Software Craftsmanship")
                .language("English")
                .pageCount(464)
                .publisher("Prentice Hall")
                .coverImageUrl("http://example.com/clean-code.jpg")
                .edition("1st Edition")
                .book(testBook)
                .build();
        
        testBook.setBookDetail(testBookDetail);
        
        entityManager.persistAndFlush(testBook);
        entityManager.clear();
    }
    
    @Test
    @DisplayName("도서 생성 및 조회 테스트")
    void testCreateAndFindBook() {
        Optional<Book> found = bookRepository.findById(testBook.getId());
        
        assertThat(found).isPresent();
        assertThat(found.get().getTitle()).isEqualTo("Clean Code");
        assertThat(found.get().getAuthor()).isEqualTo("Robert C. Martin");
        assertThat(found.get().getIsbn()).isEqualTo("9780132350884");
    }
    
    @Test
    @DisplayName("ISBN으로 도서 조회 테스트")
    void testFindByIsbn() {
        Optional<Book> found = bookRepository.findByIsbn("9780132350884");
        
        assertThat(found).isPresent();
        assertThat(found.get().getTitle()).isEqualTo("Clean Code");
    }
    
    @Test
    @DisplayName("작가명으로 도서 검색 테스트")
    void testFindByAuthorContainingIgnoreCase() {
        List<Book> books = bookRepository.findByAuthorContainingIgnoreCase("Robert");
        
        assertThat(books).hasSize(1);
        assertThat(books.get(0).getAuthor()).isEqualTo("Robert C. Martin");
        
        List<Book> booksLowerCase = bookRepository.findByAuthorContainingIgnoreCase("robert");
        assertThat(booksLowerCase).hasSize(1);
    }
    
    @Test
    @DisplayName("제목으로 도서 검색 테스트")
    void testFindByTitleContainingIgnoreCase() {
        List<Book> books = bookRepository.findByTitleContainingIgnoreCase("Clean");
        
        assertThat(books).hasSize(1);
        assertThat(books.get(0).getTitle()).isEqualTo("Clean Code");
        
        List<Book> booksLowerCase = bookRepository.findByTitleContainingIgnoreCase("code");
        assertThat(booksLowerCase).hasSize(1);
    }
    
    @Test
    @DisplayName("ID로 도서와 상세정보 함께 조회 테스트")
    void testFindByIdWithBookDetail() {
        Optional<Book> found = bookRepository.findByIdWithBookDetail(testBook.getId());
        
        assertThat(found).isPresent();
        Book book = found.get();
        assertThat(book.getTitle()).isEqualTo("Clean Code");
        assertThat(book.getBookDetail()).isNotNull();
        assertThat(book.getBookDetail().getDescription()).isEqualTo("A Handbook of Agile Software Craftsmanship");
        assertThat(book.getBookDetail().getPublisher()).isEqualTo("Prentice Hall");
    }
    
    @Test
    @DisplayName("ISBN으로 도서와 상세정보 함께 조회 테스트")
    void testFindByIsbnWithBookDetail() {
        Optional<Book> found = bookRepository.findByIsbnWithBookDetail("9780132350884");
        
        assertThat(found).isPresent();
        Book book = found.get();
        assertThat(book.getTitle()).isEqualTo("Clean Code");
        assertThat(book.getBookDetail()).isNotNull();
        assertThat(book.getBookDetail().getLanguage()).isEqualTo("English");
        assertThat(book.getBookDetail().getPageCount()).isEqualTo(464);
    }
    
    @Test
    @DisplayName("ISBN 존재 여부 확인 테스트")
    void testExistsByIsbn() {
        boolean exists = bookRepository.existsByIsbn("9780132350884");
        assertThat(exists).isTrue();
        
        boolean notExists = bookRepository.existsByIsbn("1234567890");
        assertThat(notExists).isFalse();
    }
    
    @Test
    @DisplayName("BookDetail을 통한 Book ID로 조회 테스트")
    void testFindByBookId() {
        Optional<BookDetail> found = bookDetailRepository.findByBookId(testBook.getId());
        
        assertThat(found).isPresent();
        assertThat(found.get().getDescription()).isEqualTo("A Handbook of Agile Software Craftsmanship");
        assertThat(found.get().getBook().getTitle()).isEqualTo("Clean Code");
    }
    
    @Test
    @DisplayName("BookDetail ID로 Book과 함께 조회 테스트")
    void testFindByIdWithBook() {
        Optional<BookDetail> found = bookDetailRepository.findByIdWithBook(testBookDetail.getId());
        
        assertThat(found).isPresent();
        BookDetail detail = found.get();
        assertThat(detail.getDescription()).isEqualTo("A Handbook of Agile Software Craftsmanship");
        assertThat(detail.getBook()).isNotNull();
        assertThat(detail.getBook().getTitle()).isEqualTo("Clean Code");
    }
    
    @Test
    @DisplayName("출판사로 BookDetail 검색 테스트")
    void testFindByPublisher() {
        List<BookDetail> details = bookDetailRepository.findByPublisher("Prentice Hall");
        
        assertThat(details).hasSize(1);
        assertThat(details.get(0).getPublisher()).isEqualTo("Prentice Hall");
        assertThat(details.get(0).getBook().getTitle()).isEqualTo("Clean Code");
    }
}