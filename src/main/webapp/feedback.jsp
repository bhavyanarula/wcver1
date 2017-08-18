<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>WelfareCommunity Feedback</title>
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
<div class="row" style="height:54px;">..</div>



<div class="container">
	<div class="row">
		<div class="col-md-12 col-sm-12">
			<h3 class="pageHeading">WelfareCommunity Feedback</h3>
			<p>Please provide your comments/feedback/suggestions/complaints in the below thread:</p>
		</div>
	</div>
	<hr></hr>
	 <div id="disqus_thread" class="panel panel-default" style="padding:10px; max-height:"></div>
		<script>
		
		/**
		 *  RECOMMENDED CONFIGURATION VARIABLES: EDIT AND UNCOMMENT THE SECTION BELOW TO INSERT DYNAMIC VALUES FROM YOUR PLATFORM OR CMS.
		 *  LEARN WHY DEFINING THESE VARIABLES IS IMPORTANT: https://disqus.com/admin/universalcode/#configuration-variables */
		
		var disqus_config = function () {
		    this.page.url = "https://welfarecommunity.org/Feedback.jsp";  // Replace PAGE_URL with your page's canonical URL variable
		    this.page.identifier = "wc_feedbk"; // Replace PAGE_IDENTIFIER with your page's unique identifier variable
		    this.page.title = "Your Feedback for WelfareCommunity";
		};
		
		(function() { // DON'T EDIT BELOW THIS LINE
		    var d = document, s = d.createElement('script');
		    s.src = '//wecomm-org.disqus.com/embed.js';
		    s.setAttribute('data-timestamp', +new Date());
		    (d.head || d.body).appendChild(s);
		})();
		</script>
		<noscript>Please enable JavaScript to view the <a href="https://disqus.com/?ref_noscript">comments powered by Disqus.</a></noscript>
</div>
<%@ include file="footer.jsp" %>
</body>
</html>