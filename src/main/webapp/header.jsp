<%@ taglib prefix="s" uri="/struts-tags" %>
<head>
	
	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
	
	<link href="/css/font-awesome.min.css" rel="stylesheet" />
	<link rel="stylesheet" href="/css/bootstrap.css" />
	<link href="/css/Main.css" rel="stylesheet" type="text/css" />
	<link rel='shortcut icon' type='image/x-icon' href='/favicon.ico' />
	<link href="/css/bootstrap-tour.min.css" rel="stylesheet">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
	<script src="/js/loader.js"></script>
	<div id="fb-root"></div>
	<script>
	window.mobileScreen = screen.width<480;
	(function(d, s, id) {
	  var js, fjs = d.getElementsByTagName(s)[0];
	  if (d.getElementById(id)) return;
	  js = d.createElement(s); js.id = id;
	  js.src = "//connect.facebook.net/en_US/sdk.js#xfbml=1&version=v2.7";
	  fjs.parentNode.insertBefore(js, fjs);
	}(document, 'script', 'facebook-jssdk'));</script>
	<script>
	 function basicSearch(searchAction)
	 {
		 if($('#bsQuery').val() || searchAction === 'as'){
		 var inpHidden = document.getElementById("searchAction1");
		 inpHidden.setAttribute("value",searchAction);
		 document.getElementById('searchForm').submit();
		 } else {
			 alert("Please provide a query in the textfield to search for!");
		 }
	 }
	</script>
	<style>
		._54nc{
			
			/* line-height:20px; */
			font-size:12px;
		}
		.popover-title{
			padding: 7px 7px;
			text-align : center;
		}
		.topLinks{
			padding:5px 10px 5px 10px;
		}
		.topLinks:hover{
			color: white;
    		background: #4D4F4F;
		}
		.topLinks:hover a{
			color: white;
    		background: #4D4F4F;
		}
		.topLinks:hover i{
			color: white;
    		background: #4D4F4F;
		}
		.user-menu-popover-content {
			padding:3px 3px;
		}
		.mx-wd{
			max-width : 120px;
		}
		.navbar-form {
	    padding: initial;
    }
	</style>
</head>
<nav class="navbar navbar-default navbar-fixed-top nav ">

  	<div class="col-md-1 col-sm-1 col-xs-1">
	  	<a href="<s:url action='home'></s:url>" >
  			<img style="height: 48px; width: 60px; margin-left:30px; padding:2px;" src="/images/logo.png" title="Home"/>
	  	</a>
  	</div>
  	
  	
    <div class="col-md-2 col-sm-3 pull-right col-xs-5" style="height:48px;">
	    <s:form action="advanceSearch" cssClass="navbar-form" id="searchForm"> 
	      <div class="input-group">	
	      	<s:textfield name="bsQuery" id="bsQuery" cssClass="form-control" value="%{bsQuery}"></s:textfield>
	      	<input type="hidden" name="searchAction" id="searchAction1" value="blank"/>
	      	<input type="hidden" name="start" value="0"/>
		      <div class="input-group-btn">
		        <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Search <span class="caret"></span></button>
		        <ul class="dropdown-menu dropdown-menu-right">
		          <li><a href='#' onclick="basicSearch('sbn')">by Name</a></li>
		          <li><a href='#' onclick="basicSearch('sbc1')">by Cause</a></li>
		          <li role="separator" class="divider"></li>
		          <li style="z-index:1042;"><a href='#' onclick="basicSearch('as')">Advanced Search</a></li>
		        </ul>
		      </div>
		   </div>
	    </s:form>
	    <script>
		    $('#bsQuery').on("keypress", function(e) {
			        if (e.keyCode == 13) {
			        	basicSearch('sbn');
			            return false; // prevent the button click from happening
			        }
			});
	    </script>
	  </div>
	  <s:if test="%{#session.visitor || #session.owner}" >
	  	<div class="col-md-1 col-sm-1 col-xs-3  pull-right" style="position:relative;">
		  	<a tabindex="0" class="btn" role="button" id="user-menu" style="padding:0;">
		  		<img class="img-circle img-thumbnail" style="height:48px; width:48px" src="<s:property value="#session.logoUrl" />" id="img-logout">
		  		<i class="fa fa-sort-down" style="position:absolute; top: 38px; left: 26px; font-size:16px; "></i>
	  	</a>
	  	</div>
	  	<script>
	  	 $( document ).ready( function ()	 {
			  $('#user-menu').popover({
			  			  'title' : settingsTitle,
						  'html' : true,
						  'content' : settingsContent,
						  'trigger' : 'focus',
						  'placement' : 'bottom',
						  'template' : '<div class="popover mx-wd" role="tooltip"><div class="arrow"></div><h3 class="popover-title"></h3><div class="user-menu-popover-content popover-content"></div></div>'
					});
			})
			function settingsTitle(){
			 return "<p class=\"ellipsis\" style='margin:0;' title=\"<s:property value="#session.username" />\"><s:property value="#session.username" /></p>";
			 
		 }
		 function settingsContent(){
			 var topLinks = "<a class=\"_54nc\" href=\"";
			 var item1 = "<s:url action='openSettings'></s:url>\" title=\"Profile\"><div class=\"row topLinks\"><i class=\"fa fa-gear\"></i>&nbsp;&nbsp;&nbsp; Edit Profile</div></a>";
			 var item2 = "<s:url action='logout'></s:url>\" title=\"Logout\"><div class=\"row topLinks\"><i class=\"fa fa-sign-out\"></i>&nbsp;&nbsp;&nbsp; Logout</div></a>";
			 var item3 = "#\" title=\"Help\" onclick=\"tour.restart();\"><div class=\"row topLinks\"><i class=\"fa fa-question\"></i>&nbsp;&nbsp;&nbsp;&nbsp; Help </div></a>";
			 return topLinks + item1 + "" + topLinks + item2 + "" + topLinks + item3;
			 
		 }
		</script>
	</s:if>
</nav>
