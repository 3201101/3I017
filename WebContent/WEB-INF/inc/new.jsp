<div class="panel panel-primary" id="new">
	<div class="panel-heading">
		<a data-toggle="collapse" href="#new_body" aria-expanded="true" aria-controls="new_body">
			Nouveau message <span class="pull-right glyphicon glyphicon-minus" aria-hidden="true" id="new_body_button"></span>
		</a>
	</div>
	<div class="panel-body collapse in" id="new_body">
		<form id="new_form">
			<div class="form-group">
				<textarea class="form-control" rows="3" id="new_message"></textarea>
			</div>
			<div class="collapse" id="new_options">
				<div class="form-group">
					<input type="text" class="form-control" id="new_title" placeholder="Titre">
				</div>
				<div class="form-group">
					<div class="checkbox">
						<label>
							<input type="checkbox" id="new_limited"> Limité aux amis
						</label>
					</div>
				</div>
				<div class="form-group onroot">
					<div class="checkbox">
						<label>
							<input type="checkbox" id="new_annonce"> Annonce
						</label>
					</div>
				</div>
				<select class="form-group form-control onroot" id="new_type">
					<option>normal</option>
					<option class="bg-info">info</option>
					<option class="bg-success">success</option>
					<option class="bg-warning">warning</option>
					<option class="bg-danger">danger</option>
				</select>
			</div>
			<button type="submit" class="btn btn-primary btn-lg btn-block" id="new_submit">Envoyer !</button>
			<button type="button" class="btn btn-default btn-sm btn-block" data-toggle="collapse" data-target="#new_options" aria-expanded="false" aria-controls="new_options" id="new_options_button">Plus d'options</button>
		</form>
	</div>
</div>

<script type="text/javascript">
	
	// Rendu esthétique

	$('#new_options').on('show.bs.collapse', function () {
		$('#new_options_button').text("Moins d'options");
	});
	$('#new_options').on('hide.bs.collapse', function () {
		$('#new_options_button').text("Plus d'options");
	});

	$('#new_body').on('show.bs.collapse', function () {
		$('#new_body_button').addClass('glyphicon-minus').removeClass('glyphicon-plus');
	});
	$('#new_body').on('hide.bs.collapse', function () {
		$('#new_body_button').removeClass('glyphicon-minus').addClass('glyphicon-plus');
	});
	
	$('#new_form').on('submit', function() {
		$.ajax({
			type: "POST",
			url: "${ app.path }/api/messages",
			dataType: "json",
			data: {
				session: session,
				message: $("#new_message").val(),
				title: $("#new_title").val(),
				limited: $("#new_limited").is(":checked"),
				annonce: $("#new_annonce").is(":checked"),
				type:$("#new_type").val(),
				parent: ""
			},
			success: function(r){
				if(r.error) {
					popalert("danger", r.error, r.status);
				}
				else {
					popalert("success", "Message envoyé !");
				}
			},
			error: function(r, s, e){
				popalert("danger", e, s);	
			}
		});
		return false;
	});

</script>