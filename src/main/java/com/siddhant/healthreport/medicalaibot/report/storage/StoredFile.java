package com.siddhant.healthreport.medicalaibot.report.storage;

import lombok.Builder;

@Builder
public record StoredFile(
  String originalFileName,
  String storedFileName,
  String storagePath,
  String contentType,
  long fileSize,
  String sha256
)
{ }
