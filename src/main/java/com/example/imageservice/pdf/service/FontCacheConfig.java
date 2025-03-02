package com.example.imageservice.pdf.service;

import com.example.imageservice.ImageServiceApplication;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FontCacheConfig {
    private static Logger logger = LogManager.getLogger(FontCacheConfig.class);

    @Bean
    public CommandLineRunner preCacheFonts() {
        return args -> {
            logger.log(Level.INFO, "STARTING UP!");
            // Load a sample document to trigger font cache building
            try (PDDocument document = new PDDocument()) {
                document.addPage(new PDPage());
                PDType1Font.HELVETICA.getName(); // Accessing the font to trigger cache build
            }
        };
    }
}
