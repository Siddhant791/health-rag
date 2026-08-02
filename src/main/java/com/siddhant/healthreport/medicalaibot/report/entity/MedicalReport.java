package com.siddhant.healthreport.medicalaibot.report.entity;

import com.siddhant.healthreport.medicalaibot.report.enums.ReportStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Entity
@Table(name = "medical_reports")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MedicalReport {

    @Id
    private UUID id;

    @Column(name = "stored_file_name", nullable = false)
    private String storedFileName;

    @Column(name = "original_file_name", nullable = false)
    private String originalFileName;

    @Column(name = "storage_path", nullable = false)
    private String storagePath;

    @Column(name = "content_type", nullable = false)
    private String contentType;

    @Column(name = "report_date")
    private LocalDate reportDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "report_status", nullable = false)
    private ReportStatus reportStatus;

    @Column(name = "sha256", nullable = false, unique = true)
    private String sha256;

    @Column(name = "file_size", nullable = false)
    private long fileSize;

    @Column(name = "uploaded_at", nullable = false)
    private Instant uploadedAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    public void updateReportDate(LocalDate reportDate) {
        this.reportDate = reportDate;
        touch();
    }

    public void markTextExtracted() {
        this.reportStatus = ReportStatus.TEXT_EXTRACTED;
        touch();
    }

    public void markEmbedded() {
        this.reportStatus = ReportStatus.EMBEDDED;
        touch();
    }

    public void markChunked() {
        this.reportStatus = ReportStatus.CHUNKED;
        touch();
    }

    public void markReady(){
        this.reportStatus = ReportStatus.READY;
        touch();
    }

    public void markFailed(){
        this.reportStatus = ReportStatus.FAILED;
        touch();
    }

    public static MedicalReport create(String originalFileName, String storedFileName, String storagePath, String contentType, long fileSize, String sha256) {
        MedicalReport newMedicalReport = new MedicalReport();
        newMedicalReport.id = UUID.randomUUID();
        newMedicalReport.originalFileName = originalFileName;
        newMedicalReport.storedFileName = storedFileName;
        newMedicalReport.fileSize =fileSize;
        newMedicalReport.storagePath = storagePath;
        newMedicalReport.contentType = contentType;
        newMedicalReport.sha256 = sha256;
        newMedicalReport.reportStatus = ReportStatus.UPLOADED;
        newMedicalReport.uploadedAt = Instant.now();
        newMedicalReport.updatedAt = Instant.now();
        return newMedicalReport;
    }

    private void touch(){
        updatedAt = Instant.now();
    }
}
