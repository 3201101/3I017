<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!doctype html>
<%
// on récupère les valeurs nécessaire à l'affichage
String title = (String)request.getAttribute("title");
%>
<html>
<head>
	<title><%= title %></title>
</head>
<body>
    <h1><%= title %></h1>
</body>
</html>
