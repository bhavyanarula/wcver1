<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" import="java.util.*, util.Constants"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Search Results</title>
<link href="/css/searchResults-style.css" rel="stylesheet" type="text/css">

</head>
<body>

	<%@include file="header.jsp" %>
<!-- ********************************************
	Common JS imports: Start
 -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="/js/heartbeat.js"></script>
	
<!-- 
	Common JS imports: End
 ************************************************-->
<script src="/js/bootstrap-select.js"></script>
<link href="/css/bootstrap-select.css" rel="stylesheet" type="text/css">

<script>

var searchAction = '<s:property value="searchAction" />' ;
var profileType = '<s:property value="profileType" />' ;
$(document).ready(function() {

	if(profileType === 'user'){
		$('#pt2').prop('checked', true);
	} else {
		$('#pt1').prop('checked', true);
	}
	$('#state').change(function(event) {
		loader.start();
		var state = $("select#state").val().toString();
		$.getJSON('ajaxStateChangeSearchAction',
				{selectedState : state},
				function(jsonResponse) {
					$('#ajaxResponse').text(jsonResponse.dummyMsg);
					var select = $('#city');
					select.find('option').remove();
					$.each(jsonResponse.cityList,
							function(key,value) {
								$('<option>').val(value).text(value).appendTo(select);
							});
					$('.selectpicker').selectpicker('refresh');
					loader.stop();
				});
		});
	$('#sCauseList').on('rendered.bs.select', function (e) {
		$('#inpCauseList')[0].value = $('#selectedList')[0].innerHTML;
	});
	
	$('#sCauseList').on('loaded.bs.select', function (e) {
		<%String strCauses = request.getParameter("sCauseList");
			if(strCauses!=null && !strCauses.equals(""))
				strCauses = strCauses.replace(", ", "','");
		%>
		$('#sCauseList').selectpicker('val',['<%=strCauses%>']);
	});
});
function performAdvanceSearch(){
	var causesSelected = false;
	var citySelected = false;
	var stateSelected = false;
	causesSelected = $('#sCauseList').val();
	citySelected = $('#city').val();
	stateSelected  = $('#state').val();
	if(causesSelected && (stateSelected!='All' || citySelected!='All'))
		searchAction = "sbcl";
	if(causesSelected && (!(stateSelected!='All') && !(citySelected!='All')))
		searchAction = "sbc2";
	if(!causesSelected && (stateSelected!='All' || citySelected!='All'))
		searchAction = "sbl";
	if(searchAction==='as' || searchAction==='')
	{
		alert("No parameters to search! Please select any filter to perform appropriate search.")
		return false;
	}
	$('#searchAction2').attr("value", searchAction);
	return true;
}
	


</script>

