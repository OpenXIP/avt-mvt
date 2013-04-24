package com.siemens.cmiv.avt.mvt.outlier;

/**
 * @author Jie Zheng
 *
 */
public class OutlierManagerFactory {
	private static OutlierManagerImpl outMgr = new OutlierManagerImpl();
	
	private OutlierManagerFactory(){}
	
	public static OutlierManagerImpl getInstance(){
		return outMgr;
	}
	
}
