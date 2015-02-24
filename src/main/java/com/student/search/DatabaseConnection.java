package com.student.search;

/* For importing the config settings */
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/* For connecting to msql */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

	private String dbName;
	private String dbURL;
	private String dbUsername;
	private String dbPassword;
	private Connection msqlConnect;
	
	DatabaseConnection() {
		setDbProperties();
	}
	
	public Connection getMsqlConnect() {
		return msqlConnect;
	}
	
	public void connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		    msqlConnect = DriverManager.getConnection(dbURL+'/'+dbName, dbUsername, dbPassword);
		} catch (Exception myException) {
		    System.out.println("Exception in DatabaseConnection.connect(): " + myException.getMessage());
		}
	}
	
	public void close() throws SQLException {
		msqlConnect.close();
	}
	
	private void setDbProperties() {
		Properties myProperties = new Properties();
		InputStream input = null;
		try {
			input = Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties");
			
			myProperties.load(input);
			dbName = myProperties.getProperty("dbName");
			dbURL = myProperties.getProperty("dbURL");
			dbUsername = myProperties.getProperty("dbUsername");
			dbPassword = myProperties.getProperty("dbPassword");
		} catch (IOException myException) {
			myException.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException myException) {
					myException.printStackTrace();
				}
			}
		}
	}
}