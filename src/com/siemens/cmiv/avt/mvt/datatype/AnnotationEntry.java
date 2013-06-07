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

public class AnnotationEntry {
	String AnnotationType;
	String AnnotationReader;
	String AnnotationDate;
	String AnnotationTime;
	String AnnotationStamp;
	String NoduleType;
	String NoduleDensity;
	String NoduleSet;
	String AnnotationFile;
	String DcmSegFile;
	
	String SliceThickness;
	String Session;
	String AnnotationUID;
	String AnnotationScreenshot;
	String AnnotationSeed;
	String PatientName;
	String PatientID;
	String PatientGender;
	String Comment;
	String ReferenceUID;


	public AnnotationEntry(){
		AnnotationType = "";
		AnnotationReader = "";
		AnnotationDate = "";
		AnnotationTime = "";
		AnnotationStamp = "";
		AnnotationFile = "";
		NoduleType = "";
		NoduleDensity = "";
		NoduleSet = "";
		SliceThickness = "";
		Session = "";
		AnnotationUID = "";
		AnnotationScreenshot = "";
		AnnotationSeed = "";
		PatientName = "";
		PatientID = "";
		PatientGender = "";
		Comment = "";
		ReferenceUID = "";
	}
	
	public String getDcmSegFile() {
		return DcmSegFile;
	}

	public void setDcmSegFile(String dcmSegFile) {
		DcmSegFile = dcmSegFile;
	}
	
	public String getReferenceUID() {
		return ReferenceUID;
	}

	public void setReferenceUID(String referenceUID) {
		ReferenceUID = referenceUID;
	}

	public String getAnnotationUID() {
		return AnnotationUID;
	}	
	public String getAnnotationType(){
		return this.AnnotationType;
	}
	public String getAnnotationReader(){
		return this.AnnotationReader;
	}
	public String getAnnotationDate(){
		return this.AnnotationDate;
	}
	public String getAnnotationTime(){
		return this.AnnotationTime;
	}
	public String getNoduleType(){
		return this.NoduleType;
	}
	public String getNoduleDensity(){
		return this.NoduleDensity;
	}
	public String getNoduleSet(){
		return this.NoduleSet;
	}
	public String getSliceThickness(){
		return this.SliceThickness;
	}
	public String getSession(){
		return this.Session;
	}
	public String getAnnotaionUID(){
		return this.AnnotationUID;
	}
	public String getAnnotaionScreenshot(){
		return this.AnnotationScreenshot;
	}
	
	public String getPatientName() {
		return PatientName;
	}

	public void setPatientName(String patientName) {
		this.PatientName = patientName;
	}

	public String getPatientID() {
		return PatientID;
	}

	public void setPatientID(String patientID) {
		this.PatientID = patientID;
	}

	public String getPatientGender() {
		return PatientGender;
	}
	public String getAnnotationSeed() {
		return AnnotationSeed;
	}

	public String getAnnotationFile() {
		return AnnotationFile;
	}

	public void setAnnotationFile(String annotationFile) {
		this.AnnotationFile = annotationFile;
	}

	public String getComment() {
		return Comment;
	}

	public void setComment(String comment) {
		this.Comment = comment;
	}

	public String getAnnotationStamp() {
		return AnnotationStamp;
	}

	public void setAnnotationStamp(String annotationStamp) {
		this.AnnotationStamp = annotationStamp;
	}
	

	public void setAnnotationSeed(String annotationSeed) {
		this.AnnotationSeed = annotationSeed;
	}

	public void setPatientGender(String patientGender) {
		this.PatientGender = patientGender;
	}

	public void setAnnotationType(String type){
		this.AnnotationType = type;
	}
	public void setAnnotationReader(String reader){
		this.AnnotationReader = reader;
	}
	public void setAnnotationDate(String date){
		this.AnnotationDate = date;
	}
	public void setAnnotationTime(String time){
		this.AnnotationTime = time;
	}
	public void setNoduleType(String noduleType){
		this.NoduleType = noduleType;
	}
	public void setNoduleDensity(String noduleDensity){
		this.NoduleDensity = noduleDensity;
	}
	public void setNoduleSet(String noduleSet){
		this.NoduleSet = noduleSet;
	}
	public void setSliceThickness(String sliceThickness){
		this.SliceThickness = sliceThickness;
	}
	public void setSession(String session){
		this.Session = session;
	}
	public void setAnnotationUID(String annotationUID){
		this.AnnotationUID = annotationUID;
	}
	public void setAnnotationScreenshot(String annotationScreenshot){
		this.AnnotationScreenshot = annotationScreenshot;
	}
}
