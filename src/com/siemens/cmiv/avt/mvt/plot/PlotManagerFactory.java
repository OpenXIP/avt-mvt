package com.siemens.cmiv.avt.mvt.plot;


/**
 * @author Jie Zheng
 *
 */
public class PlotManagerFactory {
	private static PlotManagerImpl plotMgr = new PlotManagerImpl();
	
	private PlotManagerFactory(){}
	
	public static PlotManagerImpl getInstance(){
		return plotMgr;
	}
	
}
