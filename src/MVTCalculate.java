

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dcm4che2.data.DicomElement;
import org.dcm4che2.data.DicomObject;
import org.dcm4che2.data.Tag;

import com.pixelmed.dicom.Attribute;
import com.pixelmed.dicom.AttributeList;
import com.pixelmed.dicom.AttributeTag;
import com.pixelmed.dicom.DicomException;
import com.pixelmed.dicom.DicomInputStream;
import com.siemens.cmiv.avt.mvt.ad.ADFactory;
import com.siemens.cmiv.avt.mvt.core.MVTCalculateEvent;
import com.siemens.cmiv.avt.mvt.core.MVTListener;
import com.siemens.cmiv.avt.mvt.datatype.AimResult;
import com.siemens.cmiv.avt.mvt.datatype.AnnotationEntry;
import com.siemens.cmiv.avt.mvt.datatype.ComputationListEntry;
import com.siemens.cmiv.avt.mvt.datatype.RetrieveResult;
import com.siemens.cmiv.avt.mvt.datatype.SubjectListEntry;
import com.siemens.cmiv.avt.qiba.QIBAImageAnnotation;
import com.siemens.scr.avt.ad.api.ADFacade;
import com.siemens.scr.avt.ad.dicom.GeneralImage;
import com.siemens.scr.avt.ad.dicom.GeneralSeries;

/**
 * @author Jie Zheng
 *
 */

public class MVTCalculate implements Runnable {

	List<RetrieveResult> aims;
	List<SubjectListEntry> list;
	MVTStatisticResultPanel calculator;
	List<String> reader_labels;
	
	String type = "SOV";
	String nominalGT = null;
	String rootPath = "C:/temp/MVT";
//	String rootPath = "aim";
	
	public void MVTCalculate_unused(List<RetrieveResult> aims, MVTStatisticResultPanel calculator){
		this.aims = aims;
		this.calculator = calculator;
	}
	
	public MVTCalculate(List<SubjectListEntry> list, List<String> reader_labels, MVTStatisticResultPanel calculator){
		this.list = list;
		this.calculator = calculator;
		this.reader_labels = reader_labels;
	}
	
	public void setCalculationType(String type){
		this.type = type;
	}
	
	public void setCalculationSubjects(String nominalGT){
		this.nominalGT = nominalGT;
	}

	public void run_unused() {
		for (int i = 0; i < aims.size(); i++){
			RetrieveResult aimResult = aims.get(i);
			
			List<AimResult> aim = aimResult.getAimFiles();
		
			ComputationListEntry entry =  new ComputationListEntry();
			
			//only get the first two aim objects, and take the 1st as GT, 2nd as annotation
			if (validateAims(aim)){
				
				AimResult item = aim.get(0);
			
				entry.setSubjectName(item.getPatientName());
				entry.setSubjectID(item.getPatientID());
				entry.setSubjectUID(item.getSeriesUID());
				
				String strNominalGT = getSpecifiedRoleinTrail(aim, true);
				String strAnnotation = getSpecifiedRoleinTrail(aim, false);
				
				calculator.getIvCanvas().set("dcm_segment.inputDcmSeg", strAnnotation);
				calculator.getIvCanvas().set("dcm_segment.refDcmSeg", strNominalGT);
				calculator.getIvCanvas().set("dcm_segment.process", "");
				
				calculator.getIvCanvas().set("mvt_surface_cal.process", "");
				calculator.getIvCanvas().set("mvt_volume_cal.process", "");
				
				String value = "";
				value = calculator.getIvCanvas().get("volume_seg.string");
				if (!value.isEmpty())
				{
					String buf = value.replace('"', ' ');
					value = buf.trim();
				}
				entry.setSegVolume(value);
				
				value = calculator.getIvCanvas().get("volume_ref.string");
				if (!value.isEmpty())
				{
					String buf = value.replace('"', ' ');
					value = buf.trim();
				}
				entry.setRefVolume(value);

				value = calculator.getIvCanvas().get("avgDistance.string");
				if (!value.isEmpty())
				{
					String buf = value.replace('"', ' ');
					value = buf.trim();
				}
				entry.setAverageSurfaceDistance(value);
				
				value = "";
				value = calculator.getIvCanvas().get("avgSqrDistance.string");
				if (!value.isEmpty())
				{
					String buf = value.replace('"', ' ');
					value = buf.trim();
				}
				entry.setAverageRMSSurfaceDistance(value);
	
				value = "";
				value = calculator.getIvCanvas().get("maxDistance.string");
				if (!value.isEmpty())
				{
					String buf = value.replace('"', ' ');
					value = buf.trim();
				}
				entry.setMaximumSurfaceDistance(value);
	
				value = "";
				value = calculator.getIvCanvas().get("volume_dif.string");
				if (!value.isEmpty())
				{
					String buf = value.replace('"', ' ');
					value = buf.trim();
				}
				entry.setVolumeDifference(value);
				
				value = "";
				value = calculator.getIvCanvas().get("volume_dif_perc_abs.string");
				if (!value.isEmpty())
				{
					String buf = value.replace('"', ' ');
					value = buf.trim();
				}
				entry.setRelativeVolumeDifference(value);
				
				value = "";
				value = calculator.getIvCanvas().get("tanimoto_error.string");
				if (!value.isEmpty()) {
					String buf = value.replace('"', ' ');
					value = buf.trim();
					
					float vol_overlap = (float) (100.0 - Float.valueOf(value).floatValue());
					value = Float.toString(vol_overlap);
					entry.setVolumeOverlap(value);
				}
			}
			
			if (i == aims.size()-1){
				entry.setLastOne(true);
			}
			
			fireResultsAvailable(entry);
		}
	}
	
	public void run() {
		for (int i = 0; i < list.size(); i++){
			SubjectListEntry aimResult = list.get(i);
			
			List<AnnotationEntry> aims = aimResult.getAnnotations();
			
			String subjectName = aimResult.getSubjectName();
			String seriesUID = aimResult.getSeriesInstanceUIDCurr();
		
			List<String> timepoints = getTimePoints(aims);
			List<String> readers = getReaders(aims);
			
			if (type.compareTo("SOV") == 0){
				for (int p = 0; p < readers.size(); p++){
					String reader = readers.get(p);
					for (int q = 0; q < timepoints.size(); q++){
					String timepoint = timepoints.get(q);
					
					String label = String.format("%s-%s", reader, timepoint);
					if (timepoint.isEmpty())
						label = reader;
					
					if (!reader_labels.contains(label) || label.compareToIgnoreCase(nominalGT) == 0)
						continue;
						
					AimPair recist = generateSubjects("RECIST", aims, nominalGT, label);
					AimPair who = generateSubjects("WHO", aims, nominalGT, label);
					AimPair volume = generateSubjects("VOLUME", aims, nominalGT, label);
					if (recist != null && who != null && volume != null)
						calculateSubjects(subjectName, seriesUID, recist, who, volume, String.format("%s-%s", nominalGT, label), type);
					}
				}
			}
			
			if (type.compareTo("MultipleReader") == 0){
				//intra-reader
				for (int p = 0; p < readers.size(); p++){
					String reader = readers.get(p);
					
					for (int r = 0; r < timepoints.size(); r++){
						String timepoint = timepoints.get(r);
						String label0 = String.format("%s-%s", reader, timepoint);
						if (timepoint.isEmpty())
							label0 = reader;
						
						for (int q = r; q < timepoints.size(); q++){
							String _timepoint = timepoints.get(q);
							String label1 = String.format("%s-%s", reader, _timepoint);
							if (_timepoint.isEmpty())
								label1 = reader;
							
							if (!reader_labels.contains(label1) || !reader_labels.contains(label0) || label1.compareToIgnoreCase(label0) == 0)
								continue;
							
							AimPair recist = generateSubjects("RECIST", aims, label0, label1);
							AimPair who = generateSubjects("WHO", aims, label0, label1);
							AimPair volume = generateSubjects("VOLUME", aims, label0, label1);
							calculateSubjects(subjectName, seriesUID, recist, who, volume, String.format("%s-%s", label0, label1), "intra-reader");
						}
						
					}
				}
			
				//inter-reader
				for (int p = 0; p < timepoints.size(); p++){
					String timepoint = timepoints.get(p);
				
					for (int r = 0; r < readers.size(); r++){
						String reader = readers.get(r);
						String label0 = String.format("%s-%s", reader, timepoint);
						if (timepoint.isEmpty())
							label0 = reader;
						
						for (int q = r; q < readers.size(); q++){
							String _reader = readers.get(q);
							String label1 = String.format("%s-%s", _reader, timepoint);
							if (timepoint.isEmpty())
								label1 = _reader;
							
							if (!reader_labels.contains(label1) || !reader_labels.contains(label0) || label1.compareToIgnoreCase(label0) == 0)
								continue;
							
							AimPair recist = generateSubjects("RECIST", aims, label0, label1);
							AimPair who = generateSubjects("WHO", aims, label0, label1);
							AimPair volume = generateSubjects("VOLUME", aims, label0, label1);
							calculateSubjects(subjectName, seriesUID, recist, who, volume, String.format("%s-%s", label0, label1), "inter-reader");
						}						
					}
				}
			}
		}
		ComputationListEntry entry = new ComputationListEntry();
		entry.setLastOne(true);
		fireResultsAvailable(entry);
	}
	
