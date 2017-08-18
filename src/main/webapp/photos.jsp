<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Photos</title>

<link rel="stylesheet" href="//blueimp.github.io/Gallery/css/blueimp-gallery.min.css">
<link rel="stylesheet" href="/css/bootstrap-image-gallery.min.css">

<link rel="stylesheet" href="/css/dropzone.css">
</head>
<body>
<%@ include file="header.jsp" %>
<!-- ********************************************
	Common JS imports: Start
 -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<!-- 
	Common JS imports: End
 ************************************************-->
<script src="/js/dropzone.js"></script>
<script src="/js/toast.min.js"></script>
<script type="text/javascript" src="/js/heartbeat.js"></script>
<script>
	function displayDropzone()
	{
		document.getElementById("dropzone-area").style.display = 'block';
	}
	function closeDropzone(){
		document.getElementById("dropzone-area").style.display = 'none';
	}
	
	function enableDelButton()
	{
		$('#bDelete').removeClass('disabled');
		if($('#bAddToSlideshow'))
			$('#bAddToSlideshow').removeClass('disabled');
		if($('#bRemoveFromSlideshow'))
			$('#bRemoveFromSlideshow').removeClass('disabled');
		var cboxs =$( "form :checked" );
		if(cboxs.length===0){
			$('#bDelete').addClass('disabled');
			if($('#bAddToSlideshow'))
				$('#bAddToSlideshow').addClass('disabled');
			if($('#bRemoveFromSlideshow'))
				$('#bRemoveFromSlideshow').addClass('disabled');
		}
			
	}
	function deleteSelected()
	{
		
		var cboxs =$( "input:checked" );
		var toBeDeletedArr=new Array(cboxs.length);
		for(var i=0; i<cboxs.length;i++)
		{
			toBeDeletedArr[i] = cboxs[i].value;
		}
		if(toBeDeletedArr.length){
			loader.start();
		$.getJSON('ajaxDeletePhotoAction',
				{'deletePhotosIdArray' : toBeDeletedArr.toString()},
				function(jsonResponse) {
						
					iqwerty.toast.Toast(jsonResponse.ajaxResponseDummyMsg);
					for(var i = 0; i<toBeDeletedArr.length;i++)
						{
							$('#div'+toBeDeletedArr[i])[0].remove();	
						}
						loader.stop();	
				});
		}
		
	}
	function addToSlideshow()
	{
		
		var cboxs =$( "form :checked" );
		var slideshowArr=[];
		for(var i =0; i<cboxs.length; i++)
		{
			slideshowArr.push(cboxs[i].value);
		}
		jQuery.ajaxSettings.traditional = true;
		if(slideshowArr.length){
			loader.start();
		$.getJSON('ajaxAddToSlideshowAction',
				{'addToSlideshow' : slideshowArr},
				function(jsonResponse) {
					loader.stop();
					iqwerty.toast.Toast(jsonResponse.ajaxResponseDummyMsg);
					 setTimeout(function(){
						   window.location.href = window.location.href;
					   },3000);
				}
				);
		}
		jQuery.ajaxSettings.traditional = false;
		
	}
	function removeFromSlideshow()
	{
		loader.start();
		var cboxs =$( "form :checked" );
		var slideshowArr=[];
		for(var i =0; i<cboxs.length; i++)
		{
			slideshowArr.push(cboxs[i].value);
		}
		jQuery.ajaxSettings.traditional = true;
		$.getJSON('ajaxRemoveFromSlideshowAction',
				{'removeFromSlideshow' : slideshowArr},
				function(jsonResponse) {
					loader.stop();
					iqwerty.toast.Toast(jsonResponse.ajaxResponseDummyMsg);
					 setTimeout(function(){
						   window.location.href = window.location.href;
					   },3000);
				}
				);
		jQuery.ajaxSettings.traditional = false;
		
	}
	
</script>
		<%@ include file="ngoHeader.jsp" %>
