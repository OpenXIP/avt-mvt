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
