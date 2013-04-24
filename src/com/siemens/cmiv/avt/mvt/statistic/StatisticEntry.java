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
