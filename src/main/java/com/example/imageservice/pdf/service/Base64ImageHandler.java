package com.example.imageservice.pdf.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;

public class Base64ImageHandler implements Closeable{
    private PDDocument document;

    public Base64ImageHandler(byte[] PDFasString) throws IOException {
        document = PDDocument.load(new ByteArrayInputStream(PDFasString));
    }

    public byte[] loadPDFasBase64(String base64FilePath) throws IOException {
        StringBuilder base64EncodedPdf = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(base64FilePath));

        String line;
        while ((line = reader.readLine()) != null) {
            base64EncodedPdf.append(line);
        }

        byte[] decodedBytes = Base64.getDecoder().decode(base64EncodedPdf.toString());
        reader.close();
        return decodedBytes;
    }

    /**
     * Converts a base64 PDF to a Base64 Image.
     *
     * @return An array containing all the pages.
     * @throws IOException
     */
    public String[] PDFtoBase64Image() throws IOException {
        PDFRenderer pdfRenderer = new PDFRenderer(document);
        int numberOfPages = document.getNumberOfPages();

        String[] encodedArr = new String[numberOfPages];
        for (int i = 0; i < numberOfPages; i++) {
            BufferedImage bufferedImage = pdfRenderer.renderImageWithDPI(i, 300); // 300 DPI
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", outputStream);
            byte[] imageBytes = outputStream.toByteArray();

            String encodedBase64 = Base64.getEncoder().encodeToString(imageBytes);

            encodedArr[i] = encodedBase64;
        }

        return encodedArr;
    }

    /**
     * Converts a base64 PDF to a Base64 Image.
     *
     * @return An array containing all the pages.
     * @throws IOException
     */
    public String[] PDFtoBase64Image(PDFConvertedToImageCallback callback) throws IOException {
        PDFRenderer pdfRenderer = new PDFRenderer(document);
        int numberOfPages = document.getNumberOfPages();

        String[] encodedArr = new String[numberOfPages];
        for (int i = 0; i < numberOfPages; i++) {
            BufferedImage bufferedImage = pdfRenderer.renderImageWithDPI(i, 300); // 300 DPI
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", outputStream);
            byte[] imageBytes = outputStream.toByteArray();

            String encodedBase64 = Base64.getEncoder().encodeToString(imageBytes);

            encodedArr[i] = encodedBase64;
        }

        callback.run(document, 0, numberOfPages);
        return encodedArr;
    }

    /**
     * Converts a base64 PDF to a Base64 Image.
     *
     * @param end    0-indexed page number.
     * @param start  0-indexed page number.
     * @return An array containing all the pages.
     * @throws IOException
     */
    public String[] PDFtoBase64Image(int start, int end) throws IOException {
        if (0 > start){
            throw new IllegalArgumentException("Start cannot be less than 0.");
        }

        if (start >= end){
            throw new IllegalArgumentException("Start cannot be bigger or equal to end.");
        }

        PDFRenderer pdfRenderer = new PDFRenderer(document);
        int numberOfPages = document.getNumberOfPages();

        //if we are requesting more pages than we have, we get everything up to the last page.
        if (numberOfPages <= end){
            end = numberOfPages;
        }

        String[] encodedArr = new String[end - start];
        int pos = 0;
        for (int i = start; i < end; i++) {
            BufferedImage bufferedImage = pdfRenderer.renderImageWithDPI(i, 72);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", outputStream);
            byte[] imageBytes = outputStream.toByteArray();

            String encodedBase64 = Base64.getEncoder().encodeToString(imageBytes);

            encodedArr[pos++] = encodedBase64;
        }

        document.close();
        return encodedArr;
    }

    /**
     * Converts a base64 PDF to a Base64 Image.
     *
     * @param end    0-indexed page number.
     * @param start  0-indexed page number.
     * @return An array containing all the pages.
     * @throws IOException
     */
    public String[] PDFtoBase64Image(int start, int end, PDFConvertedToImageCallback callback) throws IOException {
        if (0 > start){
            throw new IllegalArgumentException("Start cannot be less than 0.");
        }

        if (start >= end){
            throw new IllegalArgumentException("Start cannot be bigger or equal to end.");
        }

        PDFRenderer pdfRenderer = new PDFRenderer(document);
        int numberOfPages = document.getNumberOfPages();

        //if we are requesting more pages than we have, we get everything up to the last page.
        if (numberOfPages <= end){
            end = numberOfPages;
        }

        String[] encodedArr = new String[end - start];
        int pos = 0;
        for (int i = start; i < end; i++) {
            BufferedImage bufferedImage = pdfRenderer.renderImageWithDPI(i, 300); // 300 DPI
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", outputStream);
            byte[] imageBytes = outputStream.toByteArray();

            String encodedBase64 = Base64.getEncoder().encodeToString(imageBytes);

            encodedArr[pos++] = encodedBase64;
        }

        callback.run(document, start, end);
        document.close();
        return encodedArr;
    }

    @Override
    public void close() throws IOException {
        document.close();
    }

    public PDDocument getDocument() {
        return document;
    }
}