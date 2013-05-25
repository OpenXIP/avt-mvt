package com.siemens.cmiv.avt.mvt.io;

import gme.cacore_cacore._4_4.edu_northwestern_radiology.AnnotationEntity.CalculationEntityCollection;
import gme.cacore_cacore._4_4.edu_northwestern_radiology.CalculationEntity;
import gme.cacore_cacore._4_4.edu_northwestern_radiology.DicomImageReferenceEntity;
import gme.cacore_cacore._4_4.edu_northwestern_radiology.DicomSegmentationEntity;
import gme.cacore_cacore._4_4.edu_northwestern_radiology.ImageAnnotation;
import gme.cacore_cacore._4_4.edu_northwestern_radiology.ImageAnnotation.SegmentationEntityCollection;
import gme.cacore_cacore._4_4.edu_northwestern_radiology.ImageAnnotationCollection;
import gme.cacore_cacore._4_4.edu_northwestern_radiology.ImageReferenceEntity;
import gme.cacore_cacore._4_4.edu_northwestern_radiology.ImageSeries;
import gme.cacore_cacore._4_4.edu_northwestern_radiology.ImageStudy;
import gme.cacore_cacore._4_4.edu_northwestern_radiology.Person;
import gme.cacore_cacore._4_4.edu_northwestern_radiology.SegmentationEntity;
import gme.cacore_cacore._4_4.edu_northwestern_radiology.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import com.pixelmed.dicom.Attribute;
import com.pixelmed.dicom.AttributeList;
import com.pixelmed.dicom.AttributeTag;
import com.pixelmed.dicom.DicomException;
import com.pixelmed.dicom.DicomInputStream;


public class SubjectListXml {
	private DocumentBuilder builder = null;
	private XPath path = null;
	
