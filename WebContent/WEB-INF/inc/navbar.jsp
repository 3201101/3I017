<nav class="navbar navbar-default navbar-inverse navbar-fixed-top">
	<div class="container-fluid col-md-8 col-md-offset-2">
		<%-- Snippet Bootstrap pour la navigation mobile --%>
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar-collapse" aria-expanded="false">
				<span class="sr-only">Toggle navigation</span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<%-- Titre affichÃ© sur la barre de navigation --%>
			<a class="navbar-brand" href="#">${ app.name }</a>
		</div>

		<%-- Contenu de la barre de navigation --%>
		<div class="collapse navbar-collapse" id="navbar-collapse">

			<%-- Liens pour l'affichage des posts --%>
			<ul class="nav navbar-nav onlogin">
				<%-- TODO Conditionner l'active --%>
				<li class="active" id="ln_home"><a href="#">Accueil</a></li>
				<li><a href="#" id="ln_friends">Mes amis</a></li>
				<li><a href="#" id="ln_messages">Mes messages</a></li>
			</ul>

			<%-- Partie droite de la barre de navigation --%>
			<ul class="nav navbar-nav navbar-right">
				<%-- TODO Champ de recherche --%>
				<li>
					<form class="navbar-form navbar-right" role="search">
						<div class="form-group">
							<input type="text" class="form-control" placeholder="Explorer les logs">
						</div>
						<button type="submit" class="btn btn-default btn"><span class="glyphicon glyphicon-search" aria-hidden="true"></span></button>
					</form>
				</li>
				<%-- TODO Connexion ou profil --%>
				<li class="dropdown onlogout" id="login_dropdown">
					<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Loggez-vous !</a>
					<div class="dropdown-menu" id="login">
						<div class="container-fluid">
							<form class="form-horizontal">
								<div class="form-group">
									<div class="col-sm-12">
										<input type="text" class="form-control" id="login_username" placeholder="Identifiant">
									</div>
								</div>
								<div class="form-group">
									<div class="col-sm-12">
										<input type="password" class="form-control" id="login_password" placeholder="Mot de passe">
									</div>
								</div>
								<div class="form-group">
									<div class="col-sm-12">
										<button type="submit" class="btn btn-success btn-block" id="login_submit">Connexion</button>
									</div>
								</div>
								<div class="form-group">
									<div class="col-sm-12">
										<button type="button" class="btn btn-primary btn-block" data-toggle="modal" data-target="#join_modal">Inscription</button>
									</div>
								</div>
							</form>
						</div>
					</div>
				</li>
				<li class="dropdown onlogin" id="logout_dropdown">
					<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Mon Ln</a>
					<ul class="dropdown-menu">
						<li><a href="#" data-toggle="modal" data-target="#profile_modal">Profil</a></li>
						<li class="divider"></li>
						<li><a href="#" id="logout">Déconnexion</a></li>
					</ul>
				</li>
			</ul>
		</div>
	</div>
</nav>


<div class="modal fade" id="profile_modal" tabindex="-1" role="dialog" aria-labelledby="profile_modal_label">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title" id="profile_modal_label"></h4>
			</div>
			<div class="modal-body">
				<div class="modal-body">
				<div class="form-horizontal">
					<div class="form-group">
						<label for="profile_username" class="col-sm-3 control-label">Identifiant</label>
						<div class="col-sm-9">
							<p class="form-control-static" id="profile_username">
						</div>
					</div>
					<div class="form-group">
						<label for="profile_email" class="col-sm-3 control-label">Email</label>
						<div class="col-sm-9">
							<p class="form-control-static" id="profile_email">
						</div>
					</div>
					<div class="form-group">
						<label for="profile_nom" class="col-sm-3 control-label">Nom</label>
						<div class="col-sm-9">
							<p class="form-control-static" id="profile_nom">
						</div>
					</div>
					<div class="form-group">
						<label for="profile_prenom" class="col-sm-3 control-label">Prénom</label>
						<div class="col-sm-9">
							<p class="form-control-static" id="profile_prenom">
						</div>
					</div>
					<div class="form-group">
						<label for="profile_avatar" class="col-sm-3 control-label">Avatar</label>
						<div class="col-sm-9">
							<img src="" alt="avatar" class="img-circle" id="profile_avatar"/>
						</div>
					</div>
					<fieldset disabled>
					<div class="form-group">
						<label for="profile_admin" class="col-sm-3 control-label">Rôle</label>
						<div class="col-sm-9">
							<label>
								<input type="checkbox" id="profile_admin"> Administrateur
							</label>
						</div>
					</div>
					</fieldset>
					<div class="form-group">
						<label for="profile_friends" class="col-sm-3 control-label">Amis</label>
						<div class="col-sm-9">
							<select class="form-control" id="profile_friends">
							</select>
						</div>
					</div>
				</div>
			</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Fermer</button>
			</div>
		</div>
	</div>
