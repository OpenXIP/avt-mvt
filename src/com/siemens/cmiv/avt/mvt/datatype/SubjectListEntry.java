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
public class SubjectListEntry {
	Object bChecked;
	Object subjectName;
	Object subjectID;
	Object subjectGender;
	Object subjectAge;
	Object studyDate;
	Object studyTime;
	Object studyModality;
	Object seriesNumber;
	Object sliceThickness;
	Object studyDescription;
	Object studyInstanceUIDCurr;
	Object seriesInstanceUIDCurr;
	Object seriesSOPClassUID;
	
	Object nominalGTUID;
	Object nominalGT;
	Object nominalGTDate;
	Object nominalGTTime;
	Object nominalGTVolume;
	
	Object annotationUID;
	Object annotator;
	Object annotationDate;
	Object annotationTime;
	Object annotationVolume;
	
	List<AnnotationEntry> annotations;

	/**
	 * 
	 */
	public List<AnnotationEntry> getAnnotations() {
		return annotations;
	}

	public void setAnnotations(List<AnnotationEntry> annotations) {
		this.annotations = annotations;
	}
	
	public SubjectListEntry() {
		this.annotations = new ArrayList<AnnotationEntry>();
	}

	public Object getSubjectStatus(){
		return bChecked;
	}
	//object
	public Object getSubjectNameObj(){
		return subjectName;
	}
	public Object getSubjectIDObj(){
		return subjectID;
	}
	public Object getSubjectGenderObj(){
		return subjectGender;
	}
	public Object getSubjectAgeObj(){
		return subjectAge;
	}
	public Object getStudyDateObj(){
		return studyDate;
	}
	public Object getStudyTimeObj(){
		return studyTime;
	}
	public Object getStudyModalityObj(){
		return studyModality;
	}
	public Object getSeriesNumberObj(){
		return seriesNumber;
	}
	public Object getSliceThicknessObj(){
		return sliceThickness;
	}
	public Object getStudyDescriptionObj(){
		return studyDescription;
	}
	public Object getStudyInstanceUIDCurrObj(){
		return studyInstanceUIDCurr;
	}
	public Object getSeriesInstanceUIDCurrObj(){
		return seriesInstanceUIDCurr;
	}
	public Object getSeriesSOPClassUIDObj(){
		return seriesSOPClassUID;
	}
	
	public Object getBChecked() {
		return bChecked;
	}

	public void setBChecked(Object checked) {
		bChecked = checked;
	}
	
	//string
	public String getSubjectName(){
		return subjectName.toString();
	}
	public String getSubjectID(){
		return subjectID.toString();
	}
	public String getSubjectGender(){
		return subjectGender.toString();
	}
	public String getSubjectAge(){
		return subjectAge.toString();
	}
	public String getStudyDate(){
		return studyDate.toString();
	}
	public String getStudyTime(){
		return studyTime.toString();
	}
	public String getStudyModality(){
		return studyModality.toString();
	}
	public String getSeriesNumber(){
		return seriesNumber.toString();
	}
	public String getSliceThickness(){
		return sliceThickness.toString();
	}
	public String getStudyDescription(){
		return studyDescription.toString();
	}
	public String getStudyInstanceUIDCurr(){
		return studyInstanceUIDCurr.toString();
	}
	public String getSeriesInstanceUIDCurr(){
		return seriesInstanceUIDCurr.toString();
	}
	public String getSeriesSOPClassUID(){
		return seriesSOPClassUID.toString();
	}
	
	//norminal GT
	public String getNominalGTUID(){
		return nominalGTUID.toString();
	}
	public String getNominalGT(){
		return nominalGT.toString();
	}
	public String getNominalGTDate(){
		return nominalGTDate.toString();
	}
	public String getNominalGTTime(){
		return nominalGTTime.toString();
	}
	public String getNominalGTVolume(){
		return nominalGTVolume.toString();
	}

	//annotation
	public String getAnnotationUID(){
		return annotationUID.toString();
	}
	public String getAnnotator(){
		return annotator.toString();
	}
	public String getAnnotationDate(){
		return annotationDate.toString();
	}
	public String getAnnotationTime(){
		return annotationTime.toString();
	}
	public String getAnnotationVolume(){
		return annotationVolume.toString();
	}

