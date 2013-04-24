package com.siemens.cmiv.avt.mvt.datatype;

/**
 * @author Jie Zheng
 *
 */
public class MeasurementEntry {
	boolean bRefRECIST;
	boolean bSegRECIST;
	boolean bRECISTDiff;
	boolean bRefWHO;
	boolean bSegWHO;
	boolean bWHODiff;
	boolean bRefVolume;
	boolean bSegVolume;
	boolean bVolumeDiff;
	boolean bRelativeVolDiff;
	boolean bAvgSurfDist;
	boolean bRMSSurfDist;
	boolean bMaxSurfDist;
	boolean bVolOverlap;
	/**
	 * 
	 */
	public MeasurementEntry() {
		bRefRECIST = true;
		bSegRECIST = true;
		bRECISTDiff = true;
		bRefWHO = true;
		bSegWHO = true;
		bWHODiff = true;
		
		bRefVolume = true;
		bSegVolume = true;
		bVolumeDiff =true;
		bRelativeVolDiff = true;
		bAvgSurfDist = true;
		bRMSSurfDist = true;
		bMaxSurfDist = true;
		bVolOverlap = true;
	}
	
	public boolean isBRefRECIST() {
		return bRefRECIST;
	}

	public void setBRefRECIST(boolean refRECIST) {
		bRefRECIST = refRECIST;
	}

	public boolean isBSegRECIST() {
		return bSegRECIST;
	}

	public void setBSegRECIST(boolean segRECIST) {
		bSegRECIST = segRECIST;
	}

	public boolean isBRECISTDiff() {
		return bRECISTDiff;
	}

	public void setBRECISTDiff(boolean diff) {
		bRECISTDiff = diff;
	}

	public boolean isBRefWHO() {
		return bRefWHO;
	}

	public void setBRefWHO(boolean refWHO) {
		bRefWHO = refWHO;
	}

	public boolean isBSegWHO() {
		return bSegWHO;
	}

	public void setBSegWHO(boolean segWHO) {
		bSegWHO = segWHO;
	}

	public boolean isBWHODiff() {
		return bWHODiff;
	}

	public void setBWHODiff(boolean diff) {
		bWHODiff = diff;
	}

	public boolean isBRefVolume() {
		return bRefVolume;
	}

	public void setBRefVolume(boolean refVolume) {
		bRefVolume = refVolume;
	}

	public boolean isBSegVolume() {
		return bSegVolume;
	}

	public void setBSegVolume(boolean segVolume) {
		bSegVolume = segVolume;
	}

	public boolean isBVolumeDiff() {
		return bVolumeDiff;
	}

	public void setBVolumeDiff(boolean volumeDiff) {
		bVolumeDiff = volumeDiff;
	}

	public boolean isBRelativeVolDiff() {
		return bRelativeVolDiff;
	}

	public void setBRelativeVolDiff(boolean relativeVolDiff) {
		bRelativeVolDiff = relativeVolDiff;
	}

	public boolean isBAvgSurfDist() {
		return bAvgSurfDist;
	}

	public void setBAvgSurfDist(boolean avgSurfDist) {
		bAvgSurfDist = avgSurfDist;
	}

	public boolean isBRMSSurfDist() {
		return bRMSSurfDist;
	}

	public void setBRMSSurfDist(boolean surfDist) {
		bRMSSurfDist = surfDist;
	}

	public boolean isBMaxSurfDist() {
		return bMaxSurfDist;
	}

	public void setBMaxSurfDist(boolean maxSurfDist) {
		bMaxSurfDist = maxSurfDist;
	}

	public boolean isBVolOverlap() {
		return bVolOverlap;
	}

	public void setBVolOverlap(boolean volOverlap) {
		bVolOverlap = volOverlap;
	}

	public boolean getVolumeDiff(){
		return bVolumeDiff;
	}
	public boolean getRelativeVolDiff(){
		return bRelativeVolDiff;
	}
	public boolean getAvgSurfDist(){
		return bAvgSurfDist;
	}
	public boolean getRMSSurfDist(){
		return bRMSSurfDist;
	}
	public boolean getMaxSurfDist(){
		return bMaxSurfDist;
	}
	public boolean getVolOverlap(){
		return bVolOverlap;
	}

	public void setVolumeDiff(boolean bShow){
		this.bVolumeDiff = bShow;
	}
	public void setRelativeVolDiff(boolean bShow){
		this.bRelativeVolDiff = bShow;
	}
	public void setAvgSurfDist(boolean bShow){
		this.bAvgSurfDist = bShow;
	}
	public void setRMSSurfDist(boolean bShow){
		this.bRMSSurfDist = bShow;
	}
	public void setMaxSurfDist(boolean bShow){
		this.bMaxSurfDist = bShow;
	}
	public void setVolOverlap(boolean bShow){
		this.bVolOverlap = bShow;
	}
	
	public void setElementFlag(String element, boolean bShow){
		if (element == "NominalGT RECIST")
			setBRefRECIST(bShow);
		if (element == "Annotation RECIST")
			setBSegRECIST(bShow);
		if (element == "RECIST Difference")
			setBRECISTDiff(bShow);
		
		if (element == "NominalGT WHO")
			setBRefWHO(bShow);
		if (element == "Annotation WHO")
			setBSegWHO(bShow);
		if (element == "WHO Difference")
			setBWHODiff(bShow);

		if (element == "NominalGT Volume")
			setBRefVolume(bShow);
		if (element == "Annotation Volume")
			setBSegVolume(bShow);
		if (element == "Volume Difference")
			setVolumeDiff(bShow);
		
		if (element == "Relative VolumeDifference")
			setRelativeVolDiff(bShow);
		if (element == "Surface Distance(Average)")
			setAvgSurfDist(bShow);
		if (element == "Surface Distance(RMS)")
			setRMSSurfDist(bShow);
		if (element == "Surface Distance(Maximum)")
			setMaxSurfDist(bShow);
		if (element == "Volume Overlap")
			setVolOverlap(bShow);
	}
}
