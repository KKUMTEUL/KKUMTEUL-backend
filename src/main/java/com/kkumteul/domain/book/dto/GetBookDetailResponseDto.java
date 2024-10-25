package com.kkumteul.domain.book.dto;

import com.kkumteul.domain.book.entity.Book;
import com.kkumteul.domain.book.entity.BookTopic;
import com.kkumteul.domain.personality.entity.Genre;
import com.kkumteul.domain.personality.entity.Topic;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class GetBookDetailResponseDto {
    private Long bookId;
    private String bookTitle;
    private String bookAuthor;
    private byte[] bookImage;
    private String mbtiInfo;
    private String bookSummary;
    private String genreName;
    private List<String> topicNames;
    private String age_group;
    private String bookPage;
    private String publisher;

    public static GetBookDetailResponseDto from(final Book book){
        final GetBookDetailResponseDto bookDetailResponseDto = new GetBookDetailResponseDto();

        bookDetailResponseDto.bookId = book.getId();
        bookDetailResponseDto.bookTitle = book.getTitle();
        bookDetailResponseDto.bookAuthor = book.getAuthor();
        bookDetailResponseDto.bookImage = book.getBookImage();
        bookDetailResponseDto.bookSummary = book.getSummary();
        Genre genre = book.getGenre();
        bookDetailResponseDto.genreName = genre.getName();
        bookDetailResponseDto.topicNames = book.getBookTopics().stream()
                .map(BookTopic::getTopic)
                .map(Topic::getName)
                .toList();
        bookDetailResponseDto.age_group = book.getAge_group();
        bookDetailResponseDto.bookPage = book.getPage();
        bookDetailResponseDto.publisher = book.getPublisher();
        if (!book.getBookMBTIS().isEmpty()) {
            bookDetailResponseDto.mbtiInfo = book.getBookMBTIS().get(0).getMbti().getTitle();
        } else {
            bookDetailResponseDto.mbtiInfo = null; // 아직 모든 팩에 mapping 이 안 되어 있으므로 MBTI 가 없으면 null
        }

        return bookDetailResponseDto;
    }


}
