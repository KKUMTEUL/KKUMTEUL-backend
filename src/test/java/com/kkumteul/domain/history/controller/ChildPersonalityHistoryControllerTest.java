package com.kkumteul.domain.history.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.kkumteul.domain.history.dto.ChildPersonalityHistoryDetailDto;
import com.kkumteul.domain.history.entity.MBTIScore;
import com.kkumteul.domain.history.service.ChildPersonalityHistoryService;
import com.kkumteul.domain.mbti.dto.MBTIPercentageDto;
import com.kkumteul.domain.mbti.entity.MBTI;
import com.kkumteul.domain.mbti.entity.MBTIName;
import com.kkumteul.domain.survey.dto.FavoriteDto;
import com.kkumteul.domain.survey.dto.MbtiDto;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ChildPersonalityHistoryController.class)
class ChildPersonalityHistoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChildPersonalityHistoryService historyService;

    @Test
    @DisplayName("히스토리 상세 조회 테스트")
    public void getHistoryDetailSuccess() throws Exception {
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

        List<FavoriteDto> favoriteGenresDto = List.of(
                new FavoriteDto("그림책", null),
                new FavoriteDto("동화", null)
        );

        List<FavoriteDto> favoriteTopicsDto = List.of(
                new FavoriteDto("과학", null),
                new FavoriteDto("역사", null)
        );

        ChildPersonalityHistoryDetailDto historyDetailDto = new ChildPersonalityHistoryDetailDto(
                MBTIPercentageDto.calculatePercentage(mbtiScore),
                new MbtiDto("INFJ", "Title", "Description", null),
                favoriteGenresDto,
                favoriteTopicsDto
        );

        given(historyService.getHistoryDetail(historyId)).willReturn(historyDetailDto);

        mockMvc.perform(get("/api/history/detail/{historyId}", historyId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response.mbtiResult.mbtiName").value("INFJ"))
                .andExpect(jsonPath("$.response.favoriteGenres[0].name").value("그림책"))
                .andExpect(jsonPath("$.response.favoriteTopics[0].name").value("과학"));
    }
}