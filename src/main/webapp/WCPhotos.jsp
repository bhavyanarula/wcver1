<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>WelfareCommunity Wall</title>
 <style>



/* clearfix */
.grid:after {
  content: '';
  display: block;
  clear: both;
}
/* ---- .grid-item ---- */
.grid-sizer,
.grid-item {
  width: 25%;
}
.grid-item {
  float: left;
}

.grid-item img {
  display: block;
    width: 100%;
}
 </style>
 <%@ include file="header.jsp" %>
</head>
<body>
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

<div class="row" style="height:54px;">..</div>
<div class="container">
	<div class="row">
		<div class="col-md-4 col-sm-5 col-xs-5">
			<h3 class="pageHeading">WelfareCommunity Wall</h3>
		</div>
	</div>
	<hr></hr>
	<script src="https://npmcdn.com/imagesloaded@4.1/imagesloaded.pkgd.min.js"></script>
<script>
			function imageElement(image, owner) {
				var imgId = image.id;
				var imgUrl = image.url;
				var gridItem = $("<div />", {'class': 'grid-item'});
				var img = $(
						'<img />',	{'src' : imgUrl	}).appendTo(gridItem);
				return gridItem;
			}

			function getListOfImages(imgPageNo, pageOwnerCode, from) {

				$.getJSON('ajaxGetPhotos', {
					"imgPageNo" : imgPageNo,
					"pageOwnerCode" : pageOwnerCode,
					'from' : '<s:property value="from" />'
				}, function(jsonResponse) {
					var aboutUsArray = jsonResponse.listOfPhotos;
					aboutUsArray.forEach(function(item, index) {
						var gridItem = imageElement(item, <s:property value="#session.owner" />);
						var $grid = $('.grid').masonry({
		  itemSelector: '.grid-item',
							  percentPosition: true,
							  columnWidth: '.grid-sizer'
		});
						gridItem.imagesLoaded( function() {
							$grid.masonry().append( gridItem ).masonry('appended' , gridItem );
						});  
					});
					if (jsonResponse.endReached) {
						scene.remove();
						$("#loader").css('display', 'none');
					} 
		});
				scene.update(); 
				$("#loader").removeClass("active");
			}
		</script>
		<div class="dynamicContent">
			<div class="grid" >
				<div class="grid-sizer"></div>
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
								getListOfImages(++currPage,	"",	"<s:property value="from" />")
							}
						});
				getListOfImages(0, "",	"<s:property value="from" />");
			</script>
		</div>
</div>
<script>
</script>
<%@ include file="footer.jsp" %>
</body>
</html>