package com.siemens.cmiv.avt.mvt.datatype;

/**
 * @author Jie Zheng
 *
 */
public class ComputationListEntry {
	String subjectName;
	String subjectID;
	String subjectUID;
	String referenceVolume;
	String segmentationVolume;
	String volumeDifference;
	String relativeVolumeDifference;
	String averageSurfaceDistance;
	String averageRMSSurfaceDistance;
	String maximumSurfaceDistance;
	String volumeOverlap;
	String referenceRECIST;
	String segmentationRECIST;
	String referenceWHO;
	String segmentationWHO;
	String RECISTDifference;
	String WHODifference;
	boolean lastOne;
	String type;
	String label;
	
	String refRECISTAim;
	String segRECISTAim;
	String refWHOAim;
	String segWHOAim;
	String refVolumeAim;
	String segVolumeAim;
	
	public String getRefRECISTAim() {
		return refRECISTAim;
	}

	public void setRefRECISTAim(String refRECISTAim) {
		this.refRECISTAim = refRECISTAim;
	}

	public String getSegRECISTAim() {
		return segRECISTAim;
	}

	public void setSegRECISTAim(String segRECISTAim) {
		this.segRECISTAim = segRECISTAim;
	}

	public String getRefWHOAim() {
		return refWHOAim;
	}

	public void setRefWHOAim(String refWHOAim) {
		this.refWHOAim = refWHOAim;
	}

	public String getSegWHOAim() {
		return segWHOAim;
	}

	public void setSegWHOAim(String segWHOAim) {
		this.segWHOAim = segWHOAim;
	}

	public String getRefVolumeAim() {
		return refVolumeAim;
	}

	public void setRefVolumeAim(String refVolumeAim) {
		this.refVolumeAim = refVolumeAim;
	}

	public String getSegVolumeAim() {
		return segVolumeAim;
	}

	public void setSegVolumeAim(String segVolumeAim) {
		this.segVolumeAim = segVolumeAim;
	}
	

	public String getReferenceRECIST() {
		return referenceRECIST;
	}

	public String getReferenceVolume() {
		return referenceVolume;
	}

	public void setReferenceVolume(String referenceVolume) {
		this.referenceVolume = referenceVolume;
	}

	public String getSegmentationVolume() {
		return segmentationVolume;
	}

