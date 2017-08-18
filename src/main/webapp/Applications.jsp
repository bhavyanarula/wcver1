<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><s:property value="eventBean.name" /></title>


</head>
<body>
	<%@ include file="header.jsp"%>
	<!-- ********************************************
	Common JS imports: Start
 -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="/js/heartbeat.js"></script>
	<script>
		
		function enableARDiv()
		{
			debugger;
			var bDelete = document.getElementById("ARDiv");
			bDelete.style.display = "block";
			var cboxs =$( "form :checked" );
			if(cboxs.length===0)
				bDelete.style.display = "none";
		}
		function setVIDParam(a){
			var cboxs =$( "form :checked" );
			var vIdsArr=new Array(cboxs.length);
			for(var i=0; i<cboxs.length; i++)
			{
				vIdsArr[i] = cboxs[i].value;
			}
			a.href = a.href + "&vIdArr="+vIdsArr.toString();
			debugger;
		}
	</script>
	<!-- 
	Common JS imports: End
 ************************************************-->

<%@ include file="ngoHeader.jsp" %>
<div class="panel panel-default">
	<div class="panel-heading row">
		 <div class="col-md-6">Volunteer Applications for the event 
		 <a href="<s:url action='openEventPage'> 
				 		<s:param name="eventId"><s:property value="eventId"/></s:param>
				 		<s:param name="pageOwnerCode"><s:property value="pageOwnerCode"/></s:param>
				 </s:url>"><s:property value='eventBean.name' /></a></div>
		 <div class="input-group-btn col-md-1" style="width:8.3%;">
	        <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
	        Showing: <s:property value="appType" /> <span class="caret"></span></button>
	        <ul class="dropdown-menu dropdown-menu-right">
	        	
	          <li><a href="<s:url action='showApplications'>
        						<s:param name='eventId'><s:property value='eventBean.id' /></s:param> 
        						<s:param name='appType'>Waiting</s:param> 
        					</s:url>" >Waiting</a></li>
	          <li><a href="<s:url action='showApplications'>
        						<s:param name='eventId'><s:property value='eventBean.id' /></s:param> 
        						<s:param name='appType'>Accepted</s:param> 
        					</s:url>" >Accepted</a></li>
	          <li><a href="<s:url action='showApplications'>
        						<s:param name='eventId'><s:property value='eventBean.id' /></s:param> 
        						<s:param name='appType'>Rejected</s:param> 
        					</s:url>" >Rejected</a></li>
	          <li><a href="<s:url action='showApplications'>
        						<s:param name='eventId'><s:property value='eventBean.id' /></s:param> 
        						<s:param name='appType'>All</s:param> 
        					</s:url>" >All</a></li>
	        </ul>
	      </div>
	</div>
	<div class="panel-body">
		
	 	<div class="col-md-6">
	 		<s:if test="%{appType==\'Waiting\'}">
			 	<div class="row" style="background-color: #f5f5f5;border:1px solid #ddd;">
					<div class="col-md-3 pull-right">
						<div id="ARDiv" style="display:none;">
							<a  href="<s:url action='acceptApplication'>
						 						 <s:param name='eventId'>
						 						 	<s:property value='%{eventId}'/>
						 						 </s:param>
						 					</s:url>" onClick="setVIDParam(this)">Accept </a>
						 			/ 
						 	<a href="<s:url action='rejectApplication'>
						 						 <s:param name='eventId'>
						 						 	<s:property value='%{eventId}'/>
						 						 </s:param>
						 					</s:url>" onClick="setVIDParam(this)"> Reject</a>
					 	</div>
					</div>
				</div>
			</s:if>
		<br>
	 		<s:iterator value="volAppList">
	 			<div class="row">
			 		<div class="col-md-3">
			 			<img src = "<s:property value="photoUrl" />" style="height:100px; width:100px;">
			 		</div>
			 		<div class="col-md-6">
			 			<div class="row"><s:property value="name"/></div>
			 			<div class="row"><s:property value="email"/></div>
			 			<div class="row"><s:property value="contact"/></div>
			 			<div class="row"><s:property value="age"/> yrs (<s:property value="gender"/>)</div>
			 		</div>
			 		<s:if test="%{appType==\'Waiting\'}">
				 		<div class="col-md-3 pull-right">
				 			<form><input value="<s:property value='id'/>" type="checkbox" onchange="enableARDiv()"/></form>
				 		</div>
			 		</s:if>
			 	</div>
			 	<hr>
	 		</s:iterator>
	 		
	 		<nav>
			  <ul class="pagination">
			    <li <s:if test="%{start==0}">class="disabled"</s:if>>
			      <a href="<s:url action='showApplications'>
	   						<s:param name='eventId'><s:property value='eventBean.id' /></s:param> 
			             	<s:param name="start"  value="%{start-1}" />
		             </s:url>" class="btn btn-default" >
		             ...Previous</a>
			        
			    </li>
			    <li <s:if test="%{!hasNext}">class="disabled"</s:if>>
			    <a href="<s:url action='showApplications'>
	   						<s:param name='eventId'><s:property value='eventBean.id' /></s:param> 
			             	<s:param name="start"  value="%{start+1}" />
		             </s:url>" class="btn btn-default" >
		            Next...</a>
			       
			      </a>
			    </li>
			  </ul>
		</nav>
	 	</div>
	 </div>
 </div>
 </div>
 <%@ include file="footer.jsp" %>
 </body>
 </html>
 	