package com.siemens.cmiv.avt.mvt.io;


/**
 * @author Jie Zheng
 */

public class SubjectListFactory {
	private static SubjectList subjectlist = new XMLSubjectListImpl();
	
	private SubjectListFactory(){}
	
	public static SubjectList getInstance(){
		return subjectlist;
	}	
}
