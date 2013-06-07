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

import java.io.ByteArrayInputStream;
import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import java.util.List;
import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

import com.siemens.scr.avt.ad.annotation.ImageAnnotation;
import com.siemens.scr.avt.ad.api.ADFacade;
import com.siemens.scr.avt.ad.api.FacadeManager;

public class ADRetrieve implements Runnable {
	ADFacade adService;	
	
	String outDir;
	List<String> seriesUIDs;
	
	public ADRetrieve(List<String> seriesUIDs, String outDir){
		this.outDir = outDir;
		this.seriesUIDs = seriesUIDs;
		try {
			Class.forName("com.siemens.scr.avt.ad.api.impl.DefaultADFacadeImpl");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		adService = FacadeManager.getFacade();
		if(adService == null){
			System.out.println("Connection problem");
		}
	}

	SAXBuilder builder = new SAXBuilder();
	Document document;
	XMLOutputter outToXMLFile = new XMLOutputter();
	
	@Override
	public void run() {
//		ADRetrieveResult resultAD = new ADRetrieveResult("DB2 AD AVT");
//		try {
//			for (int i = 0; i < seriesUIDs.size(); i++){
//				String seriesUID = seriesUIDs.get(i);
//				RetrieveResult aimResult = retrieve(seriesUID);
//				
//				//the retrieved results must at least has one annotation & one GT
//				if (aimResult.includeAnnotation() && aimResult.includeGroundTruth())
//					resultAD.addAim(aimResult);
//			}
//			fireResultsAvailable(resultAD);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (NullPointerException e){
//			System.out.println("null pointer in ADRetrive");
//		}
	}
	
//	public RetrieveResult retrieve(String seriesUID) throws IOException, SQLException  {
//		
//		RetrieveResult adResult = new RetrieveResult();
//		adResult.setSeriesUID(seriesUID);
		
/*
		HashMap<Integer, Object> adDicomCriteria = new HashMap<Integer, Object>();
		HashMap<String, Object> adAnnotationCriteria = new HashMap<String, Object>();
		
		adDicomCriteria.put(Tag.SeriesInstanceUID, seriesUID);
		
//		List<ImageAnnotation> imageAnnotations = adService.retrieveAnnotations(adDicomCriteria, null);
//		int nSize = imageAnnotations.size();
//		for (int i = 0; i < nSize; i++){
//			ImageAnnotation imageAnnotation = imageAnnotations.get(i);
//		}
		

		List<String> annotationUIDs = adService.findAnnotations(adDicomCriteria, null);
		System.out.println(annotationUIDs.size());
		
		for (int i = 0; i < annotationUIDs.size(); i++){
			String annotationUID = annotationUIDs.get(i);
			
			ImageAnnotation imageAnnotation = adService.getAnnotation(annotationUID);
			
			AimResult aimResult = serialize(imageAnnotation);
			adResult.addAimFiles(aimResult);
		}
	
*/
		//retrieve nominal ground truth
//		List<ImageAnnotation> imageAnnotations = adService.retrieveAnnotationsInSeries(seriesUID, "GroundTruth");
//		int iSize = imageAnnotations.size();
//		if (iSize <= 0){
//			System.out.println("Missing GroungTruth aim objects for seriesInstanceUID: " + seriesUID);
//		}
//
//		for (int i = 0; i < iSize; i++){
//			ImageAnnotation imageAnnotation = imageAnnotations.get(i);
//			
//			AimResult aimResult = serialize(imageAnnotation);
//			aimResult.setGroundTruth(true);
//			adResult.addAimFiles(aimResult);
//			
//			adResult.setGroundTruth(true);
//		}
		
		//retrieve algorithm results
//		imageAnnotations = adService.retrieveAnnotationsInSeries(seriesUID, "Algorithm");
//		iSize = imageAnnotations.size();
//		if (iSize <= 0){
//			System.out.println("Missing Algorithm aim objects for seriesInstanceUID: " + seriesUID);
//		}
//		for (int i = 0; i < iSize; i++){
//			ImageAnnotation imageAnnotation = imageAnnotations.get(i);
//			
//			AimResult aimResult = serialize(imageAnnotation);
//			aimResult.setGroundTruth(false);
//			adResult.addAimFiles(aimResult);
//			
//			adResult.setAnnotation(true);
//		}		
		
//		return adResult;
//	}
	
//	MVTListener listener;
//    public void addMVTListener(MVTListener l) {        
//        listener = l;          
//    }
//	void fireResultsAvailable(ADRetrieveResult result){
//		ADRetrieveEvent event = new ADRetrieveEvent(result);         		
//        listener.retriveResultsAvailable(event);
//	}

//	public AimResult serialize(ImageAnnotation imageAnnotation) throws IOException, SQLException, NullPointerException{
//    	
//		String strXML = imageAnnotation.getAIM();
//		byte[] source = strXML.getBytes();
//		InputStream is = new ByteArrayInputStream(source);
//		try {
//			document = builder.build(is);
//		} catch (JDOMException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}	
//		
//		String annotatinUID = imageAnnotation.getDescriptor().getUID();
//		File outFile = new File(outDir + "aim_" + annotatinUID + ".xml");
//		
//		try {
//			FileOutputStream outStream = new FileOutputStream(outFile);			
//			outToXMLFile.output(document, outStream);
//	    	outStream.flush();
//	    	outStream.close();
//		} catch (IOException e) {
//			System.out.println("fail to store aim xml file:" + outFile.toString());
//		}
//		
//    	String aimFile = outFile.toString();
//    	
//		System.out.println("Store "+aimFile+" successfully");
//		
//		AimResult aimResult = new AimResult(aimFile);
		//Get attachment
//		Set<AnnotationAttachment> annotationAttachments = imageAnnotation.getAttachments();
//		
//		int nSize = annotationAttachments.size();
//		if (nSize <= 0){
//			System.out.println("Missing DICOM SEG file for AnnotationInstanceUID: " + annotatinUID);
//		}

//		for(AnnotationAttachment anot : annotationAttachments) {
//			DicomObject segObject = DicomParser.read(anot.getAttachmentStream());
//			String segUID = segObject.getString(Tag.SOPInstanceUID);
//			
//			String patientName = segObject.getString(Tag.PatientName);
//			aimResult.setPatientName(patientName);
//			
//			String patientID = segObject.getString(Tag.PatientID);
//			aimResult.setPatientID(patientID);
//			
//	    	String studyUID = segObject.getString(Tag.StudyInstanceUID);
//			aimResult.setStudyUID(studyUID);
//			
//	    	String seriesUID = segObject.getString(Tag.FrameOfReferenceUID);
//			aimResult.setSeriesUID(seriesUID);
//
//	    	File fileName = new File(outDir + "seg_" + segUID + ".dcm");
//	    	
//	    	try {
//				DicomOutputStream dout = new DicomOutputStream(new FileOutputStream(fileName));
//				dout.writeDicomFile(segObject);
//				dout.close();
//	    	} catch(IOException e){
//				System.out.println("fail to store DICOM seg file:" + fileName.toString());
//	    	}
//			
//			aimResult.addSegFile(fileName.getPath());
//			
//			System.out.println("store "+fileName.getPath()+" successfully");
//		}
		
//		return aimResult;
//	}
}
