package com.siemens.cmiv.avt.mvt.ui;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

/**
 * @author Jie Zheng
 *
 */

public class PlottingPanel extends JScrollPane {

	private static final long serialVersionUID = 1L;
	
	static int Column = 3;
	static int Interval = 50;
	int Height = 360;
	
	List<BufferedImage> imageArray = new ArrayList<BufferedImage>();
	List<Rectangle> imageRect = new ArrayList<Rectangle>();  //  @jve:decl-index=0:
	
	boolean validatedResize = true;
	
	/**
	 * This is the default constructor
	 */
	public PlottingPanel() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setLayout(null);
		this.setSize(200, 200);
		this.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent e) {
				if (e.getClickCount() == 2){//the double click
					int x = e.getX();
					int y = e.getY();
					
					for (int i = 0; i < imageRect.size(); i++){
						Rectangle rect = imageRect.get(i);
						if (rect.contains(x, y)){
							
							PlotWindow plotWin = new PlotWindow();
							plotWin.setPlotImage(imageArray.get(i));
							plotWin.updateWindowSize();
							
							plotWin.setVisible(true);
						}
					}
				}
			}
		});
		this.addComponentListener(new java.awt.event.ComponentAdapter() {
			public void componentResized(java.awt.event.ComponentEvent e) {
				Dimension cs = e.getComponent().getSize();
				
				if (validatedResize){
					Height = (int) cs.getHeight();
					validatedResize = false;
				}
				System.out.println(cs);
			}
		});
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Dimension cs = this.getSize();
		drawPlotting(g, cs);
	}
	
	public void emptyImages(){
		imageArray.clear();
		imageRect.clear();
	}
	
	public boolean addPlottingImage(String image) {
		try {
			
			BufferedImage img = ImageIO.read(new File(image));
			
			imageArray.add(img);	
			
	       } catch (IOException e) {
	    	   System.out.println("load plotting failed:" + image);
	    	   
	    	   return false;
	       }
	       
		return true;
	}
	
	public Dimension getViewsize() {
		int x = 0; 
		int y = 0;
		int num = imageArray.size();
		if (num > 0){
			try{
		
			Dimension cs = this.getSize();
			x = (int) cs.getWidth()-Interval;
			
			int height = (int) Height - Interval/2;
			y = height + ((num-1)/Column)*height + ((num-1)/Column)*Interval;
			}
			catch(NullPointerException e){
				System.out.println("open plot chart error");
			}
		}
			
		
		return new Dimension(x, y); 
	}
	
	public void drawPlotting(Graphics g, Dimension cs) {
		int num = imageArray.size(); 
		
		for (int i = 0; i < num; i++){
			BufferedImage img = imageArray.get(i);
			if (img == null){
				System.out.println("error: is about to display a null chart");
				continue;
			}
			int height = (int) (Height - Interval/2);
			
			int x = Interval + (i%Column)*(Interval+height);
			int y = Interval/4+(i/Column)*height+(i/Column)*Interval/2;
			
			Image icon = img.getScaledInstance(height, height, Image.SCALE_SMOOTH);
			g.drawImage(icon, x, y, null);
		}
	}
	
	public void updateImageRectArray(){
		imageRect.clear();
		
		for (int i = 0 ; i < imageArray.size(); i++){
			Rectangle rect = new Rectangle();
				
			int height = (int) (Height - Interval/2);
			int x = Interval + (i%Column)*(Interval+height);
			int y = Interval/4+(i/Column)*height+(i/Column)*Interval/2;

			rect.setBounds(x, y, height, height);
			
			assert (!rect.isEmpty());
			imageRect.add(rect);
		}
	}
}  //  @jve:decl-index=0:visual-constraint="10,10"
