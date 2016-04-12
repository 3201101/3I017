<div id="alert">
</div>

<script type="text/javascript">

	$(".alert").delay(8000).slideUp(200, function() {
	    $(this).alert('close');
	});
	var alert_i = 0;
	function popalert(type, message, title) {
		var str = "";
		if(title) {
			str = "<strong>" + title + "</strong> ";
		}
		$("#alert").prepend("<div class=\"alert alert-" + type + " alert-dismissible\" role=\"alert\" id=\"alert-" + alert_i + "\"><button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\"><span aria-hidden=\"true\">&times;</span></button>" + str + message + "</div>");
		alert_i++;
	}
	
</script>