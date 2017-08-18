
(function ($) {
    $.fn.bAlert = function () {
    	var type = options.type;
    	if(type === 'danger')
    		alertClass = 'alert-danger alert-dismissable fade in';
    	if(type === 'success')
    		alertClass = 'alert-success alert-dismissable fade in';
    	var alertDiv = $('<div />', {
    		class : 'alert '+alertClass,
    	}).appendTo(this);
    	$('<a />', {
    		href : '#',
    		class : 'close',
    		'data-dismiss' : 'alert',
    		'aria-label' : 'close',
    		text : 'x'
    	}).appendTo(alertDiv);
    	$('<strong />', {
    		text : options.heading
    	}).appendTo(alertDiv);
    	
    	$('<span />', {
    		text : ' '+options.content,
    	}).appendTo(alertDiv);
    }
} (jQuery));