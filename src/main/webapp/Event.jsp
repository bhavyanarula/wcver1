<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.Calendar" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><s:property value="eventBean.name" /></title>
<link href="/css/jquery.toolbar.css" rel="stylesheet" />
<link href="/css/Event-style.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet"
	href="//blueimp.github.io/Gallery/css/blueimp-gallery.min.css">
<link rel="stylesheet" href="/css/bootstrap-image-gallery.min.css">
<meta property="og:url"           content="https://welfarecommunity.org/event/<s:property value="eventBean.id" />" />
<meta property="og:type"          content="website" />
<meta property="og:title"         content="<s:property value="eventBean.name" />, an event by <s:property value="organizerName"/>" />
<meta property="og:description"   content="<s:property value="eventBean.evtTime"/>, 
											 <s:property value="%{eventBean.calendar.get(5)}"/> 
											 <s:property value="%{eventBean.getCalendar().getDisplayName(2, 1, new java.util.Locale(\"en\"))}" />, 
											 <s:property value="%{eventBean.getCalendar().get(1)}" /> | 
											 <s:property value="eventBean.addressBean.city"/>, 
											 <s:property value="eventBean.addressBean.state"/>" />
<meta property="og:image"         content="<s:property value="eventBean.imageURL"/>" />
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
	<!-- 
	Common JS imports: End
 ************************************************-->
 <style>
 	.pastState{
 		background-color:#65655A;
 		color:white;
 		border-color:white;
 		
 	}
 	.guideP{
	color:#adacd2;
	font-style: italic;
	
	}
	.stateDesc{
		margin-left: 20px;
		text-align:justify;
	}
	.stateName {
	    font-size: larger;
    	color: #337ab7;
    }
    .div-border{
	    border: 1px solid #ddd;
	    border-radius: 4px;
	    -webkit-box-shadow: 0 1px 1px rgba(0, 0, 0, .05);
	    box-shadow: 0 1px 1px rgba(0, 0, 0, .05);
    }
 </style>
 <script src="/js/toast.min.js"></script>
 <script src="/js/validator.js"></script>
 <script type="text/javascript" src="/js/jquery.slimscroll.min.js"></script>
 <script type="text/javascript" src="/js/jssor.slider.min.js"></script>
 <link href="/css/jssor.css" rel="stylesheet" type="text/css" />
 <script>
 window.onload = function ()	 {
	 $('.scrollDetails').slimScroll({
		    height:'95%',
		    alwaysVisible: false,
		    allowPageScroll: true,
		    position:'right',
		    distance:'0px'
		});
 }
 var popupMode = "";
 var editDivId = "";
 function openPopup(divId, mode){
	if(divId=="newWorkDiv"){
		if(mode=="edit"){
			$('#newWork').val($('#workDiv').html());
			/* var workDiv = document.getElementById("workDiv");
			document.getElementById("newWork").value = workDiv.innerHTML; */
		}
		if(mode=="create")
		{
			alert("You are now opening the event to public and volunteers, \nPlease enter some work related points for the willing volunteers' reference!");
			var strNewWork = "<s:property value='%{eventBean.workReq.replaceAll(\"\n\", \"<br>\")}' escape='false' />";
			document.getElementById("newWork").value = strNewWork.replace('<br>', '\n');
		}
		$('#'+divId).on('shown.bs.modal', function () {
			
			$('#newWorkReqForm').validator();
			
			$('#newWorkReqForm').on('submit', function (e) {
				  if (e.isDefaultPrevented()) {
					 
				  } else {
					  createWork();
					  e.preventDefault();
				  }
				});
		});
		 $('#'+divId).on('hidden.bs.modal', function (e) {
			 	$('#newWorkReqForm').unbind('submit');
			    $(this).find('form').validator('destroy');
			  });
	}
	else
	{
		$('#'+divId).on('shown.bs.modal', function () {
			var volunForm = $(this).find('form');
			volunForm.validator({
				 custom: {
					  imgFileValidator : function($el){
							  var result = false;
						  		if($el[0].value){
					  			 	var fileSizeMax = $el.data('filesize');
								    var fileSizeCheck = $el[0].files[0].size>fileSizeMax;
								    var fileTypeCheck = $el[0].files[0].type.startsWith("image");
					  					if ($el[0].files[0] && (fileSizeCheck || !fileTypeCheck)) {
					  						result = "Max file size permitted is 2MB (.gif /.jpeg /.png /.bmp)";
									    } else 
									    	result = false;
						  					
						  		}
						  		return result;
						  }
					  }
				});
		
		});
		 $('#'+divId).on('hidden.bs.modal', function (e) {
			    $(this).find('form').validator('destroy');
			    $('#newWorkReqForm').unbind('submit');
			  });
		document.volunForm.reset();
	}
	$('#'+divId).modal('show');
	
}
 
 function createWork(){
		var newWork = document.getElementById("newWork").value;
		$.getJSON('ajaxCreateNewWork',
				   {"newWork" : newWork, "eventId": "<s:property value="eventBean.id"/>" },
					function(jsonResponse) {
					   iqwerty.toast.Toast(jsonResponse.ajaxResponseDummyMsg);
					   document.getElementById("newWork").value = "";
					   $('#newWorkDiv').modal('hide');
					   setTimeout(function(){
						   window.location.href = window.location.href;
					   },3000);
					});
		return false;
	}
 function promoteEvent(targetState){
		$.getJSON('ajaxPromoteEvent',
				   {"targetState" : targetState, "eventId": "<s:property value="eventBean.id"/>" },
					function(jsonResponse) {
					   iqwerty.toast.Toast(jsonResponse.ajaxResponseDummyMsg);
					   setTimeout(function(){
						   window.location.href = window.location.href;
					   },3000);
					});
		return false;
	}
 function createNewWorkDiv(jsonResponse){
		var workDiv = document.getElementById("workDiv");
		workDiv.innerHTML = jsonResponse.newWork;
	}
 </script>
