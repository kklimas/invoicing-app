package com.server.event.db.model;

import com.server.event.enums.EventProcessingState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long fileId;
    private Long accountId;
    @Enumerated(EnumType.STRING)
    private EventProcessingState eventState;
    private String message;
    private Long cash;
}
