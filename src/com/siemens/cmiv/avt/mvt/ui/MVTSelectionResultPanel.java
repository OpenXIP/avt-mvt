package com.siemens.cmiv.avt.mvt.ui;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.siemens.cmiv.avt.mvt.datatype.AnnotationEntry;
import com.siemens.cmiv.avt.mvt.datatype.FilterEntry;
import com.siemens.cmiv.avt.mvt.datatype.SubjectListEntry;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

/**
 * @author Jie Zheng
 *
 */

public class MVTSelectionResultPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	SelectionResultsPanel SelectPanel = new SelectionResultsPanel();
	AIMPanel AimPanel = new AIMPanel();

	/**
	 * This is the default constructor
	 */
	public MVTSelectionResultPanel() {
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
	
		this.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		this.setBackground(xipColor);
		
//		this.setSize(557, 541);
		this.setLayout(new GridBagLayout());
	
		SelectPanel.setVisible(true);
		SelectPanel.getSubjectTable().getSelectionModel().addListSelectionListener(new ListSelectionHandler());

		AimPanel.setVisible(true);
		
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHEAST;
		c.fill = GridBagConstraints.BOTH;
		c.ipadx = 400;      //make this component tall
		c.ipady = 400;      //make this component tall
		c.gridwidth = 0;
		c.gridx = 0;
		c.gridy = 0;
		this.add(SelectPanel, c);
		
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(20,0,0,0);
		c.anchor = GridBagConstraints.LAST_LINE_END;
		c.ipady = 200;       //reset to default
		c.gridx = 0;       
		c.gridy = 1;       
		this.add(AimPanel, c);
	}
	
	public void loadAimsFromFolder(String aimFolder){
		SelectPanel.loadAims(aimFolder);
	}
	
	public void queryData(FilterEntry filter) {
		// TODO Auto-generated method stub
		SelectPanel.queryData(filter);
	}

	public List<String> getDatalist() {
		// TODO Auto-generated method stub
		return SelectPanel.getDatalist();
	}

	public void queryAD(FilterEntry filter) {
		SelectPanel.queryAD(filter);
	}

	public void setUIProgressBar(JProgressBar selectionProgressBar) {
		SelectPanel.setUIProgressBar(selectionProgressBar);
	}
	
	public List<String> getIdentifier(){
		return SelectPanel.getReaderList();
	}
	
   class ListSelectionHandler implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent e) { 
            int index = SelectPanel.getSubjectTable().getSelectedRow();
            
            if (index != -1){
            	SubjectListEntry item = SelectPanel.getItemEntry(index);
            	if (item != null){
            		List<AnnotationEntry> anotations = item.getAnnotations();
            		AimPanel.updateAimEntries(anotations);
            	}            	
            }
        }
    }

public List<SubjectListEntry> getSubjectlist() {
	return SelectPanel.getSubjectlist();
}

}  //  @jve:decl-index=0:visual-constraint="10,10"
