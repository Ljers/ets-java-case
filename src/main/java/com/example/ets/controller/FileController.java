package com.example.ets.controller;

import com.example.ets.entity.FileEntity;
import com.example.ets.service.FileService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/files")
@Tag(name = "Files API", description = "File upload, download, update, delete operations")
@SecurityRequirement(name = "bearerAuth")
public class FileController {

    @Autowired
    private FileService fileService;

    @Operation(summary = "Upload file")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFile(@RequestPart("file") MultipartFile file) {
        try {
            FileEntity savedFile = fileService.saveFile(file);
            return ResponseEntity.ok(savedFile);
        } catch (RuntimeException | IOException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "List all files")
    @GetMapping
    public List<FileEntity> listFiles() {
        return fileService.getAllFiles();
    }

    @Operation(summary = "Download file by ID")
    @GetMapping("/{id}/download")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long id) throws IOException {
        byte[] content = fileService.downloadFileContent(id);
        FileEntity entity = fileService.getFileById(id);

        return ResponseEntity.ok()
                .contentType(fileService.determineMediaType(entity.getExtension()))
                .header("Content-Disposition", "attachment; filename=\"" + entity.getFileName() + "\"")
                .body(content);
    }

    @Operation(summary = "Delete file by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFile(@PathVariable Long id) {
        try {
            fileService.deleteFile(id);
            return ResponseEntity.ok("File deleted successfully.");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to delete file.");
        }
    }

    @Operation(summary = "Update file by ID")
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateFile(@PathVariable Long id, @RequestPart("file") MultipartFile file) {
        try {
            FileEntity updatedFile = fileService.updateFile(id, file);
            return ResponseEntity.ok(updatedFile);
        } catch (RuntimeException | IOException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
