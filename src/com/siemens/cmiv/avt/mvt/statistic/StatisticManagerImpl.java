package com.siemens.cmiv.avt.mvt.statistic;

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

import com.siemens.cmiv.avt.mvt.datatype.ComparisonHeader;

/**
 * @author Jie Zheng
 *
 */
public class StatisticManagerImpl implements StatisticManager{
	ComparisonHeader header;
	List<StatisticEntry> entries = new ArrayList<StatisticEntry>();

	/**
	 * 
	 */
	public StatisticManagerImpl() {
	}
	public boolean addStatisticEntry(StatisticEntry entry) {
		for (int i = 0; i < entries.size(); i++)
		{
			StatisticEntry item = entries.get(i);
			if (item.getStatisticData().equalsIgnoreCase(entry.getStatisticData()) && item.getStatisticMethod().equalsIgnoreCase(entry.getStatisticMethod()))
				return false;
		}
		entries.add(entry);
		return true;
	}

	public boolean deleteStatisticEntry(int index) {
		if (index < 0 || index >= entries.size())
			return false;
		
		entries.remove(index);
		return true;
	}	

	@SuppressWarnings("unchecked")
	public List getStatisticEntries() {		
		return entries;
	}

	public boolean loadStatistic(File xmlStatisticFile){
		
		if (!xmlStatisticFile.exists())
		{
			System.out.println(xmlStatisticFile.getPath()+": not exist!");
			return false;
		}
		
		try {
			SAXBuilder builder = new SAXBuilder();
			Document document;
		
			document = builder.build(xmlStatisticFile);
		
			Element root = document.getRootElement();
			
			Element items = (root.getChild("Items"));	
			int numItems = items.getChildren().size();		
			
			for(int i = 0; i < numItems; i++){
				Element item = ((Element)items.getChildren().get(i));			
				StatisticEntry entry = new StatisticEntry();
				
				String data = item.getChild("Data").getValue(); 
				entry.setStatisticData(data);
				
				String statisticMethod = item.getChild("Method").getValue(); 
				entry.setStatisticMethod(statisticMethod);
			
				entries.add(entry);
			}	
		} catch (JDOMException e) {
			System.out.println("loadStatistics error");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("loadStatistics error");
			e.printStackTrace();
		}
		return true;		
	}

	public StatisticEntry getStatisticEntry(int i) {		
		return entries.get(i);
	}

	public int getNumberOfStatisticEntries() {		
		return entries.size();
	}
		
	public boolean storeStatistic(List<StatisticEntry> statistics, File xmlStatisticsFile){
		if(statistics == null || xmlStatisticsFile == null){return false;}

		Element root = new Element("AVT");	
		Element version = new Element("version");
		Element comment = new Element("comment");
		Element comparison = new Element("Comparison");
		Element _id = new Element("Comparison_ID");
		Element _date = new Element("Comparison_Date");
		Element _time = new Element("Comparison_Time");
		Element _uid = new Element("Comparison_UID");
		Element _refUID = new Element("Experiment_UID");
		
		root.addContent(version);
		version.setText("v0.2");
		root.addContent(comment);
		comment.setText("For Internal Use Only!");
		root.addContent(comparison);
			comparison.addContent(_id);
				_id.setText(header.getComparisonID());
			comparison.addContent(_date);
				_date.setText(header.getComparisonDate());
			comparison.addContent(_time);
				_time.setText(header.getComparisonTime());
			comparison.addContent(_uid);
				_uid.setText(header.getComparisonUID());
			comparison.addContent(_refUID);
				_refUID.setText(header.getComparisonRefUID());
		

		Element subRoot = new Element("Items");						
		for(int i = 0; i < statistics.size(); i++){						
			StatisticEntry entry = statistics.get(i);
			
			Element item = new Element("Item");
			subRoot.addContent(item);  
			      	  
			Element data = new Element("Data");
			item.addContent(data);
			data.setText(entry.getStatisticData());

			Element method = new Element("Method");
			item.addContent(method);
				method.setText(entry.getStatisticMethod());
		}
		
		root.addContent(subRoot);		                	
    
	    Document output = new Document(root);
	    FileOutputStream outStream;
		try {
			outStream = new FileOutputStream(xmlStatisticsFile);
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
