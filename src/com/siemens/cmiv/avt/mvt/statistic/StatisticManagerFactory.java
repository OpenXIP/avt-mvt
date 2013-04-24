package com.siemens.cmiv.avt.mvt.statistic;

/**
 * @author Jie Zheng
 *
 */
public class StatisticManagerFactory {
	private static StatisticManagerImpl staMgr = new StatisticManagerImpl();
	
	private StatisticManagerFactory(){}
	
	public static StatisticManagerImpl getInstance(){
		return staMgr;
	}
	
}
