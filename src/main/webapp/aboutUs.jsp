<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><s:property value="#session.pageOwnerBean.ngoName" /> - WelfareCommunity</title>
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
<script src="/js/masonry.js"></script>
<script src="/js/toast.min.js"></script>
<script src="/js/validator.js"></script>
<style>
.grid{
 margin-top:20px;
}
.grid-item {
	float: left;
}
 .circle {
border-radius: 10%;
display: inline-block;
margin-right: 20px;
}

.vignette {
	border-radius: 30%;
    width: 300px;
    margin: 1em auto;
    box-shadow: 50px 50px 113px #f5f5f5 inset,-50px -50px 110px #f5f5f5 inset;
    height: 300px;
    background-size: cover;
    background-position: center;
    background-repeat: no-repeat;
}
.guideP{
	color:#adacd2;
	font-style: italic;
}
.largeFa {
	font-size: 24px;
	cursor: pointer;
	padding : 5px;
}
.largeFa:hover{
	background-color: black;
	color: white;
	
}
</style>

 	<%@ include file="ngoHeader.jsp" %>
 	
	<script>
	
	  var $grid = $('.grid').masonry({
		  itemSelector: '.grid-item', // use a separate class for itemSelector, other than .col-
		  columnWidth: '.grid-sizer',
		  percentPosition: true
		});
  <s:if test="%{#session.owner}" >
	function createNewAboutUs(){
		var newAboutUsHeading = document.getElementById("newAboutUsHeading").value;
		var newAboutUsContent = document.getElementById("newAboutUsContent").value;
		var fData = $('#newAboutUsForm')[0];
		var formData = new FormData(fData);
		$.ajax({
            url : 'ajaxSaveNewAboutUs',
            data : formData,
            type : 'POST',
            processData: false,
            contentType: false,
            success : function(jsonResponse) {
				   iqwerty.toast.Toast(jsonResponse.ajaxResponseDummyMsg);
				   document.getElementById("newAboutUsHeading").value = "";
				   document.getElementById("newAboutUsContent").value = "";
				   createNewAboutUsDiv(jsonResponse);
				}
        });
		$('#newAboutUsDiv').modal('toggle');
		return false;
	}
	</s:if>
	function createNewAboutUsDiv(jsonResponse){
		var dCont = document.getElementById("defaultContent");
		if(dCont)
			dCont.remove();
		var aboutUsRowDiv = document.getElementById("aboutUsRows");
		var auCode = jsonResponse.newAboutUsCode;
		var auHeading = jsonResponse.newAboutUsHeading;
		var auContent = jsonResponse.newAboutUsContent;
		var auImage = jsonResponse.newAboutUsPhoto;
		
		var gridItem = document.createElement("div");
		gridItem.setAttribute('class', 'grid-item col-md-4');
		
		var panel = document.createElement("div");
		panel.setAttribute("class", "panel panel-default");
		panel.setAttribute("id", auCode+"Div");
		panel.style.padding = '4px';
		
		var headDiv = document.createElement("div");
		headDiv.setAttribute("class", "row");
		
		
		var hPDiv = document.createElement("div");
		hPDiv.setAttribute("class", "col-md-8");
		
		var headP = document.createElement("p");
		headP.setAttribute("id", auCode+"Heading");
		headP.style.textAlign = 'right';
		headP.style.fontSize= 'large';
		headP.style.fontFamily= 'segoe ui';
		headP.innerHTML = auHeading;
		hPDiv.appendChild(headP);
		
		<s:if test="%{#session.owner}">
			
			var hBDiv = document.createElement("div");
			hBDiv.setAttribute("class", "col-md-4 pull-right");
			var b1 = document.createElement("i");
			b1.setAttribute("class", "largeFa fa fa-edit pull-right");
			b1.setAttribute("onclick", "return openPopup("+auCode+",\"edit\")");
			/* b1.innerHTML = "Edit"; */
			var b2 = document.createElement("i");
			b2.setAttribute("class", "largeFa fa fa-trash pull-right");
			b2.setAttribute("onclick", "return deleteThisAboutUs("+auCode+")");
			/* b2.innerHTML = "Delete"; */
			hBDiv.appendChild(b1);
			hBDiv.appendChild(b2);
			headDiv.appendChild(hBDiv);
		</s:if>
		
		headDiv.appendChild(hPDiv);
		
		
		var imageDiv = document.createElement("div");
		imageDiv.setAttribute("class", "row");
		imageDiv.style.textAlign = 'center';
		imageDiv.innerHTML = "<p id = '"+auCode+"Image' class=\"vignette\" style=\"background-image: url("+auImage+");\"></p>";
		
		var contentDiv = document.createElement("div");
		contentDiv.setAttribute("class", "row");
		
		var contentP = document.createElement("p");
		contentP.setAttribute("id", auCode+"Content");
		contentP.style.textAlign = 'center';
		contentP.style.padding = '10px';
		contentP.innerHTML = auContent;
		contentDiv.appendChild(contentP);
		
		panel.appendChild(headDiv);
		panel.appendChild(imageDiv);
		panel.appendChild(contentDiv);
		
		gridItem.appendChild(panel);
		
	  var $grid = $('.grid').masonry({
		  itemSelector: '.grid-item', // use a separate class for itemSelector, other than .col-
		  
		  percentPosition: true
		});
		  
		$grid.masonry().append( gridItem ).masonry( 'appended', gridItem );
		return false;
	}
	<s:if test="%{#session.owner}" >
	
	function updateThisAboutUs(code){
		var newAboutUsHeading = document.getElementById("newAboutUsHeading").value;
		var newAboutUsContent = document.getElementById("newAboutUsContent").value;
		var fData = $('#newAboutUsForm')[0];
		var formData = new FormData(fData);
		formData.append("toBeUpdatedCode",code);
		$.ajax({
            url : 'ajaxUpdateThisAboutUs',
            data : formData,
            type : 'POST',
            processData: false,
            contentType: false,
            success : function(jsonResponse) {
            	iqwerty.toast.Toast(jsonResponse.ajaxResponseDummyMsg);
				   /* document.getElementById("newAboutUsHeading").value = "";
				   document.getElementById("newAboutUsContent").value = ""; */
				   
				   document.getElementById(code+"Heading").innerHTML = jsonResponse.newAboutUsHeading;
				   document.getElementById(code+"Content").innerHTML = jsonResponse.newAboutUsContent;
				   if(jsonResponse.newAboutUsPhoto)
				  		$('#'+code+"Image").css('background-image', 'url(' + jsonResponse.newAboutUsPhoto + ')');
				   var $grid = $('.grid').masonry({
						  itemSelector: '.grid-item', // use a separate class for itemSelector, other than .col-
						  percentPosition: true
						});
				   $grid.masonry();
				}
        });
		$('#newAboutUsDiv').modal('toggle');
		return false;
	}
	var popupMode = "";
	var editCode = "";
	$( document ).ready( function ()	 {
	$('#newAboutUsDiv').on('shown.bs.modal', function (e) {
		if(popupMode=="edit")
		  $(this).find('form').validator();
		else {
		  $(this).find('form').validator('destroy').validator({
			  custom:{
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
		}
		  $('#newAboutUsForm').on('submit', function (e) {
		    if (e.isDefaultPrevented()) {
		    } else {
		    	e.preventDefault(); 
				if(popupMode=="edit")
					updateThisAboutUs(editCode);
				else
					createNewAboutUs();
				
		    }
		  });
		});
	
	
	$('#newAboutUsDiv').on('hidden.bs.modal', function (e) {
	    $(this).find('form').validator('destroy');
	    $('#newAboutUsForm').unbind();
	  });
	});
function openPopup(code, mode){
		popupMode = mode;
		var popupSubmitButton = document.getElementById("popupSubmitButton");
		$('#newAboutUsForm').find("input[type=text], textarea, input[type=file]").val("");
		$('#newAboutUsDiv').modal('toggle'); 
		if(mode=="edit"){
			editCode = code;
			popupSubmitButton.innerHTML = "Save";
			$("#imgFile").removeAttr("required");
			document.getElementById("newAboutUsHeading").value = document.getElementById(code+"Heading").innerHTML;
			document.getElementById("newAboutUsContent").value= document.getElementById(code+"Content").innerHTML;
			$('.modal-title')[0].innerHTML = "Edit this About Us Paragraph";
		}
		if(mode=="create")
		{
			popupSubmitButton.innerHTML = "Create";
			$("#imgFile").attr("required","");
			document.getElementById("newAboutUsHeading").value = "";
			document.getElementById("newAboutUsContent").value= "";
			$('.modal-title')[0].innerHTML = "Add new About Us Paragraph (a custom heading and content)";
			
		}
		return false;
	}
	function deleteThisAboutUs(code){
		var r = confirm("Are you sure you want to delete this About Us Paragraph?");
		if (r == true) {
			$.getJSON('ajaxDeleteThisAboutUs',
					   {"toBeDeletedCode":code},
						function(jsonResponse) {
						   iqwerty.toast.Toast(jsonResponse.ajaxResponseDummyMsg);
						   var $grid = $('.grid').masonry({
								  itemSelector: '.grid-item'
						   });
						   $grid.masonry( 'remove', document.getElementById(code+"Div").parentElement );
						   $grid.masonry('layout');
						});
		}
		return false;
	}
	</s:if>
	</script>
	
		<hr>
		<div class="row">
			<div class="col-md-3 ">
				<h3 class="pageHeading">About us</h3>
			</div>
			<div class="col-md-2 pull-right">
				<s:if test="%{#session.owner}" >
					<div class="row">
						
						<button type="button" class="btn btn-link" id="addNewAboutUsTip" ><i class="fa fa-question-circle"></i></button>
						
		            	<script>$('#addNewAboutUsTip').tooltip({
		            		trigger:'focus',
		            		placement:'left',
		            		delay: { "show": 200, "hide": 200 },
		            		title:"You can add more custom 'About us' Paragraphs by clicking on Add New button! These will also show up on your Profile page"
		            	});</script>
			            
							
						<button class="btn btn-primary" onclick='openPopup(-1, "create")' >Add New</button>
						
					</div>
				</s:if>
			</div>
		</div>
		<hr>
		<s:if test="%{aboutUsList.size()==0}">
			<p id="defaultContent" style="margin:10px;"><s:property value="defaultContent"/></p>
		</s:if>
		<div class="container-fluid">
			<div class="grid" id="aboutUsRows">
				<s:if test="%{aboutUsList.size()!=0}">
					<script>
					<s:iterator value="aboutUsList" status="status" >
						var cont = [];
						cont.newAboutUsCode = "<s:property value="code"/>";
						cont.newAboutUsHeading = "<s:property value="heading"/>";
						cont.newAboutUsContent = "<s:property value='%{content}'/>";
						cont.newAboutUsPhoto = "<s:property value="photo" />";
						while(cont.newAboutUsContent.indexOf("#$")!=-1)
							cont.newAboutUsContent = cont.newAboutUsContent.replace("#$", "<br>");
						createNewAboutUsDiv(cont);
					</s:iterator>
					</script>
				</s:if>
			</div>
		</div>
		
	
	<s:if test="%{#session.owner}" >
		<div class="modal fade" tabindex="-1" role="dialog" id="newAboutUsDiv">
		  <div class="modal-dialog">
			<div class="modal-content">
			  <div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title">Add new About Us Paragraph <br>(a custom heading and content)</h4>
			  </div>
			  	<form role="form" id="newAboutUsForm" name="newAboutUsForm" enctype="multipart/form-data" type="post">
			  	<div class="modal-body">
				  	<div class="row">
					 <div class="col-md-6 col-xs-6 form-group">
							    <label for="name">Heading:</label>
							    <s:hidden name="s" value="-1"></s:hidden>
							    <s:textfield cssClass="form-control" placeholder="Name" id="newAboutUsHeading"  name="newAboutUsHeading" value='' 
							    required="" data-error="Required Field!!" maxlength="50"></s:textfield>
							    <div class="help-block with-errors"></div>
							  </div>
					 </div>
					 <div class="row">
						  <div class="col-md-12 col-xs-12 form-group">
						    <label for="description">Content:</label>
							<s:textarea cssClass="form-control" rows="7"  placeholder="Description" id="newAboutUsContent" name="newAboutUsContent" value='' 
							required="" data-error="Minimum 100 characters are required!!" data-minlength="100" maxlength="1000"></s:textarea>
						    <div class="help-block with-errors"></div>
				  		</div>
				  	</div>
				  	 <div class="row">
					  	<div class="col-md-12 col-xs-12 form-group">
						  	<label for="imgFile">Image for the Paragraph </label>
							<s:file name="imgFile"  id="imgFile" cssClass="form-control" 
							required=""  data-error="" data-filesize="1048576" 
							data-img-file-validator = "imgFileValidator">
							</s:file>
							<div class="help-block with-errors">Max file size permitted is 1MB (.gif /.jpeg /.png /.bmp)</div>
						 </div>
					 </div>
				  	
				  </div>
				  <div class="modal-footer">
				  	 <div class="form-group">
					    <button type="submit" class="btn btn-primary" id="popupSubmitButton"></button>
						<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
					 </div>
				  </div>
				  </form>
			</div><!-- /.modal-content -->
		  </div><!-- /.modal-dialog -->
		</div><!-- /.modal -->
		<script>
		
		</script>
	</s:if>
 </div>
 <%@ include file="footer.jsp" %>
 
</body>
</html>