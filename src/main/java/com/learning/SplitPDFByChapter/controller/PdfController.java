package com.learning.SplitPDFByChapter.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
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


    //limitation that it only works while only starting page of each chapter has only chapter text
    @PostMapping(value="/split-auto", consumes="multipart/form-data")
    public ResponseEntity<?> autoSplitPdf(@RequestPart("file") MultipartFile file) {
        try (InputStream input = file.getInputStream();
            PDDocument fullDoc = PDDocument.load(input)) {

            List<ChapterRange> chapters = detectChapters(fullDoc);

            File outputDir = new File("output");
            outputDir.mkdirs();

            for (ChapterRange chapter : chapters) {
                try (PDDocument chapterDoc = new PDDocument()) {
                    for (int i = chapter.getStartPage() - 1; i < chapter.getEndPage(); i++) {
                        chapterDoc.addPage(fullDoc.getPage(i));
                    }
                    String outputPath = outputDir + "/" + chapter.getTitle().replaceAll("\\s+", "_") + ".pdf";
                    chapterDoc.save(outputPath);
                }
            }

            return ResponseEntity.ok("Auto PDF split successful.");
        } catch (Exception e) {
            log.error("Auto split failed", e);
            return ResponseEntity.status(500).body("Failed to split: " + e.getMessage());
        }
    }


    private List<ChapterRange> detectChapters(PDDocument document) throws IOException {
        List<ChapterRange> chapters = new ArrayList<>();
        PDFTextStripper stripper = new PDFTextStripper();

        for (int i = 0; i < document.getNumberOfPages(); i++) {
            stripper.setStartPage(i + 1);
            stripper.setEndPage(i + 1);
            String text = stripper.getText(document);

            if (text.toLowerCase().matches("(?s).*chapter\\s+\\d+.*")) {
                String title = text.lines().filter(line -> line.toLowerCase().matches("chapter\\s+\\d+.*"))
                    .findFirst().orElse("Chapter " + (chapters.size() + 1));
                chapters.add(new ChapterRange(-1, i + 1, title.trim()));
            }
        }

        // Filling the end pages
        for (int i = 0; i < chapters.size(); i++) {
            int start = chapters.get(i).getStartPage();
            int end = (i + 1 < chapters.size()) ? chapters.get(i + 1).getStartPage() - 1 : document.getNumberOfPages();
            chapters.get(i).setEndPage(end);
        }
        log.info("chapters list : {}", chapters.toString());
        return chapters;
    }

    
    
}
