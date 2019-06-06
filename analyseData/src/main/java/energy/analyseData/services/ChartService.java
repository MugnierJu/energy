package energy.analyseData.services;

import java.util.List;
import java.util.UUID;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import model.Data;

public class ChartService {

	private static ChartService instance = null;
	
	private ChartService() {
		
	}
	
	public static ChartService getInstance() {
		if(instance == null) {
			instance = new ChartService();
		}
		return instance;
	}
	
	public void showPlot(List<Data> dataList) {
		
		XYSeries series = new XYSeries( UUID.randomUUID().toString());
		
		int index = 0;
		for(Data data : dataList) {
			if(index < 200) {
				series.add(index,data.getFreezer());
				index++;
			}
		}
		
		XYSeriesCollection dataSet = new XYSeriesCollection(series);
		JFreeChart chart = ChartFactory.createXYBarChart("Test", "X",true, "Y", dataSet, PlotOrientation.VERTICAL,false,false,false);
		ChartFrame frame = new ChartFrame("Title", chart);
		frame.pack();
		frame.setVisible(true);
	}
}
