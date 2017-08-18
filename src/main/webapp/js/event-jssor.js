$( document ).ready( function ()	 {
jssor_vol_slider_init = function() {
    
    var jssor_vol_options = {
      $AutoPlay: false,
      
      $SlideDuration: 160,
      $SlideWidth: 110,
      $SlideSpacing: 10,
      $Cols: 4,
      $ArrowNavigatorOptions: {
        $Class: $JssorArrowNavigator$,
        $Steps: 4
      }
    };
    
    var jssor_vol_slider = new $JssorSlider$("jssor_vol", jssor_vol_options);
    
    //responsive code begin
    //you can remove responsive code if you don't want the slider scales while window resizing
    function ScaleSlider() {
        var refSize = jssor_vol_slider.$Elmt.parentNode.clientWidth;
        if (refSize) {
            //refSize = Math.min(refSize, 809);
            jssor_vol_slider.$ScaleWidth(refSize);
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
if(document.getElementById("jssor_vol"))
	jssor_vol_slider_init();

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
});