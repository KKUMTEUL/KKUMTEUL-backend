package com.kkumteul.domain.book.service.impl;

import com.kkumteul.domain.book.dto.GetBookDetailResponseDto;
import com.kkumteul.domain.book.dto.GetBookListResponseDto;
import com.kkumteul.domain.book.entity.Book;
import com.kkumteul.domain.book.entity.BookLike;
import com.kkumteul.domain.book.entity.LikeType;
import com.kkumteul.domain.book.exception.BookNotFoundException;
import com.kkumteul.domain.book.repository.BookLikeRepository;
import com.kkumteul.domain.book.repository.BookRepository;
import com.kkumteul.domain.book.service.BookService;
import com.kkumteul.domain.childprofile.repository.ChildProfileRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
@Transactional
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookLikeRepository bookLikeRepository;
    private final ChildProfileRepository childProfileRepository;

    // 전체 도서 목록 조회
    @Override
    public Page<GetBookListResponseDto> getBookList(final Pageable pageable) {
        final Page<Book> books = bookRepository.findAllBookInfo(pageable);

        return books.map(GetBookListResponseDto::from);
    }

    @Override
    public GetBookDetailResponseDto getBookDetail(final Long bookId) {

        final Book book = bookRepository.findById(bookId)
                .orElseThrow(BookNotFoundException::new);

        return GetBookDetailResponseDto.from(book);
    }

    @Override
    @Transactional
    public void bookLike(final Long bookId, final Long childProfileId, final LikeType likeType) {
        final Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("책을 찾을 수 없습니다."));

        // 1. 도서에 좋아요/싫어요 버튼 상태 확인
        Optional<BookLike> existLike = bookLikeRepository.findByChildProfileAndBook(childProfileId, bookId);

        if (existLike.isPresent()) {
            // 2-1. 이미 있는 상태 확인
            BookLike bookLike = existLike.get();

            if (bookLike.getLikeType() == likeType) {
                throw new RuntimeException(likeType == LikeType.LIKE ? "이미 좋아요를 눌렀습니다." : "이미 싫어요를 눌렀습니다.");
            } else {
            // 2-2. 상태 변경
                bookLike.updateLikeType(likeType);
                bookLike.updateUpdateAt();

                bookLikeRepository.save(bookLike);
            }
        } else {
            // 3. 처음 누를 때
            BookLike booklike = BookLike.builder()
                    .book(book)
                    .childProfile(childProfileRepository.findById(childProfileId)
                            .orElseThrow(() -> new RuntimeException("자녀를 찾을 수 없습니다.")))
                    .likeType(likeType)
                    .updatedAt(LocalDateTime.now())
                    .build();

            bookLikeRepository.save(booklike);
        }
    }
}
