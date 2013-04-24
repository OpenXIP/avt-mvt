package com.siemens.cmiv.avt.mvt.ad;


import java.util.EventObject;

/**
 * @author Jie Zheng
 *
 */
public class ADRetrieveEvent extends EventObject {
	public ADRetrieveEvent(ADRetrieveResult source){	
		super(source);
	}
}
