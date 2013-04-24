package com.siemens.cmiv.avt.mvt.ui;

import javax.swing.SwingUtilities;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JComboBox;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;

import javax.swing.JScrollPane;

import com.siemens.cmiv.avt.mvt.plot.PlotEntry;
import com.siemens.cmiv.avt.mvt.plot.PlotManager;
import com.siemens.cmiv.avt.mvt.plot.PlotManagerFactory;
import javax.swing.JCheckBox;

/**
 * @author Jie Zheng
 *
 */

public class PlottingDesigner extends JFrame implements ActionListener, ItemListener{

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JComboBox jPlotComboBox = null;
	private JLabel jLabel = null;
	private JLabel jTitleLabel = null;
	private JTextField jTitleTextField = null;
	private JList jMeasurementList = null;
	private JLabel jData1Label = null;
	private JTextField jData1TextField = null;
	private JLabel jData2Label = null;
	private JTextField jData2TextField = null;
	private JButton jData1Button = null;
	private JButton jData2Button = null;
	private JButton jOKButton = null;
	private JButton jCancelButton = null;
	private JPanel jPlotPanel = null;
	private JPanel jDataPanel = null;
	
	StatisticalAnalysisPanel targetPanel = null;
	
//	String labels[] = {"Scatter","Bland_Altmann"};
	String labels[] = {"Scatter","Bland_Altmann","Histogram"};
	DefaultListModel listModel = new DefaultListModel(); 
	
	Color xipColor = new Color(51, 51, 102);
	private JScrollPane jScrollPane = null;
	private JLabel jLegendLabel = null;
	private JCheckBox jCheckBox = null;
	
	private boolean bLegend = true;

