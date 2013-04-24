package com.siemens.cmiv.avt.mvt.plot;


import java.io.File;
import java.util.List;

/**
 * @author Jie Zheng
 *
 */
public interface PlotManager {
	public boolean loadPlot (File xmlPlotFile);
	public boolean storePlot(List<PlotEntry> outlyings, File xmlPlotFile);			
	public boolean addPlotEntry(PlotEntry entry);
	public boolean deletePlotEntry(int index);	
	public PlotEntry getPlotEntry(int i);
	public List<PlotEntry> getPlotEntries();	
	public int getNumberOfPlotEntries();
}
