package com.siemens.cmiv.avt.mvt.statistic;


import java.util.EventObject;

/**
 * @author Jie Zheng
 *
 */
public class StatisticEvent extends EventObject {
	public StatisticEvent(StatisticResult source){	
		super(source);
	}
}
