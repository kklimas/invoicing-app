package com.server.filesystem.mapper;

import com.server.event.model.Event;
import com.server.filesystem.enums.CSVColumn;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.csv.CSVRecord;

import java.util.List;
import java.util.stream.StreamSupport;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CSVFileToEventsMapper {
    public static List<Event> map(Iterable<CSVRecord> csvRecords, Long id) {
        return StreamSupport.stream(csvRecords.spliterator(), false)
                .map(csvRecord -> {
                    var accountId = Long.parseLong(csvRecord.get(CSVColumn.ACCOUNT_ID));
                    var cash = Long.parseLong(csvRecord.get(CSVColumn.CASH));

                    return Event.builder()
                            .accountId(accountId)
                            .fileId(id)
                            .cash(cash)
                            .build();
                })
                .toList();
    }
}
