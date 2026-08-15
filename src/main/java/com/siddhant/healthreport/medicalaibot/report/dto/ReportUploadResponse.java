package com.siddhant.healthreport.medicalaibot.report.dto;

import com.siddhant.healthreport.medicalaibot.report.enums.ReportStatus;

import java.util.UUID;

public record ReportUploadResponse(UUID id, ReportStatus reportStatus) {
}
