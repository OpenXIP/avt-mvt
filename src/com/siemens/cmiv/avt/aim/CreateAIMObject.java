package com.siemens.cmiv.avt.aim;

import gme.cacore_cacore._4_4.edu_northwestern_radiology.AimVersion;
import gme.cacore_cacore._4_4.edu_northwestern_radiology.AnnotationEntity.ImagingObservationEntityCollection;
import gme.cacore_cacore._4_4.edu_northwestern_radiology.DicomImageReferenceEntity;
import gme.cacore_cacore._4_4.edu_northwestern_radiology.Equipment;
import gme.cacore_cacore._4_4.edu_northwestern_radiology.Image;
import gme.cacore_cacore._4_4.edu_northwestern_radiology.ImageAnnotation;
import gme.cacore_cacore._4_4.edu_northwestern_radiology.ImageAnnotation.ImageReferenceEntityCollection;
import gme.cacore_cacore._4_4.edu_northwestern_radiology.ImageAnnotationCollection;
import gme.cacore_cacore._4_4.edu_northwestern_radiology.ImageReferenceEntity;
import gme.cacore_cacore._4_4.edu_northwestern_radiology.ImageSeries;
import gme.cacore_cacore._4_4.edu_northwestern_radiology.ImageSeries.ImageCollection;
import gme.cacore_cacore._4_4.edu_northwestern_radiology.ImageStudy;
import gme.cacore_cacore._4_4.edu_northwestern_radiology.ImagingObservationCharacteristic;
import gme.cacore_cacore._4_4.edu_northwestern_radiology.ImagingObservationEntity;
import gme.cacore_cacore._4_4.edu_northwestern_radiology.ObjectFactory;
import gme.cacore_cacore._4_4.edu_northwestern_radiology.Person;
import gme.cacore_cacore._4_4.edu_northwestern_radiology.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import uri.iso_org._21090.II;
import uri.iso_org._21090.ST;
import uri.iso_org._21090.TS;

import com.pixelmed.dicom.Attribute;
import com.pixelmed.dicom.AttributeList;
import com.pixelmed.dicom.AttributeTag;
import com.pixelmed.dicom.DicomException;
import com.pixelmed.dicom.DicomInputStream;
import com.pixelmed.dicom.SequenceAttribute;
import com.pixelmed.dicom.SequenceItem;
import com.pixelmed.dicom.UIDGenerator;

public class CreateAIMObject {
	
	private ObjectFactory factory = new ObjectFactory(); 
	
	private String aim_Version = "TCGA";
	private String aim_SchemeDesignator = "AVT2";
	private String aim_SchemaVersion = "v0_rv1";
	private String aim_CodeValue = "AVT2";
	private String aim_CodeMeaning = "CDRH baseline target lesion";
	
	private String annotation_Reader = "";
	private int annotation_Role = 0;
	
	private String algo_manufacturer = "";
	private String algo_model = "";
	private String algo_version = "";
	
	private String patient_Name = "";
	private String patient_ID = "";
	private String patient_Gender = "";
	
	public CreateAIMObject(){
		aim_SchemeDesignator = "AVT2";
		aim_SchemaVersion = "v0_rv1";
		aim_CodeValue = "AVT2";
		aim_CodeMeaning = "CDRH baseline target lesion";
		
		annotation_Reader = "Reader1";
		annotation_Role = 0;
		
		algo_manufacturer = "Siemens";
		algo_model = "AVT2EXT";
		algo_version = "0.1.2";

		patient_Name = "";
		patient_ID = "";
		patient_Gender = "";		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		assert args.length > 0 : "Input DICOM segment object and Output AIM object should be provided!";
		
		String aimFile = args[0];
		File aim = new File(aimFile);
		
		String seed = args[1];
		String uid = args[2];
		
		String dcmFile = args[3];
		File dcm = new File(dcmFile);
		assert dcm != null : "Input DICOM segment object should exist!"; 
		
		CreateAIMObject aimCreator = new CreateAIMObject();
		aimCreator.marshallVolume(aim, seed, uid, dcm);
	}
	
	public void setAIMInformation(String version, String schemeDesignator, String schemaVersion, String codeValue, String codeMeaning){
		aim_Version = version;
		aim_SchemeDesignator = schemeDesignator;
		aim_SchemaVersion = schemaVersion;
		aim_CodeValue = codeValue;
		aim_CodeMeaning = codeMeaning;
	}
	
	public void setAnnotationInformation(String annotator, int role){
		annotation_Reader = annotator;
		annotation_Role = role;
	}
	
