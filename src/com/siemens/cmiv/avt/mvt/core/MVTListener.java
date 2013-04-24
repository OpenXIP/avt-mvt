package com.siemens.cmiv.avt.mvt.core;


import java.util.EventListener;

import com.siemens.cmiv.avt.mvt.ad.ADRetrieveEvent;
import com.siemens.cmiv.avt.mvt.ad.ADSearchEvent;

/**
 * @author Jie Zheng
 *
 */
public interface MVTListener extends EventListener{
	public void searchResultsAvailable(ADSearchEvent e);
	public void retriveResultsAvailable(ADRetrieveEvent e);
	public void calculateResultsAvailable(MVTCalculateEvent e);
}
