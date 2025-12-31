/*
 * Copyright(c) 2024 Parillume, All rights reserved worldwide
 */
package com.parillume.service;

import com.parillume.util.FileUtil.FileType;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author tom@parillume.com
 * @author tmargolis
 */
@Service
public class ConversionService {

    public int convert(FileType from, FileType to, File sourceDir) 
    throws Exception {
        if(FileType.PDF != from || FileType.PNG != to)
            throw new Exception("Conversion from "+from.name()+" to "+to.name()+" is not supported");

        int conversionCount = 0;
        
        File[] pdfFiles = sourceDir.listFiles((dir, name) -> name.toLowerCase().endsWith(FileType.PDF.getExtension()));
        if (pdfFiles != null) {
            for (File pdfFile : pdfFiles) {
                convertPdf(pdfFile, sourceDir);
                conversionCount++;
            }
        }
        
        return conversionCount;
    }

    private void convertPdf(File pdfFile, File outputDir) throws IOException {
        try (PDDocument document = PDDocument.load(pdfFile)) {
            PDFRenderer pdfRenderer = new PDFRenderer(document);

            int pageCount = document.getNumberOfPages();
            for (int page = 0; page < pageCount; page++) {
                BufferedImage image = pdfRenderer.renderImageWithDPI(page, 300); // 300 DPI
                
                String extension = pageCount > 1 ?
                                   "_page_" + (page + 1) + FileType.PNG.getExtension() :
                                   FileType.PNG.getExtension();
                String outputFileName = pdfFile.getName().replace(FileType.PDF.getExtension(), extension);
                File outputFile = new File(outputDir, outputFileName);
                
                ImageIO.write(image, "PNG", outputFile);
            }
        }
    }
}
