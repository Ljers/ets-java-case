package com.example.ets.controller;

import com.example.ets.entity.FileEntity;
import com.example.ets.service.FileService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.List;


@RestController
@RequestMapping("/api/files")
@Tag(name = "Files API", description = "File upload, download, update, delete operations")
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

    @GetMapping("/{id}/download")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long id) throws IOException {
        byte[] content = fileService.downloadFileContent(id);
        FileEntity entity = fileService.getFileById(id);

        return ResponseEntity.ok()
                .contentType(fileService.determineMediaType(entity.getExtension()))
                .header("Content-Disposition", "attachment; filename=\"" + entity.getFileName() + "\"")
                .body(content);
    }



    @Operation(summary = "Delete file by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFile(@PathVariable Long id) {
        try {
            fileService.deleteFile(id);
            return ResponseEntity.ok("File deleted successfully.");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to delete file.");
        }
    }

    @Operation(summary = "Update file by id")
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
