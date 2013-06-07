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
