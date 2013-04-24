package com.siemens.cmiv.avt.mvt.ui;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import com.siemens.cmiv.avt.mvt.datatype.ComputationListEntry;
import com.siemens.cmiv.avt.mvt.datatype.MeasurementEntry;
import com.siemens.cmiv.avt.mvt.io.XMLComputationList;
import com.siemens.cmiv.avt.mvt.statistic.StatisticEvent;
import com.siemens.cmiv.avt.mvt.statistic.StatisticResult;
import com.siemens.cmiv.avt.mvt.statistic.StatisticResultListener;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;

/**
 * @author Jie Zheng
 *
 */

public class ComputationResultsPanel extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	
	JList textArea = null;
	JScrollPane jScrollPane1 = null;
	DefaultListModel listModel = new DefaultListModel(); 
	
	JList output = null;
	JScrollPane jScrollOutput = null;
	DefaultListModel outputModel = new DefaultListModel(); 
	private JTabbedPane jResultTabbedPane = null;
	
	JScrollPane scroller = null;
	PlottingPanel plotPanel = null;
	
	JTable jSubjectTab = null;
	private JTabbedPane jSubjectTabbedPane = null;
	
	XMLComputationList xmlList = new XMLComputationList();
	ResultsTableModel subTabModel = new ResultsTableModel(xmlList);

	XTableColumnModel resultTabModel = new XTableColumnModel();
	
	//multiple reader
	JTable jInterReaderTab = null;
	XMLComputationList interReaderList = new XMLComputationList();
	ResultsTableModel interTabModel = new ResultsTableModel(interReaderList);
	
	JTable jIntraReaderTab = null;
	XMLComputationList intraReaderList = new XMLComputationList();
	ResultsTableModel intraTabModel = new ResultsTableModel(intraReaderList);
	
	String modelType = "SOV";
	
	/**
	 * This is the default constructor
	 */
	public ComputationResultsPanel() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		
		Color xipColor = new Color(51, 51, 102);
		Color labelColor = new Color(156, 162, 189);
		
		this.setBackground(xipColor);
		
		GridLayout gridLayout = new GridLayout(0,2);
		gridLayout.setRows(2);
		gridLayout.setVgap(5);
		
		this.setLayout(gridLayout);
		this.setSize(319, 286);
		this.add(getJSubjectTabbedPane());
		this.add(getJResultTabbedPane());

		output = new JList(outputModel);
		output.setForeground(Color.WHITE);
		output.setBackground(labelColor);
	    
		jScrollOutput = new JScrollPane(output);
		jResultTabbedPane.addTab("Statistics", jScrollOutput);
		
		textArea = new JList(listModel);
	    textArea.setForeground(Color.WHITE);
	    textArea.setBackground(labelColor);
	    
	    jScrollPane1 = new JScrollPane(textArea);
//		this.add(jScrollPane1);
		jResultTabbedPane.addTab("Outliers", jScrollPane1);
		
		plotPanel = new PlottingPanel();
		plotPanel.setForeground(Color.WHITE);
		plotPanel.setBackground(labelColor);
		scroller = new JScrollPane(plotPanel);
		
		jResultTabbedPane.addTab("Plotting", scroller);

		buildResultTab();

