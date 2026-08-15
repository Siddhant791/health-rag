package com.siddhant.healthreport.medicalaibot.report.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ReportUploadRequest {
    private MultipartFile file;
}
