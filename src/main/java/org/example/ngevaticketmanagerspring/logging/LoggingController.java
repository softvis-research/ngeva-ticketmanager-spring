package org.example.ngevaticketmanagerspring.logging;

import java.util.List;

import org.example.ngevaticketmanagerspring.logging.excel.LogsExcelWriter;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.example.ngevaticketmanagerspring.logging.excel.DownloadHelper.createSheetDownload;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/logs", produces = MediaType.APPLICATION_JSON_VALUE)
public class LoggingController {

    private final EventLogger eventLogger;

    @GetMapping()
    public ResponseEntity<List<String>> getLogs() {
        final List<String> logs = eventLogger.getEventLogs();
        return ResponseEntity.ok(logs);
    }

    @GetMapping(path = "/download", produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    public ResponseEntity<Resource> downloadAsExcel() {

        final List<String> logs = eventLogger.getEventLogs();
        LogsExcelWriter excelWriter = new LogsExcelWriter(logs.stream().toList(), "exceltemplate.xlsx");
        return createSheetDownload(excelWriter);
    }
}
