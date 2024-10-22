package com.kkumteul.domain.event.entity;

import com.kkumteul.domain.event.dto.EventRequestDto;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Event {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    private LocalDateTime startDate;
    private LocalDateTime expiredDate;

    @OneToMany(mappedBy = "event")
    List<JoinEvent> joinEventList = new ArrayList<>();

    @Builder
    public Event(String name, String description, LocalDateTime startDate, LocalDateTime expiredDate) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.expiredDate = expiredDate;
    }
}
