package com.siddhant.healthreport.medicalaibot.report.storage;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    StoredFile store(MultipartFile file);
}
