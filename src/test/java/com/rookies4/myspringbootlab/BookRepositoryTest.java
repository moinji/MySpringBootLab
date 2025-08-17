package com.rookies4.myspringbootlab;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void testCreateBook() {
        Book book = Book.builder()
                .title("스프링 부트 입문")
                .author("홍길동")
                .isbn("9788956746425")
                .price(30000)
                .publishDate(LocalDate.of(2025, 5, 7))
                .build();

        Book savedBook = bookRepository.save(book);

        assertThat(savedBook).isNotNull();
        assertThat(savedBook.getId()).isNotNull();
        assertThat(savedBook.getTitle()).isEqualTo("스프링 부트 입문");
        assertThat(savedBook.getAuthor()).isEqualTo("홍길동");
        assertThat(savedBook.getIsbn()).isEqualTo("9788956746425");
        assertThat(savedBook.getPrice()).isEqualTo(30000);
        assertThat(savedBook.getPublishDate()).isEqualTo(LocalDate.of(2025, 5, 7));
    }

    @Test
    public void testFindByIsbn() {
        Book book = Book.builder()
                .title("스프링 부트 입문")
                .author("홍길동")
                .isbn("9788956746425")
                .price(30000)
                .publishDate(LocalDate.of(2025, 5, 7))
                .build();

        bookRepository.save(book);

        Optional<Book> foundBook = bookRepository.findByIsbn("9788956746425");

        assertThat(foundBook).isPresent();
        assertThat(foundBook.get().getTitle()).isEqualTo("스프링 부트 입문");
        assertThat(foundBook.get().getAuthor()).isEqualTo("홍길동");
    }

    @Test
    public void testFindByAuthor() {
        Book book1 = Book.builder()
                .title("스프링 부트 입문")
                .author("홍길동")
                .isbn("9788956746425")
                .price(30000)
                .publishDate(LocalDate.of(2025, 5, 7))
                .build();

        Book book2 = Book.builder()
                .title("JPA 프로그래밍")
                .author("박둘리")
                .isbn("9788956746432")
                .price(35000)
                .publishDate(LocalDate.of(2025, 4, 30))
                .build();

        bookRepository.save(book1);
        bookRepository.save(book2);

        List<Book> booksByHong = bookRepository.findByAuthor("홍길동");
        List<Book> booksByPark = bookRepository.findByAuthor("박둘리");

        assertThat(booksByHong).hasSize(1);
        assertThat(booksByHong.get(0).getTitle()).isEqualTo("스프링 부트 입문");

        assertThat(booksByPark).hasSize(1);
        assertThat(booksByPark.get(0).getTitle()).isEqualTo("JPA 프로그래밍");
    }

    @Test
    public void testUpdateBook() {
        Book book = Book.builder()
                .title("스프링 부트 입문")
                .author("홍길동")
                .isbn("9788956746425")
                .price(30000)
                .publishDate(LocalDate.of(2025, 5, 7))
                .build();

        Book savedBook = bookRepository.save(book);

        savedBook.setPrice(35000);
        savedBook.setTitle("스프링 부트 마스터");

        Book updatedBook = bookRepository.save(savedBook);

        assertThat(updatedBook.getPrice()).isEqualTo(35000);
        assertThat(updatedBook.getTitle()).isEqualTo("스프링 부트 마스터");
        assertThat(updatedBook.getId()).isEqualTo(savedBook.getId());
    }

    @Test
    public void testDeleteBook() {
        Book book = Book.builder()
                .title("스프링 부트 입문")
                .author("홍길동")
                .isbn("9788956746425")
                .price(30000)
                .publishDate(LocalDate.of(2025, 5, 7))
                .build();

        Book savedBook = bookRepository.save(book);
        Long bookId = savedBook.getId();

        bookRepository.delete(savedBook);

        Optional<Book> deletedBook = bookRepository.findById(bookId);
        assertThat(deletedBook).isEmpty();
    }
}