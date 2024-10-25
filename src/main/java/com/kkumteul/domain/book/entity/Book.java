package com.kkumteul.domain.book.entity;


import com.kkumteul.domain.personality.entity.Genre;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;
    private String publisher;
    private String price;
    private String page;
    private String age_group;

    @Column(columnDefinition = "TEXT")
    private String summary;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] bookImage;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Genre genre;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<BookTopic> bookTopics = new ArrayList<>();

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<BookMBTI> bookMBTIS = new ArrayList<>();


    @Builder
    public Book(String title, String author, String publisher, String price, String page, String age_group,
                String summary, byte[] bookImage, Genre genre, List<BookTopic> bookTopics, List<BookMBTI> bookMBTIS) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.price = price;
        this.page = page;
        this.age_group = age_group;
        this.summary = summary;
        this.bookImage = bookImage;
        this.genre = genre;
        this.bookTopics = bookTopics;
        this.bookMBTIS = bookMBTIS;
    }
}
