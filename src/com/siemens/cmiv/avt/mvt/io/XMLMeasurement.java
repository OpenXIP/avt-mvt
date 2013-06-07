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
package com.siemens.cmiv.avt.mvt.io;

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
 * Parses XML document.
 * @author Jie Zheng
 */
public class XMLMeasurement {	
	SAXBuilder builder = new SAXBuilder();
	List<String> entries = new ArrayList<String>();
	
	/**
	 * @param args
	 */	
	public XMLMeasurement(){
			
	}

	public boolean addMeasurementEntry(String entry) {
		entries.add(entry);
		
		return true;
	}

	public boolean deleteMeasurementEntry(String entry) {
		// TODO Auto-generated method stub
		return false;
	}	

	public List getMeasurementEntries() {		
		return entries;
	}

	public boolean loadMeasurement(File xmlMeasurementFile) throws JDOMException, IOException {
		Document document = builder.build(xmlMeasurementFile);
		
		Element root = document.getRootElement();
		Element items = (root.getChild("Measurements"));	
		int numItems = items.getChildren().size();		
		
		for(int i = 0; i < numItems; i++){
			Element item = ((Element)items.getChildren().get(i));			

			String entry = item.getValue();
			entries.add(entry);
		}	
		return true;		
	}

	public String getMeasurementEntry(int i) {		
		return entries.get(i);
	}

	public int getNumberOfMeasurementEntries() {		
		return entries.size();
	}	
	
	public boolean storeMeasurement(List<String> measurements, File xmlMeasurementFile) throws JDOMException, IOException{
		if(measurements == null || xmlMeasurementFile == null){return false;}
		Document document = builder.build(xmlMeasurementFile);
		Element  root = document.getRootElement();

		Element subRoot = new Element("Measurements");						
		for(int i = 0; i < measurements.size(); i++){						
			Element item = new Element("Measurement");
			root.addContent(item);        	                	                                        		        
				item.setText(measurements.get(i));
		}
		
		root.addContent(subRoot);		                	
    
	    Document output = new Document(root);
	    FileOutputStream outStream;
		try {
			outStream = new FileOutputStream(xmlMeasurementFile);
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
