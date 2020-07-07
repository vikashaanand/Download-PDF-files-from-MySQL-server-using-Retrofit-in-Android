package com.example.downloadingfilesfrommysqlserversuingretrofit;

public class DocumentPOJO {

    private int pdfSn;
    private String pdfTitle;
    private String encodedPDF;

    public int getPdfSn() {
        return pdfSn;
    }

    public String getPdfTitle() {
        return pdfTitle;
    }

    public String getEncodedPDF() {
        return encodedPDF;
    }
}
