<%
	boolean name = (boolean)session.getAttribute("name");
%>
<nav class="navbar navbar-default navbar-fixed-top">
	<div class="container-fluid">
		<!-- Snippet Bootstrap pour la navigation mobile -->
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar-collapse" aria-expanded="false">
				<span class="sr-only">Toggle navigation</span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<!-- Titre affiché sur la barre de navigation -->
			<a class="navbar-brand" href="#"><!-- TODO TITLE -->Lima November</a>
		</div>

		<!-- Contenu de la barre de navigation -->
		<div class="collapse navbar-collapse" id="navbar-collapse">
			<!-- Champ de recherche -->
			<form class="navbar-form navbar-left" role="search">
				<div class="form-group">
					<input type="text" class="form-control" placeholder="Search">
				</div>
				<button type="submit" class="btn btn-default btn">Submit</button>
			</form>

			<!-- Liens pour l'affichage des posts -->
			<ul class="nav navbar-nav navbar-right">
				<li><a href="index">Flux global</a></li>
				<li><a href="friends">Posts d'amis</a></li>
			</ul>

			<!-- Liens pour la connexion/profil -->
			<ul class="nav navbar-nav navbar-right">
				<li><a href="login">Connexion</a></li>
				<li><a href="join">Inscription</a></li>
			</ul>
		</div>
	</div>
</nav>