	//object
	public void setSubjectStatusObj(Object bChecked){
		this.bChecked = bChecked;
	}
	public void setSubjectNameObj(Object subjectName){
		this.subjectName = subjectName;
	}
	public void setSubjectIDObj(Object subjectID){
		this.subjectID = subjectID;
	}
	public void setSubjectGenderObj(Object subjectGender){
		this.subjectGender = subjectGender;
	}
	public void setSubjectAgeObj(Object subjectAge){
		this.subjectAge = subjectAge;
	}
	public void setStudyDateObj(Object studyDate){
		this.studyDate = studyDate;
	}
	public void setStudyTimeObj(Object studyTime){
		this.studyTime = studyTime;
	}
	public void setStudyModalityObj(Object studyModality){
		this.studyModality = studyModality;
	}
	public void setSerisNumberObj(Object seriesNumber){
		this.seriesNumber = seriesNumber;
	}
	public void setSliceThicknessObj(Object sliceThickness){
		this.sliceThickness = sliceThickness;
	}
	public void setStudyDescriptionObj(Object studyDescription){
		this.studyDescription = studyDescription;
	}
	public void setStudyInstanceUIDCurrObj(Object studyInstanceUIDCurr){
		this.studyInstanceUIDCurr = studyInstanceUIDCurr;
	}
	public void setSeriesInstanceUIDCurrObj(Object seriesInstanceUIDCurr){
		this.seriesInstanceUIDCurr = seriesInstanceUIDCurr;
	}
	public void setSeriesSOPClassUIDObj(Object seriesSOPClassUID){
		this.seriesSOPClassUID = seriesSOPClassUID;
	}

	//subject
	public void setSubjectStatus(Boolean bChecked){
		this.bChecked = bChecked;
	}
	public void setSubjectName(String subjectName){
		this.subjectName = subjectName;
	}
	public void setSubjectID(String subjectID){
		this.subjectID = subjectID;
	}
	public void setSubjectGender(String subjectGender){
		this.subjectGender = subjectGender;
	}
	public void setSubjectAge(String subjectAge){
		this.subjectAge = subjectAge;
	}
	public void setStudyDate(String studyDate){
		this.studyDate = studyDate;
	}
	public void setStudyTime(String studyTime){
		this.studyTime = studyTime;
	}
	public void setStudyModality(String studyModality){
		this.studyModality = studyModality;
	}
	public void setSerisNumber(String seriesNumber){
		this.seriesNumber = seriesNumber;
	}
	public void setSliceThickness(String sliceThickness){
		this.sliceThickness = sliceThickness;
	}
	public void setStudyDescription(String studyDescription){
		this.studyDescription = studyDescription;
	}
	public void setStudyInstanceUIDCurr(String studyInstanceUIDCurr){
		this.studyInstanceUIDCurr = studyInstanceUIDCurr;
	}
	public void setSeriesInstanceUIDCurr(String seriesInstanceUIDCurr){
		this.seriesInstanceUIDCurr = seriesInstanceUIDCurr;
	}
	public void setSeriesSOPClassUID(String seriesSOPClassUID){
		this.seriesSOPClassUID = seriesSOPClassUID;
	}
	
	//norminal GT
	public void setNominalGTUID(String nominalGTUID){
		this.nominalGTUID = nominalGTUID;
	}
	public void setNominalGT(String nominalGT){
		this.nominalGT = nominalGT;
	}
	public void setNominalGTDate(String nominalGTDate){
		this.nominalGTDate = nominalGTDate;
	}
	public void setNominalGTTime(String nominalGTTime){
		this.nominalGTTime = nominalGTTime;
	}
	public void setNominalGTVolume(String nominalGTVolume){
		this.nominalGTVolume = nominalGTVolume;
	}

	//annotation
	public void setAnnotationUID(String annotationUID){
		this.annotationUID = annotationUID;
	}
	public void setAnnotator(String annotator){
		this.annotator = annotator;
	}
	public void setAnnotationDate(String annotationDate){
		this.annotationDate = annotationDate;
	}
	public void setAnnotationTime(String annotationTime){
		this.annotationTime = annotationTime;
	}
	public void setAnnotationVolume(String annotationVolume){
		this.annotationVolume = annotationVolume;
	}
}
