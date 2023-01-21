package com.server.event.model;

import com.server.enums.OperationStatus;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long fileId;
    private Long accountId;
    @Enumerated(EnumType.STRING)
    private OperationStatus eventStatus;
    private Long cash;
}
