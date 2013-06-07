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

public class IndependentVariables {
	String gender;
	String sliceThickness;
	String exposure;
	String pitch;
	String collimation;
	String kernel;
	
	public IndependentVariables() {
		this.gender = "";
		this.sliceThickness = "";
		this.exposure = "";
		this.pitch = "";
		this.collimation = "";
		this.kernel = "";		
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getSliceThickness() {
		return sliceThickness;
	}
	public void setSliceThickness(String sliceThickness) {
		this.sliceThickness = sliceThickness;
	}
	public String getExposure() {
		return exposure;
	}
	public void setExposure(String exposure) {
		this.exposure = exposure;
	}
	public String getPitch() {
		return pitch;
	}
	public void setPitch(String pitch) {
		this.pitch = pitch;
	}
	public String getCollimation() {
		return collimation;
	}
	public void setCollimation(String collimation) {
		this.collimation = collimation;
	}
	public String getKernel() {
		return kernel;
	}
	public void setKernel(String kernel) {
		this.kernel = kernel;
	}
	
}
