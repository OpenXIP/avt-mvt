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
import java.util.HashMap;
import java.util.List;

import org.dcm4che2.data.Tag;

/**
 * @author Jie Zheng
 *
 */
public class ADSearchResult {
	String datasoureDescription;
	List<HashMap<Integer, Object>> patients = new ArrayList<HashMap<Integer, Object>>();
	
	public ADSearchResult(String datasoureDescription){
		this.datasoureDescription = datasoureDescription;
	}
	
	@Override
	public String toString(){
		return new String("Search Result:" + this.datasoureDescription);
	}
	
	public void addPatient(HashMap<Integer, Object> patient){
		this.patients.add(patient);
	}
	public List<HashMap<Integer, Object>> getPatients(){
		return patients;
	}
	public boolean contains(String patientID){		
		for(int i = 0; i < patients.size(); i++){
			HashMap<Integer, Object> patient = patients.get(i);
			
			String patID = (String) patient.get(Tag.PatientID);
			
			if(patID.equalsIgnoreCase(patientID)){return true;}
		}			
		return false;
	}
}
