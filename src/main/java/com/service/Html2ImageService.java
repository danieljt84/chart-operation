package com.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.controller.form.CountActivityCompleteByPromoterForm;
import com.util.FileReader;

import gui.ava.html.image.generator.HtmlImageGenerator;

@Service
public class Html2ImageService {
	@Autowired
	FileReader fileReader;
	


	public void generate(List<CountActivityCompleteByPromoterForm> countActivityCompleteByPromoterForms, String teamName ) throws InterruptedException{
		 String fileName = fileReader.getChartByName(teamName);
		 HtmlImageGenerator hig = new HtmlImageGenerator();
		 String html ="<html>"
					+ "<head>"
					+ "<style>"
					+ "table {"
					+ "  font-family: arial, sans-serif;"
					+ "  border-collapse: collapse;"
					+ "  width: 100%;"
					+ "}"
					+ ""
					+ "td, th {"
					+ "  border: 1px solid #dddddd;"
					+ "  text-align: left;"
					+ "  padding: 8px;"
					+ "}"
					+ ""
					+ "tr:nth-child(even) {"
					+ "  background-color: #dddddd;"
					+ "}"
					+ "</style>"
					+ "</head>"
					+ "<body>"
					+ "<h2>"+teamName+"</h2>"
					+ "<br>"
					+ "<br>"
					+ "<br>"
					+ "<br>"
					+ "<br>"
					+ "<br>"
					+ "<br>"
					+ "<br>"
					+ "<br>"
					+ "<br>"
					+ "<br>"
					+ "<br>"
					+ "<br>"
					+ "<br>"
					+ "<table>"
					+ "  <tr>"
					+ "    <th>Promotor</th>"
					+ "    <th>Pendente</th>"
					+ "    <th>Completo</th>"
					+ "    <th>Percentual</th>"
					+ "  </tr>";
		 String html_data = "";
		 for(CountActivityCompleteByPromoterForm data : countActivityCompleteByPromoterForms) {
			html_data = html_data+  "  <tr>"
					+ "    <td>"+data.getPromoterName()+"</td>"
					+ "    <td>"+data.getCountMissing()+"</td>"
					+ "    <td>"+data.getCountComplete()+"</td>"
					+ "    <td>"+data.getPercent()+"%</td>"
					+ "  </tr>";
		 }
		 html = html+html_data;
		 html = html + "</table>"
					+ ""
					+ "</body>"
					+ "</html>";
		 hig.loadHtml(html);
	     hig.saveAsImage(new File("C:\\Users\\4P\\eclipse-workspace\\chart-operation\\src\\main\\resources\\png\\"+teamName+".png"));
	}
}


