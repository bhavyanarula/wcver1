function createNewAboutUs(){
	var newAboutUsHeading = document.getElementById("newAboutUsHeading").value;
	var newAboutUsContent = document.getElementById("newAboutUsContent").value;
	var fData = $('#newAboutUsForm')[0];
	var formData = new FormData(fData);
	loader.start();
	$.ajax({
        url : 'ajaxSaveNewAboutUs',
        data : formData,
        type : 'POST',
        processData: false,
        contentType: false,
        success : function(jsonResponse) {
        		loader.stop();
			   iqwerty.toast.Toast(jsonResponse.ajaxResponseDummyMsg);
			   document.getElementById("newAboutUsHeading").value = "";
			   document.getElementById("newAboutUsContent").value = "";
			   createNewAboutUsDiv(jsonResponse.aboutUsBean, 'prepended', true);
			},
		failure : function(jsonResponse) {
        	   loader.stop();
			   iqwerty.toast.Toast(jsonResponse.ajaxResponseDummyMsg);
			}
    });
	$('#newAboutUsDiv').modal('toggle');
	return false;
}


function updateThisAboutUs(code){
	var newAboutUsHeading = document.getElementById("newAboutUsHeading").value;
	var newAboutUsContent = document.getElementById("newAboutUsContent").value;
	var fData = $('#newAboutUsForm')[0];
	var formData = new FormData(fData);
	formData.append("toBeUpdatedCode",code);
	loader.start();
	$.ajax({
        url : 'ajaxUpdateThisAboutUs',
        data : formData,
        type : 'POST',
        processData: false,
        contentType: false,
        success : function(jsonResponse) {
        	loader.stop();
        	iqwerty.toast.Toast(jsonResponse.ajaxResponseDummyMsg);
			   
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
		var content = document.getElementById(code+"Content").innerHTML;
		while(content.indexOf('<br>')!=-1){
			content = content.replace('<br>', '\r\n');
		}
		document.getElementById("newAboutUsContent").value = content;
		$('.modal-title')[0].innerHTML = "Edit Post";
	}
	if(mode=="create")
	{
		popupSubmitButton.innerHTML = "POST!!";
		$("#imgFile").attr("required","");
		document.getElementById("newAboutUsHeading").value = "";
		document.getElementById("newAboutUsContent").value= "";
		$('.modal-title')[0].innerHTML = "Create new Post";
		
	}
	return false;
}
function deleteThisAboutUs(code){
	var r = confirm("Are you sure you want to delete this Post?");
	if (r == true) {
		loader.start();
		$.getJSON('ajaxDeleteThisAboutUs',
				   {"toBeDeletedCode":code},
					function(jsonResponse) {
					   loader.stop();
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
function addRemovePinAboutUs(code, pinned){
	//pinned = (pinned === 'true');
	var action = "ajaxPinThisAboutUs";
	if(pinned)
		action = "ajaxRemovePinThisAboutUs";
	loader.start();
	$.getJSON(action,
			   {"toBePinnedCode":code},
				function(jsonResponse) {
				   loader.stop();
				   iqwerty.toast.Toast(jsonResponse.ajaxResponseDummyMsg);
				   
				   var moveDiv = $('#'+code+"Div")
				   if(!pinned){
					   var $grid = $('.grid').masonry();
					   
					   var gridItem = $("<div />", {'class': 'grid-item col-md-3 col-sm-3'});
					   moveDiv.appendTo(gridItem);
					   
					   $grid.masonry( 'remove', moveDiv.parentElement );
					  
					   $grid.masonry().append( gridItem ).masonry('prepended' , gridItem );
					   
					   moveDiv.css('border-top-left-radius', '54px');
					   var pinnerDiv = moveDiv.find('#pinnerDiv');
					   var pin = $("<i />", {"class" : "fa fa-thumb-tack fa-2x",
							"style" : "transform: rotate(-45deg);",
							"title" : "Pinned to Top"}).prependTo(pinnerDiv);
					   var pinButton = moveDiv.find('#pinButton');
					   pinButton.attr('title', 'Unpin')
					   pinButton.attr('onclick', "return addRemovePinAboutUs("+code+", "+true+")");
					   $grid.masonry('layout');
				   } else {
					   moveDiv.css('border-top-left-radius', '4px');
					   moveDiv.find('#pinnerDiv i').remove();
					   var pinButton = moveDiv.find('#pinButton');
					   pinButton.attr('title', 'Pin to Top')
					   pinButton.attr('onclick', "return addRemovePinAboutUs("+code+", "+false+")");
				   }
				   
				});
	
	return false;
}
function createNewAboutUsDiv(jsonResponse, append, isOwner){
	
	var aboutUsRowDiv = document.getElementById("aboutUsRows");
	var auCode = jsonResponse.code;
	var auHeading = jsonResponse.heading;
	var auContent = jsonResponse.content.replace(/\n/g, '<br />');
	var auImage = jsonResponse.photo;
	var auCreatedOn = new Date(jsonResponse.createdOn);
	auCreatedOn = (''+auCreatedOn).substring(4, 15);
	var auIsPinned = jsonResponse.pinned;
	
	var gridItem = $("<div />", {'class': 'grid-item col-md-3 col-sm-6 col-xs-12'});
	
	var panel = $("<div />", {"class" : "panel panel-default",
								"id" : auCode+"Div",
								'style' : 'padding:4px'}).appendTo(gridItem);
	if(auIsPinned)
		panel.css('border-top-left-radius', '54px');
	
	var headDiv = $("<div />", {"class" : "row"}).appendTo(panel);
	var imageDiv = $("<div />", {"class" : "row",
								"style" : "text-align:center;"}).appendTo(panel);
	var contentDiv =  $("<div />", {"class" : "row"}).appendTo(panel);
	var dateDiv =  $("<div />", {"class" : "row"}).appendTo(panel);
	
	var headDivC1 = $("<div />", {"class" : "col-md-1 col-sm-1", 'id' : 'pinnerDiv'}).appendTo(headDiv);
	var headDivC2 = $("<div />", {"class" : "col-md-11 col-sm-11"}).appendTo(headDiv);
	var headP = $("<p />", {"id" : auCode+"Heading",
							"style" : "text-align:center; font-family:'Roboto',UILanguageFont,Arial,sans-serif; font-weight:600",
							"text" : auHeading}).appendTo(headDivC2);
	var imageP =  $("<p />", {"class" : "vignette col-md-12",
								"style" : "background-image: url("+auImage+"); height:200px;"}).appendTo(imageDiv);
	var contentP = $("<p />", {"id" : auCode+"Content",
								"style" : "text-align:center; padding: 0 8px; font-size:13px;",
								"html" : auContent}).appendTo(contentDiv);
	var dateDivC1 = $("<div />", {"class" : "col-md-5 col-sm-5"}).appendTo(dateDiv);
	var dateP = $("<p />", {"id" : auCode+"Content",
		"style" : "color:#9e9a9a; font-size:smaller; font-style:italic; margin-left:10px",
		"text" : auCreatedOn}).appendTo(dateDivC1);
	var dateDivC2 = $("<div />", {"class" : "col-md-3 col-sm-3 pull-right"}).appendTo(dateDiv);
	if(auIsPinned)
		var pin = $("<i />", {"class" : "fa fa-thumb-tack fa-2x",
								"style" : "transform: rotate(-45deg);",
								"title" : "Pinned to Top",
								"id" : 'pin'}).appendTo(headDivC1);
	
	//var headBDiv = $("<div />", {"class" : " pull-right"}).appendTo(headDiv);
	if(isOwner){
		var pinTitle = "";
		if(auIsPinned)
			pinTitle = "Unpin";
		else 
			pinTitle = "Pin to Top";
		
		var pinButton = $("<i />", {"class" : "fa fa-thumb-tack",
									"title" : pinTitle,
									"style" : "cursor: pointer;",
									"id" : "pinButton",
									"onclick" : "return addRemovePinAboutUs("+auCode+", "+auIsPinned+")"}).appendTo(dateDivC2);
		var space = $("<span />", {"text":"   "}).appendTo(dateDivC2);
		var editButton = $("<i />", {"class" : "fa fa-edit",
									"title" : "Edit",
									"style" : "cursor: pointer;",
									"onclick" : "return openPopup("+auCode+",\"edit\")"}).appendTo(dateDivC2);
		var space = $("<span />", {"text":"   "}).appendTo(dateDivC2);
		var delButton = $("<i />", {"class" : "fa fa-trash",
									"title" : "Delete",
									"style" : "cursor: pointer;",
									"onclick" : "return deleteThisAboutUs("+auCode+")"}).appendTo(dateDivC2);
	}
	
	
	
	
	
	
	
	var $grid = $('.grid').masonry({
		  itemSelector: '.grid-item', // use a separate class for itemSelector, other than .col-
		  percentPosition: true
		});
	  
	$grid.masonry().append( gridItem ).masonry(append , gridItem );
	
	scene.update(); // make sure the scene gets the new start position
	$("#loader").removeClass("active");
	return false;
}
function getNextAboutUsPage(pageOwnerCode, nextPageNo, owner){
	$.getJSON('ajaxGetAboutUsList',
			   {"aboutUsPageNo" : nextPageNo, "pageOwnerCode" : pageOwnerCode},
				function(jsonResponse) {
				   var aboutUsArray = jsonResponse.aboutUsList;
					aboutUsArray.forEach(function(item, index){
						createNewAboutUsDiv(item, 'appended', owner);
					});
					if(jsonResponse.endReached)
					{
						scene.remove();
						$("#loader").css('display', 'none');
					}
				});
}

function addBlankGridItem(){
	var aboutUsRowDiv = document.getElementById("aboutUsRows");
	
	var gridItem = document.createElement("div");
	gridItem.setAttribute('class', 'grid-item col-md-3 col-sm-3');
	
  var $grid = $('.grid').masonry({
	  itemSelector: '.grid-item', // use a separate class for itemSelector, other than .col-
	  percentPosition: true
	});
	  
	$grid.masonry().append( gridItem ).masonry( 'appended', gridItem );
	return false;
}