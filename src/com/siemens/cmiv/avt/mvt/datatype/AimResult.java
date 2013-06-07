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
