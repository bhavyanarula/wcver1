<!DOCTYPE html>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html lang="en">
<head>
	<title>WelfareCommunity - NGOs and Volunteers - India</title>

	<!-- meta -->
	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

	<!-- css -->
	<link rel="stylesheet" href="/css/bootstrap.css" />
	<link rel="stylesheet" href="/css/animate.css">
	<link rel="stylesheet" href="/css/style.css">
	<link href="/css/jssor.css" rel="stylesheet" type="text/css" />
	<link href="/css/font-awesome.min.css" rel="stylesheet" />
	<link href="/css/Main.css" rel="stylesheet" type="text/css" />
	
	
	
	<link rel='shortcut icon' type='image/x-icon' href='/favicon.ico' />
	<link href="/css/bootstrap-tour.min.css" rel="stylesheet">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
	<script src="/js/loader.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

	<!-- fonts -->
	<link href='https://fonts.googleapis.com/css?family=Roboto:400,100,100italic,300,300italic,400italic,500,500italic,700,700italic,900,900italic|Roboto+Condensed:300italic,400italic,700italic,400,300,700|Oxygen:400,300,700' rel='stylesheet'>

	<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
        <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

    <!--[if lt IE 9]>
        <p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
    <![endif]-->
    
    <style>
    	.row{
 		margin:5px 0px 5px 0px;
 	}
 	
 	div[class*='col-'], section[class*='col-'] {
 		margin: 0px;
 		padding: 5px 0px 5px 0px !important;
 		
 	}
 	.no-margin {
		margin : 0px;
	}
	body {
	    font-family: 'Lato', Calibri, Arial, sans-serif;
	    color: #222;
	     font-weight: initial;
	    letter-spacing:initial;
	}
	.btn {
		font-weight: initial;
	}
    </style>
    <div id="fb-root"></div>
	<script>(function(d, s, id) {
	  var js, fjs = d.getElementsByTagName(s)[0];
	  if (d.getElementById(id)) return;
	  js = d.createElement(s); js.id = id;
	  js.src = "//connect.facebook.net/en_US/sdk.js#xfbml=1&version=v2.7";
	  fjs.parentNode.insertBefore(js, fjs);
	}(document, 'script', 'facebook-jssdk'));</script>
	<script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
<script>
  (adsbygoogle = window.adsbygoogle || []).push({
    google_ad_client: "ca-pub-1947886091160201",
    enable_page_level_ads: true
  });
