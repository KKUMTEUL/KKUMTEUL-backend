package com.kkumteul.domain.book.repository;

import com.kkumteul.domain.book.entity.Book;
import com.kkumteul.domain.book.entity.BookTopic;
import com.kkumteul.domain.book.repository.BookRepository;
import com.kkumteul.domain.personality.entity.Topic;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private EntityManager em;

    @BeforeEach
    void setUp() throws IOException {
        File bookImageFile = new File("src/test/resources/images/book_1.jpg");
        byte[] bookImage = Files.readAllBytes(bookImageFile.toPath());

        File topicImageFile = new File("src/test/resources/images/topic_1.jpg");
        byte[] topicImage = Files.readAllBytes(topicImageFile.toPath());

        Topic topic = Topic.builder()
                .name("환경")
                .image(topicImage)
                .bookTopics(new ArrayList<>())
                .build();

        Book book = Book.builder()
                .title("책 1")
                .author("저자 1")
                .publisher("출판사 1")
                .price("12000")
                .page("143")
                .summary("테스트 줄거리")
                .bookImage(bookImage)
                .bookTopics(new ArrayList<>())
                .build();

        BookTopic bookTopic = BookTopic.builder()
                .book(book)
                .topic(topic)
                .build();

        // 관계 설정
        book.getBookTopics().add(bookTopic);
        topic.getBookTopics().add(bookTopic);

        // 엔티티 저장
        em.persist(topic);
        em.persist(book);
        em.persist(bookTopic);
        em.flush();
    }

    @Test
    @DisplayName("전체 도서 반환: 요청 시 책 목록 Page 당 12개씩 반환한다.")
    void testFindAllBookInfo() {
        Pageable pageable = PageRequest.of(0, 12);
        Page<Book> result = bookRepository.findAllBookInfo(pageable);

        assertThat(result.getTotalElements()).isGreaterThan(0);
        assertThat(result.getContent()).isNotEmpty();
        assertThat(result.getContent().get(0).getTitle()).isEqualTo("책 1");
    }
}