	public void setAlgorithmInformatation(String manufacturer, String model, String version){
		algo_manufacturer = manufacturer;
		algo_model = model;
		algo_version = version;
	}
	
	public void setPatientInformation(String pat_Name, String pat_ID, String pat_Gender){
		patient_Name = pat_Name;
		patient_ID = pat_ID;
		patient_Gender = pat_Gender;
	}
	
	public void marshallVolume(File aimFile, String seedPoint, String seedUID, File segDcm) {
		try {
			//create ImageAnnotation
			ImageAnnotationCollection imageAnnotationCollection = factory.createImageAnnotationCollection();
			II uniqueIdentifier = new II();
			uniqueIdentifier.setIdentifierName(BigInteger.ZERO.toString()).
			//imageAnnotationCollection.setUniqueIdentifier(uniqueIdentifier);
			
			ImageAnnotation annot = factory.createImageAnnotation();
			II value = new II();
			value.setIdentifierName(BigInteger.ZERO.toString()).
			
			TS dateTime = new TS();
			dateTime.setValue(getCurrentTime().toString());
			
			ST stValue = new ST();
			stValue.setValue(annotation_Reader);
			annot.setName(stValue);
			
			
			//annot.setCodingSchemeDesignator(aim_SchemeDesignator);
			//annot.setCodingSchemeVersion(aim_SchemaVersion);
			//annot.setCodeValue(aim_CodeValue);
			//annot.setCodeMeaning(aim_CodeMeaning);

			UIDGenerator uid = new UIDGenerator();
			II identifier = new II();
			identifier.setIdentifierName(uid.getNewUID());
			annot.setUniqueIdentifier(identifier);
	
			//create user
			gme.cacore_cacore._4_4.edu_northwestern_radiology.User user = factory.createUser();
			ST name = new ST();
			name.setValue(annotation_Reader);
			user.setName(name);
			
			user.setId(new BigInteger("0"));
			
			
			ST loginName = new ST();
			loginName.setValue("wustl");
			user.setLoginName(loginName);
			switch (annotation_Role) {
			case 0://nominal ground truth
				ST roleInTrial1 = new ST();
				roleInTrial1.setValue("Norminal GroundTruth");
				user.setRoleInTrial(roleInTrial1);
				break;
				
			case 1://algorithm
				ST roleInTrial2 = new ST();
				roleInTrial2.setValue("Algorithm");
				user.setRoleInTrial(roleInTrial2);
				break;
			}
			
			Annotation.User user2 = factory.createAnnotationUser();
			user2.setUser(user);
			annot.setUser(user2);					
			
			//create Equipment
			Equipment equipment = assembleEquipment(algo_manufacturer, algo_model, algo_version);
			imageAnnotationCollection.setEquipment(equipment);
			
			//create AnatomicEntity
			annot.setAnatomicEntityCollection(assmbleAnatomicEntity());

			//create GeometricShape Collection to store the seed
			annot.setGeometricShapeCollection(assmblePolylineShapeCollection(seedPoint, seedUID));

			//create imagingObservation
//			annot.setImagingObservationCollection(assmbleImageObservations());			
			
			//create segmentation Collection
			List<File> segFiles = new ArrayList<File>();
			segFiles.add(segDcm);
			annot.setSegmentationCollection(assembleSegmentationCollection(segFiles));
			
			//create imageReference collection
			annot.setImageReferenceCollection(assembleImageReferenceCollection(segDcm));

			//Create patient
			gme.cacore_cacore._4_4.edu_northwestern_radiology.Patient pat = factory.createPatient();
			
			pat.setId(BigInteger.ZERO);
			
			//PatientName
			pat.setName(patient_Name);
			
			//PatientID
			pat.setPatientID(patient_ID);

			//PatientSex
			pat.setSex(patient_Gender);
			
			ImageAnnotation.Patient _pat = factory.createImageAnnotationPatient();
			_pat.setPatient(pat);
			annot.setPatient(_pat);

			try {
		   	   JAXBContext jaxbContext = JAXBContext.newInstance("gme.cacore_cacore._3_2.edu_northwestern_radiology");
	    	   Marshaller marshaller = jaxbContext.createMarshaller();
	           marshaller.setProperty("jaxb.schemaLocation", "gme://caCORE.caCORE/3.2/edu.northwestern.radiology.AIM AIM_TCGA09302009_XML.xsd");
	           JAXBElement<ImageAnnotation> catalogElement = factory.createImageAnnotation(annot);
	           marshaller.marshal(catalogElement,new FileOutputStream(aimFile));
 			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (DicomException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	public void marshallRECIST(File aimFile, File segDcm, String recist, String recistPoint,
			String recistUID) {
		try {
			//create ImageAnnotation
			ImageAnnotationCollection imageAnnotationCollection = factory.createImageAnnotationCollection();
			
			ImageAnnotation annot = factory.createImageAnnotation();
			II uniqueIdentifier = new II();
			uniqueIdentifier.setIdentifierName(BigInteger.ZERO.toString());
			
			imageAnnotationCollection.setAimVersion(AimVersion.AI_MV_4_0);
			TS dateTime = new TS();
			dateTime.setValue(getCurrentTime().toString());
			ST schemeDesignator = new ST();
			schemeDesignator.setValue(aim_SchemeDesignator);
			annot.setName(schemeDesignator);
			//annot.setCodingSchemeDesignator(aim_SchemeDesignator);
			//annot.setCodingSchemeVersion(aim_SchemaVersion);
			//annot.setCodeValue(aim_CodeValue);
			//annot.setCodeMeaning(aim_CodeMeaning);

			UIDGenerator uid = new UIDGenerator();
			II identifier = new II();
			identifier.setIdentifierName(uid.getNewUID());
			annot.setUniqueIdentifier(identifier);
	
			//create user
			User user = factory.createUser();
			ST name = new ST();
			name.setValue(annotation_Reader);
			user.setName(name);
			ST loginName = new ST();
			loginName.setValue("wustl");
			user.setLoginName(loginName);
			ST roleInTrial = new ST();
			switch (annotation_Role) {
			case 0://nominal ground truth
				roleInTrial.setValue("Norminal GroundTruth");
				user.setRoleInTrial(roleInTrial);
				break;
				
			case 1://algorithm
				roleInTrial.setValue("Algorithm");
				user.setRoleInTrial(roleInTrial);
				break;
			}
								
			imageAnnotationCollection.setUser(user);
			imageAnnotationCollection.setAimVersion(AimVersion.AI_MV_4_0);
			//create Equipment
			imageAnnotationCollection.setEquipment(assembleEquipment(algo_manufacturer, algo_model, algo_version));
			
			//create AnatomicEntity
			annot.setAnatomicEntityCollection(assmbleAnatomicEntity());

			//create Calculation collection
			annot.setCalculationCollection(assmbleCalculationCollection(recist, "mm"));

			//create GeometricShape Collection to store the seed
			annot.setGeometricShapeCollection(assmblePolylineShapeCollection(recistPoint, recistUID));

			//create imageReference collection
			annot.setImageReferenceCollection(assembleImageReferenceCollection(segDcm, recistUID));

			//Create patient
			Person pat = factory.createPerson();
			ST id = new ST();
			id.setValue(patient_ID);
			pat.setId(id);
			
			//PatientName
			ST patientName = new ST();
			patientName.setValue(patient_Name);
			pat.setName(patientName);

			//PatientSex
			ST patientSex = new ST();
			patientSex.setValue(patient_Gender);
			pat.setSex(patientSex);
			
			imageAnnotationCollection.setPerson(pat);
			try {
		   	   JAXBContext jaxbContext = JAXBContext.newInstance("gme.cacore_cacore._4_4.edu_northwestern_radiology");
	    	   Marshaller marshaller = jaxbContext.createMarshaller();
	           marshaller.setProperty("jaxb.schemaLocation", "gme://caCORE.caCORE/4.4/edu.northwestern.radiology.AIM.xsd");
	           JAXBElement<ImageAnnotationCollection> catalogElement = factory.createImageAnnotationCollection(imageAnnotationCollection);
	           marshaller.marshal(catalogElement,new FileOutputStream(aimFile));
 			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (DicomException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void marshallWHO(File aimFile, File segDcm, String who, String whoPoint, String whoUID) {
		try {
			//create ImageAnnotation
			ImageAnnotationCollection imageAnnotationCollection = factory.createImageAnnotationCollection();
			ImageAnnotation annot = factory.createImageAnnotation();
			annot.setId(BigInteger.ZERO);
			annot.setAimVersion(aim_Version);
			annot.setDateTime(getCurrentTime());
			annot.setName(annotation_Reader);
			//annot.setCodingSchemeDesignator(aim_SchemeDesignator);
			//annot.setCodingSchemeVersion(aim_SchemaVersion);
			//annot.setCodeValue(aim_CodeValue);
			//annot.setCodeMeaning(aim_CodeMeaning);

			UIDGenerator uid = new UIDGenerator();
			annot.setUniqueIdentifier(uid.getNewUID());
	
			//create user
			gme.cacore_cacore._4_4.edu_northwestern_radiology.User user = factory.createUser();
			ST readerName = new ST();
			readerName.setValue(annotation_Reader);
			user.setName(readerName);
			ST loginName = new ST();
			loginName.setValue("wustl");
			user.setLoginName(loginName);
			switch (annotation_Role) {
			case 0://nominal ground truth
				ST roleInTrial1 = new ST();
				roleInTrial1.setValue("Norminal GroundTruth");
				user.setRoleInTrial(roleInTrial1);
				break;
				
			case 1://algorithm
				ST roleInTrial2 = new ST();
				roleInTrial2.setValue("Algorithm");
				user.setRoleInTrial(roleInTrial2);
				break;
			}			
			imageAnnotationCollection.setUser(user);
			//create Equipment
			imageAnnotationCollection.setEquipment(assembleEquipment(algo_manufacturer, algo_model, algo_version));
			
			//create AnatomicEntity
			annot.setAnatomicEntityCollection(assmbleAnatomicEntity());

			//create Calculation collection
			annot.setCalculationCollection(assmbleCalculationCollection(who, "mm2"));

			//create GeometricShape Collection to store the seed
			annot.setGeometricShapeCollection(assmblePolylineShapeCollection(whoPoint, whoUID));

			//create imageReference collection
			annot.setImageReferenceCollection(assembleImageReferenceCollection(segDcm, whoUID));

			//Create patient
			Person pat = factory.createPerson();
			
			//PatientName
			ST patientName = new ST();
			patientName.setValue(patient_Name);
			pat.setName(patientName);
			
			//PatientID
			ST patientId = new ST();
			patientId.setValue(patient_ID);
			pat.setId(patientId);

			//PatientSex
			ST patientSex = new ST();
			patientSex.setValue(patient_Gender);
			pat.setSex(patientSex);
			
			imageAnnotationCollection.setPerson(pat);
			try {
		   	   JAXBContext jaxbContext = JAXBContext.newInstance("gme.cacore_cacore._4_4.edu_northwestern_radiology");
	    	   Marshaller marshaller = jaxbContext.createMarshaller();
	           marshaller.setProperty("jaxb.schemaLocation", "gme://caCORE.caCORE/4.4/edu.northwestern.radiology.AIM.xsd");
	           JAXBElement<ImageAnnotationCollection> catalogElement = factory.createImageAnnotationCollection(imageAnnotationCollection);
	           marshaller.marshal(catalogElement,new FileOutputStream(aimFile));
 			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (DicomException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private CalculationCollection assmbleCalculationCollection(String value, String unit) {
		// set Calculation Collection
		CalculationCollection calculationColl = factory.createAnnotationCalculationCollection();
		List<Calculation> calculationList = calculationColl.getCalculation();
		
		Calculation cal = factory.createCalculation();
		
		if (unit.compareToIgnoreCase("mm") == 0){
			cal.setCodeMeaning("Long_Axis");
			cal.setCodeValue("AVT2");
			cal.setCodingSchemeDesignator("AVT2");
			cal.setMathML("");
			cal.setId(BigInteger.ZERO);
			cal.setDescription("Linear Measurement");
			cal.setUid("0");
		}
		
		if (unit.compareToIgnoreCase("mm2") == 0){
			cal.setCodeMeaning("CrossProduct");
			cal.setCodeValue("AVT2");
			cal.setCodingSchemeDesignator("AVT2");
			cal.setMathML("");
			cal.setId(BigInteger.ZERO);
			cal.setDescription("Area Measurement");
			cal.setUid("0");
		}
		
		CalculationResultCollection resultColl = factory.createCalculationCalculationResultCollection();
		List<CalculationResult> calculationResult = resultColl.getCalculationResult();
		calculationResult.add(getCalResult(value, unit));
		cal.setCalculationResultCollection(resultColl);

		calculationList.add(cal);
		
		return calculationColl;
	}
	
	private CalculationResult getCalResult(String value, String unit){
		CalculationResult calResult = factory.createCalculationResult();
		calResult.setNumberOfDimensions(BigInteger.valueOf(1));
		calResult.setId(BigInteger.ZERO);
		calResult.setUnitOfMeasure(unit);
		calResult.setType(CalculationResultIdentifier.SCALAR);
		
		// set dimensionCollection node
		gme.cacore_cacore._4_4.edu_northwestern_radiology.Dimension dimension1 = factory.createDimension();
		gme.cacore_cacore._4_4.edu_northwestern_radiology.Dimension dimension2 = factory.createDimension();
		if (unit.compareToIgnoreCase("mm") == 0){		
			dimension1.setId(BigInteger.ZERO);
			dimension1.setIndex(BigInteger.ZERO);
			dimension1.setLabel("Length");
			dimension1.setSize(BigInteger.ONE);
		}
		if (unit.compareToIgnoreCase("mm2") == 0){
			dimension1.setId(BigInteger.ZERO);
			dimension1.setIndex(BigInteger.ZERO);
			dimension1.setLabel("Length0");
			dimension1.setSize(BigInteger.ONE);
		}
			
		CalculationResult.DimensionCollection dimCollection = factory.createCalculationResultDimensionCollection();
		List<gme.cacore_cacore._4_4.edu_northwestern_radiology.Dimension> dimList = dimCollection.getDimension();
		dimList.add(dimension1);
		
		if (unit.compareToIgnoreCase("mm2") == 0){
			// dimension two
			dimension2.setId(BigInteger.ZERO);
			dimension2.setIndex(BigInteger.ONE);
			dimension2.setLabel("Length1");
			dimension2.setSize(BigInteger.ONE);
			
			dimList.add(dimension2);
		}
		
		calResult.setDimensionCollection(dimCollection);
		
		// set dataCollection node
		Coordinate coordinate = factory.createCoordinate();
		coordinate.setDimensionIndex(BigInteger.ZERO);
		coordinate.setId(BigInteger.ZERO);
		coordinate.setPosition(BigInteger.ZERO);
		
		Data.CoordinateCollection coordinateCollection = factory.createDataCoordinateCollection();
		List<Coordinate> cooList = coordinateCollection.getCoordinate();
		cooList.add(coordinate);
		
		Data data = factory.createData();
		data.setCoordinateCollection(coordinateCollection);
		data.setId(BigInteger.ZERO);
		data.setValue(Integer.parseInt(value));
		
		CalculationResult.DataCollection dataCollection = factory.createCalculationResultDataCollection();
		List<Data> dataList = dataCollection.getData();
		dataList.add(data);
		
		calResult.setDataCollection(dataCollection);
		
		return calResult;
	}
	
	private ImageReferenceCollection assembleImageReferenceCollection(
			File segDcm, String recistUID) {
		DicomImageReferenceEntity imageRef = factory.createDicomImageReferenceEntity();
		II identifier = new II();
		identifier.setIdentifierName(BigInteger.ZERO.toString())
		imageRef.setUniqueIdentifier(identifier);
		
		//study
		DICOMImageReference.Study study = factory.createDICOMImageReferenceStudy();
		
		ImageStudy normalStudy = factory.createImageStudy();

		//parse the DICOM segment object
		DicomInputStream segInput;
		AttributeList tags = new AttributeList();
		try {
			segInput = new DicomInputStream(segDcm);
			tags.read(segInput);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DicomException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		//study InstanceUID
		AttributeTag tag = new AttributeTag(0x20, 0x0d);
		Attribute attrib = tags.get(tag);
		if (attrib != null)
		{
			II instanceUID = new II();
			instanceUID.setIdentifierName(attrib.getSingleStringValueOrEmptyString());
			normalStudy.setInstanceUid(instanceUID);
		}
		
		//series
		ImageSeries seriesItem = factory.createImageSeries();
		
		//series Number
		tag = new AttributeTag(0x20, 0x11);
		attrib = tags.get(tag);
		
		//series instance UID (as the FrameofReferenceUID)
		tag = new AttributeTag(0x20, 0x52);
		attrib = tags.get(tag);
		if (attrib != null)
		{
			II instanceUID = new II();
			instanceUID.setIdentifierName(attrib.getSingleStringValueOrEmptyString());
			seriesItem.setInstanceUid(instanceUID);
		}
		
		ImageCollection imageColl = factory.createImageSeriesImageCollection();

		List<Image> imageList = imageColl.getImage();
		Image image = factory.createImage();
		
//		tag = new AttributeTag(0x08, 0x16);
//		attrib = tags.get(tag);
//		image.setSopClassUID(attrib.getSingleStringValueOrEmptyString());
		II sopInstanceUID = new II();
		sopInstanceUID.setIdentifierName(recistUID);
		image.setSopInstanceUid(sopInstanceUID);
		
		imageList.add(image);
		
		seriesItem.setImageCollection(imageColl);
		
		normalStudy.setImageSeries(seriesItem);
		study.setStudy(normalStudy);
		
		imageRef.setStudy(study);
		
		ImageReferenceCollection imagingReferenceColl = factory.createImageAnnotationImageReferenceCollection();
		List<ImageReference> imageReferenceList = imagingReferenceColl.getImageReference();
		imageReferenceList.add(imageRef);
		
		return imagingReferenceColl;
	}
	
	private GeometricShapeCollection assmblePolylineShapeCollection( String seedPoint, String seedUID) {
			
		GeometricShapeCollection geoShapeColl = factory.createImageAnnotationGeometricShapeCollection();
		List<GeometricShape> geoShapeList = geoShapeColl.getGeometricShape();
		
		gme.cacore_cacore._3_2.edu_northwestern_radiology.Polyline polyline = factory.createPolyline();
		polyline.setId(BigInteger.ZERO);
		polyline.setIncludeFlag(false);
		polyline.setLineColor("");
		polyline.setLineOpacity("");
		polyline.setLineStyle("");
		polyline.setLineThickness("");
		polyline.setShapeIdentifier(BigInteger.valueOf(0));
		
		SpatialCoordinateCollection  spatialCoordinateColl = factory.createGeometricShapeSpatialCoordinateCollection();
		List<SpatialCoordinate> spatialCoordinateList = spatialCoordinateColl.getSpatialCoordinate();
		
		String tmp = (seedPoint.replace('[', ' ')).replace(']', ' ');
		String[] buffer = tmp.trim().split(",");
		for (int i = 0; i < buffer.length/2; i++){
			//point
			TwoDimensionSpatialCoordinate twoDimSpaCoordinate = factory.createTwoDimensionSpatialCoordinate();
			twoDimSpaCoordinate.setId(BigInteger.valueOf(0));
			twoDimSpaCoordinate.setImageReferenceUID(seedUID);
			
			String val = (buffer[2*i]).trim();
			twoDimSpaCoordinate.setX(Integer.parseInt(val));
			val = (buffer[2*i+1]).trim();
			twoDimSpaCoordinate.setY(Integer.parseInt(val));
			twoDimSpaCoordinate.setCoordinateIndex(BigInteger.valueOf(0));
			twoDimSpaCoordinate.setReferencedFrameNumber(BigInteger.valueOf(i));
			
			spatialCoordinateList.add(twoDimSpaCoordinate);
		}
		
		polyline.setSpatialCoordinateCollection(spatialCoordinateColl);
		
		geoShapeList.add(polyline);
		
		return geoShapeColl;
	}
	  public AnatomicEntityCollection assmbleAnatomicEntity(){
	    	AnatomicEntityCollection anatomicentityCollection = factory.createAnnotationAnatomicEntityCollection();
	    	
	    	List<AnatomicEntity> anatomicentityList = anatomicentityCollection.getAnatomicEntity();
	    	AnatomicEntity anatomicentity = factory.createAnatomicEntity();
	    	
	    	anatomicentity.setId(BigInteger.ZERO);
	    	anatomicentity.setCodeValue("T-28000");
	    	anatomicentity.setCodeMeaning("Lung");
	    	anatomicentity.setCodingSchemeDesignator("SRT");
	    	anatomicentityList.add(anatomicentity);
	    	
	    	return anatomicentityCollection;
	   }

	   public ImagingObservationEntityCollection assmbleImageObservations(){
			ImagingObservationEntity observation = factory.createImagingObservationEntity();
			II identifier = new II();
			identifier.setIdentifierName(BigInteger.ZERO.toString());
			observation.setUniqueIdentifier(identifier);
			//observation.setCodeMeaning("Nodule");
			//observation.setCodeValue("M-03010");
			//observation.setCodingSchemeDesignator("SRT");
						
			ImagingObservationCharacteristic observationCharacter = factory.createImagingObservationCharacteristic();
			//observationCharacter.setCodeMeaning("irregularly shaped");
			//observationCharacter.setCodeValue("RID5809");
			//observationCharacter.setCodingSchemeDesignator("RadLex");
			//observationCharacter.setId(BigInteger.ZERO);
			
			ImagingObservationCharacteristicCollection observationCharacterColl = factory.createImagingObservationImagingObservationCharacteristicCollection();
			List<ImagingObservationCharacteristic> observationCharacterList = observationCharacterColl.getImagingObservationCharacteristic();
			observationCharacterList.add(observationCharacter);
			
			observation.setImagingObservationCharacteristicCollection(observationCharacterColl);
			
			ImagingObservationEntityCollection observationColl = factory.createAnnotationEntityImagingObservationEntityCollection();
			List<ImagingObservation> observationCollList = observationColl.getImagingObservation();
			observationCollList.add(observation);
			
			return observationColl;
	   }
	   
	   public SegmentationCollection assembleSegmentationCollection(List<File> segFiles){
		   SegmentationCollection segCollection = factory.createImageAnnotationSegmentationCollection();
		   List<Segmentation> segList = segCollection.getSegmentation();
		   
		   for (int i = 0; i < segFiles.size(); i++){
				Segmentation seg = factory.createSegmentation();
				seg.setId(new BigInteger(Integer.toString(i)));	
				seg.setType(SegmentationIdentifier.BINARY);
				   
				File segDcm = segFiles.get(i);
				if (!segDcm.exists())
					continue;
				//parse the DICOM segment object
				DicomInputStream segInput;
				AttributeList tags = new AttributeList();
				
				try {
					segInput = new DicomInputStream(segDcm);
					
					tags.read(segInput);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (DicomException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			   
				//get SOPInstanceUID
				AttributeTag tag = new AttributeTag(0x02, 0x03);
				Attribute attrib = tags.get(tag);
				if (attrib != null)
					seg.setReferencedSopInstanceUID(attrib.getSingleStringValueOrEmptyString());
				
				seg.setSegmentNumber(new BigInteger(Integer.toString(i)));
				
				//get SOPClassUID
				tag = new AttributeTag(0x02, 0x02);
				attrib = tags.get(tag);
				if (attrib != null)
					seg.setSopClassUID(attrib.getSingleStringValueOrEmptyString());
				
				UIDGenerator uid = new UIDGenerator();
				//sopInstanceUID
		        GregorianCalendar today = new GregorianCalendar();
				String strSOPInstanceUID;
				try {
					strSOPInstanceUID = uid.getNewSOPInstanceUID(
							Integer.toString(today.get(GregorianCalendar.HOUR_OF_DAY)),
							Integer.toString(today.get(GregorianCalendar.MINUTE)), 
							Integer.toString(today.get(GregorianCalendar.SECOND)));
					
					seg.setSopInstanceUID(strSOPInstanceUID);
				} catch (DicomException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				Segmentation.ImagingObservation segObservation= factory.createSegmentationImagingObservation();
				
				ImagingObservation observation = factory.createImagingObservation();
				observation.setId(new BigInteger("0"));
				observation.setCodeMeaning("Neoplasm");
				observation.setCodeValue("M-8FFFF");
				observation.setCodingSchemeDesignator("SRT");
							
				ImagingObservationCharacteristic observationCharacter = factory.createImagingObservationCharacteristic();
				observationCharacter.setCodeMeaning("irregularly shaped");
				observationCharacter.setCodeValue("RID5809");
				observationCharacter.setCodingSchemeDesignator("RadLex");
				observationCharacter.setId(new BigInteger("0"));
				
				ImagingObservationCharacteristicCollection observationCharacterCollection = factory.createImagingObservationImagingObservationCharacteristicCollection();
				List<ImagingObservationCharacteristic> observationCharacterList = observationCharacterCollection.getImagingObservationCharacteristic();
				observationCharacterList.add(observationCharacter);
				
				observation.setImagingObservationCharacteristicCollection(observationCharacterCollection);
				
				segObservation.setImagingObservation(observation);
				seg.setImagingObservation(segObservation);

				segList.add(seg);
		   }
		   
		   return segCollection;
	   }
	   
	   public Equipment assembleEquipment(String manufacturer, String model, String version){
		   Equipment equip = factory.createEquipment();
		   ST deviceSerialNumber = new ST();
		   deviceSerialNumber.setValue(BigInteger.ZERO.toString());
		   equip.setDeviceSerialNumber(deviceSerialNumber);
		   ST manufacturerName = new ST();
		   manufacturerName.setValue(manufacturer);
		   equip.setManufacturerName(manufacturerName);
		   ST manufacturerModelName = new ST();
		   manufacturerModelName.setValue(model);
		   equip.setManufacturerModelName(manufacturerModelName);
		   ST softwareVersion = new ST();
		   softwareVersion.setValue(version);		   
		   equip.setSoftwareVersion(softwareVersion);
		   return equip;
	   }
	   
	   public ImageReferenceEntityCollection assembleImageReferenceCollection(File segDcm){
			DicomImageReferenceEntity imageRef = factory.createDicomImageReferenceEntity();
			II uniqueIdentifier = new II();
			uniqueIdentifier.setIdentifierName(BigInteger.ZERO.toString());
			imageRef.setUniqueIdentifier(uniqueIdentifier);
			
			//study
			//DICOMImageReference.Study study = factory.createDICOMImageReferenceStudy();
			
			ImageStudy normalStudy = factory.createImageStudy();

			//parse the DICOM segment object
			DicomInputStream segInput;
			AttributeList tags = new AttributeList();
			try {
				segInput = new DicomInputStream(segDcm);
				tags.read(segInput);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DicomException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			//study InstanceUID
			AttributeTag tag = new AttributeTag(0x20, 0x0d);
			Attribute attrib = tags.get(tag);
			if (attrib != null)
			{
				II studyInstanceUid = new II();
				studyInstanceUid.setIdentifierName(attrib.getSingleStringValueOrEmptyString());
				normalStudy.setInstanceUid(studyInstanceUid);
			}
			
			//series
			ImageSeries seriesItem = factory.createImageSeries();
			
			//series Number
			tag = new AttributeTag(0x20, 0x11);
			attrib = tags.get(tag);
			
			//series instance UID (as the FrameofReferenceUID)
			tag = new AttributeTag(0x20, 0x52);
			attrib = tags.get(tag);
			if (attrib != null)
			{
				II seriesInstanceUid = new II();
				seriesInstanceUid.setIdentifierName(attrib.getSingleStringValueOrEmptyString());
				seriesItem.setInstanceUid(seriesInstanceUid);
			}
			
			ImageCollection imageColl = factory.createImageSeriesImageCollection();

			List<Image> imageList = imageColl.getImage();
			
			//shared functional groups sequence
			tag = new AttributeTag(0x5200,0x9229); 
			SequenceAttribute attribSeq = (SequenceAttribute)tags.get(tag);

			if (attribSeq != null){
				int num = attribSeq.getNumberOfItems();
				for (int i = 0; i < num; i++){
					SequenceItem itemSeq = attribSeq.getItem(i);
					AttributeList itemListSeq = itemSeq.getAttributeList();
					
					//Derivation Image sequence
					tag = new AttributeTag(0x0008,0x9124);
					SequenceAttribute sq = (SequenceAttribute)itemListSeq.get(tag);
					
					if (sq != null){
						for (int k = 0; k < sq.getNumberOfItems(); k++){
							SequenceItem item = sq.getItem(k);
							
							Image image = factory.createImage();
							AttributeList itemList = item.getAttributeList();
							tag = new AttributeTag(0x08, 0x16);
							attrib = itemList.get(tag);
							II sopClassUid = new II();
							sopClassUid.setIdentifierName(attrib.getSingleStringValueOrEmptyString());
							image.setSopClassUid(sopClassUid);
							
							tag = new AttributeTag(0x08, 0x18);
							attrib = itemList.get(tag);
							II sopInstanceUid = new II();
							sopInstanceUid.setIdentifierName(attrib.getSingleStringValueOrEmptyString());
							image.setSopInstanceUid(sopInstanceUid);
							
							imageList.add(image);
						}
					}
				}
			}
			
			seriesItem.setImageCollection(imageColl);
			normalStudy.setImageSeries(seriesItem);
			
			imageRef.setImageStudy(normalStudy);
			
			ImageReferenceEntityCollection imagingReferenceColl = factory.createImageAnnotationImageReferenceEntityCollection();
			List<ImageReferenceEntity> imageReferenceEntity = imagingReferenceColl.getImageReferenceEntity();
			imageReferenceEntity.add(imageRef);
			
			return imagingReferenceColl;
	   }
	  private XMLGregorianCalendar getCurrentTime(){
	    	DatatypeFactory factory = null;
	    	try{
	    	    factory = DatatypeFactory.newInstance();
	    	    
	    	}catch (DatatypeConfigurationException e){
	    		e.printStackTrace();
	    	}
	    	XMLGregorianCalendar xmlcalender = factory.newXMLGregorianCalendar();
		    Calendar calendar = Calendar.getInstance();
		    xmlcalender.setYear(calendar.get(Calendar.YEAR));
		    xmlcalender.setMonth(calendar.get(Calendar.MONTH) +1);
		    xmlcalender.setDay(calendar.get(Calendar.DAY_OF_MONTH));
		    xmlcalender.setHour(calendar.get(Calendar.HOUR_OF_DAY));
		    xmlcalender.setMinute(calendar.get(Calendar.MINUTE));
		    xmlcalender.setSecond(calendar.get(Calendar.SECOND));
		    return xmlcalender;
	    }
		

}
