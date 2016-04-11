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
	var link;
	var cType = getCookie("cType");
	if(cType==="movie") link = "http://www.imdb.com/find?q="+t.replace(" ","+")+"&s=all";
	else if(cType==="music") link = "https://www.youtube.com/results?search_query="+t.replace(" ","+");
	else if(cType==="book") link = "https://www.google.com/search?tbm=bks&q="+t.replace(" ","+");
	$('#result-'+i).text(t).attr("href",link);
	var p = c+'%';
	$('.confidence-'+i).css('width',p);
	$('.confidence-'+i).css('background-color','#f3989b');
}

var setResults = function() {
	var ts = getCookie('titles');	//ts ->TitleS
	var cs = getCookie('confidence');	//ts ->ConfidenceS
	if((ts!='')&&(cs!='')) {
		var titles = ts.split('|');
		var confidences = cs.split('|');
		var i=1;
		while(i<=10) {
			setResult(i,titles[i-1],confidences[i-1]);
			i++;
		}
		var d = new Date();
	    d.setTime(d.getTime() + (120*1000));
	    var expires = "expires="+d.toUTCString();
	    document.cookie = "titles_export=" + ts + "; " + expires;
		//document.cookie = "titles=; expires=Thu, 01 Jan 1970 00:00:00 UTC";
		//document.cookie = "confidence=; expires=Thu, 01 Jan 1970 00:00:00 UTC";
		Materialize.toast("Click on the titles to know more!",2000);
	}
	else Materialize.toast("not awesome bro!!",5000);
}

$(document).ready(function() {
	var ck_value = getCookie('username');
	if(ck_value.length>0) { //cookie exists, therefore login must be have been successful
		$('#profile-name').text(ck_value);
	}
	setResults();
});
