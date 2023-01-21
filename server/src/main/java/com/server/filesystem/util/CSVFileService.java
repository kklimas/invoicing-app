package com.server.filesystem.util;

import com.server.event.db.model.Event;
import com.server.filesystem.enums.CSVColumn;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.server.filesystem.mapper.CSVFileToEventsMapper.map;

@Service
public class CSVFileService {

    public List<Event> getEventsFromFile(InputStream is) throws IOException {
        var fileReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));

        var csvFormat = CSVFormat.Builder.create()
                .setHeader(CSVColumn.class)
                .setTrim(true)
                .setSkipHeaderRecord(true)
                .setIgnoreEmptyLines(true)
                .build();

        var csvParser = new CSVParser(fileReader, csvFormat);
        return map(csvParser.getRecords());
    }
}
