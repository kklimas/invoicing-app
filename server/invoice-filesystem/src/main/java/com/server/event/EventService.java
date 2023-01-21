package com.server.event;

import com.server.enums.OperationStatus;
import com.server.event.model.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;

    public List<Event> saveAll(List<Event> events) {
        return eventRepository.saveAll(events);
    }

    public List<Event> findAllByFileId(Long id) {
        return eventRepository.findEventsByFileId(id);
    }

    public void setDoneStatus(Long id) {
        setStatus(id, OperationStatus.DONE);
    }

    public void setFailedStatus(Long id) {
        setStatus(id, OperationStatus.FAILED);
    }

    private void setStatus(Long id, OperationStatus eventStatus) {
        var e = eventRepository.findById(id).orElseThrow();
        e.setEventStatus(eventStatus);
        eventRepository.save(e);
    }
}