	private List<String> getTimePoints(List<AnnotationEntry> aimlist) {
		List<String> items = new ArrayList<String>();
		
		for (int i = 0; i < aimlist.size() - 1; i++){
			AnnotationEntry entry = aimlist.get(i);
			String timeStamp = entry.getAnnotationStamp();
			if (!items.contains(timeStamp))
				items.add(timeStamp);
		}

		return items;
	}
	
	private List<String> getReaders(List<AnnotationEntry> aimlist) {
		List<String> items = new ArrayList<String>();
		
		for (int i = 0; i < aimlist.size() - 1; i++){
			AnnotationEntry entry = aimlist.get(i);
			String reader = entry.getAnnotationReader();
			if (!items.contains(reader))
				items.add(reader);
		}

		return items;
	}

	private AimPair generateSubjects(String annotationType, List<AnnotationEntry> aimlist, String labelGT, String labelCandidate) {
		if (labelGT.compareToIgnoreCase(labelCandidate) == 0)
			return null;
		
		AimPair item = new AimPair();
		for (int i = 0; i < aimlist.size(); i++){
			AnnotationEntry entry = aimlist.get(i);
			String aim = entry.getDcmSegFile();
			if (aim.isEmpty())
				aim = entry.getAnnotationFile();
			String _type = entry.getAnnotationType();
			if (_type.compareToIgnoreCase(annotationType) == 0)
			{
				String str = String.format("%s-%s", entry.getAnnotationReader(), entry.getAnnotationStamp());
				if (entry.getAnnotationStamp().isEmpty())
					str = entry.getAnnotationReader();
				if (str.compareTo(labelGT) == 0)
					item.setFirstItem(String.format("%s/%s", rootPath, aim));
				if (str.compareTo(labelCandidate) == 0)
					item.setSecondItem(String.format("%s/%s", rootPath, aim));
			}
		}
		
		return item;
	}

	private boolean calculateSubjects(String subjectName, String seriesUID, AimPair recist, AimPair who, AimPair volume, String label, String computeType){		
		ComputationListEntry entry =  new ComputationListEntry();
		entry.setSubjectName(subjectName);
		entry.setSubjectUID(seriesUID);
		
//		if (validateAnnotation(seriesUID, recist.getFirstItem()) && validateAnnotation(seriesUID, recist.getSecondItem())){
			calculateRECIST(entry, recist.getFirstItem(), recist.getSecondItem());
			
			entry.setRefRECISTAim(recist.getFirstItem());
			entry.setSegRECISTAim(recist.getSecondItem());
//		}
		
//		if (validateAnnotation(seriesUID, who.getFirstItem()) && validateAnnotation(seriesUID, who.getSecondItem())){
			calculateWHO(entry, who.getFirstItem(), who.getSecondItem());
			
			entry.setRefWHOAim(who.getFirstItem());
			entry.setSegWHOAim(who.getSecondItem());
//		}
		
		resetVolumeScenegraph();
		
//		if (validateAnnotation(seriesUID, volume.getFirstItem()) && validateAnnotation(seriesUID, volume.getSecondItem())){
			calculateVolume(entry, volume.getFirstItem(), volume.getSecondItem());
			
			entry.setRefVolumeAim(volume.getFirstItem());
			entry.setSegVolumeAim(volume.getSecondItem());
//		}
		
		entry.setType(computeType);
		entry.setLabel(label);
		
		fireResultsAvailable(entry);
		
		return true;
	}
	
	private boolean validateAnnotation(String seriesUID, String firstItem) {
		QIBAImageAnnotation qibaAnnotation = new QIBAImageAnnotation();
		
		File aimFile = new File(firstItem);
		String refUID = qibaAnnotation.getReferredSeriesUIDFromXml(aimFile);
		if (refUID.compareToIgnoreCase(seriesUID) == 0)
			return true;
		
		return false;
	}

	private void calculateRECIST(ComputationListEntry entry, String strNominalGT, String strAnnotation){
		float recistValue0 = getRECIST(strNominalGT);
		entry.setReferenceRECIST(Float.toString(recistValue0));
		
		float recistValue1 = getRECIST(strAnnotation);
		entry.setSegmentationRECIST(Float.toString(recistValue1));
		
		entry.setRECISTDifference(Float.toString(recistValue1-recistValue0));
		
		entry.setRefRECISTAim(strNominalGT);
		entry.setSegRECISTAim(strAnnotation);
	}
	
	private void calculateWHO(ComputationListEntry entry, String strNominalGT, String strAnnotation){
		float whoValue0 = getWHO(strNominalGT);
		entry.setReferenceWHO(Float.toString(whoValue0));
		
		float whoValue1 = getWHO(strAnnotation);
		entry.setSegmentationWHO(Float.toString(whoValue1));
		
		entry.setWHODifference(Float.toString(whoValue1-whoValue0));
		
		entry.setRefWHOAim(strNominalGT);
		entry.setSegWHOAim(strAnnotation);
	}

