package com.siemens.cmiv.avt.mvt.datatype;

/**
 * @author Jie Zheng
 *
 */
public class ComputationHeader {
	String computationID;
	String computationDate;
	String computationTime;
	String computationUID;
	String computationRefUID;

	/**
	 * 
	 */
	public ComputationHeader() {
		// TODO Auto-generated constructor stub
	}
	
	public String getComputationID(){
		return computationID;
	}
	public String getComputationDate(){
		return computationDate;
	}
	public String getComputationTime(){
		return computationTime;
	}
	public String getComputationUID(){
		return computationUID;
	}
	public String getComputationRefUID(){
		return computationRefUID;
	}

	public void setComputationID(String computationID){
		this.computationID = computationID;
	}
	public void setComputationDate(String computationDate){
		this.computationDate = computationDate;
	}
	public void setComptationTime(String computationTime){
		this.computationTime = computationTime;
	}
	public void setComptationUID(String comptationUID){
		this.computationUID = comptationUID;
	}
	public void setComptationRefUID(String comptationRefUID){
		this.computationRefUID = comptationRefUID;
	}
}
