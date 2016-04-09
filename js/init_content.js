var i = 3;

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


$('#add-field').click(function() {
  if(i<=10) {
//    Materialize.toast("What is life without pizza?",4000);
    var form = $('#content-form');
    i++;
    $('<div class="row"><div class="input-field col s9"><input type="text" class="grey lighten-5" name="con-field-'+i+'" id="con-field-'+i+'" size=255/></div><div class="input-field col s3"><p class="range-field"><input type="range" style="padding-top: 5px" id="rating-'+i+'" name="rating-'+i+'" min="0" max="5" /></p></div></div>').prependTo(form);
    return false;
  }
  else {
    Materialize.toast("Max 10 titles!!",4000);
    $('#add-btn').addClass('disabled').removeClass("waves-effect waves-light");
  }
});

var insert_check=function() { //check the sign-up status
  var msg = getCookie('insertfailed');
  Materialize.toast(msg,3000);
  document.cookie = "insertfailed=; expires=Thu, 01 Jan 1970 00:00:00 UTC";
}

$(document).ready(function() {
      $('.modal-trigger').leanModal({
       dismissible: true,
       opacity: .5,
       in_duration: 200,
       out_duration: 200,
       starting_top: '10%'
    });
  insert_check();
  });