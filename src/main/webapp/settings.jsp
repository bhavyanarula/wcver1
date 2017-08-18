<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Settings</title>
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
<script src="/js/validator.js"></script>
<script src="/js/toast.min.js"></script>
<script>
$(document).ready(function() {
	
	$('#state').change(function(event) {
		loader.start();
		var state = $("select#state").val().toString();
		$.getJSON('ajaxStateChangeAction',
				{selectedState : state},
				function(jsonResponse) {
					loader.stop();
					var select = $('#city');
					select.find('option').remove();
					$.each(jsonResponse.cityList,
							function(key,value) {
								$('<option>').val(value).text(value).appendTo(select);
							});
					$( "#city" ).trigger( "change" );
				});
		});
	$('#city').change(function(event) {
		loader.start();
		var city = $("select#city").val().toString();
		$.getJSON('ajaxCityChangeAction', {selectedCity : city}, function(jsonResponse) {
			loader.stop();
			var select = $('#pincode');
			select.find('option').remove();
			$.each(jsonResponse.pincodeList, function(key,value) {$('<option>').val(value).text(value).appendTo(select);
			});
			$( "#pincode" ).trigger( "change" );
		});
	});
	$('#pincode').change(function(event) {
		loader.start();
		var pincode = $("select#pincode").val().toString();
		$.getJSON('ajaxPincodeChangeAction', {selectedPincode : pincode}, function(jsonResponse) {
			loader.stop();
			var select = $('#area');
			select.find('option').remove();
			$.each(jsonResponse.areaList, function(key,value) {$('<option>').val(value).text(value).appendTo(select);
			});
		});
	});
	
});
	var addArr = [];
	
	function addToAddArray(a)
	{
		addArr.push(a);
	}
	
	function openPopup(){
		$('#newAddressModal').modal('toggle');
		$('#newAddressModal').on('shown.bs.modal', function () {
		    $(this).find('form').validator('destroy').validator();
		});
	}
	
	<s:iterator value="addressBeanList" status="status">
		addToAddArray({'code':'<s:property value="code" />', 'state':'<s:property value="state" />',
			'city':'<s:property value="city" />',
			'area':'<s:property value="area" />', 'pincode':'<s:property value="pincode" />',
			'street':'<s:property value="street" />'
			});
	</s:iterator>
	
	
	var currAddIndex = -1;
	
	var currAddress = "";
	
	function nextAdd()
	{
		currAddIndex++;
		setAdd(currAddIndex);
		if(currAddIndex>=addArr.length-1)
			document.getElementById("bAddNext").setAttribute("disabled", true);
		else
			document.getElementById("bAddNext").removeAttribute("disabled");
		if(currAddIndex>0)
			document.getElementById("bAddPrevious").removeAttribute("disabled");
		else
			document.getElementById("bAddPrevious").setAttribute("disabled", true);
	}
	function previousAdd()
	{
		currAddIndex--;
		setAdd(currAddIndex);
		if(currAddIndex==0)
			document.getElementById("bAddPrevious").setAttribute("disabled", true);
		else
			document.getElementById("bAddPrevious").removeAttribute("disabled");
		if(currAddIndex<addArr.length-1)
			document.getElementById("bAddNext").removeAttribute("disabled");
		else
			document.getElementById("bAddNext").setAttribute("disabled", true);
	}
	
	function setAdd(i)
	{
		var pState, pCity, pPincode, pArea, pStreet;
		pState = document.getElementById("pState");
		pCity = document.getElementById("pCity");
		pPincode = document.getElementById("pPincode");
		pArea = document.getElementById("pArea");
		pStreet = document.getElementById("pStreet");
		currAddress = addArr[i];
		if(addArr.length){
			pState.innerHTML = "State: "+currAddress.state;
			pCity.innerHTML = "City: "+currAddress.city;
			pPincode.innerHTML = "Pincode: "+currAddress.pincode;
			pArea.innerHTML = "Area: "+currAddress.area;
			pStreet.innerHTML = "Street: "+currAddress.street;
			document.getElementById("bAddDelete").removeAttribute("disabled");
		}
		else{
			pState.innerHTML = "";
			pCity.innerHTML= "";
			pPincode.innerHTML= "";
			pArea.innerHTML= "";
			pStreet.innerHTML = "";
			document.getElementById("bAddDelete").setAttribute("disabled", true);
		}
	}
	
	function deleteAddress(){
		if(addArr.length==1){
			alert("An NGO must have an address, add another one before you delete this!")
		} else {
			if (confirm("Are you sure you want to delete this location")) {
				loader.start();
				$.getJSON('ajaxDeleteAddressAction',
						   {"currentAddressCode" : currAddress.code},
							function(jsonResponse) {
							   loader.stop();
							   if(jsonResponse.ajaxResponseDummyMsg=="Successfully deleted!")
							   {
								   debugger;
								   for(var i=0;i<addArr.length;i++)
								   {
								   		if(addArr[i].code==jsonResponse.currentAddressCode)
								   			addArr.splice(i, 1);
								   }
								   currAddIndex = -1;
								   nextAdd();
							   }
							   iqwerty.toast.Toast(jsonResponse.ajaxResponseDummyMsg);
							});
				
			} 
		}
	 }

	function addNewAddress()
	{
		var state = $("select#state").val().toString();
		var city =  $("select#city").val().toString();
		var pincode =  $("select#pincode").val().toString();
		var area =  $("select#area").val().toString();
		var street =  $("#street").val().toString();
		loader.start();
		$.getJSON('ajaxAddNewAddressAction',
				   {"addressBean.state" :state,
					"addressBean.city" : city,
					"addressBean.pincode" : pincode,
					"addressBean.area" : area,
					"addressBean.street" : street
				   },
					function(jsonResponse) {
					   loader.stop();
					   if(jsonResponse.ajaxResponseDummyMsg=="Successfully added!")
					   {
						   addToAddArray({'code':jsonResponse.newAddressBean.code,'state':jsonResponse.newAddressBean.state,
							'city':jsonResponse.newAddressBean.city,
							'area':jsonResponse.newAddressBean.area, 'pincode':jsonResponse.newAddressBean.pincode,
							'street':jsonResponse.newAddressBean.street
							});
						   currAddIndex = -1;
						   nextAdd();
						   $('.nun').css("display", "block");
					   	}
					   iqwerty.toast.Toast(jsonResponse.ajaxResponseDummyMsg);
					   
					});
		return false;	
	}
	
	function addNewCause()
	{
		var newMasterCauseCode =  $("select#newCause").val().toString();
		loader.start();
		$.getJSON('ajaxAddNewCauseAction',
				   {"newMasterCauseCode" : newMasterCauseCode},
					function(jsonResponse) {
					   loader.stop();
					   if(jsonResponse.ajaxResponseDummyMsg=="Successfully added!")
					   {
						   var newCauseCode = jsonResponse.newCauseCode;
						   var newCauseName = jsonResponse.newCauseName;
						   var causeListForm = document.getElementById('causeListForm');
						   var apHTML1 = "<div id =\"cDiv"+newCauseCode+"\" >";
						   var apHTML2 =  "<input type=\"checkbox\" name=\""+newCauseCode+"\" value=\""+newCauseName+"\" onchange=\"enableDelButton()\" />";
						   var apHTML3 = newCauseName+"</div>";
						   causeListForm.innerHTML+=apHTML1+apHTML2+" "+apHTML3;
						   $("select option[value='"+newCauseCode+"']").remove();
						  
					   }
					   iqwerty.toast.Toast(jsonResponse.ajaxResponseDummyMsg);
					});
		
	}
	function deleteCause(){
		var checkdCause = $( "input:checked" ).length;
		var dntDelCause = ($('#causeListForm').children().length == 1) || ($('#causeListForm').children().length == checkdCause );
			
		if(dntDelCause){
			alert("An NGO must have atleast one Cause, add another one before you delete this/all!")
		} else {
			var r = confirm("Are you sure you want to delete this Cause");
			if (r == true) {
				var cboxs =$( "input:checked" );
				var toBeDeletedArr=[];
				for(var i = 0; i<cboxs.length; i++)
				{
					toBeDeletedArr.push(cboxs[i].name);
				}
				jQuery.ajaxSettings.traditional = true;
				loader.start();
				$.getJSON('ajaxDeleteCauseAction',
						{'deleteCauseCodeArray' : toBeDeletedArr},
						function(jsonResponse) {
							loader.stop();
							iqwerty.toast.Toast(jsonResponse.ajaxResponseDummyMsg);
							 var causeSelect = document.getElementById("newCause");
							   
							for(var i = 0; i<cboxs.length; i++)
							{
								var divEle = document.getElementById('cDiv'+cboxs[i].name);
								divEle.remove();
								causeSelect.innerHTML = causeSelect.innerHTML + "<option value=\""+cboxs[i].name+"\">"+cboxs[i].value+"</option>";
							}
						}
				);
				jQuery.ajaxSettings.traditional = false;
			}
		}
	 }
	function enableDelButton()
	{
		var bDelete = document.getElementById("bCDelete");
		
		var cboxs =$( "#causeListForm :checked" );
		if(cboxs.length===0)
			bDelete.style.display = "none";
		else
			bDelete.style.display = "block";
	}
