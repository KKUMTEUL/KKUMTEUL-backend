package com.kkumteul.domain.book.dto;

import com.kkumteul.domain.book.entity.LikeType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostBookLikeRequestDto {
    private final Long bookId;
    private final Long childProfileId;
    private final LikeType likeType;
}

