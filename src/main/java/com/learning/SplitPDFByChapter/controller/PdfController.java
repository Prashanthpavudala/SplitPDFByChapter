package com.learning.SplitPDFByChapter.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.SplitPDFByChapter.model.ChapterRange;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/pdf")
public class PdfController {

    @PostMapping(value="/split", consumes="Multipart/form-data")
    public ResponseEntity<?> splitPdf(
        @RequestPart("file") MultipartFile file, 
        @RequestPart("chapters") String chaptersJson) throws JsonProcessingException
        {
            InputStream input = null;
            PDDocument fullDoc = null;
            ObjectMapper mapper = new ObjectMapper();
            List<ChapterRange> chapters = mapper.readValue(chaptersJson, new TypeReference<List<ChapterRange>>() {});
            try {
                input = file.getInputStream();
                fullDoc = PDDocument.load(input);
                
                for(ChapterRange chapter : chapters){
                    PDDocument chapterDoc = new PDDocument();
                    for(int i=chapter.getStartPage()-1; i<chapter.getEndPage(); i++){
                        chapterDoc.addPage(fullDoc.getPage(i));
                    }
                    String outputPath = "output/" + chapter.getTitle().replaceAll("\\s", "_") + ".pdf" ;
                    new File("output").mkdirs();
                    chapterDoc.save(outputPath);
                    chapterDoc.close();
                }

                return ResponseEntity.ok("PDF split successfully. Check the 'output' folder.");

            } catch (IOException e) {
                log.error("Exception occurred in splitPdf is: ", e);
                return ResponseEntity.status(500).body("Failed to split the pdf: "+ e.getMessage());
            } finally {
                try {
                    if(fullDoc != null) fullDoc.close();
                    if(input != null) input.close();
                } catch (IOException e) {
                    log.error("Exception in closing resources: ", e);
                }
            }
        }
    
}