<div class="row" style="height:54px;">..</div>
<div class="container">
	<div class="panel panel-default row">
       		<div class="panel-heading">Advanced Search</div>
			<div class="panel-body">
			<s:form action="advanceSearch" name="advSearchForm" method="get">
				<div class="row">
					<div class="col-md-3">
						<s:hidden  name="sCauseList" id="inpCauseList"></s:hidden>
						<s:hidden  name="searchAction" id="searchAction2"></s:hidden>
						Causes:<br>
						<select class="selectpicker" id="sCauseList" data-live-search="true" multiple title="All"
						data-icon-base="fa" data-tick-icon="fa-check-circle-o" data-width='222' data-show-content="true">
						  <s:iterator value="selectCauseList" var="i">
						  	<option value="<s:property value='#i.value' />"><s:property value="#i.value" /></option>
						  </s:iterator>
						</select>
					</div>
					<div class="col-md-3">
						State:<br>
						<s:select list="stateList" id="state" name="sState" cssClass="selectpicker" title="All"
						value="%{sState}"></s:select>
					</div>
					<div class="col-md-3">
						City:<br>
						<s:select list="cityList"  id="city" name="sCity" cssClass="selectpicker" title="All"
						value="%{sCity}"></s:select>
					</div>
					<div class="col-md-2">
						Auto generated<br> or User Profiles:
						<div class="radio">
						  <label>
						    <input type="radio" name="profileType" id="pt1" value="auto">
						    AUTO
						  </label>
						</div>
						<div class="radio">
						  <label>
						    <input type="radio" name="profileType" id="pt2" value="user">
						    USER
						  </label>
						</div>
					</div>
					<div class="col-md-1">
						<br>
						<s:submit name="submit" cssClass="btn btn-simple" value="Search" onClick = "return performAdvanceSearch()"></s:submit>
					</div>
				</div>
             	 <s:hidden name="start" id="start" value="0"/>
			</s:form>
		</div>
	</div>
	<div class="row">
		<s:if test="hasActionErrors()">
		   <div class="errors">
		      <s:actionerror/>
		   </div>
		</s:if>
		<div class="panel panel-default">
       		<div class="panel-heading">Search Results:</div>
			<div class="panel-body">
				<s:if test="%{searchResults.size() == 0}">
					No matching results found..
				</s:if>
				<s:else>
				<ul>
					 <s:iterator value="searchResults">
					 <li>
						<a href="<%=Constants.ROOTURL%>/<s:property value="alias"/>">
						<div class="media">
						  <div class="media-left">
						      <img class="media-object thumbnail" src="<s:property value="ngoLogoUrl"/>" alt="..." style="width:96px; height:96px;">
						  </div>
						  <div class="media-body row">
							<div class="col-md-10">
								<div class="row">
									<strong style="color: #043f40; font-size:18px;"><s:property value="ngoName"/> </strong>
								</div>
								<s:if test="%{!ngoCauseBeanList.isEmpty()}">
								<div class="row">
										<p style="color:#43754a;"><s:iterator value="ngoCauseBeanList"><s:property value="causeName"/>&nbsp; &nbsp; </s:iterator></p>
								</div>
								</s:if>
								<s:if test="%{ngoAddressBeanList.size()!=0}">
								<div class="row">
									<s:iterator value="ngoAddressBeanList">
								    		<p style="color: gray;">
								    			<strong style="color: black;"><s:property value="city"/></strong> - 
								    			<s:property value="street"/>, <s:property value="area"/>, <s:property value="city"/>, <s:property value="state"/>
								    		</p>
							    	</s:iterator>
								</div>
								</s:if>
							</div>						  	
							<div class="col-md-2">
								<div class="row">
									<s:if test="%{type==\"user\"}">
										<i class="fa fa-user" title="This is a user generated profile"></i>
									</s:if>
									<s:else>
										<i class="fa fa-laptop" title="This is an auto generated profile"></i>
									</s:else>
								</div>
								<s:if test="%{type==\"user\"}">
									<div class="row">
										<s:property value="noOfAppreciations"/> 
										<i class="fa fa-thumbs-o-up" title="<s:property value="noOfAppreciations"/> appreciation(s)"></i>
									</div>
								</s:if>
							</div>
						  </div>
						</div>
					 	</a>
					 </li>
					 <hr>
					 </s:iterator>
				 </ul>
				 </s:else>
			</div>
		</div>
		<nav style="width:165px; margin:0px auto;">
		  <ul class="pagination">
		    <li <s:if test="%{start==0}">class="disabled"</s:if>>
		      <a href="/advanceSearch.action?<s:property value="asQuery"/><s:property value="%{start-1}"/>"> Prev </a>
		    </li>
		    <li <s:if test="%{!hasNext}">class="disabled"</s:if>>
		     <a href="/advanceSearch.action?<s:property value="asQuery"/><s:property value="%{start+1}"/>"> Next </a>
		 
		       
		    </li>
		  </ul>
		</nav>
     </div>
</div>
			
<%@ include file="footer.jsp" %>
</body>
</html>