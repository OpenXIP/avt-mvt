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
