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

function addTable(d,col1,col2) {
	var myTableDiv = document.getElementById(d);
	var table = document.createElement('TABLE');
	var tableHead = document.createElement('THEAD');
	var tableBody = document.createElement('TBODY');
	
	table.appendChild(tableBody);
	var heading=["Tilte","Rating"];
	//TABLE COLUMNS
	var tr = document.createElement('TR');
	for (i = 0; i < heading.length; i++) {
	    var th = document.createElement('TH');
	    th.appendChild(document.createTextNode(heading[i]));
	    tr.appendChild(th);
	}
	tableHead.appendChild(tr);
	
	//TABLE ROWS
	for (i = 0; i < col1.length; i++) {
	    var tr = document.createElement('TR');
        var td1 = document.createElement('TD');
        var td2 = document.createElement('TD');
        td1.appendChild(document.createTextNode(col1[i]));
        td2.appendChild(document.createTextNode(col2[i]));
        tr.appendChild(td1);
        tr.appendChild(td2);
        tableBody.appendChild(tr);
	}
	myTableDiv.appendChild(table);
}

var populateTables = function() {
	var tc = getCookie("pref");
	var rc = getCookie("ratings");
	document.cookie = "pref=; expires=Thu, 01 Jan 1970 00:00:00 UTC";
	document.cookie = "ratings=; expires=Thu, 01 Jan 1970 00:00:00 UTC";
	//tc="SomeMovie|SomeOtherMovie|YetAnotherMovie|SomeMovie|SomeOtherMovie|YetAnotherMovie|SomeMovie|SomeOtherMovie|YetAnotherMovie,SomeSong,SomeBook|SomeOtherBook";
	//rc="5|4|5|5|4|5|5|4|5,5,2|5"
	if(tc!=""&&rc!="") {
		var titles = tc.split(',');
		var ratings = rc.split(',');
		addTable("movie-card",titles[0].split('|'),ratings[0].split('|'));
		addTable("music-card",titles[1].split('|'),ratings[1].split('|'));
		addTable("book-card",titles[2].split('|'),ratings[2].split('|'));
	}
	else {
		$('#preferences-section').addClass('hide');
		Materialize.toast("No previous preferences!",5000);
	}
}

var populateDetails = function() {
	var dc = getCookie("details");
	var deets = dc.split(',');
	$('#userid').text(deets[0]);
	$('#age').text(deets[1]);
	$('#gender').text(deets[2]);
}

$(document).ready(function() {
	var uc = getCookie('username');
	if(uc.length>0) {
		$('#profile-name').text(uc);
	}
	populateDetails();
	populateTables();
	$('table').addClass('striped');
	$('table').css('background-color','#ffffff');
	$('.parallax').parallax();
});
