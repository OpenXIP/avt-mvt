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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.siemens.cmiv.avt.mvt.datatype.SubjectListEntry;

/**
 * Parses XML document.
 * @author Jie Zheng
 */
public class XMLSubjectList {	
	SAXBuilder builder = new SAXBuilder();
	Document document;
	Element root;	
	List<SubjectListEntry> entries = new ArrayList<SubjectListEntry>();
	
	/**
	 * @param args
	 */	
	public XMLSubjectList(){
			
	}

	public boolean addSubjectListEntry(SubjectListEntry entry) {
		return false;
	}

	public boolean deleteSubjectListEntry(SubjectListEntry entry) {
		// TODO Auto-generated method stub
		return false;
	}	

	public List getSubjectListEntries() {		
		return entries;
	}

	public boolean loadSubjectList(File xmlSubjectListFile) {
		try {
			document = builder.build(xmlSubjectListFile);
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		root = document.getRootElement();
		Element items = (root.getChild("Subjects"));	
		int numItems = items.getChildren().size();		
		
		for(int i = 0; i < numItems; i++){
			Element item = ((Element)items.getChildren().get(i));			
			SubjectListEntry entry = new SubjectListEntry();
			String subjectName = item.getChild("Subject_Name").getValue();
			entry.setSubjectName(subjectName);
			String subjectStudyUID = item.getChild("StudyInstanceUID").getValue();
			entry.setStudyInstanceUIDCurr(subjectStudyUID);
			String subjectSeriesUID = item.getChild("SeriesInstanceUID").getValue();
			entry.setSeriesInstanceUIDCurr(subjectSeriesUID);
			
			Element subItem =(item.getChild("StudyInstanceUID").getChild("SeriesInstanceUID")); 
			String studyID = subItem.getAttributeValue("ID"); 
			entry.setSubjectID(studyID);
			String studyGender = subItem.getAttributeValue("Sex"); 
			entry.setSubjectGender(studyGender);
			String studyAge = subItem.getAttributeValue("Age"); 
			entry.setSubjectAge(studyAge);
			String studyModality = subItem.getAttributeValue("Modality"); 
			entry.setStudyModality(studyModality);
			String studyDate = subItem.getAttributeValue("Date"); 
			entry.setStudyDate(studyDate);
			String studyTime = subItem.getAttributeValue("Time"); 
			entry.setStudyTime(studyTime);
			String studyDescription = subItem.getAttributeValue("StudyDescription"); 
			entry.setStudyDescription(studyDescription);
			
			String nominalGTUID = item.getChild("Nominal_GT").getChild("GT_UID").getValue();
			entry.setNominalGTUID(nominalGTUID);
			String nominalGT = item.getChild("Nominal_GT").getAttributeValue("Annotator"); 
			entry.setNominalGT(nominalGT);
			String nominalGTDate = item.getChild("Nominal_GT").getAttributeValue("Date"); 
			entry.setNominalGTDate(nominalGTDate);
			String nominalGTTime = item.getChild("Nominal_GT").getAttributeValue("Time"); 
			entry.setNominalGTTime(nominalGT);
			String nominalGTVolume = item.getChild("Nominal_GT").getAttributeValue("Volume"); 
			entry.setNominalGTVolume(nominalGTVolume);
			
			String annotationUID = (item.getChild("Annotation").getChild("Annotation_UID")).getValue();
			entry.setAnnotationUID(annotationUID);
			String annotator = item.getChild("Annotation").getAttributeValue("Annotator"); 
			entry.setAnnotator(annotator);
			String annotationDate = item.getChild("Annotation").getAttributeValue("Date"); 
			entry.setAnnotationDate(annotationDate);
			String annotationTime = item.getChild("Annotation").getAttributeValue("Time"); 
			entry.setAnnotationTime(annotationTime);
			String annotationVolume = item.getChild("Annotation").getAttributeValue("Volume"); 
			entry.setAnnotationVolume(annotationVolume);
		
			entries.add(entry);
		}	
		return true;		
	}

	public boolean modifySubjectListEntry(SubjectListEntry entry) {
		// TODO Auto-generated method stub
		return false;
	}

	public SubjectListEntry getSubjectListEntry(int i) {		
		return entries.get(i);
	}

	public int getNumberOfSubjectListEntries() {		
		return entries.size();
	}	
	
	public List getSubjectlistEntries() {
		// TODO Auto-generated method stub
		return entries;
	}

	public boolean addSubjectlistEntry(SubjectListEntry entry) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean deleteSubjectlistEntry(SubjectListEntry entry) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean storeSubjectList(List<SubjectListEntry> subjects,
			File xmlSubjectListFile) {
		// TODO Auto-generated method stub
		return false;
	}
}
