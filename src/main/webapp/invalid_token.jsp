<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Duplicate Request</title>
</head>
<body>
<%@ include file="header.jsp" %>
	<div class="container" style="margin-top:54px;">
	<div class="page-header row img-circle" 
	style="background-color:#337ab7; color:white; text-align:center; vertical-align:center;
	width:300px; height: 300px; margin:30px auto; padding-top:65px; ">
	  <h1 style="font-size:50px; text-shadow: 3px 0 white;">!!!!<br><small style="color:white; ">Duplicate Request</small></h1>
	</div>
	<div class="row">
		<div class="col-md-8" style="margin:5px auto;">
		  <h3>User information is not updated, duplicate request detected.</h3>
			<h4>Possible Reasons are:</h4>
			<ul class="list-group">
			    <li class="list-group-item">Back button usage to submit form again</li>
			    <li class="list-group-item">Double click on Submit button</li>
			    <li class="list-group-item">Using "Reload" Option in browser</li>
			</ul>
			<s:if test="hasActionErrors()">
			    <s:actionerror/>
			</s:if>
		</div>
	</div>
	</div>
	
<%@ include file="footer.jsp" %>
</body>
</html>