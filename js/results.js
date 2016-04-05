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
	$('#result'+i).text(t);
	$('#result'+i).setAttribute(style,'width: '+c+'%');
}

var setResults = function() {
	var ts = getCookie('titles');	//ts ->TitleS
	var cs = getCookie('confidence');	//ts ->ConfidenceS
	if((ts!='')&&(cs!='') {
		var titles = ts.split('|');
		var confidences = cs.split('|');
		var i=1;
		if((titles.length===10)&&(confidences.length===10)) {
			while(i<=10) {
				setResult(i,titles[i-1],confidences[i-1]);
			}
			Materialize.toast("Awesome bro!!",2000);
		}
		else Materialize.toast("Sorry bro!!",2000);
	}
	else Materialize.toast("not awesome bro!!",2000);
}

$(document).ready(function() {
	var ck_value = getCookie('username');
	if(ck_value.length>0) { //cookie exists, therefore login must be have been succesful
		$('#profile-name').text(ck_value + '  (Logout)');
	}
	var reco = getCookie('recofailed');
	setResults();
});
