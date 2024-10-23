package com.kkumteul.domain.history.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import com.kkumteul.domain.history.dto.ChildPersonalityHistoryDetailDto;
import com.kkumteul.domain.history.entity.ChildPersonalityHistory;
import com.kkumteul.domain.history.entity.MBTIScore;
import com.kkumteul.domain.history.repository.ChildPersonalityHistoryRepository;
import com.kkumteul.domain.mbti.entity.MBTI;
import com.kkumteul.domain.mbti.entity.MBTIName;
import com.kkumteul.exception.HistoryNotFoundException;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ChildPersonalityHistoryServiceTest {

    @Mock
    private ChildPersonalityHistoryRepository historyRepository;

    @InjectMocks
    private ChildPersonalityHistoryService historyService;

    @Test
    @DisplayName("히스토리 상세 조회 테스트")
    public void getHistoryDetailSuccess() {
        Long historyId = 1L;

        MBTI mbti = MBTI.builder()
                .mbti(MBTIName.ENTJ)
                .title("title")
                .description("description")
                .mbtiImage(null)
                .build();

        MBTIScore mbtiScore = MBTIScore.builder()
                .iScore(3)
                .eScore(3)
                .sScore(4)
                .nScore(5)
                .fScore(2)
                .tScore(5)
                .pScore(4)
                .jScore(3)
                .mbti(mbti)
                .build();

        ChildPersonalityHistory history = mock(ChildPersonalityHistory.class);

        when(history.getMbtiScore()).thenReturn(mbtiScore);
        when(historyRepository.findByIdWithMbtiScore(historyId)).thenReturn(Optional.of(history));

        ChildPersonalityHistoryDetailDto result = historyService.getHistoryDetail(historyId);

        assertThat(result).isNotNull();
        assertThat(result.getMbtiPercentages()).isNotNull();
        assertThat(result.getMbtiResult()).isNotNull();

        verify(historyRepository, times(1)).findByIdWithMbtiScore(historyId);
    }

    @Test
    @DisplayName("히스토리 상세 조회 실패 테스트")
    public void getHistoryDetailFail() {
        Long historyId = 999L;
        when(historyRepository.findByIdWithMbtiScore(historyId)).thenReturn(Optional.empty());

        assertThrows(HistoryNotFoundException.class, () -> historyService.getHistoryDetail(historyId));

        verify(historyRepository, times(1)).findByIdWithMbtiScore(historyId);
    }
}