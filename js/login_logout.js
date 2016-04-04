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
	$('#profile-name').text('blah blah');
	$('#profile-name').addClass('hide');//hide the profile name by default
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
	window.location("index.html");
}
$(document).ready(function() {
	  check_uname();
});