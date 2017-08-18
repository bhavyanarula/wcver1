"use strict";
var loader = loader || {};
loader.start = function() {
	
	var y = $(window).scrollTop() + $(window).height() / 2;
	var x = $(window).scrollLeft() + $(window).width() / 2;
	if($('.loader').length){
		$('.loader').css({'display' : 'block', 'top' : y, left : x});
	} else {
	$('<div />', {style : 'border: 5px solid #f3f3f3;'+
		'-webkit-animation: spin 1s linear infinite;'+
	    'animation: spin 1s linear infinite;'+
	    'border-top: 5px solid #555;'+
	    'border-radius: 50%;'+
	    'width: 50px;'+
	    'height: 50px;'+
	    'position: absolute;'+
	    'z-index :999;'+
	    'top :'+y+'px; left:'+x+'px;',
	    class: 'loader'}).appendTo($(document.body));
	}
	
};

loader.stop = function(){
	$('.loader').css('display', 'none');
};