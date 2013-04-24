package com.siemens.cmiv.avt.mvt.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.JTabbedPane;

import com.siemens.cmiv.avt.mvt.datatype.AnnotationEntry;

@SuppressWarnings("serial")
public class AnnotationPanel extends JPanel {
	
	JTable jSubjectTab = null;
	private JTabbedPane jSubjectTabbedPane = null;
	
	List<AnnotationEntry> annotationList = new ArrayList<AnnotationEntry>();
	AnnotationTableModel subTabModel = new AnnotationTableModel();

	/**
	 * This is the default constructor
	 */
	public AnnotationPanel() {
		super();
		initialize();
	}
	
	private void initialize() {
		
		Color xipColor = new Color(51, 51, 102);
		final Color labelColor = new Color(156, 162, 189);
		
		this.setBackground(xipColor);
		
		GridLayout gridLayout = new GridLayout();
		gridLayout.setRows(1);
		gridLayout.setVgap(5);
		this.setLayout(gridLayout);
//		this.setSize(319, 286);
		this.add(getJSubjectTabbedPane(), null);
		
		jSubjectTab = new JTable(subTabModel);
		jSubjectTab.setPreferredScrollableViewportSize(new Dimension(550,30));
		jSubjectTab.setForeground(Color.WHITE);
		jSubjectTab.setBackground(labelColor);
		jSubjectTab.setFillsViewportHeight(true);
		jSubjectTab.setShowGrid(true);	
		jSubjectTab.setFillsViewportHeight(true);
		jSubjectTab.getColumnModel().getColumn(0).setPreferredWidth(10);
		JScrollPane scrollSubPane=new JScrollPane(jSubjectTab);
		jSubjectTabbedPane.addTab("Annotation", scrollSubPane);

//		initialData();
	}
	
	private JTabbedPane getJSubjectTabbedPane() {
		if (jSubjectTabbedPane == null) {
			jSubjectTabbedPane = new JTabbedPane();
		}
		return jSubjectTabbedPane;
	}
	
	class AnnotationTableModel extends AbstractTableModel {
		String[] strArrayColumnNames = {
			"Reader",
			"Annotation Date",
			"Annotation Time",
			"Nodule Type",
			"Nodule Density",
			"Nodule Set",
			"Slice Thickness",
			"Session", 
       };    
		
		public int getColumnCount() {			
			return strArrayColumnNames.length;
		}

		public int getRowCount() {			
			return annotationList.size();
		}

		public String getValueAt(int rowIndex, int columnIndex) {
			switch( columnIndex ) {
				case 0:
                	return annotationList.get(rowIndex).getAnnotationReader();
                	
				case 1:
                	return annotationList.get(rowIndex).getAnnotationDate();
                //
				case 2:
                	return annotationList.get(rowIndex).getAnnotationTime();
                //
				case 3:
                	return annotationList.get(rowIndex).getNoduleType();
                //
				case 4:
                	return annotationList.get(rowIndex).getNoduleDensity();
	                //
				case 5:
                	return annotationList.get(rowIndex).getNoduleSet();
	                //
				case 6:
                	return annotationList.get(rowIndex).getSliceThickness();
	                //
				case 7:
                	return annotationList.get(rowIndex).getSession();
            }   
			
			return null;
		}
		
	
		@Override
		public String getColumnName( int col ) {
            return strArrayColumnNames[col];
        }
		
        public int getColumnWidth( int nCol ) {
            switch( nCol ) {
                case 0:
                    return 100;
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

	public List<String> getAimFileList() {
		List<String> aimFiles = new ArrayList<String>();
		
		return aimFiles;
	}

}
