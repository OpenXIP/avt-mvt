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
package com.siemens.cmiv.avt.mvt.io;

import java.io.File;
import java.util.List;

import com.siemens.cmiv.avt.mvt.ad.ADSearchResult;
import com.siemens.cmiv.avt.mvt.datatype.FilterEntry;
import com.siemens.cmiv.avt.mvt.datatype.SubjectListEntry;

/**
 * @author Jie Zheng
 *
 */
public interface SubjectList {
	public boolean loadSubjectList (File xmlSubjectListFile);
	public boolean storeSubjectList(List<SubjectListEntry> subjects, File xmlSubjectListFile);			
	public boolean addSubjectlistEntry(SubjectListEntry entry);
	public boolean deleteSubjectlistEntry(SubjectListEntry entry);
	public List getSubjectListEntries();	
	public int getNumberOfSubjectListEntries();
	public SubjectListEntry getSubjectListEntry(int i);
	public boolean setFilterList(FilterEntry filter);
	public void updateQuerySubjectList(ADSearchResult result);
}
