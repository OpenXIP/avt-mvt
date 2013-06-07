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
package com.siemens.cmiv.avt.mvt.plot;


/**
 * @author Jie Zheng
 *
 */
public class PlotEntry {
	String data1;
	String data2;
	String title;
	String chart;
	boolean legend;

	/**
	 * 
	 */
	public PlotEntry() {
	}
	
	public String getPlotData1(){
		return data1;
	}
	public String getPlotData2(){
		return data2;
	}
	public String getPlotTitle(){
		return title;
	}
	public String getPlotChart(){
		return chart;
	}
	public boolean getLegend(){
		return legend;
	}

	public void setPlotData1(String data1){
		this.data1 = data1;
	}
	
	public void setPlotData2(String data2){
		this.data2 = data2;
	}
	
	public void setPlotTitle(String title){
		this.title = title;
	}

	public void setPlotChart(String chart){
		this.chart = chart;
	}
	
	public void setLegend(boolean legend){
		this.legend = legend;
	}
	
	public boolean compareEntry(PlotEntry entry){
		if (data1.equalsIgnoreCase(entry.getPlotData1()) && data2.equalsIgnoreCase(entry.getPlotData2()) && chart.equalsIgnoreCase(entry.getPlotChart()) && title.equalsIgnoreCase(entry.getPlotTitle()))
			return false;
	
		return true;
	}
}
