package com.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.stereotype.Service;

import com.controller.form.CountActivityCompleteByPromoterForm;
import com.itextpdf.layout.renderer.IRenderer;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfArray;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.TextMarginFinder;



@Service
public class PDFService {
	
	public void createPDF(List<CountActivityCompleteByPromoterForm> datas, String teamName) throws IOException, DocumentException{
		 Path root = FileSystems.getDefault().getPath("").toAbsolutePath();
		    Path filePath = Paths.get(root.toString(),"src", "main", "resources", "pdf",teamName+"-"+LocalDate.now());	         
		    Document document = new Document();
	        PdfWriter.getInstance(document, new FileOutputStream(filePath.toString()+".pdf"));
	        float[] columnWidths = {6, 1, 1,1};
	        PdfPTable table = new PdfPTable(columnWidths);
	        table.setWidthPercentage(100);
	        table.getDefaultCell().setUseAscender(true);
	        table.getDefaultCell().setUseDescender(true);
	        Font f = new Font(FontFamily.HELVETICA, 11, Font.NORMAL, GrayColor.GRAYWHITE);
	        PdfPCell cell = new PdfPCell(new Phrase(teamName+"-"+LocalDate.now(), f));
	        cell.setBackgroundColor(GrayColor.GRAYBLACK);
	        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	        cell.setColspan(4);
	        table.addCell(cell);
	        table.getDefaultCell().setBackgroundColor(new GrayColor(0.75f));
	        for (int i = 0; i < 1; i++) {
	            table.addCell("Promotor");
	            table.addCell("Pendente");
	            table.addCell("Completo");
	            table.addCell("%");
	        }
	        table.setHeaderRows(4);
	        table.setFooterRows(1);
	        table.getDefaultCell().setBackgroundColor(GrayColor.GRAYWHITE);
	        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
	        Font yourCustomFont = FontFactory.getFont(FontFactory.HELVETICA, 9);
	        for (CountActivityCompleteByPromoterForm form: datas) {
	            table.addCell(new Phrase(form.getPromoterName(),yourCustomFont));
	            table.addCell(new Phrase(String.valueOf(form.getCountMissing()),yourCustomFont));
	            table.addCell(new Phrase(String.valueOf(form.getCountComplete()),yourCustomFont));
	            table.addCell(new Phrase(new BigDecimal(form.getCountComplete()).divide(new BigDecimal(form.getCountComplete()+form.getCountMissing()),2,RoundingMode.HALF_EVEN).multiply(new BigDecimal(100)).setScale(2,RoundingMode.HALF_EVEN).toString()+"%",yourCustomFont));
	        }
	        table.getTotalWidth();
	        document.open();
	        document.add(table);
	        document.close();
	 
	        var width = table.getTotalWidth();
	        var height = table.getTotalHeight();
	        
	        pdfToImage(filePath.toString());
	}
	
	public void resizePdf() {
	}
	
	public void pdfToImage(String filePath) throws InvalidPasswordException, IOException {
		PDDocument pdf = PDDocument.load(new File(filePath.toString()+".pdf"));
        PDFRenderer pdfRenderer = new PDFRenderer(pdf);
        for (int page = 0; page < pdf.getNumberOfPages(); ++page)
        { 
	        BufferedImage bi = pdfRenderer.renderImageWithDPI (0, 300);
	        ImageIO.write (bi, "JPEG", new File (filePath.toString()+".jpeg")); 
        }
        pdf.close();
	}
	

}
