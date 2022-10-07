package com.service;

import java.awt.Color;
import java.awt.Font;
import java.math.BigDecimal;
import java.math.RoundingMode;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CenterTextMode;
import org.jfree.chart.plot.Marker;
import org.jfree.chart.plot.RingPlot;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.ui.RectangleAnchor;
import org.jfree.chart.ui.TextAnchor;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.springframework.stereotype.Service;

@Service
public class ChartService {
	
	private static final String INVISIBLE = "have_a_look_on_me_if_you_can_xD";
	private static java.awt.Color whiteColorAlphaChannel = new java.awt.Color(255, 255, 255, 0);

	
	public JFreeChart createChart(String team, Integer countComplete,Integer countMissing) {	
		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue("COMPLETO", generateNumberInPlotComplete(countComplete,countMissing));
		dataset.setValue("PENDENTE", generateNumberInPlotMissing(countComplete, countMissing));
		dataset.setValue(INVISIBLE, 180);
		
		JFreeChart chart = ChartFactory.createRingChart(team, dataset, false, false, false);
		RingPlot plot = (RingPlot) chart.getPlot();
	

		plot.setStartAngle(180);
		plot.setCircular(true);
		plot.setSimpleLabels(true);
		plot.setSectionDepth(0.2);
		plot.setBackgroundPaint(Color.WHITE);
		plot.setSeparatorsVisible(false);
		Color invisible = new Color(0xffffff, true);
		plot.setSectionPaint(INVISIBLE, whiteColorAlphaChannel); // 180° alpha invisible
		plot.setSectionOutlinePaint(INVISIBLE, whiteColorAlphaChannel); // 180° alpha invisible
		plot.setShadowPaint(null);
		plot.setLabelGenerator(null);
		return chart;
	}
	
	public Double generateNumberInPlotComplete(Integer countComplete, Integer countMissing ) {
		return new BigDecimal(new BigDecimal(countComplete).divide(new BigDecimal(countComplete+countMissing),2,RoundingMode.HALF_EVEN).doubleValue()*180).setScale(0, RoundingMode.HALF_EVEN).doubleValue();
	}
	public Double generatepercentual(Integer countComplete, Integer countMissing ) {
		return new BigDecimal(new BigDecimal(countComplete).divide(new BigDecimal(countComplete+countMissing),2,RoundingMode.HALF_EVEN).doubleValue()*100).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
	}
	public Double generateNumberInPlotMissing(Integer countComplete, Integer countMissing ) {
		return new BigDecimal(new BigDecimal(countMissing).divide(new BigDecimal(countComplete+countMissing),2,RoundingMode.HALF_EVEN).doubleValue()*180).setScale(0, RoundingMode.HALF_EVEN).doubleValue();
	}
}
