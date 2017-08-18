<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><s:property value="eventListHeading"/></title>

</head>
<body>
<%@ include file="header.jsp" %>
<!-- ********************************************
	Common JS imports: Start
 -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="/js/heartbeat.js"></script>
	<script type="text/javascript" src="/WelfareCommunity/js/jquery.slimscroll.min.js"></script>
	
<!-- 
	Common JS imports: End
 ************************************************-->
	
<s:if test="%{eventListHeading=='NGO Events'}">
	<%@ include file="ngoHeader.jsp" %>
</s:if>
<s:else>
	<div class="row" style="height:54px;">..</div>
	<div class="container">
</s:else>
		<div class="panel panel-default">
       		<div class="panel-heading row">
       			<div class="col-md-3 col-sm-6 col-xs-6 pull-left"><s:property value="eventListHeading"/></div>
       			<s:if test="%{#session.owner}">
	       			<div class="col-md-1 col-sm-6 col-xs-6 pull-right">
		       			<a href="<s:url action='openNewEventForm'><s:param name="pageOwnerCode">
								<s:property value="pageOwnerCode"/></s:param></s:url>"
							title="Create New Event">
								<i class="fa fa-calendar-plus-o fa-2x" style="color:black"></i>
						</a>
					</div>
				</s:if>
       			<form action="getListOfEvents" method="get" id="eventSearch">
       				<input type="hidden" name="pageOwnerCode" value="<s:property value="pageOwnerCode"/>" />
       				<div class="form-group col-md-1 col-sm-2 col-xs-2 pull-right">
	       				<button type="submit" class="btn btn-simple">Search</button>
	       			</div> 
	       			<div class="form-group col-md-2  col-sm-4 col-xs-4 pull-right">
	       				<select name="eventMonth" id="eventMonth" class="form-control" >
	       					<option value="-1">--- Month ---</option>
	       					<option value="0">January</option>
	       					<option value="1">February</option>
	       					<option value="2">March</option>
	       					<option value="3">April</option>
	       					<option value="4">May</option>
	       					<option value="5">June</option>
	       					<option value="6">July</option>
	       					<option value="7">August</option>
	       					<option value="8">September</option>
	       					<option value="9">October</option>
	       					<option value="10">November</option>
	       					<option value="11">December</option>
	       				</select>
	       			</div>
	       			<div class="form-group col-md-2 col-sm-4 col-xs-4 pull-right">
	       				<select name="eventYear" id="eventYear" class="form-control">
	       					<option value="-1">--- Year ---</option>
	       					<option value="2016">2016</option>
	       					<option value="2015">2015</option>
	       					<option value="2017">2017</option>
	       				</select>
	       			</div>
	       		</form>
	       		<script type="text/javascript">
		       		$(window).load(function() {
		       			$( "#eventYear" ).val("<s:property value="eventYear"/>");
		       			$( "#eventMonth" ).val("<s:property value="eventMonth"/>");
		       			var pPageHref = $( "#prevPage" ).attr("href");
		       			$( "#prevPage" ).attr("href", pPageHref+"&eventMonth="+"<s:property value="eventMonth"/>"+"&eventYear="+"<s:property value="eventYear"/>");
		       			var nPageHref = $( "#nextPage" ).attr("href");
		       			$( "#nextPage" ).attr("href", nPageHref+"&eventMonth="+"<s:property value="eventMonth"/>"+"&eventYear="+"<s:property value="eventYear"/>");
		       			
		       		});
	       		</script>
       			
       		</div>
			<div class="panel-body">
				<s:if test="%{eventBeanList.size()==0}">
					No <s:if test="%{start!=0}">more </s:if> results..!!
				</s:if>
				 <s:iterator value="eventBeanList">
					 <a href="<s:url action='openEventPage'> 
					 <s:param name="eventId"><s:property value="id"/></s:param>
					 <s:param name="pageOwnerCode"><s:property value="organizer"/></s:param>
					 </s:url>">
					 	 <div class="col-md-6 col-sm-6 col-xs-12" style=" background-color: rgba(245, 245, 245, 0.42); border:1px solid #dddddd; color:black;">
							<div class="row">
								<img class="thumbnail" src="<s:property value="imageURL"/>" style="height:180px; margin:0 auto;">
							  </div>
							<div class="row">
							    <p class="media-heading" style="font-size:larger; text-align: center;"><s:property value="name"/></p>
							</div>
							    <div class="row">
								<div class="col-md-6 col-sm-6 col-xs-12 pull-left">
									   	<span class="badge" style="float:left;"><s:property value="%{calendar.get(5)}"/></span>&nbsp;
									 	<s:property value="%{calendar.getDisplayName(2, 1, new java.util.Locale(\"en\"))}" /> 
									 	<s:property value="%{calendar.get(1)}" /><br><s:property value="evtTime"/>
									 </div>
								 <div class="col-md-6 col-sm-6 col-xs-12 pull-right">
								   		<p style="text-align: right;"><s:property value="addressBean.city"/>, <s:property value="addressBean.state"/></p>
								   	</div>
							   	</div>
							   	<hr class="no-margin">
							<div class="row scroll" style="text-align: center;">
							   		<p><s:property value="details"/></p>
							</div>
						</div>
					</a>
				 </s:iterator>
				 <nav style="width:165px; margin:0px auto;">
				  <ul class="pagination">
				    <li <s:if test="%{start==0}">class="disabled"</s:if>>
				      <a id="prevPage" href="<s:url action='getListOfEvents'>
			    					<s:param name="pageOwnerCode"><s:property value="pageOwnerCode"/></s:param>
			    			      	<s:param name="start"  value="%{start-1}" />
		              </s:url>">
		             ...Previous</a>
				        
				    </li>
				    <li <s:if test="%{!hasNext}">class="disabled"</s:if>>
				    	<a id="nextPage" href="<s:url action='getListOfEvents'>
			    					<s:param name="pageOwnerCode"><s:property value="pageOwnerCode"/></s:param>
			    			      	<s:param name="start"  value="%{start+1}" />
			              </s:url>">
			            Next...</a>
				    </li>
				  </ul>
				</nav>
			</div>
		</div>
		<script>
			$('.scroll').slimScroll({
			    height:'150',
			    alwaysVisible: false,
			    allowPageScroll: true,
			    position:'right'
			});
		</script>
	</div>
	<%@ include file="footer.jsp" %>
</body>
</html>