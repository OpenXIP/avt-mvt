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
