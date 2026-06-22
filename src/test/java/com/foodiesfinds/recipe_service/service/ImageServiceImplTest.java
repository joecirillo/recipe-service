package com.foodiesfinds.recipe_service.service;

import com.foodiesfinds.recipe_service.core.config.S3Properties;
import com.foodiesfinds.recipe_service.core.exception.BadRequestException;
import com.foodiesfinds.recipe_service.service.implementation.ImageServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ImageServiceImplTest {

    @Mock
    S3Client s3Client;

    @Mock
    S3Properties s3Properties;

    @InjectMocks
    ImageServiceImpl imageService;

    @Test
    void upload_success_returnsS3Url() {
        when(s3Properties.getBucketName()).thenReturn("test-bucket");

        MockMultipartFile file = new MockMultipartFile(
                "file", "photo.jpg", "image/jpeg", "image-data".getBytes()
        );

        String url = imageService.upload(file);

        assertThat(url).startsWith("https://test-bucket.s3.us-west-2.amazonaws.com/recipes/");
        assertThat(url).endsWith(".jpg");

        ArgumentCaptor<PutObjectRequest> requestCaptor = ArgumentCaptor.forClass(PutObjectRequest.class);
        verify(s3Client).putObject(requestCaptor.capture(), any(RequestBody.class));

        PutObjectRequest captured = requestCaptor.getValue();
        assertThat(captured.bucket()).isEqualTo("test-bucket");
        assertThat(captured.key()).startsWith("recipes/");
        assertThat(captured.contentType()).isEqualTo("image/jpeg");
    }

    @Test
    void upload_emptyFile_throwsBadRequest() {
        MockMultipartFile file = new MockMultipartFile(
                "file", "empty.jpg", "image/jpeg", new byte[0]
        );

        assertThatThrownBy(() -> imageService.upload(file))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("empty");
    }

    @Test
    void upload_invalidContentType_throwsBadRequest() {
        MockMultipartFile file = new MockMultipartFile(
                "file", "document.pdf", "application/pdf", "pdf-bytes".getBytes()
        );

        assertThatThrownBy(() -> imageService.upload(file))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Unsupported file type");
    }

    @Test
    void upload_nullFile_throwsBadRequest() {
        assertThatThrownBy(() -> imageService.upload(null))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("empty");
    }

    @Test
    void upload_heic_success() {
        when(s3Properties.getBucketName()).thenReturn("test-bucket");

        MockMultipartFile file = new MockMultipartFile(
                "file", "photo.heic", "image/heic", "image-data".getBytes()
        );

        String url = imageService.upload(file);

        assertThat(url).endsWith(".heic");
    }

    @Test
    void upload_heif_success() {
        when(s3Properties.getBucketName()).thenReturn("test-bucket");

        MockMultipartFile file = new MockMultipartFile(
                "file", "photo.heif", "image/heif", "image-data".getBytes()
        );

        String url = imageService.upload(file);

        assertThat(url).endsWith(".heif");
    }

    @Test
    void upload_noExtension_defaultsToJpg() {
        when(s3Properties.getBucketName()).thenReturn("test-bucket");

        MockMultipartFile file = new MockMultipartFile(
                "file", "photo", "image/jpeg", "image-data".getBytes()
        );

        String url = imageService.upload(file);

        assertThat(url).endsWith(".jpg");
    }

    @Test
    void delete_success_callsS3DeleteObject() {
        when(s3Properties.getBucketName()).thenReturn("test-bucket");
        when(s3Client.deleteObject(any(DeleteObjectRequest.class))).thenReturn(DeleteObjectResponse.builder().build());

        imageService.delete("recipes/some-uuid.jpg");

        ArgumentCaptor<DeleteObjectRequest> captor = ArgumentCaptor.forClass(DeleteObjectRequest.class);
        verify(s3Client).deleteObject(captor.capture());
        assertThat(captor.getValue().bucket()).isEqualTo("test-bucket");
        assertThat(captor.getValue().key()).isEqualTo("recipes/some-uuid.jpg");
    }

    @Test
    void delete_blankKey_throwsBadRequest() {
        assertThatThrownBy(() -> imageService.delete("  "))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("empty");
    }

    @Test
    void delete_nullKey_throwsBadRequest() {
        assertThatThrownBy(() -> imageService.delete(null))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("empty");
    }

    @Test
    void delete_invalidKey_throwsBadRequest() {
        assertThatThrownBy(() -> imageService.delete("other/some-file.jpg"))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Invalid image key");
    }
}
