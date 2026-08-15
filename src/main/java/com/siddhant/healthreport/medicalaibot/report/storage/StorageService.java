package com.siddhant.healthreport.medicalaibot.report.storage;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public interface StorageService {
    StoredFile store(MultipartFile file);
}
