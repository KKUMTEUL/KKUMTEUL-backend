package com.kkumteul.domain.event.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class EventRequestDto {
    private Long eventId;
    private String phoneNumber;
    private String name;

}
