package com.siddhant.healthreport.medicalaibot.report.service;

import com.siddhant.healthreport.medicalaibot.config.StorageConfiguration;
import com.siddhant.healthreport.medicalaibot.report.storage.StorageService;
import com.siddhant.healthreport.medicalaibot.report.storage.StoredFile;
import com.siddhant.healthreport.medicalaibot.utils.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.UUID;

@Service
public class LocalStorageService implements StorageService {
    private final Path storagePath;

    public LocalStorageService(StorageConfiguration locationConfiguration){
        storagePath = Paths.get(locationConfiguration.getLocation());

        try{
            Files.createDirectories(storagePath);
        } catch (IOException ioException){
            throw new RuntimeException(ioException);
        }
    }

    @Override
    public StoredFile store(MultipartFile file) {
        // Extract original file name
        String originalFileName = file.getOriginalFilename();

        // Extract extension
        String extension = FileUtils.getFileExtension(originalFileName);

        // Generate a unique file name for storage
        String storedFileName = UUID.randomUUID() + extension;

        // Resolve storage path with root path
        Path destination = storagePath.resolve(storedFileName);

        destination = destination.normalize();

        if (!destination.startsWith(storagePath)) {
            throw new RuntimeException("Invalid storage Path");
        }

        return null;
    }
}