<s:if test="hasActionErrors()">
<s:actionerror/>
    <script>
    	alert(document.getElementsByClassName("errorMessage")[0].childNodes[1].childNodes[0].innerHTML);
    </script>
</s:if>
<s:if test="hasActionMessages()">
<s:actionmessage/>
    <script>
    	alert(document.getElementsByClassName("actionMessage")[0].childNodes[1].childNodes[0].innerHTML);
    </script>
</s:if>
		<%@ include file="ngoHeader.jsp" %>
	<hr>
	<div class="row">
		<div class="col-md-6 col-sm-6" style="position:relative;">  
			<div class="col-md-12 col-sm-12"  id=nameDiv>
				<p id=nameP>
					<span class="fa fa-calendar-check-o"> </span> <s:property value="eventBean.name" />
				</p>
			</div>
			<img class="img-thumbnail" alt="Event image" src="<s:property value="eventBean.imageURL"/>" style="height:400px; width:100%; " />
				<div style="position:absolute; bottom:5px; width:97%; padding:5px;">
					<span style="float:right; text-align:right; color: white; text-shadow: 2px 2px black;">
					Organized by: <a href="<s:url action='openProfile'>
												<s:param name='pageOwnerCode'><s:property value='eventBean.organizer' /></s:param> 
											</s:url>" style="color: white;">
							<s:property value="organizerName"/>
							</a>
					</span>
				</div>
				
				
		</div>
		<div class="col-md-6  col-sm-6">
			            
		   
						      		
						      		
						      	
			<div class="row">
				<div class="col-md-8 col-sm-8">
					<div class="panel panel-default col-md-12" style="height:391px;">
						<div class="panel-body scrollDetails" >
				        	<p style="text-align : justify;"><s:property escape='false' value='%{eventBean.details.replaceAll(\"\r\n\", \"<br>\")}' /></p>
				       	</div>
				    </div>
				</div>
				<div class="col-md-4 col-sm-4">
					<div class="list-group">
						 <div class="list-group-item active">
						 	<span class="badge" style="float:left;"><s:property value="%{eventBean.calendar.get(5)}"/></span>&nbsp;
						 	<s:property value="%{eventBean.getCalendar().getDisplayName(2, 1, new java.util.Locale(\"en\"))}" /> 
						 	<s:property value="%{eventBean.getCalendar().get(1)}" />
						 </div>
						 <div class="list-group-item">
							<s:property value="eventBean.evtTime"/>
						</div>
						<div class="list-group-item">
							<s:property value="eventBean.addressBean.street"/>, 
							<s:property value="eventBean.addressBean.area"/>
						</div>
						<div class="list-group-item">
							<s:property value="eventBean.addressBean.city"/>, <s:property value="eventBean.addressBean.state"/>
						</div>
					</div>
					<div class="fb-share-button" 
						data-href="https://welfarecommunity.org/event/<s:property value="eventBean.id" />" 
						data-layout="button">
					</div>
				</div>
				
			</div>
		</div>
	</div>
	<s:if test="%{#session.owner}" >
		<div class="row" id="event-menu" style="background-color: #f8f8f8; border: 1px solid #e7e7e7; margin:10px 0px 10px 0px;">
			<div class="col-md-6 col-sm-12 col-xs-12">
				<a href="<s:url action='getPhotos'>
							<s:param name="eventId"><s:property value="eventBean.id"/></s:param>
							<s:param name="from">event</s:param> 
							<s:param name="pageOwnerCode"><s:property value="pageOwnerCode"/></s:param>
						</s:url>">
					<div class="col-md-3 col-sm-3 col-xs-3 button" style="text-align:center;"><i class="fa fa-upload fa-2x"></i><br>Photos</div>
				</a>
            	<a href="<s:url action='openEditEventPage'> <s:param name="eventId"><s:property value="eventBean.id"/></s:param></s:url>">
            		<div class="col-md-3 col-sm-3 col-xs-3 button" style="text-align:center;"><i class="fa fa-edit fa-2x"></i><br>Edit</div>
            	</a>
            	<a href="<s:url action='deleteEvent'>
            				<s:param name="eventId"><s:property value="eventBean.id"/></s:param>
            			</s:url> " onclick="return confirm('Are you sure you want to delete this event?')">
            		<div class="col-md-3 col-sm-3 col-xs-3 button" style="text-align:center;"><i class="fa fa-trash fa-2x"></i><br>Delete</div>
            	</a>
      			<a href="<s:url action='showApplications'>
								<s:param name='eventId'><s:property value='eventBean.id' /></s:param>
								<s:param name='appType'>Waiting</s:param> 
							</s:url>" title="Show Applications">
 						<div class="col-md-3 col-sm-3 col-xs-3 button" style="text-align:center;"><i class="fa fa-envelope fa-2x"></i>
 						<s:if test="%{noOfNewApplications!=0}">	
 							<span class="badge" style="background-color:red;"><s:property value='noOfNewApplications' /></span>
 						</s:if>	
 						<br>Applications</div>
				</a>
	        </div>
        	<div class="col-md-6  col-sm-12 col-xs-12" style="margin-top: 1.5%;">
						<div class="col-md-3 col-sm-4 col-xs-4" style="text-align:center; position:relative;">
							<button class="btn btn-default" disabled id="state-create">Create</button>
							<i class="fa fa-long-arrow-right"></i>
							<s:if test="%{eventBean.status=='create'}">
								<i class="fa fa-check" style="position:absolute; bottom:-4px; left:calc(47%);"></i>
							</s:if>
						</div>
						<div class="col-md-3 col-sm-4 col-xs-4" style="text-align:center; position:relative;">
							<button class="btn btn-success" id="state-open" <s:if test="%{eventBean.status!='create'}">disabled</s:if> onclick='openPopup("newWorkDiv", "create")'>Open</button>
							<i class="fa fa-long-arrow-right"></i>
							<s:if test="%{eventBean.status=='open'}">
								<i class="fa fa-check" style="position:absolute; bottom:-4px; left:calc(47%);"></i>
							</s:if>
						</div>
						<div class="col-md-3 col-sm-4 col-xs-4" style="text-align:center; position:relative;">
							<button class="btn btn-simple" id="state-closed" <s:if test="%{eventBean.status!='open'}">disabled</s:if> onclick="promoteEvent('closed')">Closed</button>
							<s:if test="%{eventBean.status=='closed'}">
								<i class="fa fa-check" style="position:absolute; bottom:-4px; left:calc(47%);"></i>
							</s:if>
						</div>
						<div class="col-md-3 col-sm-1 col-xs-1">
							<a href="javascript:void();" id="event-states-help" data-toggle="tooltip" data-placement="top" title="Event states. Click for help"> 
				              <i class="fa fa-question-circle"></i>
				            </a>
				            <script>
						     	$('#event-states-help').tooltip();
						     	$('#event-states-help').click(function() {
						     		var statesTour = initializeTour("TOUR_EVENT_STATES");
						     		statesTour.restart();
						     	});
						     </script>
						</div>
				</div>
		</div>
	</s:if>
					<s:if test="%{eventBean.listOfEventPhotos.size()>0}">
		<div class="row" style="margin-bottom:25px;" id="event-photos-row">
			    				<a href="<s:url action='getPhotos'>
				    						 <s:param name="eventId"><s:property value="eventBean.id"/></s:param>
				    						 <s:param name="from">event</s:param>
				    						 <s:param name="pageOwnerCode"><s:property value="pageOwnerCode"/></s:param>
			    						  </s:url>" style="font-size:smaller; font-weight:lighter;" title="View All">
				 <div class="col-md-1 col-sm-1 col-xs-1 vertical-heading">
		    			P H O T O S
			    		</div>
	    	</a>
   			<div class="col-md-11 col-sm-11 col-xs-11">
    			<div  id="jssor_photos" style="position: relative; top: 0px; left: 0px; height: 150px; overflow: hidden; visibility: hidden;">
			        <!-- Loading Screen -->
			        <div data-u="loading" style="position: absolute; top: 0px; left: 0px;">
			            <div style="filter: alpha(opacity=70); opacity: 0.7; position: absolute; display: block; top: 0px; left: 0px; width: 100%; height: 100%;"></div>
			            <div style="position:absolute;display:block;background:url('images/loading.gif') no-repeat center center;top:0px;left:0px;width:100%;height:100%;"></div>
				</div>
			        <div data-u="slides" id="causes-slides" style="cursor: default; position: relative; top: 0px; left: 0px; width: 100%; height: 150px; overflow: hidden;">
			        	<s:iterator value="eventBean.listOfEventPhotos">
					    			<div class="col-md-4 col-sm-4 thumbnail">
								<a href="<s:property value="url.replace(\"_thumb\",\"\")" />" title="" data-gallery  >
			    					<img src='<s:property value="url" />' style="height:200px; ">
										</a>
					    			</div>
					    		</s:iterator>
				    		</div>
			        <!-- Bullet Navigator -->
			        <div data-u="navigator" class="jssorb03" style="bottom:10px;right:10px;">
			            <!-- bullet navigator item prototype -->
			            <div data-u="prototype" style="width:21px;height:21px;">
			                <div data-u="numbertemplate"></div>
			            </div>
			        </div>
			        <!-- Arrow Navigator -->
			        <span data-u="arrowleft" class="jssora03l" style="top:0px;left:8px;width:55px;height:55px;" data-autocenter="2"></span>
			        <span data-u="arrowright" class="jssora03r" style="top:0px;right:8px;width:55px;height:55px;" data-autocenter="2"></span>
		    	</div>
						</div>
	  	</div>
	</s:if>
	<s:if test="%{noOfAcceptedApplications!=0 || eventBean.status=='open'}" >
	<div class="row" id="volun-row">
		<div class="panel panel-default" style="max-height:450px">
			<div class="panel-heading">
					Volunteers Section <s:if test="%{eventBean.status!='closed'}">
					        		<span style="color:red;">
									<s:if test="%{eventBean.status=='open'}">
										(We are currently looking for volunteers for this event!)
									</s:if>
									<s:if test="%{eventBean.status=='active'}">
										(We are no longer looking for volunteers for this event!)
									</s:if>
										<s:if test="%{eventBean.status=='create'}">
											Event is not yet open!
										</s:if>
									</span>
									</s:if>
				</div>
				<div class="panel-body"  id="volunPanelBody"  style="max-height:400px">
									<s:if test="%{eventBean.status=='open'}">
						<div class="col-md-3 col-sm-3" style="border-right:1px solid #ddd;">
								<div class="row">
						        	<span class="pageHeading" style="font-size:17px;">Work Requirements:</span>
						        	<s:if test="%{#session.guest && eventBean.status=='open'}" >
						        		<button class="btn btn-success" id="cause" style="margin-right:3px;" onclick='openPopup("volunDiv", "create")'>Apply</button>
						        	</s:if>
						        	<s:if test="%{#session.owner && eventBean.status=='open'}">
						        		<button class="btn btn-default" style="margin-right:3px;" onclick='openPopup("newWorkDiv", "edit")'>Edit</button>
						        	</s:if>
					        	</div>
										<p id="workDiv"><s:property escape='false' value='%{eventBean.workReq.replaceAll(\"\n\", \"<br>\")}' /></p>
						</div>
					        </s:if>
	        		
				    <s:if test="%{noOfAcceptedApplications>0}">
	        			<div class="col-md-9 col-sm-9" style="border-left:1px solid #ddd;" id="acc-app">
					    <div class="row" style="margin-bottom:20px;">
		    		
					    	<div class="col-md-12 col-sm-12" id="jssor_vol" style="position: relative; top: 0px; left: 0px; height: 150px; overflow: hidden; visibility: hidden;">
						        <!-- Loading Screen -->
						        <div data-u="loading" style="position: absolute; top: 0px; left: 0px;">
						            <div style="filter: alpha(opacity=70); opacity: 0.7; position: absolute; display: block; top: 0px; left: 0px; width: 100%; height: 100%;"></div>
						            <div style="position:absolute;display:block;background:url('images/loading.gif') no-repeat center center;top:0px;left:0px;width:100%;height:100%;"></div>
						        </div>
						        <div data-u="slides" id="causes-slides" style="cursor: default; position: relative; top: 0px; left: 0px; width: 100%; height: 150px; overflow: hidden;">
						        	<s:iterator value="volunteerList" var="indx">
						        		<div style="border:0.5px solid #ddd; text-align:center; padding:5px;"> 
					    					<img src='<s:property value="photoUrl" />' style="height:100px; width:100px;" />
							    			<p style="width:100px; text-align:center;"><s:property value="name" /></p>
						        		</div>
						    		</s:iterator>
						        </div>
						        <!-- Bullet Navigator -->
						        <div data-u="navigator" class="jssorb03" style="bottom:10px;right:10px;">
						            <!-- bullet navigator item prototype -->
						            <div data-u="prototype" style="width:21px;height:21px;">
						                <div data-u="numbertemplate"></div>
						            </div>
						        </div>
						        <!-- Arrow Navigator -->
						        <span data-u="arrowleft" class="jssora03l" style="top:0px;left:8px;width:55px;height:55px;" data-autocenter="2"></span>
						        <span data-u="arrowright" class="jssora03r" style="top:0px;right:8px;width:55px;height:55px;" data-autocenter="2"></span>
						    </div>
						    <p ><s:property value="noOfAcceptedApplications"/> Application(s) accepted. </p>
					    </div>
					  	</div>
				    </s:if>
		    	</div>
	    	</div>
	    </div>
	</s:if>   
    <script src="/js/event-jssor.js"></script>
    <div id="disqus_thread" class="panel panel-default" style="padding:10px; max-height:"></div>
		<script>
		
		/**
		 *  RECOMMENDED CONFIGURATION VARIABLES: EDIT AND UNCOMMENT THE SECTION BELOW TO INSERT DYNAMIC VALUES FROM YOUR PLATFORM OR CMS.
		 *  LEARN WHY DEFINING THESE VARIABLES IS IMPORTANT: https://disqus.com/admin/universalcode/#configuration-variables */
		
		var disqus_config = function () {
		    this.page.url = "https://welfarecommunity.org/event/<s:property value="eventBean.id"/>";  // Replace PAGE_URL with your page's canonical URL variable
		    this.page.identifier = "wc_evt_"+<s:property value="eventBean.id"/>; // Replace PAGE_IDENTIFIER with your page's unique identifier variable
		    this.page.title = "Events in WelfareCommunity : <s:property value="eventBean.name"/>";
		};
		
		(function() { // DON'T EDIT BELOW THIS LINE
		    var d = document, s = d.createElement('script');
		    s.src = '//wecomm-org.disqus.com/embed.js';
		    s.setAttribute('data-timestamp', +new Date());
		    (d.head || d.body).appendChild(s);
		})();
		</script>
		<noscript>Please enable JavaScript to view the <a href="https://disqus.com/?ref_noscript">comments powered by Disqus.</a></noscript>
	</div>				
         <!-- The Bootstrap Image Gallery lightbox, should be a child element of the document body -->
	<div id="blueimp-gallery"
		class="blueimp-gallery blueimp-gallery-controls"
		data-use-bootstrap-modal="false">
		<!-- The container for the modal slides -->
		<div class="slides"></div>
		<!-- Controls for the borderless lightbox -->
		<h3 class="title"></h3>
		<a class="prev"><</a> <a class="next">></a> <a class="close">X</a> <a
			class="play-pause"></a>
		<ol class="indicator"></ol>
		<!-- The modal dialog, which will be used to wrap the lightbox content -->
		<div class="modal fade">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" aria-hidden="true">&times;</button>
						<h4 class="modal-title"></h4>
					</div>
					<div class="modal-body next"></div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default pull-left prev">
							<i class="glyphicon glyphicon-chevron-left"></i> Previous
						</button>
						<button type="button" class="btn btn-primary next">
							Next <i class="glyphicon glyphicon-chevron-right"></i>
						</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	    <div class="modal fade" tabindex="-1" role="dialog" id="newWorkDiv">
		  <div class="modal-dialog">
			<div class="modal-content">
			  <div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title">Add some Work Requirements related points</h4>
			  </div>
			  <form role="form" id="newWorkReqForm">
				  <div class="modal-body">
					<div class="col-md-12 col-xs-12 form-group">
						<s:textarea cssClass="form-control" placeholder="Work Requirements..." id="newWork" rows="5" data-minlength="100"
						 required="" data-error="Minimum 100 characters required!!" maxlength="1000"></s:textarea>
						<div class="help-block with-errors"></div>
					  </div>
				  </div>
				  <div class="modal-footer">
					<button type="submit" class="btn btn-primary">Save</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
				  </div>
			  </form>
			</div><!-- /.modal-content -->
		  </div><!-- /.modal-dialog -->
		</div><!-- /.modal -->
		<div class="modal fade" tabindex="-1" role="dialog" id="volunDiv">
		  <div class="modal-dialog">
			<div class="modal-content">
			  <div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title">Please fill in some of your personal details for application.</h4>
			  </div>
			  <form enctype="multipart/form-data" method="post" action="/saveVolunteerInfo"  role="form" name="volunForm">
			  	<div class="modal-body">
				  	<s:hidden name="eventId" value="%{eventBean.id}"></s:hidden>
				  	<s:hidden name="pageOwnerCode" value="%{pageOwnerCode}"></s:hidden>
				  	<div class="row">
					  <div class="col-md-6 col-xs-6 form-group">
					    <label for="name">Name *</label>
					    <s:textfield cssClass="form-control" id="name" placeholder="Name" name="volunteerBean.name" value="%{volunteerBean.name}"
					     required="" data-error="Required Field!!" maxlength="59"></s:textfield>
					    <div class="help-block with-errors"></div>
					  </div>
					 </div>
					 <div class="row">
						  <div class="col-md-6 col-xs-6 form-group">
						    <label for="email">Email ID *</label>
						    <s:textfield type="email" cssClass="form-control" id="email"  
						    placeholder="Email" name="volunteerBean.email" value="%{volunteerBean.email}" required="" 
						    data-error="Email address is invalid!" maxlength="49"> </s:textfield>
						    <div class="help-block with-errors"></div>
				  		</div>
				  	</div>
				  	<div class="row">
						<div class="col-md-3 col-xs-4 form-group">
							<label class="description" for="element_10">Contact Number *</label>
							<s:textfield id="element_10" name="volunteerBean.contact" cssClass="form-control" required=""  
							value="%{volunteerBean.contact}" type="number" size="12"></s:textfield>
							<div class="help-block with-errors"></div>
						</div>
				  	</div>
				  	<div class="row"> 
					  <div class="form-group col-md-3">
					    Age<s:textfield cssClass="form-control" type="number" min="0" id="age" name="volunteerBean.age" value="%{volunteerBean.age}"  ></s:textfield>
					    <div class="help-block with-errors"></div>
					  </div>
					</div>
					<div class="row"> 
					  <div class="form-group col-md-4">
					  	Gender<br>
					  	<label class="radio-inline"><input type="radio" name="volunteerBean.gender" value="Male" checked="checked">Male</label>
						<label class="radio-inline"><input type="radio" name="volunteerBean.gender" value="Female">Female</label>
					  </div>
					</div>
					<div class="row">
					  	<div class="col-md-5 col-xs-6 form-group">
						  	<label for="element_6">Passport photo </label>
							<s:file id="element_6" name="imgFile" cssClass="form-control" data-filesize="1048576" data-img-file-validator = "imgFileValidator">
							</s:file>
							<div class="help-block with-errors">Max file size permitted is 1MB (.gif /.jpeg /.png /.bmp)</div>
						</div>
				  	</div>
		  		</div>
				  <div class="modal-footer">
					<s:submit cssClass="btn btn-primary" name="submit" value="Submit and Apply"></s:submit>
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
				  </div>
			  </form>
			</div><!-- /.modal-content -->
		  </div><!-- /.modal-dialog -->
		</div><!-- /.modal -->
	<script src="//blueimp.github.io/Gallery/js/jquery.blueimp-gallery.min.js"></script>
	<script src="/js/bootstrap-image-gallery.min.js"></script>
	
	<%@ include file="footer.jsp"%>
</body>
</html>