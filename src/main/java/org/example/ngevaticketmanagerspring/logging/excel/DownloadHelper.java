package org.example.ngevaticketmanagerspring.logging.excel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class DownloadHelper {

    private DownloadHelper() {
    }

    public static ResponseEntity<Resource> createSheetDownload(final LogsExcelWriter writer) {

        try (final ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            writer.write(outputStream);
            return createDownload(writer.getFileName(), outputStream.toByteArray(),
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        } catch (IOException exception) {
            return ResponseEntity.internalServerError().build();
        }
    }

    private static ResponseEntity<Resource> createDownload(String fileName, byte[] content, String mediaType) {
        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=" + fileName);
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");

        final ByteArrayResource resource = new ByteArrayResource(content);

        return ResponseEntity.ok()
            .headers(header)
            .contentLength(resource.contentLength())
            .contentType(MediaType.parseMediaType(mediaType))
            .body(resource);
    }
}
