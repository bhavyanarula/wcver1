<%@ taglib prefix="s" uri="/struts-tags" %>
<head>
	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
	<style>
.entity-info {
	background-color: white;
	-webkit-border-radius: 3px;
	border-radius: 3px;
	-webkit-box-shadow: 0 -1px 1.5px rgba(0, 0, 0, 0.12), 0 1px 1px 0
		rgba(0, 0, 0, 0.24);
	box-shadow: 0 -1px 1.5px rgba(0, 0, 0, 0.12), 0 1px 1px 0
		rgba(0, 0, 0, 0.24);
	-webkit-box-sizing: border-box;
	box-sizing: border-box;
	z-index: 1;
	padding: 5px 15px 5px 15px !important;
}

.description-row {
	margin: 0px auto;
	 position: relative; 
	z-index: 1;

	opacity :.95;
}
.entity-avatar-container {
    width: 144px;
    height: 0;
    margin: 0 auto 0 auto;
}

.entity-avatar-image {
    -webkit-background-size: cover;
    background-size: cover;
    -webkit-border-radius: 75px;
    border-radius: 75px;
    -webkit-box-shadow: 0 2px 0 0 rgba(0,0,0,0.17);
    box-shadow: 0 2px 0 0 rgba(0,0,0,0.17);
    background-color: #f5f5f5;
    background-position: center;
    border: 5px solid #f5f5f5;
    display: inline-block;
    height: 144px;
    position: absolute;
    top: -124px;
    width: 144px;
    vertical-align: middle;
}

.entity-name {
	    color: #212121;
    font-size: 28px;
    font-weight: 400;
    line-height: 1;
    padding-top: 8px;
    text-align: center;
    font-family: "Roboto",UILanguageFont,Arial,sans-serif;
    
}
.show-more-content {
    position: relative;
}
.cause-list{
	
}
.left-cause-list {
	
}
.right-cause-list {
	
}
.cause-item {
	background-color: white;
	width:100%;
	padding: 3px 3px 3px 3px;
	margin-bottom:3px;
	border-radius:3px;
	opacity:1;
	    -webkit-box-shadow: 0 -1px 1.5px rgba(0, 0, 0, 0.12), 0 1px 1px 0 rgba(0, 0, 0, 0.24);
    box-shadow: 0 -1px 1.5px rgba(0, 0, 0, 0.12), 0 1px 1px 0 rgba(0, 0, 0, 0.24);
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
        letter-spacing: 1.7px;
    font-size:12px;
}
.left-cause-item {
	/* float:right; */
	text-align:right;
}
.right-cause-item {
	/* float:left; */
	text-align:left;
}
.left-i{
	/*  margin-left:2px !important;  */
	padding:0;
	float:right;
}
.left-p{
	display:inline;
	margin-top: 3px !important;
	padding:0;
	float:right;
}
.right-i{
	/*  margin-right:2px !important;  */
	padding:0;
	float:left;
}
.right-p{
	display:inline;
	margin-top: 3px !important;
	padding:0;
	float:left;
}
.cityList::first-letter { 
    visibility : hidden;
}
.appreciation-href:hover {
	text-shadow:black;
}

</style>
<script type="text/javascript" src="/js/jquery.slimscroll.min.js"></script>
<script type="text/javascript" src="/js/appreciate.js"></script>
<script>
	
