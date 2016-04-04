var i = 3;
var addField = function() {
	var form = $('#content-form');
	i++;
	$('<input type="text" class="grey lighten-5" id="content-field-'+ i +'" size="255" name="content-field-'+ i +'"/>').appendTo(form);
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