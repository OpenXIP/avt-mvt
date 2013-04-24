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
