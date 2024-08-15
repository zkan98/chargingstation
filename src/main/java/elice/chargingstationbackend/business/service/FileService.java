package elice.chargingstationbackend.business.service;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Service
public class FileService {

    @Value("${spring.cloud.gcp.storage.bucket}")
    private String bucketName;

    private final Storage storage = StorageOptions.getDefaultInstance().getService();

    public Resource getFileAsResource(String fileName) {
        Blob blob = storage.get(BlobId.of(bucketName, fileName));
        if (blob == null || !blob.exists()) {
            throw new RuntimeException("File not found: " + fileName);
        }
        InputStream inputStream = new ByteArrayInputStream(blob.getContent());
        return new InputStreamResource(inputStream) {
            @Override
            public String getFilename() {
                return fileName; // 이 부분에서 파일 이름을 반환하도록 수정합니다.
            }
        };
    }
}
