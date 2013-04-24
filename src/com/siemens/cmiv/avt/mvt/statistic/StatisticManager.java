package com.siemens.cmiv.avt.mvt.statistic;

import java.io.File;
import java.util.List;

/**
 * @author Jie Zheng
 *
 */
public interface StatisticManager {
	public boolean loadStatistic (File xmlStatisticFile);
	public boolean storeStatistic(List<StatisticEntry> statistic, File xmlStatisticFile);			
	public boolean addStatisticEntry(StatisticEntry entry);
	public boolean deleteStatisticEntry(int index);	
	public StatisticEntry getStatisticEntry(int i);
	public List<StatisticEntry> getStatisticEntries();	
	public int getNumberOfStatisticEntries();
}
