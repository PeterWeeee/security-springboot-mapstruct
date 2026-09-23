package vn.iotstar.service.impl;

import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.iotstar.service.CloudinaryService;
import vn.iotstar.service.CloudinaryUploadResult;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CloudinaryServiceImpl implements CloudinaryService {

    private final Cloudinary cloudinary;

    @Override
    public CloudinaryUploadResult upload(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Chua chon anh");
        }

        String type = file.getContentType();
        if (type == null || !type.startsWith("image/")) {
            throw new IllegalArgumentException("Chi cho phep file hinh anh");
        }

        // 1. Thu upload len Cloudinary neu da co cau hinh that
        try {
            if (cloudinary.config.cloudName != null
                    && !cloudinary.config.cloudName.isBlank()
                    && !cloudinary.config.cloudName.equals("dfdfdf")
                    && !cloudinary.config.cloudName.equals("your_cloud_name")) {
                Map<?, ?> result = cloudinary.uploader().upload(
                        file.getBytes(),
                        Map.of("folder", "shop/products")
                );
                String secureUrl = String.valueOf(result.get("secure_url"));
                String publicId = String.valueOf(result.get("public_id"));
                log.info("Cloudinary upload thanh cong! URL: {}, Public ID: {}", secureUrl, publicId);
                return new CloudinaryUploadResult(secureUrl, publicId);
            }
        } catch (Exception e) {
            log.warn("Cloudinary upload khong thanh cong: {}. Chuyen sang fallback luu anh local.", e.getMessage(), e);
        }

        // 2. Fallback luu tru cuc bo an toan
        try {
            Path uploadPath = Paths.get("uploads/products").toAbsolutePath().normalize();
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String originalName = file.getOriginalFilename() != null ? file.getOriginalFilename() : "image.png";
            String filename = UUID.randomUUID() + "_" + originalName.replaceAll("\\s+", "_");
            Path targetFile = uploadPath.resolve(filename);
            Files.copy(file.getInputStream(), targetFile, java.nio.file.StandardCopyOption.REPLACE_EXISTING);

            String localUrl = "/uploads/products/" + filename;
            return new CloudinaryUploadResult(localUrl, "local_" + filename);
        } catch (IOException ex) {
            throw new IllegalStateException("Luu anh local that bai: " + ex.getMessage(), ex);
        }
    }

    @Override
    public void delete(String publicId) {
        if (publicId == null || publicId.isBlank()) return;

        if (publicId.startsWith("local_")) {
            String filename = publicId.substring(6);
            try {
                Path uploadPath = Paths.get("uploads/products").toAbsolutePath().normalize();
                Path targetFile = uploadPath.resolve(filename);
                Files.deleteIfExists(targetFile);
            } catch (Exception ignored) {
            }
            return;
        }

        try {
            log.info("Yeu cau xoa anh tren Cloudinary voi publicId: {}", publicId);
            Map<?, ?> res = cloudinary.uploader().destroy(publicId, Map.of("resource_type", "image"));
            log.info("Ket qua xoa Cloudinary: {}", res);
        } catch (Exception e) {
            log.warn("Xoa anh Cloudinary that bai: {}", e.getMessage());
        }
    }
}
