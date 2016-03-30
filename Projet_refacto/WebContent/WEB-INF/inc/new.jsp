<div class="panel panel-primary" id="new">
	<div class="panel-heading"><a data-toggle="collapse" href="#new_body" aria-expanded="true" aria-controls="new_body">Nouveau message <span class="pull-right glyphicon glyphicon-minus" aria-hidden="true" id="new_body_button"></span></a></div>
	<div class="panel-body collapse" id="new_body">
		<form>
			<div class="form-group">
				<textarea class="form-control" rows="3"></textarea>
			</div>
			<div class="collapse" id="new_options">
				<div class="form-group">
					<input type="text" class="form-control" id="new_title" placeholder="Titre">
				</div>
				<div class="form-group">
					<div class="checkbox">
						<label>
							<input type="checkbox"> Limité aux amis
						</label>
					</div>
				</div>
				<div class="form-group">
					<div class="checkbox">
						<label>
							<input type="checkbox"> Annonce
						</label>
					</div>
				</div>
				<select class="form-group form-control">
					<option>Normal</option>
					<option class="bg-info">Information</option>
					<option class="bg-success">Nouveauté</option>
					<option class="bg-warning">Maintenance</option>
					<option class="bg-danger">Panne</option>
				</select>
			</div>
			<button type="button" class="btn btn-primary btn-lg btn-block" id="new_submit">Envoyer !</button>
			<button type="button" class="btn btn-default btn-sm btn-block" data-toggle="collapse" data-target="#new_options" aria-expanded="false" aria-controls="new_options" id="new_options_button">Plus d'options</button>
		</form>
	</div>
</div>

<script type="text/javascript">
	
	// Rendu esth�tique

	$('#new_options').on('show.bs.collapse', function () {
		$('#new_options_button').text("Moins d'options");
	})
	$('#new_options').on('hide.bs.collapse', function () {
		$('#new_options_button').text("Plus d'options");
	})

	$('#new_body').on('show.bs.collapse', function () {
		$('#new_body_button').addClass('glyphicon-minus').removeClass('glyphicon-plus');
	})
	$('#new_body').on('hide.bs.collapse', function () {
		$('#new_body_button').removeClass('glyphicon-minus').addClass('glyphicon-plus');
	})

</script>