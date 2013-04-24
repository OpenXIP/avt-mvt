package com.siemens.cmiv.avt.mvt.datatype;

/**
 * @author Jie Zheng
 *
 */
public class FilterEntry {
	String gender;
	int age;
	float sliceThickness;
	String annotator;

	/**
	 * 
	 */
	public FilterEntry() {
		// TODO Auto-generated constructor stub
		gender = "ALL";
		age = 0;
		sliceThickness = 0.0f;
		annotator = "ALL";
	}
	
	public String getGender(){
		return gender;
	}
	public int getAge(){
		return age;
	}
	public float getSliceThickness(){
		return sliceThickness;
	}
	public String getAnnotator(){
		return annotator;
	}

	public void setGender(String gender){
		this.gender = gender;
	}
	public void setAge(int age){
		this.age = age;
	}
	public void setSliceThickness(float sliceThickness){
		this.sliceThickness = sliceThickness;
	}
	public void setAnnotator(String annotator){
		this.annotator = annotator;
	}
}
