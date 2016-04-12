<div class="panel panel-primary" id="profile">
	<div class="panel-heading">
		<a data-toggle="collapse" href="#profile_body" aria-expanded="true" aria-controls="profile_body">
			À propos <span class="pull-right glyphicon glyphicon-plus" aria-hidden="true" id="profile_body_button"></span>
		</a>
	</div>
	<div class="panel-body collapse text-center" id="profile_body">
		<img src="http://placehold.it/256/00ff00/ffffff" alt="Avatar" class="img-thumbnail">
		<h1>@Utilisateur</h1>
		<a class="btn btn-default" href="#" role="button">Ajouter à mes amis</a>
	</div>
</div>

<script type="text/javascript">
	
	// Rendu esthétique

	$('#profile_body').on('show.bs.collapse', function () {
		$('#profile_body_button').removeClass('glyphicon-plus').addClass('glyphicon-minus');
	})
	$('#profile_body').on('hide.bs.collapse', function () {
		$('#profile_body_button').removeClass('glyphicon-minus').addClass('glyphicon-plus');
	})

</script>