</div>


<div class="modal fade" tabindex="-1" role="dialog" id="join_modal" aria-labelledby="join_modal_label">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title" id="join_modal_label">Inscription</h4>
			</div>
			<div class="modal-body">
				<div class="form-horizontal">
					<div class="form-group">
						<label for="join_username" class="col-sm-3 control-label">Identifiant</label>
						<div class="col-sm-9">
							<input type="text" class="form-control" id="join_username" placeholder="Identifiant" required>
							<span id="join_username_help" class="help-block"></span>
						</div>
					</div>
					<div class="form-group">
						<label for="join_password" class="col-sm-3 control-label">Mot de passe</label>
						<div class="col-sm-9">
							<input type="password" class="form-control" id="join_password" placeholder="Mot de passe" required>
							<span id="join_password_help" class="help-block"></span>
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-9 col-sm-offset-3">
							<input type="password" class="form-control" id="join_password2" placeholder="Confirmation du mot de passe" required>
							<span id="join_password2_help" class="help-block"></span>
						</div>
					</div>
					<div class="form-group">
						<label for="join_email" class="col-sm-3 control-label">Email</label>
						<div class="col-sm-9">
							<input type="email" class="form-control" id="join_email" placeholder="Adresse email" required>
							<span id="join_email_help" class="help-block"></span>
						</div>
					</div>
					<div class="form-group">
						<label for="join_nom" class="col-sm-3 control-label">Nom</label>
						<div class="col-sm-9">
							<input type="text" class="form-control" id="join_nom" placeholder="Nom de famille" required>
							<span id="join_nom_help" class="help-block"></span>
						</div>
					</div>
					<div class="form-group">
						<label for="join_prenom" class="col-sm-3 control-label">Prénom</label>
						<div class="col-sm-9">
							<input type="text" class="form-control" id="join_prenom" placeholder="Prénom" required>
							<span id="join_prenom_help" class="help-block"></span>
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-lg btn-default" data-dismiss="modal">Fermer</button>
				<button type="button" class="btn btn-lg btn-success" id="join_submit">S'inscrire</button>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
	var session = 0;
	var admin = false;
	var username = "";

	function login() {
		$(".onlogin").show();
		$(".onlogout").hide();
		if(admin) {
			$(".onroot").show();
		}
		$.ajax({
			type: "GET",
			url: "${ app.path }/api/users",
			dataType: "json",
			data: {
				username: username
			},
			success: function(r){
				if(r)
				{
					$("#profile_modal_label").text("Mon Profil : @" + r.username);
					$("#profile_username").text(r.username);
					$("#profile_nom").text(r.nom);
					$("#profile_prenom").text(r.prenom);
					$("#profile_email").text(r.email);
					$("#profile_avatar").attr("src", r.avatar);
					if(r.admin){
						$("#profile_admin").attr("checked", "checked");
					}
					$.ajax({
						type: "GET",
						url: "${ app.path }/api/friends",
						dataType: "json",
						data: {
							session: session
						},
						success: function(r){
							$.each(r.friends, function(i, f) {
								$("#profile_friends").append("<option>" + f + "</option>");
							});
						}
					});

				}
			}
		});
	}

	function logout() {
		$(".onlogin").hide();
		$(".onroot").hide()
		$(".onlogout").show();
	}

	$(logout());
	$(function(){
		var r = Cookies.get('session');
		if(r !== undefined){
			session = r.session;
			username = r.login;
			admin = r.admin;
			login();
		}
	});

	/* Connexion */

	$("#login").on("submit", function(){
		$.ajax({
			type: "POST",
			url: "${ app.path }/api/login",
			dataType: "json",
			data: {
				username: $("#login_username").val(),
				password: $("#login_password").val()
			},
			success: function(r){
				if(r.session) {
					session = r.session;
					username = r.login;
					admin = r.admin;
					login();
					Cookies.set('session', r);
					popalert("info", "Vous êtes connecté.");
				} 
				else {
					popalert("danger", r.error, r.status);
				}
			}
		});
		return false;
	});

	$("#logout").on("click", function(){
		$.ajax({
			type: "POST",
			url: "${ app.path }/api/logout",
			dataType: "json",
			data:{
				session: session
			},
			success: function(r){
			}
		});
		logout();
		session = 0;
		Cookies.remove('session');
		popalert("info", "Vous êtes déconnecté.");
	});


	/* Inscription */

	$("#join_username").on("change", function(){
		$.ajax({
			type: "GET",
			url: "${ app.path }/api/users",
			dataType: "json",
			data:{
				username: $("#join_username").val()
			},
			success: function(r){
				if(r.username !== undefined)
				{
					$("#join_username").parent().parent().addClass("has-error");
					$("#join_username_help").text("Ce nom d'utilisateur est déjà  pris.");
				}
				else if($("#join_username").val().length > 255)
				{
					$("#join_username").parent().parent().addClass("has-error");
					$("#join_username_help").text("Ce nom est trop long.");
				}
				else
				{
					$("#join_username").parent().parent().addClass("has-success");
					$("#join_username_help").empty();
				}
			}
		});
	});

	$("#join_password").on("change", function(){
		if($("#join_password").val().length < 8){
			$("#join_password").parent().parent().addClass("has-error").removeClass("has-success");
			$("#join_password_help").text("Votre mot de passe doit faire au moins 8 caractères.");
		}
		else if($("#join_password").val() > 255)
		{
			$("#join_password").parent().parent().addClass("has-error").removeClass("has-success");
			$("#join_password_help").text("Ce mot de passe est trop long.");
		}
		else{
			$("#join_password").parent().parent().removeClass("has-error").addClass("has-success");
			$("#join_password_help").empty();
		}
	});

	$("#join_password2").on("change", function(){
		if($("#join_password").val() != $("#join_password2").val()){
			$("#join_password2").parent().parent().addClass("has-error").removeClass("has-success");
			$("#join_password2_help").text("Les mots de passe ne correspondent pas.");
		}
		else{
			$("#join_password2").parent().parent().removeClass("has-error").addClass("has-success");
			$("#join_password2_help").empty();
		}
	});

	$("#join_email").on("change", function(){
		if($("#join_email").val().length > 255)
		{
			$("#join_email").parent().parent().addClass("has-error").removeClass("has-success");
			$("#join_email_help").text("Cet email est trop long.");
		}
		else{
			$("#join_email").parent().parent().removeClass("has-error").addClass("has-success");
			$("#join_email_help").empty();
		}
	});

	$("#join_nom").on("change", function(){
		if($("#join_nom").val().length > 255)
		{
			$("#join_nom").parent().parent().addClass("has-error").removeClass("has-success");
			$("#join_nom_help").text("Ce nom est trop long.");
		}
		else{
			$("#join_nom").parent().parent().removeClass("has-error").addClass("has-success");
			$("#join_nom_help").empty();
		}
	});

	$("#join_prenom").on("change", function(){
		if($("#join_prenom").val().length > 255)
		{
			$("#join_prenom").parent().parent().addClass("has-error").removeClass("has-success");
			$("#join_prenom_help").text("Ce prénom est trop long.");
		}
		else{
			$("#join_prenom").parent().parent().removeClass("has-error").addClass("has-success");
			$("#join_prenom_help").empty();
		}
	});

	$("#join_submit").on("click", function(){
		$.ajax({
			type: "POST",
			url: "${ app.path }/api/users",
			dataType: "json",
			data:{
				username: $("#join_username").val(),
				password: $("#join_password").val(),
				email: $("#join_email").val(),
				nom: $("#join_nom").val(),
				prenom: $("#join_prenom").val(),
			},
			success: function(r){
				if(r.error === undefined)
				{
					popalert("success", "Votre inscription a bien été prise en compte !", "Félicitations");
				}
				else
					popalert("danger", r.error, r.status);
			},
			error: function(r, s, e){
				popalert("danger", e, s);
			}
		});
		$("#join_modal").modal('hide');
	});

</script>