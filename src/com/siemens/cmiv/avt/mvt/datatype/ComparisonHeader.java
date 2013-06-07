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

/**
 * @author Jie Zheng
 *
 */
public class ComparisonHeader {
	String comparisonID;
	String comparisonDate;
	String comparisonTime;
	String comparisonUID;
	String comparisonRefUID;

	/**
	 * 
	 */
	public ComparisonHeader() {
		comparisonID = "Computation1";
		comparisonDate = "20090303";
		comparisonTime = "112326";
		comparisonUID = "123.13.232.34";
		comparisonRefUID = "Experiment1";
	}
	
	public String getComparisonID(){
		return comparisonID;
	}
	public String getComparisonDate(){
		return comparisonDate;
	}
	public String getComparisonTime(){
		return comparisonTime;
	}
	public String getComparisonUID(){
		return comparisonUID;
	}
	public String getComparisonRefUID(){
		return comparisonRefUID;
	}

	public void setComparisonID(String comparisonID){
		this.comparisonID = comparisonID;
	}
	public void setComparisonDate(String comparisonDate){
		this.comparisonDate = comparisonDate;
	}
	public void setComparisonTime(String comparisonTime){
		this.comparisonTime = comparisonTime;
	}
	public void setComparisonUID(String comparisonUID){
		this.comparisonUID = comparisonUID;
	}
	public void setComparisonRefUID(String comparisonRefUID){
		this.comparisonRefUID = comparisonRefUID;
	}
}
