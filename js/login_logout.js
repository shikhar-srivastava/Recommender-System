var script = document.createElement('script');
script.src = 'js/jquery-2.2.2.min.js';
script.type = 'text/javascript';
document.getElementsByTagName('head')[0].appendChild(script);

var getCookie = function(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for(var i=0; i<ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') c = c.substring(1);
        if (c.indexOf(name) == 0) return c.substring(name.length,c.length);
    }
    return "";
}	
var check_uname = function() {
	$('#profile-name').addClass('hide');//hide the profile name by default
	$('#login-btn').removeClass('hide');
	var ck_value = getCookie('username');
	if(ck_value.length>0) { //cookie exists, therefore login must be have been succesful
		$('#login-btn').addClass('hide');
		Materialize.toast('', 4000);
		$('#profile-name').text(ck_value + '  (Logout)');
		$('#profile-name').removeClass('hide');
	}
	else {
		var erck = getCookie('loginfailed');	//Error Cookie
		if(erck.length>0) {
			document.cookie = "loginfailed=; expires=Thu, 01 Jan 1970 00:00:00 UTC";
			Materialize.toast(erck, 4000);
			location.reload();
		}
	}	
}
var logout = function() {
	document.cookie = "username=; expires=Thu, 01 Jan 1970 00:00:00 UTC";
	var Redirect = function(){window.location.href="index.html";};
	setTimeout('Redirect()', 5000);
}
$(document).ready(function() {
	  check_uname();
});
