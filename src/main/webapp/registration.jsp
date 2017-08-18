<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" import="java.util.*, beans.*"%>
<%!String mode;%>
<%
	try	{
		
		String mode = ""+request.getAttribute("mode");
		if("".equals(mode) || mode==null || mode.equals("null"))
			mode="create";
	
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
<meta name="viewport" content="width=device-width, initial-scale=1"></meta>

<title>Register</title>

</head>
<body id="main_body" >
<%@ include file="header.jsp" %>
	<!-- ********************************************
	Common JS imports: Start
 -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
	
<!-- 
	Common JS imports: End
 ************************************************-->
<script src="/js/validator.js"></script>
<script src="/js/toast.min.js"></script>
<script type="text/javascript" src="/js/selectize.js"></script>
<link rel="stylesheet" type="text/css" href="/css/selectize.css" />

<style>
.popover
{
	width:410px;
}
.guideP{
	color:#adacd2;
	font-style: italic;
}
.has-feedback label ~ .form-control-feedback {
    top: 25px;
}
</style>
<script>
var oEmail, oAlias;
$(document).ready(function(){
    $('[data-toggle="popover"]').popover({
        html: true, 
    	content: function() {
              return $('#popover-content').html();
            }
    });

});


$(document).ready(function() {
	
	$('#state').change(function(event) {
		loader.start();
		var state = $("select#state").val().toString();
		$.getJSON('ajaxStateChangeAction',
				{selectedState : state},
				function(jsonResponse) {
					$('#ajaxResponse').text(jsonResponse.dummyMsg);
					var select = $('#city');
					select.find('option').remove();
					$.each(jsonResponse.cityList,
							function(key,value) {
								$('<option>').val(value).text(value).appendTo(select);
							});
					$( "#city" ).trigger( "change" );
					loader.stop();
				});
		});
	$('#city').change(function(event) {
		loader.start();
		var city = $("select#city").val().toString();
		$.getJSON('ajaxCityChangeAction', {selectedCity : city}, function(jsonResponse) {
			$('#ajaxResponse').text(jsonResponse.dummyMsg);
			var select = $('#pincode');
			select.find('option').remove();
			$.each(jsonResponse.pincodeList, function(key,value) {$('<option>').val(value).text(value).appendTo(select);
			});
			$( "#pincode" ).trigger( "change" );
			loader.stop();
		});
	});
	$('#pincode').change(function(event) {
		loader.start();
		var pincode = $("select#pincode").val().toString();
		$.getJSON('ajaxPincodeChangeAction', {selectedPincode : pincode}, 
			function(jsonResponse) {
				$('#ajaxResponse').text(jsonResponse.dummyMsg);
				var select = $('#area');
				select.find('option').remove();
				$.each(jsonResponse.areaList, function(key,value) {$('<option>').val(value).text(value).appendTo(select);
				loader.stop();
				});
			});
	});
	
});

</script>
<s:if test="%{mode=='edit'}">
	<%@ include file="ngoHeader.jsp" %>
</s:if>
<s:else>
	<div class="row" style="height:54px;">..</div>
	<div class="container">
</s:else>
		<div class="row">
			<div class="col-md-6 col-sm-6"><h3 class="pageHeading"><s:property value="pageHeading" /></h3></div>
			<s:if test="%{mode!='edit'}">
				<div class="pull-right">
					<button class="btn btn-primary" name="showAutoGenModal" onclick="showAutoGenModal()">Fetch from auto-listed NGOs</button>
				</div>
				<script>
					$(document).ready(function() {showAutoGenModal()});
				</script>
			</s:if>
		</div>
		
		<hr></hr>
		<form enctype="multipart/form-data" method="post" action="Register"  role="form">
		<s:token />
		  <div class="row">
			  <div class="col-md-4 col-xs-12 form-group has-feedback">
			    <label for="email">Email ID (to be used for login)</label>
			    <s:textfield type="email" cssClass="form-control" id="email" disabled="%{mode=='edit'}" 
			    placeholder="joe.smith@xyz.com" name="ngoBean.ngoEmail" value="%{ngoBean.ngoEmail}" required="" 
			     data-email-validator = "emailValidator" maxlength="100"> </s:textfield>
			     <span class="fa form-control-feedback" aria-hidden="true"></span>
			    <div class="help-block with-errors"></div>
			  </div>
		  </div>
		  <s:if test="%{mode!='edit'}">
			  <div class="row">
			  	<div class="col-md-4 col-xs-12 form-group has-feedback">
				    <s:password cssClass="form-control" id="password" placeholder="Password" name="password" value="%{password}" required="" 
				    data-error="Minimum 6 characters are required." data-minlength="6" maxlength="12"></s:password>
				    <span class="fa form-control-feedback" aria-hidden="true"></span>
				    <div class="help-block with-errors" ></div>
				</div>
				<div class="col-md-4 col-xs-12 form-group has-feedback">
				    <s:password cssClass="form-control" name="repassword" id="repassword" placeholder="Retype password" value="" required="" 
				    data-match="#password" data-match-error="Both Password fields do not match!" 
				    data-minlength="6" maxlength="12"></s:password>
				    <span class="fa form-control-feedback" aria-hidden="true"></span>
				    <div class="help-block with-errors">Both password fields must match!!</div>
				</div>
			  </div>
		  </s:if>
		  <div class="row">
			  <div class="col-md-4 col-xs-12 form-group has-feedback">
			   <label for="name">Name of your Organization</label>
			    <s:textfield cssClass="form-control" id="name" placeholder="Abc Def GHI" name="ngoBean.ngoName" value="%{ngoBean.ngoName}" 
			    required="" maxlength="100" data-error="Required Field!!"></s:textfield>
			    <div class="help-block with-errors"></div>
			    <span class="fa form-control-feedback" aria-hidden="true"></span>
			  </div>
			  <div class="col-md-4 col-xs-12 form-group has-feedback">
			  	<label for="alias">Alias (short name)</label>
			    <s:textfield cssClass="form-control" id="alias" disabled="%{mode=='edit'}"  placeholder="alias name" name="ngoBean.alias" 
			    value="%{ngoBean.alias}" required="" data-alias-validator="1" data-minlength="2" maxlength="20" oninput="aliasEg(this);"></s:textfield>
			    <span class="fa form-control-feedback" aria-hidden="true"></span>
			    <script>
			    	function aliasEg(that){
			    		if(that.value)
			    			$('#aliasExample')[0].innerHTML='Your URL- https://welfarecommunity.org/'+that.value;
			    		else
			    			$('#aliasExample')[0].innerHTML='';
			    	}
			    </script>
			    <div class="help-block with-errors"></div>
				<!-- <p class="guideP">Give a Alias name to your ngo to use it as a URL for direct public access!</p> -->
				<p id="aliasExample" class="guideP"></p>
		  	  </div>
		  </div>
		  	
				    
		  <s:if test="%{mode!='edit'}">
		  <hr></hr>
			  <div class="row">
				<div class="col-md-4 col-xs-12">
				<p><b>Address:</b></p>
					<div class="row">
						<div class="col-md-6 col-xs-12 form-group">
							<label for="element_9_5">State</label>
							<s:select list="stateList" id="state" name="addressBean.state" cssClass="form-control" value="%{addressBean.state}"
							required="" data-error="Required Field!"></s:select>
							<div class="help-block with-errors"></div>
						</div>
						<div class="col-md-6 col-xs-12 form-group">
							<label for="element_9_6">City</label>
							<s:select list="cityList" cssClass="form-control" id="city" name="addressBean.city" value="%{addressBean.city}"
							required="" data-error="Required Field!"></s:select>
							<div class="help-block with-errors"></div>
						</div>
					</div> 
					<div class="row">
						<div class="col-md-6 col-xs-12 form-group">
							<label for="element_9_4">Pincode</label>
							<s:select list="pincodeList" id="pincode" name="addressBean.pincode" cssClass="form-control"
							required="" data-error="Required Field!"></s:select>
							<div class="help-block with-errors"></div>
						</div>
						<div class="col-md-6 col-xs-12 form-group">
							<label for="element_9_7">Area</label>
							<s:select list="areaList" cssClass="form-control" id="area" name="addressBean.area"
							required="" data-error="Required Field!"></s:select>
							<div class="help-block with-errors"></div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-12 col-xs-12 form-group">
							 <s:textfield id="street" name="addressBean.street" cssClass="form-control" value="%{addressBean.street}" 
							 required="" data-error="Required Field!" placeholder="Street/Colony/PlotNo."> </s:textfield>
							 <div class="help-block with-errors"></div>
						</div>
					</div>
					<!-- <p class="guideP">(You can add more centers once you create your profile.)</p> -->
				</div>
				<div class="col-md-4 col-xs-12 form-group">
				 	<p><b>Cause:</b></p>
					<div class="row">
				 		<label for="element_9_7">you work for</label>
						<s:select list="ngoCauseList" cssClass="form-control" id="cause" name="causeBean.causeCode"></s:select>
						<p class="guideP">(You can add more causes once you create your profile.)</p>
					</div>
				</div>
			</div>
			</s:if>
		  <s:else>
			  <div class="row">
			  	<div class="col-md-8 col-xs-12 form-group">
				    <label for="description">Introduction</label>
				    <s:textarea cssClass="form-control" rows="7" id="description" placeholder="A brief introduction about your organization in minimum 100 words.." name="ngoBean.ngoDescription" 
				    value="%{ngoBean.ngoDescription}" data-minlength="100" maxlength="1000" required="" 
				    data-error="Have a descriptive description about your NGO in minimum 100 characters!!" ></s:textarea>
				    <div class="help-block with-errors"></div>
			  	</div>
			  </div>
			  <div class="row">
		  		<div class="col-md-1 col-xs-12 form-group">
				    <label for="ngoNoOfVolunteers">Volunteers</label>
				    <s:textfield cssClass="form-control" id="ngoNoOfVolunteers" 
				    name="ngoBean.noOfVolunteers" value="%{ngoBean.noOfVolunteers}" type="number"></s:textfield>
				</div>
				<div class="col-md-2 col-xs-12 form-group">
					<label for="phone">Contact Number</label>
					<s:textfield id="phone" name="ngoBean.ngoPhone" cssClass="form-control" value="%{ngoBean.ngoPhone}" placeholder="XXXX XXX XXX"
					type="number" maxlength="12" data-minlength="10" required="" data-error="Required Field! Min-Length of 10."></s:textfield>
					<div class="help-block with-errors"></div>
				</div>
		  	</div>
		</s:else>
		<s:hidden id="autoGenId" name="autoGenId"></s:hidden>
			<s:if test="%{mode=='edit'}">
				<div class="form-group">
					<s:submit id="saveForm" cssClass="btn btn-primary" type="submit" name="submit" value="Update Changes" ></s:submit>
					<button type="button" class="btn btn-default" type="button" onclick="javascript: history.back()">Cancel</button>
				</div>
			</s:if>
			<s:else>
				<div class="form-group">
					<s:submit id="saveForm" cssClass="btn btn-primary" type="submit" name="submit" value="Create Profile" ></s:submit>
				</div>
			</s:else>
		</form>
		</div>
		<s:if test="%{mode!='edit'}">
			<div class="modal fade" tabindex="-1" role="dialog" id="alertModal">
			  <div class="modal-dialog">
			    <div class="modal-content">
			      <div class="modal-header">
			        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
			        <h4 class="modal-title">Select from auto-generated profiles</h4>
			      </div>
			      <div class="modal-body">
			      	<p><strong>We have a list of auto-generated profiles in our database, 
			      	please type in your organization name to check for the entry.</strong><br></br>
			      	If you find your NGO/NPO in our database, then<br></br>
			      	&nbsp;&nbsp;&nbsp;&nbsp;choose "Ok" to put basic details in the form<br></br>
					&nbsp;&nbsp;&nbsp;&nbsp;or "Cancel" to start a fresh process.
			      	</p> 
			        <select id="ngoList" >
			        	<option value="<s:property value="alreadySetNgoId" />"><s:property value="alreadySetNgoName" /></option>
			        </select>
			        <script>
			        var globalTimeout;
						$('#ngoList').selectize({
							valueField: 'uid',
							labelField: 'ngoName',
							searchField: 'ngoName',
							options: [],
							openOnFocus : true,
							create: false,
							render: {
								option: function(item, escape) {
									return "<option value="+item.uid+">"+item.ngoName+"</option>";
								}
							},
							load: function(query, callback) {
								if (!query.length) return callback();
								if (globalTimeout) clearTimeout(globalTimeout);
							    globalTimeout = setTimeout(function(){
								$.getJSON('ajaxGetAutoGenNgoNames', {"query" : query},
						  				function(jsonResponse) {
											callback(jsonResponse.autoGenNgos);
										});
								}, 1500);
							}
						});
						function fillInDetails(){
							var sel = $('#ngoList');
							var id = sel[0].value;
							loader.start();
							$.getJSON('ajaxFillInAction', {"fillInId" : id}, 
								function(jsonResponse) {
									loader.stop();
									$('#name')[0].value=jsonResponse.fillInBean.ngoName;
									$('#alias')[0].value=jsonResponse.fillInBean.alias;
									oAlias = jsonResponse.fillInBean.alias;
									$('#email')[0].value=jsonResponse.fillInBean.ngoEmail;
									oEmail=jsonResponse.fillInBean.ngoEmail;
									var addressBean = jsonResponse.fillInBean.ngoAddressBeanList[0];
									$.ajaxSetup({async: false});
									$('#state')[0].value = addressBean.state;
									$( "#state" ).trigger( "change" );
									$('#city')[0].value = addressBean.city;
									//$( "#city" ).trigger( "change" );
									$('#pincode')[0].value = addressBean.pincode;
									//$( "#pincode" ).trigger( "change" );
									$('#area')[0].value = addressBean.area;
									$('#street')[0].value = addressBean.street;
									$.ajaxSetup({async: true});
									var causeBean = jsonResponse.fillInBean.ngoCauseBeanList[0];
									$('#cause')[0].value = causeBean.causeCode;
									$('#alertModal').modal('hide');
									$('#autoGenId').value = oEmail;
								});
						}
						
						</script>
			      </div>
			      <div class="modal-footer">
			      	<button class="btn btn-primary" name="ok" value="Fill in" onclick="fillInDetails()">Select</button>
			        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
			      </div>
			    </div><!-- /.modal-content -->
			  </div><!-- /.modal-dialog -->
			</div><!-- /.modal -->
			<script>
			function showAutoGenModal(){
				$('#alertModal').modal('show');
			};
			
			</script>
		</s:if>
		
	<script>
		$('form').validator({
			feedback:{
				success: 'fa fa-check-circle',
				  error: 'fa fa-exclamation'
			  },
			  delay: 200,
			  focus: false,
			  custom: {
				  emailValidator : function($el)
				  	{
				  		var result = false;
				  		if(<%=!((String)request.getAttribute("mode")).equals("create")%>)
				  			return false;
				  		if($el[0].value){
				  			$.ajaxSetup({async: false});
					  		$.getJSON('emailValidationAction', {"ngoBean.ngoEmail" : $el[0].value, "notValidate": oEmail},
				  				function(jsonResponse) {
					  				if(jsonResponse.ajaxResponseDummyMsg=="validEmail")
					  					result = false;
					  				else
					  					result = "This email is already registered with us. ";
								});
					  		$.ajaxSetup({async: true});
				  		}
				  		return result;
				  	},
			  	aliasValidator : function($el)
				  	{
				  		var result = false;
				  		if(<%=!((String)request.getAttribute("mode")).equals("create")%>)
				  			return false;
			  			$.ajaxSetup({async: false});
				  		if($el[0].value){
				  			$.getJSON('aliasNameValidationAction', {"ngoBean.alias" : $el[0].value, "notValidate": oAlias},
				  				function(jsonResponse) {
					  				if(jsonResponse.ajaxResponseDummyMsg=="validAlias")
					  					result = false;
					  				else
					  					result = "This Alias name is already used, Please enter a different one!";
								});
				  		}
				  		$.ajaxSetup({async: true});
				  		return result;
				  	},
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
	</script>
		<%@ include file="footer.jsp" %>
  </body>
</html>
<%
}
	catch(NullPointerException e) {
		e.printStackTrace();
		RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
		rd.forward(request,response);
	}
%>