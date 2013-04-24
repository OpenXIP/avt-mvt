package com.siemens.cmiv.avt.mvt.datatype;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jie Zheng
 *
 */
public class AimResult {
	String			patName;
	String			patID;
	String			studyUID;
	String			seriesUID;
	String			annotator;
	Boolean			bGroundTruth;
	String			aimFile;
	List<String>	segFiles = new ArrayList<String>();	
	
	public AimResult(String aimFile){
		this.bGroundTruth = false;
		this.aimFile = aimFile;
	}
	
	public void setPatientName(String patName){
		this.patName = patName;
	}
	
	public void setPatientID(String patID){
		this.patID = patID;
	}
	
	public void setStudyUID(String studyUID){
		this.studyUID = studyUID;
	}
	
	public void setSeriesUID(String seriesUID){
		this.seriesUID = seriesUID;
	}
	
	public void setAnnotator(String annotator){
		this.annotator = annotator;
	}
	
	public void setGroundTruth(boolean bGroundTruth){
		this.bGroundTruth = bGroundTruth;
	}
	
	public void setAimFile(String aimFile){
		this.aimFile = aimFile;
	}
	
	public void addSegFile(String segFile){
		this.segFiles.add(segFile);
	}

	public String getPatientName(){
		return this.patName;
	}
	
	public String getPatientID(){
		return this.patID;
	}
	
	public String getStudyUID(){
		return this.studyUID;
	}
	
	public String getSeriesUID(){
		return this.seriesUID;
	}
	
	public String getAimFile(){
		return this.aimFile;
	}
	
	public String getAnnotator(){
		return this.annotator;
	}
	
	public Boolean isGroundTruth(){
		return bGroundTruth;
	}
	
	public List<String> getSegFiles(){
		return this.segFiles;
	}
	
	public String getDefaultSegFile(){
		assert(segFiles.size() > 0);
		
		return segFiles.get(0);
	}
	
}
