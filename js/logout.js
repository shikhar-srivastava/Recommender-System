var getCookie = function(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for(var i=0; i<ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') c = c.substring(1);
        if (c.indexOf(name) == 0) {
            var len1 = name.length;
            var len2 = c.length;
            if(c.charAt(len1) == '"') len1++;
            if(c.charAt(len2-1) == '"') len2--;
            return c.substring(len1,len2);  //remove those pesky quotations
        }
    }
    return "";
}   

var logout = function() {
	document.cookie = "username=; expires=Thu, 01 Jan 1970 00:00:00 UTC";
	window.location.href="index.html";
}

var getProfile = function() {
	var ck_value = getCookie('username');
	if(ck_value.length>0)  $('#profile-name').text(ck_value);
}
$(document).ready(function() {
	getProfile();
});
