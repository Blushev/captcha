package com.captcha.demo;

import com.captcha.demo.captcha.CaptchaGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		CaptchaGenerator captchaGenerator = new CaptchaGenerator(200, 80, "CAPTCHA", "captcha.png");
		captchaGenerator.generateCaptcha();
	}

}
