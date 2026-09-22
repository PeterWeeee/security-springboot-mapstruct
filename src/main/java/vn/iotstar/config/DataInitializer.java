package vn.iotstar.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import vn.iotstar.entity.Product;
import vn.iotstar.entity.Role;
import vn.iotstar.entity.User;
import vn.iotstar.repository.ProductRepository;
import vn.iotstar.repository.RoleRepository;
import vn.iotstar.repository.UserRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner init(
            RoleRepository roleRepository,
            UserRepository userRepository,
            ProductRepository productRepository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {
            Role userRole = roleRepository.findByName("ROLE_USER")
                    .orElseGet(() -> roleRepository.save(new Role("ROLE_USER")));

            Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                    .orElseGet(() -> roleRepository.save(new Role("ROLE_ADMIN")));

            User admin = userRepository.findByUsername("admin").orElse(null);
            if (admin == null) {
                admin = User.builder()
                        .username("admin")
                        .email("admin@hcmute.edu.vn")
                        .password(passwordEncoder.encode("123456"))
                        .fullName("System Administrator")
                        .role(adminRole)
                        .enabled(true)
                        .build();
                admin = userRepository.save(admin);
            }

            User user = userRepository.findByUsername("user01").orElse(null);
            if (user == null) {
                user = User.builder()
                        .username("user01")
                        .email("user01@gmail.com")
                        .password(passwordEncoder.encode("123456"))
                        .fullName("Nguyễn Hữu Trung")
                        .role(userRole)
                        .enabled(true)
                        .build();
                user = userRepository.save(user);
            }

            if (productRepository.count() == 0) {
                Product p1 = Product.builder()
                        .name("Điện thoại iPhone 16 Pro Max")
                        .description("Điện thoại Apple iPhone 16 Pro Max 256GB chính hãng")
                        .price(new BigDecimal("34990000.00"))
                        .user(user)
                        .createdAt(LocalDateTime.now())
                        .build();
                productRepository.save(p1);

                Product p2 = Product.builder()
                        .name("Laptop Dell XPS 15")
                        .description("Laptop Dell XPS 15 Intel Core i7, 32GB RAM, 1TB SSD")
                        .price(new BigDecimal("45000000.00"))
                        .user(user)
                        .createdAt(LocalDateTime.now())
                        .build();
                productRepository.save(p2);
            }
        };
    }
}
