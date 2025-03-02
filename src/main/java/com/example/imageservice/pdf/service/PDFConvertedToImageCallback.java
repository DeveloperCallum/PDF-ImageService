package com.example.imageservice.pdf.service;

import org.apache.pdfbox.pdmodel.PDDocument;

public interface PDFConvertedToImageCallback {
    void run(PDDocument document, int start, int end);
}
