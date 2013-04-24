package com.siemens.cmiv.avt.mvt.utils;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JComponent;

public class ImagePanel extends JComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Image backgroundImage = null;
	 
	/**
	 * Constructor
	 */
	public ImagePanel() {
		super();
	}
 
	/**
	 * Returns the background image
	 * @return	Background image
	 */
	public Image getBackgroundImage() {
		return backgroundImage;
	}
 
	/**
	 * Sets the background image
	 * @param backgroundImage	Background image
	 */
	public void setBackgroundImage(Image backgroundImage) {
		this.backgroundImage = backgroundImage;
	}
	
	/**
	 * Overrides the painting to display a background image
	 */
	protected void paintComponent(Graphics g) {
		if (isOpaque()) {
			g.setColor(getBackground());
			g.fillRect(0, 0, getWidth(), getHeight());
		}
		if (backgroundImage != null) {
			g.drawImage(backgroundImage,0,0,this);
		}
	}
 
}
