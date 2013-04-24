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
