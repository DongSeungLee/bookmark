package com.example.demo;

import com.example.demo.AOP.DoRepeat;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import java.io.*;
import java.nio.charset.StandardCharsets;

@Service
@Slf4j
public class PdfService {
    public void createPdf(String html,String folder,String fileName) throws DocumentException, IOException, DocumentException {
        File f = new File(folder);
        if(!f.exists()) {
            if(!f.mkdirs()) {
                throw new IOException();
            }
        }

        String pdfPath = folder + fileName;

        InputStream is = new ByteArrayInputStream(html.getBytes(StandardCharsets.UTF_8));

        Document document = new Document(PageSize.LETTER);
        document.setMargins(20f, 20f, 20f, 20f);
        FileOutputStream os = new FileOutputStream(pdfPath);
        PdfWriter writer = PdfWriter.getInstance(document, os);
        document.open();
        XMLWorkerHelper.getInstance().parseXHtml(writer, document, is);
        document.close();
        os.close();
    }
    @DoRepeat
    public void aopTestMethod1(){
        System.out.println("aopTestMethod1");
        return;
    }
    @DoRepeat
    public String apoTestMethodRetString(){
        return "hoho";
    }
}
