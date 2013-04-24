package com.siemens.cmiv.avt.mvt.ui;

import java.awt.Toolkit;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.JPopupMenu;

import com.siemens.cmiv.avt.mvt.utils.ImagePanel;
import com.siemens.cmiv.avt.mvt.utils.ImageSelection;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author Jie Zheng
 *
 */

public class PlotWindow extends JFrame {

	private static final long serialVersionUID = 1L;

	BufferedImage plotImage = null; 
	
	ImagePanel contentPane = null;
	JPopupMenu popMenu = null;
	
	/**
	 * This is the default constructor
	 */
	public PlotWindow() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(366, 334);
		
		contentPane = new ImagePanel();
		this.setContentPane(contentPane);
		this.setTitle("Plot Window");
		
		buildPopupMenu();
	}

	public void setWindowTile(String title){
		this.setTitle(title);
	}
	
	public void setPlotImage(BufferedImage plotImage){
		this.plotImage = plotImage;
		
		assert(contentPane != null);
		contentPane.setBackgroundImage(this.plotImage);
	}
	
	public void updateWindowSize(){
		if (plotImage != null){
			int width = plotImage.getWidth();
			int height = plotImage.getHeight();
			
			this.setSize(width+10, height+20);
			this.setResizable(false);
		}
	}
	
	public void buildPopupMenu(){
		if (popMenu == null){
			popMenu = new JPopupMenu();
			
			ActionListener actionListener = new PopupActionListener();
			
			JMenuItem menuItem = new JMenuItem("Copy");
			menuItem.addActionListener(actionListener);
			popMenu.add(menuItem);
			
			menuItem = new JMenuItem("Save as BMP");
			menuItem.addActionListener(actionListener);
			popMenu.add(menuItem);
			
			menuItem = new JMenuItem("Save as JPEG");
			menuItem.addActionListener(actionListener);
			popMenu.add(menuItem);
			
			menuItem = new JMenuItem("Save as PNG");
			menuItem.addActionListener(actionListener);
			popMenu.add(menuItem);
			
	        MouseListener popupListener = new PopupListener(popMenu);
	        addMouseListener(popupListener);
		}
	}
			
	class PopupActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
    		JMenuItem source = (JMenuItem)(e.getSource());
    		if (source.getText() == "Copy"){
    			System.out.println("Copy to clipboard");
    			
    			if (plotImage != null){
    				setClipboard(plotImage);
    			}
    			
    		}else if(source.getText() == "Save as BMP"){
    			System.out.println("Save as BMP");
    			
    			saveImage("bmp");
    			
    		}else if(source.getText() == "Save as JPEG"){
    			System.out.println("Save as JPEG");
    			
    			saveImage("jpg");
    			
    		}else if(source.getText() == "Save as PNG"){
    			System.out.println("Save as PNG");
    			
    			saveImage("png");
    			}
    		}
	}
	
    class PopupListener extends MouseAdapter {
        JPopupMenu popup;

        PopupListener(JPopupMenu popupMenu) {
            popup = popupMenu;
        }

        public void mousePressed(MouseEvent e) {
            maybeShowPopup(e);
        }

        public void mouseReleased(MouseEvent e) {
            maybeShowPopup(e);
        }

        private void maybeShowPopup(MouseEvent e) {
            if (e.isPopupTrigger()) {
                popup.show(e.getComponent(),
                           e.getX(), e.getY());
            }
        }
    }

    public void saveImage(String format){
		if (plotImage == null)
			return;
		
		File saveFile = new File("savedimage." + format);
		JFileChooser fc = new JFileChooser();
		fc.setSelectedFile(saveFile);
		int returnVal = fc.showSaveDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			saveFile = fc.getSelectedFile();
			
			try{
				ImageIO.write(plotImage, format, saveFile);
			} catch (IOException ex){
				JOptionPane.showConfirmDialog(null, "Save: "+saveFile.getPath(), "Error", JOptionPane.DEFAULT_OPTION);
				return;
			}
			
		}
	}
	
	  // This method writes a image to the system clipboard.
	  // otherwise it returns null.
	  public static void setClipboard(BufferedImage image) {
	    ImageSelection imageSelection = new ImageSelection(image);
  
	    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(imageSelection, null);
	  }
	
}  //  @jve:decl-index=0:visual-constraint="10,10"
