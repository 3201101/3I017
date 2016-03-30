<nav class="navbar navbar-default navbar-fixed-top">
	<div class="container-fluid col-md-8 col-md-offset-2">
		<%-- Snippet Bootstrap pour la navigation mobile --%>
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar-collapse" aria-expanded="false">
				<span class="sr-only">Toggle navigation</span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<%-- Titre affiché sur la barre de navigation --%>
			<a class="navbar-brand" href="#">${ app.name }</a>
		</div>

		<%-- Contenu de la barre de navigation --%>
		<div class="collapse navbar-collapse" id="navbar-collapse">
		
			<%-- Liens pour l'affichage des posts --%>
			<ul class="nav navbar-nav">
				<%-- TODO Conditionner l'active --%>
				<li class="active"><a href="${ app.path }/accueil">Accueil</a></li>
				<li><a href="${ app.path }/amis">Amis</a></li>
				<li><a href="${ app.path }/annonces">Annonces</a></li>
			</ul>

			<%-- Partie droite de la barre de navigation --%>
			<ul class="nav navbar-nav navbar-right">
				<%-- TODO Champ de recherche --%>
				<li>
					<form class="navbar-form navbar-right" role="search">
						<div class="form-group">
							<input type="text" class="form-control" placeholder="Recherche">
						</div>
						<button type="submit" class="btn btn-default btn"><span class="glyphicon glyphicon-search" aria-hidden="true"></span></button>
					</form>
				</li>
				<%-- TODO Connexion ou profil --%>
				<%-- <li><a href="join">Connexion</a></li> --%>
				<li>
					<form class="navbar-form navbar-right" onsubmit="login()">
						<div class="form-group">
							<input type="text" class="form-control" placeholder="Login">
						</div>
						<div class="form-group">
							<input type="password" class="form-control" placeholder="Password">
						</div>
						<button type="submit" class="btn btn-default btn">Connecter</button>
					</form>
				</li>
			</ul>
		</div>
	</div>
</nav>
