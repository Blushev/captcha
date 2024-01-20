package com.captcha.demo.captcha;

import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CaptchaGenerator {

    private static final Logger LOGGER = Logger.getLogger(CaptchaGenerator.class.getName());

    private int width;
    private int height;
    private String text;
    private String fileName;

    public CaptchaGenerator(int width, int height, String text, String fileName) {
        this.width = width;
        this.height = height;
        this.text = text;
        this.fileName = fileName;
    }

    public void generateCaptcha() {
        try {
            File outputFile = new File(fileName);

            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = image.createGraphics();

            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, width, height);

            Font font = new Font("Arial", Font.BOLD, 30);
            g2d.setFont(font);

            drawTextWithRandomEffects(g2d, text);

            applyBlurThumbnailator(image);

            ImageIO.write(image, "png", outputFile);

            System.out.println("Captcha generated successfully with text: " + text);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void drawTextWithRandomEffects(Graphics2D g2d, String text) {
        Random random = new Random();

        for (int i = 0; i < text.length(); i++) {
            char currentChar = text.charAt(i);

            int fontSize = 20 + random.nextInt(20);

            Color randomColor = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));

            drawLetterWithRandomEffects(g2d, currentChar, i * width / text.length(), fontSize, randomColor);
        }
    }

    private void drawLetterWithRandomEffects(Graphics2D g2d, char currentChar, int x, int fontSize, Color color) {
        BufferedImage letterImage = new BufferedImage(fontSize, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D letterG2d = letterImage.createGraphics();

        Font font = new Font("Arial", Font.BOLD, fontSize);
        letterG2d.setFont(font);
        letterG2d.setColor(color);

        letterG2d.drawString(String.valueOf(currentChar), 0, height / 2);

        applyRandomEffects(letterG2d);

        g2d.drawImage(letterImage, x, 0, null);
    }

    private void applyRandomEffects(Graphics2D g2d) {
        Random random = new Random();

        applyRandomColor(g2d);

        applyOffset(g2d, random.nextInt(10) - 5, random.nextInt(10) - 5);

        applyScale(g2d, 0.8 + random.nextDouble() * 0.4, 0.8 + random.nextDouble() * 0.4);

        applyRotation(g2d, Math.toRadians(random.nextInt(60) - 30));

        applyOverlay(g2d, random.nextInt(width), random.nextInt(height));
    }

    private void applyRandomColor(Graphics2D g2d) {
        Random random = new Random();
        Color randomColor = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
        g2d.setColor(randomColor);
        LOGGER.log(Level.INFO, "Applying random color: " + randomColor);
    }

    private void applyOffset(Graphics2D g2d, int xOffset, int yOffset) {
        AffineTransform transform = new AffineTransform();
        transform.translate(xOffset, yOffset);
        g2d.setTransform(transform);
    }

    private void applyScale(Graphics2D g2d, double xScale, double yScale) {
        AffineTransform transform = new AffineTransform();
        transform.scale(xScale, yScale);
        g2d.setTransform(transform);
    }

    private void applyRotation(Graphics2D g2d, double angle) {
        AffineTransform transform = new AffineTransform();
        transform.rotate(angle, width / 2.0, height / 2.0);
        g2d.setTransform(transform);
    }

    private void applyOverlay(Graphics2D g2d, int xOverlay, int yOverlay) {
        BufferedImage overlayImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D overlayG2d = overlayImage.createGraphics();

        Random random = new Random();
        overlayG2d.setColor(new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
        overlayG2d.drawLine(0, 0, xOverlay, yOverlay);

        g2d.drawImage(overlayImage, 0, 0, null);
    }

    private void applyBlurThumbnailator(BufferedImage image) {
        try {
            image = Thumbnails.of(image).size(width, height).outputQuality(1.0).asBufferedImage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}