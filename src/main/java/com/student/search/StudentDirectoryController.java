package com.student.search;

import java.util.List;

import com.opensymphony.xwork2.ActionSupport;

public class StudentDirectoryController extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private String namesQuery;
	private List<Object> student;
	private StudentSearchService studentSearchService = new StudentSearchService();
	private List<List<Object>> resultsList;
	
	/*Struts Actions*/
	public String nameSearchAction() throws Exception {
		setResultsList(studentSearchService.queryStudents(namesQuery));
		if(resultsList.size() > 0)
			return "success";
		else
			return "failure";
	}
	
	public String studentDetailsAction() {
		char studentId = student.get(0).toString().charAt(1);
		setResultsList(studentSearchService.queryStudentDetails(studentId));
		setStudent(studentSearchService.queryStudentId(studentId));
		return "success";
	}
	
	/*Get and Set methods*/
	public String getNamesQuery() {
		return namesQuery;
	}
	
	public void setNamesQuery(String namesQuery) {
		this.namesQuery = namesQuery;
	}

	public List<List<Object>> getResultsList() {
		return resultsList;
	}

	public void setResultsList(List<List<Object>> resultsList) {
		this.resultsList = resultsList;
	}
	
	public List<Object> getStudent() {
		return student;
	}

	public void setStudent(List<Object> student) {
		this.student = student;
	}
}