//		for internal testing		
//		addPlotChart("d:\\ba.png");
//		addPlotChart("d:\\scatter.png");
//		updatePlotPanel();
	}
	
	public void buildResultTab(){
		Color labelColor = new Color(156, 162, 189);
		
		jSubjectTab = new JTable(subTabModel);
		jSubjectTab.setColumnModel(resultTabModel);
		jSubjectTab.createDefaultColumnsFromModel();
		jSubjectTab.setPreferredScrollableViewportSize(new Dimension(550,30));
		jSubjectTab.setForeground(Color.WHITE);
		jSubjectTab.setBackground(labelColor);
		jSubjectTab.setFillsViewportHeight(true);
		jSubjectTab.setShowGrid(true);	
		jSubjectTab.addMouseListener(new ActionJList());
//		jSubjectTab.getSelectionModel().addListSelectionListener(new RowListener());
		jSubjectTab.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		jInterReaderTab = new JTable(interTabModel);
		jInterReaderTab.setColumnModel(resultTabModel);
		jInterReaderTab.createDefaultColumnsFromModel();
		jInterReaderTab.setPreferredScrollableViewportSize(new Dimension(550,30));
		jInterReaderTab.setForeground(Color.WHITE);
		jInterReaderTab.setBackground(labelColor);
		jInterReaderTab.setFillsViewportHeight(true);
		jInterReaderTab.setShowGrid(true);	
		jInterReaderTab.addMouseListener(new ActionJList());
//		jInterReaderTab.getSelectionModel().addListSelectionListener(new RowListener());
		jInterReaderTab.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		jIntraReaderTab = new JTable(intraTabModel);
		jIntraReaderTab.setColumnModel(resultTabModel);
		jIntraReaderTab.createDefaultColumnsFromModel();
		jIntraReaderTab.setPreferredScrollableViewportSize(new Dimension(550,30));
		jIntraReaderTab.setForeground(Color.WHITE);
		jIntraReaderTab.setBackground(labelColor);
		jIntraReaderTab.setFillsViewportHeight(true);
		jIntraReaderTab.setShowGrid(true);	
		jIntraReaderTab.addMouseListener(new ActionJList());
//		jIntraReaderTab.getSelectionModel().addListSelectionListener(new RowListener());
		jIntraReaderTab.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}
	
	public void updateResultTab(String type){
		jSubjectTabbedPane.removeAll();
		
		if (type.compareToIgnoreCase("SOV") == 0){
			JScrollPane scrollSubPane=new JScrollPane(jSubjectTab);
			jSubjectTabbedPane.addTab("Computation Results", scrollSubPane);
		}
		if (type.compareToIgnoreCase("MultipleReader") == 0){
			JScrollPane scrollSubPane1=new JScrollPane(jIntraReaderTab);
			jSubjectTabbedPane.addTab("Intra_Reader Results", scrollSubPane1);
			
			JScrollPane scrollSubPane0=new JScrollPane(jInterReaderTab);
			jSubjectTabbedPane.addTab("Inter_Reader Results", scrollSubPane0);			
		}
		
		modelType = type;
	}

	public void queryData(List<String> datalist){
		jSubjectTab.removeAll();

		xmlList.setDatalist(datalist);
		String path = "config/ComputationResults.xml";
		File xmlSubjectListFile = new File(path);	
		xmlList.loadComputationList(xmlSubjectListFile);		
	}

	/**
	 * This method initializes jSubjectTabbedPane	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */
	private JTabbedPane getJSubjectTabbedPane() {
		if (jSubjectTabbedPane == null) {
			jSubjectTabbedPane = new JTabbedPane();
		}
		return jSubjectTabbedPane;
	}
	
	private JTabbedPane getJResultTabbedPane() {
		if (jResultTabbedPane == null) {
			jResultTabbedPane = new JTabbedPane();
		}
		return jResultTabbedPane;
	}
	
	@SuppressWarnings("serial")
	class ResultsTableModel extends AbstractTableModel {
		
		/** Column names */
		String[] strArrayColumnNames = {
			"Subject Name",
			"Subject ID",
			"Label",
			"NominalGT RECIST[mm]",
			"Annotation RECIST[mm]",
			"RECIST Difference[mm]",
			"NominalGT WHO[mm2]",
			"Annotation WHO[mm2]",
			"WHO Difference[mm2]",
			"NominalGT Volume[ml]",
			"Annotation Volume[ml]",
			"Volume Difference[ml]",
			"Relative VolumeDifference[%]",
			"Surface Distance(Average)[mm]",
			"Surface Distance(RMS)[mm]",
			"Surface Distance(Maximum)[mm]",
			"Volume Overlap[%]"
       };    
		private XMLComputationList itemList = null;
		/** Constructor */
		public ResultsTableModel(XMLComputationList items){
			itemList = items;
		}
		
		public int getColumnCount() {
			return strArrayColumnNames.length;
		}

		public int getRowCount() {			
			return itemList.getNumberOfComputationListEntries();
		}

		public Object getValueAt(int rowIndex, int columnIndex) {
			int col = columnIndex;
			switch( col ) {
                //Subject name
				case 0:
                	return itemList.getComputationListEntry(rowIndex).getSubjectName();
                	
				case 1:
                	return itemList.getComputationListEntry(rowIndex).getSubjectID();

				case 2:
                	return itemList.getComputationListEntry(rowIndex).getLabel();
              //
				case 3:
					return itemList.getComputationListEntry(rowIndex).getReferenceRECIST();
				case 4:
					return itemList.getComputationListEntry(rowIndex).getSegmentationRECIST();
				case 5:
					return itemList.getComputationListEntry(rowIndex).getRECISTDifference();
					
				case 6:
					return itemList.getComputationListEntry(rowIndex).getReferenceWHO();
				case 7:
					return itemList.getComputationListEntry(rowIndex).getSegmentationWHO();
				case 8:
					return itemList.getComputationListEntry(rowIndex).getWHODifference();

				case 9:
					return itemList.getComputationListEntry(rowIndex).getReferenceVolume();
				case 10:
					return itemList.getComputationListEntry(rowIndex).getSegmentationVolume();
				case 11:
                    return itemList.getComputationListEntry(rowIndex).getVolumeDifference();
                //
				case 12:
					return itemList.getComputationListEntry(rowIndex).getRelativeVolumeDifference();
				
				case 13:
                    return itemList.getComputationListEntry(rowIndex).getAverageSurfaceDistance();
				
				case 14:
					return itemList.getComputationListEntry(rowIndex).getAverageRMSSurfaceDistance();
                    
				case 15:
                   return itemList.getComputationListEntry(rowIndex).getMaximumSurfaceDistance();
                    
				case 16:
                    return itemList.getComputationListEntry(rowIndex).getVolumeOverlap();
                    
              default:
                    return null;
            }           			
		}
				
		@Override
		public String getColumnName( int col ) {
            return strArrayColumnNames[col];
        }
		
		public int getColumnWidth( int nCol ) {
            switch( nCol ) {
                case 0:
                    return 100;
                default:
                    return 100;
            }
        }
		
	}
	public void setResultsListFlag(MeasurementEntry flags) {
		// TODO Auto-generated method stub
		TableColumn column  = resultTabModel.getColumnByModelIndex(0);
		resultTabModel.setColumnVisible(column, true);
		
		column = resultTabModel.getColumnByModelIndex(1);
		resultTabModel.setColumnVisible(column, true);

		int index  = 3;
		column = resultTabModel.getColumnByModelIndex(index);
		resultTabModel.setColumnVisible(column, flags.isBRefRECIST());
		column = resultTabModel.getColumnByModelIndex(++index);
		resultTabModel.setColumnVisible(column, flags.isBSegRECIST());
		column = resultTabModel.getColumnByModelIndex(++index);
		resultTabModel.setColumnVisible(column, flags.isBRECISTDiff());

		column = resultTabModel.getColumnByModelIndex(++index);
		resultTabModel.setColumnVisible(column, flags.isBRefWHO());
		column = resultTabModel.getColumnByModelIndex(++index);
		resultTabModel.setColumnVisible(column, flags.isBSegWHO());
		column = resultTabModel.getColumnByModelIndex(++index);
		resultTabModel.setColumnVisible(column, flags.isBWHODiff());

		column = resultTabModel.getColumnByModelIndex(++index);
		resultTabModel.setColumnVisible(column, flags.isBRefVolume());
		column = resultTabModel.getColumnByModelIndex(++index);
		resultTabModel.setColumnVisible(column, flags.isBSegVolume());
		column = resultTabModel.getColumnByModelIndex(++index);
		resultTabModel.setColumnVisible(column, flags.getVolumeDiff());
		
		column  = resultTabModel.getColumnByModelIndex(++index);
		resultTabModel.setColumnVisible(column, flags.getRelativeVolDiff());
		
		column  = resultTabModel.getColumnByModelIndex(++index);
		resultTabModel.setColumnVisible(column, flags.getAvgSurfDist());
		
		column  = resultTabModel.getColumnByModelIndex(++index);
		resultTabModel.setColumnVisible(column, flags.getRMSSurfDist());
		
		column  = resultTabModel.getColumnByModelIndex(++index);
		resultTabModel.setColumnVisible(column, flags.getMaxSurfDist());
		
		column  = resultTabModel.getColumnByModelIndex(++index);
		resultTabModel.setColumnVisible(column, flags.getVolOverlap());
	}
	
	public void updateResultList() {
		if (modelType.compareToIgnoreCase("SOV") == 0)
			subTabModel.fireTableDataChanged();
		if (modelType.compareToIgnoreCase("MultipleReader") == 0){
			int index = jSubjectTabbedPane.getSelectedIndex();
			String title = jSubjectTabbedPane.getTitleAt(index);
			if (title.compareToIgnoreCase("Inter_Reader Results") == 0){
				interTabModel.fireTableDataChanged();
			}
			if (title.compareToIgnoreCase("Intra_Reader Results") == 0){
				intraTabModel.fireTableDataChanged();
			}
		}
	}	
	
	private class ActionJList extends MouseAdapter{
	    
	  public void mouseClicked(MouseEvent e){
	   if(e.getClickCount() == 2){
		   
           int index = getSelectTabItem();
           if (index != -1){
        	   XMLComputationList itemList = getXMLComputationList();
        	   String subjectName = itemList.getComputationListEntry(index).getSubjectName();
        	   String subjectUID = itemList.getComputationListEntry(index).getSubjectUID();
        	   
        	   StatisticResult result = new StatisticResult(subjectName, subjectUID);
        	   
        	   String aimBuffer = itemList.getComputationListEntry(index).getRefRECISTAim();
        	   result.AddSubjectAim("RefRECISTAim", aimBuffer);
        	   
        	   aimBuffer = itemList.getComputationListEntry(index).getSegRECISTAim();
        	   result.AddSubjectAim("SegRECISTAim", aimBuffer);
           	   
        	   aimBuffer = itemList.getComputationListEntry(index).getRefWHOAim();
        	   result.AddSubjectAim("RefWHOAim", aimBuffer);
           	   
        	   aimBuffer = itemList.getComputationListEntry(index).getSegWHOAim();
        	   result.AddSubjectAim("SegWHOAim", aimBuffer);
           	   
        	   aimBuffer = itemList.getComputationListEntry(index).getRefVolumeAim();
        	   result.AddSubjectAim("RefVolumeAim", aimBuffer);
           	   
        	   aimBuffer = itemList.getComputationListEntry(index).getSegVolumeAim();
        	   result.AddSubjectAim("SegVolumeAim", aimBuffer);

        	   if (subjectUID.compareToIgnoreCase("1.2.3.4.5.6.7.8.9.0") != 0)
        		   fireResultsAvailable(result);
           }
           
	     System.out.println("Double clicked");
		}	 				 
	  }
	}
	
    private class RowListener implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent arg0) {
           if (arg0.getValueIsAdjusting()) {
                return;
            }
           int index = getSelectTabItem();
           if (index != -1){
        	   XMLComputationList itemList = getXMLComputationList();
        	   String subjectName = itemList.getComputationListEntry(index).getSubjectName();
        	   String subjectUID = itemList.getComputationListEntry(index).getSubjectUID();
        	   
        	   StatisticResult result = new StatisticResult(subjectName, subjectUID);
        	   
        	   String aimBuffer = itemList.getComputationListEntry(index).getRefRECISTAim();
        	   result.AddSubjectAim("RefRECISTAim", aimBuffer);
        	   
        	   aimBuffer = itemList.getComputationListEntry(index).getSegRECISTAim();
        	   result.AddSubjectAim("SegRECISTAim", aimBuffer);
           	   
        	   aimBuffer = itemList.getComputationListEntry(index).getRefWHOAim();
        	   result.AddSubjectAim("RefWHOAim", aimBuffer);
           	   
        	   aimBuffer = itemList.getComputationListEntry(index).getSegWHOAim();
        	   result.AddSubjectAim("SegWHOAim", aimBuffer);
           	   
        	   aimBuffer = itemList.getComputationListEntry(index).getRefVolumeAim();
        	   result.AddSubjectAim("RefVolumeAim", aimBuffer);
           	   
        	   aimBuffer = itemList.getComputationListEntry(index).getSegVolumeAim();
        	   result.AddSubjectAim("SegVolumeAim", aimBuffer);

        	   fireResultsAvailable(result);
           }
		}
    }
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void clearOutlier(){
		listModel.clear();
	}
	public void AddOutlier(String item){
		listModel.addElement(item);
	}
	
	public void clearStatistics(){
		outputModel.clear();
	}
	public void AddStatistics(String item){
		outputModel.addElement(item);
	}	
	
	public boolean isColumnVisible(String element) {
		int offset = 2;
		
		int index  = 0;
		if (element.equalsIgnoreCase("NominalGT RECIST"))
			index = offset + 1;
		if (element.equalsIgnoreCase("Annotation RECIST"))
			index = offset + 2;
		if (element.equalsIgnoreCase("RECIST Difference"))
			index = offset + 3;
		
		if (element.equalsIgnoreCase("NominalGT WHO"))
			index = offset + 4;
		if (element.equalsIgnoreCase("Annotation WHO"))
			index = offset + 5;
		if (element.equalsIgnoreCase("WHO Difference"))
			index = offset + 6;

		if (element.equalsIgnoreCase("NominalGT Volume"))
			index = offset + 7;
		if (element.equalsIgnoreCase("Annotation Volume"))
			index = offset + 8;
		if (element.equalsIgnoreCase("Volume Difference"))
			index = offset + 9;
		
		if (element.equalsIgnoreCase("Relative VolumeDifference"))
			index = offset + 10;
		
		if (element.equalsIgnoreCase("Surface Distance(Average)"))
			index = offset + 11;
		
		if (element.equalsIgnoreCase("Surface Distance(RMS)"))
			index = offset + 12;
		
		if (element.equalsIgnoreCase("Surface Distance(Maximum)"))
			index = offset + 13;
		
		if (element.equalsIgnoreCase("Volume Overlap"))
			index = offset + 14;
		
		TableColumn column  = resultTabModel.getColumnByModelIndex(index);
		return resultTabModel.isColumnVisible(column);
	}
	
	public XMLComputationList getXMLComputationList(){
		if (modelType.compareToIgnoreCase("SOV") == 0)
			return xmlList;
		if (modelType.compareToIgnoreCase("MultipleReader") == 0){
			int index = jSubjectTabbedPane.getSelectedIndex();
			String title = jSubjectTabbedPane.getTitleAt(index);
			if (title.compareToIgnoreCase("Inter_Reader Results") == 0){
				return interReaderList;
			}
			if (title.compareToIgnoreCase("Intra_Reader Results") == 0){
				return intraReaderList;
			}
		}
		
		return null;
	}
	
	public int getSelectTabItem(){
		if (modelType.compareToIgnoreCase("SOV") == 0)
			return jSubjectTab.getSelectedRow();
		if (modelType.compareToIgnoreCase("MultipleReader") == 0){
			int index = jSubjectTabbedPane.getSelectedIndex();
			String title = jSubjectTabbedPane.getTitleAt(index);
			if (title.compareToIgnoreCase("Inter_Reader Results") == 0){
				return jInterReaderTab.getSelectedRow();
			}
			if (title.compareToIgnoreCase("Intra_Reader Results") == 0){
				return jIntraReaderTab.getSelectedRow();
			}
		}
		
		return -1;
	}

	public String getComputationType(){
		return modelType;
	}
	
	public XMLComputationList getSOVComputationList(){
		return xmlList;
	}
	
	public XMLComputationList getInterReaderComputationList(){
		return interReaderList;
	}
	
	public XMLComputationList getIntraReaderComputationList(){
		return intraReaderList;
	}
	
  	public ResultsTableModel getResultsTableModel(){
		if (modelType.compareToIgnoreCase("SOV") == 0)
			return subTabModel;
		if (modelType.compareToIgnoreCase("MultipleReader") == 0){
			int index = jSubjectTabbedPane.getSelectedIndex();
			String title = jSubjectTabbedPane.getTitleAt(index);
			if (title.compareToIgnoreCase("Inter_Reader Results") == 0){
				return interTabModel;
			}
			if (title.compareToIgnoreCase("Intra_Reader Results") == 0){
				return intraTabModel;
			}
		}
		
		return null;
  	}
  	
  	public int getResultsTableCols(){
		if (modelType.compareToIgnoreCase("SOV") == 0)
			return subTabModel.getColumnCount();
		if (modelType.compareToIgnoreCase("MultipleReader") == 0){
			int index = jSubjectTabbedPane.getSelectedIndex();
			String title = jSubjectTabbedPane.getTitleAt(index);
			if (title.compareToIgnoreCase("Inter_Reader Results") == 0){
				return interTabModel.getColumnCount();
			}
			if (title.compareToIgnoreCase("Intra_Reader Results") == 0){
				return intraTabModel.getColumnCount();
			}
		}
			
 		return 0;
  	}
  	
  	StatisticResultListener listener;
    public void addStatisticalResultListener(StatisticResultListener l) {        
        listener = l;          
    }
    
	void fireResultsAvailable(StatisticResult searchResult){
		StatisticEvent event = new StatisticEvent(searchResult);         		
        listener.statisticResultsAvailable(event);
	}
	
	public void clearTab() {
		jSubjectTab.removeAll();
	}

	public void clearEntries(){
		if (modelType.compareToIgnoreCase("SOV") == 0)
			xmlList.clearEntries();
		
		if (modelType.compareToIgnoreCase("MultipleReader") == 0){
			interReaderList.clearEntries();
			intraReaderList.clearEntries();
		}
	}

	public void addEntry(ComputationListEntry entry){
		if (modelType.compareToIgnoreCase("SOV") == 0)
			xmlList.addComputationListEntry(entry);
		
		if (modelType.compareToIgnoreCase("MultipleReader") == 0){
			if (entry.getType().compareToIgnoreCase("inter-reader") == 0)
				interReaderList.addComputationListEntry(entry);
			if (entry.getType().compareToIgnoreCase("intra-reader") == 0)
				intraReaderList.addComputationListEntry(entry);
		}		
	}
	
	public void updateTab() {
		subTabModel.fireTableDataChanged();
		interTabModel.fireTableDataChanged();
		intraTabModel.fireTableDataChanged();
	}

	public void emptyPlotCharts(){
		plotPanel.emptyImages();
	}
	
	public void addPlotChart(String plotFile){
		plotPanel.addPlottingImage(plotFile);
	}
	
	public void updatePlotPanel(){
		Dimension dim = plotPanel.getViewsize();
		plotPanel.scrollRectToVisible(new Rectangle(0, 0, dim.width, dim.height));
		plotPanel.setPreferredSize(dim);
		
		plotPanel.updateImageRectArray();		
		plotPanel.repaint();
		
		updateTabbedPane(2);
	}
	
	public void updateTabbedPane(int index){
		
		jResultTabbedPane.setSelectedIndex(index);

	}
}


