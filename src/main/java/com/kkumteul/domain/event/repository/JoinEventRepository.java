package com.kkumteul.domain.event.repository;

import com.kkumteul.domain.event.entity.Event;
import com.kkumteul.domain.event.entity.JoinEvent;
import com.kkumteul.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface JoinEventRepository extends JpaRepository<JoinEvent, Long> {
    boolean existsByUserAndEvent(User user, Event event);
}
