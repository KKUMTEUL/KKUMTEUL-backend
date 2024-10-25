package com.kkumteul.domain.mbti.entity;

import com.kkumteul.domain.book.entity.BookMBTI;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MBTI {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private MBTIName mbti;

    private String title;
    private String description;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] mbtiImage;

    @OneToMany(mappedBy = "mbti", fetch = FetchType.LAZY)
    private List<BookMBTI> bookMBTIList = new ArrayList<>();

    @Builder
    public MBTI(MBTIName mbti, String title, String description, byte[] mbtiImage, List<BookMBTI> bookMBTIS) {
        this.mbti = mbti;
        this.title = title;
        this.description = description;
        this.mbtiImage = mbtiImage;
        this.bookMBTIList = bookMBTIS;
    }
}
