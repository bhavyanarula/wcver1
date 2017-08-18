<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><s:property value="#session.pageOwnerBean.ngoName" /> - WelfareCommunity</title>
<link rel="stylesheet" href="//blueimp.github.io/Gallery/css/blueimp-gallery.min.css" />
<link rel="stylesheet" href="/css/slick.css" />
<link rel="stylesheet" href="/css/bootstrap-image-gallery.min.css" />
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
	    /* width: 300px; */
	    margin: 1em auto;
	    /* box-shadow: 25px 25px 113px #f5f5f5 inset,-25px -25px 50px #f5f5f5 inset; */
	    background-size: cover;
	    background-repeat: no-repeat;
	    background-position: center;
	}
	.guideP{
		color:#adacd2;
		font-style: italic;
	}
	
	/* .blueimp-gallery>.title{
		display:block;
		font-size:24px;
		bottom:5px;
		top:initial;
	} */
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

<link href="/css/profile-style.css" rel="stylesheet" type="text/css" />
<link href="/css/jssor.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="/js/jssor.slider.min.js"></script>
<script src="/js/validator.js"></script>
<script src="/js/masonry.js"></script>
<s:if test="%{pageOwnerBean.type!=\"auto\"}">
	<script src="/js/profile-jssor.js"></script>
	<script src="/js/parallax.js"></script>
</s:if>
 <script>
 $( document ).ready( function ()	 {
				$('.scroll').slimScroll({
				    height:'95%',
				    alwaysVisible: false,
				    allowPageScroll: true,
				    position:'right',
				    distance:'3px'
				});
	});

</script>

