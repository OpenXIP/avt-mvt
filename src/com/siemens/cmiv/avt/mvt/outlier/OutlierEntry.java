package com.siemens.cmiv.avt.mvt.outlier;

/**
 * @author Jie Zheng
 *
 */
public class OutlierEntry {
	String criteriaMeasurement;
	String criteriaScaling;

	/**
	 * 
	 */
	public OutlierEntry() {
		// TODO Auto-generated constructor stub
	}
	
	public String getCriteriaMeasurement(){
		return criteriaMeasurement;
	}
	public String getCriteriaScaling(){
		return criteriaScaling;
	}

	public void setCriteriaMeasurement(String criteriaMeasurement){
		this.criteriaMeasurement = criteriaMeasurement;
	}
	public void setCriteriaScaling(String criteriaScaling){
		this.criteriaScaling = criteriaScaling;
	}
	public int getStatisticsDataID(String statisticData){
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
	
	public String getCriteriaScalingID(String CriteriaScaling){
		if (CriteriaScaling.equalsIgnoreCase("Top 25%"))
			return ">0.75";
		
		if (CriteriaScaling.equalsIgnoreCase("Top 50%"))
			return ">0.5";

		if (CriteriaScaling.equalsIgnoreCase("Bottom 50%"))
			return "<0.5";
		
		if (CriteriaScaling.equalsIgnoreCase("Bottom 25%"))
			return "<0.25";
	
		return "";
	}
}
