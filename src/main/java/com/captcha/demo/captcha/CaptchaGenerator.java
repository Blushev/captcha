package com.captcha.demo.captcha;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Component
public class CaptchaGenerator {

    private String text;
    private int width;
    private int height;
    private String fileName;

    @Autowired
    private CaptchaUtils captchaUtils;

    @Autowired
    public CaptchaGenerator(@Value("${captcha.width}") int width, @Value("${captcha.height}") int height, @Value("${captcha.text}") String text, @Value("${captcha.fileName}") String fileName) {
        this.width = width;
        this.height = height;
        this.text = text;
        this.fileName = fileName;
    }

    public void generateCaptcha() {
        BufferedImage image = captchaUtils.generateCaptcha(text, width, height);
        try {
            File outputFile = new File(fileName);
            ImageIO.write(image, "png", outputFile);
            System.out.println("Captcha generated successfully with text: " + text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}