	public void setSegmentationVolume(String segmentationVolume) {
		this.segmentationVolume = segmentationVolume;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setReferenceRECIST(String referenceRECIST) {
		this.referenceRECIST = referenceRECIST;
	}

	public String getSegmentationRECIST() {
		return segmentationRECIST;
	}

	public void setSegmentationRECIST(String segmentationRECIST) {
		this.segmentationRECIST = segmentationRECIST;
	}

	public String getReferenceWHO() {
		return referenceWHO;
	}

	public void setReferenceWHO(String referenceWHO) {
		this.referenceWHO = referenceWHO;
	}

	public String getSegmentationWHO() {
		return segmentationWHO;
	}

	public void setSegmentationWHO(String segmentationWHO) {
		this.segmentationWHO = segmentationWHO;
	}

	public String getRECISTDifference() {
		return RECISTDifference;
	}

	public void setRECISTDifference(String difference) {
		RECISTDifference = difference;
	}

	public String getWHODifference() {
		return WHODifference;
	}

	public void setWHODifference(String difference) {
		WHODifference = difference;
	}
	
	/**
	 * 
	 */
	public ComputationListEntry() {
		subjectName = "";
		subjectID = "";
		subjectUID = "";
		referenceVolume = "";
		segmentationVolume = "";
		volumeDifference = "";
		relativeVolumeDifference = "";
		averageSurfaceDistance = "";
		averageRMSSurfaceDistance = "";
		maximumSurfaceDistance = "";
		volumeOverlap = "";
		referenceRECIST = "";
		segmentationRECIST = "";
		referenceWHO = "";
		segmentationWHO = "";
		RECISTDifference = "";
		WHODifference = "";
		type = "";
		label = "";
		
		refRECISTAim = "";
		segRECISTAim = "";
		refWHOAim = "";
		segWHOAim = "";
		refVolumeAim = "";
		segVolumeAim = "";
		
		lastOne = false;
	}
	
	public String getMeasurement(int index){
		switch (index){
		case 0:
			return referenceRECIST;
		case 1:
			return segmentationRECIST;
		case 2:
			return RECISTDifference;
			
		case 3:
			return referenceWHO;
		case 4:
			return segmentationWHO;
		case 5:
			return WHODifference;

		case 6:
			return referenceVolume;
		case 7:
			return segmentationVolume;
		case 8:
			return volumeDifference;
			
		case 9:
			return relativeVolumeDifference;
			
		case 10:
			return averageSurfaceDistance;
			
		case 11:
			return averageRMSSurfaceDistance;
			
		case 12:
			return maximumSurfaceDistance;
			
		case 13:
			return volumeOverlap;
		}
		
		return "";
	}
	
	public String getMeasurement(String title){
		if (title.equalsIgnoreCase("NominalGT RECIST")){
			return referenceRECIST;
		}
		if (title.equalsIgnoreCase("Annotation RECIST")){
			return segmentationRECIST;
		}
		if (title.equalsIgnoreCase("RECIST Difference")){
			return RECISTDifference;
		}
		
		if (title.equalsIgnoreCase("NominalGT WHO")){
			return referenceWHO;
		}
		if (title.equalsIgnoreCase("Annotation WHO")){
			return segmentationWHO;
		}
		if (title.equalsIgnoreCase("WHO Difference")){
			return WHODifference;
		}

		if (title.equalsIgnoreCase("NominalGT Volume")){
			return segmentationVolume;
		}
		else if (title.equalsIgnoreCase("Annotation Volume")){
			return referenceVolume;
		}
		else if (title.equalsIgnoreCase("Volume Difference")){
			return volumeDifference;
		}
		else if (title.equalsIgnoreCase("Relative VolumeDifference")){
			return relativeVolumeDifference;
		}
		else if (title.equalsIgnoreCase("Surface Distance(Average)")){
			return averageSurfaceDistance;
		}
		else if (title.equalsIgnoreCase("Surface Distance(RMS)")){
			return averageRMSSurfaceDistance;
		}
		else if (title.equalsIgnoreCase("Surface Distance(Maximum)")){
			return maximumSurfaceDistance;
		}
		else if (title.equalsIgnoreCase("Volume Overlap")){
			return volumeOverlap;
		}
		
		return null;
	}
	
	public String getRefVolume(){
		return referenceVolume;
	}
	public String getSegVolume(){
		return segmentationVolume;
	}
	public String getSubjectName(){
		return subjectName;
	}
	public String getSubjectID(){
		return subjectID;
	}
	public String getSubjectUID(){
		return subjectUID;
	}
	public String getVolumeDifference(){
		return volumeDifference;
	}
	public String getRelativeVolumeDifference(){
		return relativeVolumeDifference;
	}
	public String getAverageSurfaceDistance(){
		return averageSurfaceDistance;
	}
	public String getAverageRMSSurfaceDistance(){
		return averageRMSSurfaceDistance;
	}
	public String getMaximumSurfaceDistance(){
		return maximumSurfaceDistance;
	}
	public String getVolumeOverlap(){
		return volumeOverlap;
	}
	public boolean isLastOne(){
		return lastOne;
	}
	
	public void setMeasurement(int index, String value){
		switch (index){
		case 0:
			this.referenceRECIST = value;
			break;
		case 1:
			this.segmentationRECIST = value;
			break;
		case 2:
			this.RECISTDifference = value;
			break;

		case 3:
			this.referenceWHO = value;
			break;
		case 4:
			this.segmentationWHO = value;
			break;
		case 5:
			this.WHODifference = value;
			break;

		case 6:
			this.referenceVolume = value;
			break;
		case 7:
			this.segmentationVolume = value;
			break;
		case 8:
			this.volumeDifference = value;
			break;
			
		case 9:
			this.relativeVolumeDifference = value;
			break;
			
		case 10:
			this.averageSurfaceDistance = value;
			break;
			
		case 11:
			this.averageRMSSurfaceDistance = value;
			break;
			
		case 12:
			this.maximumSurfaceDistance = value;
			break;
			
		case 13:
			this.volumeOverlap = value;
			break;
		}
	}

	public void setRefVolume(String referenceVolume){
		this.referenceVolume = referenceVolume;
	}
	public void setSegVolume(String segmentationVolume){
		this.segmentationVolume = segmentationVolume;
	}

	public void setSubjectName(String subjectName){
		this.subjectName = subjectName;
	}
	public void setSubjectID(String subjectID){
		this.subjectID = subjectID;
	}
	public void setSubjectUID(String subjectUID){
		this.subjectUID = subjectUID;
	}
	public void setVolumeDifference(String volumeDifference){
		this.volumeDifference = volumeDifference;
	}
	public void setRelativeVolumeDifference(String relativeVolumeDifference){
		this.relativeVolumeDifference = relativeVolumeDifference;
	}
	public void setAverageSurfaceDistance(String averageSurfaceDistance){
		this.averageSurfaceDistance = averageSurfaceDistance;
	}
	public void setAverageRMSSurfaceDistance(String averageRMSSurfaceDistance){
		this.averageRMSSurfaceDistance = averageRMSSurfaceDistance;
	}
	public void setMaximumSurfaceDistance(String maximumSurfaceDistance){
		this.maximumSurfaceDistance = maximumSurfaceDistance;
	}
	public void setVolumeOverlap(String volumeOverlap){
		this.volumeOverlap = volumeOverlap;
	}
	public void setLastOne(boolean lastOne){
		this.lastOne = lastOne;
	}

}
