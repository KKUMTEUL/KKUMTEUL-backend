package com.kkumteul.domain.history.controller;

import com.kkumteul.domain.history.dto.ChildPersonalityHistoryDetailDto;
import com.kkumteul.domain.history.service.ChildPersonalityHistoryService;
import com.kkumteul.util.ApiUtil;
import com.kkumteul.util.ApiUtil.ApiSuccess;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/history")
public class ChildPersonalityHistoryController {

    private final ChildPersonalityHistoryService historyService;

    @GetMapping("/detail/{historyId}")
    public ApiSuccess<?> getHistoryDetail(@PathVariable(name = "historyId") Long historyId) {
        ChildPersonalityHistoryDetailDto historyDetail = historyService.getHistoryDetail(historyId);

        return ApiUtil.success(historyDetail);
    }
}
