package br.com.zupacademy.ecommerce.config.uploader;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Primary
public class StorageProviderFake implements UploadManager {
    @Override public Set<String> save ( List<MultipartFile> images ) {
        return images.stream().map(image -> "http://bucket.io/" + image.getOriginalFilename()).collect(Collectors.toSet());
    }
}
