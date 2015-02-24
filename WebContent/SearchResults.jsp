<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Rock And Roll Clown Academy</title>
</head>
<body>
<h1>Student Directory</h1>
   <form action="search">
      <label for="namesQuery">Search Student Names</label><br/>
      <s:textfield name="namesQuery" value="%{namesQuery}" />
      <input type="submit" value="Search"/>
   </form>
   
<h2>Search Results</h2>
<table width="50%">
	<tr>
		<td style="width:25%"><b>First Name</b></td>
		<td style="width:25%"><b>Last Name</b></td>
		<td style="width:40%"><b>Email</b></td>
		<td style="width:10%"><b>GPA</b></td>
	</tr>
<s:iterator var="currentStudent" value="%{resultsList}">
	<tr>
		<s:iterator value="#currentStudent" begin="1">
			<td><s:property /></td>
		</s:iterator>
	<s:form action="details">
		<s:hidden name="student" value="%{#currentStudent}" />
		<s:submit value="Details" />
	</s:form>
	</tr>
</s:iterator>
</table>
</body>
</html>