$( document ).ready( function ()	 {
	
	
	var causesArr = [];
	function addToLeftCauseList(item){
		item.name = item.name.replace("&amp;", "&");
		var leftDiv = $('.left-cause-list');
		
		var itemDiv = $('<div />', {'class':'cause-item left-cause-item row', 'title' : item.name}).appendTo(leftDiv);
		
		var iDiv = $('<div />', {'class':' left-i col-md-2 col-sm-2'}).appendTo(itemDiv);
		var i = $('<i />', {'class':item.icon}).appendTo(iDiv);
		
		var txtDiv = $('<div />', {'class':'left-p col-md-10 col-sm-10'}).appendTo(itemDiv);
		var txt = $('<p />', {'text':item.name, 'class':'ellipsis'}).appendTo(txtDiv);
		
		
	};
	function addToRightCauseList(item){
		item.name = item.name.replace("&amp;", "&");
		var rightDiv = $('.right-cause-list');
		
		var itemDiv = $('<div />', {'class':'cause-item right-cause-item row', 'title' : item.name}).appendTo(rightDiv);
		
		var iDiv = $('<div />', {'class':' right-i col-md-2 col-sm-2'}).appendTo(itemDiv);
		var i = $('<i />', {'class':item.icon}).appendTo(iDiv);
		
		var txtDiv = $('<div />', {'class':'right-p col-md-10 col-sm-10'}).appendTo(itemDiv);
		var txt = $('<p />', {'text':item.name,
							'class':'ellipsis'}).appendTo(txtDiv);
		
		
	};
	<s:iterator value="#session.pageOwnerBean.ngoCauseBeanList">
		causesArr.push({'name':"<s:property value="causeName"/>",
						'icon':"<s:property value="causeIcon"/>"});
	</s:iterator>
	causesArr.forEach(function(item, index){
		if(item.name === 'Others')
			return;
		if(index%2==0 && !window.mobileScreen)
			addToLeftCauseList(item);
		else
			addToRightCauseList(item);
	});
	
	$('.left-cause-list').slimScroll({
		width:'100%',
	    height:'160',
	    alwaysVisible: false,
	    allowPageScroll: false,
	    position:'left',
	    distance:'0px'
	});
	$('.right-cause-list').slimScroll({
		width:'100%',
	    height:'auto',
	    alwaysVisible: false,
	    allowPageScroll: false,
	    position:'right',
	    distance:'0px'
	});
});


</script>

