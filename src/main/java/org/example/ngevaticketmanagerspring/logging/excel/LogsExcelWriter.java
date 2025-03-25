package org.example.ngevaticketmanagerspring.logging.excel;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;

import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import lombok.Getter;
import lombok.Setter;

public class LogsExcelWriter {

    private final Collection<String> logs;

    private final String template;

    @Getter
    @Setter
    private String fileName;

    public LogsExcelWriter(Collection<String> logs, final String template) {
        this.template = template;
        if (logs == null || logs.isEmpty()) {
            throw new IllegalArgumentException();
        }

        this.fileName = "NGEVA Logs.xlsx";
        this.logs = logs;
    }

    public void write(OutputStream result) throws IOException {

        try (final XSSFWorkbook workbook = getWorkBook()) {
            XSSFSheet sheet = workbook.getSheetAt(0);

            PrintSetup printSetup = sheet.getPrintSetup();
            printSetup.setLandscape(true);
            sheet.setFitToPage(true);
            sheet.setHorizontallyCenter(true);
            sheet.setVerticallyCenter(true);

            for (final String entry : logs) {
                XSSFRow currentRow = sheet.createRow(sheet.getLastRowNum() + 1);

                String[] splitLogEntry = splitLogEntry(entry);

                addCell(currentRow, LogsExcelConfig.TIMESTAMP, splitLogEntry[0]);
                addCell(currentRow, LogsExcelConfig.EVENT, splitLogEntry[1]);
                addCell(currentRow, LogsExcelConfig.STATUS, splitLogEntry[2]);
                addCell(currentRow, LogsExcelConfig.MESSAGE, splitLogEntry[3]);
            }
            sheet.autoSizeColumn(LogsExcelConfig.TIMESTAMP.getColumn());
            sheet.autoSizeColumn(LogsExcelConfig.EVENT.getColumn());
            sheet.autoSizeColumn(LogsExcelConfig.STATUS.getColumn());
            sheet.autoSizeColumn(LogsExcelConfig.MESSAGE.getColumn());
            workbook.write(result);
        }
    }

    void addCell(XSSFRow currentRow, LogsExcelConfig config, String value) {
        currentRow.createCell(config.getColumn()).setCellValue(value);
    }

    public static String[] splitLogEntry(String logEntry) {
        return logEntry.split(" \\| ");
    }

    private XSSFWorkbook getWorkBook() throws IOException {
        InputStream ioStream = this.getClass().getClassLoader().getResourceAsStream(this.template);
        if (ioStream == null) {
            throw new IOException("Stream from template cannot be null");
        }
        return new XSSFWorkbook(ioStream);
    }
}

