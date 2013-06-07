/*
Copyright (c) 2010, Siemens Corporate Research a Division of Siemens Corporation 
All rights reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;

import org.dcm4che2.data.DicomObject;
import org.dcm4che2.data.Tag;

import com.siemens.cmiv.avt.mvt.ad.ADFactory;
import com.siemens.cmiv.avt.mvt.datatype.ComputationListEntry;
import com.siemens.cmiv.avt.mvt.datatype.FilterEntry;
import com.siemens.cmiv.avt.mvt.datatype.IndependentVariables;
import com.siemens.cmiv.avt.mvt.datatype.MeasurementEntry;
import com.siemens.cmiv.avt.mvt.datatype.SubjectListEntry;
import com.siemens.cmiv.avt.mvt.outlier.OutlierEntry;
import com.siemens.cmiv.avt.mvt.plot.PlotEntry;
import com.siemens.cmiv.avt.mvt.statistic.StatisticEntry;
import com.siemens.cmiv.avt.mvt.ui.MVTSelectionResultPanel;
import com.siemens.cmiv.avt.mvt.ui.MVTToolPanel;
import com.siemens.scr.avt.ad.api.ADFacade;
import com.siemens.scr.avt.ad.dicom.GeneralImage;

/**
 * @author Jie Zheng
 *
 */

public class MeasurementVariabilityPanel extends JPanel implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final int WIDTH = 350;
	final int BOTTOM = 50;
	
	MVTToolPanel jTool = new MVTToolPanel();
	MVTSelectionResultPanel jResult0 = new MVTSelectionResultPanel();
	MVTStatisticResultPanel jResult1 = new MVTStatisticResultPanel();
	
	Border border = BorderFactory.createLoweredBevelBorder();		
    String	outDir = "C:\\temp";
    
	//get screen resolution
	private int screenHeight = (int)java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
	private int screenWidth = (int)java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
	private float heightProportion = 0;
	private float widthProportion = 0;
	
	private int skip = 3;

	public MeasurementVariabilityPanel(){
		heightProportion = (float)(screenHeight / (float)1050);
		widthProportion = (float)(screenWidth / (float)1680);

		setLayout(null);
    	
    	Color xipColor = new Color(51, 51, 102);
    	setBackground(xipColor);

       	jResult0.setVisible(true);
    	jResult1.setVisible(false);
  	
    	add(jResult0);
    	add(jResult1);
		add(jTool);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); 
		jResult0.setBounds(getWidthValue(0), getHeightValue(0), screenSize.width-getWidthValue(WIDTH), screenSize.height-getHeightValue(BOTTOM));
		jResult1.setBounds(getWidthValue(0), getHeightValue(0), screenSize.width-getWidthValue(WIDTH), screenSize.height-getHeightValue(BOTTOM));
		jTool.setBounds(screenSize.width-getWidthValue(WIDTH), getHeightValue(0), getWidthValue(WIDTH), screenSize.height-getHeightValue(BOTTOM));
		
		jTool.getCriterialPanel().getJQueryButton().addActionListener(this);
		
		jTool.getCriterialPanel().getJRunButton().addActionListener(this);
		jTool.getAnalysisPanel().getJRunButton().addActionListener(this);
		jTool.getAnalysisPanel().getJBackButton().addActionListener(this);
		jTool.getAnalysisPanel().getJPlotButton().addActionListener(this);
		
		//for internal testing
