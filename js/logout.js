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

var logout = function() {
	document.cookie = "username=; expires=Thu, 01 Jan 1970 00:00:00 UTC";
	window.location.href="index.html";
}
$(document).ready(function() {
	var ck_value = getCookie('username');
	if(ck_value.length>0) { //cookie exists, therefore login must be have been succesful
		$('#profile-name').text(ck_value + '  (Logout)');
	}
});
