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
