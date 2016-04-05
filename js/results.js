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

var setResult = function(i,t,c) {	//index, title, confidence
	$('#result-'+i).text(t);
	var p = c+'%';
	$('.confidence-'+i).css('width',p);
	$('.confidence-'+i).css('background-color','#f3989b');
}

var setResults = function() {
	var ts = getCookie('titles');	//ts ->TitleS
	var cs = getCookie('confidence');	//ts ->ConfidenceS
	Materialize.toast(ts,2000);
	Materialize.toast(cs,2000);
	if((ts!='')&&(cs!='')) {
		var titles = ts.split('|');
		var confidences = cs.split('|');
		var i=1;
		while(i<=10) {
			setResult(i,titles[i-1],confidences[i-1]);
			i++;
		}
		document.cookie = "titles=; expires=Thu, 01 Jan 1970 00:00:00 UTC";
		document.cookie = "confidence=; expires=Thu, 01 Jan 1970 00:00:00 UTC";
			Materialize.toast("Awesome bro!!",2000);
	}
	else Materialize.toast("not awesome bro!!",2000);
}

$(document).ready(function() {
	var ck_value = getCookie('username');
	if(ck_value.length>0) { //cookie exists, therefore login must be have been successful
		$('#profile-name').text(ck_value + '  (Logout)');
	}
	setResults();
});
