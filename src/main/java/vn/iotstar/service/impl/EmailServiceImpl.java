package vn.iotstar.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import vn.iotstar.service.EmailService;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Override
    public void sendOtp(String email, String otp, String subject) {
        log.info("=================================================");
        log.info(">>> [OTP EMAIL DISPATCH] Gui ma OTP toi: {}", email);
        log.info(">>> MA OTP LA: [ {} ]", otp);
        log.info("=================================================");
        System.out.println(">>> [OTP SYSTEM] MA OTP CUA BAN LA: " + otp + " (Gui toi: " + email + ")");

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject(subject);
            message.setText("""
                Xin chao,

                Ma OTP cua ban la: %s

                Ma OTP co hieu luc trong 5 phut va chi su dung mot lan.
                Vui long khong chia se ma nay cho nguoi khac.
            """.formatted(otp));
            mailSender.send(message);
            log.info("Da gui email thanh cong toi: {}", email);
        } catch (Exception e) {
            log.warn("Khong the gui email qua SMTP: {}. Ma OTP van duoc in o console: [{}]", e.getMessage(), otp);
        }
    }
}
