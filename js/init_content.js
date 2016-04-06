var i = 3;

$('#add-field').click(function() {
  if(i<=10) {
//    Materialize.toast("What is life without pizza?",4000);
    var form = $('#content-form');
    i++;
    $('<div class="row"><div class="input-field col s9"><input type="text" class="grey lighten-5" name="con-field-'+i+'" id="con-field-'+i+'" size=255/></div><div class="input-field col s3"><p class="range-field"><input type="range" style="padding-top: 5px" id="rating-'+i+'" min="0" max="5" /></p></div></div>').prependTo(form);
    return false;
  }
  else {
    Materialize.toast("Max 10 titles!!",4000);
    $('#add-btn').addClass('disabled').removeClass("waves-effect waves-light");
  }
});

$(document).ready(function() {
      $('.modal-trigger').leanModal({
       dismissible: true,
       opacity: .5,
       in_duration: 200,
       out_duration: 200,
       starting_top: '10%'
    });
    
  });