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
public class CriteriaEntry {
	String measurement;
	String scaling;

	/**
	 * 
	 */
	public CriteriaEntry() {
		// TODO Auto-generated constructor stub
	}
	
	public String getMeasurement(){
		return measurement;
	}
	public String getScaling(){
		return scaling;
	}

	public void setMeasurement(String measurement){
		this.measurement = measurement;
	}
	public void setScaling(String scaling){
		this.scaling = scaling;
	}
}
