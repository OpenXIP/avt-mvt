/*
Copyright (c) 2010, Siemens Corporate Research a Division of Siemens Corporation 
All rights reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
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