</script>
</head>
<body id="home">

	<!-- ****************************** Preloader ************************** -->

	<div id="preloader"></div>

	<!-- ****************************** Sidebar ************************** -->



	<!-- ****************************** Header ************************** -->

	<header class="sticky" id="header">
		<section class="">
			<section class="row" id="logo_menu">
				<section class="col-xs-6"><a class="logo" href=""><img style="height: 58px; width: 75px; margin-left:30px" src="/images/WC_BW.png" title="Home"/></a></section>
				
				<section class="col-xs-6 col-md-6 col-sm-6" style="margin-top: 5px;">
					<form action="advanceSearch" class="navbar-form" id="searchForm1"> 
						<div class="pull-right">
						      <div class="input-group">	
						      	<input type='text' name="bsQuery" class="bsQuery form-control" value="" />
						      	
							      <div class="input-group-btn">
							        <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Search <span class="caret"></span></button>
							        <ul class="dropdown-menu dropdown-menu-right">
							          <li><a href='#' onclick="basicSearch1('sbn')">by Name</a></li>
							          <li><a href='#' onclick="basicSearch1('sbc1')">by Cause</a></li>
							          <li role="separator" class="divider"></li>
							          <li style="z-index:1042;"><a href='#' onclick="basicSearch1('as')">Advanced Search</a></li>
							        </ul>
							      </div>
							   </div>
						  
						    <script>
							    $('.bsQuery').on("keypress", function(e) {
								        if (e.keyCode == 13) {
								        	basicSearch1('sbn');
								            return false; // prevent the button click from happening
								        }
								});
						    </script>
					  		</div>
					  		<input type="hidden" name="searchAction" id="searchAction1" value="blank"/>
					      	<input type="hidden" name="start" value="0"/>
					    </form>
				
				</section>
			</section>
		</section>
	</header>

	<!-- ****************************** Banner ************************** -->


	<section id="banner" >
		<section class="row">
				<div class="col-md-7 col-sm-7 col-xs-12" style="margin-left:20px;">
					<div class="headings">
						<p class="wow animated fadeInLeft">Welcome to</p>
						<h1 class="wow animated fadeInDown" data-wow-delay="0.7s">WELFARE COMMUNITY</h1>
						<h4 class="wow animated fadeInDown" data-wow-delay="0.7s">a network for socially responsible people..</h4>
						<p class="wow animated fadeInRight" data-wow-delay="1s">Are you an NGO or having any SOCIAL INITIATIVE, or an Individual willing to VOLUNTEER for some Social Work?<br> 
						Get into the network where all care for humanity...</p>
						<div class="row wow animated bounceInUp" data-wow-delay="1.5s">
							<div class="col-xs-6 col-sm-4 col-md-3">
								<div>
									<a href="#" style="display:inline;" class="btn btn-primary btn-lg" role='button' onclick='showLoginModal()'><i class="fa fa-sign-in" style="color:white;"></i>&nbsp;&nbsp;Login&nbsp;&nbsp;</a>
								</div>
							</div>
							<div class="col-xs-6 col-sm-4 col-md-3">
								<div>
									<a href="<s:url action='SignUp'/>"  style="display:inline;" class="btn btn-success btn-lg"><i class="fa fa-pencil" style="color:white;"></i> Sign Up</a>
								</div>
							</div>
						</div>
						<div class="row" style="margin-top: 20px;">
							<script src='/js/alert.js'></script>
						<s:if test="hasActionErrors()">
			                <s:actionerror/>
				                <div id="alert-error-login">
				                </div>
			                    <script>
				                	var options = {type : 'danger',
				                					heading : 'Error!',
				                					content :  document.getElementsByClassName("errorMessage")[0].childNodes[1].childNodes[0].innerHTML,
				                				  };
				                	$('#alert-error-login').bAlert(options);
			                    </script>
			                 
			                </s:if>
			                <s:if test="hasActionMessages()">
			                <s:actionmessage/>
				                <div id="alert-success-login">
				                </div>
			                    <script>
				                	var options = {type : 'success',
				                					heading : 'Success!',
				                					content :  document.getElementsByClassName("actionMessage")[0].childNodes[1].childNodes[0].innerHTML,
				                				  };
				                	$('#alert-success-login').bAlert(options);
			                    </script>
		                 
		                </s:if>
		                </div>
					</div>
				</div>
				<!-- <div class="col-md-6  col-sm-6 hidden-xs hidden-sm">
					<div class="hand-container">
					<img class="iphone-hand img_res wow animated bounceInUp" data-wow-duration="1.2s" src="/WelfareCommunity/img/iphone_hand.png"></img>
					<div class="clearfix"></div>
					</div>
				</div> -->
		</section>
	</section>

	<!-- ****************************** Features Section ************************** -->

	<section id="features" class="block">
		<section class="row">
			<section class="row">
				<div class="title-box"><h1 class="block-title wow animated rollIn">
				<span class="bb-top-left"></span>
				<span class="bb-bottom-left"></span>
				Features
				<span class="bb-top-right"></span>
				<span class="bb-bottom-right"></span>
				</h1></div>
			</section>
			
			<section class="row">
				<div class="col-sm-6 col-md-4 feature-box">
					<div class="feature-box wow animated flipInX" data-wow-delay="0.3s">
						<i class="fa fa-globe fa-4x" style="color:#337ab7;"></i>
						<h2>Web Presence</h2>
						<p>Have a face for your Organization on the web. Share the URL http://welfarecommunity.org/ {yourname} for direct access to your profile.</p>
					</div>
				</div>
				<div class="col-sm-6 col-md-4 feature-box">
					<div class="feature-box wow animated flipInX" data-wow-delay="0.3s">
						<i class="fa fa-laptop fa-4x" style="color:#5cb85c;"></i>
						<h2>Profile</h2>
						<p>Photos, Slideshow, Events,<br> Comments, Appreciations, Posts..</p>
					</div>
				</div>
				<div class="col-sm-6 col-md-4 feature-box">
					<div class="feature-box wow animated flipInX" data-wow-delay="0.3s">
						<i class="fa fa-signal fa-4x" style="color:#00ceb8;"></i>
						<h2>Scale</h2>
						<p>Scale your efforts to a new level. Seek Volunteers to get help for your events.</p>
					</div>
				</div>
				<div class="col-sm-6 col-md-4 feature-box">
					<div class="feature-box wow animated flipInX" data-wow-delay="0.6s">
						<i class="fa fa-calendar-check-o fa-4x" style="color:#d9534f;"></i>
						<h2>Events</h2>
						<p> Organize and Promote or contribute in events and make them a success. </p>
					</div>
				</div>
				<div class="col-sm-6 col-md-4 feature-box">
					<div class="feature-box wow animated flipInX" data-wow-delay="0.6s">
						<i class="fa fa-group fa-4x" style="color:#a785a0;"></i>
						<h2>Collaborate</h2>
						<p>Encourage more people into social welfare. If you wish to VOLUNTEER, visit any event in your area and apply</p>
					</div>
				</div>
				<div class="col-sm-6 col-md-4 feature-box">
					<div class="feature-box wow animated flipInX" data-wow-delay="0.6s">
						<i class="fa fa-search fa-4x" style="color:#f0dc4c;"></i>
						<h2>Look Out</h2>
						<div class="row">
							<p>Check out the people doing the good work in your area, or for a cause you care for!</p>
						</div>
						
						<div class="row">
							<form action="advanceSearch" class="navbar-form" id="searchForm2"> 
							<div class="" >
							      <div class="input-group">	
							      	<input type='text' name="bsQuery" class="bsQuery form-control" value="" />
							      	
								      <div class="input-group-btn">
								        <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Search <span class="caret"></span></button>
								        <ul class="dropdown-menu dropdown-menu-right">
								          <li><a href='#' onclick="basicSearch2('sbn')">by Name</a></li>
								          <li><a href='#' onclick="basicSearch2('sbc1')">by Cause</a></li>
								          <li role="separator" class="divider"></li>
								          <li style="z-index:1042;"><a href='#' onclick="basicSearch2('as')">Advanced Search</a></li>
								        </ul>
								      </div>
								   </div>
							  
							    <script>
								    $('.bsQuery').on("keypress", function(e) {
									        if (e.keyCode == 13) {
									        	basicSearch2('sbn');
									            return false; // prevent the button click from happening
									        }
									});
							    </script>
						  		</div>
						  		<input type="hidden" name="searchAction" id="searchAction2" value="blank"/>
						      	<input type="hidden" name="start" value="0"/>
						    </form>
						</div>
					</div>
				</div>
				
			</section>
		</section>
	</section>