</head>

	<div class="container">
	<s:if test="%{#session.pageOwnerBean.uid!=null || (pageOwnerCode!=null && !pageOwnerCode.equals(\"\"))}"> 
	<div class="row description-row">
		<div class="col-md-2 col-sm-2 cause-list " style="max-height:160px;">
			<div class="left-cause-list" style="margin-right:7px;"></div>
			
		</div>
		<div class="col-md-8 col-sm-8 col-xs-12 entity-info">
			<div class="entity-avatar-container row">
				<a href="https://welfarecommunity.org/<s:property value="#session.pageOwnerBean.alias" />" >
					<img class="entity-avatar-image"
						alt="<s:property value="#session.pageOwnerBean.ngoName" />"
						src="<s:property value="#session.pageOwnerBean.ngoLogoUrl" />">
				</a>
			</div>
			<h1 class="entity-name" ><s:property value="#session.pageOwnerBean.ngoName" /></h1>
			<div class="row show-more-content">
				<div class="row">
					<div class="pull-left col-md-6 col-sm-6 col-xs-12" >
						<a href="<s:url action='openContactUs'>
			            <s:param name="pageOwnerCode"><s:property value="#session.pageOwnerBean.uid"/></s:param>
			        	</s:url>" title="Contact us" >	
							<p class="ellipsis" style="letter-spacing:1.7px; color:black;" id="menu-item-contact-us">
								<i class="fa fa-phone" style="color:black"></i> <s:property value="#session.pageOwnerBean.ngoPhone" />
								<i class="fa fa-external-link" style="color:blue; font-size:10px;position: absolute; margin-left: 20px;"></i>
							</p>
							<p class="ellipsis" style="letter-spacing:1.7px; color:black;">
								<i class="fa fa-envelope" style="color:black"></i> <s:property value="#session.pageOwnerBean.ngoEmail" />
							</p>
						</a>
						<s:if test="%{#session.pageOwnerBean.type!=\"auto\"}">
							<p class="ellipsis" style="letter-spacing:1.7px;"><i class="fa fa-users" style="color:black"></i>&nbsp;<s:property value="#session.pageOwnerBean.noOfVolunteers" /> Volunteer(s)</p>
							<p class="ellipsis" style="letter-spacing:1.7px;" id="menu-item-photos">
							<a href="<s:url action='getPhotos'>
				             	 <s:param name="from">ngo</s:param>
				             	 <s:param name="pageOwnerCode"><s:property value="#session.pageOwnerBean.uid"/></s:param>
				              </s:url>" title="View All">
			             			<i class="fa fa-image" style="color:black"></i> Photos
			             	 </a>
			             </p>
			             </s:if>
					</div>
					<div class="pull-right col-md-6 col-sm-6 col-xs-12">
						<p class="ellipsis" style="letter-spacing:1.7px;" id="menu-item-alias">
							<i class="fa fa-globe" style="color:black"></i>&nbsp;https://welfarecommunity.org/<s:property value="#session.pageOwnerBean.alias" />
						</p>
						<p class="ellipsis cityList" style="letter-spacing:1.7px;">
						<i class="fa fa-map-marker" style="color:black"></i>
						<s:iterator value="#session.pageOwnerBean.ngoAddressBeanList">&nbsp;<s:property value="city"/></s:iterator>
			       		</p>
			       		<s:if test="%{#session.pageOwnerBean.type!=\"auto\"}">
			       		<p class="ellipsis" style="letter-spacing:1.7px;" >
				       		<s:if test="%{(#session.guest) || (#session.visitor && !#session.isUserAppreciated)}" >
					   		<a class="appreciation-href" href="javascript:void(0)" title="Appreciate"
						   		  onclick = "return appreciate(<s:property value="#session.guest" />, '<s:property value='#session.pageOwnerBean.uid'/>') ">
					   			<i class="fa fa-thumbs-o-up" style="color:black"  id="thumbsUpI"></i>
					   		</a>
					   		<script>
						   		$('#thumbsUpI').tooltip({
				            		trigger:'hover focus',
				            		placement:'bottom',
				            		delay: { "show": 200, "hide": 200 },
				            		title: "Click to appreciate.."
				            	
				            	});
					   		</script>
						</s:if>
						<s:else>
							<i class="fa fa-thumbs-o-up" style="color:black"></i>
						</s:else>
				       		<span id="tick" <s:if test="%{!#session.visitor || !#session.isUserAppreciated}" >style="display:none;"</s:if> >&#10004 </span>
			       		<span id="noOfAppreciations">
			       			<s:property value="#session.pageOwnerBean.noOfAppreciations" />
		       			</span> Appreciation(s)</p>
			       			<p class="ellipsis" style="letter-spacing:1.7px;" id="menu-item-events">
			       			<a href="<s:url action='getListOfEvents'><s:param name="pageOwnerCode">
									<s:property value="#session.pageOwnerBean.uid"/></s:param></s:url>"
								title="View Events">
								<i class="fa fa-calendar-check-o" style="color:black"></i> Events
							</a>
						</p>
						</s:if>
		       			
					</div>
				</div>
				
				<div class="row text-center" id="ngoDescription">
					
				</div>
			</div>
			
		</div>
		<div class="col-md-2 col-sm-2 col-xs-12 cause-list " style="max-height:160px;">
			<div class="right-cause-list" style="margin-left:7px;"></div>
		</div>
	</div>
	<script>
		var boolSlideshow = false;
		<s:if test="%{pageOwnerBean.listOfSlideshowPhotos.size()>0}">
			boolSlideshow = true;
		</s:if>
		if	(boolSlideshow){
			if(window.mobileScreen)
				$('.description-row').css("margin-top", "100px");
			else
				$('.description-row').css("margin-top", "380px");
		} else {
			if(window.mobileScreen)
				$('.description-row').css("margin-top", "50px");
			else
			$('.description-row').css("margin-top", "199px");
		}
	</script>
	</s:if>
	<s:else>
		<div class="row" style="height:54px;">..</div>
	</s:else>
	
	
	
	
	
	<div class="modal fade" tabindex="-1" role="dialog" id="guestAppreciateDiv">
	  <div class="modal-dialog">
		<div class="modal-content">
		  <div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
			<h4 class="modal-title">We need your name and email-id for Appreciation Records!</h4>
		  </div>
		  	<form role="form" id="appForm">
		  	<div class="modal-body">
		  		
			  	<div class="row">
				  <div class="col-md-6 col-xs-6 form-group">
				    <label for="name">Name</label>
				    <s:textfield cssClass="form-control" id="appByName" placeholder="Name" name="appByName" value="" required="" 
				    data-error="Required Field!!"  maxlength="100"></s:textfield>
				    <div class="help-block with-errors"></div>
				  </div>
				 </div>
				 <div class="row">
					  <div class="col-md-6 col-xs-6 form-group">
					    <label for="email">Email ID</label>
					    <s:textfield type="email" cssClass="form-control" id="appByEmail" placeholder="Email" name="appByEmail" value="" required="" 
					    data-error="Email address is invalid!"  maxlength="32"> </s:textfield>
					    <div class="help-block with-errors"></div>
			  		</div>
			  	</div>
			  	
			  </div>
			  <div class="modal-footer">
			  	 <div class="form-group">
				    <button type="submit" class="btn btn-primary">Appreciate</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
				 </div>
			  </div>
			  </form>
		</div><!-- /.modal-content -->
	  </div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
	

<%-- </s:if> --%>