//		loadInputAims("C:/temp/MVT");
//		updateToolAim();		
	}
	private int getHeightValue(int height){
		return (int)(height * heightProportion);
	}
	private int getWidthValue(int width){
		return (int)(width * widthProportion);
	}
		
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == jTool.getCriterialPanel().getJRunButton()){
			MeasurementEntry flags = jTool.getCriterialPanel().getMeasurements();
			if (flags == null)
				return;

			List<SubjectListEntry> subjectlist = jResult0.getSubjectlist();
			if (subjectlist.size() <= 0){
				Object[] options = {"OK"}; 
	            JOptionPane.showOptionDialog(this, "Please select one subject at least",
	                    "Warning",  
	                    JOptionPane.OK_OPTION,  
	                    JOptionPane.WARNING_MESSAGE,  
	                    null,  
	                    options,  
	                    options[0]);
				
				return;
			}
			
			jResult0.setVisible(false);
			jResult1.setVisible(true);
			jResult1.setResultsListFlag(flags);
			
			jTool.setPanelVisible(true);
			jResult1.updateSceneGraph();
	
			jResult1.setUIProgressBar(jTool.getJSelectionProgressBar());
			
			jResult1.setStudyType(jTool.getStudyType());
			jResult1.setNominalGT(jTool.getNominalGT());
			
			List<String> identifier = jResult0.getIdentifier();
			
			if (identifier.size() > 0)
				jResult1.calculateMeasurements(subjectlist, identifier);
		}
		else if(e.getSource() == jTool.getAnalysisPanel().getJRunButton()){
			String strUID = "1.2.3.4.5.6.7.8.9.0";
			jResult1.getComputationResultsPanel().getXMLComputationList().deleteComputationListEntry(strUID);
			
			int iteria = jResult1.getComputationResultsPanel().getResultsTableCols();
			List<StatisticEntry> items = jTool.getAnalysisPanel().getStatiscManager().getStatisticEntries();

			String type = "Mean";
			String buf = "100*sd(data)/mean(data)";
			List<Integer> count = new ArrayList<Integer>();
			for (int i = 0; i < items.size(); i++){
				StatisticEntry item = items.get(i);
				if (item.getStatisticMethod().equalsIgnoreCase(type))
					count.add(item.getStatisticDataID(item.getStatisticData()));
			}
			addStatisticsResult(count, iteria, type, buf);
			
			type = "SD";
			count.clear();
			for (int i = 0; i < items.size(); i++){
				StatisticEntry item = items.get(i);
				if (item.getStatisticMethod().equalsIgnoreCase(type))
					count.add(item.getStatisticDataID(item.getStatisticData()));
			}
			addStatisticsResult(count, iteria, type, buf);
			
			type = "Script";
			count.clear();
			for (int i = 0; i < items.size(); i++){
				StatisticEntry item = items.get(i);
				buf = item.getStatisticMethod();
				int index = buf.indexOf(":");
				if ( index != -1){
					for (int j = 1; j < iteria; j++)
						count.add(j);
					addStatisticsResult(count, iteria, type, buf);
				}
			}
			
			jResult1.getComputationResultsPanel().clearStatistics();
			jResult1.getComputationResultsPanel().updateResultList();
			for (int i = 0; i < items.size(); i++){
				StatisticEntry item = items.get(i);
				buf = item.getStatisticMethod();
				if (buf.compareToIgnoreCase("Multiple Regression") == 0 || buf.compareToIgnoreCase("N-way ANOVA") == 0){
					count.clear();
					for (int j = 0; j < items.size(); j++)
						count.add(item.getStatisticDataID(item.getStatisticData()));
					calculateStatistic(item, count, iteria);
				}
			}

			updateOutlier();
			
			jResult1.getComputationResultsPanel().updateTabbedPane(1);
			jResult1.getComputationResultsPanel().updateTabbedPane(0);
		}
		else if(e.getSource() == jTool.getAnalysisPanel().getJBackButton()) {
			jResult0.setVisible(true);
			jResult1.setVisible(false);
			
			jTool.setPanelVisible(false);
			
			jResult1.getComputationResultsPanel().emptyPlotCharts();
			jResult1.getComputationResultsPanel().clearStatistics();
			jResult1.getComputationResultsPanel().clearOutlier();
			jResult1.getIvCanvas().set("Main_Switch.whichChild", "-1");
		}
		else if(e.getSource() == jTool.getAnalysisPanel().getJPlotButton()){
			List<PlotEntry> items = jTool.getAnalysisPanel().getPlotManager().getPlotEntries();
			
			jResult1.getComputationResultsPanel().emptyPlotCharts();
			
			boolean bUpdate = false;
			for (int i = 0; i < items.size(); i++){
				PlotEntry entry = items.get(i);
				String chart = entry.getPlotChart();
			
				String plotFile = "";
				if (chart.compareToIgnoreCase("Histogram") == 0){
					plotFile = plotHistgram(entry.getPlotTitle(), entry.getPlotData1(), entry.getLegend());
				}else{
					plotFile = plotChart(chart, entry.getPlotTitle(), entry.getPlotData1(), entry.getPlotData2(), entry.getLegend());
				}
				File xipplotFile = new File(plotFile);	
				if (!xipplotFile.exists()){
					System.out.println(xipplotFile.getPath()+": not exist!");
					continue;
				}
				jResult1.getComputationResultsPanel().addPlotChart(plotFile);
				bUpdate = true;
			}
			
			if (bUpdate){
				jResult1.getComputationResultsPanel().updatePlotPanel();
			}
		}
		else if(e.getSource() == jTool.getCriterialPanel().getJQueryButton()){
			FilterEntry filter = jTool.getCriterialPanel().getFilterList();
			jResult0.setUIProgressBar(jTool.getJSelectionProgressBar());
//			jResult0.queryData(filter);
			jResult0.queryAD(filter);			
		}
		
	}	
	
	private String plotHistgram(String plotTitle, String plotData1, boolean bLegend) {
		List<ComputationListEntry> entries = jResult1.getComputationResultsPanel().getXMLComputationList().getComputationListEntries();
		
		String strUID = "1.2.3.4.5.6.7.8.9.0";
		List<Float> data1 = new ArrayList<Float>();
		for (int j = 0; j < entries.size(); j++){
			ComputationListEntry entry = entries.get(j);
			if (entry.getSubjectUID().equalsIgnoreCase(strUID))
				continue;
			
			float value1 = getMeasurement(plotData1, entry);
			data1.add(value1);
		}
		try {
			File file = new File(outDir);
			File chartFile = null;
			try {
				chartFile = File.createTempFile("123", ".png", file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
			String chart_path = chartFile.getPath();
			String value1 = formatData(data1);
			if (value1.isEmpty())
				return null;
			
			String title = plotTitle.replace(" ", "_");
			String label = plotData1.replace(" ", "_");
			
			String strFile = chart_path.replace("\\", "/");
				
			jResult1.getIvCanvas().set("Histogram.data", value1);
			jResult1.getIvCanvas().set("Histogram.title", title);
			jResult1.getIvCanvas().set("Histogram.x_Label", label);
			jResult1.getIvCanvas().set("Histogram.y_Label", "Value");
			jResult1.getIvCanvas().set("Histogram.file", strFile);		
			
			jResult1.getIvCanvas().set("Histogram.normalCurve", "FALSE");

//			if (bLegend)
//				jResult1.getIvCanvas().set("Histogram.normalCurve", "TRUE");
//			else
//				jResult1.getIvCanvas().set("Histogram.normalCurve", "FALSE");
			
			jResult1.getIvCanvas().set("Histogram.update", "");
			
			return chart_path;
			
		}catch (NullPointerException e){
			System.out.println("output folder is null");
		}
		
		return null;
	}
	private String plotChart(String type, String plotTitle, String plotData1,
			String plotData2, boolean bLegend) {
		List<ComputationListEntry> entries = jResult1.getComputationResultsPanel().getXMLComputationList().getComputationListEntries();
		
		String strUID = "1.2.3.4.5.6.7.8.9.0";
		List<Float> data1 = new ArrayList<Float>();
		List<Float> data2 = new ArrayList<Float>();
		for (int j = 0; j < entries.size(); j++){
			ComputationListEntry entry = entries.get(j);
			if (entry.getSubjectUID().equalsIgnoreCase(strUID))
				continue;
			
			float value1 = getMeasurement(plotData1, entry);
			data1.add(value1);
			float value2 = getMeasurement(plotData2, entry);
			data2.add(value2);
		}
		try {
			File file = new File(outDir);
			File chartFile = null;
			try {
				chartFile = File.createTempFile("123", ".png", file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
			String chart_path = chartFile.getPath();
			if (plotStatisticsChart(chart_path, type, plotTitle, bLegend, data1, plotData1, data2, plotData2))
				return chart_path;
		}catch (NullPointerException e){
			System.out.println("output folder is null");
		}
		
		return null;
	}

	public float getMeasurement(String plotData, ComputationListEntry entry){
		String buf = entry.getMeasurement(plotData);
		buf.replace('"', ' ');
		buf.trim();
		if (!buf.isEmpty()) {
			try {
				float value = Float.valueOf(buf);
				return value;
			} catch (NumberFormatException e){
				System.out.println("Convert to FLOAT failed");
			}
		}
		
		return 0;
	}
	
	public static void main(String[] args){
		JFrame frame = new JFrame();
		MeasurementVariabilityPanel panel = new MeasurementVariabilityPanel();
		frame.add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);	
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); 
		frame.setBounds(0, 0, screenSize.width, screenSize.height);

	}
	
	public boolean plotStatisticsChart(String file, String type, String title, boolean bLegend, List<Float> data1, String label1, List<Float> data2, String label2){
		String value1 = formatData(data1);
		if (value1.isEmpty())
			return false;
		
		String value2 = formatData(data2);
		if (value2.isEmpty())
			return false;
		
		String label = title.replace(" ", "_");
		String labelData1 = label1.replace(" ", "_");
		String labelData2 = label2.replace(" ", "_");
		
		String strFile = file.replace("\\", "/");
		if (type.equalsIgnoreCase("Scatter")){
			if (labelData1.contains("Volume")){
				labelData1 += "(ml)";
			}
			if (labelData2.contains("Volume")){
				labelData2 += "(ml)";
			}
			
			jResult1.getIvCanvas().set("Scatter.x_Axial", value1);
			jResult1.getIvCanvas().set("Scatter.x_Label", labelData1);
			jResult1.getIvCanvas().set("Scatter.y_Axial", value2);
			jResult1.getIvCanvas().set("Scatter.y_Label", labelData2);
			jResult1.getIvCanvas().set("Scatter.title", label);
			jResult1.getIvCanvas().set("Scatter.file", strFile);
			
			if (bLegend)
				jResult1.getIvCanvas().set("Scatter.legend", "TRUE");
			else
				jResult1.getIvCanvas().set("Scatter.legend", "FALSE");
			
			jResult1.getIvCanvas().set("Scatter.update", "");
		}
		else if (type.equalsIgnoreCase("Bland_Altmann")){
			labelData1 = "Average";
			if (label1.contains("Volume")){
				labelData1 += "(ml)";
			}
			labelData2 = "Difference";
			if (label2.contains("Volume")){
				labelData2 += "(ml)";
			}
			
			jResult1.getIvCanvas().set("Bland_Altmann.x_Axial", value1);
//			jResult1.getIvCanvas().set("Bland_Altmann.x_Axial", "[23.0186,433.188,3.84375,12.6648,9.59167,119.257,48.186,451.794,9.01794,68.8843]");
			jResult1.getIvCanvas().set("Bland_Altmann.x_Label", labelData1);
			jResult1.getIvCanvas().set("Bland_Altmann.y_Axial", value2);
//			jResult1.getIvCanvas().set("Bland_Altmann.y_Axial", "[35.3452,710.467,86.7656,16.449,30.7617,163.889,68.9897,508.236,18.7073,148.343]");
			jResult1.getIvCanvas().set("Bland_Altmann.y_Label", labelData2);
			jResult1.getIvCanvas().set("Bland_Altmann.title", label);
			jResult1.getIvCanvas().set("Bland_Altmann.file", strFile);
			
			if (bLegend)
				jResult1.getIvCanvas().set("Bland_Altmann.legend", "TRUE");
			else
				jResult1.getIvCanvas().set("Bland_Altmann.legend", "FALSE");
			
			jResult1.getIvCanvas().set("Bland_Altmann.update", "");
			
		}
		
		return true;
	}
	
	public String computeStatisticsMean(List<Float> data){
		String value = formatData(data);
		if (value.isEmpty())
			return "";
		
		jResult1.getIvCanvas().set("Statistics.data", value);
		jResult1.getIvCanvas().set("Statistics.update", "");
		
		value = "";
		value = jResult1.getIvCanvas().get("Mean.string");
		
		return value;
	}
	
	public String computeStatisticsSD(List<Float> data){
		String value = formatData(data);
		if (value.isEmpty())
			return "";
		
		jResult1.getIvCanvas().set("Statistics.data", value);
		jResult1.getIvCanvas().set("Statistics.update", "");
		
		value = "";
		value = jResult1.getIvCanvas().get("SD.string");
		
		return value;
	}
	
	public String computeStatisticsQuantile(List<Float> data, String percentile){
		String value = formatData(data);
		if (value.isEmpty())
			return "";
		
		jResult1.getIvCanvas().set("Statistics.data", value);
		jResult1.getIvCanvas().set("Statistics.prob", percentile);
		jResult1.getIvCanvas().set("Statistics.update", "");
		
		value = "";
		value = jResult1.getIvCanvas().get("Quantile.string");
		
		return value;
	}
	
	public String computeStatisticsScript(List<Float> data, String expression){
		String value = "";
		int index = expression.indexOf("data");
		if (index == -1)
			return "";
		
		value = formatData(data);
		if (value.isEmpty())
			return "";
		
		jResult1.getIvCanvas().set("Script.data", value);
		jResult1.getIvCanvas().set("Script.script", expression);
		jResult1.getIvCanvas().set("Script.update", "");
		
		value = "";
		value = jResult1.getIvCanvas().get("Output.string");
		
		return value;
	}
	
	public String formatData(List<Float> data){
		int size = data.size();
		if (size <= 0)
			return "";
		
		String value = "[";
		for (int i = 0; i < size; i++){
			Float item = data.get(i);
			value += item.toString();
			value += ' ';
		}
		
		String buf = value.trim();
		value = buf.replace(' ', ',');
		value += "]";

		return value;
	}
	
	public void addStatisticsResult(List<Integer> count, int iteria, String type, String script){
		if (count.isEmpty())
			return;
		
		String strUID = "1.2.3.4.5.6.7.8.9.0";
		List<ComputationListEntry> entries = jResult1.getComputationResultsPanel().getXMLComputationList().getComputationListEntries();
		
		ComputationListEntry newItem = new ComputationListEntry();
	
		String name = type;
		for (int i = skip; i < iteria; i++)	{
			if (!count.contains(i)){
				continue;
			}
				
			List<Float> data = new ArrayList<Float>();
			
			for (int j = 0; j < entries.size(); j++){
				ComputationListEntry entry = entries.get(j);
				if (entry.getSubjectUID().equalsIgnoreCase(strUID))
					continue;
				
				try {
					String str = entry.getMeasurement(i-skip);
					if (!str.isEmpty()){
						float value = Float.valueOf(str);
						data.add(value);
					}
				} catch (NumberFormatException e){
					System.out.println("Error - convert measuresement to FOLAT");
				}
			}
			
			String result = "";
			if (type.equalsIgnoreCase("Mean")){
				result = computeStatisticsMean(data);
			}
			
			if (type.equalsIgnoreCase("SD")){
				result = computeStatisticsSD(data);
			}
			
			if (type.equalsIgnoreCase("Script")){
				int index = script.indexOf(":");
				name = script.substring(0, index);
				String buf = script.substring(index+1);

				result = computeStatisticsScript(data, buf);
			}
			
			String buf = result.replace('"', ' ');
			result = buf.trim();
			buf = result.replace('[', ' ');
			result = buf.trim();
			buf = result.replace(']', ' ');
			result = buf.trim();

			if (result.isEmpty())
				continue;

			newItem.setMeasurement(i-skip, result);
			
		}
		newItem.setSubjectName(name);
		newItem.setSubjectUID(strUID);
		jResult1.getComputationResultsPanel().getXMLComputationList().addComputationListEntry(newItem);
	}
	
	@SuppressWarnings("unchecked")
	private void updateOutlier() {
		jResult1.getComputationResultsPanel().clearOutlier();
		jResult1.getComputationResultsPanel().AddOutlier("Outliers:");
		
		String strUID = "1.2.3.4.5.6.7.8.9.0";
		List<ComputationListEntry> entries = jResult1.getComputationResultsPanel().getXMLComputationList().getComputationListEntries();
		
		String outlying = "";
		List<OutlierEntry> items = jTool.getAnalysisPanel().getOutlierManager().getOutlierEntries();
		for (int i = 0; i < items.size(); i++){
			String measurement = items.get(i).getCriteriaMeasurement();
			String scalling = items.get(i).getCriteriaScaling();
			if (!jResult1.getComputationResultsPanel().isColumnVisible(measurement))
				continue;
	
			String criteria = items.get(i).getCriteriaScalingID(scalling);
			outlying = measurement + ": " + scalling;
			List<Float> data = new ArrayList<Float>();
			int index = items.get(i).getStatisticsDataID(measurement);
			for (int j = 0; j < entries.size(); j++){
				
				ComputationListEntry entry = entries.get(j);
				if (entry.getSubjectUID().equalsIgnoreCase(strUID))
					continue;
				
				String buf = entry.getMeasurement(index-skip);
				buf.replace('"', ' ');
				buf.trim();
				if (!buf.isEmpty()) {
					try {
						float value = Float.valueOf(buf);
						data.add(value);
					} catch (NumberFormatException e){
						System.out.println("Convert to FLOAT failed");
					}
				}
			}
			
			if (data.isEmpty())
				continue;
			
			int len = criteria.length();
			
			String flag = criteria.substring(0, 1);
			
			String percentile = criteria.substring(1, len);
			String result = computeStatisticsQuantile(data, percentile);
			String buf = result.replace('"', ' ');
			result = buf.trim();
			buf = result.replace('[', ' ');
			result = buf.trim();
			buf = result.replace(']', ' ');
			result = buf.trim();

			if (result.isEmpty())
				return;
			
			buf = "";
			if (flag.equalsIgnoreCase(">"))
				buf = "greater than ";
			if (flag.equalsIgnoreCase("<"))
				buf = "smaller than ";
			
			String label = outlying + " (";
			label += buf;
			label += result;
			label += ")";
			
			jResult1.getComputationResultsPanel().AddOutlier(" ");
			jResult1.getComputationResultsPanel().AddOutlier(label);
			
			try{
				float fValue = Float.valueOf(result).floatValue();
				for (int k =0; k < data.size(); k++){
					boolean bOutlier = false;
					if (flag.equalsIgnoreCase(">")){
						if (data.get(k) > fValue)
							bOutlier = true;
					}
					if (flag.equalsIgnoreCase("<")){
						if (data.get(k) < fValue)
							bOutlier = true;
					}
					if (bOutlier){
						String subjectName = entries.get(k).getSubjectName();
						String subjectID = entries.get(k).getSubjectID();
						String subjectLabel = entries.get(k).getLabel();
						jResult1.getComputationResultsPanel().AddOutlier(String.format("%s - %s - %s", subjectName, subjectID, subjectLabel));
					}
				}
			} catch (NumberFormatException e){
				System.out.println("Convert to FLOAT failed");
			}
		}

	}

	private void calculateStatistic(StatisticEntry item, List<Integer> count, int iteria) {
		String statisticType = item.getStatisticMethod();
		jResult1.getComputationResultsPanel().AddStatistics("Statistical Method: " + statisticType);
		jResult1.getComputationResultsPanel().AddStatistics("Dependent Data: " + item.getStatisticData());
		
		String buffer = "Variable Data: ";
		List<String> variables = item.getMultipleData();
		for (int i = 0; i < variables.size(); i++){
			buffer += String.format("%s, ", variables.get(i));
		}
		jResult1.getComputationResultsPanel().AddStatistics(buffer);
		
		String strUID = "1.2.3.4.5.6.7.8.9.0";
		String type = jResult1.getComputationResultsPanel().getComputationType();
		List<Float> data = new ArrayList<Float>();
		List<List<Float>> factor = new ArrayList<List<Float>>();
		
		List<ComputationListEntry> entries = jResult1.getComputationResultsPanel().getXMLComputationList().getComputationListEntries();
		
		List<String> uids = new ArrayList<String>();
		for (int i = skip; i < iteria; i++)	{
			if (!count.contains(i)){
				continue;
			}
				
			for (int j = 0; j < entries.size(); j++){
				ComputationListEntry entry = entries.get(j);
				if (entry.getSubjectUID().equalsIgnoreCase(strUID))
					continue;
				
				uids.add(entry.getSubjectUID());
				
				try {
					String str = entry.getMeasurement(i-skip);
					if (!str.isEmpty()){
						float value = Float.valueOf(str);
						data.add(value);
					}
				} catch (NumberFormatException e){
					System.out.println("Error - convert measuresement to FOLAT");
				}
			}
		}
		
		if (statisticType.equalsIgnoreCase("Multiple Regression"))
			getMRegressionVariables(uids, factor, variables);
		if (statisticType.equalsIgnoreCase("N-way ANOVA"))
			getANOVAVariables(entries, factor, variables);
			
		List<String> result = new ArrayList<String>();
		if (statisticType.equalsIgnoreCase("Multiple Regression")){
			result = computeStatisticsMR(data, factor);
			if (result != null)
				if (result.size() == 5){
				jResult1.getComputationResultsPanel().AddStatistics("Residual: " + result.get(0));
				jResult1.getComputationResultsPanel().AddStatistics("Estimate: " + result.get(1));
				jResult1.getComputationResultsPanel().AddStatistics("Std: " + result.get(2));
				jResult1.getComputationResultsPanel().AddStatistics("F_Value: " + result.get(3));
				jResult1.getComputationResultsPanel().AddStatistics("P_Value: " + result.get(4));
			}
		}
		
		if (statisticType.equalsIgnoreCase("N-way ANOVA")){
			result = computeStatisticsANOVA(data, factor);
			if (result != null)
				if (result.size() == 5){
				jResult1.getComputationResultsPanel().AddStatistics("DF: " + result.get(0));
				jResult1.getComputationResultsPanel().AddStatistics("Sum: " + result.get(1));
				jResult1.getComputationResultsPanel().AddStatistics("Mean: " + result.get(2));
				jResult1.getComputationResultsPanel().AddStatistics("F_Value: " + result.get(3));
				jResult1.getComputationResultsPanel().AddStatistics("P_Value: " + result.get(4));
			}
		}
		jResult1.getComputationResultsPanel().AddStatistics("--The End--");
	}

	private void getANOVAVariables(List<ComputationListEntry> entries, List<List<Float>> factor,
			List<String> variables) {
		
		if (variables.size() < 0)
			return;
		
		String strUID = "1.2.3.4.5.6.7.8.9.0";
		for (int i = 0; i < variables.size(); i++){
			int index = factor.size();
			factor.add(new ArrayList<Float>());
			
			for (int j = 0; j < entries.size(); j++){
				ComputationListEntry entry = entries.get(j);
				if (entry.getSubjectUID().equalsIgnoreCase(strUID))
					continue;
				
				float value = getMeasurement(variables.get(i), entry);
				
				factor.get(index).add(value);
			}
		}			
	}
	
	private void getMRegressionVariables(List<String> uids, List<List<Float>> factor,
			List<String> variables) {
		
		List<IndependentVariables> dcmVariables = getDicomIndepentVariables(uids);
		if (dcmVariables == null || dcmVariables.size() <= 0)
			return;
		
		for (int i = 0; i < variables.size(); i++){
			if (variables.get(i).compareToIgnoreCase("Slice Thickness") == 0){
				int index = factor.size();
				factor.add(new ArrayList<Float>());
				
				for (int j = 0; j < dcmVariables.size(); j++){
					String value = dcmVariables.get(j).getSliceThickness();
					if (value == null)
						factor.get(index).add(-1.0f);
					else if	(value.isEmpty())
						factor.get(index).add(0.0f);						
					else
						factor.get(index).add(Float.valueOf(value.trim()).floatValue());
				}
			}
			
			if (variables.get(i).compareToIgnoreCase("Gender") == 0){
				int index = factor.size();
				factor.add(new ArrayList<Float>());
				
				for (int j = 0; j < dcmVariables.size(); j++){
					String value = dcmVariables.get(j).getGender();
					if (value == null)
						factor.get(index).add(-1.0f);
					else if	(value.isEmpty())
						factor.get(index).add(0.0f);						
					else if (value.compareToIgnoreCase("M") == 0)
						factor.get(index).add(1.0f);
					else if (value.compareToIgnoreCase("F") == 0)
						factor.get(index).add(2.0f);
					else
						factor.get(index).add(3.0f);
				}
			}	
			
			if (variables.get(i).compareToIgnoreCase("Exposure") == 0){
				int index = factor.size();
				factor.add(new ArrayList<Float>());
				
				for (int j = 0; j < dcmVariables.size(); j++){
					String value = dcmVariables.get(j).getExposure();
					if (value == null)
						factor.get(index).add(-1.0f);
					else if	(value.isEmpty())
						factor.get(index).add(0.0f);						
					else
						factor.get(index).add(Float.valueOf(value.trim()).floatValue());
				}
			}	
			
			if (variables.get(i).compareToIgnoreCase("Pitch") == 0){
				int index = factor.size();
				factor.add(new ArrayList<Float>());
				
				for (int j = 0; j < dcmVariables.size(); j++){
					String value = dcmVariables.get(j).getPitch();
					if (value == null)
						factor.get(index).add(-1.0f);
					else if	(value.isEmpty())
						factor.get(index).add(0.0f);						
					else
						factor.get(index).add(Float.valueOf(value.trim()).floatValue());
				}
			}	
			
			if (variables.get(i).compareToIgnoreCase("Collimation") == 0){
				int index = factor.size();
				factor.add(new ArrayList<Float>());
				
				for (int j = 0; j < dcmVariables.size(); j++){
					String value = dcmVariables.get(j).getCollimation();
					if (value == null)
						factor.get(index).add(-1.0f);
					else if	(value.isEmpty())
						factor.get(index).add(0.0f);						
					else
						factor.get(index).add(Float.valueOf(value.trim()).floatValue());
				}
			}	
			
			if (variables.get(i).compareToIgnoreCase("Reconstruction kernel") == 0){
				int index = factor.size();
				factor.add(new ArrayList<Float>());
				
				for (int j = 0; j < dcmVariables.size(); j++){
					String value = dcmVariables.get(j).getKernel();
					if (value == null)
						factor.get(index).add(-1.0f);
					else if	(value.isEmpty())
						factor.get(index).add(0.0f);						
					else if (value.compareToIgnoreCase("C") == 0)
						factor.get(index).add(1.0f);
					else if (value.compareToIgnoreCase("B10f") == 0)
						factor.get(index).add(2.0f);
					else if (value.compareToIgnoreCase("B") == 0)
						factor.get(index).add(3.0f);
					else
						factor.get(index).add(4.0f);
				}
			}	
		}		
	}
	
	private List<IndependentVariables> getDicomIndepentVariables(
			List<String> uids) {
		ADFacade facade = ADFactory.getADServiceInstance();
		if (facade == null){
			System.out.println("fail to create ADFacade");
			return null;
		}
		
		List<IndependentVariables> variables = new ArrayList<IndependentVariables>();
		for (int i = 0; i < uids.size(); i++){
			IndependentVariables variable = new IndependentVariables();
			
			String seriesInstanceUID = uids.get(i);
			HashMap<Integer, Object> dicomCriteria = new HashMap<Integer, Object>();
			dicomCriteria.put(Tag.SeriesInstanceUID, seriesInstanceUID);
			List<GeneralImage> images = facade.findImagesByCriteria(dicomCriteria, null);
			if (images.size() > 0 ){
				String sopInstanceUID = images.get(0).getSOPInstanceUID();
				DicomObject dcmInfor = facade.getDicomObject(sopInstanceUID);

				variable.setSliceThickness(String.format("%.1f", dcmInfor.getFloat(Tag.SliceThickness)));
				variable.setGender(dcmInfor.getString(Tag.PatientSex));
				variable.setCollimation(dcmInfor.getString(Tag.SingleCollimationWidth));
				variable.setExposure(dcmInfor.getString(Tag.Exposure));
				variable.setKernel(dcmInfor.getString(Tag.ConvolutionKernel));
				variable.setPitch(dcmInfor.getString(Tag.SpiralPitchFactor));
			}
			variables.add(variable);
		}
		
		return variables;
	}
	private String getSliceThickness(String seriesInstanceUID) {
		ADFacade facade = ADFactory.getADServiceInstance();
		if (facade == null){
			System.out.println("fail to create ADFacade");
			return "";
		}
		
		HashMap<Integer, Object> dicomCriteria = new HashMap<Integer, Object>();
		dicomCriteria.put(Tag.SeriesInstanceUID, seriesInstanceUID);
		List<GeneralImage> images = facade.findImagesByCriteria(dicomCriteria, null);
		if (images.size() <=0 )
			return "";
		
		return images.get(0).getSliceThickness();
	}
	
	private List<String> computeStatisticsANOVA(List<Float> data, List<List<Float>> factor) {
		if (factor.size() > 8)
			return null;
		
		String buffer = formatData(data);
		
		jResult1.getIvCanvas().set("Anova.data0", buffer);
		
		for (int i = 0; i < factor.size(); i++){
			List<Float> _factor = factor.get(i);
			buffer = formatData(_factor);
			
			jResult1.getIvCanvas().set(String.format("Anova.data%d", i+1), buffer);
		}
		
		jResult1.getIvCanvas().set("Anova.update", "");
		
		List<String> result = new ArrayList<String>();
		result.add(jResult1.getIvCanvas().get("Anova_Df.string"));
		result.add(jResult1.getIvCanvas().get("Anova_Sum.string"));
		result.add(jResult1.getIvCanvas().get("Anova_Mean.string"));
		result.add(jResult1.getIvCanvas().get("Anova_Fvalue.string"));
		result.add(jResult1.getIvCanvas().get("Anova_Pvalue.string"));
		
		return result;
	}
	
	private List<String> computeStatisticsMR(List<Float> data,
			List<List<Float>> factor) {
		if (factor.size() > 8)
			return null;
		
		String _data = formatData(data);
		jResult1.getIvCanvas().set("MultipleRegression.dependentY", _data);
		
		for (int i = 0; i < factor.size(); i++){
			List<Float> _factor = factor.get(i);
			String buffer = formatData(_factor);
			
			jResult1.getIvCanvas().set(String.format("MultipleRegression.variable%d", i), buffer);
		}
		
		jResult1.getIvCanvas().set("MultipleRegression.update", "");
		
		List<String> result = new ArrayList<String>();
		result.add(jResult1.getIvCanvas().get("MRegression_Residual.string"));
		result.add(jResult1.getIvCanvas().get("MRegression_Estimate.string"));
		result.add(jResult1.getIvCanvas().get("MRegression_Std.string"));
		result.add(jResult1.getIvCanvas().get("MRegression_Fvalue.string"));
		result.add(jResult1.getIvCanvas().get("MRegression_Pvalue.string"));
		
		return result;
	}
	
	public void setOutputDir(String outDir) {
		this.outDir = outDir;
		jResult1.setOutputDir(outDir);		
	}
	
	public void loadInputAims(String aimFolder){
		jResult0.loadAimsFromFolder(aimFolder);
	}
	
	public void updateToolAim(){
		List<String> identifier = jResult0.getIdentifier();
		jTool.setIdentifiers(identifier);
	}
	public void cleanup() {
		jResult1.stopRServe();		
	}
}
