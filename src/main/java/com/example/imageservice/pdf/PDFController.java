package com.example.imageservice.pdf;

import com.example.imageservice.pdf.model.Base64Request;
import com.example.imageservice.pdf.model.PDFResponse;
import com.example.imageservice.pdf.model.token.LoadToken;
import com.example.imageservice.pdf.service.Base64ImageHandler;
import com.example.imageservice.pdf.service.PDFConvertedToImageCallback;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Base64;

@RestController
@RequestMapping(value = "/pdf")
public class PDFController {
    private static final Logger logger = LogManager.getLogger(PDFController.class);

    @Deprecated
    @PostMapping("/convert/image")
    public PDFResponse convertImage(@RequestBody Base64Request base64Request, @RequestParam(required = false) Integer start, @RequestParam(required = false) Integer end) throws IOException {
        PDFResponse pdfResponse = new PDFResponse();
        pdfResponse.setBase64PDF(base64Request.getBase64());

        //Callback to make sure Token is set correctly.
        PDFConvertedToImageCallback callback = (document, realStart, realEnd) -> {
            pdfResponse.getTokens().add(new LoadToken(realStart, realEnd, System.currentTimeMillis() / 1000));
            pdfResponse.setHeight(document.getPage(0).getMediaBox().getHeight());
            pdfResponse.setWidth(document.getPage(0).getMediaBox().getWidth());
        };

        processImage(base64Request.getBase64(), start, end, pdfResponse, callback);

        pdfResponse.setBase64PDF("");
        return pdfResponse;
    }

    @PostMapping("/convert/image/getPage")
    public PDFResponse convertImage(@RequestBody PDFResponse pdfResponse, @RequestParam(required = false) Integer start, @RequestParam(required = false) Integer end) throws IOException {
        //Callback to make sure Token is set correctly.
        PDFConvertedToImageCallback callback = (document, realStart, realEnd) -> {
            pdfResponse.getTokens().add(new LoadToken(realStart, realEnd, System.currentTimeMillis() / 1000));
            pdfResponse.setHeight(document.getPage(0).getMediaBox().getHeight());
            pdfResponse.setWidth(document.getPage(0).getMediaBox().getWidth());
        };

        processImage(pdfResponse.getBase64PDF(), start, end, pdfResponse, callback);

        pdfResponse.setBase64PDF("");
        return pdfResponse;
    }

    /**
     * This method takes the base64 PDF, decodes it, converts pages to images, encodes those images, then send it back.
     * @param base64PDF Encoded base64 PDF.
     * @param start Starting page
     * @param end Ending page
     * @param pdfResponse The reply that was sent by the server.
     * @param callback What to do after the method has finished.
     */
    private void processImage(String base64PDF, Integer start, Integer end, PDFResponse pdfResponse, PDFConvertedToImageCallback callback) {
        byte[] pdf = base64PDF.replaceAll("[\\n\\r]", "").getBytes();

        try (Base64ImageHandler imageHandler = new Base64ImageHandler(Base64.getDecoder().decode(pdf))) {
            int numberOfPages = imageHandler.getDocument().getNumberOfPages();
            String[] images;
            if (start == null || end == null) {
                images = imageHandler.PDFtoBase64Image(callback);
            } else {
                images = imageHandler.PDFtoBase64Image(start, end, callback);
            }

            pdfResponse.setNumberOfPages(numberOfPages);
            pdfResponse.setBase64Images(images);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
