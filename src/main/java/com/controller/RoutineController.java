package com.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.text.TextUtilities;
import org.jfree.ui.TextAnchor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.stereotype.Controller;

import com.controller.form.CountActivityCompleteAndMissingForm;
import com.controller.form.CountActivityCompleteByPromoterForm;
import com.itextpdf.text.DocumentException;
import com.service.ApiService;
import com.service.ChartService;
import com.service.Html2ImageService;
import com.service.PDFService;

@Controller
public class RoutineController {

	@Autowired
	ChartService chartService;
	@Autowired
	ApiService apiService;
	@Autowired
	PDFService pdfService;
	@Autowired
	WhatsappController whatsappController;
	@Autowired
	Html2ImageService html2ImageService;

	@Scheduled(cron = "* * * * * *")
	public void run() {
		LocalDate date;
		if(LocalDate.now().getDayOfWeek().equals(DayOfWeek.MONDAY)) {
			 date =  LocalDate.now().minusDays(2);
		}else {
			date =  LocalDate.now().minusDays(1);
		}
		CountActivityCompleteAndMissingForm[] countActivityCompleteAndMissingForms = apiService.getCountActivityCompleteAndMissing(date);
		CountActivityCompleteByPromoterForm[] countActivityCompleteByPromoterForms = apiService.getCountActivityCompleteAndMissingByPromoter(date);
		creatChart(countActivityCompleteAndMissingForms);
		createPNG(countActivityCompleteByPromoterForms);
		whatsappController.run();
	}
	

	public void createPNG(CountActivityCompleteByPromoterForm[] countActivityCompleteByPromoterForms) {
		List<String> teamsName = Arrays.asList(countActivityCompleteByPromoterForms).stream()
				.map(element -> element.getTeamName()).distinct().collect(Collectors.toList());
		for (String teamName : teamsName) {
			var datas = Arrays.asList(countActivityCompleteByPromoterForms).stream()
					.filter(element -> element.getTeamName().equals(teamName)).collect(Collectors.toList());
			datas = CountActivityCompleteByPromoterForm.orderByPercent(datas);
			try {
				html2ImageService.generate(datas, teamName);
				insertChartInDataTable(teamName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void creatChart(CountActivityCompleteAndMissingForm[] activityCompleteAndMissings) {
		for (CountActivityCompleteAndMissingForm activityCompleteAndMissing : activityCompleteAndMissings) {
			JFreeChart chart = chartService.createChart(activityCompleteAndMissing.getTeamName(),
					activityCompleteAndMissing.getCountComplete(), activityCompleteAndMissing.getCountMissing());
			BufferedImage image = chart.createBufferedImage(400, 500);
			image = image.getSubimage(0, 0, 400, 300);
			// fetch graphics from the buffered image for perform modifications.
			Graphics2D g2 = (Graphics2D) image.getGraphics();
			g2.setFont(g2.getFont().deriveFont(30f));
			g2.setColor(Color.red);
			g2.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 35));
			String percentual = chartService.generatepercentual(activityCompleteAndMissing.getCountComplete(),
					activityCompleteAndMissing.getCountMissing()) + "%";
			String total = activityCompleteAndMissing.getCountComplete() + "/"
					+ (activityCompleteAndMissing.getCountComplete() + activityCompleteAndMissing.getCountMissing());
			// will draw string horizontally
			TextUtilities.drawAlignedString(percentual, g2, 200, image.getHeight() / 1.5f, TextAnchor.CENTER);
			TextUtilities.drawAlignedString(total, g2, 200, (float) image.getHeight() / 1.2f, TextAnchor.CENTER);
			// will draw string Vertically
			// TextUtilities.drawRotatedString(str, g2, -Math.PI / 2, location_x,
			// location_y);
			g2.dispose();
			// generate png file from the modified buffered image
			Path root = FileSystems.getDefault().getPath("").toAbsolutePath();
			try {
				ImageIO.write(image, "png", new File("C:\\Users\\4P\\eclipse-workspace\\chart-operation\\src\\main\\resources\\chart\\"+activityCompleteAndMissing.getTeamName()+".png"));
			} catch (IOException e) {
				System.out.println("Error While Creating chart");
				e.printStackTrace();
			}
		}
	}

	public void insertChartInDataTable(String teamName) {
		try
		{
		    BufferedImage source = ImageIO.read(new File("C:\\Users\\4P\\eclipse-workspace\\chart-operation\\src\\main\\resources\\png\\"+teamName+".png"));
		    BufferedImage logo = ImageIO.read(new File("C:\\Users\\4P\\eclipse-workspace\\chart-operation\\src\\main\\resources\\chart\\"+teamName+".png"));
		    Graphics g = source.getGraphics();
		    g.drawImage(logo, 0, 0, null);
		    ImageIO.write(source, "PNG",new File("C:\\Users\\4P\\eclipse-workspace\\chart-operation\\src\\main\\resources\\png\\"+teamName+".png"));
		}
		catch (Exception e)
		{
		    e.printStackTrace();
		}
	}

	public Double generateNumberInPlotComplete(Integer countComplete, Integer countMissing) {
		return new BigDecimal(countComplete)
				.divide(new BigDecimal(countComplete + countMissing), 2, RoundingMode.HALF_EVEN).doubleValue() * 180;
	}

	public Double generateNumberInPlotMissing(Integer countComplete, Integer countMissing) {
		return new BigDecimal(countMissing)
				.divide(new BigDecimal(countComplete + countMissing), 2, RoundingMode.HALF_EVEN).doubleValue() * 180;
	}
}
