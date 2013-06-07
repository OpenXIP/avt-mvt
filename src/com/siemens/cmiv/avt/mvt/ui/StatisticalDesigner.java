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

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.SwingUtilities;
import javax.swing.JPanel;
import javax.swing.JList; 
import javax.swing.JFrame;
import javax.swing.border.TitledBorder;
import javax.swing.DefaultListModel; 
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.ScrollPaneConstants;

import com.siemens.cmiv.avt.mvt.statistic.StatisticManagerFactory;
import com.siemens.cmiv.avt.mvt.statistic.StatisticEntry;
import com.siemens.cmiv.avt.mvt.statistic.StatisticManager;

public class StatisticalDesigner extends JFrame implements ActionListener, ItemListener{
	
	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JPanel jPanel1 = null;
	private JLabel jLabel1 = null;
	private JScrollPane jScrollPane1 = null;
	private JList list1 = null;
	private JList list2 = null;
	private JLabel jLabel2 = null;
	private JButton jButton1 = null;
	private JButton jButton2 = null;
	private JScrollPane jScrollPane2 = null;
	private JPanel jPanel2 = null;
	private JComboBox jComboBox2 = null;
	private JButton jButton3 = null;
	private JButton jButton4 = null;
	DefaultListModel listModel = new DefaultListModel(); 
	DefaultListModel listModel2 = new DefaultListModel();
	String change1 = null;
	String change2 = null;
	
	Color xipColor = new Color(51, 51, 102);
	
	StatisticalAnalysisPanel targetPanel = null;
	
	private JPanel jPanelMultiRegression = null;
	private JPanel jPanelDependent = null;
	private JComboBox jCmbDependent = null;
	
	private JScrollPane jPanelIndependent = null;
	private JComboBox jCmbInDependent0 = null;
	private JComboBox jCmbInDependent1 = null;
	private JComboBox jCmbInDependent2 = null;
	private JComboBox jCmbInDependent3 = null;
	private JComboBox jCmbInDependent4 = null;
	private JComboBox jCmbInDependent5 = null;
	private JComboBox jCmbInDependent6 = null;

	private JPanel jPanelAnova = null;
	private JCheckBox jCheckBox = null;
	private boolean bMultipleData = true;

	private String [] Measurement_Label = {"NominalGT RECIST", "Annotation RECIST", "RECIST Difference", "NominalGT WHO", "Annotation WHO",
			"WHO Difference", "NominalGT Volume", "Annotation Volume", "Volume Difference", "Relative VolumeDifference", "Surface Distance(Average)",
			"Surface Distance(RMS)", "Surface Distance(Maximum)", "Volume Overlap"};
	
//	private String [] Independent_Vars = {"NA", "Nodule Type", "Nodule Desity", "Nodule Set", "Slice Thickness", "Session", "Measurement Method", "Reader"};
	private String [] Independent_Vars = {"NA", "Gender", "Exposure", "Pitch", "Collimation", "Reconstruction kernel", "Slice Thickness"};

