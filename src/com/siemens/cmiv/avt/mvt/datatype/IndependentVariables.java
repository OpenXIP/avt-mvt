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
