package com.siddhant.healthreport.medicalaibot.report.service;

import com.siddhant.healthreport.medicalaibot.config.StorageConfiguration;
import com.siddhant.healthreport.medicalaibot.report.storage.StorageService;
import com.siddhant.healthreport.medicalaibot.report.storage.StoredFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
        return null;
    }
}
