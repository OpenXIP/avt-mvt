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
