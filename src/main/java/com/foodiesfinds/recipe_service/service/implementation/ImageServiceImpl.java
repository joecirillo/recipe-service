package com.foodiesfinds.recipe_service.service.implementation;

import com.foodiesfinds.recipe_service.core.config.S3Properties;
import com.foodiesfinds.recipe_service.core.exception.BadRequestException;
import com.foodiesfinds.recipe_service.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private static final Set<String> ALLOWED_TYPES = Set.of(
            "image/jpeg", "image/png", "image/webp", "image/gif",
            "image/heic", "image/heif"
    );

    private static final Map<String, String> EXTENSION_MAP = Map.of(
            "image/jpeg", "jpg",
            "image/png",  "png",
            "image/webp", "webp",
            "image/gif",  "gif",
            "image/heic", "heic",
            "image/heif", "heif"
    );

    private final S3Client s3Client;
    private final S3Properties s3Properties;

    @Override
    public String upload(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BadRequestException("File must not be empty");
        }

        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_TYPES.contains(contentType)) {
            throw new BadRequestException("Unsupported file type. Allowed: jpeg, png, webp, gif, heic, heif");
        }

        String ext = EXTENSION_MAP.getOrDefault(contentType, "jpg");
        String key = "recipes/" + UUID.randomUUID() + "." + ext;

        try {
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(s3Properties.getBucketName())
                    .key(key)
                    .contentType(contentType)
                    .contentLength(file.getSize())
                    .build();

            s3Client.putObject(request, RequestBody.fromBytes(file.getBytes()));
        } catch (IOException e) {
            throw new BadRequestException("Failed to read uploaded file");
        }

        return "https://" + s3Properties.getBucketName() + ".s3.us-west-2.amazonaws.com/" + key;
    }

    @Override
    public void delete(String key) {
        if (key == null || key.isBlank()) {
            throw new BadRequestException("Image key must not be empty");
        }
        if (!key.startsWith("recipes/")) {
            throw new BadRequestException("Invalid image key");
        }

        DeleteObjectRequest request = DeleteObjectRequest.builder()
                .bucket(s3Properties.getBucketName())
                .key(key)
                .build();

        s3Client.deleteObject(request);
    }
}