<%@ include file="ngoHeader.jsp" %>
					 
		<s:if test="%{pageOwnerBean.listOfSlideshowPhotos.size()>0}">
        <style>
        	
        	.parallax-window {
			    
			    background: transparent;
			    position:absolute;
			    top:50px;
			    left:0px;
			    border-radius:0px;
				  }
			.blueimp-gallery-carousel{
				box-shadow : initial !important;
	
}
			#slides div {
		 		border-radius:0px;
}

	  		
        </style>
        <script src="js/slick.min.js"></script>
		  	
	        	
	            
       <div class="parallax-window col-md-12 col-sm-12 col-xs-12" id="parallax-window">
		  <div class="parallax-slider"> 
	            
             	
	            
	                       		
				 
		  	<div id="slides">
				  <s:iterator value="pageOwnerBean.listOfSlideshowPhotos" >
			    	<div><img class='slideshow-imgs' src="<s:property value="url" />"  alt="<s:property value="url" />" /></div>
				    </s:iterator>
				</div>
				</div>
	        </div>
	        
		<script>
		var prlxHeight = mobileScreen?'200px':'400px';
		$('.parallax-window').css({'min-height': prlxHeight});
		if(mobileScreen)
			$('.slideshow-imgs').css({height:'200px', width:'100%', 'object-fit':'cover'});
		else
			$('.slideshow-imgs').css({height:'400px', width:'100%', 'object-fit':'cover'});
		$(document).ready(function(){
			$('#slides').on('init', function(slick){
				$('.parallax-window').parallax({
				    naturalWidth: 600,
				    naturalHeight: 400,
				    positionX : '59px',
				    positionY : '0px'
				  });
				});
			$('#slides').slick({
				  dots: false,
				  accessibility:false,
				  infinite: true,
				  speed: 500,
				  fade: true,
				  autoplay:true,
				  cssEase: 'linear',
				  arrows: false
				});
		});
		</script>

	     </s:if>
	     <s:else>
		     <s:if test="%{#session.owner}" >
			     <div class="row">
			     	<div class="col-md-3" style="margin-left: 44%;">
						<button type="button" class="btn btn-link" id="addSlideshowToolip" ><i class="fa fa-question-circle"></i></button>
						<a href="<s:url action='getPhotos'>
			             	 <s:param name="from">ngo</s:param>
			             	 <s:param name="pageOwnerCode"><s:property value="pageOwnerCode"/></s:param>
			              </s:url>" > Add Slideshow
			            </a>
		            	<script>$('#addSlideshowToolip').tooltip({
		            		trigger:'focus',
		            		placement:'left',
		            		delay: { "show": 200, "hide": 200 },
		            		title: "You can add a slideshow of photos to your profile! Click on Add Slideshow link."
		            	
		            	});</script>
			          </div>  
			     </div>
		    </s:if>
	     </s:else>
	     <script>
		     var desc = "<s:property escape='false' value='%{#session.pageOwnerBean.ngoDescription.replaceAll(\"\r\n\", \"<br>\")}'  />";
		     if(desc!="null" && desc!=null){
			 $('#ngoDescription').html("<hr style=\'margin:5px 0px 10px 0px;\'> "+desc	);
		     }
		</script>
	     <s:if test="%{pageOwnerBean.type!=\"auto\"}">
     		<s:if test="%{photoList.size()!=0}">
				<div class="row" style="margin-bottom:25px;" id="photos-row">
						<a href="<s:url action='getPhotos'>
					             	 <s:param name="from">ngo</s:param>
					             	 <s:param name="pageOwnerCode"><s:property value="#session.pageOwnerBean.uid"/></s:param>
					              </s:url>" title="View All">
						<div class="col-xs-1 vertical-heading">
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
						        	<s:iterator value="photoList">
						    			<div class="thumbnail">
						    				<a href="<s:property value="url.replace(\"_thumb\",\"\")" />" title="" data-gallery  >
						    					<img src='<s:property value="url" />' style="height:100%; width:100%;" />
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
		    <s:if test="%{pageOwnerBean.ngoEventBeanList.size()!=0}">	
			     <div class="row" id="events-row">
		     		<a href="<s:url action='getListOfEvents'><s:param name="pageOwnerCode">
									<s:property value="#session.pageOwnerBean.uid"/></s:param></s:url>"
								title="View Events">
			     		<div class="col-xs-1 vertical-heading">
			     			E V E N T S
		    </div>
		     		</a>
		     			<div class="col-md-11 col-sm-11 col-xs-11">
			     			<div id="jssor_events" style="position: relative; top: 0px; left: 0px; height: 150px; overflow: hidden; visibility: hidden;">
						        <!-- Loading Screen -->
						        <div data-u="loading" style="position: absolute; top: 0px; left: 0px;">
						            <div style="filter: alpha(opacity=70); opacity: 0.7; position: absolute; display: block; top: 0px; left: 0px; width: 100%; height: 100%;"></div>
						            <div style="position:absolute;display:block;background:url('images/loading.gif') no-repeat center center;top:0px;left:0px;width:100%;height:100%;"></div>
						        </div>
						        <div data-u="slides" id="events-slides" style="cursor: default; position: relative; top: 0px; left: 0px; width: 100%; height: 150px; overflow: hidden;">
						        	<s:iterator value="pageOwnerBean.ngoEventBeanList" var="indx">
										<div style="background-image: url('<s:property value="imageURL"/>'); background-position:center; border:1px solid #dddddd; padding:2px;">
											<a href="<s:url action='openEventPage'> 
													 <s:param name="eventId"><s:property value="id"/></s:param>
													 <s:param name="pageOwnerCode"><s:property value="pageOwnerCode"/></s:param>
												 </s:url>" title="Go to event page..">
												<div style=" background-color: #eaeaea; opacity:0.8; padding:5px; height:100%; width:100%">
													<div class="row media-heading" style="text-align: center; white-space: nowrap; overflow: hidden;    text-overflow: ellipsis;">
														<s:property value="name"/>
													</div>
													<hr class="no-margin" style="border-top: 1px solid #c3bcbc;">
													<div class="row">
													   	<p style="text-align: center; color:black;">
														   	<span class="badge" ><s:property value="%{calendar.get(5)}"/></span>&nbsp;
														 	<s:property value="%{calendar.getDisplayName(2, 1, new java.util.Locale(\"en\"))}" /> 
														 	<s:property value="%{calendar.get(1)}" /></br>
														 	<s:property value="evtTime"/>
														 </p>
		     		</div>
													<hr class="no-margin" style="border-top: 1px solid #c3bcbc;">
													<div class="row">
													 	<p style="text-align:center;  color:black;">
												   			<s:property value="addressBean.city"/>, <s:property value="addressBean.state"/>
												   		</p>
		     		</div>
			     </div>
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
				<s:if test="%{#session.owner && isFirstVisit()}">
					<div class="modal fade" tabindex="-1" role="dialog" id="missingProfileModal">
					  <div class="modal-dialog">
					    <div class="modal-content">
					      <div class="modal-header">
					        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					        <h4 class="modal-title">Missing Profile Information</h4>
					      </div>
					      <div class="modal-body">
					      	<p>Following information is missing from your profile:</p>
					      	<a href="<s:url action='editProfile'></s:url>" >Click here to provide</a>
					      	<ul id="missingInfoList">
		     
					      	</ul>
	     </div>
					      <div class="modal-footer">
					        <button type="button" class="btn btn-default" data-dismiss="modal">Remind me on next login</button>
					      </div>
					    </div><!-- /.modal-content -->
					  </div><!-- /.modal-dialog -->
					</div><!-- /.modal -->
					<script>
						var list = $('#missingInfoList');
						if(!desc)
							$('<li />', {text : 'A brief introduction'}).appendTo(list);
						if(!"<s:property value="#session.pageOwnerBean.ngoPhone" />")
							$('<li />', {text : 'Your contact number'}).appendTo(list);
						if(!<s:property value="#session.pageOwnerBean.noOfVolunteers" />)
							$('<li />', {text : 'Number of Volunteers working with you'}).appendTo(list);
						if(!desc || !"<s:property value="#session.pageOwnerBean.ngoPhone" />")
							$('#missingProfileModal').modal('show');
					</script>
				</s:if>
	     
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
				                        <i class="glyphicon glyphicon-chevron-left"></i>
				                        Previous
				                    </button>
				                    <button type="button" class="btn btn-primary next">
				                        Next
				                        <i class="glyphicon glyphicon-chevron-right"></i>
				                    </button>
				                </div>
				            </div>
				        </div>
				    </div>
				</div>
				<script src="//blueimp.github.io/Gallery/js/jquery.blueimp-gallery.min.js"></script>
				<script src="/js/post.js"></script>
				<script src="/js/toast.min.js"></script>
				
				<script>
				
				$(function () {
					  $('[data-toggle="tooltip"]').tooltip()
					
					
					
					
									
					
					
					
					
					
					
					
					  
					})
					  
				</script>
				<hr></hr>
				<s:if test="%{#session.owner}">
					<div class="row">
						<button type="button" id="btn-create-post" class="btn btn-primary pull-right" onclick='openPopup(-1, "create")' 
						data-toggle="tooltip" data-placement="bottom" title="Post anything on your profile with a Title, an Image and some Content..">Create Post!</button>
					</div>
				</s:if>
				<div class="container-fluid dynamicContent" style="padding:0px;">
					<div class="grid" id="aboutUsRows">
					</div>
					<div id="loader" style="text-align:center;">
						<img src="/images/loading.gif">
					</div>
					<script src="//cdnjs.cloudflare.com/ajax/libs/ScrollMagic/2.0.5/ScrollMagic.min.js"></script>
					<script src="//cdnjs.cloudflare.com/ajax/libs/ScrollMagic/2.0.5/plugins/debug.addIndicators.min.js"></script>
							<script>
						var controller = new ScrollMagic.Controller();
						var currPage = 0;
						// build scene
						var scene = new ScrollMagic.Scene({triggerElement: ".dynamicContent #loader", triggerHook: "onEnter"})
								
										.addTo(controller)
										.on("enter", function (e) {
											if (!$("#loader").hasClass("active")) {
												$("#loader").addClass("active");
												if (console){
													console.log("loading new items");
								}
												// simulate ajax call to add content using the function below
												getNextAboutUsPage("<s:property value="pageOwnerCode"/>", ++currPage, <s:property value="#session.owner"/>);	
											}
							});
						getNextAboutUsPage("<s:property value="pageOwnerCode"/>", 0, <s:property value="#session.owner"/>);		
							</script>
				</div>
				
		     	
				<div class="modal fade" tabindex="-1" role="dialog" id="newAboutUsDiv">
				  <div class="modal-dialog">
					<div class="modal-content">
					  <div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						<h4 class="modal-title">Post to your Profile</h4>
														  </div>
					  	<form role="form" id="newAboutUsForm" name="newAboutUsForm" enctype="multipart/form-data" type="post">
					  	<div class="modal-body">
														    <div class="row">
							 <div class="col-md-6 col-xs-6 form-group">
									    <label for="name">Title</label>
									    <s:hidden name="s" value="-1"></s:hidden>
									    <s:textfield cssClass="form-control" placeholder="Name" id="newAboutUsHeading"  name="newAboutUsHeading" value='' 
									    required="" data-error="Required Field!!" maxlength="50"></s:textfield>
									    <div class="help-block with-errors"></div>
															   	</div>
								</div>
							 <div class="row">
				
								  <div class="col-md-12 col-xs-12 form-group">
								    <label for="description">Content</label>
									<s:textarea cssClass="form-control" rows="7"  placeholder="Description" id="newAboutUsContent" name="newAboutUsContent" value='' 
									required="" data-error="Minimum 100 characters are required!!" data-minlength="100" data-maxlength="700"></s:textarea>
								    <div class="help-block with-errors"></div>
						    		</div>
					    	</div>
									<div class="row">
							  	<div class="col-md-12 col-xs-12 form-group">
								  	<label for="imgFile">Image</label>
									<s:file name="imgFile"  id="imgFile" cssClass="form-control" 
									required=""  data-filesize="1048576" 
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
			    
 		</s:if>
		
		</div>
	<%@ include file="footer.jsp" %>
      

</body>
</html>