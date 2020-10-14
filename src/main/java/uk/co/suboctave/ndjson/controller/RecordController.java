package uk.co.suboctave.ndjson.controller;

import uk.co.suboctave.ndjson.dto.Record;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/record")
public class RecordController {

    @GetMapping(value = "/all", produces = "application/x-ndjson")
    public Flux<Record> allRecords() {
        List records = new ArrayList();
        for (int i = 0; i < 5; i++) {
            records.add(new Record("Record-" + i));
        }
        return Flux.fromIterable(records);
    }

}
