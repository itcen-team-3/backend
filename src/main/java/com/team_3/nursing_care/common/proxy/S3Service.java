package com.team_3.nursing_care.common.proxy;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface S3Service {

    String uploadVoiceFile(MultipartFile file);
    String uploadSignFile(MultipartFile file);
    InputStream download(String s3Key);
    String getFileUrl(String s3Key);
}
