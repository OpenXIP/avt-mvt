package com.siemens.cmiv.avt.mvt.ui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;

import com.siemens.cmiv.avt.mvt.datatype.FilterEntry;
import com.siemens.cmiv.avt.mvt.datatype.MeasurementEntry;

/**
 * @author Jie Zheng
 *
 */

public class SelectCriterialPanel extends JPanel{

	private static final long serialVersionUID = 1L; 
	private JList jUnselectList = null;
	private JList jSelectedList = null;
	private JButton jAddButton = null;
	private JButton jDelButton = null;
	private JLabel jLabel8 = null;
	private JLabel jLabel9 = null;
	private JButton jRunButton = null;
	private JScrollPane jScrollunListPane = null;
	private JScrollPane jScrollselectPane = null;
	
	private JButton jQueryButton = null;

	DefaultListModel unselectListModel = new DefaultListModel();
	DefaultListModel selectListModel = new DefaultListModel();
	
	FilterEntry filter = new FilterEntry();  
	MeasurementEntry flags = new MeasurementEntry(); 
	
	List<String>	gtIdentifiers = new ArrayList<String>();
	
	//get screen resolution
	private int screenHeight = (int)java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
	private int screenWidth = (int)java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
	private float heightProportion = 1;
	private float widthProportion = 1;
	
	// Comparison Field component
	private JComboBox jNominatedComboBox = null;
	
	// Partition Field component
	private JComboBox jAnalysisTypeComboBox = null;
	