	public StatisticalDesigner(){
		// TODO Auto-generated constructor stub
		initialize();
	}

	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(308, 19, 70, 22));
			jLabel2.setText("Selected");
			jLabel2.setForeground(Color.WHITE);
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(9, 19, 70, 22));
			jLabel1.setText("Unselected");
			jLabel1.setForeground(Color.WHITE);
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.setBackground(xipColor);
			TitledBorder border = BorderFactory.createTitledBorder("Data Selection");
			border.setTitleColor(Color.WHITE);
			jPanel1.setBorder(border);
			jPanel1.setBounds(new Rectangle(5, 93, 539, 221));
			jPanel1.add(jLabel1, null);
			jPanel1.add(getJScrollPane1(), null);
			jPanel1.add(jLabel2, null);
			jPanel1.add(getJButton1(), null);
			jPanel1.add(getJButton2(), null);
			jPanel1.add(getJScrollPane2(), null);
		}
		return jPanel1;
	}
	
	private JPanel getMultipleRegressionJPanel() {
		if (jPanelMultiRegression == null) {
			jPanelMultiRegression = new JPanel();
			jPanelMultiRegression.setLayout(null);
			jPanelMultiRegression.setBackground(xipColor);
			
			TitledBorder border = BorderFactory.createTitledBorder("Data Selection");
			border.setTitleColor(Color.WHITE);
			jPanelMultiRegression.setBorder(border);
			jPanelMultiRegression.setBounds(new Rectangle(5, 93, 539, 221));
			
			jPanelMultiRegression.add(getJPanelDependent(), null);
			jPanelMultiRegression.add(getJScrollDependent(), null);
		}
		return jPanelMultiRegression;
	}

	private JPanel getJPanelDependent() {
		if (jPanelDependent == null) {
			jPanelDependent = new JPanel();
			jPanelDependent.setLayout(null);
			jPanelDependent.setBounds(new Rectangle(4, 19, 522, 57));
			jPanelDependent.setBackground(xipColor);
			TitledBorder border = BorderFactory.createTitledBorder("Dependent variable");
			border.setTitleColor(Color.WHITE);
			jPanelDependent.setBorder(border);
			jPanelDependent.add(getJCmbDependent(), null);
//			jPanelDependent.add(getJCheckBox(), null);
		}
		return jPanelDependent;
	}
	
	private JCheckBox getJCheckBox() {
		if (jCheckBox == null) {
			jCheckBox = new JCheckBox();
			jCheckBox.setBounds(new Rectangle(309, 20, 128, 26));
			jCheckBox.setText("Multiple Data");
			jCheckBox.setForeground(Color.WHITE);
			jCheckBox.setBackground(xipColor);
			jCheckBox.setSelected(false);
			jCheckBox.addItemListener(this);
		}
		return jCheckBox;
	}
	
	private JComboBox getJCmbDependent() {
		if (jCmbDependent == null) {
			jCmbDependent = new JComboBox(Measurement_Label);
			jCmbDependent.setBorder(BorderFactory.createMatteBorder(1, 1,
			        1, 1, Color.WHITE));
			jCmbDependent.setBounds(new Rectangle(9, 20, 198, 26));
			jCmbDependent.setBackground(Color.WHITE);
		}
		return jCmbDependent;
	}
	
	private JScrollPane getJScrollDependent() {
		if (jPanelIndependent == null) {
			jPanelIndependent = new JScrollPane();
			TitledBorder border = BorderFactory.createTitledBorder("Independent variables");
			border.setTitleColor(Color.WHITE);
			jPanelIndependent.setBorder(border);
			jPanelIndependent.setBackground(xipColor);

			jPanelIndependent.setBounds(new Rectangle(8, 83, 523, 135));
			jPanelIndependent.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			
			JPanel cmbPanel = new JPanel();
			cmbPanel.setBackground(xipColor);
			cmbPanel.setBounds(8, 83, 523, 320);
			cmbPanel.setLayout(new BoxLayout(cmbPanel, BoxLayout.PAGE_AXIS));
			cmbPanel.add(Box.createVerticalGlue());
			
			cmbPanel.add(getJCmbIndpendent0(), null);
			cmbPanel.add(Box.createVerticalGlue());
			cmbPanel.add(getJCmbIndpendent1(), null);
			cmbPanel.add(Box.createVerticalGlue());
			cmbPanel.add(getJCmbIndpendent2(), null);
//			cmbPanel.add(Box.createVerticalGlue());
//			cmbPanel.add(getJCmbIndpendent3(), null);
//			cmbPanel.add(Box.createVerticalGlue());
//			cmbPanel.add(getJCmbIndpendent4(), null);
//			cmbPanel.add(Box.createVerticalGlue());
//			cmbPanel.add(getJCmbIndpendent5(), null);
//			cmbPanel.add(Box.createVerticalGlue());
//			cmbPanel.add(getJCmbIndpendent6(), null);

			jPanelIndependent.setViewportView(cmbPanel);
		}
		return jPanelIndependent;
	}

	private JComboBox getJCmbIndpendent0() {
		if (jCmbInDependent0 == null) {
			jCmbInDependent0 = new JComboBox(Independent_Vars);
			jCmbInDependent0.setBorder(BorderFactory.createMatteBorder(1, 1,
			        1, 1, Color.WHITE));
			jCmbInDependent0.setBounds(new Rectangle(9, 20, 198, 26));
			jCmbInDependent0.setBackground(Color.WHITE);
			jCmbInDependent0.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {

				}
			});
		}
		return jCmbInDependent0;
	}
	
	private JComboBox getJCmbIndpendent1() {
		if (jCmbInDependent1 == null) {
			jCmbInDependent1 = new JComboBox(Independent_Vars);
			jCmbInDependent1.setBorder(BorderFactory.createMatteBorder(1, 1,
			        1, 1, Color.WHITE));
			jCmbInDependent1.setBounds(new Rectangle(9, 40, 198, 26));
			jCmbInDependent1.setBackground(Color.WHITE);
		}
		return jCmbInDependent1;
	}
	
	private JComboBox getJCmbIndpendent2() {
		if (jCmbInDependent2 == null) {
			jCmbInDependent2 = new JComboBox(Independent_Vars);
			jCmbInDependent2.setBorder(BorderFactory.createMatteBorder(1, 1,
			        1, 1, Color.WHITE));
			jCmbInDependent2.setBounds(new Rectangle(9, 40, 198, 26));
			jCmbInDependent2.setBackground(Color.WHITE);
		}
		return jCmbInDependent2;
	}

	private JComboBox getJCmbIndpendent3() {
		if (jCmbInDependent3 == null) {
			jCmbInDependent3 = new JComboBox(Independent_Vars);
			jCmbInDependent3.setBorder(BorderFactory.createMatteBorder(1, 1,
			        1, 1, Color.WHITE));
			jCmbInDependent3.setBounds(new Rectangle(9, 40, 198, 26));
			jCmbInDependent3.setBackground(Color.WHITE);
		}
		return jCmbInDependent3;
	}

	private JComboBox getJCmbIndpendent4() {
		if (jCmbInDependent4 == null) {
			jCmbInDependent4 = new JComboBox(Independent_Vars);
			jCmbInDependent4.setBorder(BorderFactory.createMatteBorder(1, 1,
			        1, 1, Color.WHITE));
			jCmbInDependent4.setBounds(new Rectangle(9, 40, 198, 26));
			jCmbInDependent4.setBackground(Color.WHITE);
		}
		return jCmbInDependent4;
	}

	private JComboBox getJCmbIndpendent5() {
		if (jCmbInDependent5 == null) {
			jCmbInDependent5 = new JComboBox(Independent_Vars);
			jCmbInDependent5.setBorder(BorderFactory.createMatteBorder(1, 1,
			        1, 1, Color.WHITE));
			jCmbInDependent5.setBounds(new Rectangle(9, 40, 198, 26));
			jCmbInDependent5.setBackground(Color.WHITE);
		}
		return jCmbInDependent5;
	}

	private JComboBox getJCmbIndpendent6() {
		if (jCmbInDependent6 == null) {
			jCmbInDependent6 = new JComboBox(Independent_Vars);
			jCmbInDependent1.setBorder(BorderFactory.createMatteBorder(1, 1,
			        1, 1, Color.WHITE));
			jCmbInDependent6.setBounds(new Rectangle(9, 40, 198, 26));
			jCmbInDependent6.setBackground(Color.WHITE);
		}
		return jCmbInDependent6;
	}

	/**
	 * This method initializes jScrollPane1	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setBorder(BorderFactory.createMatteBorder(1, 1,
			        1, 1, Color.WHITE));
			jScrollPane1.setBounds(new Rectangle(9, 43, 205, 169));
			jScrollPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
			jScrollPane1.setViewportView(getJList1());
			jScrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		}
		return jScrollPane1;
	}
	
	private JList getJList1(){ 
	if (list1 == null)	{ 
		list1 = new JList(); 
		for (int i = 0; i < Measurement_Label.length; i++)
			listModel.addElement(Measurement_Label[i]); 

		list1.setModel(listModel); 
	
		list1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
			public void valueChanged(javax.swing.event.ListSelectionEvent e) {
			}
		});
		} 
	
		return list1; 
	} 

	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(new Rectangle(233, 87, 52, 25));
			jButton1.setText(">>");
			jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
				@Override
				public void mouseClicked(java.awt.event.MouseEvent e) {
  				    change1 = (String)list1.getSelectedValue(); 
  				    if (change1 != null){
						listModel.removeElement(change1); 
						listModel2.addElement(change1);
  				    }
				}
			});
		}
		return jButton1;
	}

	/**
	 * This method initializes jButton2	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setBounds(new Rectangle(234, 136, 52, 23));
			jButton2.setText("<<");
			jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
				@Override
				public void mouseClicked(java.awt.event.MouseEvent e) {
					change2 = (String)list2.getSelectedValue(); 
					if (change2 != null){
					listModel2.removeElement(change2);
					listModel.addElement(change2);
					}
				}
			});
		}
		return jButton2;
	}

	/**
	 * This method initializes jScrollPane2	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setBorder(BorderFactory.createMatteBorder(1, 1,
			        1, 1, Color.WHITE));
			jScrollPane2.setBounds(new Rectangle(308, 43, 209, 169));
			jScrollPane2.setViewportView(getJList2());
			jScrollPane2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
			jScrollPane2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		}
		return jScrollPane2;
	}
	
	private JList getJList2(){ 
		if (list2 == null){ 
			list2 = new JList(); 
			list2.setModel(listModel2); 
		
			list2.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
				public void valueChanged(javax.swing.event.ListSelectionEvent e) {
				}
			});
		} 
	
		return list2 ; 
	} 

	/**
	 * This method initializes jPanel2	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jPanel2.setBounds(new Rectangle(4, 19, 540, 57));
			jPanel2.setBackground(xipColor);
			TitledBorder border = BorderFactory.createTitledBorder("Statistic Methods");
			border.setTitleColor(Color.WHITE);
			jPanel2.setBorder(border);
			jPanel2.add(getJComboBox2(), null);
		}
		return jPanel2;
	}

	/**
	 * This method initializes jComboBox2	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboBox2() {
		if (jComboBox2 == null) {
			String labels[] = {"Mean","SD","Multiple Regression", "N-way ANOVA"};
			jComboBox2 = new JComboBox(labels);
			jComboBox2.setBorder(BorderFactory.createMatteBorder(1, 1,
			        1, 1, Color.WHITE));
			jComboBox2.setBounds(new Rectangle(9, 20, 198, 26));
			jComboBox2.setBackground(Color.WHITE);
			jComboBox2.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					int item = jComboBox2.getSelectedIndex();
					switch (item){
					case 0:
					case 1:
						getMultipleRegressionJPanel().setVisible(false);
						getJPanel1().setVisible(true);
						break;
						
					case 2:
						getJPanel1().setVisible(false);
						getMultipleRegressionJPanel().setVisible(true);
						setComBoxValues(Independent_Vars, null);
						break;
						
					case 3:
						getJPanel1().setVisible(false);
						getMultipleRegressionJPanel().setVisible(true);
						setComBoxValues(Measurement_Label, "NA");
						break;
					}
				}
			});
		}
		return jComboBox2;
	}

	private void setComBoxValues(String []values, String data){
		getJCmbIndpendent0().removeAllItems();
		getJCmbIndpendent1().removeAllItems();
		getJCmbIndpendent2().removeAllItems();
		
		if (data != null){
			getJCmbIndpendent0().addItem(data);
			getJCmbIndpendent1().addItem(data);
			getJCmbIndpendent2().addItem(data);
			
		}
		
		for (int i = 0; i < values.length; i++){
			getJCmbIndpendent0().addItem(values[i]);
			getJCmbIndpendent1().addItem(values[i]);
			getJCmbIndpendent2().addItem(values[i]);
		}
	}
	/**
	 * This method initializes jButton3	
	 * 	
	 * @return javax.swing.JButton	
	 */
	JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setBounds(new Rectangle(368, 373, 71, 25));
			jButton3.setText("Done");
			jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
				@Override
				public void mousePressed(java.awt.event.MouseEvent e) {
					try{
						StatisticManager staMgr = StatisticManagerFactory.getInstance();
						
						int item = jComboBox2.getSelectedIndex();
						switch(item){
						case 0:
						case 1:
						{
							int nSize = listModel2.getSize();
							if (nSize <= 0)
								return;
							
							String buffer = (String) getJComboBox2().getSelectedItem();
							for (int i = 0; i < nSize; i++){
								StatisticEntry content = new StatisticEntry();
								content.setStatisticData((String) listModel2.get(i));
								content.setStatisticMethod(buffer);
								staMgr.addStatisticEntry(content);
							}
						}
						break;
							
						case 2:
						case 3:
						{
							String buffer = (String) getJComboBox2().getSelectedItem();
							StatisticEntry content = new StatisticEntry();
							content.setStatisticMethod(buffer);
							buffer = getJCmbDependent().getSelectedItem().toString();
							if (!buffer.isEmpty())
								content.setStatisticData(buffer);
							
							buffer = getJCmbIndpendent0().getSelectedItem().toString();
							if (!buffer.isEmpty() && buffer.compareToIgnoreCase("NA")!=0)
								content.addStatisticData(buffer);
							
							buffer = getJCmbIndpendent1().getSelectedItem().toString();
							if (!buffer.isEmpty() && buffer.compareToIgnoreCase("NA")!=0)
								content.addStatisticData(buffer);
							
							buffer = getJCmbIndpendent2().getSelectedItem().toString();
							if (!buffer.isEmpty() && buffer.compareToIgnoreCase("NA")!=0)
								content.addStatisticData(buffer);
							
//							buffer = getJCmbIndpendent3().getSelectedItem().toString();
//							if (!buffer.isEmpty() && buffer.compareToIgnoreCase("NA")!=0)
//								content.addStatisticData(buffer);
//							
//							buffer = getJCmbIndpendent4().getSelectedItem().toString();
//							if (!buffer.isEmpty() && buffer.compareToIgnoreCase("NA")!=0)
//								content.addStatisticData(buffer);
//							
//							buffer = getJCmbIndpendent5().getSelectedItem().toString();
//							if (!buffer.isEmpty() && buffer.compareToIgnoreCase("NA")!=0)
//								content.addStatisticData(buffer);
//							
//							buffer = getJCmbIndpendent6().getSelectedItem().toString();
//							if (!buffer.isEmpty() && buffer.compareToIgnoreCase("NA")!=0)
//								content.addStatisticData(buffer);
							
							content.setBMultipleData(bMultipleData);
							
							staMgr.addStatisticEntry(content);						
						}
						break;
						}
						
						if (targetPanel != null)
							targetPanel.updateStatisticsList();
						
					}catch (IllegalArgumentException e1){
						return;
					}
								
					dispose();
				}
			});
		}
		return jButton3;
	}

	/**
	 * This method initializes jButton4	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton4() {
		if (jButton4 == null) {
			jButton4 = new JButton();
			jButton4.setBounds(new Rectangle(464, 373, 70, 25));
			jButton4.setText("Reset");
			jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
				@Override
				public void mousePressed(java.awt.event.MouseEvent e) {
					listModel.clear();
					listModel2.clear();
					
					for (int i = 0; i < Measurement_Label.length; i++)
						listModel2.addElement(Measurement_Label[i]); 
					
					getJCmbIndpendent0().setSelectedIndex(0);
					getJCmbIndpendent1().setSelectedIndex(0);
					getJCmbIndpendent2().setSelectedIndex(0);
//					getJCmbIndpendent3().setSelectedIndex(0);
//					getJCmbIndpendent4().setSelectedIndex(0);
//					getJCmbIndpendent5().setSelectedIndex(0);
//					getJCmbIndpendent6().setSelectedIndex(0);
					
					getJCmbDependent().setSelectedIndex(0);
				}
			});
		}
		return jButton4;
	}

	public void setSelectedItems(List<String> items){
		listModel2.clear();
		
		for (int i = 0; i < items.size(); i++){
			listModel2.addElement(items.get(i));
		}
	}
	
	public void setUnSelectedItems(List<String> items){
		listModel.clear();
		
		for (int i = 0; i < items.size(); i++){
			listModel.addElement(items.get(i));
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				StatisticalDesigner thisClass = new StatisticalDesigner();
				thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				thisClass.setVisible(true);
			}
		});
		
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(560, 440);
		this.setTitle("Statistic Designer");
		this.add(getJContentPane());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.setBackground(xipColor);
			jContentPane.add(getJPanel1(), null);
			jContentPane.add(getJButton3(),null);
			jContentPane.add(getJButton4(), null);
			jContentPane.add(getJPanel2(), null);
			
			jContentPane.add(getMultipleRegressionJPanel(), null);
			getMultipleRegressionJPanel().setVisible(false);
		}
		jContentPane.setVisible(true);
		return jContentPane;
	}
	
	public void setTarget(StatisticalAnalysisPanel target){
		this.targetPanel = target;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		Object source = e.getItemSelectable();
		
		if (source == jCheckBox){
			bMultipleData = !bMultipleData;
		}
	}
}  //  @jve:decl-index=0:visual-constraint="17,56"
