package com.siddhant.healthreport.medicalaibot.report.service;

import com.siddhant.healthreport.medicalaibot.config.StorageConfiguration;
import com.siddhant.healthreport.medicalaibot.report.storage.StorageService;
import com.siddhant.healthreport.medicalaibot.report.storage.StoredFile;
import com.siddhant.healthreport.medicalaibot.utils.FileUtils;
import com.siddhant.healthreport.medicalaibot.utils.HexUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

        // Final destination where file needs to be stored
        destination = destination.normalize();

        byte[] hash;

        if (!destination.startsWith(storagePath)) {
            throw new RuntimeException("Invalid storage Path");
        }

        try {
            // Let's read the file now and store it in the destination
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            try (OutputStream outputStream = Files.newOutputStream(destination);
                 InputStream inputFileStream = file.getInputStream()) {
                DigestInputStream digestInputStream = new DigestInputStream(inputFileStream, digest);

                // 8KB of buffer
                byte[] buffer = new byte[8192];

                int byteRead;

                while ((byteRead = digestInputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, byteRead);
                }
            }

            // This is to close the digest
            hash = digest.digest();
        } catch (IOException | NoSuchAlgorithmException exception) {
            throw new RuntimeException("Unable to store file", exception);
        }

        return StoredFile.builder()
                .contentType(file.getContentType())
                .fileSize(file.getSize())
                .storedFileName(storedFileName)
                .originalFileName(originalFileName)
                .storagePath(destination.toString())
                .sha256(HexUtils.bytesToHex(hash))
                .build();
    }
}
