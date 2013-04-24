package com.siemens.cmiv.avt.mvt.core;


import java.util.EventObject;

import com.siemens.cmiv.avt.mvt.datatype.ComputationListEntry;

/**
 * @author Jie Zheng
 *
 */
public class MVTCalculateEvent extends EventObject {
	public MVTCalculateEvent(ComputationListEntry source){	
		super(source);
	}
}
