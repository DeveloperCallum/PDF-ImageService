package com.example.imageservice.pdf.model;

import com.example.imageservice.pdf.model.token.Token;

import java.util.LinkedList;

public class PDFResponse {
    private String base64PDF;
    private Float height;
    private Float width;
    private LinkedList<Token> tokens = new LinkedList<>();

    private int numberOfPages;
    private String[] base64Images;

    public PDFResponse(String base64PDF) {
        this.base64PDF = base64PDF;
    }

    public PDFResponse(String base64PDF, String[] base64Images) {
        this.base64PDF = base64PDF;
        this.base64Images = base64Images;
    }

    public PDFResponse() {
    }

    public String getBase64PDF() {
        return base64PDF;
    }

    public void setBase64PDF(String base64PDF) {
        this.base64PDF = base64PDF;
    }

    public LinkedList<Token> getTokens() {
        return tokens;
    }

    public String[] getBase64Images() {
        return base64Images;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public void setBase64Images(String[] base64Images) {
        this.base64Images = base64Images;
    }

    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public Float getWidth() {
        return width;
    }

    public void setWidth(Float width) {
        this.width = width;
    }
}
