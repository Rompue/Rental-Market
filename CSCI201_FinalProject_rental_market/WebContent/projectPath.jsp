<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>


<%
String path = request.getContextPath();

System.out.println(path);
%>

${pageContext.request.contextPath}