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

/**
 * @author Jie Zheng
 *
 */

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.image.BufferedImage;
import java.io.IOException;

	  // Inner class is used to hold an image while on the clipboard.
	  public class ImageSelection implements Transferable 
	  {
	    // the Image object which will be housed by the ImageSelection
	    private BufferedImage image;

	    public ImageSelection(BufferedImage image) {
	      this.image = image;
	    }

	    // Returns the supported flavors of our implementation
	    public DataFlavor[] getTransferDataFlavors() 
	    {
	      return new DataFlavor[] {DataFlavor.imageFlavor};
	    }
	    
	    // Returns true if flavor is supported
	    public boolean isDataFlavorSupported(DataFlavor flavor) 
	    {
	      return DataFlavor.imageFlavor.equals(flavor);
	    }

	    // Returns Image object housed by Transferable object
	    public Object getTransferData(DataFlavor flavor)
	      throws UnsupportedFlavorException,IOException 
	    {
	      if (!DataFlavor.imageFlavor.equals(flavor)) 
	      {
	        throw new UnsupportedFlavorException(flavor);
	      }
	      // else return the payload
	      return image;
	    }
	  }
