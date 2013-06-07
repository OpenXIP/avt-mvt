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

import javax.swing.JProgressBar;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import com.siemens.cmiv.avt.mvt.datatype.ComputationHeader;
import com.siemens.cmiv.avt.mvt.datatype.ComputationListEntry;

/**
 * Parses XML document.
 * @author Jie Zheng
 */
public class XMLComputationList{	
	SAXBuilder builder = new SAXBuilder();
	Document document;
	Element root;	
	List<ComputationListEntry> entries = new ArrayList<ComputationListEntry>();
	List<String> datalist = new ArrayList<String>();
	
	JProgressBar progressBar = null;
	
	/**
	 * @param args
	 */	
	public XMLComputationList(){
			
	}
	
	public void clearEntries(){
		entries.clear();
	}

	public boolean addComputationListEntry(ComputationListEntry entry) {
		entries.add(entry);
		
		return true;
	}

	public boolean deleteComputationListEntry(ComputationListEntry entry) {
		// TODO Auto-generated method stub
		return true;
	}	
	
	public boolean deleteComputationListEntry(String subjectUID) {
		int i = 0;
		int size = entries.size();
		for (i = 0; i < size; i++){
			ComputationListEntry item = entries.get(i);
			if (item.getSubjectUID().equalsIgnoreCase(subjectUID)) {
				entries.remove(i);
				
				i = 0;
				size = entries.size();
			}
		}
			
		return false;
	}	

	public List getComputationListEntries() {		
		return entries;
	}

	public void setDatalist(List<String> datalist){
		this.datalist.clear();
		this.datalist.addAll(datalist);
	}
	
	public boolean loadComputationList(File xmlComputationListFile) {
		try {
			document = builder.build(xmlComputationListFile);
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		root = document.getRootElement();
		Element items = (root.getChild("Items"));	
		int numItems = items.getChildren().size();		
		
		entries.clear();
		for(int i = 0; i < numItems; i++){
			Element item = ((Element)items.getChildren().get(i));			
			ComputationListEntry entry = new ComputationListEntry();
			String subjectName = item.getChild("Subject_Name").getValue();
			entry.setSubjectName(subjectName);
			String subjectID = item.getChild("Subject_ID").getValue();
			entry.setSubjectID(subjectID);
			String subjectSeriesUID = item.getChild("Subject_SeriesInstanceUID").getValue();
			entry.setSubjectUID(subjectSeriesUID);
			
			boolean bTheOne = true;
			if (!datalist.contains(subjectSeriesUID))
				bTheOne = false;
			
			String volumeDifference = item.getChild("SignedVolumeDifference").getValue();
			entry.setVolumeDifference(volumeDifference);
			String relativeVolumeDifference = item.getChild("AbsoluteRelativeVolumeDifference").getValue();
			entry.setRelativeVolumeDifference(relativeVolumeDifference);
			String averageSurfaceDistance = item.getChild("AverageSymmetricSurfaceDistance").getValue();
			entry.setAverageSurfaceDistance(averageSurfaceDistance);
			String averageRMSSurfaceDistance = item.getChild("AverageSymmetricRMSSurfaceDistance").getValue();
			entry.setAverageRMSSurfaceDistance(averageRMSSurfaceDistance);
			String maximumSurfaceDistance = item.getChild("MaximumSurfaceDistance").getValue();
			entry.setMaximumSurfaceDistance(maximumSurfaceDistance);
			String volumeOverlap = item.getChild("VolumeOverlap").getValue();
			entry.setVolumeOverlap(volumeOverlap);
		
			if (bTheOne)
				entries.add(entry);
		}	
		return true;		
	}

	public boolean modifyComputationListEntry(ComputationListEntry entry) {
		// TODO Auto-generated method stub
		return false;
	}

	public ComputationListEntry getComputationListEntry(int i) {		
		return entries.get(i);
	}

	public int getNumberOfComputationListEntries() {		
		return entries.size();
	}	
	
	public boolean storeComputationList(ComputationHeader header, List<ComputationListEntry> computations, File xmlComputationListFile){
		if(computations == null || xmlComputationListFile == null){return false;}
		Element main_root = new Element("AVT");	
		Element version = new Element("version");
		Element comment = new Element("comment");
		Element computation = new Element("Computation");
		Element _id = new Element("Computation_ID");
		Element _date = new Element("Computation_Date");
		Element _time = new Element("Computation_Time");
		Element _uid = new Element("Computation_UID");
		Element _refUID = new Element("Experiment_UID");
		
		main_root.addContent(version);
		version.setText("v0.2");
		main_root.addContent(comment);
		comment.setText("For Internal Use Only!");
		main_root.addContent(computation);
			computation.addContent(_id);
				_id.setText(header.getComputationID());
			computation.addContent(_date);
				_date.setText(header.getComputationDate());
			computation.addContent(_time);
				_time.setText(header.getComputationTime());
			computation.addContent(_uid);
				_uid.setText(header.getComputationUID());
			computation.addContent(_refUID);
				_refUID.setText(header.getComputationRefUID());
		
		Element root = new Element("Items");						
		for(int i = 0; i < computations.size(); i++){						
			Element item = new Element("Item");                
			Element name = new Element("Subject_Name");
			Element id = new Element("Subject_ID");
			Element uid = new Element("Subject_SeriesInstanceUID");
			Element volDiff = new Element("SignedVolumeDifference");
			Element normalizedVolDiff = new Element("AbsoluteRelativeVolumeDifference");
			Element avgSurfDist = new Element("AverageSymmetricSurfaceDistance");
			Element rmsSurfDist = new Element("AverageSymmetricRMSSurfaceDistance");
			Element maxSurfDist = new Element("MaximumSurfaceDistance");
			Element volumeOverlapRatio = new Element("VolumeOverlap");
			root.addContent(item);        	                	                                        		        
				item.addContent(name);
					name.setText(computations.get(i).getSubjectName());
				item.addContent(id);			            
					id.setText(computations.get(i).getSubjectID());						
				item.addContent(uid);
					uid.setText(computations.get(i).getSubjectUID());
				item.addContent(volDiff);
					volDiff.setText(computations.get(i).getVolumeDifference());	       	
				item.addContent(normalizedVolDiff);
					normalizedVolDiff.setText(computations.get(i).getRelativeVolumeDifference());	       	
				item.addContent(avgSurfDist);
					avgSurfDist.setText(computations.get(i).getAverageSurfaceDistance());	       	
				item.addContent(rmsSurfDist);
					rmsSurfDist.setText(computations.get(i).getAverageRMSSurfaceDistance());	       	
				item.addContent(maxSurfDist);
					maxSurfDist.setText(computations.get(i).getMaximumSurfaceDistance());	       	
				item.addContent(volumeOverlapRatio);
					volumeOverlapRatio.setText(computations.get(i).getVolumeOverlap());	       	
		}	

		main_root.addContent(root);
    	Document document = new Document(main_root);
    	FileOutputStream outStream;
		try {
			outStream = new FileOutputStream(xmlComputationListFile);
			XMLOutputter outToXMLFile = new XMLOutputter();
			outToXMLFile.setFormat(Format.getPrettyFormat());
	    	outToXMLFile.output(document, outStream);
	    	outStream.flush();
	    	outStream.close();
	    	return true;
		} catch (FileNotFoundException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
	}

	public boolean storeComputationList(
			List<ComputationListEntry> computations, File xmlComputationListFile) {
		// TODO Auto-generated method stub
		return false;
	}
}
