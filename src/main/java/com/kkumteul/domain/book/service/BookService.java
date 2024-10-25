package com.kkumteul.domain.book.service;

import com.kkumteul.domain.book.dto.GetBookDetailResponseDto;
import com.kkumteul.domain.book.dto.GetBookListResponseDto;
import com.kkumteul.domain.book.entity.LikeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {

    // 전체 도서 목록 조회
    Page<GetBookListResponseDto> getBookList(Pageable pageable);

    // 상세 도서 조회
    GetBookDetailResponseDto getBookDetail(final Long bookId);

    // 좋아요, 싫어요 처리
    void bookLike(Long bookId, Long childProfileId, LikeType likeType);
}
