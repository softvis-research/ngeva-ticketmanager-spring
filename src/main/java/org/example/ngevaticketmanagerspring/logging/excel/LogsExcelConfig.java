package org.example.ngevaticketmanagerspring.logging.excel;

import lombok.Getter;

/**
 * Konfiguration für das Schreiben von Excel-Arbeitsmappen für TardyUsersSummaries
 */

@Getter
public enum LogsExcelConfig {

    TIMESTAMP(0, "Timestamp"),
    EVENT(1, "Event"),
    STATUS(2, "Status"),
    MESSAGE(3, "Message");

    private final int column;

    private final String header;

    LogsExcelConfig(int column, final String header) {
        this.column = column;
        this.header = header;
    }
}
