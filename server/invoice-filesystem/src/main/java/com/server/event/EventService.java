package com.server.event;

import com.server.enums.OperationStatus;
import com.server.event.db.model.Event;
import com.server.event.db.repository.EventRepository;
import com.server.event.dto.ResponseEventDTO;
import com.server.event.enums.EventProcessingState;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;

    public List<Event> saveProcessingEvents(List<Event> events) {
        events.forEach(e -> e.setEventState(EventProcessingState.PROCESSING));
        return eventRepository.saveAll(events);
    }

    public List<Event> findEventsByEventId(Long id) {
        return eventRepository.findEventsByFileId(id);
    }
    public void setDoneStatus(ResponseEventDTO eventDTO) {
        setStatus(eventDTO, EventProcessingState.DONE);
    }

    public void setFailedStatus(ResponseEventDTO eventDTO) {
        setStatus(eventDTO, EventProcessingState.FAILED);
    }

    private void setStatus(ResponseEventDTO eventDTO, EventProcessingState eventStatus) {
        var e = eventRepository.findById(eventDTO.getId()).orElseThrow();
        e.setEventState(eventStatus);
        if (EventProcessingState.FAILED == eventStatus) {
            e.setMessage(eventDTO.getMessage());
        }
        eventRepository.save(e);
    }
}