<hr>
		<div class="row">
			<div class="col-md-7 col-sm-12 col-xs-12">
				<h3 class="pageHeading"><s:property value="header" /></h3>
			</div>
			<div class="col-md-5 col-sm-12 col-cs-12 pull-right" style="padding-top:20px;">
				 <s:if test="%{#session.owner}">
		       		<div class="col-md-2 col-xs-2 pull-right">	
	       				 <div class="input-group-btn">
					        <i class="btn fa fa-ellipsis-v fa-2x dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" style="font-size:20px;">
					        </i>
					        <ul class="dropdown-menu dropdown-menu-right">
								<s:if test="%{!album.equalsIgnoreCase(\"Slideshow\")}">
					          <li class="disabled" id="bDelete">
					          	<a role="button" href="#" onclick="deleteSelected()" title="Delete selected" >Delete selected</a>
					          </li>
								</s:if>
					          <s:if test="%{!album.equalsIgnoreCase(\"Slideshow\")}">
						          <li class="disabled" id="bAddToSlideshow">
						          	<a role="button" href="#"   onclick="addToSlideshow()" title="Add to Slideshow" >Add to Profile Slideshow</a>
						          </li>
					          </s:if>
					          <s:if test="%{album.equalsIgnoreCase(\"Slideshow\")}">
						          <li class="disabled" id="bRemoveFromSlideshow" >
						          	<a role="button" href="#" onclick="removeFromSlideshow()" title="Remove from Slideshow">Remove from Profile Slideshow</a>
						          </li>
					          </s:if>
					        </ul>
					      </div>
				      </div>
				      <div class="col-md-4 col-xs-4 pull-right">
						<button class="btn btn-simple" name="addMore" onClick="displayDropzone()" title="Upload Photos">Upload Photos</button>
		       		</div>	
	       		</s:if>
				<s:if test="%{from==\"ngo\"}">
					<div class="col-md-4 col-xs-4 pull-right">
			       		<div class="input-group-btn" >
					        <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
					        Album: <s:property value="album" /> <span class="caret"></span></button>
					        <ul class="dropdown-menu dropdown-menu-right">
					        	
					          <li><a href="<s:url action='getPhotos'>
							             	 <s:param name="from">ngo</s:param>
							             	 <s:param name="album">slideshow</s:param>
							             	 <s:param name="pageOwnerCode"><s:property value="pageOwnerCode"/></s:param>
							              </s:url>" title="Slideshow pics">Slideshow</a></li>
					          <li><a href="<s:url action='getPhotos'>
							             	 <s:param name="from">ngo</s:param>
							             	 <s:param name="album">all</s:param>
							             	 <s:param name="pageOwnerCode"><s:property value="pageOwnerCode"/></s:param>
							              </s:url>" title="View All" >All</a></li>
					          <li><a href="<s:url action='getPhotos'>
							             	 <s:param name="from">ngo</s:param>
							             	 <s:param name="album">events</s:param>
							             	 <s:param name="pageOwnerCode"><s:property value="pageOwnerCode"/></s:param>
							              </s:url>" title="Event Photos">Events</a></li>
					        </ul>
					      </div>
				      </div>
			     </s:if>
			</div>
				 
	     </div>
		    
		
		<hr>
		<div class="row">
	    	
	       	<div class="dropzoneArea" id="dropzone-area" style="display:none;">
		    	<form action="uploadPhotosAction" class="dropzone dz-clickable" id="dropzone-form">
		    		
		    		<s:hidden name="from" value="%{from}" />
		    		<s:hidden name="eventId" value="%{eventId}" />
		    		<div class="dz-default dz-message"><span>Drop files here to upload</span></div>
		    		
		    	</form>
		    	<script>
		    			
						  Dropzone.options.dropzoneForm = {
							paramName: "imgFile",
						    maxFilesize: 2,
						    dictFileTooBig:	'File size exceeds maximum of {{maxFilesize}}MB!!',
						    maxFiles: 9,
						    dictMaxFilesExceeded: 'You can only upload 9 files at a time!! Please Try Again.',
						    acceptedFiles: 'image/*',
						    dictInvalidFileType: 'Invalid file!! Only image files accepted.',
						    //addRemoveLinks:true,
						    init: function() {
						      this.on("uploadprogress", function(file, progress) {
						        console.log("File progress", progress);
						      });
						    }
						  }
						  function refresh(){
							  window.location.reload(true);
						  }
					</script>
		    	<button class="btn btn-default pull-right" onClick="refresh()">Done</button>
		    	<button class="btn btn-default pull-right" onClick="closeDropzone()">Close</button>
		    	<br><br><br>
		    </div>
		<script>
			function imageElement(image, owner) {
				var imgId = image.id;
				var imgUrl = image.url;
				var imgDiv = $('<div />', {
					'class' : 'col-md-3 col-sm-6 col-xs-12 thumbnail',
					'id' : 'div' + imgId
				});
				if (owner) {
					var form = $('<form />').appendTo(imgDiv);
					var chkbox = $('<input />', {
						'type' : 'checkbox',
						'name' : imgId,
						'onchange' : 'enableDelButton()',
						'value' : imgId
					}).appendTo(form);
				}
				var link = $('<a />', {
					'href' : imgUrl.replace("_thumb", ""),
					'data-gallery' : ''
				}).appendTo(imgDiv);
				var img = $(
						'<img />',
						{
							'style' : 'object-fit:cover ; height:200px; border-radius: 4px',
							'src' : imgUrl
						}).appendTo(link);
	    	 
				var galleryDiv = $('.galleryDiv');
				imgDiv.appendTo(galleryDiv);
				scene.update(); // make sure the scene gets the new start position
				$("#loader").removeClass("active");
			}

			function getListOfImages(imgPageNo, ownerId, from, album) {

				loader.start();
				
				var data = {};
				data.imgPageNo = imgPageNo;
				if(from === 'event')
					data.eventId = ownerId;
				else
					data.pageOwnerCode = ownerId;
				data.from = from;
				data.album = album;
				$.getJSON('ajaxGetPhotos', data, function(jsonResponse) {
					loader.stop();
					var aboutUsArray = jsonResponse.listOfPhotos;
					aboutUsArray.forEach(function(item, index) {
						imageElement(item, <s:property value="#session.owner" />);
					});

					if (jsonResponse.endReached) {
						scene.remove();
						$("#loader").css('display', 'none');
					} 
				});
			}
		</script>
		<div class="dynamicContent">
			<div class="row galleryDiv">
			</div>
			<div id="loader" style="text-align: center;">
				<img src="/images/loading.gif">
			</div>
			<script src="//cdnjs.cloudflare.com/ajax/libs/ScrollMagic/2.0.5/ScrollMagic.min.js"></script>
			<script	src="//cdnjs.cloudflare.com/ajax/libs/ScrollMagic/2.0.5/plugins/debug.addIndicators.min.js"></script>
			<script>
				var controller = new ScrollMagic.Controller();
				var currPage = 0;
				// build scene
				var scene = new ScrollMagic.Scene({
					triggerElement : ".dynamicContent #loader",
					triggerHook : "onEnter"
				}).addTo(controller).on("enter",
						function(e) {
							if (!$("#loader").hasClass("active")) {
								$("#loader").addClass("active");
								if (console) {
									console.log("loading new items");
								}
								// simulate ajax call to add content using the function below
								<s:if test='%{from.equals("ngo")}'>
								getListOfImages(++currPage,	"<s:property value="pageOwnerCode" />",	"<s:property value="from" />", "<s:property value="album" />")
								</s:if>
								<s:if test='%{from.equals("event")}'>
									getListOfImages(++currPage,	"<s:property value="eventId" />",	"<s:property value="from" />", "<s:property value="album" />")
								</s:if>
							}
						});
			<s:if test='%{from.equals("ngo")}'>
				getListOfImages(0, "<s:property value="pageOwnerCode" />",	"<s:property value="from" />", "<s:property value="album" />")
			</s:if>
			<s:if test='%{from.equals("event")}'>
				getListOfImages(0,	"<s:property value="eventId" />",	"<s:property value="from" />", "<s:property value="album" />")
			</s:if>
			</script>
						</div>
		
	    	 	<!-- The Bootstrap Image Gallery lightbox, should be a child element of the document body -->
				<div id="blueimp-gallery" class="blueimp-gallery blueimp-gallery-controls" data-use-bootstrap-modal="false">
				    <!-- The container for the modal slides -->
				    <div class="slides"></div>
				    <!-- Controls for the borderless lightbox -->
				    <h3 class="title"></h3>
				  	<a class="prev"><i class="fa fa-chevron-left"></i></a>
				    <a class="next"><i class="fa fa-chevron-right"></i></a>
				    <a class="close"><i class="fa fa-close"></i></a>
				    <a class="play-pause"></a>
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
				                        <i class="fa fa-chevron-left"></i>
				                        Previous
				                    </button>
				                    <button type="button" class="btn btn-primary next">
				                        Next
				                        <i class="fa fa-chevron-left"></i>
				                    </button>
				                </div>
				            </div>
				        </div>
				    </div>
				</div>
	    	  </div>
   	  	<script src="//blueimp.github.io/Gallery/js/jquery.blueimp-gallery.min.js"></script>
		
	</div>
	<%@ include file="footer.jsp" %>
</body>
</html>