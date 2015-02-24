package com.student.search;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.ListUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class StudentSearchService {

	private DatabaseConnection db = new DatabaseConnection();
	
	public List<List<Object>> queryStudents(String names) {
		StringBuffer nameSearchQuery = new StringBuffer();
		for(String singleName: names.split("\\s+")) {
			if(nameSearchQuery.length() > 0) {
				nameSearchQuery.append(" UNION ");
			}
	    	nameSearchQuery.append("SELECT * FROM students WHERE first_name LIKE '%" + singleName + "%'"
	    			+ " UNION SELECT * FROM students WHERE last_name LIKE '%" + singleName + "%'");
	    }
		return makeQuery(nameSearchQuery.toString());
	}
	
	public List<List<Object>> queryStudentDetails(char studentId) {
		/*String detailsSearchQuery = "SELECT title FROM classes WHERE class_id IN " +
				"(SELECT class_id FROM student_class_assoc WHERE student_id = " + studentId +
				") UNION (SELECT gpa FROM student_class_assoc WHERE student_id = " + studentId;*/
		List<List<Object>> classTitles = makeQuery("SELECT title FROM classes WHERE class_id IN " +
				"(SELECT class_id FROM student_class_assoc WHERE student_id = " + studentId + ")");
		List<List<Object>> classGPAs = makeQuery("SELECT gpa FROM student_class_assoc WHERE student_id = " + studentId);
		List<List<Object>> titlesAndGpas = new ArrayList<List<Object>>();
		for(int i=0; i<classTitles.size(); i++) {
			titlesAndGpas.add(i, ListUtils.union(classTitles.get(i), classGPAs.get(i)));
		}
		return titlesAndGpas;
	}
	
	public List<Object> queryStudentId(char studentId) {
		List<List<Object>> student = makeQuery("SELECT * FROM students WHERE student_id = " + studentId);
		List<Object> betterStudent = new ArrayList<Object>();
		for(Object item : student.get(0)) {
			betterStudent.add(item);
		}
		return betterStudent;
	}
	
	private List<List<Object>> makeQuery(String queryRequest) {
		Statement myStatement = null;
		ResultSet myResultSet = null;
		//String jsonResult = null;
		List<List<Object>> listResult = new ArrayList<List<Object>>();
		try {
			db.connect();
			myStatement = db.getMsqlConnect().createStatement(); //TODO Will fail here with null pointer exception, needs to reconnect.
			myResultSet = myStatement.executeQuery(queryRequest);
			//jsonResult = resultSetToJson(myResultSet);
			listResult = resultSetToList(myResultSet);
			db.close();
		}
		catch (SQLException myException){
		    System.out.println("SQLException: " + myException.getMessage());
		    System.out.println("SQLState: " + myException.getSQLState());
		    System.out.println("VendorError: " + myException.getErrorCode());
		}
		finally {
		    if (myResultSet != null) {
		        try {
		        	myResultSet.close();
		        } catch (SQLException myException) { }
		        myResultSet = null;
		    }
		    if (myStatement != null) {
		        try {
		            myStatement.close();
		        } catch (SQLException myException) { }
		        myStatement = null;
		    }
		}
		//return jsonResult;
		return listResult;
	}
	
	private List<List<Object>> resultSetToList(ResultSet myResultSet) {
		ResultSetMetaData rsmd;
		List<List<Object>> finalResults = new ArrayList<List<Object>>();
		try {
			rsmd = myResultSet.getMetaData();
			while (myResultSet.next()) {
				List<Object> elements = new ArrayList<Object>();
				for (int i=1; i<=rsmd.getColumnCount(); i++) {
					elements.add(myResultSet.getString(i));
				}
				finalResults.add(elements);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return finalResults;
	}
	
	@SuppressWarnings("unused")
	private String resultSetToJson(ResultSet myResultSet) {
		ResultSetMetaData rsmd;
		JsonObject jsonResponse = new JsonObject();
		try {
			rsmd = myResultSet.getMetaData();
			JsonArray data = new JsonArray();
			while(myResultSet.next() ) {
				JsonArray row = new JsonArray();
				for(int i=1; i<=rsmd.getColumnCount(); i++) {
			         row.add(new JsonPrimitive(myResultSet.getString(i))); // write key:value pairs
			    }
				data.add(row);
			}
			jsonResponse.add("Students", data);
		} catch (SQLException myException) {
			System.out.println("SQLException in resultSetToJson: " + myException.getMessage());
		}
		return jsonResponse.toString();
	}
}
