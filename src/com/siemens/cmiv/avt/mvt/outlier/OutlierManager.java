package com.siemens.cmiv.avt.mvt.outlier;

import java.io.File;
import java.util.List;

/**
 * @author Jie Zheng
 *
 */
public interface OutlierManager {
	public boolean loadOutlier (File xmlOutlierFile);
	public boolean storeOutlier(List<OutlierEntry> outlyings, File xmlOutlierFile);			
	public boolean addOutlierEntry(OutlierEntry entry);
	public boolean deleteOutlierEntry(int index);	
	public OutlierEntry getOutlierEntry(int i);
	public List<OutlierEntry> getOutlierEntries();	
	public int getNumberOfOutlierEntries();
}
