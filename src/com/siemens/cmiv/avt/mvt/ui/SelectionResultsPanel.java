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
package com.siemens.cmiv.avt.mvt.ui;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.JTabbedPane;

import com.siemens.cmiv.avt.mvt.ad.ADQuery;
import com.siemens.cmiv.avt.mvt.ad.ADRetrieveEvent;
import com.siemens.cmiv.avt.mvt.ad.ADSearchEvent;
import com.siemens.cmiv.avt.mvt.ad.ADSearchResult;
import com.siemens.cmiv.avt.mvt.core.MVTCalculateEvent;
import com.siemens.cmiv.avt.mvt.core.MVTListener;
import com.siemens.cmiv.avt.mvt.datatype.AnnotationEntry;
import com.siemens.cmiv.avt.mvt.datatype.FilterEntry;
import com.siemens.cmiv.avt.mvt.datatype.SubjectListEntry;
import com.siemens.cmiv.avt.mvt.io.SubjectList;
import com.siemens.cmiv.avt.mvt.io.SubjectListXml;
import com.siemens.cmiv.avt.mvt.io.XMLSubjectListImpl;
import com.siemens.cmiv.avt.mvt.io.SubjectListXml.Subject;
import com.siemens.cmiv.avt.mvt.statistic.StatisticResult;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jie Zheng
 *
 */

public class SelectionResultsPanel extends JPanel implements ActionListener, MVTListener{

	private static final long serialVersionUID = 1L;

	JTable jSubjectTab = null;
	private JTabbedPane jSubjectTabbedPane = null;
	
	SubjectTableModel subTabModel = new SubjectTableModel();
	SubjectList xmlList = new XMLSubjectListImpl();  //  @jve:decl-index=0:
	JProgressBar progressBar = null;
	/**
	 * This is the default constructor
	 */
	public SelectionResultsPanel() {
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
		final Color labelColor = new Color(156, 162, 189);
		
		this.setBackground(xipColor);
		
		GridLayout gridLayout = new GridLayout();
		gridLayout.setRows(1);
		gridLayout.setVgap(5);
		this.setLayout(gridLayout);
		this.setSize(319, 286);
		this.add(getJSubjectTabbedPane(), null);
		
		jSubjectTab = new JTable(subTabModel);
		jSubjectTab.setPreferredScrollableViewportSize(new Dimension(550,30));
		jSubjectTab.setForeground(Color.WHITE);
		jSubjectTab.setBackground(labelColor);
		jSubjectTab.setFillsViewportHeight(true);
		jSubjectTab.setShowGrid(true);	
		jSubjectTab.setFillsViewportHeight(true);
		jSubjectTab.getColumnModel().getColumn(0).setPreferredWidth(10);
		jSubjectTab.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollSubPane=new JScrollPane(jSubjectTab);
		jSubjectTabbedPane.addTab("Subject", scrollSubPane);

//		initialData();
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

	public void queryData(FilterEntry filter){
		jSubjectTab.removeAll();

		String path = "config/DataList.xml";
		File xmlSubjectListFile = new File(path);	
		xmlList.setFilterList(filter);
		xmlList.loadSubjectList(xmlSubjectListFile);	
		
		subTabModel.fireTableDataChanged();
	}
	
	public void initialData(){
		String path = "config/DataList.xml";
		File xmlSubjectListFile = new File(path);	
		xmlList.loadSubjectList(xmlSubjectListFile);		
	}

	public void loadAims(String aimFolder){
		if (aimFolder.length() == 0){
			System.out.println("error: input aim folder is empty");
			return;
		}
		
		File aimData = new File(String.format("%s/DataList.xml", aimFolder));
		aimData.delete();
		
		SubjectListXml aExperiment = new SubjectListXml();

		ArrayList<Subject> subjects = new ArrayList<Subject>();
		aExperiment.getsubjects(new File(aimFolder),subjects);

		String path = String.format("%s/DataList.xml", aimFolder);
		aExperiment.outputXml(subjects, new File(path));
		
		File xmlSubjectListFile = new File(path);	
		xmlList.loadSubjectList(xmlSubjectListFile);		
		
		subTabModel.fireTableDataChanged();
	}
	
	public SubjectListEntry getItemEntry(int index){
		if (index >= 0 && index < xmlList.getNumberOfSubjectListEntries()){
			return xmlList.getSubjectListEntry(index);
		}
		
		return null;
	}
	
	@SuppressWarnings("serial")
	class SubjectTableModel extends AbstractTableModel {
		String[] strArrayColumnNames = {
			"Status",
			"Subject Name",
			"Subject ID",
			"Subject Gender",
			"Subject Age",
			"Study Date",
			"Study Time",
			"Modality",
			"Study Desc", 
       };    
		
		public int getColumnCount() {			
			return strArrayColumnNames.length;
		}

		public int getRowCount() {			
			return xmlList.getNumberOfSubjectListEntries();
		}

		public Object getValueAt(int rowIndex, int columnIndex) {
			switch( columnIndex ) {
				//check box
				case 0:
					return xmlList.getSubjectListEntry(rowIndex).getSubjectStatus();
                //Subject name
				case 1:
                	return xmlList.getSubjectListEntry(rowIndex).getSubjectNameObj();
				//Subject ID
				case 2:
                	return xmlList.getSubjectListEntry(rowIndex).getSubjectIDObj();
                //
				case 3:
                	return xmlList.getSubjectListEntry(rowIndex).getSubjectGenderObj();
                //
				case 4:
                	return xmlList.getSubjectListEntry(rowIndex).getSubjectAgeObj();
                //
				case 5:
                	return xmlList.getSubjectListEntry(rowIndex).getStudyDateObj();
	                //
				case 6:
                	return xmlList.getSubjectListEntry(rowIndex).getStudyTimeObj();
	                //
				case 7:
                	return xmlList.getSubjectListEntry(rowIndex).getStudyModalityObj();
	                //
				case 8:
                	return xmlList.getSubjectListEntry(rowIndex).getStudyDescriptionObj();
            }   
			
			return null;
		}
		
		@SuppressWarnings("unchecked")
		public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }
	
		@Override
		public String getColumnName( int col ) {
            return strArrayColumnNames[col];
        }
		
	    public boolean isCellEditable(int row, int col) {
			// Note that the data/cell address is constant,
			// no matter where the cell appears onscreen.
			if (col > 0) {
				return false;
			} else {
				return true;
			}
		}
		
        public void setValueAt(Object value, int row, int col) {
			switch( col ) {
			//check box
			case 0:
				xmlList.getSubjectListEntry(row).setSubjectStatusObj(value);
				break;
            //Subject name
			case 1:
            	xmlList.getSubjectListEntry(row).setSubjectNameObj(value);
            	break;
			//Subject ID
			case 2:
            	xmlList.getSubjectListEntry(row).setSubjectIDObj(value);
				break;
           //
			case 3:
            	xmlList.getSubjectListEntry(row).setSubjectGenderObj(value);
				break;
           //
			case 4:
            	xmlList.getSubjectListEntry(row).setSubjectAgeObj(value);
				break;
           //
			case 5:
            	xmlList.getSubjectListEntry(row).setStudyDateObj(value);
				break;
                //
			case 6:
            	xmlList.getSubjectListEntry(row).setStudyTimeObj(value);
				break;
               //
			case 7:
            	xmlList.getSubjectListEntry(row).setStudyModalityObj(value);
				break;
               //
			case 8:
            	xmlList.getSubjectListEntry(row).setStudyDescriptionObj(value);
				break;
       }   
            fireTableCellUpdated(row, col);
        }

        public int getColumnWidth( int nCol ) {
            switch( nCol ) {
                case 0:
                    return 10;
                case 1:
                    return 100;
                case 2:
                    return 150;
                case 3:
                    return 300;
                case 4:
                    return 200;                
                default:
                    return 150;
            }
        }		
	}

