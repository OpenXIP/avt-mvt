

import gme.cacore_cacore._4_4.edu_northwestern_radiology.DicomImageReferenceEntity;
import gme.cacore_cacore._4_4.edu_northwestern_radiology.ImageAnnotation;
import gme.cacore_cacore._4_4.edu_northwestern_radiology.ImageReferenceEntity;
import gme.cacore_cacore._4_4.edu_northwestern_radiology.ImageSeries;
import gme.cacore_cacore._4_4.edu_northwestern_radiology.ImageStudy;
import gme.cacore_cacore._4_4.edu_northwestern_radiology.TwoDimensionSpatialCoordinate;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.dcm4che2.data.DicomElement;
import org.dcm4che2.data.DicomObject;
import org.dcm4che2.data.Tag;
import org.dcm4che2.io.DicomOutputStream;

import uri.iso_org._21090.II;

import com.pixelmed.dicom.Attribute;
import com.pixelmed.dicom.AttributeList;
import com.pixelmed.dicom.AttributeTag;
import com.pixelmed.dicom.DicomException;
import com.pixelmed.dicom.DicomInputStream;
import com.siemens.cmiv.avt.mvt.ad.ADFactory;
import com.siemens.cmiv.avt.mvt.ad.ADRetrieve;
import com.siemens.cmiv.avt.mvt.ad.ADRetrieveEvent;
import com.siemens.cmiv.avt.mvt.ad.ADRetrieveResult;
import com.siemens.cmiv.avt.mvt.ad.ADSearchEvent;
import com.siemens.cmiv.avt.mvt.core.MVTCalculateEvent;
import com.siemens.cmiv.avt.mvt.core.MVTListener;
import com.siemens.cmiv.avt.mvt.core.ProcessMonitoring;
import com.siemens.cmiv.avt.mvt.datatype.ComputationListEntry;
import com.siemens.cmiv.avt.mvt.datatype.MeasurementEntry;
import com.siemens.cmiv.avt.mvt.datatype.RetrieveResult;
import com.siemens.cmiv.avt.mvt.datatype.SubjectListEntry;
import com.siemens.cmiv.avt.mvt.statistic.StatisticEvent;
import com.siemens.cmiv.avt.mvt.statistic.StatisticResult;
import com.siemens.cmiv.avt.mvt.statistic.StatisticResultListener;
import com.siemens.cmiv.avt.mvt.ui.ComputationResultsPanel;
import com.siemens.cmiv.avt.qiba.QIBAImageAnnotation;
import com.siemens.scr.avt.ad.api.ADFacade;
import com.siemens.scr.avt.ad.dicom.GeneralImage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jie Zheng
 *
 */

public class MVTStatisticResultPanel extends JPanel implements ActionListener, StatisticResultListener, MVTListener {

	private static final long serialVersionUID = 1L;
	
	final int WIDTH = 350;
	final int BOTTOM = 50;

	ComputationResultsPanel ComputePanel = null;
	ivCanvas mivCanvas;

	String		outDir;
	JProgressBar progressBar = null;
	
	private	String	studyType = "SOV";
	private String	nominalGT = "";
	
	private String tempFolder = "c:\\temp";
	private String datasetCache = "";
	
	private ProcessMonitoring rServProcess = new ProcessMonitoring();
	
	/**
	 * This is the default constructor
	 */
	public MVTStatisticResultPanel() {
		super();
		initialize();
	}
	
	public String getStudyType() {
		return studyType;
	}

	public void setStudyType(String studyType) {
		this.studyType = studyType;
		ComputePanel.updateResultTab(studyType);
	}

	public String getNominalGT() {
		return nominalGT;
	}

	public void setNominalGT(String nominalGT) {
		this.nominalGT = nominalGT;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
 		Color xipColor = new Color(51, 51, 102);
 		setLayout(null);
    	
		setBackground(xipColor);
		JPanel container = new JPanel();
		container.setLayout(null);		
	
		container.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		container.setBackground(xipColor);
		
		container.setSize(557, 541);
		container.setLayout(new BorderLayout(50, 10));

		JPanel topPanel = new JPanel();
		topPanel.setBackground(xipColor);
		container.add(topPanel, BorderLayout.NORTH);
		
		JPanel leftPanel = new JPanel();
		leftPanel.setBackground(xipColor);;
		container.add(leftPanel, BorderLayout.WEST);
		
		JPanel rightPanel = new JPanel();
		rightPanel.setBackground(xipColor);;
		container.add(rightPanel, BorderLayout.EAST);
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setBackground(xipColor);;
		container.add(bottomPanel, BorderLayout.SOUTH);
		
		ComputePanel = new ComputationResultsPanel();
		ComputePanel.setVisible(true);
		ComputePanel.addStatisticalResultListener(this);
		container.add(ComputePanel, BorderLayout.CENTER);
		
		mivCanvas = new ivCanvas();
		this.add(mivCanvas);
		this.add(container);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); 
		container.setBounds(0, 0, screenSize.width - WIDTH, screenSize.height*6/10);
		mivCanvas.setBounds(0, screenSize.height*6/10, (int)(screenSize.width - 1.15*WIDTH), (int) ((int)screenSize.height*3.5/10));
		
		mivCanvas.initialize();
		loadLibrary();	
		runMVT();
		
		startRServe();
		
