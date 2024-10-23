package com.kkumteul.domain.book.repository;

import com.kkumteul.domain.book.entity.BookTopic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookTopicRepository extends JpaRepository<BookTopic, Long> {
}