	/**
	 * This is the default constructor
	 */
	public SelectCriterialPanel() {
		super();
		if (screenWidth < 1680)
			widthProportion = (float)(screenWidth / (float)1680);
		if (screenHeight < 1050)
			heightProportion = (float)(screenHeight / (float)1050);
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
	 */
	private void initialize() {		
		Color xipColor = new Color(51, 51, 102);
		
		this.setLayout(null);
		this.setBackground(xipColor);

		// add Analysis type Field
		TitledBorder border = BorderFactory.createTitledBorder("Analysis");
		border.setTitleFont(new Font("",Font.BOLD,getHeightValue(12)));
		border.setTitleColor(Color.WHITE);
		
		JPanel jAnaysisType = new JPanel();
		jAnaysisType.setBorder(border);
		jAnaysisType.setLayout(null);
		jAnaysisType.setBounds(new Rectangle(getWidthValue(0), getHeightValue(45), getWidthValue(350), getHeightValue(100)));
		jAnaysisType.setBackground(xipColor);
		
		JLabel jTypeLabel = new JLabel();
		jTypeLabel.setBounds(new Rectangle(getWidthValue(10), getHeightValue(24), getWidthValue(140), getHeightValue(17)));
		jTypeLabel.setFont(new Font("",Font.BOLD,getHeightValue(12)));
		jTypeLabel.setText("Type:");
		jTypeLabel.setBackground(xipColor);
		jTypeLabel.setForeground(Color.WHITE);
		jAnaysisType.add(jTypeLabel, null);
		
		jAnaysisType.add(getJAnalysisTypeComboBox(), null);
		
		// add analysis Field
		JLabel jNominatedLabel = new JLabel();
		jNominatedLabel.setBounds(new Rectangle(getWidthValue(10), getHeightValue(59), getWidthValue(143), getHeightValue(17)));
		jNominatedLabel.setFont(new Font("",Font.BOLD,getHeightValue(12)));
		jNominatedLabel.setText("Nominated GroundTruth:");
		jNominatedLabel.setBackground(xipColor);
		jNominatedLabel.setForeground(Color.WHITE);
		jAnaysisType.add(jNominatedLabel, null);
		
		jAnaysisType.add(getJNominatedComboBox(), null);
		
		// add Measurements Field
		border = BorderFactory.createTitledBorder("Measurements");
		border.setTitleFont(new Font("",Font.BOLD,getHeightValue(12)));
		border.setTitleColor(Color.WHITE);
		
		JPanel jMeasurements = new JPanel();
		jMeasurements.setBorder(border);
		jMeasurements.setLayout(null);
		jMeasurements.setBounds(new Rectangle(getWidthValue(0), getHeightValue(152), getWidthValue(350), getHeightValue(350)));
		jMeasurements.setBackground(xipColor);

		jMeasurements.add(getJAddButton(), null);
		jMeasurements.add(getJDelButton(), null);
		jMeasurements.add(getJScrollunListPane(), null);
		jMeasurements.add(getJScrollselectPane(), null);
		
		jLabel9 = new JLabel();
		jLabel9.setBounds(new Rectangle(getWidthValue(182), getHeightValue(26), getWidthValue(68), getHeightValue(21)));
		jLabel9.setFont(new Font("",Font.BOLD,getHeightValue(12)));
		jLabel9.setText("Selected:");
		jLabel9.setForeground(Color.WHITE);
		jLabel8 = new JLabel();
		jLabel8.setBounds(new Rectangle(getWidthValue(25), getHeightValue(26), getWidthValue(82), getHeightValue(19)));
		jLabel8.setFont(new Font("",Font.BOLD,getHeightValue(12)));
		jLabel8.setText("Unselected:");
		jLabel8.setForeground(Color.WHITE);		
		jMeasurements.add(jLabel8, null);
		jMeasurements.add(jLabel9, null);
		
		this.setSize(getWidthValue(350), getHeightValue(800));
		this.add(jMeasurements, null);
		this.add(getJRunButton(), null);
		this.add(jAnaysisType, null);
//		this.add(getJQueryButton(), null);
	}


	/**
	 * This method initializes jUnselectList	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JList getJUnselectList() {
		if (jUnselectList == null) {
			jUnselectList = new JList(unselectListModel);
			jUnselectList.setBounds(new Rectangle(getWidthValue(9), getHeightValue(48), getWidthValue(126), getHeightValue(139)));
			jUnselectList.setFont(new Font("", Font.BOLD, getHeightValue(12)));
			jUnselectList.setVisibleRowCount(8);
		}
		return jUnselectList;
	}

	/**
	 * This method initializes jSelectedList	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JList getJSelectedList() {
		if (jSelectedList == null) {
			selectListModel.addElement("RECIST");
			selectListModel.addElement("RECIST Difference");
			selectListModel.addElement("WHO");
			selectListModel.addElement("WHO Difference");
			selectListModel.addElement("Volume");
			selectListModel.addElement("Volume Difference");
			selectListModel.addElement("Relative VolumeDifference");
			selectListModel.addElement("Volume Overlap");
			selectListModel.addElement("Surface Distance(Average)");
			selectListModel.addElement("Surface Distance(RMS)");
			selectListModel.addElement("Surface Distance(Maximum)");
			jSelectedList = new JList(selectListModel);
			jSelectedList.setFont(new Font("", Font.BOLD, getHeightValue(12)));
			jSelectedList.setVisibleRowCount(8);
		}
		return jSelectedList;
	}

	/**
	 * This method initializes jAddButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJAddButton() {
		if (jAddButton == null) {
			jAddButton = new JButton();
			jAddButton.setBounds(new Rectangle(getWidthValue(70), getHeightValue(305), getWidthValue(55), getHeightValue(24)));
			jAddButton.setFont(new Font("", Font.BOLD, getHeightValue(12)));
			jAddButton.setText(">>");
			jAddButton.addMouseListener(new java.awt.event.MouseAdapter() {
				@Override
				public void mousePressed(java.awt.event.MouseEvent e) {
					int index = getJUnselectList().getSelectedIndex();
					if (index != -1){
						String item = (String) getJUnselectList().getSelectedValue();
						selectListModel.addElement(item);
						unselectListModel.remove(index);	
						if (item.compareToIgnoreCase("RECIST") == 0){
							flags.setElementFlag("NominalGT RECIST", true);
							flags.setElementFlag("Annotation RECIST", true);							
						}else if (item.compareToIgnoreCase("WHO") == 0){
							flags.setElementFlag("NominalGT WHO", true);
							flags.setElementFlag("Annotation WHO", true);							
						}else
							flags.setElementFlag(item, true);
					}
				}
			}
			);
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
			jDelButton.setBounds(new Rectangle(getWidthValue(227), getHeightValue(305), getWidthValue(55), getHeightValue(24)));
			jDelButton.setFont(new Font("", Font.BOLD, getHeightValue(12)));
			jDelButton.setText("<<");
			jDelButton.addMouseListener(new java.awt.event.MouseAdapter() {
				@Override
				public void mousePressed(java.awt.event.MouseEvent e) {
					int index = getJSelectedList().getSelectedIndex();
					if (index != -1){
						String item = (String) getJSelectedList().getSelectedValue();
						unselectListModel.addElement(item);
						selectListModel.remove(index);
						if (item.compareToIgnoreCase("RECIST") == 0){
							flags.setElementFlag("NominalGT RECIST", false);
							flags.setElementFlag("Annotation RECIST", false);							
						}else if (item.compareToIgnoreCase("WHO") == 0){
							flags.setElementFlag("NominalGT WHO", false);
							flags.setElementFlag("Annotation WHO", false);							
						}else
							flags.setElementFlag(item, false);
					}
				}
			}
			);
		}
		return jDelButton;
	}

	/**
	 * This method initializes jRunButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	public JButton getJRunButton() {
		if (jRunButton == null) {
			jRunButton = new JButton();
			jRunButton.setBounds(new Rectangle(getWidthValue(239), getHeightValue(690), getWidthValue(103), getHeightValue(30)));
			jRunButton.setFont(new Font("", Font.BOLD, getHeightValue(12)));
			jRunButton.setText("RUN");
		}
		return jRunButton;
	}

	/**
	 * This method initializes jScrollunListPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollunListPane() {
		if (jScrollunListPane == null) {
			jScrollunListPane = new JScrollPane();
			jScrollunListPane.setBounds(new Rectangle(getWidthValue(10), getHeightValue(50), getWidthValue(155), getHeightValue(235)));
			jScrollunListPane.setViewportView(getJUnselectList());
		}
		return jScrollunListPane;
	}

	/**
	 * This method initializes jScrollselectPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollselectPane() {
		if (jScrollselectPane == null) {
			jScrollselectPane = new JScrollPane();
			jScrollselectPane.setBounds(new Rectangle(getWidthValue(180), getHeightValue(50), getWidthValue(155), getHeightValue(235)));
			jScrollselectPane.setViewportView(getJSelectedList());
		}
		return jScrollselectPane;
	}

	public MeasurementEntry getMeasurements() {
		int nSize = selectListModel.getSize();
		
		if (nSize <= 0)
			return null;
		else
			return flags;
	}
	
	private JComboBox getJNominatedComboBox() {
		if (jNominatedComboBox == null) {
			jNominatedComboBox = new JComboBox();
			jNominatedComboBox.setBounds(new Rectangle(getWidthValue(154), getHeightValue(58), getWidthValue(183), getHeightValue(23)));
			jNominatedComboBox.setFont(new Font("",Font.BOLD,getHeightValue(12)));
			jNominatedComboBox.addItem("<choose one>");
		}
		return jNominatedComboBox;
	}
	
	private JComboBox getJAnalysisTypeComboBox() {
		if (jAnalysisTypeComboBox == null) {
			jAnalysisTypeComboBox = new JComboBox();
			jAnalysisTypeComboBox.setBounds(new Rectangle(getWidthValue(154), getHeightValue(20), getWidthValue(183), getHeightValue(23)));
			jAnalysisTypeComboBox.setFont(new Font("",Font.BOLD,getHeightValue(12)));
			jAnalysisTypeComboBox.addItem("Performance/Sources of Variation");
			jAnalysisTypeComboBox.addItem("Reader Variability");
			jAnalysisTypeComboBox.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					JComboBox cb = (JComboBox)e.getSource();
					String buffer = (String)cb.getSelectedItem();
					
					jNominatedComboBox.removeAllItems();
					if (buffer != null){
						if (buffer.compareTo("Performance/Sources of Variation") == 0){
							for (int i = 0; i < gtIdentifiers.size(); i++){
								jNominatedComboBox.addItem(gtIdentifiers.get(i));
							}
							if (gtIdentifiers.size() <= 0)
								jNominatedComboBox.addItem("<choose one>");
						}
						if (buffer.compareTo("Reader Variability") == 0){
							jNominatedComboBox.addItem("N/A");
						}
					}
				}
			});
		}
		return jAnalysisTypeComboBox;
	}
	
	public JButton getJQueryButton() {
		if (jQueryButton == null) {
			jQueryButton = new JButton();
			jQueryButton.setText("Query");
			jQueryButton.setBounds(new Rectangle(getWidthValue(120), getHeightValue(690), getWidthValue(103), getHeightValue(30)));
			jQueryButton.setFont(new Font("", Font.BOLD, getHeightValue(12)));
		}
		return jQueryButton;
	}
	
	public FilterEntry getFilterList() {
		// TODO Auto-generated method stub
		filter.setGender("ALL");
		filter.setAge(0);
		filter.setSliceThickness(0.0f);
		filter.setAnnotator("ALL");
		
		return filter;
	}
	
	public void setIdentifiers(List<String> names){
		getJNominatedComboBox().removeAllItems();
		gtIdentifiers.clear();
		
		for (int i = 0; i < names.size(); i++){
			getJNominatedComboBox().addItem(names.get(i));
			gtIdentifiers.add(names.get(i));
		}
	}
	public String getStudyType() {
		String buffer = (String)jAnalysisTypeComboBox.getSelectedItem();
		if (buffer != null){
			if (buffer.compareTo("Performance/Sources of Variation") == 0)
				buffer = "SOV";
			
			if (buffer.compareTo("Reader Variability") == 0)
				buffer = "MultipleReader";
		}
		return buffer;
	}
	public String getNominalGT() {
		String buffer = (String)jNominatedComboBox.getSelectedItem();
		return buffer;
	}
}  //  @jve:decl-index=0:visual-constraint="19,16"