	/**
	 * This method initializes jPlotComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJPlotComboBox() {
		if (jPlotComboBox == null) {
			jPlotComboBox = new JComboBox(labels);
			jPlotComboBox.setBorder(BorderFactory.createMatteBorder(1, 1,
			        1, 1, Color.WHITE));
			jPlotComboBox.setBackground(Color.WHITE);
			jPlotComboBox.setBounds(new Rectangle(196, 40, 280, 25));
			
			jPlotComboBox.addActionListener(this);
		}
		return jPlotComboBox;
	}

	/**
	 * This method initializes jTitleTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTitleTextField() {
		if (jTitleTextField == null) {
			jTitleTextField = new JTextField();
			jTitleTextField.setBounds(new Rectangle(196, 72, 280, 25));
		}
		return jTitleTextField;
	}

	/**
	 * This method initializes jMeasurementList	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JList getJMeasurementList() {
		if (jMeasurementList == null) {
			jMeasurementList = new JList();
			jMeasurementList.setBounds(new Rectangle(20, 164, 171, 244));
			
			listModel.addElement("NominalGT RECIST"); 
			listModel.addElement("Annotation RECIST"); 
			listModel.addElement("RECIST Difference"); 
			listModel.addElement("NominalGT WHO"); 
			listModel.addElement("Annotation WHO"); 
			listModel.addElement("WHO Difference"); 
			listModel.addElement("NominalGT Volume"); 
			listModel.addElement("Annotation Volume"); 
			listModel.addElement("Volume Difference"); 
			listModel.addElement("Relative VolumeDifference"); 
			listModel.addElement("Surface Distance(Average)"); 
			listModel.addElement("Surface Distance(RMS)"); 
			listModel.addElement("Surface Distance(Maximum)"); 
			listModel.addElement("Volume Overlap"); 
		
			jMeasurementList.setModel(listModel); 

		}
		return jMeasurementList;
	}

	/**
	 * This method initializes jData1TextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJData1TextField() {
		if (jData1TextField == null) {
			jData1TextField = new JTextField();
			jData1TextField.setBounds(new Rectangle(277, 212, 195, 27));
		}
		return jData1TextField;
	}

	/**
	 * This method initializes jData2TextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJData2TextField() {
		if (jData2TextField == null) {
			jData2TextField = new JTextField();
			jData2TextField.setBounds(new Rectangle(281, 330, 195, 27));
		}
		return jData2TextField;
	}

	/**
	 * This method initializes jData1Button	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJData1Button() {
		if (jData1Button == null) {
			jData1Button = new JButton();
			jData1Button.setBounds(new Rectangle(209, 212, 53, 28));
			jData1Button.setText(">>");
			jData1Button.addMouseListener(new java.awt.event.MouseAdapter() {
				@Override
				public void mouseClicked(java.awt.event.MouseEvent e) {
					String str = jData1Button.getText();
					if (str.compareToIgnoreCase(">>") == 0){
						String change = (String)jMeasurementList.getSelectedValue(); 
						if (change != null){
							jData1TextField.setText(change);
							jData1Button.setText("<<");
						}
					}
					else {
						jData1TextField.setText("");
						jData1Button.setText(">>");
					}
				}
			});
		}
		return jData1Button;
	}

	/**
	 * This method initializes jData2Button	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJData2Button() {
		if (jData2Button == null) {
			jData2Button = new JButton();
			jData2Button.setBounds(new Rectangle(212, 330, 53, 28));
			jData2Button.setText(">>");
			jData2Button.addMouseListener(new java.awt.event.MouseAdapter() {
				@Override
				public void mouseClicked(java.awt.event.MouseEvent e) {
					String str = jData2Button.getText();
					if (str.compareToIgnoreCase(">>") == 0){
						String change = (String)jMeasurementList.getSelectedValue(); 
						if (change != null){
							jData2TextField.setText(change);
							jData2Button.setText("<<");
						}
					}
					else {
						jData2TextField.setText("");
						jData2Button.setText(">>");
					}
				}
			});
		}
		return jData2Button;
	}

	/**
	 * This method initializes jOKButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJOKButton() {
		if (jOKButton == null) {
			jOKButton = new JButton();
			jOKButton.setBounds(new Rectangle(257, 459, 88, 26));
			jOKButton.setText("OK");
			jOKButton.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mousePressed(java.awt.event.MouseEvent e) {
					try{
						PlotManager plotMgr = PlotManagerFactory.getInstance();
						
						PlotEntry content = new PlotEntry();
						String buffer = getJTitleTextField().getText();
						if (buffer.isEmpty()){
							JOptionPane.showConfirmDialog(null, "Please input title", "alert", JOptionPane.DEFAULT_OPTION);
							return;
						}
						content.setPlotTitle(buffer);
						
						String type = (String) getJPlotComboBox().getSelectedItem();
						content.setPlotChart(type);
						
						buffer = (String) getJData1TextField().getText();
						if (buffer.isEmpty()){
							JOptionPane.showConfirmDialog(null, "Please select data1", "alert", JOptionPane.DEFAULT_OPTION);
							return;
						}
						content.setPlotData1(buffer);
						
						buffer = (String) getJData2TextField().getText();
						if (buffer.isEmpty() && type.compareToIgnoreCase("Histogram") != 0){
							JOptionPane.showConfirmDialog(null, "Please select data2", "alert", JOptionPane.DEFAULT_OPTION);
							return;
						}
						content.setPlotData2(buffer);				
						
						content.setLegend(bLegend);
						
						if (!plotMgr.addPlotEntry(content)){
							JOptionPane.showConfirmDialog(null, "The new plot exists. Please try again", "alert", JOptionPane.DEFAULT_OPTION);
							return;							
						}
						
						if (targetPanel != null)
							targetPanel.updatePlotList();
					}catch (IllegalArgumentException e1){
						return;
					}
								
					dispose();
				}
			});
		}
		return jOKButton;
	}

	/**
	 * This method initializes jCancelButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJCancelButton() {
		if (jCancelButton == null) {
			jCancelButton = new JButton();
			jCancelButton.setBounds(new Rectangle(380, 459, 88, 26));
			jCancelButton.setText("Cancel");
			jCancelButton.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mousePressed(java.awt.event.MouseEvent e) {
					dispose();
				}
			});
		}
		return jCancelButton;
	}

	/**
	 * This method initializes jPlotPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPlotPanel() {
		if (jPlotPanel == null) {
			jLegendLabel = new JLabel();
			jLegendLabel.setBounds(new Rectangle(24, 94, 94, 17));
			jLegendLabel.setText("Plotting Legend:");
			jLegendLabel.setForeground(Color.WHITE);
			jPlotPanel = new JPanel();
			jPlotPanel.setLayout(null);
			jPlotPanel.setBounds(new Rectangle(7, 12, 481, 128));
			jPlotPanel.setBackground(xipColor);
			TitledBorder border = BorderFactory.createTitledBorder("Plotting Setting");
			border.setTitleColor(Color.WHITE);
			jPlotPanel.setBorder(border);
			jPlotPanel.add(jLegendLabel, null);
			jPlotPanel.add(getJCheckBox(), null);
		}
		return jPlotPanel;
	}

	/**
	 * This method initializes jDataPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJDataPanel() {
		if (jDataPanel == null) {
			jDataPanel = new JPanel();
			jDataPanel.setLayout(null);
			jDataPanel.setBounds(new Rectangle(7, 156, 481, 264));
			jDataPanel.setBackground(xipColor);
			TitledBorder border = BorderFactory.createTitledBorder("Data Setting");
			border.setTitleColor(Color.WHITE);
			jDataPanel.setBorder(border);
			jDataPanel.add(getJScrollPane(), null);
		}
		return jDataPanel;
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setBounds(new Rectangle(15, 25, 172, 228));
			jScrollPane.setBorder(BorderFactory.createMatteBorder(1, 1,
			        1, 1, Color.WHITE));
			jScrollPane.setViewportView(getJMeasurementList());
			jScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
			jScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jCheckBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBox() {
		if (jCheckBox == null) {
			jCheckBox = new JCheckBox();
			jCheckBox.setBounds(new Rectangle(186, 90, 134, 24));
			jCheckBox.setText("on");
			jCheckBox.setForeground(Color.WHITE);
			jCheckBox.setBackground(xipColor);
			jCheckBox.setSelected(true);
			jCheckBox.addItemListener(this);
		}
		return jCheckBox;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				PlottingDesigner thisClass = new PlottingDesigner();
				thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				thisClass.setVisible(true);
			}
		});
	}

	/**
	 * This is the default constructor
	 */
	public PlottingDesigner() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(505, 528);
		this.setContentPane(getJContentPane());
		this.setTitle("Plotting Designer");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jData2Label = new JLabel();
			jData2Label.setBounds(new Rectangle(280, 300, 111, 26));
			jData2Label.setText("Y Axis:");
			jData2Label.setForeground(Color.WHITE);
			jData1Label = new JLabel();
			jData1Label.setBounds(new Rectangle(276, 183, 111, 26));
			jData1Label.setText("X Axis:");
			jData1Label.setForeground(Color.WHITE);
			jTitleLabel = new JLabel();
			jTitleLabel.setBounds(new Rectangle(30, 70, 78, 24));
			jTitleLabel.setText("Plotting Title:");
			jTitleLabel.setForeground(Color.WHITE);
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(29, 39, 105, 25));
			jLabel.setText("Plotting Type:");
			jLabel.setForeground(Color.WHITE);
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJPlotComboBox(), null);
			jContentPane.add(jLabel, null);
			jContentPane.add(jTitleLabel, null);
			jContentPane.add(getJTitleTextField(), null);
			jContentPane.add(getJMeasurementList(), null);
			jContentPane.add(jData1Label, null);
			jContentPane.add(getJData1TextField(), null);
			jContentPane.add(jData2Label, null);
			jContentPane.add(getJData2TextField(), null);
			jContentPane.add(getJData1Button(), null);
			jContentPane.add(getJData2Button(), null);
			jContentPane.add(getJOKButton(), null);
			jContentPane.add(getJCancelButton(), null);
			jContentPane.add(getJPlotPanel(), null);
			jContentPane.add(getJDataPanel(), null);
			jContentPane.setBackground(xipColor);
		}
		return jContentPane;
	}

	public void setTarget(StatisticalAnalysisPanel statisticalAnalysisPanel) {
		this.targetPanel = statisticalAnalysisPanel;
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JComboBox cb = (JComboBox)e.getSource();
        String type = (String)cb.getSelectedItem();
      
        if (type == labels[0]){
        	jData1Label.setText("X Axis:");
        	jData2Label.setText("Y Axis:");
        	jData2TextField.setEnabled(true);
        	jData2Button.setEnabled(true);
       }else if (type == labels[1]){
        	jData1Label.setText("First method:");
        	jData2Label.setText("Second method:");
        	jData2TextField.setEnabled(true);
        	jData2Button.setEnabled(true);
     }else if (type == labels[2]){
          	jData1Label.setText("Data:");
        	jData2Label.setText("N/A");
        	jData2TextField.setEnabled(false);
        	jData2Button.setEnabled(false);
       }
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		Object source = e.getItemSelectable();
		
		if (source == jCheckBox){
			bLegend = !bLegend;
		}
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
