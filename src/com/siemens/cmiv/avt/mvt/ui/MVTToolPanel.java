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
import java.awt.Font;
import java.awt.Rectangle;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

/**
 * @author Jie Zheng
 *
 */

public class MVTToolPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	SelectCriterialPanel criteriaPanel = new SelectCriterialPanel();
	StatisticalAnalysisPanel statiscalPanel = new StatisticalAnalysisPanel();  //  @jve:decl-index=0:visual-constraint="349,10"

	Color xipColor = new Color(51, 51, 102);
	Color textColor = new Color(212, 213, 234);  //  @jve:decl-index=0:
	Color labelColor = new Color(0, 153, 153);

	private JProgressBar jSelectionProgressBar = null;
	//get screen resolution
	private int screenHeight = (int)java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
	private int screenWidth = (int)java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
	private float heightProportion = 1;
	private float widthProportion = 1;
	/**
	 * This is the default constructor
	 */
	public MVTToolPanel() {
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
		
		this.setSize(getWidthValue(350), getHeightValue(963));
		this.setLayout(null);
		setBackground(xipColor);
		
		statiscalPanel.setBounds(new Rectangle(getWidthValue(0), getHeightValue(150), getWidthValue(350), getHeightValue(750)));
		statiscalPanel.setVisible(false);
//		statiscalPanel.setVisible(true);
		this.add(statiscalPanel, null);
		
		criteriaPanel.setBounds(new Rectangle(getWidthValue(0), getHeightValue(150), getWidthValue(350), getHeightValue(750)));
		criteriaPanel.setVisible(true);
//		criteriaPanel.setVisible(false);
		this.add(criteriaPanel, null);
		
		getJSelectionProgressBar().setBounds(new Rectangle(getWidthValue(10), getHeightValue(920), getWidthValue(330), getHeightValue(25)));
//		getJSelectionProgressBar().setBackground(textColor);
		getJSelectionProgressBar().setIndeterminate(false);
		getJSelectionProgressBar().setStringPainted(true);	    
		getJSelectionProgressBar().setBackground(new Color(156, 162, 189));
		getJSelectionProgressBar().setForeground(xipColor);
		this.add(getJSelectionProgressBar(), null);
		
		JLabel jLabel0 = new JLabel();
		jLabel0.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, getHeightValue(45)));
		jLabel0.setText("AVT");
		jLabel0.setBounds(new Rectangle(getWidthValue(104), getHeightValue(13), getWidthValue(150), getHeightValue(50)));
		jLabel0.setBackground(xipColor);
		jLabel0.setForeground(Color.WHITE);
		this.add(jLabel0, null);
		
		JLabel jLabel01 = new JLabel();
		jLabel01.setFont(new Font("Arial", Font.BOLD, getHeightValue(22)));
		jLabel01.setText("Measurement Variability Tool");
		jLabel01.setBounds(new Rectangle(getWidthValue(29), getHeightValue(59), getWidthValue(313), getHeightValue(50)));
		jLabel01.setBackground(xipColor);
		jLabel01.setForeground(labelColor);
		this.add(jLabel01, null);
	}

	/**
	 * This method initializes jSelectionProgressBar	
	 * 	
	 * @return javax.swing.JProgressBar	
	 */
	public JProgressBar getJSelectionProgressBar() {
		if (jSelectionProgressBar == null) {
			jSelectionProgressBar = new JProgressBar();
			jSelectionProgressBar.setBounds(new Rectangle(getWidthValue(10), getHeightValue(980), getWidthValue(303), getHeightValue(21)));
			jSelectionProgressBar.setFont(new Font("", Font.BOLD, getHeightValue(12)));
		}
		return jSelectionProgressBar;
	}

	public void setPanelVisible(boolean bVisible) {
		statiscalPanel.setVisible(bVisible);
		criteriaPanel.setVisible(!bVisible);		
	}
	
	public StatisticalAnalysisPanel getAnalysisPanel(){
		return statiscalPanel;
	}
	
	public SelectCriterialPanel getCriterialPanel(){
		return criteriaPanel;
	}
	
	public void setIdentifiers(List<String> names){
		criteriaPanel.setIdentifiers(names);
	}
	public String getStudyType() {
		return criteriaPanel.getStudyType();
	}
	public String getNominalGT() {
		return criteriaPanel.getNominalGT();
	}
}  //  @jve:decl-index=0:visual-constraint="17,1"