	private void calculateVolume_unused(ComputationListEntry entry, String strNominalGT, String strAnnotation){
		System.out.println("Calculate: " + strNominalGT + " and " + strAnnotation);
	
		QIBAImageAnnotation qibaAnnotation = new QIBAImageAnnotation();
		
		StringBuffer ref_point = new StringBuffer(); 
		StringBuffer ref_pointIndex = new StringBuffer(); 
		List<String> ref_ImageUIDs = new ArrayList<String>();	
		File refFile = new File(strNominalGT);
		qibaAnnotation.getReferredImagesUIDFromXml(refFile, ref_ImageUIDs);
		if (ref_ImageUIDs.size() <= 0)
			return;
		
		qibaAnnotation.OutPutPointsToStrings(refFile, ref_point, ref_pointIndex);		
//		writeString2File(ref_point, "c:/temp/ref_point.txt");
//		writeString2File(ref_pointIndex, "c:/temp/ref_pointIndex.txt");

		StringBuffer seg_point = new StringBuffer(); 
		StringBuffer seg_pointIndex = new StringBuffer(); 
		List<String> seg_ImageUIDs = new ArrayList<String>();		
		File segFile = new File(strAnnotation);
		qibaAnnotation.getReferredImagesUIDFromXml(segFile, seg_ImageUIDs);
		if (seg_ImageUIDs.size() <= 0)
			return;
		
		qibaAnnotation.OutPutPointsToStrings(segFile, seg_point, seg_pointIndex);		
//		writeString2File(seg_point, "c:/temp/seg_point.txt");
//		writeString2File(seg_pointIndex, "c:/temp/seg_pointIndex.txt");

		Map<String, String> ref_ImageAttributes = new HashMap<String, String>();
		Map<String, String> seg_ImageAttributes = new HashMap<String, String>();
//		if (!getImageAttributes(ref_ImageUIDs.get(0), ref_ImageAttributes, seg_ImageUIDs.get(0), seg_ImageAttributes))
//			return;
		if (!getImageAttributesFromAD(ref_ImageUIDs.get(0), ref_ImageAttributes, seg_ImageUIDs.get(0), seg_ImageAttributes))
			return;
		
		if (ref_ImageAttributes.size() < 2 || seg_ImageAttributes.size() < 2){
			System.out.println("can not find the referred dicom image");
			return;
		}
		
		entry.setSubjectID(ref_ImageAttributes.get("SeriesNumber"));
		
		String ref_Position = ref_ImageAttributes.get("ImagePosition");
		String ref_Direction = "[1 0 0, 0 1 0]";
		String ref_Spacing = ref_ImageAttributes.get("SliceSpacing");
		
		String seg_Position = seg_ImageAttributes.get("ImagePosition");
		String seg_Direction = "[1 0 0, 0 1 0]";
		String seg_Spacing = seg_ImageAttributes.get("SliceSpacing");
		
		//setup segmentation
		calculator.getIvCanvas().set("QIBA_Contour_0.voxelPosition", seg_Position);
		calculator.getIvCanvas().set("QIBA_Contour_0.voxelDirection", seg_Direction);
		calculator.getIvCanvas().set("QIBA_Contour_0.voxelSpacing", seg_Spacing);
		calculator.getIvCanvas().set("QIBA_Contour_0.depth", Integer.toString(seg_ImageUIDs.size()));
		
//		calculator.getIvCanvas().set("Contour_0.pointFile","c:/temp/seg_point.txt");
//		calculator.getIvCanvas().set("Contour_0.indexFile", "c:/temp/seg_pointIndex.txt");
//		calculator.getIvCanvas().set("Contour_0.execute", "");
		
		calculator.getIvCanvas().set("QIBA_Contour_0.point", seg_point.toString());
		calculator.getIvCanvas().set("QIBA_Contour_0.coordIndex", seg_pointIndex.toString());
		
//		calculator.getIvCanvas().set("QIBA_Contour_0.voxelPosition", "-196 -70 -1428.5");
//		calculator.getIvCanvas().set("QIBA_Contour_0.voxelDirection", "[ 1 0 0, 0 1 0 ]");
//		calculator.getIvCanvas().set("QIBA_Contour_0.voxelSpacing", "0.78125 0.78125 5.0");
//		calculator.getIvCanvas().set("QIBA_Contour_0.depth", "10");
//		calculator.getIvCanvas().set("QIBA_Contour_0.point", "[401.0 244.0, 400.0 244.0, 400.0 245.0, 399.0 245.0, 399.0 244.0, 398.0 244.0, 398.0 243.0, 399.0 243.0, 399.0 242.0, 400.0 241.0, 400.0 242.0, 401.0 242.0, 401.0 243.0, 411.0 246.0, 411.0 247.0, 411.0 248.0, 410.0 248.0, 410.0 249.0, 410.0 250.0, 409.0 250.0, 408.0 250.0, 408.0 251.0, 407.0 251.0, 407.0 252.0, 406.0 252.0, 405.0 252.0, 404.0 252.0, 404.0 253.0, 403.0 253.0, 402.0 253.0, 402.0 252.0, 401.0 252.0, 401.0 251.0, 400.0 251.0, 400.0 250.0, 399.0 250.0, 399.0 249.0, 398.0 249.0, 398.0 248.0, 398.0 247.0, 397.0 247.0, 397.0 246.0, 397.0 245.0, 397.0 244.0, 397.0 243.0, 397.0 242.0, 397.0 241.0, 397.0 240.0, 398.0 240.0, 398.0 239.0, 399.0 239.0, 400.0 239.0, 400.0 238.0, 401.0 238.0, 402.0 238.0, 403.0 238.0, 404.0 238.0, 405.0 238.0, 405.0 239.0, 406.0 239.0, 407.0 239.0, 407.0 240.0, 408.0 240.0, 409.0 240.0, 409.0 241.0, 410.0 241.0, 410.0 242.0, 410.0 243.0, 411.0 243.0, 411.0 244.0, 411.0 245.0, 414.0 246.0, 414.0 247.0, 413.0 247.0, 413.0 248.0, 413.0 249.0, 413.0 250.0, 413.0 251.0, 412.0 251.0, 412.0 252.0, 411.0 252.0, 411.0 253.0, 410.0 253.0, 409.0 253.0, 408.0 253.0, 408.0 254.0, 407.0 254.0, 406.0 254.0, 405.0 254.0, 404.0 255.0, 404.0 254.0, 403.0 255.0, 403.0 254.0, 402.0 254.0, 401.0 254.0, 400.0 254.0, 400.0 253.0, 399.0 253.0, 399.0 252.0, 398.0 252.0, 398.0 251.0, 397.0 251.0, 397.0 250.0, 397.0 249.0, 396.0 249.0, 396.0 248.0, 396.0 247.0, 396.0 246.0, 396.0 245.0, 396.0 244.0, 396.0 243.0, 396.0 242.0, 396.0 241.0, 396.0 240.0, 397.0 240.0, 397.0 239.0, 397.0 238.0, 398.0 238.0, 398.0 237.0, 399.0 237.0, 400.0 237.0, 401.0 237.0, 401.0 236.0, 402.0 236.0, 403.0 236.0, 404.0 236.0, 405.0 236.0, 406.0 236.0, 407.0 236.0, 407.0 237.0, 408.0 237.0, 409.0 237.0, 410.0 237.0, 410.0 238.0, 411.0 238.0, 411.0 239.0, 412.0 239.0, 412.0 240.0, 413.0 240.0, 413.0 241.0, 413.0 242.0, 413.0 243.0, 413.0 244.0, 414.0 244.0, 414.0 245.0, 416.0 245.0, 416.0 246.0, 416.0 247.0, 416.0 248.0, 416.0 249.0, 416.0 250.0, 415.0 250.0, 415.0 251.0, 415.0 252.0, 414.0 252.0, 414.0 253.0, 413.0 253.0, 413.0 254.0, 412.0 254.0, 412.0 255.0, 411.0 255.0, 410.0 255.0, 409.0 255.0, 409.0 256.0, 408.0 256.0, 407.0 256.0, 406.0 256.0, 405.0 256.0, 404.0 256.0, 403.0 256.0, 402.0 256.0, 401.0 256.0, 400.0 256.0, 399.0 256.0, 399.0 255.0, 398.0 255.0, 398.0 254.0, 397.0 254.0, 396.0 254.0, 396.0 253.0, 396.0 252.0, 395.0 252.0, 395.0 251.0, 394.0 251.0, 394.0 250.0, 394.0 249.0, 394.0 248.0, 394.0 247.0, 393.0 247.0, 393.0 246.0, 393.0 245.0, 393.0 244.0, 393.0 243.0, 393.0 242.0, 393.0 241.0, 394.0 241.0, 394.0 240.0, 394.0 239.0, 394.0 238.0, 395.0 238.0, 395.0 237.0, 396.0 237.0, 396.0 236.0, 397.0 236.0, 397.0 235.0, 398.0 235.0, 398.0 234.0, 399.0 234.0, 400.0 234.0, 401.0 234.0, 401.0 233.0, 402.0 233.0, 403.0 233.0, 404.0 233.0, 405.0 233.0, 406.0 233.0, 407.0 233.0, 408.0 233.0, 408.0 234.0, 409.0 234.0, 410.0 234.0, 411.0 234.0, 411.0 235.0, 412.0 235.0, 412.0 236.0, 413.0 236.0, 413.0 237.0, 414.0 237.0, 414.0 238.0, 415.0 238.0, 415.0 239.0, 415.0 240.0, 416.0 240.0, 416.0 241.0, 416.0 242.0, 416.0 243.0, 416.0 244.0, 417.0 245.0, 417.0 246.0, 417.0 247.0, 417.0 248.0, 417.0 249.0, 417.0 250.0, 416.0 250.0, 416.0 251.0, 416.0 252.0, 415.0 252.0, 415.0 253.0, 414.0 253.0, 414.0 254.0, 413.0 254.0, 413.0 255.0, 412.0 255.0, 412.0 256.0, 411.0 256.0, 410.0 256.0, 410.0 257.0, 409.0 257.0, 408.0 257.0, 407.0 257.0, 406.0 257.0, 406.0 258.0, 405.0 258.0, 404.0 258.0, 403.0 258.0, 403.0 257.0, 402.0 257.0, 401.0 257.0, 400.0 257.0, 399.0 257.0, 399.0 256.0, 398.0 256.0, 397.0 256.0, 397.0 255.0, 396.0 255.0, 396.0 254.0, 395.0 254.0, 395.0 253.0, 394.0 253.0, 394.0 252.0, 394.0 251.0, 393.0 251.0, 393.0 250.0, 393.0 249.0, 393.0 248.0, 392.0 248.0, 392.0 247.0, 392.0 246.0, 392.0 245.0, 392.0 244.0, 392.0 243.0, 392.0 242.0, 392.0 241.0, 392.0 240.0, 393.0 240.0, 393.0 239.0, 393.0 238.0, 394.0 238.0, 394.0 237.0, 395.0 237.0, 395.0 236.0, 395.0 235.0, 396.0 235.0, 397.0 235.0, 397.0 234.0, 398.0 234.0, 398.0 233.0, 399.0 233.0, 400.0 233.0, 401.0 233.0, 401.0 232.0, 402.0 232.0, 403.0 232.0, 404.0 232.0, 405.0 232.0, 406.0 232.0, 407.0 232.0, 408.0 232.0, 408.0 233.0, 409.0 233.0, 410.0 233.0, 411.0 233.0, 411.0 234.0, 412.0 234.0, 412.0 235.0, 413.0 235.0, 414.0 235.0, 414.0 236.0, 415.0 236.0, 415.0 237.0, 415.0 238.0, 416.0 238.0, 416.0 239.0, 416.0 240.0, 417.0 240.0, 417.0 241.0, 417.0 242.0, 417.0 243.0, 417.0 244.0, 419.0 245.0, 419.0 246.0, 419.0 247.0, 418.0 247.0, 418.0 248.0, 418.0 249.0, 417.0 249.0, 417.0 250.0, 416.0 250.0, 416.0 251.0, 416.0 252.0, 415.0 252.0, 415.0 253.0, 415.0 254.0, 414.0 254.0, 414.0 255.0, 413.0 255.0, 412.0 255.0, 412.0 256.0, 411.0 256.0, 410.0 256.0, 410.0 257.0, 409.0 257.0, 408.0 257.0, 407.0 257.0, 407.0 258.0, 406.0 258.0, 405.0 258.0, 404.0 258.0, 403.0 258.0, 402.0 258.0, 402.0 257.0, 401.0 257.0, 400.0 257.0, 399.0 257.0, 399.0 256.0, 398.0 256.0, 397.0 256.0, 397.0 255.0, 396.0 255.0, 396.0 254.0, 395.0 254.0, 395.0 253.0, 394.0 253.0, 394.0 252.0, 394.0 251.0, 393.0 251.0, 393.0 250.0, 393.0 249.0, 392.0 249.0, 392.0 248.0, 392.0 247.0, 392.0 246.0, 392.0 245.0, 392.0 244.0, 392.0 243.0, 392.0 242.0, 392.0 241.0, 392.0 240.0, 393.0 240.0, 393.0 239.0, 393.0 238.0, 394.0 238.0, 394.0 237.0, 394.0 236.0, 395.0 236.0, 395.0 235.0, 396.0 235.0, 397.0 235.0, 397.0 234.0, 398.0 234.0, 398.0 233.0, 399.0 233.0, 400.0 233.0, 401.0 233.0, 401.0 232.0, 402.0 232.0, 403.0 232.0, 404.0 232.0, 405.0 232.0, 406.0 232.0, 407.0 232.0, 408.0 232.0, 408.0 233.0, 409.0 233.0, 410.0 233.0, 411.0 233.0, 411.0 234.0, 412.0 234.0, 412.0 235.0, 413.0 235.0, 414.0 235.0, 414.0 236.0, 415.0 236.0, 415.0 237.0, 415.0 238.0, 416.0 238.0, 416.0 239.0, 417.0 239.0, 417.0 240.0, 417.0 241.0, 418.0 241.0, 418.0 242.0, 418.0 243.0, 418.0 244.0, 419.0 244.0, 419.0 245.0, 418.0 245.0, 418.0 246.0, 418.0 247.0, 418.0 248.0, 418.0 249.0, 417.0 249.0, 417.0 250.0, 416.0 250.0, 416.0 251.0, 416.0 252.0, 415.0 252.0, 415.0 253.0, 414.0 253.0, 414.0 254.0, 413.0 254.0, 413.0 255.0, 412.0 255.0, 412.0 256.0, 411.0 256.0, 410.0 256.0, 410.0 257.0, 409.0 257.0, 408.0 257.0, 407.0 257.0, 406.0 257.0, 405.0 257.0, 404.0 257.0, 403.0 257.0, 402.0 257.0, 401.0 257.0, 400.0 257.0, 400.0 256.0, 399.0 256.0, 398.0 256.0, 398.0 255.0, 397.0 255.0, 397.0 254.0, 396.0 254.0, 396.0 253.0, 395.0 253.0, 395.0 252.0, 394.0 252.0, 394.0 251.0, 394.0 250.0, 393.0 250.0, 393.0 249.0, 393.0 248.0, 393.0 247.0, 392.0 247.0, 392.0 246.0, 392.0 245.0, 392.0 244.0, 392.0 243.0, 392.0 242.0, 392.0 241.0, 393.0 241.0, 393.0 240.0, 393.0 239.0, 394.0 239.0, 394.0 238.0, 394.0 237.0, 395.0 237.0, 395.0 236.0, 396.0 236.0, 396.0 235.0, 397.0 235.0, 398.0 235.0, 398.0 234.0, 399.0 234.0, 399.0 233.0, 400.0 233.0, 401.0 233.0, 402.0 233.0, 403.0 233.0, 404.0 233.0, 404.0 232.0, 405.0 233.0, 405.0 232.0, 406.0 233.0, 407.0 233.0, 408.0 233.0, 409.0 233.0, 410.0 233.0, 410.0 234.0, 411.0 234.0, 412.0 234.0, 412.0 235.0, 413.0 235.0, 413.0 236.0, 414.0 236.0, 414.0 237.0, 415.0 237.0, 415.0 238.0, 416.0 238.0, 416.0 239.0, 416.0 240.0, 417.0 240.0, 417.0 241.0, 418.0 241.0, 418.0 242.0, 418.0 243.0, 418.0 244.0, 416.0 245.0, 416.0 246.0, 416.0 247.0, 416.0 248.0, 416.0 249.0, 415.0 249.0, 415.0 250.0, 415.0 251.0, 415.0 252.0, 414.0 252.0, 414.0 253.0, 413.0 253.0, 413.0 254.0, 412.0 254.0, 412.0 255.0, 411.0 255.0, 410.0 255.0, 409.0 255.0, 408.0 255.0, 408.0 256.0, 407.0 256.0, 406.0 256.0, 405.0 256.0, 404.0 256.0, 403.0 256.0, 403.0 255.0, 402.0 255.0, 401.0 255.0, 400.0 255.0, 399.0 255.0, 399.0 254.0, 398.0 254.0, 398.0 253.0, 397.0 253.0, 397.0 252.0, 396.0 252.0, 396.0 251.0, 395.0 251.0, 395.0 250.0, 395.0 249.0, 394.0 249.0, 394.0 248.0, 394.0 247.0, 394.0 246.0, 393.0 246.0, 393.0 245.0, 393.0 244.0, 393.0 243.0, 394.0 243.0, 394.0 242.0, 394.0 241.0, 394.0 240.0, 395.0 240.0, 395.0 239.0, 395.0 238.0, 396.0 238.0, 396.0 237.0, 397.0 237.0, 397.0 236.0, 398.0 236.0, 399.0 236.0, 399.0 235.0, 400.0 235.0, 401.0 235.0, 401.0 234.0, 402.0 234.0, 403.0 234.0, 404.0 234.0, 405.0 234.0, 406.0 234.0, 407.0 234.0, 408.0 234.0, 409.0 234.0, 409.0 235.0, 410.0 235.0, 411.0 235.0, 411.0 236.0, 412.0 236.0, 412.0 237.0, 413.0 237.0, 413.0 238.0, 414.0 238.0, 414.0 239.0, 415.0 239.0, 415.0 240.0, 415.0 241.0, 416.0 241.0, 416.0 242.0, 416.0 243.0, 416.0 244.0, 412.0 246.0, 412.0 247.0, 412.0 248.0, 412.0 249.0, 411.0 249.0, 411.0 250.0, 411.0 251.0, 410.0 251.0, 410.0 252.0, 409.0 252.0, 409.0 253.0, 408.0 253.0, 407.0 253.0, 406.0 253.0, 405.0 253.0, 404.0 253.0, 403.0 253.0, 402.0 253.0, 402.0 252.0, 401.0 252.0, 401.0 251.0, 400.0 251.0, 399.0 251.0, 399.0 250.0, 399.0 249.0, 398.0 249.0, 398.0 248.0, 398.0 247.0, 398.0 246.0, 397.0 246.0, 397.0 245.0, 397.0 244.0, 397.0 243.0, 398.0 243.0, 398.0 242.0, 398.0 241.0, 398.0 240.0, 399.0 240.0, 399.0 239.0, 400.0 239.0, 401.0 239.0, 401.0 238.0, 402.0 238.0, 403.0 238.0, 404.0 238.0, 405.0 238.0, 406.0 238.0, 407.0 238.0, 408.0 238.0, 409.0 238.0, 410.0 238.0, 410.0 239.0, 411.0 239.0, 411.0 240.0, 412.0 240.0, 412.0 241.0, 412.0 242.0, 412.0 243.0, 412.0 244.0, 412.0 245.0, 410.0 246.0, 410.0 247.0, 409.0 247.0, 409.0 248.0, 409.0 249.0, 408.0 249.0, 408.0 250.0, 407.0 250.0, 407.0 251.0, 406.0 251.0, 405.0 251.0, 405.0 252.0, 404.0 252.0, 404.0 251.0, 403.0 251.0, 402.0 251.0, 402.0 250.0, 401.0 250.0, 401.0 249.0, 400.0 249.0, 400.0 248.0, 400.0 247.0, 399.0 247.0, 399.0 246.0, 399.0 245.0, 399.0 244.0, 399.0 243.0, 399.0 242.0, 400.0 242.0, 400.0 241.0, 401.0 241.0, 401.0 240.0, 402.0 240.0, 403.0 240.0, 403.0 239.0, 404.0 239.0, 405.0 239.0, 405.0 240.0, 406.0 240.0, 407.0 240.0, 408.0 240.0, 408.0 241.0, 409.0 241.0, 409.0 242.0, 409.0 243.0, 410.0 243.0, 410.0 244.0, 410.0 245.0]");
//		calculator.getIvCanvas().set("QIBA_Contour_0.coordIndex", "[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9]");
		calculator.getIvCanvas().set("QIBA_Contour_0.execute", "");
		calculator.getIvCanvas().set("QIBA_Mask_0.execute", "");
		calculator.getIvCanvas().set("Mask_Switch_0.index", "0");
		
		//setup reference
		calculator.getIvCanvas().set("QIBA_Contour_1.voxelPosition", ref_Position);
		calculator.getIvCanvas().set("QIBA_Contour_1.voxelDirection", ref_Direction);
		calculator.getIvCanvas().set("QIBA_Contour_1.voxelSpacing", ref_Spacing);
		calculator.getIvCanvas().set("QIBA_Contour_1.depth", Integer.toString(ref_ImageUIDs.size()));
		
//		calculator.getIvCanvas().set("Contour_1.pointFile","c:/temp/ref_point.txt");
//		calculator.getIvCanvas().set("Contour_1.indexFile", "c:/temp/ref_pointIndex.txt");
//		calculator.getIvCanvas().set("Contour_1.execute", "");

		calculator.getIvCanvas().set("QIBA_Contour_1.point", ref_point.toString());
		calculator.getIvCanvas().set("QIBA_Contour_1.coordIndex", ref_pointIndex.toString());
		
//		calculator.getIvCanvas().set("QIBA_Contour_1.voxelPosition", "-196.0 -70.0 -1421.0");
//		calculator.getIvCanvas().set("QIBA_Contour_1.voxelDirection", "[1 0 0, 0 1 0]");
//		calculator.getIvCanvas().set("QIBA_Contour_1.voxelSpacing", "0.78125 0.78125 5.0");
//		calculator.getIvCanvas().set("QIBA_Contour_1.depth", "5");
//		calculator.getIvCanvas().set("QIBA_Contour_1.point", "[413.0 246.0, 413.0 247.0, 412.0 247.0, 412.0 248.0, 412.0 249.0, 411.0 249.0, 411.0 250.0, 410.0 250.0, 410.0 251.0, 409.0 251.0, 408.0 251.0, 408.0 252.0, 407.0 252.0, 406.0 252.0, 405.0 252.0, 404.0 252.0, 403.0 252.0, 402.0 252.0, 401.0 252.0, 401.0 251.0, 400.0 251.0, 400.0 250.0, 399.0 250.0, 399.0 249.0, 398.0 249.0, 398.0 248.0, 398.0 247.0, 398.0 246.0, 397.0 246.0, 397.0 245.0, 397.0 244.0, 398.0 244.0, 398.0 243.0, 398.0 242.0, 399.0 242.0, 399.0 241.0, 399.0 240.0, 400.0 240.0, 400.0 239.0, 401.0 239.0, 402.0 239.0, 402.0 238.0, 403.0 238.0, 404.0 238.0, 405.0 238.0, 406.0 238.0, 407.0 238.0, 407.0 239.0, 408.0 239.0, 409.0 239.0, 409.0 240.0, 410.0 240.0, 411.0 240.0, 411.0 241.0, 412.0 241.0, 412.0 242.0, 412.0 243.0, 413.0 243.0, 413.0 244.0, 413.0 245.0, 416.0 245.0, 416.0 246.0, 416.0 247.0, 415.0 247.0, 415.0 248.0, 415.0 249.0, 415.0 250.0, 414.0 250.0, 414.0 251.0, 413.0 251.0, 413.0 252.0, 412.0 252.0, 412.0 253.0, 411.0 253.0, 410.0 253.0, 410.0 254.0, 409.0 254.0, 408.0 254.0, 407.0 254.0, 407.0 255.0, 406.0 255.0, 405.0 255.0, 404.0 255.0, 403.0 255.0, 403.0 254.0, 402.0 254.0, 401.0 254.0, 400.0 254.0, 400.0 253.0, 399.0 253.0, 399.0 252.0, 398.0 252.0, 397.0 252.0, 397.0 251.0, 396.0 251.0, 396.0 250.0, 396.0 249.0, 395.0 249.0, 395.0 248.0, 395.0 247.0, 395.0 246.0, 395.0 245.0, 394.0 245.0, 394.0 244.0, 395.0 244.0, 395.0 243.0, 395.0 242.0, 395.0 241.0, 396.0 241.0, 396.0 240.0, 396.0 239.0, 397.0 239.0, 397.0 238.0, 398.0 238.0, 398.0 237.0, 399.0 237.0, 399.0 236.0, 400.0 236.0, 400.0 235.0, 401.0 235.0, 402.0 235.0, 403.0 235.0, 404.0 235.0, 405.0 235.0, 406.0 235.0, 407.0 235.0, 408.0 235.0, 408.0 236.0, 409.0 236.0, 410.0 236.0, 410.0 237.0, 411.0 237.0, 411.0 238.0, 412.0 238.0, 413.0 238.0, 413.0 239.0, 414.0 239.0, 414.0 240.0, 414.0 241.0, 415.0 241.0, 415.0 242.0, 415.0 243.0, 415.0 244.0, 416.0 244.0, 416.0 245.0, 416.0 246.0, 416.0 247.0, 416.0 248.0, 416.0 249.0, 415.0 249.0, 415.0 250.0, 415.0 251.0, 414.0 251.0, 414.0 252.0, 413.0 252.0, 413.0 253.0, 412.0 253.0, 412.0 254.0, 411.0 254.0, 410.0 254.0, 410.0 255.0, 409.0 255.0, 408.0 255.0, 407.0 255.0, 407.0 256.0, 406.0 256.0, 405.0 256.0, 404.0 256.0, 404.0 255.0, 403.0 255.0, 402.0 255.0, 401.0 255.0, 401.0 254.0, 400.0 254.0, 399.0 254.0, 399.0 253.0, 398.0 253.0, 397.0 253.0, 397.0 252.0, 396.0 252.0, 396.0 251.0, 396.0 250.0, 395.0 250.0, 395.0 249.0, 395.0 248.0, 394.0 248.0, 394.0 247.0, 394.0 246.0, 394.0 245.0, 394.0 244.0, 394.0 243.0, 394.0 242.0, 394.0 241.0, 394.0 240.0, 395.0 240.0, 395.0 239.0, 396.0 239.0, 396.0 238.0, 396.0 237.0, 397.0 237.0, 398.0 237.0, 398.0 236.0, 399.0 236.0, 399.0 235.0, 400.0 235.0, 401.0 235.0, 402.0 235.0, 402.0 234.0, 403.0 234.0, 404.0 234.0, 405.0 234.0, 406.0 234.0, 407.0 234.0, 407.0 235.0, 408.0 235.0, 409.0 235.0, 410.0 235.0, 410.0 236.0, 411.0 236.0, 412.0 236.0, 412.0 237.0, 413.0 237.0, 413.0 238.0, 414.0 238.0, 414.0 239.0, 415.0 239.0, 415.0 240.0, 415.0 241.0, 416.0 241.0, 416.0 242.0, 416.0 243.0, 416.0 244.0, 416.0 246.0, 416.0 247.0, 415.0 247.0, 415.0 248.0, 415.0 249.0, 415.0 250.0, 414.0 250.0, 414.0 251.0, 413.0 251.0, 413.0 252.0, 412.0 252.0, 412.0 253.0, 411.0 253.0, 411.0 254.0, 410.0 254.0, 409.0 254.0, 408.0 254.0, 408.0 255.0, 407.0 255.0, 406.0 255.0, 405.0 255.0, 404.0 255.0, 404.0 254.0, 403.0 254.0, 402.0 254.0, 401.0 254.0, 401.0 253.0, 400.0 253.0, 400.0 252.0, 399.0 252.0, 399.0 251.0, 398.0 251.0, 398.0 250.0, 397.0 250.0, 397.0 249.0, 396.0 249.0, 396.0 248.0, 396.0 247.0, 395.0 247.0, 395.0 246.0, 395.0 245.0, 395.0 244.0, 395.0 243.0, 395.0 242.0, 395.0 241.0, 396.0 241.0, 396.0 240.0, 396.0 239.0, 397.0 239.0, 397.0 238.0, 398.0 238.0, 399.0 238.0, 399.0 237.0, 400.0 237.0, 400.0 236.0, 401.0 236.0, 402.0 236.0, 403.0 236.0, 403.0 235.0, 404.0 235.0, 405.0 235.0, 406.0 235.0, 406.0 236.0, 407.0 236.0, 408.0 236.0, 409.0 236.0, 409.0 237.0, 410.0 237.0, 410.0 238.0, 411.0 238.0, 412.0 238.0, 412.0 239.0, 413.0 239.0, 413.0 240.0, 414.0 240.0, 414.0 241.0, 414.0 242.0, 414.0 243.0, 415.0 243.0, 415.0 244.0, 415.0 245.0, 416.0 245.0, 412.0 245.0, 412.0 246.0, 412.0 247.0, 412.0 248.0, 411.0 248.0, 411.0 249.0, 410.0 249.0, 410.0 250.0, 410.0 251.0, 409.0 251.0, 409.0 252.0, 408.0 252.0, 407.0 252.0, 406.0 252.0, 405.0 252.0, 404.0 252.0, 404.0 251.0, 403.0 251.0, 402.0 251.0, 402.0 250.0, 401.0 250.0, 401.0 249.0, 400.0 249.0, 400.0 248.0, 399.0 248.0, 399.0 247.0, 399.0 246.0, 398.0 246.0, 398.0 245.0, 398.0 244.0, 398.0 243.0, 398.0 242.0, 399.0 242.0, 399.0 241.0, 399.0 240.0, 400.0 240.0, 400.0 239.0, 401.0 239.0, 402.0 239.0, 402.0 238.0, 403.0 238.0, 404.0 238.0, 405.0 238.0, 406.0 238.0, 406.0 239.0, 407.0 239.0, 408.0 239.0, 408.0 240.0, 409.0 240.0, 410.0 240.0, 410.0 241.0, 411.0 241.0, 411.0 242.0, 411.0 243.0, 412.0 243.0, 412.0 244.0]");
//		calculator.getIvCanvas().set("QIBA_Contour_1.coordIndex", "[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4]");
		calculator.getIvCanvas().set("QIBA_Contour_1.execute", "");
		calculator.getIvCanvas().set("QIBA_Mask_1.execute", "");
		calculator.getIvCanvas().set("Mask_Switch_1.index", "0");
		
		//convert the mask
		calculator.getIvCanvas().set("Mask_Align.process", "");
		calculator.getIvCanvas().set("ref_vol.trigger", "");
		calculator.getIvCanvas().set("input_vol.trigger", "");
		
		calculator.getIvCanvas().set("mvt_surface_cal.process", "");
		calculator.getIvCanvas().set("mvt_volume_cal.process", "");
		
//		calculator.getIvCanvas().set("dump_vol.update", "");
		
		String value = "";
		value = calculator.getIvCanvas().get("vol_Seg.string");
		if (!value.isEmpty())
		{
			String buf = value.replace('"', ' ');
			value = buf.trim();
		}
		entry.setSegVolume(value);
		
//		value = calculator.getIvCanvas().get("dump.string");
		value = calculator.getIvCanvas().get("vol_Ref.string");
		if (!value.isEmpty())
		{
			String buf = value.replace('"', ' ');
			value = buf.trim();
		}
		entry.setRefVolume(value);

		value = calculator.getIvCanvas().get("avgDistance.string");
		if (!value.isEmpty())
		{
			String buf = value.replace('"', ' ');
			value = buf.trim();
		}
		entry.setAverageSurfaceDistance(value);
		
		value = "";
		value = calculator.getIvCanvas().get("avgSqrDistance.string");
		if (!value.isEmpty())
		{
			String buf = value.replace('"', ' ');
			value = buf.trim();
		}
		entry.setAverageRMSSurfaceDistance(value);

		value = "";
		value = calculator.getIvCanvas().get("maxDistance.string");
		if (!value.isEmpty())
		{
			String buf = value.replace('"', ' ');
			value = buf.trim();
		}
		entry.setMaximumSurfaceDistance(value);

		value = "";
		value = calculator.getIvCanvas().get("vol_Diff.string");
		if (!value.isEmpty())
		{
			String buf = value.replace('"', ' ');
			value = buf.trim();
		}
		entry.setVolumeDifference(value);
		
		value = "";
		value = calculator.getIvCanvas().get("vol_DifPercAbs.string");
		if (!value.isEmpty())
		{
			String buf = value.replace('"', ' ');
			value = buf.trim();
		}
		entry.setRelativeVolumeDifference(value);
		
		value = "";
		value = calculator.getIvCanvas().get("tanimoto_Error.string");
		if (!value.isEmpty()) {
			String buf = value.replace('"', ' ');
			value = buf.trim();
		}
		entry.setVolumeOverlap(value);
		
		entry.setRefVolumeAim(strNominalGT);
		entry.setSegVolumeAim(strAnnotation);
		
//		value = calculator.getIvCanvas().get("QIBA_Mask_0.modelMatrix");
//		System.out.println(value);
	}
	
