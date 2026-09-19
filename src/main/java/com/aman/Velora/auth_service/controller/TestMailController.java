package com.aman.Velora.auth_service.controller;

import com.aman.Velora.auth_service.service.OtpService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
public class TestMailController {

    private final OtpService otpService;

    public TestMailController(OtpService otpService) {
        this.otpService = otpService;
    }

    @PostMapping("/send-mail")
    public String sendTestMail(@RequestParam String email) {
        otpService.sendTestEmail(email);
        return "Email send to " + email;
    }
}
