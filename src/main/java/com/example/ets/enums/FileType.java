package com.example.ets.enums;

import lombok.Getter;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.List;

@Getter
public enum FileType {
    PNG("png", MediaType.IMAGE_JPEG),
    JPEG("jpeg", MediaType.IMAGE_JPEG),
    JPG("jpg", MediaType.IMAGE_JPEG),
    PDF("pdf", MediaType.APPLICATION_PDF),
    DOCX("docx", MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.wordprocessingml.document")),
    XLSX("xlsx", MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));

    private final String extension;
    private final MediaType mediaType;

    FileType(String extension, MediaType mediaType) {
        this.extension = extension;
        this.mediaType = mediaType;
    }

    public static MediaType fromExtension(String ext) {
        for (FileType type : values()) {
            if (type.getExtension().equalsIgnoreCase(ext)) {
                return type.getMediaType();
            }
        }
        return MediaType.APPLICATION_OCTET_STREAM;
    }

    public static boolean isAllowed(String ext) {
        return Arrays.stream(values())
                .anyMatch(type -> type.getExtension().equalsIgnoreCase(ext));
    }

    public static List<String> getAllExtensions() {
        return Arrays.stream(values())
                .map(FileType::getExtension)
                .collect(Collectors.toList());
    }
}