	private void calculateVolume(ComputationListEntry entry, String strNominalGT, String strAnnotation){
		System.out.println("Calculate: " + strNominalGT + " and " + strAnnotation);
		
		String seriesNumber = "";
		if (strNominalGT.indexOf(".xml") != -1)
			seriesNumber = loadAimAnnotation(strNominalGT, 1);
		else if (strNominalGT.indexOf(".dcm") != -1)
			seriesNumber = loadSegAnnotation(strNominalGT, 1);
		
		if (strAnnotation.indexOf(".xml") != -1)
			seriesNumber = loadAimAnnotation(strAnnotation, 0);
		else if (strAnnotation.indexOf(".dcm") != -1)
			seriesNumber = loadSegAnnotation(strAnnotation, 0);
		
		if (seriesNumber.isEmpty())
			return;
		
		entry.setSubjectID(seriesNumber);
		
		//convert the mask
		calculator.getIvCanvas().set("Mask_Align.process", "");
		calculator.getIvCanvas().set("ref_vol.trigger", "");
		calculator.getIvCanvas().set("input_vol.trigger", "");
		
		calculator.getIvCanvas().set("mvt_surface_cal.process", "");
		calculator.getIvCanvas().set("mvt_volume_cal.process", "");
		
//		calculator.getIvCanvas().set("dump_vol.update", "");
		
		String value = "";
		value = calculator.getIvCanvas().get("vol_Seg.string");
		if (!value.isEmpty())
		{
			String buf = value.replace('"', ' ');
			value = buf.trim();
		}
		entry.setSegVolume(value);
		
//		value = calculator.getIvCanvas().get("dump.string");
		value = calculator.getIvCanvas().get("vol_Ref.string");
		if (!value.isEmpty())
		{
			String buf = value.replace('"', ' ');
			value = buf.trim();
		}
		entry.setRefVolume(value);

		value = calculator.getIvCanvas().get("avgDistance.string");
		if (!value.isEmpty())
		{
			String buf = value.replace('"', ' ');
			value = buf.trim();
		}
		entry.setAverageSurfaceDistance(value);
		
		value = "";
		value = calculator.getIvCanvas().get("avgSqrDistance.string");
		if (!value.isEmpty())
		{
			String buf = value.replace('"', ' ');
			value = buf.trim();
		}
		entry.setAverageRMSSurfaceDistance(value);

		value = "";
		value = calculator.getIvCanvas().get("maxDistance.string");
		if (!value.isEmpty())
		{
			String buf = value.replace('"', ' ');
			value = buf.trim();
		}
		entry.setMaximumSurfaceDistance(value);

		value = "";
		value = calculator.getIvCanvas().get("vol_Diff.string");
		if (!value.isEmpty())
		{
			String buf = value.replace('"', ' ');
			value = buf.trim();
		}
		entry.setVolumeDifference(value);
		
		value = "";
		value = calculator.getIvCanvas().get("vol_DifPercAbs.string");
		if (!value.isEmpty())
		{
			String buf = value.replace('"', ' ');
			value = buf.trim();
		}
		entry.setRelativeVolumeDifference(value);
		
		value = "";
		value = calculator.getIvCanvas().get("tanimoto_Error.string");
		if (!value.isEmpty()) {
			String buf = value.replace('"', ' ');
			value = buf.trim();
		}
		entry.setVolumeOverlap(value);
		
		entry.setRefVolumeAim(strNominalGT);
		entry.setSegVolumeAim(strAnnotation);
		
//		value = calculator.getIvCanvas().get("QIBA_Mask_0.modelMatrix");
//		System.out.println(value);
	}

