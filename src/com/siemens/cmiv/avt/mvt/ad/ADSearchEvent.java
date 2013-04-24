package com.siemens.cmiv.avt.mvt.ad;


import java.util.EventObject;

/**
 * @author Jie Zheng
 *
 */
public class ADSearchEvent extends EventObject {
	public ADSearchEvent(ADSearchResult source){	
		super(source);
	}
}
