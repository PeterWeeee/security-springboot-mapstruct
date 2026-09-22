package vn.iotstar.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import vn.iotstar.entity.Role;
import vn.iotstar.entity.User;
import vn.iotstar.repository.RoleRepository;
import vn.iotstar.repository.UserRepository;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(RoleRepository roleRepository,
                              UserRepository userRepository,
                              PasswordEncoder encoder,
                              @Value("${ADMIN_EMAIL:admin@hcmute.edu.vn}") String adminEmail,
                              @Value("${ADMIN_PASSWORD:123456}") String adminPassword) {
        return args -> {
            Role userRole = roleRepository.findByNameIgnoreCase("ROLE_USER")
                    .orElseGet(() -> roleRepository.save(new Role("ROLE_USER")));

            Role adminRole = roleRepository.findByNameIgnoreCase("ROLE_ADMIN")
                    .orElseGet(() -> roleRepository.save(new Role("ROLE_ADMIN")));

            if (!userRepository.existsByEmailIgnoreCase(adminEmail)) {
                User admin = User.builder()
                        .email(adminEmail.toLowerCase())
                        .fullName("System Administrator")
                        .password(encoder.encode(adminPassword))
                        .role(adminRole)
                        .enabled(true)
                        .build();
                userRepository.save(admin);
            }

            if (!userRepository.existsByEmailIgnoreCase("user@gmail.com")) {
                User user = User.builder()
                        .email("user@gmail.com")
                        .fullName("Nguyễn Văn User")
                        .password(encoder.encode("123456"))
                        .role(userRole)
                        .enabled(true)
                        .build();
                userRepository.save(user);
            }
        };
    }
}
