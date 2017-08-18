var already = false;
function appreciate(isGuest, ato){
	if(!already){
	if(!isGuest)
	{
		loader.start();
		$.getJSON('ajaxAppreciateAction',
			   {"pageOwnerCode" : ato},
				function(jsonResponse) {
				   loader.stop();
				   document.getElementById("noOfAppreciations").innerHTML = jsonResponse.noOfAppreciations;
				   $('#appreciation').unbind();
				   document.getElementById("tick").style.display = "inline";
					   already = true;
				});
	}
	else 
	{
		$('#guestAppreciateDiv').modal('toggle');
		$('#guestAppreciateDiv').on('shown.bs.modal', function () {
			var gAppForm = $(this).find('form');
			gAppForm.validator('destroy').validator();
			gAppForm.validator().on('submit', function (e) {
				  if (e.isDefaultPrevented()) {
					 
				  } else {
					  appreciateByGuest(ato);
					  e.preventDefault();
				  }
				});
		});
		}
	}
	
	return false;
}

function appreciateByGuest(ato){
	$('#guestAppreciateDiv').modal('toggle');
	loader.start();
	$.getJSON('ajaxAppreciateAction',
			   {"pageOwnerCode" : ato, "appByEmail" : $('#appByEmail')[0].value, "appByName":$('#appByName')[0].value},
				function(jsonResponse) {
				   loader.stop();
				   document.getElementById("noOfAppreciations").innerHTML = jsonResponse.noOfAppreciations;
				   $('#appreciation').unbind();
				   document.getElementById("tick").style.display = "inline";
				   already = true;
				});
	return false;
}
