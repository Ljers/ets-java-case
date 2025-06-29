package com.example.ets.service;

import com.example.ets.enums.FileType;
import com.example.ets.entity.FileEntity;
import com.example.ets.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class FileService {

    private final String uploadDir = "files";

    @Autowired
    private FileRepository fileRepository;



    public FileEntity saveFile(MultipartFile file) throws IOException {
        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null || file.isEmpty()) {
            throw new RuntimeException("Invalid file.");
        }
        String extension = getFileExtension(originalFileName).toLowerCase();

        if (!FileType.isAllowed(extension)) {
            throw new RuntimeException("Invalid file type.");
        }
        if (file.getSize() > 5 * 1024 * 1024) {
            throw new RuntimeException("File size exceeds 5MB.");
        }

        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();

        String storedFileName = UUID.randomUUID() + "." + extension;
        Path filePath = Paths.get(uploadDir).resolve(storedFileName).normalize();
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        FileEntity entity = new FileEntity(originalFileName, storedFileName, extension, file.getSize(), filePath.toString());
        return fileRepository.save(entity);
    }

    public List<FileEntity> getAllFiles() {
        return fileRepository.findAll();
    }


    public FileEntity getFileById(Long id) {
        return fileRepository.findById(id).orElseThrow(() -> new RuntimeException("File not found"));
    }

    public byte[] downloadFileContent(Long id) throws IOException {
        FileEntity entity = getFileById(id);
        Path path = Paths.get(entity.getPath());
        return Files.readAllBytes(path);
    }

    public MediaType determineMediaType(String extension) {
        return FileType.fromExtension(extension);
    }




    public void deleteFile(Long id) throws IOException {
        FileEntity entity = getFileById(id);
        Path path = Paths.get(entity.getPath());
        Files.deleteIfExists(path);
        fileRepository.deleteById(id);
    }

    public FileEntity updateFile(Long id, MultipartFile newFile) throws IOException {
        FileEntity oldEntity = getFileById(id);
        Files.deleteIfExists(Paths.get(oldEntity.getPath()));

        return saveFile(newFile);
    }

    private String getFileExtension(String fileName) {
        int lastIndex = fileName.lastIndexOf(".");
        if (lastIndex == -1) return "";
        return fileName.substring(lastIndex + 1);
    }
}