	public SubjectListXml(){
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		XPathFactory xPathFactory = XPathFactory.newInstance();
		path = xPathFactory.newXPath();
	}
	public DocumentBuilder getBuilder() {
		return builder;
	}
	public void setBuilder(DocumentBuilder builder) {
		this.builder = builder;
	}
	public XPath getPath() {
		return path;
	}
	public void setPath(XPath path) {
		this.path = path;
	}
	// set Experiment Node.
	private void setExperElement(Element experimentElement,Document doc){
		Text ExperimentID = doc.createTextNode("user1");
		Text ExperimentUID = doc.createTextNode("Experiment1");
		
		Calendar calendar = Calendar.getInstance();
		Text ExperimentDate = doc.createTextNode(String.valueOf(calendar
				.get(Calendar.YEAR))
				+ String.valueOf(calendar.get(Calendar.MONTH) + 1)
				+ String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
		Text ExperimentTime = doc.createTextNode(String.valueOf(calendar
				.get(Calendar.HOUR_OF_DAY))
				+ String.valueOf(calendar.get(Calendar.MINUTE))
				+ String.valueOf(calendar.get(Calendar.SECOND)));
		
		Element ExperID = doc.createElement("Experiment_ID");
		Element ExperUID = doc.createElement("Experiment_UID");
		Element ExperDate = doc.createElement("Experiment_Date");
		Element ExperTime = doc.createElement("Experiment_Time");
		
		ExperID.appendChild(ExperimentID);
		ExperUID.appendChild(ExperimentUID);
		ExperDate.appendChild(ExperimentDate);
		ExperTime.appendChild(ExperimentTime);
		
		experimentElement.appendChild(ExperID);
		experimentElement.appendChild(ExperDate);
		experimentElement.appendChild(ExperTime);
		experimentElement.appendChild(ExperUID);
	}
	
	private void setTextNode(String attribstr, Element eleName, Document doc){
		Text valueText = doc.createTextNode(attribstr);
		eleName.appendChild(valueText);
	}
	private void setdicomAttr(Element subjectElement, Subject subject, Document doc){
		
		Element SubjectName = doc.createElement("Subject_Name");
		Element StudyInstanceUID = doc.createElement("StudyInstanceUID");
		Element SeriesInstanceUID = doc.createElement("SeriesInstanceUID");
		Element SubjectID = doc.createElement("Subject_ID");
		Element SubjectGender = doc.createElement("Subject_Gender");
		Element SubjectAge = doc.createElement("Subject_Age");
		Element Modality = doc.createElement("Modality");
		Element StudyID = doc.createElement("Study_ID");
		Element SeriesNumber = doc.createElement("Series_Number");
		Element StudyDate = doc.createElement("StudyDate");
		Element SeriesDate = doc.createElement("SeriesDate");
		Element StudyTime = doc.createElement("StudyTime");
		Element SeriesTime = doc.createElement("SeriesTime");
		Element SliceThickness = doc.createElement("SliceThickness");
		Element StudyDescription = doc.createElement("StudyDescription");
		
		
		// patient's name
		setTextNode(subject.getSubject_Name(), SubjectName, doc);
		
		// Study Instance UID
		setTextNode(subject.getStudyInstanceUID(), StudyInstanceUID, doc);
		
		// Series Instance UID
		setTextNode(subject.getSeriesInstanceUID(), SeriesInstanceUID, doc);
		
		// patient id
		setTextNode(subject.getSubject_ID(), SubjectID, doc);
		
		// patient's sex
		setTextNode(subject.getSubject_Gender(), SubjectGender, doc);
		
		// patient's age
		setTextNode(subject.getSubject_Age(), SubjectAge, doc);
		
		// modality
		setTextNode(subject.getModality(), Modality, doc);
		
		// study id
		setTextNode(subject.getStudy_ID(), StudyID, doc);
		
		// series number
		setTextNode(subject.getSeries_Number(), SeriesNumber, doc);
		
		// study date
		setTextNode(subject.getStudyDate(), StudyDate, doc);
		
		// series date
		setTextNode(subject.getSeriesDate(), SeriesDate, doc);
		
		// study time
		setTextNode(subject.getStudyTime(), StudyTime, doc);
		
		// series time
		setTextNode(subject.getSeriesTime(), SeriesTime, doc);
		
		// slice thickness
		setTextNode(subject.getSliceThickness(), SliceThickness, doc);
		
		// study description
		setTextNode(subject.getStudyDescription(), StudyDescription, doc);
		
		subjectElement.appendChild(SubjectName);
		subjectElement.appendChild(StudyInstanceUID);
		subjectElement.appendChild(SeriesInstanceUID);
		subjectElement.appendChild(SubjectID);
		subjectElement.appendChild(SubjectGender);
		subjectElement.appendChild(SubjectAge);
		subjectElement.appendChild(Modality);
		subjectElement.appendChild(StudyID);
		subjectElement.appendChild(SeriesNumber);
		subjectElement.appendChild(StudyDate);
		subjectElement.appendChild(SeriesDate);
		subjectElement.appendChild(StudyTime);
		subjectElement.appendChild(SeriesTime);
		subjectElement.appendChild(SliceThickness);
		subjectElement.appendChild(StudyDescription);
		
	}
	
	private void setAnnotationNode(Element Annotations, ArrayList<Annotation> annotationList, Document doc){
		int num = annotationList.size();
		for(int i = 0; i < num; ++i){
			Annotation annotation = annotationList.get(i);
			
			Element Annotation = doc.createElement("Annotation");
			
			Element AnnoUID = doc.createElement("Annotation_UID");
			Element Annotator = doc.createElement("Annotator");
			Element AnnotationDate = doc.createElement("Annotation_Date");
			Element AnnotationTime = doc.createElement("Annotation_Time");
			Element TimePoint = doc.createElement("TimePoint");
			Element AnnotationType = doc.createElement("Annotation_Type");
			Element NoduleType = doc.createElement("Nodule_Type");
			Element NoduleDensity = doc.createElement("Nodule_Density");
			Element NoduleSet = doc.createElement("Nodule_Set");
			Element Session = doc.createElement("Session");
			Element ReferenceFile = doc.createElement("Reference_File");
			Element DcmSegmentationFile = doc.createElement("DicomSeg_File");
			
			Text annuidText = doc.createTextNode(annotation.getAnnotation_UID());
			Text usernameText = doc.createTextNode(annotation.getAnnotator());
			Text timeText = doc.createTextNode(annotation.getTimePoint());
			Text anntypeText = doc.createTextNode(annotation.getAnnotation_Type());
			Text refFileText = doc.createTextNode(annotation.getReference_File());
			Text segFileText = doc.createTextNode(annotation.getReference_Seg());
			Text annotationDateText = doc.createTextNode(annotation.getAnnotation_Date());
			Text annotationTimeText = doc.createTextNode(annotation.getAnnotation_Time());
			Text NoduleTypeText = doc.createTextNode(annotation.getNodule_Type());
			Text NoduleDensityText = doc.createTextNode(annotation.getNodule_Density());
			Text NoduleSetText = doc.createTextNode(annotation.getNodule_Set());
			Text SessionText = doc.createTextNode(annotation.getSession());
			
			AnnoUID.appendChild(annuidText);
			Annotator.appendChild(usernameText);
			TimePoint.appendChild(timeText);
			AnnotationType.appendChild(anntypeText);
			ReferenceFile.appendChild(refFileText);
			DcmSegmentationFile.appendChild(segFileText);
			AnnotationDate.appendChild(annotationDateText);
			AnnotationTime.appendChild(annotationTimeText);
			NoduleType.appendChild(NoduleTypeText);
			NoduleDensity.appendChild(NoduleDensityText);
			NoduleSet.appendChild(NoduleSetText);
			Session.appendChild(SessionText);
			
			Annotation.appendChild(AnnoUID);
			Annotation.appendChild(Annotator);
			Annotation.appendChild(AnnotationDate);
			Annotation.appendChild(AnnotationTime);
			Annotation.appendChild(TimePoint);
			Annotation.appendChild(AnnotationType);
			Annotation.appendChild(NoduleType);
			Annotation.appendChild(NoduleDensity);
			Annotation.appendChild(NoduleSet);
			Annotation.appendChild(Session);
			Annotation.appendChild(ReferenceFile);
			Annotation.appendChild(DcmSegmentationFile);
			
			Annotations.appendChild(Annotation);
		}
	}
	private Element getSubjectNode(Subject subject, Document doc){
		Element subjectElement = doc.createElement("Subject");
		
		setdicomAttr(subjectElement, subject, doc);
		
		Element Annotations = doc.createElement("Annotations");
		
		ArrayList<Annotation> annotationList = subject.getAnnotations();
		
		setAnnotationNode(Annotations, annotationList, doc);
		
		subjectElement.appendChild(Annotations);
		
		return subjectElement;
	}
	public void outputXml(ArrayList<Subject> Subjects, File OutFile){
		if((Subjects == null) || (OutFile == null)){
			return;
		}
		
		Document doc = builder.newDocument();
		Element AVTElement = doc.createElement("AVT");
		Element versionElement = doc.createElement("version");
		Element commentElement = doc.createElement("comment");
		Element experimentElement = doc.createElement("Experiment");
		Element subjectsElement = doc.createElement("Subjects");
		
		Text verText = doc.createTextNode("v0.2");
		Text comText = doc.createTextNode("For Internal Use Only!");
		
		AVTElement.setAttribute("Format", "MVT Experiment Information");
		versionElement.appendChild(verText);
		commentElement.appendChild(comText);
		
		setExperElement(experimentElement, doc);
		
		doc.appendChild(AVTElement);
		AVTElement.appendChild(versionElement);
		AVTElement.appendChild(commentElement);
		AVTElement.appendChild(experimentElement);
		
		int size = Subjects.size();
		for(int i = 0; i < size; ++i){
			Subject subject = Subjects.get(i);
			
			Element subjectElement = getSubjectNode(subject, doc);
			
			subjectsElement.appendChild(subjectElement);
		}
		AVTElement.appendChild(subjectsElement);
		
		try {
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.transform(new DOMSource(doc), new StreamResult(new FileOutputStream(OutFile)));
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    public void getsubjects(File ResultXmlFolder, ArrayList<Subject> Subjects){
    	if(ResultXmlFolder == null || Subjects == null){
			return;
		}
		if(!ResultXmlFolder.isDirectory()){
			return;
		}
		File[] resultXmlsFiles = ResultXmlFolder.listFiles();
		int size = resultXmlsFiles.length;
		
		try {
		   JAXBContext jaxbContext = JAXBContext.newInstance("gme.cacore_cacore._3_2.edu_northwestern_radiology");
		   Unmarshaller u = jaxbContext.createUnmarshaller();
		   
			for(int i = 0; i < size; ++i){
				File resultXmlFile = resultXmlsFiles[i];
				if(!resultXmlFile.isFile() || resultXmlFile.getName().indexOf(".dcm") != -1){
					continue;
				}
				
			   JAXBElement<?> obj = (JAXBElement<?>)u.unmarshal(resultXmlFile);			   
				
			   //get annotation UID
			   ImageAnnotationCollection imageAnnotationCollection = ((ImageAnnotationCollection)obj.getValue());
			   ImageAnnotation imageAnnotation = imageAnnotationCollection.getImageAnnotations().getImageAnnotation().get(0);
			   String annotationType = imageAnnotation.getTypeCode().get(0).getCode();
			   
			   //skip the seed aim
			   if (annotationType.compareToIgnoreCase("AVT001") == 0)
				   continue;
			   
			   //get series instance UID
			   ImageReferenceEntity imageReference = imageAnnotation.getImageReferenceEntityCollection().getImageReferenceEntity().get(0);
			   DicomImageReferenceEntity ref = (DicomImageReferenceEntity) imageReference;
			   ImageStudy study = ref.getImageStudy();	 
			   String StudyInstanceUID = study.getInstanceUid().toString();
			   
			   ImageSeries series = study.getImageSeries();
			   String SeriesInstanceUID = series.getInstanceUid().toString();
				
				boolean existsubject = false;
				Subject subject = null;
				int subjectnum = Subjects.size();
				for(int j = 0; j < subjectnum; ++j){
					Subject tmpsubject = Subjects.get(j);
					String tmpSubjectStudyUID = tmpsubject.getStudyInstanceUID();
					String tmpSubjectSeriesUID = tmpsubject.getSeriesInstanceUID();
					
					if(!"".equals(tmpSubjectStudyUID) && tmpSubjectStudyUID.equals(StudyInstanceUID) && 
							!"".equals(tmpSubjectSeriesUID) && tmpSubjectSeriesUID.equals(SeriesInstanceUID)){
						existsubject = true;
						subject = tmpsubject;
						break;
					}
				}
			   
				if(false == existsubject){
					subject = new Subject();
					
					Person pat = imageAnnotationCollection.getPerson();
					
					String Subject_Name = pat.getName().getValue();
					String Subject_ID = pat.getId().getValue();
					String Subject_Gender = pat.getSex().getValue();
					
					subject.setSubject_Name(Subject_Name);
					subject.setSubject_ID(Subject_ID);
					subject.setSubject_Gender(Subject_Gender);
					subject.setStudyInstanceUID(StudyInstanceUID);
					subject.setSeriesInstanceUID(SeriesInstanceUID);
					Subjects.add(subject);
				}
				
				Annotation annotation = new Annotation();
				
			    String AnnotationUID = imageAnnotation.getUniqueIdentifier().toString();
			    
			    User user = imageAnnotationCollection.getUser();
				String username = user.getName().getValue();
				
				String filenameString = resultXmlFile.getName();
				
				String AnnotationTypeStr = "";
				CalculationEntityCollection calEntityCollection = imageAnnotation.getCalculationEntityCollection();
				CalculationEntity calEntity = calEntityCollection.getCalculationEntity().get(0);
				if (calEntity == null){
				   AnnotationTypeStr = "VOLUME";
					
				   SegmentationEntityCollection segentityCollection = imageAnnotation.getSegmentationEntityCollection();
				   if (segentityCollection != null){ //load Dicom segmentation object
					   List<SegmentationEntity> segs = segentityCollection.getSegmentationEntity();
					   DicomSegmentationEntity dicomSegEntity = (DicomSegmentationEntity) segs.get(0);
					   String uid = dicomSegEntity.getReferencedSopInstanceUid().toString();
					
					   String segFile = getDicomSegFile(resultXmlsFiles, uid);
					   if (!segFile.isEmpty())
						   annotation.setReference_Seg(segFile);
				   }
				}
				else {
				   String str = calEntity.getTypeCode().get(0).getCode();
				   if (str.compareToIgnoreCase("Long_Axis") == 0 || str.indexOf("Long Axis") != -1)
						AnnotationTypeStr = "RECIST";
				   
				   if (str.compareToIgnoreCase("CrossProduct") == 0 || str.indexOf("Cross Product") != -1)
						AnnotationTypeStr = "WHO";
				}
				
				annotation.setAnnotation_UID(AnnotationUID);
				annotation.setAnnotation_Type(AnnotationTypeStr);
				annotation.setReference_File(filenameString);
				
				String [] buffer;
				buffer = username.split("_");
				if (buffer.length > 1){
					annotation.setAnnotator(buffer[0]);
					annotation.setTimePoint(buffer[buffer.length-1]);
				}
				else
					annotation.setAnnotator(username);
				
				ArrayList<Annotation> annotationList = subject.getAnnotations();
				annotationList.add(annotation);
			}
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}
	private String getDicomSegFile(File[] files, String uid) {
		for (int i = 0; i < files.length; i++){
			File file = files[i];
			
			if (file.getName().indexOf(".dcm") != -1){
				DicomInputStream segInput = null;
				AttributeList tags = new AttributeList();
				try {
					segInput = new DicomInputStream(file);
					tags.read(segInput);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (DicomException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				// instanceUID
				AttributeTag tag = new AttributeTag(0x08, 0x18);
				Attribute attrib = tags.get(tag);
				if (attrib != null){
					String sopUID = attrib.getDelimitedStringValuesOrEmptyString();
					
					if (sopUID.compareToIgnoreCase(uid) == 0)
						return file.getName();				
				}			
			}

		}
		return "";
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SubjectListXml aExperiment = new SubjectListXml();
		if(args.length == 0){
        	return;
        }
		String resultFolderString = "";
		String outFileString = "";
		try {
			Document doc = aExperiment.getBuilder().parse(new File(args[0]));
			Element node = (Element)aExperiment.getPath().evaluate("/Root/Parameters", doc, XPathConstants.NODE);
			resultFolderString = aExperiment.getPath().evaluate("ResultFolder", node).trim();
			outFileString = aExperiment.getPath().evaluate("OutFile", node).trim();
			
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<Subject> subjects = new ArrayList<Subject>();
		aExperiment.getsubjects(new File(resultFolderString),subjects);
		if("".equals(outFileString)){
			outFileString = "E:\\work\\ResultFile.xml";
		}
		aExperiment.outputXml(subjects, new File(outFileString));
	}
	
	class Annotation{
		private String Annotation_UID = "";
		private String Annotator = "";
		private String Annotation_Date = "";
		private String Annotation_Time = "";
		private String TimePoint = "";
		private String Annotation_Type = "";
		private String Nodule_Type = "";
		private String Nodule_Density = "";
		private String Nodule_Set = "";
		private String Session = "";
		private String Reference_File = "";
		private String Reference_Seg = "";
		public String getAnnotation_Time() {
			return Annotation_Time;
		}
		public void setAnnotation_Time(String annotationTime) {
			Annotation_Time = annotationTime;
		}
		public String getAnnotation_UID() {
			return Annotation_UID;
		}
		public void setAnnotation_UID(String annotationUID) {
			Annotation_UID = annotationUID;
		}
		public String getAnnotator() {
			return Annotator;
		}
		public String getReference_Seg() {
			return Reference_Seg;
		}
		public void setReference_Seg(String reference_Seg) {
			Reference_Seg = reference_Seg;
		}
		public void setAnnotator(String annotator) {
			Annotator = annotator;
		}
		public String getAnnotation_Date() {
			return Annotation_Date;
		}
		public void setAnnotation_Date(String annotationDate) {
			Annotation_Date = annotationDate;
		}
		public String getTimePoint() {
			return TimePoint;
		}
		public void setTimePoint(String timePoint) {
			TimePoint = timePoint;
		}
		public String getAnnotation_Type() {
			return Annotation_Type;
		}
		public void setAnnotation_Type(String annotationType) {
			Annotation_Type = annotationType;
		}
		public String getNodule_Type() {
			return Nodule_Type;
		}
		public void setNodule_Type(String noduleType) {
			Nodule_Type = noduleType;
		}
		public String getNodule_Density() {
			return Nodule_Density;
		}
		public void setNodule_Density(String noduleDensity) {
			Nodule_Density = noduleDensity;
		}
		public String getNodule_Set() {
			return Nodule_Set;
		}
		public void setNodule_Set(String noduleSet) {
			Nodule_Set = noduleSet;
		}
		public String getSession() {
			return Session;
		}
		public void setSession(String session) {
			Session = session;
		}
		public String getReference_File() {
			return Reference_File;
		}
		public void setReference_File(String referenceFile) {
			Reference_File = referenceFile;
		}
		
	}
    
	public class Subject{
		private String Subject_Name = "";
		private String StudyInstanceUID = "";
		private String SeriesInstanceUID = "";
		private String Subject_ID = "";
		private String Subject_Gender = "";
		private String Subject_Age = "";
		private String Modality = "";
		private String Study_ID = "";
		private String Series_Number = "";
		private String StudyDate = "";
		private String SeriesDate = "";
		private String StudyTime = "";
		private String SeriesTime = "";
		private String SliceThickness = "";
		private String StudyDescription = "";
		private ArrayList<Annotation> annotations = new ArrayList<Annotation>();
		public String getSubject_Name() {
			return Subject_Name;
		}
		public void setSubject_Name(String subjectName) {
			Subject_Name = subjectName;
		}
		public String getStudyInstanceUID() {
			return StudyInstanceUID;
		}
		public void setStudyInstanceUID(String studyInstanceUID) {
			StudyInstanceUID = studyInstanceUID;
		}
		public String getSeriesInstanceUID() {
			return SeriesInstanceUID;
		}
		public void setSeriesInstanceUID(String seriesInstanceUID) {
			SeriesInstanceUID = seriesInstanceUID;
		}
		public String getSubject_ID() {
			return Subject_ID;
		}
		public void setSubject_ID(String subjectID) {
			Subject_ID = subjectID;
		}
		public String getSubject_Gender() {
			return Subject_Gender;
		}
		public void setSubject_Gender(String subjectGender) {
			Subject_Gender = subjectGender;
		}
		public String getSubject_Age() {
			return Subject_Age;
		}
		public void setSubject_Age(String subjectAge) {
			Subject_Age = subjectAge;
		}
		public String getModality() {
			return Modality;
		}
		public void setModality(String modality) {
			Modality = modality;
		}
		public String getStudy_ID() {
			return Study_ID;
		}
		public void setStudy_ID(String studyID) {
			Study_ID = studyID;
		}
		public String getSeries_Number() {
			return Series_Number;
		}
		public void setSeries_Number(String seriesNumber) {
			Series_Number = seriesNumber;
		}
		public String getStudyDate() {
			return StudyDate;
		}
		public void setStudyDate(String studyDate) {
			StudyDate = studyDate;
		}
		public String getSeriesDate() {
			return SeriesDate;
		}
		public void setSeriesDate(String seriesDate) {
			SeriesDate = seriesDate;
		}
		public String getStudyTime() {
			return StudyTime;
		}
		public void setStudyTime(String studyTime) {
			StudyTime = studyTime;
		}
		public String getSeriesTime() {
			return SeriesTime;
		}
		public void setSeriesTime(String seriesTime) {
			SeriesTime = seriesTime;
		}
		public String getSliceThickness() {
			return SliceThickness;
		}
		public void setSliceThickness(String sliceThickness) {
			SliceThickness = sliceThickness;
		}
		public String getStudyDescription() {
			return StudyDescription;
		}
		public void setStudyDescription(String studyDescription) {
			StudyDescription = studyDescription;
		}
		public ArrayList<Annotation> getAnnotations() {
			return annotations;
		}
		public void setAnnotations(ArrayList<Annotation> annotations) {
			this.annotations = annotations;
		}
	}
}
