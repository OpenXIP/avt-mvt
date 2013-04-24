package com.siemens.cmiv.avt.mvt.datatype;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jie Zheng
 *
 */
public class RetrieveResult {
	String				subjectName;
	String				subjectID;
	String				studyUID;
	String				seriesUID;
	
	List <AimResult> 	aimResults = new ArrayList<AimResult>();
	
	boolean				includeGroundTruth;
	boolean				includeAnnotation;
	
	public RetrieveResult(){
		includeGroundTruth = false;
		includeAnnotation = false;
	}
	
	public void setStudyUID(String studyUID){
		this.studyUID = studyUID;
	}
	
	public void setSeriesUID(String seriesUID){
		this.seriesUID = seriesUID;
	}
	
	public void setSubjectName(String subjectName){
		this.subjectName = subjectName;
	}
	
	public void setSubjectID(String subjectID){
		this.subjectID = subjectID;
	}
	
	public void addAimFiles(AimResult aimFile){
		this.aimResults.add(aimFile);
	}
	
	public void setGroundTruth(boolean hasGroundTruth){
		this.includeGroundTruth = hasGroundTruth;
	}
	
	public void setAnnotation(boolean hasAnnotation){
		this.includeAnnotation = hasAnnotation;
	}

	public String getStudyUID(){
		return this.studyUID;
	}
	
	public String getSeriesUID(){
		return this.seriesUID;
	}
	
	public String getSubjectName(){
		return this.subjectName;
	}
	
	public String getSubjectID(){
		return this.subjectID;
	}

	public List<AimResult> getAimFiles(){
		return this.aimResults;
	}
	
	public boolean includeGroundTruth(){
		if (this.includeGroundTruth){
			return true;
		}
		
		return false;
	}
	
	public boolean includeAnnotation(){
		if (this.includeAnnotation){
			return true;
		}
		
		return false;
	}
}
