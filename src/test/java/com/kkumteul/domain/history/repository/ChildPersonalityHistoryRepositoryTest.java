package com.kkumteul.domain.history.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import com.kkumteul.domain.childprofile.entity.ChildProfile;
import com.kkumteul.domain.history.entity.ChildPersonalityHistory;
import com.kkumteul.domain.history.entity.HistoryCreatedType;
import com.kkumteul.domain.history.entity.MBTIScore;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class ChildPersonalityHistoryRepositoryTest {

    @Autowired
    ChildPersonalityHistoryRepository historyRepository;

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("히스토리 ID로 히스토리 조회 테스트")
    void testGetHistoryById() {
        MBTIScore mbtiScore = MBTIScore.builder()
                .iScore(10)
                .build();

        em.persist(mbtiScore);

        ChildPersonalityHistory history = ChildPersonalityHistory.builder()
                .createdAt(LocalDateTime.now())
                .historyCreatedType(HistoryCreatedType.FEEDBACK)
                .mbtiScore(mbtiScore)
                .build();

        historyRepository.save(history);

        Optional<ChildPersonalityHistory> result = historyRepository.findByIdWithMbtiScore(history.getId());

        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(history.getId());
        assertThat(result.get().getMbtiScore()).isNotNull();
    }
}