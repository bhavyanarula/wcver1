/**
 * 
 */
var isGuest = true;
    $(document).ready(function() {
        $.active = false;
        $('body').bind('click keypress', function() { $.active = true; });
        $.getJSON('/heartbeat',
 				function(jsonResponse) {
 				   isGuest = jsonResponse.guest;
 				});
        checkActivity(3600000, 20000, 0); 
    });

    function checkActivity(timeout, interval, elapsed) {
        if ($.active) {
            elapsed = 0;
            $.active = false;
            //$.get('heartbeat', );
            $.getJSON('/heartbeat',
     				function(jsonResponse) {
     				   isGuest = jsonResponse.guest;
     				});
        }
        if (elapsed < timeout) {
            elapsed += interval;
            setTimeout(function() {
                checkActivity(timeout, interval, elapsed);
            }, interval);
        } else {
        	if(!isGuest){
        		alert("Your Session is timed out due to inactivity, Please login again");
        		
        		window.location = 'logout.action'; // Redirect to "session expired" page.
        	} else {
        		 setTimeout(function() {
                     checkActivity(timeout, interval, 0);
                 }, interval);
        	}
        }
    }
