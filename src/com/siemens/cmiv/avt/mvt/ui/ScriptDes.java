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
import javax.swing.SwingUtilities;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import java.awt.Rectangle;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import com.siemens.cmiv.avt.mvt.statistic.StatisticManagerFactory;
import com.siemens.cmiv.avt.mvt.statistic.StatisticEntry;
import com.siemens.cmiv.avt.mvt.statistic.StatisticManager;

public class ScriptDes extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JPanel jPanel = null;
	private JScrollPane jScrollPane = null;
	private JTextArea jtextArea = null;
	private JButton jButton = null;
	private JButton jButton1 = null;
	private JLabel jLabel = null;
	Color xipColor = new Color(51, 51, 102);
	StatisticalAnalysisPanel targetPanel = null;
	private JTextField jNameTextField = null;

	public ScriptDes() throws HeadlessException {
		// TODO Auto-generated constructor stub
		super();
		initialize();
	}

	public ScriptDes(GraphicsConfiguration gc) {
		super(gc);
		// TODO Auto-generated constructor stub
		initialize();
	}

	public ScriptDes(String title) throws HeadlessException {
		super(title);
		// TODO Auto-generated constructor stub
		initialize();
	}

	public ScriptDes(String title, GraphicsConfiguration gc) {
		super(title, gc);
		// TODO Auto-generated constructor stub
		initialize();
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(null);
			TitledBorder border = BorderFactory.createTitledBorder("Self-defined Statistic Script");
			border.setTitleColor(Color.WHITE);
			jPanel.setBorder(border);
			jPanel.setBounds(new Rectangle(2, 51, 381, 198));
			jPanel.add(getJScrollPane(), null);
			jPanel.setBackground(xipColor);
		}
		return jPanel;
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setBorder(BorderFactory.createMatteBorder(1, 1,
			        1, 1, Color.WHITE));
			jScrollPane.setBounds(new Rectangle(12, 24, 363, 162));
			jScrollPane.setViewportView(getJtextArea());
			jScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
			jScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		}
		return jScrollPane;
	}
	
	private  JTextArea getJtextArea() 
	{ 
		if (jtextArea == null){ 
			jtextArea = new JTextArea(); 
			jtextArea.setText(""); 
			jtextArea.setLineWrap(true);
		} 
		return jtextArea ; 
	} 

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new Rectangle(224, 272, 67, 26));
			jButton.setText("Done");
			jButton.addMouseListener(new java.awt.event.MouseAdapter() {
				@Override
				public void mouseClicked(java.awt.event.MouseEvent e) {
					try{
						String name = getJNameTextField().getText();
						if (name.trim().isEmpty())
							return;
						String  buffer= getJtextArea().getText();
						if (buffer.trim().isEmpty())
							return;
						
						StatisticEntry content = new StatisticEntry();
						StatisticManager staMgr = StatisticManagerFactory.getInstance();
						String method = name + ":" + buffer;
						content.setStatisticMethod(method);
						content.setStatisticData("All Measurements");
						staMgr.addStatisticEntry(content);

						if (targetPanel != null)
							targetPanel.updateStatisticsList();
					}catch (IllegalArgumentException e1){
						return;
					}
								
					dispose();
				}
			});
		}
		return jButton;
	}

	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(new Rectangle(304, 272, 69, 26));
			jButton1.setText("Reset");
			jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
				@Override
				public void mouseClicked(java.awt.event.MouseEvent e) {
					jtextArea.setText("");
				}
			});
		}
		return jButton1;
	}

	/**
	 * This method initializes jNameTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJNameTextField() {
		if (jNameTextField == null) {
			jNameTextField = new JTextField();
			jNameTextField.setBounds(new Rectangle(142, 15, 234, 21));
		}
		return jNameTextField;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				ScriptDes thisClass = new ScriptDes();
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
		this.setSize(393, 342);
		this.setContentPane(getJContentPane());
		this.setTitle("Script Designer");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(9, 15, 127, 19));
			jLabel.setText("Measurement Name:");
			jLabel.setForeground(Color.WHITE);
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJPanel(), null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(getJButton1(), null);
			jContentPane.add(jLabel, null);
			jContentPane.setBackground(xipColor);
			jContentPane.add(getJNameTextField(), null);
		}
		return jContentPane;
	}
	
	public void setTarget(StatisticalAnalysisPanel target){
		this.targetPanel = target;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10" 
