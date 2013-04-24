package com.siemens.cmiv.avt.mvt.statistic;


import java.util.EventListener;

/**
 * @author Jie Zheng
 *
 */
public interface StatisticResultListener extends EventListener{
	public void statisticResultsAvailable(StatisticEvent e);
}
