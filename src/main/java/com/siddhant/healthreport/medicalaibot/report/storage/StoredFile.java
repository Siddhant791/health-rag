package com.siddhant.healthreport.medicalaibot.report.storage;

public record StoredFile(
  String originalFileName,
  String storedFileName,
  String storagePath,
  String contentType,
  long fileSize,
  String sha256
)
{ }
