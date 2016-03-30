<div class="x-center col-md-4">
	<form class="form-horizontal" action="" method="post">
		<div class="errors">
	    	<div class="form-group alert alert-danger center" role="alert">
	    		<strong></strong>
	    	</div>
	    </div>

		<input type="hidden" name="_csrf_token" value="" />

		<div class="form-group">
		    <input class="form-control input-lg center" type="text" id="username" name="username" value="" required="required" placeholder="Identifiant" />
		</div>

		<div class="form-group">
		    <input class="form-control input-lg center" type="password" id="password" name="password" required="required" placeholder="Mot de passe" />
		</div>

		<div class="form-group">
			<button id="_submit" name="_submit" type="submit" class="btn btn-lg btn-success btn-block">Connexion</button>
		</div>

		<div class="form-group">
			<a href="join" class="btn btn-xs btn-link btn-block ">Inscription</a>
		</div>
	</form>
</div>