</script>
<s:if test="hasActionErrors()">
<p style="display:none"><s:actionerror/></p>
        <script>
    	alert(document.getElementsByClassName("errorMessage")[0].childNodes[1].childNodes[0].innerHTML);
        </script>
</s:if>
	<%@ include file="ngoHeader.jsp" %>
	<hr>
		<div class="row">
			<div class="col-md-3 ">
				<h3 class="pageHeading">Profile</h3>
			</div>
		</div>
		<hr></hr>
		<div class="row">
			<div class="col-md-6">
				<ul class="list-group">
					<div class="row">
						<li class="list-group-item active">
							Edit Profile 
						</li>
						<li class="list-group-item">
							<a href="<s:url action='editProfile'></s:url>" title="Edit Profile">Edit your Profile</a><br>
							<p style="font-size:smaller;">(name, description, contact..)</p>
						</li>
					</div>
					<div class="row">
						<li class="list-group-item active">
								Change Password
						</li>
						<li class="list-group-item">
							<form method="post" action="updatePassword" role="form" id="passwordChangeForm">
								<div class="col-md-12 col-xs-12 form-group">
									<label for="password">Old Password</label>
								    <s:password cssClass="form-control" id="oldPassword" placeholder="Password" name="oldPassword" 
								    required="" data-error="Required Field!!"></s:password>
								    <div class="help-block with-errors"></div>
							    </div>
								<div class="col-md-12 col-xs-12 form-group">
									<label for="password">New Password</label>
								    <s:password cssClass="form-control" id="newPassword" placeholder="Password" name="newPassword" 
								    required="" data-error="Required Field!!" data-minlength-error="Minimum 6 characters are required." data-minlength="6" maxlength="12"></s:password>
								    <div class="help-block with-errors"></div>
							    </div>
								<div class="col-md-12 col-xs-12 form-group">
									<label for="repassword">Retype new Password</label>
								    <s:password cssClass="form-control" id="repassword" placeholder="Retype password" 
								    value="" required="" data-error="Required Field!!" data-match="#newPassword" data-match-error="Both Password fields do not match!"
								    data-minlength-error="Minimum 6 characters are required." data-minlength="6" maxlength="12"></s:password>
								    <div class="help-block with-errors"></div>
							    </div>
							    <s:submit cssClass="btn btn-default" type="submit" name="submit" value="Submit" ></s:submit>
							</form>
							<script>
								$('#passwordChangeForm').validator();
							</script>
						</li>
					</div>
					<div class="row">
						<li class="list-group-item active">
							Change your logo image
						</li>
						<li class="list-group-item">
							<form enctype="multipart/form-data" method="post" action="updateNgoLogo" id="logoForm">
								
								<div class="col-md-12 col-xs-12 form-group">
								  	<label for="element_6">Logo </label>
									<s:file id="element_6" name="imgFile" cssClass="form-control" 
									required=""  data-filesize="1048576" data-img-file-validator = "imgFileValidator">
									</s:file>
									<div class="help-block">Max file size permitted is 1MB (.gif /.jpeg /.png /.bmp)</div>
								</div>
								<button type="submit" name="Update" class="btn btn-default">Update</button>
							</form>
							<script>
							$(document).ready(function() {
								$('form').validator({
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
						</script>
						</li>
					</div>
					
					<div class="row">
						<li class="list-group-item active">
							Delete your Welfare Community Account
						</li>
						<li class="list-group-item">
							<a href="<s:url action='deleteAccount'></s:url>"
									   title="Delete Account" onclick="return confirm('Are you sure you want to delete your account? All your traces will be deleted')"><i class="fa fa-trash fa-2x"></i></a>
						</li>
					</div>
					
					
		  		</ul>
			</div>
			<div class="col-md-6">
				<div class="row">
					<li class="list-group-item active">
						Add/Remove Locations
					</li>
					<li class="list-group-item">
						<p id="pState"></p>
						<p id="pCity"></p>
						<p id="pPincode"></p>
						<p id="pArea"></p>
						<p id="pStreet"></p><br />
						
						<div class="btn-group" role="group" aria-label="..." id="addButtonGroup">
							<button type="button" class="btn btn-default nun" id="bAddPrevious" onclick="previousAdd()">Previous</button> 
							<button type="button" class="btn btn-default nun" id="bAddNext" onclick="nextAdd()">Next</button>
							<button type="button" class="btn btn-danger nun" id="bAddDelete" onclick="deleteAddress()">Delete</button>
							<button class="btn btn-primary" onclick='openPopup()'>Add more</button>
						</div>
						<s:if test="addressBeanList.size()==0">
							<script>$('.nun').css("display", "none");</script>
						</s:if>
						<script>nextAdd();</script>
					</li>
				</div>
				<div class="row">
					<li class="list-group-item active">
						Add/Remove Causes
					</li>
					<li class="list-group-item">
					<div class="row">
						<div class="col-md-5">
							<form id="causeListForm">
								<s:iterator value="causeBeanList" status="status">
									<div id ="cDiv<s:property value="causeCode" />" >
										<input type="checkbox" name="<s:property value="causeCode" />" value="<s:property value="causeName" />" onchange="enableDelButton()"/>
										<s:property value="causeName" /><br>
									</div>
								</s:iterator>
							</form>
							<hr>
							<button type="button" class="btn btn-default" id="bCDelete" onclick="deleteCause()" style="display:none;">Delete Selected</button>
						</div>
						<div class="col-md-7">
							<s:select list="causeList" cssClass="form-control" id="newCause" name="causeName"></s:select>
							<button class="btn btn-default" onclick='addNewCause()'>Add more</button>
						</div>
					</div>
					</li>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" tabindex="-1" role="dialog" id="newAddressModal">
	  <div class="modal-dialog">
		<div class="modal-content">
		  <div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
			<h4 class="modal-title">Add new Center for your NGO</h4>
		  </div>
		  	<form role="form" id="">
		  	<div class="modal-body">
			  	<div class="row">
					<div class="col-md-6 col-xs-6 form-group">
						<label>State</label>
						<s:select list="stateList" id="state" name="addressBean.state" cssClass="form-control" required="">
						</s:select>
						<div class="help-block with-errors"></div>
					</div>
					<div class="col-md-6 col-xs-6 form-group">
						<label>City</label>
						<s:select list="cityList" cssClass="form-control" id="city" name="addressBean.city" required=""></s:select>
						<div class="help-block with-errors"></div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-6 col-xs-6 form-group">
						<label>Pincode</label>
						<s:select list="pincodeList" id="pincode" name="addressBean.pincode" cssClass="form-control" required=""></s:select>
						<div class="help-block with-errors"></div>
					</div>
					<div class="col-md-6 col-xs-6 form-group">
						<label>Area</label>
						<s:select list="areaList" cssClass="form-control" id="area" name="addressBean.area" required=""> </s:select>
						<div class="help-block with-errors"></div>
					</div>				  
			  	</div>
				<div class="row">
					<div class="col-md-12 col-xs-12 form-group">
						<label>Street/Colony/PlotNo.</label>
						<input id="street" name="addressBean.street" class="form-control" value="" type="text" required="">
						<div class="help-block with-errors"></div>
					</div>
				</div>
			  </div>
			  <div class="modal-footer">
			  	 <div class="form-group">
				    <button type="submit" class="btn btn-primary"  onclick="return addNewAddress()">Add New</button>
					<button type="button" class="btn btn-simple" data-dismiss="modal">Cancel</button>
				 </div>
			  </div>
			  </form>
		</div><!-- /.modal-content -->
	  </div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
</div>
<%@ include file="footer.jsp" %>
</body>
</html>