<hr>
	<!-- ****************************** Gallery Section ************************** -->

	
	<section id="gallery-ngos" class="block">
		<section class="row">
			<section class="row">
				<div class="title-box" style="color:black;"><h1 class="block-title wow animated rollIn">
				<span class="bb-top-left" style="border-color: black; "></span>
				<span class="bb-bottom-left" style="border-color: black; "></span>
				NGOs
				<span class="bb-top-right" style="border-color: black; "></span>
				<span class="bb-bottom-right" style="border-color: black; "></span>
				</h1></div>
			</section>
			<section class="row">
		     	<div class="col-md-12 col-sm-12">
			     	<div id="jssor_N" style="position: relative;  top: 0px; left: 0px; height: 150px; overflow: hidden; visibility: hidden;">
				        <!-- Loading Screen -->
				        <div data-u="loading" style="position: absolute; top: 0px; left: 0px;">
				            <div style="filter: alpha(opacity=70); opacity: 0.7; position: absolute; display: block; top: 0px; left: 0px; width: 100%; height: 100%;"></div>
				            <div style="position:absolute;display:block;background:url('images/loading.gif') no-repeat center center;top:0px;left:0px;width:100%;height:100%;"></div>
				        </div>
				        <div data-u="slides"  id="ngos-slides" style="cursor: default; position: relative; top: 0px; left: 0px; width: 100%; height: 150px; overflow: hidden;">
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
			</section>
		</section>
	</section>
	<section id="gallery-causes" class="block">
		<section class="row">
			<section class="row">
				<div class="title-box" style="color:#fff;"><h1 class="block-title wow animated rollIn">
				<span class="bb-top-left" style="border-color: #fff; "></span>
				<span class="bb-bottom-left" style="border-color: #fff; "></span>
				Causes
				<span class="bb-top-right" style="border-color: #fff; "></span>
				<span class="bb-bottom-right" style="border-color: #fff; "></span>
				</h1></div>
			</section>
			<section class="row">
			     	<div class="col-md-12 col-sm-12">
				     	<div id="jssor_C" style="position: relative; top: 0px; left: 0px;  height: 150px; overflow: hidden; visibility: hidden;">
					        <!-- Loading Screen -->
					        <div data-u="loading" style="position: absolute; top: 0px; left: 0px;">
					            <div style="filter: alpha(opacity=70); opacity: 0.7; position: absolute; display: block; top: 0px; left: 0px; width: 100%; height: 100%;"></div>
					            <div style="position:absolute;display:block;background:url('images/loading.gif') no-repeat center center;top:0px;left:0px;width:100%;height:100%;"></div>
					        </div>
					        <div data-u="slides" id="causes-slides" style="cursor: default; position: relative; top: 0px; left: 0px; width: 100%; height: 150px; overflow: hidden;">
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
			</section>
		</section>
	</section>
	<section id="gallery-events" class="block">
		<section class="row">
			<section class="row">
				<div class="title-box" style="color:black;"><h1 class="block-title wow animated rollIn">
				<span class="bb-top-left" style="border-color: black; "></span>
				<span class="bb-bottom-left" style="border-color: black; "></span>
				Events
				<span class="bb-top-right" style="border-color: black; "></span>
				<span class="bb-bottom-right" style="border-color: black; "></span>
				</h1></div>
			</section>
			<section class="row">
		     	<div class="col-md-12 col-sm-12">
			     	<div id="jssor_E" style="position: relative; top: 0px; left: 0px;  height: 267px; overflow: hidden; visibility: hidden;">
				        <!-- Loading Screen -->
				        <div data-u="loading" style="position: absolute; top: 0px; left: 0px;">
				            <div style="filter: alpha(opacity=70); opacity: 0.7; position: absolute; display: block; top: 0px; left: 0px; width: 100%; height: 100%;"></div>
				            <div style="position:absolute;display:block;background:url('images/loading.gif') no-repeat center center;top:0px;left:0px;width:100%;height:100%;"></div>
				        </div>
				        <div data-u="slides" id="events-slides" style="cursor: default; position: relative; top: 0px; left: 0px;width: 100%; height: 267px; overflow: hidden;">
					            
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
			</section>
		</section>
	</section>



	<!-- All the scripts -->

	<script src="/js/wow.min.js"></script>
	<script src="/js/script.js"></script>
	<script type="text/javascript" src="/js/jssor.slider.min.js"></script>
 	<script src="/js/index.js"></script>
 	<script src="/js/slick.min.js"></script>
 	<script src="/js/validator.js"></script>
    <!-- use jssor.slider.debug.js instead for debug -->
    <script>
    
       
   	$(window).load(function() {
   		window.mobileScreen = screen.width<480;
       	var cslides = $('#causes-slides');
       	var causes = [];
       	$.ajax({
               url : 'indexGetCausesList',
               data : '',
               type : 'POST',
               processData: false,
               contentType: false,
               success : function(jsonResponse) {
               	causes = jsonResponse.causesList;
               	causes.forEach(function(item){
               		var div = jQuery('<div/>').appendTo(cslides);
               		div.css({'display': 'none', 'text-align': 'center'});
               		var a = jQuery('<a />').appendTo(div);
               		a.attr("href", "advanceSearch.action?sCauseList="+item.causeName+"&searchAction=sbc2&sState=All&sCity=All&profileType=auto&start=0");
               		var causeIconSize = screen.width<480?' fa-2x':' fa-4x';
               		
               		var i = jQuery('<i/>', {
               			'class':item.causeIcon+causeIconSize,
               			'style':'color:white; text-shadow: gray 2px 2px'
               		}).appendTo(a);
               		
               		jQuery('<br/>').appendTo(a);
               		
               		var p = jQuery('<p/>', {
               			'text':item.causeName,
               			'style':'color:white; font-size:18px; text-shadow: gray 2px 2px'
               		}).appendTo(a);
               		
               	});
               	jssor_C_slider_init();
  				}
           });
	       	if (navigator.geolocation) {
	    		navigator.geolocation.getCurrentPosition(showPosition);
	    	} else {
	    		x.innerHTML = "Geolocation is not supported by this browser.";
	    	}
       });
   	
   	function openVerifyDiv(){
 		$('#verifyDiv').modal('show');
 	}
 	function openForgPassDiv(){
 		$('#loginModal').modal('hide');
 		$('#step1').css("display","block");
 		$('#step2').css("display","none");
 		$('#forgPassDiv').modal('show');
 	}
 	function goToStep2(){
 		$('#step1').css("display","none");
 		$('#step2').css("display","block");
 	}
 	function sendOTP(){
 		var fgEmail = document.getElementById('fgEmail').value;
 		if(fgEmail){
 			$('#loadingImg').css("display", "block");
 			
     		$.getJSON('sendOTP',
    				{"email" : fgEmail},
    				function(jsonResponse) {
    					if(jsonResponse.responseCode == 404){
    						alert(jsonResponse.responseString);
    					}
    					else{
    						$('#fgEmail').attr("readonly", "true");
    						goToStep2();
        					$('#loadingImg').css("display", "none");
    					}
    					
    				}
    		);
 		}
 		else {
 			alert("Email cannot be blank!!");
 		}
 	}
 	function basicSearch1(searchAction)
	 {
		var query = $('.bsQuery');
		 if((query[0] && query[0].value) || searchAction === 'as'){
			 var inpHidden = document.getElementById("searchAction1");
			 inpHidden.setAttribute("value", searchAction);
			 document.getElementById('searchForm1').submit();
		 } else {
			 alert("Please provide a query in the textfield to search for!");
		 }
		
	 }
 	function basicSearch2(searchAction)
	 {
		var query = $('.bsQuery');
		 if((query[1] && query[1].value) || searchAction === 'as'){
			 var inpHidden = document.getElementById("searchAction2");
			 inpHidden.setAttribute("value", searchAction);
			 document.getElementById('searchForm2').submit();
		 } else {
			 alert("Please provide a query in the textfield to search for!");
		 }
		
	 }
    </script>



	<div class="modal fade" tabindex="-1" role="dialog" id="loginModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">Login to WelfareCommunity</h4>
				</div>
				<s:form name="loginForm" id="loginForm" action="Login" method="post" >
				<div class="modal-body">
					<div class="row">
					
						<div class="form-group col-md-10 col-xs-10">
						  <label for="loginEmail">Email address</label>
						  <input type="email" class="form-control" id="loginEmail" placeholder="Email" name="loginUserBean.email" 
						  required="" data-error="Invalid entry!!" />
						  <div class="help-block with-errors"></div>
						</div>
						<div class="form-group col-md-10 col-xs-10">
						  <label for="loginPassword">Password</label>
						  <input type="password" class="form-control" id="loginPassword" placeholder="Password" name="loginUserBean.password"
						  required="" data-error="Required Field!!" />
						  <div class="help-block with-errors"></div>
						</div>
						
					
					 </div>
				</div>

				<div class="modal-footer">
					<button type="submit" id="login-btn" class="btn btn-primary" >Login</button>
					<button type="button" class="btn btn-link" onclick = "return openForgPassDiv()">Forgot Password?</button>
				</div>
				 </s:form>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>	<!-- /.modal-dialog -->
	<div class="modal fade" tabindex="-1" role="dialog" id="forgPassDiv" >
		<img src="images/loading.gif" id="loadingImg" style="display:none; position:absolute; top: 30%; left:47%; z-index:100"></img>
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title">Set new Password <span class="text-muted" style="font-size:smaller">(An OTP to reset password will be sent to this email.)</span></h4>
	      </div>
	      <s:form name="verificationForm"  action="createNewPassword" method="post" data-toggle="validator" role="form">
	      <div class="modal-body">
	      	<div class="form-group">
				<label for="fgEmail">Email ID:</label>
	         	<s:textfield type="email" cssClass="form-control" id="fgEmail" name="email" required=""/>
	         	<div class="help-block with-errors"></div>
	         </div>
	    </div>
	      <div id="step1">
		      <div class="modal-body">
		           <div class="form-group">
		           		<button type="button" class="btn btn-link form-control" onclick="goToStep2()">Already have an valid OTP?</button>
		           </div>
		           
		      </div>
		      <div class="modal-footer">
		      	<button type="button" class="btn btn-primary" name="Send" value="Send OTP" onclick="sendOTP()" id="bSendOTP">Send OTP</button>
		        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
		      </div>
		  </div>
		  <div id="step2">
		      
		      <div class="modal-body">
	            <div class="form-group">
				   	<label for="otp">OTP</label>
	            	<s:password cssClass="form-control" id="otp" name="passcode" required="" 
	            	data-minlength="8"  maxlength="8"/>
	            	<div class="help-block with-errors"></div>
	            </div>
	            <div class="form-group">
				   	<label for="fgPass">New Password</label>
	            	<s:password cssClass="form-control" id="fgPass" name="password" required="" 
	            	 data-minlength="6" maxlength="12"/>
	            	<div class="help-block with-errors"></div>
	            </div>
	            <div class="form-group">
				   	<label for="fgRePass">Re-enter New Password</label>
	            	<s:password cssClass="form-control" id="fgRePass" name="repassword" required=""
	            	 data-minlength="6" maxlength="12" data-match="#fgPass" data-match-error="Whoops, these don't match"/>
	            	<div class="help-block with-errors"></div> 
	            </div>
		      </div>
		      <div class="modal-footer">
		      	<s:submit name="submit" cssClass="btn btn-primary" value="Create New Password" id="bCreateNewPass"></s:submit>
		        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
		      </div>
		  </div>
	      </s:form>
	    </div><!-- /.modal-content -->
	  </div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
	<%@ include file="footer.jsp" %>
</body>
</html>
