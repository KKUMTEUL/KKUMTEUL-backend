package com.kkumteul.domain.book.entity;


import com.kkumteul.domain.childprofile.entity.ChildProfile;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookLike {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private LikeType likeType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "child_profile_id")
    private ChildProfile childProfile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    private LocalDateTime updatedAt;

    @Builder
    public BookLike(LikeType likeType, ChildProfile childProfile, Book book, LocalDateTime updatedAt) {
        this.likeType = likeType;
        this.childProfile = childProfile;
        this.book = book;
        this.updatedAt = updatedAt;
    }

    public void updateLikeType(LikeType type){
        this.likeType = type;
    }

    public void updateUpdateAt(){
        this.updatedAt = LocalDateTime.now();
    }
}
