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
package com.siemens.cmiv.avt.mvt.plot;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

/**
 * @author Jie Zheng
 *
 */
public class PlotManagerImpl implements PlotManager{
	SAXBuilder builder = new SAXBuilder();
	List<PlotEntry> entries = new ArrayList<PlotEntry>();
	Document document;

	/**
	 * 
	 */
	public PlotManagerImpl() {
		// TODO Auto-generated constructor stub
	}
	public boolean addPlotEntry(PlotEntry entry) {
		for (int i = 0; i < entries.size(); i++)
		{
			PlotEntry item = entries.get(i);
			if (!item.compareEntry(entry))
				return false;
		}
		entries.add(entry);
		return true;
	}

	public boolean deletePlotEntry(int index) {
		if (index < 0 || index >= entries.size())
			return false;
		
		entries.remove(index);
		return true;
	}	

	public List<PlotEntry> getPlotEntries() {		
		return entries;
	}

	public boolean loadPlot(File xmlPlotFile){
		if (!xmlPlotFile.exists())
		{
			System.out.println(xmlPlotFile.getPath()+": not exist!");
			return false;
		}
		
		try {
			document = builder.build(xmlPlotFile);
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Element root = document.getRootElement();
		
		Element items = (root.getChild("Items"));	
		int numItems = items.getChildren().size();		
		
		for(int i = 0; i < numItems; i++){
			Element item = ((Element)items.getChildren().get(i));			
			PlotEntry entry = new PlotEntry();
			
			String data1 = item.getChild("Data1").getValue(); 
			entry.setPlotData1(data1);
			
			String data2 = item.getChild("Data2").getValue(); 
			entry.setPlotData2(data2);
			
			String title = item.getChild("Title").getValue(); 
			entry.setPlotTitle(title);

			String legend = item.getChild("Legend").getValue();
			if (legend.compareToIgnoreCase("on") == 0)
				entry.setLegend(true);
			else
				entry.setLegend(false);
			
			String chart = item.getChild("Chart").getValue(); 
			entry.setPlotChart(chart);
			
			entries.add(entry);
		}	
		return true;		
	}

	public PlotEntry getPlotEntry(int i) {		
		return entries.get(i);
	}

	public int getNumberOfPlotEntries() {		
		return entries.size();
	}
		
	public boolean storePlot(List<PlotEntry> plottings, File xmlPlotFile){
		if(plottings == null || xmlPlotFile == null){return false;}

		Element root = new Element("AVT");	
		Element version = new Element("version");
		Element comment = new Element("comment");
		Element comparison = new Element("Plot");
		Element _id = new Element("Plot_ID");
		Element _date = new Element("Plot_Date");
		Element _time = new Element("Plot_Time");
		Element _uid = new Element("Plot_UID");
		Element _refUID = new Element("Experiment_UID");
		
		root.addContent(version);
		version.setText("v0.2");
		root.addContent(comment);
		comment.setText("For Internal Use Only!");
		root.addContent(comparison);
			comparison.addContent(_id);
				_id.setText("123");
			comparison.addContent(_date);
				_date.setText("20090523");
			comparison.addContent(_time);
				_time.setText("135616");
			comparison.addContent(_uid);
				_uid.setText("1.2.5.6.7894");
			comparison.addContent(_refUID);
				_refUID.setText("1.2.3.4.5.6.345");
		

		Element subRoot = new Element("Items");						
		for(int i = 0; i < plottings.size(); i++){						
			PlotEntry entry = plottings.get(i);
			
			Element item = new Element("Item");
			subRoot.addContent(item);  
			      	  
			Element data1 = new Element("Data1");
			item.addContent(data1);
			data1.setText(entry.getPlotData1());
			
			Element data2 = new Element("Data2");
			item.addContent(data2);
			data2.setText(entry.getPlotData2());
			
			Element title = new Element("Title");
			item.addContent(title);
			title.setText(entry.getPlotTitle());

			Element chart = new Element("Chart");
			item.addContent(chart);
			chart.setText(entry.getPlotChart());
		}
		
		root.addContent(subRoot);		                	
    
	    Document output = new Document(root);
	    FileOutputStream outStream;
		try {
			outStream = new FileOutputStream(xmlPlotFile);
			XMLOutputter outToXMLFile = new XMLOutputter();
			outToXMLFile.setFormat(Format.getPrettyFormat());
	    	outToXMLFile.output(output, outStream);
	    	outStream.flush();
	    	outStream.close();
	    	return true;
		} catch (FileNotFoundException e) {
			return false;
		} catch (IOException e) {
			return false;
		}    	                    
	}
}
