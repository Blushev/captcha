package com.captcha.demo.captcha;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CaptchaApp implements CommandLineRunner {

    private final CaptchaGenerator captchaGenerator;

    @Autowired
    public CaptchaApp(CaptchaGenerator captchaGenerator) {
        this.captchaGenerator = captchaGenerator;
    }

    @Override
    public void run(String... args) {
        captchaGenerator.generateCaptcha();
    }
}