package com.server.event.db.repository;

import com.server.event.db.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findEventsByFileId(Long fileId);
}
