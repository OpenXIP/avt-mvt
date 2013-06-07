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
public class FilterEntry {
	String gender;
	int age;
	float sliceThickness;
	String annotator;

	/**
	 * 
	 */
	public FilterEntry() {
		// TODO Auto-generated constructor stub
		gender = "ALL";
		age = 0;
		sliceThickness = 0.0f;
		annotator = "ALL";
	}
	
	public String getGender(){
		return gender;
	}
	public int getAge(){
		return age;
	}
	public float getSliceThickness(){
		return sliceThickness;
	}
	public String getAnnotator(){
		return annotator;
	}

	public void setGender(String gender){
		this.gender = gender;
	}
	public void setAge(int age){
		this.age = age;
	}
	public void setSliceThickness(float sliceThickness){
		this.sliceThickness = sliceThickness;
	}
	public void setAnnotator(String annotator){
		this.annotator = annotator;
	}
}
