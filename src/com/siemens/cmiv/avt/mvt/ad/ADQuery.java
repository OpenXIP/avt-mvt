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

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.dcm4che2.data.DicomObject;
import org.dcm4che2.data.Tag;

import com.siemens.cmiv.avt.mvt.core.MVTListener;
import com.siemens.cmiv.avt.mvt.datatype.FilterEntry;
import com.siemens.scr.avt.ad.api.ADFacade;
import com.siemens.scr.avt.ad.dicom.GeneralSeries;
import com.siemens.scr.avt.ad.dicom.GeneralStudy;
import com.siemens.scr.avt.ad.dicom.Patient;


 
public class ADQuery implements Runnable {
	
	final double EPSILON = 1e-10;
	
	ADFacade adService;	
	FilterEntry filter;
	
	public ADQuery(FilterEntry filter){
		this.filter = filter;
		adService = ADFactory.getADServiceInstance();
		if(adService == null){
			System.out.println("Connection problem");
		}
	}

	public void run() {
		int filterAge = filter.getAge();
		float filterSlice = filter.getSliceThickness();
		
		ADSearchResult resultAD = new ADSearchResult("DB2 AD AVT");
		
		try {
		HashMap<Integer, Object> adCriteria = convertToADDicomCriteria(filter);
		List<Patient> patients = adService.findPatientByCriteria(adCriteria, null);
		
		for (int i = 0; i < patients.size(); i++){
			Patient patient = patients.get(i);
			
			Set<GeneralStudy> studies = adService.retrieveStudiesOf(patient);
			for(GeneralStudy study : studies){
				
				Set<GeneralSeries> series = adService.retrieveSeriesOf(study);
				for (GeneralSeries serie : series){
					
					HashMap<Integer, Object> pat = new HashMap<Integer, Object>();
					pat.put(Tag.PatientName, patient.getPatientName());
					pat.put(Tag.PatientID, patient.getPatientID());
					pat.put(Tag.PatientSex, patient.getPatientSex());
					
					DicomObject dcmObject = patient.getDicomObject();
					Date date = dcmObject.getDate(Tag.PatientBirthDate);
					Calendar _birthDate = Calendar.getInstance();
					if (date != null)
						_birthDate.setTime(date);
					
					dcmObject = study.getDicomObject();
					date = dcmObject.getDate(Tag.StudyDate);
					Calendar _studyDate = Calendar.getInstance();
					if (date != null)
						_studyDate.setTime(date);
					
					int age = _studyDate.get(Calendar.YEAR) - _birthDate.get(Calendar.YEAR);
					if (age > 0)
						pat.put(Tag.PatientAge, Integer.toString(age));

					if (!validateAge(filterAge, age))
						continue;
					
					String studyDate = dcmObject.getString(Tag.StudyDate);
					pat.put(Tag.StudyDate, studyDate);
					String studyTime = dcmObject.getString(Tag.StudyTime);
					pat.put(Tag.StudyTime, studyTime);
					pat.put(Tag.StudyDescription, study.getStudyDescription());
					pat.put(Tag.StudyInstanceUID, study.getStudyInstanceUID());
					
					pat.put(Tag.Modality, serie.getModality());
					pat.put(Tag.SeriesDescription, serie.getSeriesDescription());
					pat.put(Tag.SeriesInstanceUID, serie.getSeriesInstanceUID());
/*					
					if (filterSlice > 0.0f){
						HashMap<Integer, Object> adImageCriteria = new HashMap<Integer, Object>();
						adImageCriteria.put(Tag.SeriesInstanceUID, serie.getSeriesInstanceUID());
						List<DicomObject> objects = adService.retrieveDicomObjsWithoutPixel(adImageCriteria, null);
						if (objects.size() > 0){
							DicomObject object = objects.get(0);
							float slice = object.getFloat(Tag.SliceThickness);
							
							if (!validateSlice(filterSlice, slice))
								continue;
						}
					}
*/				
					resultAD.addPatient(pat);
				}
			}
		}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		fireResultsAvailable(resultAD);
		
	}
	
	MVTListener listener;
    public void addMVTListener(MVTListener l) {        
        listener = l;          
    }
	void fireResultsAvailable(ADSearchResult result){
		ADSearchEvent event = new ADSearchEvent(result);         		
        listener.searchResultsAvailable(event);
	}	

	boolean validateAge(int filterAge, int subjectAge){
		boolean bTheOne = true;
		
		switch(filterAge){
		case 1:
			if (subjectAge > 1)
				bTheOne = false;
			break;
			
		case 2:
			if (subjectAge <= 1 || subjectAge >3)
				bTheOne = false;
			break;
			
		case 3:
			if (subjectAge <= 3 || subjectAge >12)
				bTheOne = false;
			break;
			
		case 4:
			if (subjectAge <= 12 || subjectAge >18)
				bTheOne = false;
			break;
			
		case 5:
			if (subjectAge <= 18 || subjectAge >60)
				bTheOne = false;
			break;
			
		case 6:
			if (subjectAge <= 60 || subjectAge >80)
				bTheOne = false;
			break;
			
		case 7:
			if (subjectAge <= 80)
				bTheOne = false;
			break;
		}

		return bTheOne;
	}
	
	boolean validateSlice(float filterSlice, float slice){
		boolean bTheOne = true;
		
		if (filterSlice > 0.0f && filterSlice != slice)
			bTheOne = false;
		
		return bTheOne;
	}
	
	HashMap<Integer, Object> convertToADDicomCriteria(FilterEntry filter) {
		HashMap<Integer, Object> adCriteria = new HashMap<Integer, Object>();
		
	    String patientSex = filter.getGender();
	    if (!patientSex.equalsIgnoreCase("All"))
	    	adCriteria.put(Tag.PatientSex, patientSex);
			
		return adCriteria;
	}
}
