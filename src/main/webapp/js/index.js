var slideWidth = '';
 jssor_C_slider_init = function() {
	slideWidth = window.mobileScreen?100:200; 
    var jssor_C_options = {
      $AutoPlay: true,
      $AutoPlaySteps: window.mobileScreen?2:5,
      $SlideWidth: slideWidth,
      $SlideSpacing: 8,
      $Cols: 10,
      $ArrowNavigatorOptions: {
        $Class: $JssorArrowNavigator$,
        $Steps: window.mobileScreen?2:5
      }
    };
    
    var jssor_C_slider = new $JssorSlider$("jssor_C", jssor_C_options);
    
    //responsive code begin
    //you can remove responsive code if you don't want the slider scales while window resizing
    function ScaleSlider() {
        var refSize = jssor_C_slider.$Elmt.parentNode.clientWidth;
        if (refSize) {
            //refSize = Math.min(refSize, 809);
            jssor_C_slider.$ScaleWidth(refSize);
        }
        else {
            window.setTimeout(ScaleSlider, 30);
        }
    }
    ScaleSlider();
    $Jssor$.$AddEvent(window, "load", ScaleSlider);
    $Jssor$.$AddEvent(window, "resize", ScaleSlider);
    $Jssor$.$AddEvent(window, "orientationchange", ScaleSlider);
    //responsive code end
};

jssor_N_slider_init = function() {
    
    var jssor_N_options = {
      $AutoPlay: false,
      $SlideWidth: 200,
      $SlideSpacing: 10,
      $Cols: 5,
      $Loop : 0,
      $ArrowNavigatorOptions: {
        $Class: $JssorArrowNavigator$,
        $Steps: window.mobileScreen?2:5,
      }
    };
    
    var jssor_N_slider = new $JssorSlider$("jssor_N", jssor_N_options);
    

};

jssor_E_slider_init = function() {
    
    var jssor_E_options = {
      $AutoPlay: false,
      $SlideWidth: 200,
      $SlideSpacing: 10,
      $Cols: 5,
      $Loop : 0,
      $ArrowNavigatorOptions: {
        $Class: $JssorArrowNavigator$,
        $Steps: window.mobileScreen?2:5,
      }
    };
    
    var jssor_E_slider = new $JssorSlider$("jssor_E", jssor_E_options);
    
 
};

function designNGOSlides(ngo){
	var container = $('<div />', {'style': ' padding:5px; height:150px;'});
	var link = $('<a />', {'href':'https://welfarecommunity.org/'+ngo.alias,
						'title':ngo.ngoName}).appendTo(container);
	
	var imgRow = $('<div />', {'class':'row',
								'style':'text-align:center;'}).appendTo(link);
	var logo = $('<img />', {'class':'img-thumbnail img-circle',
							'style' : 'height:70px; width:70px;',
							'src': ngo.ngoLogoUrl}).appendTo(imgRow);
	var heading = $('<div />', {'class' : 'row media-heading ellipsis',
								'style' : 'text-align: center; color:white; background-color:rgb(115, 85, 74); border: 1px solid rgb(221, 221, 221);','text' : ngo.ngoName} ).appendTo(link);
	var hr = $('<hr />', {'class' : 'no-margin'} ).appendTo(link);
	var row = $('<div />', {'class':'row'}).appendTo(container);
	var address = ngo.ngoAddressBeanList;
	var mediaContent = $('<p/>',{
			'style': 'overflow:hidden; text-align:center;',
			'text' : ''
		}).appendTo(row);
	address.forEach(function(item){
		mediaContent.text(mediaContent.text()+" "+item.city + "  ");
	 });	
	return container;
}

function designEventSlides(event){
	
	var div = $('<div />', {'style':'padding:5px; color:black; border:1px #0a4c2c solid;height:calc(100%-10); width:100%;'} );
	var container = $('<a />', {'href':'https://welfarecommunity.org/event/'+event.id,
		'title':event.name,
		'style' : 'display:block; '}).appendTo(div);
	var img = $('<img />', {'src' : event.imageURL, 'style': 'height:160px; width:100%'}).appendTo(container);
	
	var heading = $('<div />', {'class' : 'row',
								'style' : 'color:black; font-size:larger; font-weight:bold; text-align: center; white-space: nowrap; overflow: hidden; text-overflow: ellipsis;','text' : event.name} ).appendTo(container);
	var hr = $('<hr />', {'class' : 'no-margin',
						'style' : 'border-top: 1px solid #c3bcbc;'} ).appendTo(container);
	
	var d = new Date(event.calendar).toDateString();
	var dateTimeRow = $('<div />', {'class' : 'row'}).appendTo(container);
	var dateCol = $('<div />', {'text' : d, 'class' : 'col-md-7', style:'color:gray;'}).appendTo(dateTimeRow);
	
	var timeCol = $('<div />', {'text' : event.evtTime, 'class' : 'col-md-5', 'style' : 'text-align:right; color:gray;'}).appendTo(dateTimeRow);
	
	var hr = $('<hr />', {'class' : 'no-margin',
		'style' : 'border-top: 1px solid #c3bcbc;'} ).appendTo(container);
	
	$('<div />', {'text' : event.addressBean.city, 'class' : 'row', style:'color:gray;'}).appendTo(container);
	
	
	return div;
}

function loadNandEJssor(currentCity){
	$.getJSON('indexGetCityNGOList',
			{'currentCity':currentCity},
			function(jsonResponse) {
				var ngos = jsonResponse.currCityNgoList;
				if(ngos.length)
				{
					$('#cityNgosDiv').css("display", "block");
				
					var ngoSlides = $('#ngos-slides');
					ngos.forEach(function(item){
						designNGOSlides(item).appendTo(ngoSlides);
	            		
	            	});
	            	jssor_N_slider_init();
				}
            	var events = jsonResponse.currCityEventList;
            	if(events.length){
            		$('#cityEventsDiv').css("display", "block");
            		var eventSlides = $('#events-slides');
    				events.forEach(function(item){
    					designEventSlides(item).appendTo(eventSlides);
                		
                	});
                	jssor_E_slider_init();
            	}
				
            	
			});
}

function showPosition(position) {
	var lat = position.coords.latitude;
	var lngt = position.coords.longitude;
	
	$.ajax({
        url : 'https://maps.googleapis.com/maps/api/geocode/json?latlng='+lat+','+lngt+'&sensor=true',
        data : '',
        type : 'GET',
        processData: false,
        contentType: false,
        success : function(jsonResponse) {
        	var currentCity='';
        	var addComp = jsonResponse.results[0].address_components;
        	outer: addComp.forEach(function(item, indx){
        		var types = item.types;
        		inner : types.forEach(function(type){
        			if(/*type==='administrative_area_level_2' || */type==='locality'){
        				currentCity +=  item.long_name+',';
        				//break outer;
        			}
        				
        		});
        	});
        	loadNandEJssor(currentCity);
		},
		failure : function(resp){
			console.log("failure");
			console.log(resp);
		}
    });
}
function showLoginModal(){
	$('#loginModal').modal('show');
	$('#loginModal').on('shown.bs.modal', function () {
		
		$('#loginForm').validator();
		
		$('#loginForm').on('submit', function (e) {
			  if (e.isDefaultPrevented()) {
				 
				  e.preventDefault();
			  }
			});
	});
	$('#loginModal').on('hidden.bs.modal', function (e) {
		$('#loginForm').unbind('submit');
		    $(this).find('form').validator('destroy');
	  });
}