	private String loadSegAnnotation(String strNominalGT, int id) {
		//setup segmentation
		calculator.getIvCanvas().set(String.format("DICOM_Seg_%d.inputDcmSeg", id), strNominalGT);
		calculator.getIvCanvas().set(String.format("DICOM_Seg_%d.process", id), "");
		calculator.getIvCanvas().set(String.format("Mask_Switch_%d.index", id), "1");
		
		DicomInputStream segInput = null;
		AttributeList tags = new AttributeList();
		try {
			segInput = new DicomInputStream(new File(strNominalGT));
			tags.read(segInput);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DicomException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// referred SeriesInstanceUID
		AttributeTag tag = new AttributeTag(0x20, 0x52);
		Attribute attrib = tags.get(tag);
		String uid = "";
		if (attrib != null)	{
			uid = attrib.getDelimitedStringValuesOrEmptyString();
			
			ADFacade facade = ADFactory.getADServiceInstance();
			if (facade == null){
				System.out.println("fail to create ADFacade");
				return "";
			}
			
			HashMap<Integer, Object> dicomCriteria = new HashMap<Integer, Object>();
			dicomCriteria.put(Tag.SeriesInstanceUID, uid);
			List<GeneralImage> images = facade.findImagesByCriteria(dicomCriteria, null);
			if (images.size() <=0 )
				return "";
			
			String sopInstanceUID = images.get(0).getSOPInstanceUID();
			DicomObject segObject = facade.getDicomObjectWithoutPixel(sopInstanceUID);
			DicomElement buffer = segObject.get(Tag.SeriesNumber);
			uid = String.format("%d", buffer.getInt(true));
		}
	
		return uid;
	}

	private String loadAimAnnotation(String strNominalGT, int id) {
		QIBAImageAnnotation qibaAnnotation = new QIBAImageAnnotation();
		
		StringBuffer ref_point = new StringBuffer(); 
		StringBuffer ref_pointIndex = new StringBuffer(); 
		List<String> ref_ImageUIDs = new ArrayList<String>();	
		File refFile = new File(strNominalGT);
		qibaAnnotation.getReferredImagesUIDFromXml(refFile, ref_ImageUIDs);
		if (ref_ImageUIDs.size() <= 0)
			return "";
		
		qibaAnnotation.OutPutPointsToStrings(refFile, ref_point, ref_pointIndex);		
//		writeString2File(ref_point, "c:/temp/ref_point.txt");
//		writeString2File(ref_pointIndex, "c:/temp/ref_pointIndex.txt");

		Map<String, String> ref_ImageAttributes = new HashMap<String, String>();
		if (!getImageAttributeFromAD(ref_ImageUIDs.get(0), ref_ImageAttributes))
			return "";
		
		if (ref_ImageAttributes.size() < 2){
			System.out.println("can not find the referred dicom image");
			return "";
		}
		
		String SeriesID = ref_ImageAttributes.get("SeriesNumber");
		
		String ref_Position = ref_ImageAttributes.get("ImagePosition");
		String ref_Direction = "[1 0 0, 0 1 0]";
		String ref_Spacing = ref_ImageAttributes.get("SliceSpacing");
		
		//setup reference
		calculator.getIvCanvas().set(String.format("QIBA_Contour_%d.voxelPosition", id), ref_Position);
		calculator.getIvCanvas().set(String.format("QIBA_Contour_%d.voxelDirection", id), ref_Direction);
		calculator.getIvCanvas().set(String.format("QIBA_Contour_%d.voxelSpacing", id), ref_Spacing);
		calculator.getIvCanvas().set(String.format("QIBA_Contour_%d.depth", id), Integer.toString(ref_ImageUIDs.size()));
		
		calculator.getIvCanvas().set(String.format("QIBA_Contour_%d.point", id), ref_point.toString());
		calculator.getIvCanvas().set(String.format("QIBA_Contour_%d.coordIndex", id), ref_pointIndex.toString());
		
		calculator.getIvCanvas().set(String.format("QIBA_Contour_%d.execute", id), "");
		calculator.getIvCanvas().set(String.format("QIBA_Mask_%d.execute", id), "");
		calculator.getIvCanvas().set(String.format("Mask_Switch_%d.index", id), "0");
		
		return SeriesID;
	
	}

	private boolean getImageAttributeFromAD(String imageUID0,	Map<String, String> ref_ImageAttributes) {
		
		ADFacade facade = ADFactory.getADServiceInstance();
		if (facade == null){
			System.out.println("fail to create ADFacade");
			return false;
		}
		
		DicomObject dcmObject0 = facade.getDicomObjectWithoutPixel(imageUID0);
		if (dcmObject0 != null){
			DicomElement buffer = dcmObject0.get(Tag.ImagePositionPatient);
			double [] data = buffer.getDoubles(true);
			if (data.length == 3)
				ref_ImageAttributes.put("ImagePosition", String.format("%f %f %f", data[0], data[1], data[2]));
			
			buffer = dcmObject0.get(Tag.PixelSpacing);
			double [] data0 = buffer.getDoubles(true);
			
			buffer = dcmObject0.get(Tag.SpacingBetweenSlices);
			double data1 = buffer.getDouble(true);
			
			if (data0.length == 2)
				ref_ImageAttributes.put("SliceSpacing", String.format("%f %f %f", data0[0], data0[1], data1));		
			
			buffer = dcmObject0.get(Tag.SeriesNumber);
			int seriesID = buffer.getInt(true);
			ref_ImageAttributes.put("SeriesNumber", String.format("%d", seriesID));		

			buffer = dcmObject0.get(Tag.SeriesInstanceUID);
			String seriesUID = buffer.getString(null, true);
			ref_ImageAttributes.put("SeriesInstanceUID", seriesUID);		
		}
	
		return true;
	}

	private boolean getImageAttributesFromAD(String imageUID0, Map<String, String> ref_ImageAttributes, String imageUID1, Map<String, String> seg_ImageAttributes){
		ADFacade facade = ADFactory.getADServiceInstance();
		if (facade == null){
			System.out.println("fail to create ADFacade");
			return false;
		}
		
		DicomObject dcmObject0 = facade.getDicomObjectWithoutPixel(imageUID0);
		if (dcmObject0 != null){
			DicomElement buffer = dcmObject0.get(Tag.ImagePositionPatient);
			double [] data = buffer.getDoubles(true);
			if (data.length == 3)
				ref_ImageAttributes.put("ImagePosition", String.format("%f %f %f", data[0], data[1], data[2]));
			
			buffer = dcmObject0.get(Tag.PixelSpacing);
			double [] data0 = buffer.getDoubles(true);
			
			buffer = dcmObject0.get(Tag.SpacingBetweenSlices);
			double data1 = buffer.getDouble(true);
			
			if (data0.length == 2)
				ref_ImageAttributes.put("SliceSpacing", String.format("%f %f %f", data0[0], data0[1], data1));		
			
			buffer = dcmObject0.get(Tag.SeriesNumber);
			int seriesID = buffer.getInt(true);
			ref_ImageAttributes.put("SeriesNumber", String.format("%d", seriesID));		
		}
		
		DicomObject dcmObject1 = facade.getDicomObjectWithoutPixel(imageUID1);
		if (dcmObject1 != null){
			DicomElement buffer = dcmObject1.get(Tag.ImagePositionPatient);
			double [] data = buffer.getDoubles(true);
			if (data.length == 3)
				seg_ImageAttributes.put("ImagePosition", String.format("%f %f %f", data[0], data[1], data[2]));
			
			buffer = dcmObject1.get(Tag.PixelSpacing);
			double [] data0 = buffer.getDoubles(true);
			
			buffer = dcmObject1.get(Tag.SpacingBetweenSlices);
			double data1 = buffer.getDouble(true);
			
			if (data0.length == 2)
				seg_ImageAttributes.put("SliceSpacing", String.format("%f %f %f", data0[0], data0[1], data1));
			
			buffer = dcmObject1.get(Tag.SeriesNumber);
			int seriesID = buffer.getInt(true);
			seg_ImageAttributes.put("SeriesNumber", String.format("%d", seriesID));		
		}
	
		return true;
	}
	
	private boolean getImageAttributes(String imageUID0, Map<String, String> attributes0, String imageUID1, Map<String, String> attributes1) {
		// TODO Auto-generated method stub
		
		String folder = "data\\3";
		File dir = new File(folder);
		String[] files = dir.list(); 
		if (files != null){
			
			boolean bFind0 = false;
			boolean bFind1 = false;
			
			for (int i = 0; i < files.length; i++){
				File filename = new File(String.format("%s\\%s", folder, files[i]));
				
				DicomInputStream segInput = null;
				AttributeList tags = new AttributeList();
				try {
					segInput = new DicomInputStream(filename);
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
					String uid = attrib.getDelimitedStringValuesOrEmptyString();
					
					tag = new AttributeTag(0x20, 0x32);
					attrib = tags.get(tag);
					String position = "";
					if (attrib != null){
						String buf = attrib.getDelimitedStringValuesOrEmptyString();
						position = buf.replace('\\', ' ');	
					}
					
					tag = new AttributeTag(0x18, 0x88);
					attrib = tags.get(tag);
					String temp = "";
					if (attrib != null){
						String buf = attrib.getDelimitedStringValuesOrEmptyString();
						temp = buf.trim();
					}
					
					tag = new AttributeTag(0x28, 0x30);
					attrib = tags.get(tag);
					String spacing = "";
					if (attrib != null){
						String buf = attrib.getDelimitedStringValuesOrEmptyString();
						spacing = String.format("%s %s", buf.replace('\\', ' '), temp);
					}
					
					if (uid.compareToIgnoreCase(imageUID0) == 0){
						attributes0.put("ImagePosition", position);
						attributes0.put("SliceSpacing", spacing);						
						bFind0 = true;
					}
					if (uid.compareToIgnoreCase(imageUID1) == 0){
						attributes1.put("ImagePosition", position);
						attributes1.put("SliceSpacing", spacing);
						bFind1 = true;
					}
					
					if (bFind0 && bFind1)
						return true;
				}				
			}
		}
		
		return false;
	}

	private float getRECIST(String strAimFile){
		QIBAImageAnnotation qibaAnnotation = new QIBAImageAnnotation();
		
		File aimFile = new File(strAimFile);
		return qibaAnnotation.getRECISTFromXml(aimFile);
	}
	
	private String getAnnotationUID(String strAimFile){
		QIBAImageAnnotation qibaAnnotation = new QIBAImageAnnotation();
		
		File aimFile = new File(strAimFile);
		return qibaAnnotation.getAnnotationUIDFromXml(aimFile);
	}
	
	private float getWHO(String strAimFile){
		QIBAImageAnnotation qibaAnnotation = new QIBAImageAnnotation();
		
		File aimFile = new File(strAimFile);
		return qibaAnnotation.getWHOFromXml(aimFile);		
	}
	
	MVTListener listener;
    public void addMVTListener(MVTListener l) {        
        listener = l;          
    }
	void fireResultsAvailable(ComputationListEntry result){
		MVTCalculateEvent event = new MVTCalculateEvent(result);         		
        listener.calculateResultsAvailable(event);
	}	

	public boolean validateAims(List<AimResult> aim){
		boolean ret = false;
		
		int nSize = 2;

		if (aim.size() >= nSize){
			
			try{
				String seriesUID0 = aim.get(0).getSeriesUID();
				String seriesUID1 = aim.get(1).getSeriesUID();

				if (seriesUID0.compareToIgnoreCase(seriesUID1) == 0){
					ret = true;
				}
			} catch (NullPointerException e){
				System.out.println("Fail to validate aim objects - nullPointer");
			}
		}
		
		return ret;
		
	}
	public String getSpecifiedRoleinTrail(List<AimResult> aim, boolean bGroundTruth){
		String strFile = "";
		for (int i = 0; i < aim.size(); i++){
			AimResult item = aim.get(i);
			
			strFile = "";
			if (bGroundTruth){
				if (item.isGroundTruth()){
					strFile = item.getDefaultSegFile();
					
					return strFile;
				}
			}
			else {
				if (!item.isGroundTruth()){
					strFile = item.getDefaultSegFile();
					
					return strFile;
				}
			}
		}
		
		return strFile;
	}
	
	class AimPair{
		private String subject0;
		private String subject1;
		
		AimPair(){
			this.subject0 = "";
			this.subject1 = "";
		}
		
		AimPair(String subject0, String subject1){
			this.subject0 = subject0;
			this.subject1 = subject1;
		}
		
		public String getFirstItem(){
			return this.subject0;
		}
		
		public String getSecondItem(){
			return this.subject1;
		}
		
		public void setPair(String subject0, String subject1){
			this.subject0 = subject0;
			this.subject1 = subject1;
		}
		
		public void setFirstItem(String subject){
			this.subject0 = subject;
		}
		
		public void setSecondItem(String subject){
			this.subject1 = subject;
		}
	}
	
    //utility function to remove the extra quotes
    public String parseOpenInventorString(String str)
    {
    	String  strBuf = str;
    	
    	int index0 = str.indexOf('"');
    	int index1 = str.lastIndexOf('"');
    	
    	if (index0 >= 0 && index1 < str.length())
    		strBuf = str.substring(index0+1, index1);    	
    	
    	return strBuf;
    }
    
    private void writeString2File(StringBuffer buffer, String file){
		try {
			FileOutputStream out = new FileOutputStream(new File(file)); 
			out.write(buffer.toString().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
   	
    }

    private void resetVolumeScenegraph(){
		calculator.getIvCanvas().set("Mask_Switch_0.index", "-1");
		calculator.getIvCanvas().set("Mask_Switch_1.index", "-1");   	
		
		calculator.getIvCanvas().set("QIBA_Contour_0.point", "0 0");
		calculator.getIvCanvas().set("QIBA_Contour_0.coordIndex", "0");
		calculator.getIvCanvas().set("QIBA_Contour_1.point", "0 0");
		calculator.getIvCanvas().set("QIBA_Contour_1.coordIndex", "0");
    }
}
