package com.siemens.cmiv.avt.mvt.ui;

import javax.swing.BorderFactory;
import javax.swing.SwingUtilities;
import javax.swing.JPanel;
import javax.swing.ButtonGroup;
import java.awt.Color;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Rectangle;
import javax.swing.JComboBox;
import javax.swing.border.TitledBorder;
import javax.swing.JRadioButton;
import javax.swing.JButton;

import com.siemens.cmiv.avt.mvt.outlier.OutlierEntry;
import com.siemens.cmiv.avt.mvt.outlier.OutlierManager;
import com.siemens.cmiv.avt.mvt.outlier.OutlierManagerFactory;

public class Outlier extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JLabel jLabel2 = null;
	private JPanel jPanel2 = null;
	private JRadioButton jRadioButton1 = null;
	private JButton jDoneButton = null;
	private JButton jCancelButton = null;
	
	Color xipColor = new Color(51, 51, 102);  //  @jve:decl-index=0:
	private JComboBox jDataComboBox = null;
	private JComboBox jScallingComboBox = null;
	StatisticalAnalysisPanel targetPanel = null;
	
	boolean bScaling = true;
	
	public Outlier() throws HeadlessException {
		// TODO Auto-generated constructor stub
		super();
		initialize();
	}

	public Outlier(GraphicsConfiguration arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
		initialize();
	}

	public Outlier(String arg0) throws HeadlessException {
		super(arg0);
		// TODO Auto-generated constructor stub
		initialize();
	}

	public Outlier(String arg0, GraphicsConfiguration arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
		initialize();
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
			TitledBorder border = BorderFactory.createTitledBorder("Criteria Selection");
			border.setTitleColor(Color.WHITE);
			jPanel2.setBorder(border);
			jPanel2.setBounds(new Rectangle(8, 58, 453, 119));
			jPanel2.add(getJRadioButton1(), null);
			ButtonGroup bg=new ButtonGroup();
			bg.add(jRadioButton1);
			jPanel2.setBackground(xipColor);
			jPanel2.add(getJScallingComboBox(), null);
		}
		return jPanel2;
	}

	/**
	 * This method initializes jRadioButton1	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJRadioButton1() {
		if (jRadioButton1 == null) {
			jRadioButton1 = new JRadioButton("Cutoff by", true);
			jRadioButton1.setBounds(new Rectangle(14, 56, 141, 19));
			jRadioButton1.setForeground(Color.WHITE);
			jRadioButton1.setBackground(xipColor);
			jRadioButton1.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					bScaling = !bScaling;
				}
			});
		}
		return jRadioButton1;
	}

	/**
	 * This method initializes jDoneButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJDoneButton() {
		if (jDoneButton == null) {
			jDoneButton = new JButton();
			jDoneButton.setBounds(new Rectangle(255, 284, 79, 23));
			jDoneButton.setText("Done");
			jDoneButton.addMouseListener(new java.awt.event.MouseAdapter() {
				@Override
				public void mousePressed(java.awt.event.MouseEvent e) {
					try{
						OutlierManager outMgr = OutlierManagerFactory.getInstance();
						
						OutlierEntry content = new OutlierEntry();
						String buffer = (String) getJDataComboBox().getSelectedItem();
						content.setCriteriaMeasurement(buffer);
						
						buffer = (String) getJScallingComboBox().getSelectedItem();

						content.setCriteriaScaling(buffer);
						outMgr.addOutlierEntry(content);
						
						if (targetPanel != null)
							targetPanel.updateOutlierList();
					}catch (IllegalArgumentException e1){
						return;
					}
								
					dispose();
				}
			});
		}
		return jDoneButton;
	}

	/**
	 * This method initializes jCancelButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJCancelButton() {
		if (jCancelButton == null) {
			jCancelButton = new JButton();
			jCancelButton.setBounds(new Rectangle(369, 283, 77, 23));
			jCancelButton.setText("Cancel");
			jCancelButton.addMouseListener(new java.awt.event.MouseAdapter() {
				@Override
				public void mousePressed(java.awt.event.MouseEvent e) {
					dispose();
				}
			});
		}
		return jCancelButton;
	}

	/**
	 * This method initializes jDataComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJDataComboBox() {
		if (jDataComboBox == null) {
			String labels[] = {
					"NominalGT RECIST",
					"Annotation RECIST",
					"RECIST Difference",
					"NominalGT WHO",
					"Annotation WHO",
					"WHO Difference",
					"NominalGT Volume",
					"Annotation Volume",
					"Volume Difference",
					"Relative VolumeDifference",
					"Surface Distance(Average)",
					"Surface Distance(RMS)",
					"Surface Distance(Maximum)",
					"Volume Overlap"};
			jDataComboBox = new JComboBox(labels);
			jDataComboBox.setBackground(Color.WHITE);
			jDataComboBox.setBounds(new Rectangle(174, 18, 282, 24));
		}
		return jDataComboBox;
	}

	/**
	 * This method initializes jScallingComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJScallingComboBox() {
		if (jScallingComboBox == null) {
			String labels[] = {"Top 25%","Top 50%","Bottom 50%","Bottom 25%"};
			jScallingComboBox = new JComboBox(labels);
			jScallingComboBox.setBackground(Color.WHITE);
			jScallingComboBox.setBounds(new Rectangle(174, 56, 154, 20));
		}
		return jScallingComboBox;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Outlier thisClass = new Outlier();
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
		this.setSize(476, 366);
		this.setContentPane(getJContentPane());
		this.setTitle("Outliers Designer");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel2 = new JLabel();
			jLabel2.setText("Measurement applied on:");
			jLabel2.setBounds(new Rectangle(14, 17, 147, 26));
			jLabel2.setForeground(Color.WHITE);
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJPanel2(), null);
			jContentPane.add(getJDoneButton(), null);
			jContentPane.add(getJCancelButton(), null);
			jContentPane.setBackground(xipColor);
			jContentPane.add(jLabel2, null);
			jContentPane.add(getJDataComboBox(), null);
		}
		return jContentPane;
	}

	public void setTarget(StatisticalAnalysisPanel target){
		this.targetPanel = target;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
