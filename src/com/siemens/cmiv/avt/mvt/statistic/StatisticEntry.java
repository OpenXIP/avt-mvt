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
package com.siemens.cmiv.avt.mvt.statistic;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jie Zheng
 *
 */
public class StatisticEntry {
	String statisticData;
	String statisticMethod;
	List<String> multipleData;
	boolean	bMultipleData;

	/**
	 * 
	 */
	public StatisticEntry(String statisticData, String statisticMethod) {
		this.statisticData = statisticData;
		this.statisticMethod = statisticMethod;
	}
	
	public boolean isBMultipleData() {
		return bMultipleData;
	}

	public void setBMultipleData(boolean multipleData) {
		bMultipleData = multipleData;
	}

	public StatisticEntry(){
		multipleData = new ArrayList<String>();
	}
	
	public String getStatisticData(){
		return statisticData;
	}
	public String getStatisticMethod(){
		return statisticMethod;
	}

	public void setStatisticMethod(String statisticMethod){
		this.statisticMethod = statisticMethod;
	}
	public void setStatisticData(String statisticData){
		this.statisticData = statisticData;
	}
	
	public void addStatisticData(String data){
		this.multipleData.add(data);
	}
	
	public List<String> getMultipleData(){
		return this.multipleData;
	}
	
	public int getStatisticDataID(String statisticData){
		int index = 3;
		
		if (statisticData.equalsIgnoreCase("NominalGT RECIST"))
			return index;
		
		index++;
		if (statisticData.equalsIgnoreCase("Annotation RECIST"))
			return index;

		index++;
		if (statisticData.equalsIgnoreCase("RECIST Difference"))
			return index;

		index++;
		if (statisticData.equalsIgnoreCase("NominalGT WHO"))
			return index;
		
		index++;
		if (statisticData.equalsIgnoreCase("Annotation WHO"))
			return index;

		index++;
		if (statisticData.equalsIgnoreCase("WHO Difference"))
			return index;

		index++;
		if (statisticData.equalsIgnoreCase("NominalGT Volume"))
			return index;
		
		index++;
		if (statisticData.equalsIgnoreCase("Annotation Volume"))
			return index;

		index++;
		if (statisticData.equalsIgnoreCase("Volume Difference"))
			return index;
		
		index++;
		if (statisticData.equalsIgnoreCase("Relative VolumeDifference"))
			return index;

		index++;
		if (statisticData.equalsIgnoreCase("Surface Distance(Average)"))
			return index;
		
		index++;
		if (statisticData.equalsIgnoreCase("Surface Distance(RMS)"))
			return index;

		index++;
		if (statisticData.equalsIgnoreCase("Surface Distance(Maximum)"))
			return index;
		
		index++;
		if (statisticData.equalsIgnoreCase("Volume Overlap"))
			return index;
	
		return -1;
	}
}
