package com.example.ets.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "files")
public class FileEntity {
    public FileEntity() {

    }
    public FileEntity(String fileName, String storedFileName, String extension, Long size, String path) {
        this.fileName = fileName;
        this.storedFileName = storedFileName;
        this.extension = extension;
        this.size = size;
        this.path = path;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "stored_file_name")
    private String storedFileName;

    @Column(name = "extension")
    private String extension;

    @Column(name = "size")
    private Long size;

    @Column(name = "path")
    private String path;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getStoredFileName() {
        return storedFileName;
    }

    public void setStoredFileName(String storedFileName) {
        this.storedFileName = storedFileName;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
