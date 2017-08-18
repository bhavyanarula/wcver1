<script src="/js/bootstrap-tour.min.js"></script>
<script src = "/js/tour.js"></script>
<script>
var tour = initializeTour("<s:property value="tourName" />");
var vHeadingWidth;
if(window.mobileScreen)
	vHeadingWidth = '6.5%';
else 
	vHeadingWidth = '3%';
$('.vertical-heading').css({width : vHeadingWidth});
</script>
<footer class="navbar-default row" style="padding-top:15px;">
	<div class="row" style="text-align:center;">
		<div class="fb-page col-md-4 col-sm-4" 
			  data-href="https://www.facebook.com/welfarecommunity.org/"
			  data-width="380" 
			  data-hide-cover="false"
			  data-show-facepile="false" 
			  data-show-posts="false"></div>
		<div class="col-md-4 col-sm-4">
			<div class="row">
				<img class="" style="height: 80px; width: 200px;" src="/images/full-logo.png"/>
				 
			</div>
			<div class="row">
				<a href="https://welfarecommunity.org/about">ABOUT US</a>
				 | <a href="openContactUs.action?pageOwnerCode=432d637645f127785c9b5fd4fe106821">CONTACT US</a>
				  | <a href="feedback.jsp" id="wc-feedback">FEEDBACK</a> 
			</div>
			<div class="row">
				a network for socially responsible people...
			</div>
		</div>
		<div class="col-md-3 col-sm-3 pull-right" style="margin-right:60px;">
			<ul class="list-group">
				<li class="list-group-item"><a href="<s:url action='advanceSearch'>
														<s:param name="searchAction">sbn</s:param>
														<s:param name="bsQuery">*</s:param>
														<s:param name="start">0</s:param>
													</s:url>"
						title="NGOs in Welfare Community">
							NGOs in Welfare Community
				  </a>
			  </li>
			  <li class="list-group-item">
				  <a href="<s:url action='getListOfEvents'></s:url>"
						title="Events in Welfare Community">
							Events in Welfare Community
				  </a>
			  </li>
			  <li class="list-group-item"><a href="<s:url action='getAllWCPhotos'>
			   		<s:param name="from">all</s:param>
	              </s:url>" title="View All">
							Welfare Community Wall
				  </a>
			  </li>
			</ul>
		</div>
	</div>
	<hr class="no-margin">
	<div class="row">
		<div class="pull-left" style="margin-left:10px; margin-top:10px">
			<span style="font-size:smaller;">Best viewed in Google Chrome and Mozilla Firefox</span>
		</div>
		<div class="pull-right" style="margin-right:10px;">
			Founder & Developer: Bhavya Narula 
			<a href="https://www.linkedin.com/in/bhavya-narula" target="_blank"><i class="fa fa-linkedin-square fa-2x" style="color:#0177b5"></i></a>
			<a href="https://www.facebook.com/bhavya.narula" target="_blank"><i class="fa fa-facebook-official fa-2x" style="color:#3b5998"></i></a>
		</div>
	</div>
</footer>
