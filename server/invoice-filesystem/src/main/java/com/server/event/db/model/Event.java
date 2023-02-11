package com.server.event.model;

import com.server.enums.OperationStatus;
import com.server.filesystem.db.model.StorageFile;
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
    @ManyToOne
    @JoinColumn(name = "file_id")
    private StorageFile storageFile;
    private Long accountId;
    @Enumerated(EnumType.STRING)
    private OperationStatus eventStatus;
    private Long cash;
}
