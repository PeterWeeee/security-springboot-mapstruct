package vn.iotstar.service;

import com.cloudinary.Cloudinary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import vn.iotstar.service.impl.CloudinaryServiceImpl;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

public class CloudinaryServiceTest {

    private CloudinaryService cloudinaryService;
    private String cloudName;

    @BeforeEach
    void setUp() throws IOException {
        Properties props = new Properties();
        File envFile = new File(".env");
        if (envFile.exists()) {
            try (FileInputStream fis = new FileInputStream(envFile)) {
                props.load(fis);
            }
        }

        cloudName = props.getProperty("CLOUDINARY_CLOUD_NAME", System.getenv("CLOUDINARY_CLOUD_NAME"));
        String apiKey = props.getProperty("CLOUDINARY_API_KEY", System.getenv("CLOUDINARY_API_KEY"));
        String apiSecret = props.getProperty("CLOUDINARY_API_SECRET", System.getenv("CLOUDINARY_API_SECRET"));

        assertNotNull(cloudName, "CLOUDINARY_CLOUD_NAME không được null trong .env");
        assertNotNull(apiKey, "CLOUDINARY_API_KEY không được null trong .env");
        assertNotNull(apiSecret, "CLOUDINARY_API_SECRET không được null trong .env");

        Cloudinary cloudinary = new Cloudinary(Map.of(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret,
                "secure", true
        ));

        cloudinaryService = new CloudinaryServiceImpl(cloudinary);
    }

    private byte[] createSampleImageBytes() throws IOException {
        BufferedImage image = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 200, 200);
        g.setColor(Color.BLUE);
        g.drawString("Test Cloudinary", 40, 100);
        g.dispose();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        return baos.toByteArray();
    }

    @Test
    @DisplayName("Test upload ảnh sản phẩm thực tế lên Cloudinary thật và kiểm tra URL")
    void testUploadAndVerifyCloudinary() throws Exception {
        byte[] imageBytes = createSampleImageBytes();
        MockMultipartFile file = new MockMultipartFile(
                "image",
                "test_product_banner.png",
                "image/png",
                imageBytes
        );

        // 1. Thực hiện upload ảnh lên Cloudinary
        CloudinaryUploadResult result = cloudinaryService.upload(file);

        assertNotNull(result, "Upload result không được null");
        System.out.println("==================================================");
        System.out.println("KẾT QUẢ UPLOAD THÀNH CÔNG LÊN CLOUDINARY:");
        System.out.println("URL: " + result.url());
        System.out.println("Public ID: " + result.publicId());
        System.out.println("==================================================");

        // 2. Kiểm tra thông tin kết quả
        assertFalse(result.publicId().startsWith("local_"), "Ảnh không được rơi vào fallback local!");
        assertTrue(result.url().startsWith("https://res.cloudinary.com/" + cloudName + "/"),
                "URL phải là đường dẫn HTTPS từ tài khoản Cloudinary: " + cloudName);
        assertTrue(result.publicId().startsWith("shop/products/"), "Public ID phải nằm trong thư mục shop/products");

        // 3. Kiểm tra HTTP GET đến URL thực tế của Cloudinary
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(result.url()))
                .GET()
                .build();
        HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
        System.out.println("HTTP Status khi truy cập ảnh từ Cloudinary: " + response.statusCode());
        assertEquals(200, response.statusCode(), "Cloudinary phải phục vụ ảnh với HTTP status 200");

        // 4. Dọn dẹp ảnh test sau khi kiểm tra xong
        cloudinaryService.delete(result.publicId());
        System.out.println("Đã dọn dẹp ảnh test trên Cloudinary thành công!");
    }
}