		showDrillingDown(false);
	}
	
	public void loadLibrary() {
		try {
			BufferedReader br = new BufferedReader(new FileReader("./ivJava.ini"));
			String line = "";
			while ((line = br.readLine()) != null) {
				if (line.length() > 0) {
					int index = line.indexOf("LoadLibrary=");
					if (index >= 0) {
						String Library = line.substring(index + 12);
						Library = Library.replace(';', ',');
						System.out.println("Loading rad extensions : ");
						System.out.println(Library);
						if (!mivCanvas.loadLibraries(Library))
							System.out.println("Not all rad extensions could be loaded");
						break;
					}
				}
			}
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}
	
	public void runMVT(){	   
		File ivFile = new File("MVTII.iv");
	    String filePth;
	    if(ivFile.exists()) {
	    	filePth = ivFile.getAbsolutePath();
	    } else {
	    	return;
	    }	              	          
		if (null != mivCanvas && filePth.length() != 0) {
			try {
				mivCanvas.loadGraphOpenGL(filePth);
				mivCanvas.repaint();	    
			} catch (Exception e) {
				  e.printStackTrace();
			}
		}
	}
	
	public ivCanvas getIvCanvas(){
		return mivCanvas;
	}

	public void setResultsListFlag(MeasurementEntry flags) {
		// TODO Auto-generated method stub
		ComputePanel.setResultsListFlag(flags);
	}

	public void queryData(List<String> datalist) {
		// TODO Auto-generated method stub
		ComputePanel.queryData(datalist);	
	}

	public void selectItem(StatisticResult item) {
		String subjectUID = item.getSubjectUID();
		
		String strUID = "1.2.3.4.5.6.7.8.9.0";
		if (subjectUID == strUID)
			return;
		
//		String dataPath = getDataset(subjectUID, tempFolder);
//		File data = new File(dataPath);
//		if (!data.exists()) {
//			System.out.println("Can not retrieve DICOM series");
//			return;
//		}
		
		String dataset = getDataset(subjectUID);
		if (dataset.isEmpty())
			return;
		
		getIvCanvas().set("Main_Switch.whichChild", "0");
		
		if (dataset.compareTo(datasetCache) != 0){
			getIvCanvas().set("LoadDicom.name", dataset);	
			datasetCache = dataset;
		}

		String refRECISTAim = item.getAim("RefRECISTAim");
		if (refRECISTAim.length() > 0){
			Map<String, II> aimResult = getSeedInformation(refRECISTAim);
			if (aimResult.size() > 0){
				II imageUID = aimResult.get("ImageReferenceUID");
				II points = aimResult.get("SeedPoints");
				
				getIvCanvas().set("Import_Points.points", points.toString());
				getIvCanvas().set("Import_Points.sopInstanceUID", imageUID.toString());
			}
		}
		String refVolumeAim = item.getAim("RefVolumeAim");
		setVolumeOverlay(refVolumeAim, 0);		
		
		String segVolumeAim = item.getAim("SegVolumeAim");
		setVolumeOverlay(segVolumeAim, 1);		
		
		updateSceneGraph();
	}
	
	public void updateSceneGraph(){
		Graphics g = getIvCanvas().getGraphics();
		getIvCanvas().update(g);
	}
	
	public void setVolumeOverlay_unused(String aimCDRH, int id){
		QIBAImageAnnotation qibaAnnotation = new QIBAImageAnnotation();
		
		StringBuffer ref_point = new StringBuffer(); 
		StringBuffer ref_pointIndex = new StringBuffer(); 
		List<String> ref_ImageUIDs = new ArrayList<String>();	
		File refFile = new File(aimCDRH);
		qibaAnnotation.getReferredImagesUIDFromXml(refFile, ref_ImageUIDs);
		if (ref_ImageUIDs.size() <= 0)
			return;
		
		qibaAnnotation.OutPutPointsToStrings(refFile, ref_point, ref_pointIndex);		
		
		Map<String, String> ref_ImageAttributes = new HashMap<String, String>();
		if (!getImageAttributesFromAD(ref_ImageUIDs.get(0), ref_ImageAttributes))
			return;
		
//		if (!getImageAttributes(ref_ImageUIDs.get(0), ref_ImageAttributes))
//			return;
		
		if (ref_ImageAttributes.size() < 2){
			System.out.println("can not find the referred dicom image");
			return;
		}
			
		String ref_Position = ref_ImageAttributes.get("ImagePosition");
		String ref_Direction = "[1 0 0, 0 1 0]";
		String ref_Spacing = ref_ImageAttributes.get("VoxelSpacing");
		
		//setup reference
		String xipNode = String.format("QIBA_Contour_Import_%d", id);
		
		String xipField = String.format("%s.voxelPosition", xipNode);
		getIvCanvas().set(xipField, ref_Position);
//		getIvCanvas().set(xipField, "-196.0 -70.0 -1421.0");
		
		xipField = String.format("%s.voxelDirection", xipNode);
		getIvCanvas().set(xipField, ref_Direction);
//		getIvCanvas().set(xipField, "[1 0 0, 0 1 0]");
		
		xipField = String.format("%s.voxelSpacing", xipNode);
		getIvCanvas().set(xipField, ref_Spacing);
//		getIvCanvas().set(xipField, "0.78125 0.78125 5.0");
		
		xipField = String.format("%s.depth", xipNode);
		getIvCanvas().set(xipField, Integer.toString(ref_ImageUIDs.size()));
//		getIvCanvas().set(xipField, "5");
		
		xipField = String.format("%s.point", xipNode);
		getIvCanvas().set(xipField, ref_point.toString());
//		getIvCanvas().set(xipField, "[413.0 246.0, 413.0 247.0, 412.0 247.0, 412.0 248.0, 412.0 249.0, 411.0 249.0, 411.0 250.0, 410.0 250.0, 410.0 251.0, 409.0 251.0, 408.0 251.0, 408.0 252.0, 407.0 252.0, 406.0 252.0, 405.0 252.0, 404.0 252.0, 403.0 252.0, 402.0 252.0, 401.0 252.0, 401.0 251.0, 400.0 251.0, 400.0 250.0, 399.0 250.0, 399.0 249.0, 398.0 249.0, 398.0 248.0, 398.0 247.0, 398.0 246.0, 397.0 246.0, 397.0 245.0, 397.0 244.0, 398.0 244.0, 398.0 243.0, 398.0 242.0, 399.0 242.0, 399.0 241.0, 399.0 240.0, 400.0 240.0, 400.0 239.0, 401.0 239.0, 402.0 239.0, 402.0 238.0, 403.0 238.0, 404.0 238.0, 405.0 238.0, 406.0 238.0, 407.0 238.0, 407.0 239.0, 408.0 239.0, 409.0 239.0, 409.0 240.0, 410.0 240.0, 411.0 240.0, 411.0 241.0, 412.0 241.0, 412.0 242.0, 412.0 243.0, 413.0 243.0, 413.0 244.0, 413.0 245.0, 416.0 245.0, 416.0 246.0, 416.0 247.0, 415.0 247.0, 415.0 248.0, 415.0 249.0, 415.0 250.0, 414.0 250.0, 414.0 251.0, 413.0 251.0, 413.0 252.0, 412.0 252.0, 412.0 253.0, 411.0 253.0, 410.0 253.0, 410.0 254.0, 409.0 254.0, 408.0 254.0, 407.0 254.0, 407.0 255.0, 406.0 255.0, 405.0 255.0, 404.0 255.0, 403.0 255.0, 403.0 254.0, 402.0 254.0, 401.0 254.0, 400.0 254.0, 400.0 253.0, 399.0 253.0, 399.0 252.0, 398.0 252.0, 397.0 252.0, 397.0 251.0, 396.0 251.0, 396.0 250.0, 396.0 249.0, 395.0 249.0, 395.0 248.0, 395.0 247.0, 395.0 246.0, 395.0 245.0, 394.0 245.0, 394.0 244.0, 395.0 244.0, 395.0 243.0, 395.0 242.0, 395.0 241.0, 396.0 241.0, 396.0 240.0, 396.0 239.0, 397.0 239.0, 397.0 238.0, 398.0 238.0, 398.0 237.0, 399.0 237.0, 399.0 236.0, 400.0 236.0, 400.0 235.0, 401.0 235.0, 402.0 235.0, 403.0 235.0, 404.0 235.0, 405.0 235.0, 406.0 235.0, 407.0 235.0, 408.0 235.0, 408.0 236.0, 409.0 236.0, 410.0 236.0, 410.0 237.0, 411.0 237.0, 411.0 238.0, 412.0 238.0, 413.0 238.0, 413.0 239.0, 414.0 239.0, 414.0 240.0, 414.0 241.0, 415.0 241.0, 415.0 242.0, 415.0 243.0, 415.0 244.0, 416.0 244.0, 416.0 245.0, 416.0 246.0, 416.0 247.0, 416.0 248.0, 416.0 249.0, 415.0 249.0, 415.0 250.0, 415.0 251.0, 414.0 251.0, 414.0 252.0, 413.0 252.0, 413.0 253.0, 412.0 253.0, 412.0 254.0, 411.0 254.0, 410.0 254.0, 410.0 255.0, 409.0 255.0, 408.0 255.0, 407.0 255.0, 407.0 256.0, 406.0 256.0, 405.0 256.0, 404.0 256.0, 404.0 255.0, 403.0 255.0, 402.0 255.0, 401.0 255.0, 401.0 254.0, 400.0 254.0, 399.0 254.0, 399.0 253.0, 398.0 253.0, 397.0 253.0, 397.0 252.0, 396.0 252.0, 396.0 251.0, 396.0 250.0, 395.0 250.0, 395.0 249.0, 395.0 248.0, 394.0 248.0, 394.0 247.0, 394.0 246.0, 394.0 245.0, 394.0 244.0, 394.0 243.0, 394.0 242.0, 394.0 241.0, 394.0 240.0, 395.0 240.0, 395.0 239.0, 396.0 239.0, 396.0 238.0, 396.0 237.0, 397.0 237.0, 398.0 237.0, 398.0 236.0, 399.0 236.0, 399.0 235.0, 400.0 235.0, 401.0 235.0, 402.0 235.0, 402.0 234.0, 403.0 234.0, 404.0 234.0, 405.0 234.0, 406.0 234.0, 407.0 234.0, 407.0 235.0, 408.0 235.0, 409.0 235.0, 410.0 235.0, 410.0 236.0, 411.0 236.0, 412.0 236.0, 412.0 237.0, 413.0 237.0, 413.0 238.0, 414.0 238.0, 414.0 239.0, 415.0 239.0, 415.0 240.0, 415.0 241.0, 416.0 241.0, 416.0 242.0, 416.0 243.0, 416.0 244.0, 416.0 246.0, 416.0 247.0, 415.0 247.0, 415.0 248.0, 415.0 249.0, 415.0 250.0, 414.0 250.0, 414.0 251.0, 413.0 251.0, 413.0 252.0, 412.0 252.0, 412.0 253.0, 411.0 253.0, 411.0 254.0, 410.0 254.0, 409.0 254.0, 408.0 254.0, 408.0 255.0, 407.0 255.0, 406.0 255.0, 405.0 255.0, 404.0 255.0, 404.0 254.0, 403.0 254.0, 402.0 254.0, 401.0 254.0, 401.0 253.0, 400.0 253.0, 400.0 252.0, 399.0 252.0, 399.0 251.0, 398.0 251.0, 398.0 250.0, 397.0 250.0, 397.0 249.0, 396.0 249.0, 396.0 248.0, 396.0 247.0, 395.0 247.0, 395.0 246.0, 395.0 245.0, 395.0 244.0, 395.0 243.0, 395.0 242.0, 395.0 241.0, 396.0 241.0, 396.0 240.0, 396.0 239.0, 397.0 239.0, 397.0 238.0, 398.0 238.0, 399.0 238.0, 399.0 237.0, 400.0 237.0, 400.0 236.0, 401.0 236.0, 402.0 236.0, 403.0 236.0, 403.0 235.0, 404.0 235.0, 405.0 235.0, 406.0 235.0, 406.0 236.0, 407.0 236.0, 408.0 236.0, 409.0 236.0, 409.0 237.0, 410.0 237.0, 410.0 238.0, 411.0 238.0, 412.0 238.0, 412.0 239.0, 413.0 239.0, 413.0 240.0, 414.0 240.0, 414.0 241.0, 414.0 242.0, 414.0 243.0, 415.0 243.0, 415.0 244.0, 415.0 245.0, 416.0 245.0, 412.0 245.0, 412.0 246.0, 412.0 247.0, 412.0 248.0, 411.0 248.0, 411.0 249.0, 410.0 249.0, 410.0 250.0, 410.0 251.0, 409.0 251.0, 409.0 252.0, 408.0 252.0, 407.0 252.0, 406.0 252.0, 405.0 252.0, 404.0 252.0, 404.0 251.0, 403.0 251.0, 402.0 251.0, 402.0 250.0, 401.0 250.0, 401.0 249.0, 400.0 249.0, 400.0 248.0, 399.0 248.0, 399.0 247.0, 399.0 246.0, 398.0 246.0, 398.0 245.0, 398.0 244.0, 398.0 243.0, 398.0 242.0, 399.0 242.0, 399.0 241.0, 399.0 240.0, 400.0 240.0, 400.0 239.0, 401.0 239.0, 402.0 239.0, 402.0 238.0, 403.0 238.0, 404.0 238.0, 405.0 238.0, 406.0 238.0, 406.0 239.0, 407.0 239.0, 408.0 239.0, 408.0 240.0, 409.0 240.0, 410.0 240.0, 410.0 241.0, 411.0 241.0, 411.0 242.0, 411.0 243.0, 412.0 243.0, 412.0 244.0]");
		
		xipField = String.format("%s.coordIndex", xipNode);
		getIvCanvas().set(xipField, ref_pointIndex.toString());
//		getIvCanvas().set(xipField, "[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4]");
		
		xipField = String.format("%s.execute", xipNode);
		getIvCanvas().set(xipField, "");
		
		xipField = String.format("QIBA_Mask_Import_%d.execute", id);
		getIvCanvas().set(xipField, "");
		
		xipField = String.format("Mask_Switch_Import_%d.index", id);
		getIvCanvas().set(xipField, "0");		
	}
	
	public void setVolumeOverlay(String annotation, int id){
		if (annotation.indexOf(".xml") != -1)
			setAimOverlay(annotation, id);
		else
			setSegOverlay(annotation, id);		
	}

	private void setSegOverlay(String annotation, int id) {
		
		String xipNode = String.format("DICOM_Seg_Import_%d", id);
		getIvCanvas().set(String.format("%s.inputDcmSeg", xipNode), annotation);
		getIvCanvas().set(String.format("%s.process", xipNode), "");
		
		getIvCanvas().set(String.format("Mask_Switch_Import_%d.index", id), "1");		
	}

	private void setAimOverlay(String annotation, int id) {
		QIBAImageAnnotation qibaAnnotation = new QIBAImageAnnotation();
		
		StringBuffer ref_point = new StringBuffer(); 
		StringBuffer ref_pointIndex = new StringBuffer(); 
		List<String> ref_ImageUIDs = new ArrayList<String>();	
		File refFile = new File(annotation);
		qibaAnnotation.getReferredImagesUIDFromXml(refFile, ref_ImageUIDs);
		if (ref_ImageUIDs.size() <= 0)
			return;
		
		qibaAnnotation.OutPutPointsToStrings(refFile, ref_point, ref_pointIndex);		
		
		Map<String, String> ref_ImageAttributes = new HashMap<String, String>();
		if (!getImageAttributesFromAD(ref_ImageUIDs.get(0), ref_ImageAttributes))
			return;
		
//		if (!getImageAttributes(ref_ImageUIDs.get(0), ref_ImageAttributes))
//			return;
		
		if (ref_ImageAttributes.size() < 2){
			System.out.println("can not find the referred dicom image");
			return;
		}
			
		String ref_Position = ref_ImageAttributes.get("ImagePosition");
		String ref_Direction = "[1 0 0, 0 1 0]";
		String ref_Spacing = ref_ImageAttributes.get("VoxelSpacing");
		
		//setup reference
		String xipNode = String.format("QIBA_Contour_Import_%d", id);
		
		String xipField = String.format("%s.voxelPosition", xipNode);
		getIvCanvas().set(xipField, ref_Position);
//		getIvCanvas().set(xipField, "-196.0 -70.0 -1421.0");
		
		xipField = String.format("%s.voxelDirection", xipNode);
		getIvCanvas().set(xipField, ref_Direction);
//		getIvCanvas().set(xipField, "[1 0 0, 0 1 0]");
		
		xipField = String.format("%s.voxelSpacing", xipNode);
		getIvCanvas().set(xipField, ref_Spacing);
//		getIvCanvas().set(xipField, "0.78125 0.78125 5.0");
		
		xipField = String.format("%s.depth", xipNode);
		getIvCanvas().set(xipField, Integer.toString(ref_ImageUIDs.size()));
//		getIvCanvas().set(xipField, "5");
		
		xipField = String.format("%s.point", xipNode);
		getIvCanvas().set(xipField, ref_point.toString());
//		getIvCanvas().set(xipField, "[413.0 246.0, 413.0 247.0, 412.0 247.0, 412.0 248.0, 412.0 249.0, 411.0 249.0, 411.0 250.0, 410.0 250.0, 410.0 251.0, 409.0 251.0, 408.0 251.0, 408.0 252.0, 407.0 252.0, 406.0 252.0, 405.0 252.0, 404.0 252.0, 403.0 252.0, 402.0 252.0, 401.0 252.0, 401.0 251.0, 400.0 251.0, 400.0 250.0, 399.0 250.0, 399.0 249.0, 398.0 249.0, 398.0 248.0, 398.0 247.0, 398.0 246.0, 397.0 246.0, 397.0 245.0, 397.0 244.0, 398.0 244.0, 398.0 243.0, 398.0 242.0, 399.0 242.0, 399.0 241.0, 399.0 240.0, 400.0 240.0, 400.0 239.0, 401.0 239.0, 402.0 239.0, 402.0 238.0, 403.0 238.0, 404.0 238.0, 405.0 238.0, 406.0 238.0, 407.0 238.0, 407.0 239.0, 408.0 239.0, 409.0 239.0, 409.0 240.0, 410.0 240.0, 411.0 240.0, 411.0 241.0, 412.0 241.0, 412.0 242.0, 412.0 243.0, 413.0 243.0, 413.0 244.0, 413.0 245.0, 416.0 245.0, 416.0 246.0, 416.0 247.0, 415.0 247.0, 415.0 248.0, 415.0 249.0, 415.0 250.0, 414.0 250.0, 414.0 251.0, 413.0 251.0, 413.0 252.0, 412.0 252.0, 412.0 253.0, 411.0 253.0, 410.0 253.0, 410.0 254.0, 409.0 254.0, 408.0 254.0, 407.0 254.0, 407.0 255.0, 406.0 255.0, 405.0 255.0, 404.0 255.0, 403.0 255.0, 403.0 254.0, 402.0 254.0, 401.0 254.0, 400.0 254.0, 400.0 253.0, 399.0 253.0, 399.0 252.0, 398.0 252.0, 397.0 252.0, 397.0 251.0, 396.0 251.0, 396.0 250.0, 396.0 249.0, 395.0 249.0, 395.0 248.0, 395.0 247.0, 395.0 246.0, 395.0 245.0, 394.0 245.0, 394.0 244.0, 395.0 244.0, 395.0 243.0, 395.0 242.0, 395.0 241.0, 396.0 241.0, 396.0 240.0, 396.0 239.0, 397.0 239.0, 397.0 238.0, 398.0 238.0, 398.0 237.0, 399.0 237.0, 399.0 236.0, 400.0 236.0, 400.0 235.0, 401.0 235.0, 402.0 235.0, 403.0 235.0, 404.0 235.0, 405.0 235.0, 406.0 235.0, 407.0 235.0, 408.0 235.0, 408.0 236.0, 409.0 236.0, 410.0 236.0, 410.0 237.0, 411.0 237.0, 411.0 238.0, 412.0 238.0, 413.0 238.0, 413.0 239.0, 414.0 239.0, 414.0 240.0, 414.0 241.0, 415.0 241.0, 415.0 242.0, 415.0 243.0, 415.0 244.0, 416.0 244.0, 416.0 245.0, 416.0 246.0, 416.0 247.0, 416.0 248.0, 416.0 249.0, 415.0 249.0, 415.0 250.0, 415.0 251.0, 414.0 251.0, 414.0 252.0, 413.0 252.0, 413.0 253.0, 412.0 253.0, 412.0 254.0, 411.0 254.0, 410.0 254.0, 410.0 255.0, 409.0 255.0, 408.0 255.0, 407.0 255.0, 407.0 256.0, 406.0 256.0, 405.0 256.0, 404.0 256.0, 404.0 255.0, 403.0 255.0, 402.0 255.0, 401.0 255.0, 401.0 254.0, 400.0 254.0, 399.0 254.0, 399.0 253.0, 398.0 253.0, 397.0 253.0, 397.0 252.0, 396.0 252.0, 396.0 251.0, 396.0 250.0, 395.0 250.0, 395.0 249.0, 395.0 248.0, 394.0 248.0, 394.0 247.0, 394.0 246.0, 394.0 245.0, 394.0 244.0, 394.0 243.0, 394.0 242.0, 394.0 241.0, 394.0 240.0, 395.0 240.0, 395.0 239.0, 396.0 239.0, 396.0 238.0, 396.0 237.0, 397.0 237.0, 398.0 237.0, 398.0 236.0, 399.0 236.0, 399.0 235.0, 400.0 235.0, 401.0 235.0, 402.0 235.0, 402.0 234.0, 403.0 234.0, 404.0 234.0, 405.0 234.0, 406.0 234.0, 407.0 234.0, 407.0 235.0, 408.0 235.0, 409.0 235.0, 410.0 235.0, 410.0 236.0, 411.0 236.0, 412.0 236.0, 412.0 237.0, 413.0 237.0, 413.0 238.0, 414.0 238.0, 414.0 239.0, 415.0 239.0, 415.0 240.0, 415.0 241.0, 416.0 241.0, 416.0 242.0, 416.0 243.0, 416.0 244.0, 416.0 246.0, 416.0 247.0, 415.0 247.0, 415.0 248.0, 415.0 249.0, 415.0 250.0, 414.0 250.0, 414.0 251.0, 413.0 251.0, 413.0 252.0, 412.0 252.0, 412.0 253.0, 411.0 253.0, 411.0 254.0, 410.0 254.0, 409.0 254.0, 408.0 254.0, 408.0 255.0, 407.0 255.0, 406.0 255.0, 405.0 255.0, 404.0 255.0, 404.0 254.0, 403.0 254.0, 402.0 254.0, 401.0 254.0, 401.0 253.0, 400.0 253.0, 400.0 252.0, 399.0 252.0, 399.0 251.0, 398.0 251.0, 398.0 250.0, 397.0 250.0, 397.0 249.0, 396.0 249.0, 396.0 248.0, 396.0 247.0, 395.0 247.0, 395.0 246.0, 395.0 245.0, 395.0 244.0, 395.0 243.0, 395.0 242.0, 395.0 241.0, 396.0 241.0, 396.0 240.0, 396.0 239.0, 397.0 239.0, 397.0 238.0, 398.0 238.0, 399.0 238.0, 399.0 237.0, 400.0 237.0, 400.0 236.0, 401.0 236.0, 402.0 236.0, 403.0 236.0, 403.0 235.0, 404.0 235.0, 405.0 235.0, 406.0 235.0, 406.0 236.0, 407.0 236.0, 408.0 236.0, 409.0 236.0, 409.0 237.0, 410.0 237.0, 410.0 238.0, 411.0 238.0, 412.0 238.0, 412.0 239.0, 413.0 239.0, 413.0 240.0, 414.0 240.0, 414.0 241.0, 414.0 242.0, 414.0 243.0, 415.0 243.0, 415.0 244.0, 415.0 245.0, 416.0 245.0, 412.0 245.0, 412.0 246.0, 412.0 247.0, 412.0 248.0, 411.0 248.0, 411.0 249.0, 410.0 249.0, 410.0 250.0, 410.0 251.0, 409.0 251.0, 409.0 252.0, 408.0 252.0, 407.0 252.0, 406.0 252.0, 405.0 252.0, 404.0 252.0, 404.0 251.0, 403.0 251.0, 402.0 251.0, 402.0 250.0, 401.0 250.0, 401.0 249.0, 400.0 249.0, 400.0 248.0, 399.0 248.0, 399.0 247.0, 399.0 246.0, 398.0 246.0, 398.0 245.0, 398.0 244.0, 398.0 243.0, 398.0 242.0, 399.0 242.0, 399.0 241.0, 399.0 240.0, 400.0 240.0, 400.0 239.0, 401.0 239.0, 402.0 239.0, 402.0 238.0, 403.0 238.0, 404.0 238.0, 405.0 238.0, 406.0 238.0, 406.0 239.0, 407.0 239.0, 408.0 239.0, 408.0 240.0, 409.0 240.0, 410.0 240.0, 410.0 241.0, 411.0 241.0, 411.0 242.0, 411.0 243.0, 412.0 243.0, 412.0 244.0]");
		
		xipField = String.format("%s.coordIndex", xipNode);
		getIvCanvas().set(xipField, ref_pointIndex.toString());
//		getIvCanvas().set(xipField, "[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4]");
		
		xipField = String.format("%s.execute", xipNode);
		getIvCanvas().set(xipField, "");
		
		xipField = String.format("QIBA_Mask_Import_%d.execute", id);
		getIvCanvas().set(xipField, "");
		
		xipField = String.format("Mask_Switch_Import_%d.index", id);
		getIvCanvas().set(xipField, "0");		
	}

	private boolean getImageAttributesFromAD(String imageUID0, Map<String, String> attributes0){
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
				attributes0.put("ImagePosition", String.format("%f %f %f", data[0], data[1], data[2]));
			
			buffer = dcmObject0.get(Tag.PixelSpacing);
			double [] data0 = buffer.getDoubles(true);
			
			buffer = dcmObject0.get(Tag.SpacingBetweenSlices);
			double data1 = buffer.getDouble(true);
			
			if (data0.length == 2)
				attributes0.put("VoxelSpacing", String.format("%f %f %f", data0[0], data0[1], data1));		
		}
		
		return true;
	}

	public Map<String, II> getSeedInformation(String aim){
		
		Map<String, II> aimResult = new HashMap<String, II>();

		File aimFile = new File(aim);
    	if (!aimFile.exists())
    		return aimResult;
    	
        try{
		   JAXBContext jaxbContext = JAXBContext.newInstance("gme.cacore_cacore._3_2.edu_northwestern_radiology");
		   Unmarshaller u = jaxbContext.createUnmarshaller();
		   JAXBElement obj = (JAXBElement)u.unmarshal(aimFile);			
		   ImageAnnotation imageAnnotation = ((ImageAnnotation)obj.getValue());
		   
		   //get annotation UID
		   aimResult.put("AnnotationUID", imageAnnotation.getUniqueIdentifier());
		   
		   //get series instance UID
		   ImageReferenceEntity imageReference = imageAnnotation.getImageReferenceEntityCollection().getImageReferenceEntity().get(0);
		   DicomImageReferenceEntity ref = (DicomImageReferenceEntity) imageReference;
		   ImageStudy study = ref.getImageStudy();	 
		   ImageSeries series = study.getImageSeries();
		   aimResult.put("SeriesInstanceUID", series.getInstanceUid());
		   
		   //get the seed point
		   List<GeometricShapeEntity> geometricShapes = imageAnnotation.getGeometricShapeCollection().getGeometricShape();
		   SpatialCoordinateCollection pointCollection = geometricShapes.get(0).getSpatialCoordinateCollection();
		   List<SpatialCoordinate> pointCoords = pointCollection.getSpatialCoordinate();
		   
		   if (pointCoords.size() < 2)
			   return aimResult;
		   
		   //start point
		   double []point0 = new double[2];
		   TwoDimensionSpatialCoordinate point = (TwoDimensionSpatialCoordinate) pointCoords.get(0);
		   point0[0] = point.getX().getValue();
		   point0[1] = point.getY().getValue();
		   aimResult.put("ImageReferenceUID", point.getImageReferenceUID());
		   
		   int []point1 = new int[2];
		   point = (TwoDimensionSpatialCoordinate) pointCoords.get(1);
		   point1[0] = (int)point.getX();
		   point1[1] = (int)point.getY();
		   
		   aimResult.put("SeedPoints", String.format("[%d, %d, %d, %d]", point0[0], point0[1], point1[0], point1[1]));
		   
		   //get patient information
		   ImageAnnotation.Patient pat = imageAnnotation.getPatient();
		   Patient _pat = pat.getPatient();
		   aimResult.put("PatientName", _pat.getName());
		   aimResult.put("PatientID", _pat.getPatientID());
		   aimResult.put("PatientGender", _pat.getSex());
		   
        } catch (JAXBException e){
            e.printStackTrace();
 	    }    	
    		
		return aimResult;
	}
	
	public String getDataset(String seriesInstanceUID, String strTempFolder){
		String dumpFolder = String.format("%s\\%s", strTempFolder, seriesInstanceUID);
		File dump = new File(dumpFolder);
		
		if (!dump.exists()){
			ADFacade facade = ADFactory.getADServiceInstance();
			if (facade == null){
				System.out.println("fail to create ADFacade");
				return "";
			}
			HashMap<Integer, Object> dicomCriteria = new HashMap<Integer, Object>();
			dicomCriteria.put(Tag.SeriesInstanceUID, seriesInstanceUID);
			List<GeneralImage> images = facade.findImagesByCriteria(dicomCriteria, null);
			if (images.size() <=0 )
				return "";
		
			dump.mkdir();
			
			for (GeneralImage image : images){
				String sopInstanceUID = image.getSOPInstanceUID();
				DicomObject segObject = facade.getDicomObject(sopInstanceUID);
	
		    	File fileName = new File(dumpFolder + "\\" + sopInstanceUID + ".dcm");
		    	
		    	try {
					DicomOutputStream dout = new DicomOutputStream(new FileOutputStream(fileName));
					dout.writeDicomFile(segObject);
					dout.close();
		    	} catch(IOException e){
					System.out.println("fail to store DICOM file:" + fileName.toString());
		    	}
				
			}
		}
		return dumpFolder;
	}
	
	public String getDataset(String seriesInstanceUID){
		ADFacade facade = ADFactory.getADServiceInstance();
		if (facade == null){
			System.out.println("fail to create ADFacade");
			return "";
		}
		HashMap<Integer, Object> dicomCriteria = new HashMap<Integer, Object>();
		dicomCriteria.put(Tag.SeriesInstanceUID, seriesInstanceUID);
		List<GeneralImage> images = facade.findImagesByCriteria(dicomCriteria, null);
		if (images.size() <=0 )
			return "";
	
		String ptBuffer = "[";
		for (GeneralImage image : images){
		    String uri = String.format("\"%s\",", image.getUri());
			ptBuffer += uri;
		}
		String ptList = ptBuffer.substring(0, ptBuffer.lastIndexOf(","));
		ptList += "]";
		return ptList;
	}

	public ComputationResultsPanel getComputationResultsPanel(){
		return ComputePanel;
	}
	
	public void startRServe(){
//		getIvCanvas().set("Rserve.start", "");
		
//		RServeProcess rServ = new RServeProcess();
//		Thread t = new Thread(rServ);
//		t.start();	
		
		rServProcess.exec("Rserve.exe", 1000);
	}
	
	public void stopRServe(){
		rServProcess.kill();
	}
	
	public void showDrillingDown(boolean bShow){
		if (bShow)
			getIvCanvas().set("Main_Switch.whichChild", "0");
		else
			getIvCanvas().set("Main_Switch.whichChild", "-1");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	}

	@SuppressWarnings("unchecked")
	@Override
	public void statisticResultsAvailable(StatisticEvent e) {
		StatisticResult result = (StatisticResult) e.getSource();	
		
		try {
			selectItem(result);
		} catch (NullPointerException e0){
			System.out.println("Statistical result is null");
		}
	}

	public void calculateMeasurements_unused(List<String> datalist) {
		assert(progressBar != null);
		
		progressBar.setString("Processing retrieve request ...");
		progressBar.setIndeterminate(true);
		progressBar.updateUI();
		
		ComputePanel.clearTab();
		ComputePanel.clearEntries();
		
		//internal testing
//		outDir = "D:\\temp\\";
		
		try {
			if (!outDir.isEmpty()){
				ADRetrieve adRetrirve = new ADRetrieve(datalist, outDir);
//				adRetrirve.addMVTListener(this);
				Thread t = new Thread(adRetrirve);
				t.start();	
			} else {
				System.out.println("please check the output folder:" + outDir);
			}
		} catch (NullPointerException e){
			System.out.println("output folder is null");
			progressBar.setString("");
			progressBar.setIndeterminate(false);	
		}
	}
	
	public void calculateMeasurements(List<SubjectListEntry> datalist, List<String> reader_Label) {
		assert(progressBar != null);
		
		progressBar.setString("Begin calculation");
		progressBar.setIndeterminate(true);			
		progressBar.updateUI();
		
		ComputePanel.clearTab();
		ComputePanel.clearEntries();
		
		System.out.println("Begin calculation");
		System.out.println(datalist.size());
		
		MVTCalculate mvtCalculate = new MVTCalculate(datalist, reader_Label, this);
		mvtCalculate.setCalculationType(studyType);
		mvtCalculate.setCalculationSubjects(nominalGT);
		
		mvtCalculate.addMVTListener(this);
		Thread t = new Thread(mvtCalculate);
		t.start();	
	}

	public void setUIProgressBar(JProgressBar selectionProgressBar) {
		this.progressBar = selectionProgressBar;		
	}

	public void setOutputDir(String outDir) {
		this.outDir = outDir;
	}

	@Override
	public void calculateResultsAvailable(MVTCalculateEvent e) {
		ComputationListEntry result = (ComputationListEntry) e.getSource();		
		
		if (result.isLastOne()){
			progressBar.setString("Finish calculation");
			progressBar.setIndeterminate(false);	
		}
		else
		{
			ComputePanel.addEntry(result);
			ComputePanel.updateTab();
			
			progressBar.setString("calculating");
		}		
	}
	
	private boolean getImageAttributes(String imageUID0, Map<String, String> attributes0) {
		// TODO Auto-generated method stub
		
		String folder = "data\\3";
		File dir = new File(folder);
		String[] files = dir.list(); 
		if (files != null){
			
			boolean bFind0 = false;
			
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
						attributes0.put("VoxelSpacing", spacing);						
						bFind0 = true;
					}
		
					if (bFind0)
						return true;
				}				
			}
		}
		
		return false;
	}

	@Override
	public void retriveResultsAvailable(ADRetrieveEvent e) {
//		
//		progressBar.setString("Finish retrieving");
//		
//		System.out.println("Finish retrieving");
//		
//		ADRetrieveResult result = (ADRetrieveResult) e.getSource();				
//		if(result != null){	
//	
//			ComputePanel.clearEntries();
//			
//			try {
//				List<RetrieveResult> aims = result.getAims();
//				calculateResults(aims);
//			} catch (NullPointerException e0){
//				System.out.println("Failed to calculate the retrieve aims");
//			}
//		}
	}

	@Override
	public void searchResultsAvailable(ADSearchEvent e) {
	}
	
//	public void calculateResults(List<RetrieveResult> aims){
//		
//		progressBar.setString("Begin calculation");
//		
//		System.out.println("Begin calculation");
//		System.out.println(aims.size());
//		
//		MVTCalculate mvtCalculate = new MVTCalculate(aims, this);
//		mvtCalculate.addMVTListener(this);
//		Thread t = new Thread(mvtCalculate);
//		t.start();			
//	}
}  //  @jve:decl-index=0:visual-constraint="10,10"

class RServeProcess implements Runnable {

	@Override
	public void run() {
		
	    try {
	        Process p = Runtime.getRuntime().exec("Rserve.exe");
	        p.waitFor();
	        System.out.println(p.exitValue());
	      }
	      catch (Exception err) {
	        err.printStackTrace();
	      }
		
	}
	
}