	public List<String> getDatalist() {
		// TODO Auto-generated method stub
		List<String> datalist = new ArrayList<String>();
		List<SubjectListEntry> entries = xmlList.getSubjectListEntries();
		
		for (int i = 0; i < entries.size(); i++){
			try {
			String modality = entries.get(i).getStudyModality();
			
			//only image data are required
			if (modality.compareToIgnoreCase("SEG") !=0 ){
				String seriesUID = entries.get(i).getSeriesInstanceUIDCurr();
				datalist.add(seriesUID);
			}
			} catch (NullPointerException e) {
				System.out.println("no modality or seriesInstanceUID information");
			}
		}
		
		return datalist;
	}	
	
	Boolean verifyCriteria(FilterEntry filter) {
		// TODO Auto-generated method stub
		return true;
	}

	public void queryAD(FilterEntry filter){
		assert(progressBar != null);
		
		progressBar.setString("Processing search request ...");
		progressBar.setIndeterminate(true);			
		progressBar.updateUI();

		jSubjectTab.removeAll();

		Boolean bln = verifyCriteria(filter);
		
		if (bln){

		ADQuery adQuery = new ADQuery(filter);
		adQuery.addMVTListener(this);
		Thread t = new Thread(adQuery);
		t.start();			
		}
		else {
			progressBar.setString("");
			progressBar.setIndeterminate(false);
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
	}

	public void setUIProgressBar(JProgressBar selectionProgressBar) {
		this.progressBar = selectionProgressBar;
	}

	@Override
	public void retriveResultsAvailable(ADRetrieveEvent e) {
	}

	@Override
	public void searchResultsAvailable(ADSearchEvent e) {
		ADSearchResult result = (ADSearchResult) e.getSource();				
		if(result != null){	
			xmlList.updateQuerySubjectList(result);	
			
			subTabModel.fireTableDataChanged();
		}
											
		progressBar.setString("AVT AD Search finished");
		progressBar.setIndeterminate(false);	

		System.out.println("Finish querying");

	}

	@Override
	public void calculateResultsAvailable(MVTCalculateEvent e) {
	}
	
	public JTable getSubjectTable(){
		return jSubjectTab;
	}
	
	public List<String> getReaderList(){
		List<String> annotations = new ArrayList<String>();
		for (int i = 0; i < xmlList.getNumberOfSubjectListEntries(); i++){
			SubjectListEntry item = xmlList.getSubjectListEntry(i);
			
			List<AnnotationEntry> list = item.getAnnotations();
			for (int j = 0; j < list.size(); j++){
				String annotator = list.get(j).getAnnotationReader();
				String stamp = list.get(j).getAnnotationStamp();
				
				String reader = String.format("%s-%s", annotator, stamp);
				if (stamp.isEmpty())
					reader = annotator;
				
				if (!annotations.contains(reader))
					annotations.add(reader);
			}
		}
		
		return annotations;
	}

	public List<SubjectListEntry> getSubjectlist() {
		List<SubjectListEntry> entries = new ArrayList<SubjectListEntry>();
		List<SubjectListEntry> items = xmlList.getSubjectListEntries();
		for (int i = 0; i < items.size(); i++){
			SubjectListEntry item = items.get(i);
			String buf = item.getBChecked().toString();
			if (buf.compareToIgnoreCase("true") == 0)
				entries.add(item);
		}
		
		return entries;
	}
 	
 }  //  @jve:decl-index=0:visual-constraint="10,10"

