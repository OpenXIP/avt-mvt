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

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;

import org.jdom.JDOMException;

import com.siemens.cmiv.avt.mvt.outlier.OutlierManager;
import com.siemens.cmiv.avt.mvt.outlier.OutlierManagerFactory;
import com.siemens.cmiv.avt.mvt.outlier.OutlierManagerImpl;
import com.siemens.cmiv.avt.mvt.plot.PlotManager;
import com.siemens.cmiv.avt.mvt.plot.PlotManagerFactory;
import com.siemens.cmiv.avt.mvt.plot.PlotManagerImpl;
import com.siemens.cmiv.avt.mvt.statistic.StatisticManagerFactory;
import com.siemens.cmiv.avt.mvt.statistic.StatisticManagerImpl;
import com.siemens.cmiv.avt.mvt.statistic.StatisticManager;

/**
 * @author Jie Zheng
 *
 */

public class StatisticalAnalysisPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel jStatisticalPanel = null;
	private JButton jAddSAButton = null;
	private JButton jNewSAButton = null;
	private JButton jDelSAButton = null;
	private JScrollPane jSAScrollPane = null;
	private JTable jSATable = null;
	private JPanel jOAPanel = null;
	private JLabel jLabel0 = null;
	private JButton jAddOADataButton = null;
	private JButton jDelOADataButton = null;
	private JScrollPane jOADataScrollPane = null;
	private JTable jOADataTable = null;
	private JButton jRunButton = null;
	private JButton jBackButton = null;
	private JLabel jLabel2 = null;
	
	SATableModel saTabModel = new SATableModel();
	OATableModel oaTabModel = new OATableModel();
	PlotTableModel plotTabModel = new PlotTableModel();
	
	StatisticManager staMgr = new StatisticManagerImpl(); 
	OutlierManager outMgr = new OutlierManagerImpl();  //  @jve:decl-index=0:
	PlotManager PlotMgr = new PlotManagerImpl();  //  @jve:decl-index=0:

	StatisticalDesigner statisticalWnd = null;
	ScriptDes scriptWnd = null;
	PlottingDesigner plotWnd = null;
	
	Color xipColor = new Color(51, 51, 102);
	
	Outlier outlyingWnd = null;
	private JPanel jPlotPanel = null;
	private JButton jAddButton = null;
	private JButton jDelButton = null;
	private JLabel jLabel = null;
	private JScrollPane jPlotScrollPane = null;
	private JTable jPlotTable = null;
	private JButton jPlotButton = null;
	
	//get screen resolution
	private int screenHeight = (int)java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
	private int screenWidth = (int)java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
	private float heightProportion = 0;
	private float widthProportion = 0;

	// Partition Field component
	private JComboBox jAcquistionComboBox = null;
	private JComboBox jNoduleComboBox = null;
	private JComboBox jToolComboBox = null;
	private JComboBox jRepositingComboBox = null;
	private JComboBox jVersionComboBox = null;

	/**
	 * This is the default constructor
	 * @throws IOException 
	 * @throws JDOMException 
	 */
	public StatisticalAnalysisPanel() {
		super();
		heightProportion = (float)(screenHeight / (float)1050);
		widthProportion = (float)(screenWidth / (float)1680);
		initialize();
	}
	private int getHeightValue(int height){
		return (int)(height * heightProportion);
	}
	private int getWidthValue(int width){
		return (int)(width * widthProportion);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 * @throws IOException 
	 * @throws JDOMException 
	 */
	private void initialize(){
		jLabel2 = new JLabel();
		jLabel2.setBounds(new Rectangle(getWidthValue(6), getHeightValue(22), getWidthValue(59), getHeightValue(22)));
		jLabel2.setFont(new Font("",Font.BOLD,getHeightValue(12)));
		jLabel2.setText("Methods:");
		jLabel0 = new JLabel();
		jLabel0.setBounds(new Rectangle(getWidthValue(11), getHeightValue(19), getWidthValue(105), getHeightValue(22)));
		jLabel0.setFont(new Font("",Font.BOLD,getHeightValue(12)));
		jLabel0.setText("Threshold:");
		
		TitledBorder border;
		
		// add Partition Field
		border = BorderFactory.createTitledBorder("Partition");
		border.setTitleFont(new Font("",Font.BOLD,getHeightValue(12)));
		border.setTitleColor(Color.WHITE);
		
		JPanel jPartition = new JPanel();
		jPartition.setBorder(border);
		jPartition.setLayout(null);
		jPartition.setBounds(new Rectangle(getWidthValue(0), getHeightValue(157), getWidthValue(350), getHeightValue(170)));
		jPartition.setBackground(xipColor);
		
		JLabel jAcquLabel = new JLabel();
		jAcquLabel.setBounds(new Rectangle(getWidthValue(10), getHeightValue(24), getWidthValue(140), getHeightValue(17)));
		jAcquLabel.setFont(new Font("",Font.BOLD,getHeightValue(12)));
		jAcquLabel.setText("Acquisition parameters:");
		jAcquLabel.setBackground(xipColor);
		jAcquLabel.setForeground(Color.WHITE);
		jPartition.add(jAcquLabel, null);
		
		JLabel jNoduleLabel = new JLabel();
		jNoduleLabel.setBounds(new Rectangle(getWidthValue(10), getHeightValue(52), getWidthValue(140), getHeightValue(17)));
		jNoduleLabel.setFont(new Font("",Font.BOLD,getHeightValue(12)));
		jNoduleLabel.setText("Nodule characteristics:");
		jNoduleLabel.setBackground(xipColor);
		jNoduleLabel.setForeground(Color.WHITE);
		jPartition.add(jNoduleLabel, null);
		
		JLabel jToolLabel = new JLabel();
		jToolLabel.setBounds(new Rectangle(getWidthValue(10), getHeightValue(80), getWidthValue(140), getHeightValue(17)));
		jToolLabel.setFont(new Font("",Font.BOLD,getHeightValue(12)));
		jToolLabel.setText("Tool used for markup:");
		jToolLabel.setBackground(xipColor);
		jToolLabel.setForeground(Color.WHITE);
		jPartition.add(jToolLabel, null);
		
		JLabel jRepositingLabel = new JLabel();
		jRepositingLabel.setBounds(new Rectangle(getWidthValue(10), getHeightValue(108), getWidthValue(140), getHeightValue(17)));
		jRepositingLabel.setFont(new Font("",Font.BOLD,getHeightValue(12)));
		jRepositingLabel.setText("Location:");
		jRepositingLabel.setBackground(xipColor);
		jRepositingLabel.setForeground(Color.WHITE);
		jPartition.add(jRepositingLabel, null);
		
		JLabel jVersionLabel = new JLabel();
		jVersionLabel.setBounds(new Rectangle(getWidthValue(10), getHeightValue(136), getWidthValue(140), getHeightValue(17)));
		jVersionLabel.setFont(new Font("",Font.BOLD,getHeightValue(12)));
		jVersionLabel.setText("Version of seg. Algo:");
		jVersionLabel.setBackground(xipColor);
		jVersionLabel.setForeground(Color.WHITE);
		jPartition.add(jVersionLabel, null);
		
		jPartition.add(getJAcquistionComboBox(), null);
		jPartition.add(getJNoduleComboBox(), null);
		jPartition.add(getJToolComboBox(), null);
		jPartition.add(getJRepositingComboBox(), null);
		jPartition.add(getJVersionComboBox(), null);
	
		border = BorderFactory.createTitledBorder("Statistics Analysis");
		border.setTitleColor(Color.WHITE);
		border.setTitleFont(new Font("",Font.BOLD,getHeightValue(12)));
		
		jStatisticalPanel = new JPanel();
		jStatisticalPanel.setLayout(null);
		jStatisticalPanel.setBorder(border);
		jStatisticalPanel.setBackground(xipColor);		
		jStatisticalPanel.setBounds(new Rectangle(getWidthValue(8), getHeightValue(8), getWidthValue(295), getHeightValue(204)));

		jStatisticalPanel.add(getJAddSAButton(), null);
		jStatisticalPanel.add(getJNewSAButton(), null);
		jStatisticalPanel.add(getJDelSAButton(), null);
		jStatisticalPanel.add(getJSAScrollPane(), null);
		jLabel2.setForeground(Color.WHITE);
		jStatisticalPanel.add(jLabel2, null);
		
		border = BorderFactory.createTitledBorder("Outlier Analysis");
		border.setTitleColor(Color.WHITE);
		border.setTitleFont(new Font("",Font.BOLD,getHeightValue(12)));

		jOAPanel = new JPanel();
		jOAPanel.setLayout(null);
		jOAPanel.setBorder(border);
		jOAPanel.setBackground(xipColor);		
		jOAPanel.setBounds(new Rectangle(getWidthValue(7), getHeightValue(220), getWidthValue(292), getHeightValue(178)));
		
		jLabel0.setForeground(Color.WHITE);
		jOAPanel.add(jLabel0, null);
		jOAPanel.add(getJAddOADataButton(), null);
		jOAPanel.add(getJDelOADataButton(), null);
		jOAPanel.add(getJOADataScrollPane(), null);
		
		this.setFont(new Font("",Font.BOLD,getHeightValue(12)));
		this.setSize(getWidthValue(350), getHeightValue(800));
		this.setLayout(null);
		this.setBackground(xipColor);		
		this.add(jStatisticalPanel, null);
		this.add(jOAPanel, null);
		this.add(getJPlotButton(), null);
		this.add(getJRunButton(), null);
		this.add(getJBackButton(), null);
		this.add(getJPlotPanel(), null);
		
//		this.add(jPartition,null);
	
		loadStatistics();
		
		statisticalWnd = new StatisticalDesigner();
		statisticalWnd.setVisible(false);
		statisticalWnd.setTarget(this);
		
		scriptWnd = new ScriptDes();
		scriptWnd.setVisible(false);
		scriptWnd.setTarget(this);
		
		loadOutlier();
		
		outlyingWnd = new Outlier();
		outlyingWnd.setVisible(false);
		outlyingWnd.setTarget(this);

		loadPlot();
		plotWnd = new PlottingDesigner();
		plotWnd.setVisible(false);
		plotWnd.setTarget(this);
	
	}

	public void loadStatistics(){
		staMgr = StatisticManagerFactory.getInstance();    	
    	File xipStatisticConfig = new File("config/Statistics.xml");	
    	staMgr.loadStatistic(xipStatisticConfig);
    	
    	saTabModel.fireTableDataChanged();
	}
	
	public void loadOutlier(){
		outMgr = OutlierManagerFactory.getInstance();    	
		File xipOutlierConfig = new File("config/Outliers.xml");	
		outMgr.loadOutlier(xipOutlierConfig);
    	
    	oaTabModel.fireTableDataChanged();
	}
	
	public void loadPlot(){
		PlotMgr = PlotManagerFactory.getInstance();    	
		File xipPlotConfig = new File("config/Plotting.xml");	
		PlotMgr.loadPlot(xipPlotConfig);
    	
		plotTabModel.fireTableDataChanged();
	}

	/**
	 * This method initializes jAddSAButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJAddSAButton() {
		if (jAddSAButton == null) {
			jAddSAButton = new JButton();
			jAddSAButton.setBounds(new Rectangle(getWidthValue(62), getHeightValue(22), getWidthValue(70), getHeightValue(20)));
			jAddSAButton.setText("Add");
			jAddSAButton.setFont(new Font("",Font.BOLD,getHeightValue(12)));
			jAddSAButton.addMouseListener(new java.awt.event.MouseAdapter() {
				@Override
				public void mousePressed(java.awt.event.MouseEvent e) {
					if (statisticalWnd == null)
						return;
					statisticalWnd.setVisible(true);	
				}
			});
		}
		return jAddSAButton;
	}

	/**
	 * This method initializes jNewSAButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJNewSAButton() {
		if (jNewSAButton == null) {
			jNewSAButton = new JButton();
			jNewSAButton.setBounds(new Rectangle(getWidthValue(134), getHeightValue(22), getWidthValue(83), getHeightValue(20)));
			jNewSAButton.setText("Custom");
			jNewSAButton.setFont(new Font("",Font.BOLD,getHeightValue(12)));
			jNewSAButton.addMouseListener(new java.awt.event.MouseAdapter() {
				@Override
				public void mousePressed(java.awt.event.MouseEvent e) {
					if (scriptWnd == null)
						return;
					scriptWnd.setAlwaysOnTop(true);
					scriptWnd.setVisible(true);
					}
			});
		}
		return jNewSAButton;
	}

	/**
	 * This method initializes jDelSAButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJDelSAButton() {
		if (jDelSAButton == null) {
			jDelSAButton = new JButton();
			jDelSAButton.setBounds(new Rectangle(getWidthValue(218), getHeightValue(22), getWidthValue(66), getHeightValue(20)));
			jDelSAButton.setText("Del");
			jDelSAButton.setFont(new Font("",Font.BOLD,getHeightValue(12)));
			jDelSAButton.addMouseListener(new java.awt.event.MouseAdapter() {
				@Override
				public void mousePressed(java.awt.event.MouseEvent e) {
					delSelectSAItems();
					}
			});
		}
		return jDelSAButton;
	}

	/**
	 * This method initializes jSAScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJSAScrollPane() {
		if (jSAScrollPane == null) {
			jSAScrollPane = new JScrollPane();
			jSAScrollPane.setBounds(new Rectangle(getWidthValue(9), getHeightValue(51), getWidthValue(277), getHeightValue(137)));
			jSAScrollPane.setViewportView(getJSATable());
		}
		return jSAScrollPane;
	}

	/**
	 * This method initializes jSATable	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getJSATable() {
		if (jSATable == null) {
			jSATable = new JTable(saTabModel);
			jSATable.setPreferredScrollableViewportSize(new Dimension(getWidthValue(550),getHeightValue(30)));
			jSATable.setForeground(Color.BLACK);
			jSATable.setBackground(Color.WHITE);
			jSATable.setFillsViewportHeight(true);
			jSATable.setShowGrid(true);
		}
		return jSATable;
	}

	/**
	 * This method initializes jAddOADataButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJAddOADataButton() {
		if (jAddOADataButton == null) {
			jAddOADataButton = new JButton();
			jAddOADataButton.setBounds(new Rectangle(getWidthValue(136), getHeightValue(19), getWidthValue(70), getHeightValue(19)));
			jAddOADataButton.setText("Add");
			jAddOADataButton.setFont(new Font("",Font.BOLD,getHeightValue(12)));
			jAddOADataButton.addMouseListener(new java.awt.event.MouseAdapter() {   
				@Override
				public void mousePressed(java.awt.event.MouseEvent e) {
					if (outlyingWnd == null)
						return;
					outlyingWnd.setVisible(true);
					}
			});
		}
		return jAddOADataButton;
	}

	/**
	 * This method initializes jDelOADataButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJDelOADataButton() {
		if (jDelOADataButton == null) {
			jDelOADataButton = new JButton();
			jDelOADataButton.setBounds(new Rectangle(getWidthValue(208), getHeightValue(19), getWidthValue(70), getHeightValue(19)));
			jDelOADataButton.setText("Del");
			jDelOADataButton.setFont(new Font("",Font.BOLD,getHeightValue(12)));
			jDelOADataButton.addMouseListener(new java.awt.event.MouseAdapter() {   
				@Override
				public void mousePressed(java.awt.event.MouseEvent e) {
					delSelectOAItems();
				}
			});
		}
		return jDelOADataButton;
	}

	/**
	 * This method initializes jOADataScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJOADataScrollPane() {
		if (jOADataScrollPane == null) {
			jOADataScrollPane = new JScrollPane();
			jOADataScrollPane.setBounds(new Rectangle(getWidthValue(10), getHeightValue(45), getWidthValue(271), getHeightValue(119)));
			jOADataScrollPane.setViewportView(getJOADataTable());
		}
		return jOADataScrollPane;
	}

	/**
	 * This method initializes jOADataTable	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getJOADataTable() {
		if (jOADataTable == null) {
			jOADataTable = new JTable(oaTabModel);
			jOADataTable.setPreferredScrollableViewportSize(new Dimension(getWidthValue(550),getHeightValue(30)));
			jOADataTable.setForeground(Color.BLACK);
			jOADataTable.setBackground(Color.WHITE);
			jOADataTable.setFillsViewportHeight(true);
			jOADataTable.setShowGrid(true);
		}
		return jOADataTable;
	}

	/**
	 * This method initializes jRunButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	public JButton getJRunButton() {
		if (jRunButton == null) {
			jRunButton = new JButton();
			jRunButton.setBounds(new Rectangle(getWidthValue(108), getHeightValue(634), getWidthValue(93), getHeightValue(29)));
			jRunButton.setFont(new Font("",Font.BOLD,getHeightValue(12)));
			jRunButton.setText("RUN");
		}
		return jRunButton;
	}

	/**
	 * This method initializes jBackButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	public JButton getJBackButton() {
		if (jBackButton == null) {
			jBackButton = new JButton();
			jBackButton.setBounds(new Rectangle(getWidthValue(12), getHeightValue(634), getWidthValue(93), getHeightValue(29)));
			jBackButton.setFont(new Font("",Font.BOLD,getHeightValue(12)));
			jBackButton.setText("BACK");
		}
		return jBackButton;
	}
	
	@SuppressWarnings("serial")
	class SATableModel extends AbstractTableModel {
		String[] strArrayColumnNames = {
			"Comparison to be Analyzed",
			"Statistical Method",
			};    
		
		public int getColumnCount() {			
			return strArrayColumnNames.length;
		}

		public int getRowCount() {			
			return staMgr.getNumberOfStatisticEntries();
		}

		public Object getValueAt(int rowIndex, int columnIndex) {
			switch( columnIndex ) {
                //
				case 0:
                	return staMgr.getStatisticEntry(rowIndex).getStatisticData();
				//
				case 1:
					String name = staMgr.getStatisticEntry(rowIndex).getStatisticMethod();
					int index = name.indexOf(":");
					if (index != -1){
						String buf = name.substring(0, index);
						name = buf;
					}
                	return name;               	
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
                    return 150;
                case 1:
                    return 100;
                 default:
                    return 150;
            }
        }		
	}	
	
	@SuppressWarnings("serial")
	class OATableModel extends AbstractTableModel {
		String[] strArrayColumnNames = {
			"Comparison to be Analyzed",
			"Outlier Criteria",
			};    
		
		public int getColumnCount() {			
			return strArrayColumnNames.length;
		}

		public int getRowCount() {			
			return outMgr.getNumberOfOutlierEntries();
		}

		public Object getValueAt(int rowIndex, int columnIndex) {
			switch( columnIndex ) {
                //
				case 0:
                	return outMgr.getOutlierEntry(rowIndex).getCriteriaMeasurement();
				//
				case 1:
                	return outMgr.getOutlierEntry(rowIndex).getCriteriaScaling();                	
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
                    return 150;
                case 1:
                    return 100;
                 default:
                    return 150;
            }
        }		
	}

	public void updateStatisticsList() {
		// TODO Auto-generated method stub
		saTabModel.fireTableDataChanged();
	}	
	
	public boolean delSelectSAItems(){
		int[] index = jSATable.getSelectedRows();
		
		for (int i = 0; i < index.length; i++)
			staMgr.deleteStatisticEntry(index[i]);
		
		updateStatisticsList();
		return true;
	}
	
	public void updateOutlierList() {
		oaTabModel.fireTableDataChanged();
	}	
	
	public boolean delSelectOAItems(){
		int[] index = jOADataTable.getSelectedRows();
		
		for (int i = 0; i < index.length; i++)
			outMgr.deleteOutlierEntry(index[i]);
		
		updateOutlierList();
		return true;
	}
	
	public StatisticManager getStatiscManager(){
		return staMgr;
	}
	
	public OutlierManager getOutlierManager(){
		return outMgr;
	}
	
	public PlotManager getPlotManager(){
		return PlotMgr;
	}

	/**
	 * This method initializes jPlotPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */

	class PlotTableModel extends AbstractTableModel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		String[] strArrayColumnNames = {
			"Plotting title",
			"Chart",
			};    
		
		public int getColumnCount() {			
			return strArrayColumnNames.length;
		}

		public int getRowCount() {			
			return PlotMgr.getNumberOfPlotEntries();
		}

		public Object getValueAt(int rowIndex, int columnIndex) {
			switch( columnIndex ) {
                //
				case 0:
                	return PlotMgr.getPlotEntry(rowIndex).getPlotTitle();
				//
				case 1:
                	return PlotMgr.getPlotEntry(rowIndex).getPlotChart();                	
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
                    return 150;
                case 1:
                    return 100;
                 default:
                    return 150;
            }
        }		
	}

	private JPanel getJPlotPanel() {
		if (jPlotPanel == null) {
			jLabel = new JLabel();
			jLabel.setFont(new Font("",Font.BOLD,getHeightValue(12)));
			jLabel.setText("Charts:");
			jLabel.setBounds(new Rectangle(getWidthValue(9), getHeightValue(18), getWidthValue(99), getHeightValue(20)));
			jLabel.setForeground(Color.WHITE);
			
			jPlotPanel = new JPanel();
			jPlotPanel.setLayout(null);
			jPlotPanel.setBounds(new Rectangle(getWidthValue(9), getHeightValue(407), getWidthValue(291), getHeightValue(189)));
			
			TitledBorder border;
			border = BorderFactory.createTitledBorder("Plotting");
			border.setTitleColor(Color.WHITE);
			border.setTitleFont(new Font("",Font.BOLD,getHeightValue(12)));

			jPlotPanel.setBorder(border);
			jPlotPanel.setBackground(xipColor);		
			
			jPlotPanel.add(getJAddButton(), null);
			jPlotPanel.add(getJDelButton(), null);
			jPlotPanel.add(jLabel, null);
			jPlotPanel.add(getJPlotScrollPane(), null);
//			jPlotPanel.add(getJPlotButton(), null);
		}
		return jPlotPanel;
	}

	/**
	 * This method initializes jAddButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJAddButton() {
		if (jAddButton == null) {
			jAddButton = new JButton();
			jAddButton.setBounds(new Rectangle(getWidthValue(136), getHeightValue(18), getWidthValue(70), getHeightValue(19)));
			jAddButton.setFont(new Font("",Font.BOLD,getHeightValue(12)));
			jAddButton.setText("Add");
			jAddButton.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mousePressed(java.awt.event.MouseEvent e) {
					if (plotWnd != null)
						plotWnd.setVisible(true);
				}
			});
		}
		return jAddButton;
	}

	/**
	 * This method initializes jDelButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJDelButton() {
		if (jDelButton == null) {
			jDelButton = new JButton();
			jDelButton.setBounds(new Rectangle(getWidthValue(208), getHeightValue(18), getWidthValue(70), getHeightValue(19)));
			jDelButton.setFont(new Font("",Font.BOLD,getHeightValue(12)));
			jDelButton.setText("Del");
			jDelButton.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mousePressed(java.awt.event.MouseEvent e) {
					delSelectPlotItems();
				}
			});
		}
		return jDelButton;
	}

	/**
	 * This method initializes jPlotScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJPlotScrollPane() {
		if (jPlotScrollPane == null) {
			jPlotScrollPane = new JScrollPane();
			jPlotScrollPane.setBounds(new Rectangle(getWidthValue(10), getHeightValue(46), getWidthValue(271), getHeightValue(125)));
			jPlotScrollPane.setViewportView(getJPlotTable());
		}
		return jPlotScrollPane;
	}

	/**
	 * This method initializes jPlotTable	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getJPlotTable() {
		if (jPlotTable == null) {
			jPlotTable = new JTable(plotTabModel);
			jPlotTable.setPreferredScrollableViewportSize(new Dimension(getWidthValue(550),getHeightValue(30)));
			jPlotTable.setForeground(Color.BLACK);
			jPlotTable.setBackground(Color.WHITE);
			jPlotTable.setFillsViewportHeight(true);
			jPlotTable.setShowGrid(true);
		}
		return jPlotTable;
	}

	/**
	 * This method initializes jPlotButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	public JButton getJPlotButton() {
		if (jPlotButton == null) {
			jPlotButton = new JButton();
			jPlotButton.setFont(new Font("",Font.BOLD,getHeightValue(12)));
			jPlotButton.setText("PLOT");
//			jPlotButton.setBounds(new Rectangle(getWidthValue(182), getHeightValue(151), getWidthValue(93), getHeightValue(26)));
			jPlotButton.setBounds(new Rectangle(getWidthValue(204), getHeightValue(634), getWidthValue(93), getHeightValue(29)));
		}
		return jPlotButton;
	}
	
	public void updatePlotList() {
		plotTabModel.fireTableDataChanged();
	}	
	
	public boolean delSelectPlotItems(){
		int[] index = jPlotTable.getSelectedRows();
		
		for (int i = 0; i < index.length; i++)
			PlotMgr.deletePlotEntry(index[i]);
		
		updatePlotList();
		return true;
	}
	private JComboBox getJAcquistionComboBox() {
		if (jAcquistionComboBox == null) {
			jAcquistionComboBox = new JComboBox();
			jAcquistionComboBox.setFont(new Font("",Font.BOLD,getHeightValue(12)));
			jAcquistionComboBox.setBounds(new Rectangle(getWidthValue(154), getHeightValue(20), getWidthValue(183), getHeightValue(23)));
			jAcquistionComboBox.setFont(new Font("",Font.BOLD,getHeightValue(12)));
			jAcquistionComboBox.addItem("Exposure");
			jAcquistionComboBox.addItem("Pitch");
			jAcquistionComboBox.addItem("Slice Thickness");
			jAcquistionComboBox.addItem("Reconstruction Kernel");
		}
		return jAcquistionComboBox;
	}
	
	private JComboBox getJNoduleComboBox() {
		if (jNoduleComboBox == null) {
			jNoduleComboBox = new JComboBox();
			jNoduleComboBox.setFont(new Font("",Font.BOLD,getHeightValue(12)));
			jNoduleComboBox.setBounds(new Rectangle(getWidthValue(154), getHeightValue(48), getWidthValue(183), getHeightValue(23)));
			jNoduleComboBox.setFont(new Font("",Font.BOLD,getHeightValue(12)));
			jNoduleComboBox.addItem("Attached");
			jNoduleComboBox.addItem("Unattached");
			jNoduleComboBox.addItem("Spherical");
			jNoduleComboBox.addItem("Elliptical");
			jNoduleComboBox.addItem("Lobulated");
			jNoduleComboBox.addItem("Spiculated");
			jNoduleComboBox.addItem("Random");
		}
		return jNoduleComboBox;
	}
	
	private JComboBox getJToolComboBox() {
		if (jToolComboBox == null) {
			jToolComboBox = new JComboBox();
			jToolComboBox.setFont(new Font("",Font.BOLD,getHeightValue(12)));
			jToolComboBox.setBounds(new Rectangle(getWidthValue(154), getHeightValue(76), getWidthValue(183), getHeightValue(23)));
			jToolComboBox.setFont(new Font("",Font.BOLD,getHeightValue(12)));
			jToolComboBox.addItem("AVT Image Annotation Tool");
		}
		return jToolComboBox;
	}
	private JComboBox getJRepositingComboBox() {
		if (jRepositingComboBox == null) {
			jRepositingComboBox = new JComboBox();
			jRepositingComboBox.setFont(new Font("",Font.BOLD,getHeightValue(12)));
			jRepositingComboBox.setBounds(new Rectangle(getWidthValue(154), getHeightValue(104), getWidthValue(183), getHeightValue(23)));
			jRepositingComboBox.setFont(new Font("",Font.BOLD,getHeightValue(12)));
			jRepositingComboBox.addItem("abdomen");
			jRepositingComboBox.addItem("head");
			jRepositingComboBox.addItem("lower extremity");
			jRepositingComboBox.addItem("neck");
			jRepositingComboBox.addItem("nervous system");
			jRepositingComboBox.addItem("spine");
			jRepositingComboBox.addItem("thorax");
			jRepositingComboBox.addItem("trunk");
			jRepositingComboBox.addItem("upper extremity");
			jRepositingComboBox.addItem("blood vessel");
		}
		return jRepositingComboBox;
	}
	private JComboBox getJVersionComboBox() {
		if (jVersionComboBox == null) {
			jVersionComboBox = new JComboBox();
			jVersionComboBox.setFont(new Font("",Font.BOLD,getHeightValue(12)));
			jVersionComboBox.setBounds(new Rectangle(getWidthValue(154), getHeightValue(132), getWidthValue(183), getHeightValue(23)));
			jVersionComboBox.setFont(new Font("",Font.BOLD,getHeightValue(12)));
			jVersionComboBox.addItem("n/a");
		}
		return jVersionComboBox;
	}

}  //  @jve:decl-index=0:visual-constraint="-10,-23"
