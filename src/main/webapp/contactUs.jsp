<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Contact Us</title>
<style>
	.contactDivs{
		border: 1px solid #ddd;
		padding:15px !important;
		height: auto;
	}
</style>
</head>
<body>
<%@ include file="header.jsp" %>

<!-- ********************************************
	Common JS imports: Start
 -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="/js/heartbeat.js"></script>
	
<!-- 
	Common JS imports: End
 ************************************************-->

	<%@ include file="ngoHeader.jsp" %>
	<hr>
	<div class="row">
		<h3 class="pageHeading">Contact us</h3>
	</div>
	<hr>
	<div class="row">
		<div class="col-md-6" style="border: 1px solid #ddd; padding:15px !important;">
			<div class="col-md-1">
					<i class="fa fa-phone fa-2x"></i>
			</div>
			<div class="col-md-5">
				<s:property value="#session.pageOwnerBean.ngoPhone" />
			</div>
		</div>
		<div class="col-md-6" style="border: 1px solid #ddd; padding:15px !important;">
			<div class="col-md-1">
					<i class="fa fa-envelope fa-2x"></i>
			</div>
			<div class="col-md-5">
				<s:property value="#session.pageOwnerBean.ngoEmail" />
			</div>
		</div>
	</div>
	<div class="row">
		<s:iterator value="ngoAddressBeanList">
			<div class="col-md-6 contactDivs">
				<div class="col-md-1">
					<i class="fa fa-home fa-2x"></i>
				</div>
				<div class="col-md-5">
					<s:property value="street"/>,<br>
					<s:property value="area"/>,<br>
					<s:property value="city"/> - <s:property value="pincode"/>,<br>
					<s:property value="state"/>, India
				</div>
			</div>
		</s:iterator>
	</div>
	<br> <br>
</div>
<%@ include file="footer.jsp" %>
</body>
</html>