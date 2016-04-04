var i = 3;
var addField = function() {
	var form = $('#content-form');
	i++;
	$('<div class="row"><div class="input-field col s9"><input type="text" class="grey lighten-5" name="con-field-'+i+'" id="con-field-'+i+'" size=255/></div><div class="input-field col s3"><p class="range-field"><input type="range" id="rating-'+i+'" min="0" max="5" /></p></div></div>').appendTo(form);
	return false;
}

$(document).ready(function() {
      $('.modal-trigger').leanModal({
       dismissible: true,
       opacity: .5,
       in_duration: 200,
       out_duration: 200,
       starting_top: '10%'
    });
  });