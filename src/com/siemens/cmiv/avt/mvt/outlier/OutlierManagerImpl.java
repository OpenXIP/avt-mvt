package com.siemens.cmiv.avt.mvt.outlier;

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
public class OutlierManagerImpl implements OutlierManager{
	ComparisonHeader header;
	SAXBuilder builder = new SAXBuilder();
	List<OutlierEntry> entries = new ArrayList<OutlierEntry>();
	Document document;

	/**
	 * 
	 */
	public OutlierManagerImpl() {
		// TODO Auto-generated constructor stub
	}
	public boolean addOutlierEntry(OutlierEntry entry) {
		for (int i = 0; i < entries.size(); i++)
		{
			OutlierEntry item = entries.get(i);
			if (item.getCriteriaMeasurement().equalsIgnoreCase(entry.getCriteriaMeasurement()) && item.getCriteriaScaling().equalsIgnoreCase(entry.getCriteriaScaling()))
				return false;
		}
		entries.add(entry);
		return true;
	}

	public boolean deleteOutlierEntry(int index) {
		if (index < 0 || index >= entries.size())
			return false;
		
		entries.remove(index);
		return true;
	}	

	public List OutlierEntries() {		
		return entries;
	}

	public boolean loadOutlier(File xmlOutlierFile){
		if (!xmlOutlierFile.exists())
		{
			System.out.println(xmlOutlierFile.getPath()+": not exist!");
			return false;
		}
		
		try {
			document = builder.build(xmlOutlierFile);
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
			OutlierEntry entry = new OutlierEntry();
			
			String data = item.getChild("Measurement").getValue(); 
			entry.setCriteriaMeasurement(data);
			
			String scaling = item.getChild("Scaling").getValue(); 
			entry.setCriteriaScaling(scaling);
		
			entries.add(entry);
		}	
		return true;		
	}

	public OutlierEntry getOutlierEntry(int i) {		
		return entries.get(i);
	}

	public int getNumberOfOutlierEntries() {		
		return entries.size();
	}
		
	public boolean storeOutlier(List<OutlierEntry> outliers, File xmlOutlierFile){
		if(outliers == null || xmlOutlierFile == null){return false;}

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
		for(int i = 0; i < outliers.size(); i++){						
			OutlierEntry entry = outliers.get(i);
			
			Element item = new Element("Item");
			subRoot.addContent(item);  
			      	  
			Element data = new Element("Measurement");
			item.addContent(data);
			data.setText(entry.getCriteriaMeasurement());

			Element scaling = new Element("Scaling");
			item.addContent(scaling);
			scaling.setText(entry.getCriteriaScaling());
		}
		
		root.addContent(subRoot);		                	
    
	    Document output = new Document(root);
	    FileOutputStream outStream;
		try {
			outStream = new FileOutputStream(xmlOutlierFile);
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
	
	public List getOutlierEntries() {
		return entries;
	}
}
