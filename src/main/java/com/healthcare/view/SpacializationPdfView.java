package com.healthcare.view;

import java.awt.Color;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.healthcare.entity.Specialization;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class SpacializationPdfView extends AbstractPdfView{

	@Override
	protected void buildPdfMetadata(Map<String, Object> model, Document document, HttpServletRequest request) {

		HeaderFooter header =new HeaderFooter(new Phrase("Hospital Management"),false);
		header.setAlignment(Element.ALIGN_CENTER);
		document.setHeader(header);
		

		HeaderFooter footer =new HeaderFooter(new Phrase(new Date()+"(c) Swapnil burde, Page #"),true);
		footer.setAlignment(Element.ALIGN_RIGHT);
		document.setFooter(footer);
	}

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		//to download
		response.setHeader("Content-Disposition", "attachment;filename=SPEC.pdf");

		//Get Data from controller
		List<Specialization> list=(List<Specialization>) model.get("list");
		
		// Title
		Font titleFont = new Font(Font.TIMES_ROMAN, 30, Font.BOLD, Color.RED);
		Paragraph title=new Paragraph("Spacicalization data",titleFont);
		title.setAlignment(Element.ALIGN_CENTER);
		title.setSpacingBefore(20.0f);
		title.setSpacingAfter(25.0f);
		document.add(title);
			
		//Table header
		Font tableHead = new Font(Font.TIMES_ROMAN, 12, Font.BOLD, Color.ORANGE);
		PdfPTable table = new PdfPTable(4);// no.of columns
		table.addCell(new Phrase("ID",tableHead));
		table.addCell(new Phrase("CODE",tableHead));
		table.addCell(new Phrase("NAME",tableHead));
		table.addCell(new Phrase("NOTE",tableHead));
		
		//Table Body (data)
		for(Specialization spec:list) {
			table.addCell(spec.getId().toString());
			table.addCell(spec.getSpecCode());
			table.addCell(spec.getSpecName());
			table.addCell(spec.getSpecNote());
		}
		document.add(table);
		
	}
}
