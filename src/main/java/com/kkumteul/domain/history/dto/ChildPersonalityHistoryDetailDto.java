package com.kkumteul.domain.history.dto;

import com.kkumteul.domain.mbti.dto.MBTIPercentageDto;
import com.kkumteul.domain.survey.dto.FavoriteDto;
import com.kkumteul.domain.survey.dto.MbtiDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChildPersonalityHistoryDetailDto {
    MBTIPercentageDto mbtiPercentages;
    private MbtiDto mbtiResult;
    private List<FavoriteDto> favoriteGenres;
    private List<FavoriteDto> favoriteTopics;
}
