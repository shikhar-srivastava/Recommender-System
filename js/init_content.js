var i = 3;

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
          return c.substring(len1,len2);  //remove those pesky quotaions
        }
    }
    return "";
}

var reco_check=function() { //check the sign-up status
  var msg = getCookie('recofailed');
  Materialize.toast(msg,3000);
  document.cookie = "reco=; expires=Thu, 01 Jan 1970 00:00:00 UTC";
} 

var addField = function() {
  if(i<10) {
    var form = $('#content-form');
    i++;
    $('<div class="row"><div class="input-field col s9"><input type="text" class="grey lighten-5" name="con-field-'+i+'" id="con-field-'+i+'" size=255/></div><div class="input-field col s3"><p class="range-field"><input type="range" style="padding-top: 5px" id="rating-'+i+'" name="rating-'+i+'" min="0" max="5" /></p></div></div>').prependTo(form);
    return false;
  }
  else {
    Materialize.toast("Max 10 titles!!",4000);
    $('#add-btn').addClass('disabled').removeClass("waves-effect waves-light");
  }
}

window.onkeydown = function (e) {
    var code = e.keyCode ? e.keyCode : e.which;
    if (code === 187) { // '+' key
        addField();
    }
};

$('#add-field').click(function() {
  addField();
});

var insert_check=function() { //check the sign-up status
  var msg = getCookie('insertfailed');
  Materialize.toast(msg,3000);
  document.cookie = "insertfailed=; expires=Thu, 01 Jan 1970 00:00:00 UTC";
}

var import_titles = function() {
  var tc = getCookie('titles_export');
  var cc = getCookie('export_check');
  if(tc!="" && cc!="") {
    var titles = tc.split('|');
    var checks = cc.split('|');
    var k = 1;
    for(var j=0; j < titles.length; j++){
      if(checks[j] == '1') {
        if(k>3) addField();
        $('#con-field-'+k).attr('value',titles[j]);
        k++;
      }
    }
  }
  //document.cookie = "titles_export=; expires=Thu, 01 Jan 1970 00:00:00 UTC";
  //document.cookie = "export_check=; expires=Thu, 01 Jan 1970 00:00:00 UTC";
}

var logout = function() {
  document.cookie = "username=; expires=Thu, 01 Jan 1970 00:00:00 UTC";
  window.location.href="index.html";
}

var getProfile = function() {
  var ck_value = getCookie('username');
  if(ck_value.length>0) { //cookie exists, therefore login must be have been succesful
    $('#profile-name').text(ck_value);
  }
}

function open_popup(form) {
    window.open('', 'formpopup', 'width=400,height=400,resizeable,scrollbars,status=0,titlebar=0');
    form.target = 'formpopup';
}

$(document).ready(function() {
      $('.modal-trigger').leanModal({
       dismissible: true,
       opacity: .5,
       in_duration: 200,
       out_duration: 200,
       starting_top: '10%'
    });
  $('.parallax').parallax();
  insert_check();
  getProfile();
  reco_check();
  if( getCookie("cType")===$('#cType').text()) import_titles();
  Materialize.toast("Tip : Press '+' to add a new field",5000);
});