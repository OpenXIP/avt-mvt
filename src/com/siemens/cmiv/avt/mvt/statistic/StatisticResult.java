package com.siemens.cmiv.avt.mvt.statistic;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Jie Zheng
 *
 */
public class StatisticResult {
	String subjectName;
	String subjectUID;
	Map<String, String> aims;
	
	public StatisticResult(String subjectName, String subjectUID){
		this.subjectName = subjectName;
		this.subjectUID = subjectUID;
		
		this.aims = new HashMap<String, String>();
	}
	public void AddSubjectAim(String key, String aim){
		this.aims.put(key, aim);
	}
	
	public String getSubjectUID(){
		return subjectUID;
	}
	public String getSubjectName(){
		return subjectName;
	}
	public String getAim(String key){
		return (String) aims.get(key);
	}
}
