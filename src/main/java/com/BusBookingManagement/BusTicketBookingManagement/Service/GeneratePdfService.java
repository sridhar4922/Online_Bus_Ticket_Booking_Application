package com.BusBookingManagement.BusTicketBookingManagement.Service;

import com.lowagie.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Map;

@Service
public class GeneratePdfService {


    @Autowired
    private TemplateEngine templateEngine;

    public void generatePdfFile(String templateName, Map<String,Object> data,String pdfFileName){
        Context context=new Context();
        context.setVariables(data);

        String htmlContent=templateEngine.process(templateName,context);

        try (FileOutputStream fileOutputStream=new FileOutputStream(pdfFileName)){
            ITextRenderer fileRenderer=new ITextRenderer();
            fileRenderer.setDocumentFromString(htmlContent);
            fileRenderer.layout();
            fileRenderer.createPDF(fileOutputStream,false);
            fileRenderer.finishPDF();
        }catch (FileNotFoundException e){
            throw new RuntimeException("PDF File Not Found: "+pdfFileName,e);
        }catch(DocumentException e){
            throw new RuntimeException("Error while creating PDF document", e);
        }catch(Exception e){
            throw new RuntimeException("File to Generate pdf:"+e.getMessage(),e);
        }
    }
}

