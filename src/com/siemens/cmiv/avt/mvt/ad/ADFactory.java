package com.siemens.cmiv.avt.mvt.ad;


import com.siemens.scr.avt.ad.api.ADFacade;
import com.siemens.scr.avt.ad.api.FacadeManager;

/**
 * @author Jie Zheng
 *
 */
public class ADFactory {
	private static ADFacade avtMgr = FacadeManager.getFacade();
				
	private ADFactory(){};
	
	public static ADFacade getADServiceInstance(){				
		return avtMgr;
	}
	
}
