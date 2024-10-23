package com.kkumteul.domain.history.entity;


import com.kkumteul.domain.childprofile.entity.ChildProfile;
import com.kkumteul.domain.mbti.entity.MBTIScore;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.kkumteul.domain.childprofile.entity.GenreScore;
import com.kkumteul.domain.childprofile.entity.CumulativeMBTIScore;
import com.kkumteul.domain.childprofile.entity.TopicScore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChildPersonalityHistory {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "child_profile_id")
    private ChildProfile childProfile;

    private LocalDateTime createdAt;
    private boolean isDeleted;
    private LocalDateTime deletedAt;
    private HistoryCreatedType historyCreatedType;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "mbti_score_id")
    private MBTIScore mbtiScore;

    @OneToMany(mappedBy = "history", cascade = CascadeType.ALL, orphanRemoval = true)
    @BatchSize(size = 10)
    private final List<FavoriteGenre> favoriteGenres = new ArrayList<>();

    @OneToMany(mappedBy = "history", cascade = CascadeType.ALL, orphanRemoval = true)
    @BatchSize(size = 10)
    private final List<FavoriteTopic> favoriteTopics = new ArrayList<>();

    @Builder
    public ChildPersonalityHistory(ChildProfile childProfile, LocalDateTime createdAt, boolean isDeleted,
                                   LocalDateTime deletedAt, HistoryCreatedType historyCreatedType,
                                   MBTIScore mbtiScore) {
        this.childProfile = childProfile;
        this.createdAt = createdAt;
        this.isDeleted = isDeleted;
        this.deletedAt = deletedAt;
        this.historyCreatedType = historyCreatedType;
        this.mbtiScore = mbtiScore;
    }

    public void addFavoriteGenre(FavoriteGenre favoriteGenre) {
        favoriteGenres.add(favoriteGenre);
        favoriteGenre.setHistory(this);
    }

    public void setChildProfile(ChildProfile childProfile) {
        this.childProfile = childProfile;
    }

    public void addFavoriteTopic(FavoriteTopic favoriteTopic) {
        favoriteTopics.add(favoriteTopic);
        favoriteTopic.setHistory(this);
    }

    public List<FavoriteGenre> getFavoriteGenres() {
        return Collections.unmodifiableList(favoriteGenres);
    }

    public List<FavoriteTopic> getFavoriteTopics() {
        return Collections.unmodifiableList(favoriteTopics);
    }
}
