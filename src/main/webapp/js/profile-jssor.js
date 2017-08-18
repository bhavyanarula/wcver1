$( document ).ready( function ()	 {
jssor_photos_slider_init = function() {
    
    var jssor_photos_options = {
      $AutoPlay: false,
      
      $SlideWidth: 210,
      $SlideSpacing: 3,
      $Cols: 4,
      $ArrowNavigatorOptions: {
        $Class: $JssorArrowNavigator$,
        $Steps: 4
      }
    };
    
    var jssor_photos_slider = new $JssorSlider$("jssor_photos", jssor_photos_options);
    
    //responsive code begin
    //you can remove responsive code if you don't want the slider scales while window resizing
    function ScaleSlider() {
        var refSize = jssor_photos_slider.$Elmt.parentNode.clientWidth;
        if (refSize) {
            //refSize = Math.min(refSize, 809);
            jssor_photos_slider.$ScaleWidth(refSize);
        }
        else {
            window.setTimeout(ScaleSlider, 30);
        }
    }
     ScaleSlider();
    $Jssor$.$AddEvent(window, "load", ScaleSlider);
    $Jssor$.$AddEvent(window, "resize", ScaleSlider);
    $Jssor$.$AddEvent(window, "orientationchange", ScaleSlider);    //responsive code end
};
if(document.getElementById('jssor_photos'))
	jssor_photos_slider_init();


jssor_events_slider_init = function() {
    
    var jssor_events_options = {
      $AutoPlay: false,
      
      $SlideWidth: 210,
      $SlideSpacing: 6,
      $Cols: 5,
      $ArrowNavigatorOptions: {
        $Class: $JssorArrowNavigator$,
        $Steps: 4
      }
    };
    
    var jssor_events_slider = new $JssorSlider$("jssor_events", jssor_events_options);
    
    //responsive code begin
    //you can remove responsive code if you don't want the slider scales while window resizing
    function ScaleSlider() {
        var refSize = jssor_events_slider.$Elmt.parentNode.clientWidth;
        if (refSize) {
            //refSize = Math.min(refSize, 809);
            jssor_events_slider.$ScaleWidth(refSize);
        }
        else {
            window.setTimeout(ScaleSlider, 30);
        }
    }
     ScaleSlider();
    $Jssor$.$AddEvent(window, "load", ScaleSlider);
    $Jssor$.$AddEvent(window, "resize", ScaleSlider);
    $Jssor$.$AddEvent(window, "orientationchange", ScaleSlider);    //responsive code end
};
if(document.getElementById('jssor_events'))
	jssor_events_slider_init();
});