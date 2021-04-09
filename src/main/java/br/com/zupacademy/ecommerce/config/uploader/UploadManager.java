package br.com.zupacademy.ecommerce.config.uploader;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface UploadManager {
    Set<String> save( List<MultipartFile> images );
}
