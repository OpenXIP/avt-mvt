package com.siemens.cmiv.avt.mvt.ad;


import java.util.ArrayList;
import java.util.List;

import com.siemens.cmiv.avt.mvt.datatype.RetrieveResult;


/**
 * @author Jie Zheng
 *
 */
public class ADRetrieveResult {
	String datasoureDescription;
	
	List<RetrieveResult> aims = new ArrayList<RetrieveResult>();
	
	public ADRetrieveResult(String datasoureDescription){
		this.datasoureDescription = datasoureDescription;
	}
	
	@Override
	public String toString(){
		return new String("Retrieve Result:" + this.datasoureDescription);
	}
	
	public void addAim(RetrieveResult aim){
		this.aims.add(aim);
	}
	public List<RetrieveResult> getAims(){
		return aims;
	}
	
	public boolean contains(String seriesUID){		
		for(int i = 0; i < aims.size(); i++){
			RetrieveResult aim = aims.get(i);
			
			String _seriesUID = aim.getSeriesUID();
			
			if(_seriesUID.equalsIgnoreCase(seriesUID)){return true;}
		}			
		return false;
	}
}
