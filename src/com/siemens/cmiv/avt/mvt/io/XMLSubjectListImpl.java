package com.siemens.cmiv.avt.mvt.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.dcm4che2.data.Tag;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.siemens.cmiv.avt.mvt.ad.ADSearchResult;
import com.siemens.cmiv.avt.mvt.datatype.AnnotationEntry;
import com.siemens.cmiv.avt.mvt.datatype.FilterEntry;
import com.siemens.cmiv.avt.mvt.datatype.SubjectListEntry;

/**
 * Parses XML document.
 * @author Jie Zheng
 */
public class XMLSubjectListImpl implements SubjectList{	
	SAXBuilder builder = new SAXBuilder();
	Document document;
	Element root;	
	List<SubjectListEntry> entries = new ArrayList<SubjectListEntry>();
	
	FilterEntry filter = new FilterEntry();
	
	/**
	 * @param args
	 */	
	public XMLSubjectListImpl(){
			
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

	public boolean loadSubjectList(File xmlSubjectListFile){
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
		
		entries.clear();
		for(int i = 0; i < numItems; i++){
			Element item = ((Element)items.getChildren().get(i));	
			
			SubjectListEntry entry = new SubjectListEntry();
			
			entry.setSubjectStatus(true);
			
			String subjectName = item.getChild("Subject_Name").getValue();
			entry.setSubjectName(subjectName);
			String subjectStudyUID = item.getChild("StudyInstanceUID").getValue();
			entry.setStudyInstanceUIDCurr(subjectStudyUID);
			String subjectSeriesUID = item.getChild("SeriesInstanceUID").getValue();
			entry.setSeriesInstanceUIDCurr(subjectSeriesUID);
			
			String studyID = item.getChild("Subject_ID").getValue(); 
			entry.setSubjectID(studyID);
			String studyGender = item.getChild("Subject_Gender").getValue(); 
			entry.setSubjectGender(studyGender);
			String studyAge = item.getChild("Subject_Age").getValue(); 
			entry.setSubjectAge(studyAge);
			String studyModality = item.getChild("Modality").getValue(); 
			entry.setStudyModality(studyModality);
			String studyDate = item.getChild("StudyDate").getValue(); 
			entry.setStudyDate(studyDate);
			String studyTime = item.getChild("StudyTime").getValue(); 
			entry.setStudyTime(studyTime);
			String sliceThickness = item.getChild("SliceThickness").getValue(); 
			entry.setSliceThickness(sliceThickness);
			String studyDescription = item.getChild("StudyDescription").getValue(); 
			entry.setStudyDescription(studyDescription);
			
			List<AnnotationEntry> annotations = entry.getAnnotations();
			annotations.clear();
			
			Element subItems = (item.getChild("Annotations"));	
			int subNum = subItems.getChildren().size();	
			for (int j = 0; j < subNum; j++){
				AnnotationEntry subEntry = new AnnotationEntry();
				
				Element AnnotationItem = ((Element)subItems.getChildren().get(j));			
				String annotationUID = (AnnotationItem.getChild("Annotation_UID")).getValue();
				subEntry.setAnnotationUID(annotationUID);
				String annotator = AnnotationItem.getChild("Annotator").getValue(); 
				subEntry.setAnnotationReader(annotator);
				String annotationTimeStamp = AnnotationItem.getChild("TimePoint").getValue(); 
				subEntry.setAnnotationStamp(annotationTimeStamp);
				String annotationVolume = AnnotationItem.getChild("Annotation_Type").getValue(); 
				subEntry.setAnnotationType(annotationVolume);
				String annotationFile = AnnotationItem.getChild("Reference_File").getValue(); 
				subEntry.setAnnotationFile(annotationFile);
				String segFile = AnnotationItem.getChild("DicomSeg_File").getValue(); 
				subEntry.setDcmSegFile(segFile);
				
				subEntry.setSliceThickness(sliceThickness);
				
				annotations.add(subEntry);
			}
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

	@Override
	public boolean setFilterList(FilterEntry filter) {
		// TODO Auto-generated method stub
		this.filter.setAge(filter.getAge());
		this.filter.setGender(filter.getGender());
		this.filter.setSliceThickness(filter.getSliceThickness());
		this.filter.setAnnotator(filter.getAnnotator());
		
		return true;
	}

	@Override
	public void updateQuerySubjectList(ADSearchResult result) {
		entries.clear();
		
		for(int i = 0; i < result.getPatients().size(); i++){
			HashMap<Integer, Object> patient = result.getPatients().get(i);	
			
			SubjectListEntry entry = new SubjectListEntry();
	
			entry.setSubjectStatus(true);
			entry.setSubjectName((String)patient.get(Tag.PatientName));
			entry.setSubjectID((String)patient.get(Tag.PatientID));
			entry.setSubjectGender((String)patient.get(Tag.PatientSex));
			entry.setSubjectAge((String)patient.get(Tag.PatientAge));
			
			entry.setStudyDate((String)patient.get(Tag.StudyDate));
			entry.setStudyTime((String)patient.get(Tag.StudyTime));
			
			entry.setStudyDescription((String)patient.get(Tag.StudyDescription));
			entry.setStudyInstanceUIDCurr((String)patient.get(Tag.StudyInstanceUID));
			
			entry.setStudyModality((String)patient.get(Tag.Modality));
			entry.setSeriesInstanceUIDCurr((String)patient.get(Tag.SeriesInstanceUID));
			
			entries.add(entry);
		}
	}
}
