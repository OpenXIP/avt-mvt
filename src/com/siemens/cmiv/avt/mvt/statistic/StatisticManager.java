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
package com.siemens.cmiv.avt.mvt.statistic;

import java.io.File;
import java.util.List;

/**
 * @author Jie Zheng
 *
 */
public interface StatisticManager {
	public boolean loadStatistic (File xmlStatisticFile);
	public boolean storeStatistic(List<StatisticEntry> statistic, File xmlStatisticFile);			
	public boolean addStatisticEntry(StatisticEntry entry);
	public boolean deleteStatisticEntry(int index);	
	public StatisticEntry getStatisticEntry(int i);
	public List<StatisticEntry> getStatisticEntries();	
	public int getNumberOfStatisticEntries();
}
