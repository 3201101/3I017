<head>
	<%-- Titre généré par les métadonnées d'AppSettings et PageSettings --%>
	<title>${ app.name } - ${ page.name }</title>
	<%-- Feuille de style Bootstrap --%>
	<link rel="stylesheet" type="text/css" href="${ app.path }/css/bootstrap.min.css">
	<%-- Feuille de style spécifique à l'application (surcharge et ajouts sur Bootstrap) --%>
	<link rel="stylesheet" type="text/css" href="${ app.path }/css/style.css">
	<%-- Bibliothèque JQuery --%>
	<script type="text/javascript" src="${ app.path }/js/jquery.js"></script>
	<%-- Bibliothèque de fonctions liés au rendu visuel proposé par Bootstrap --%>
	<script type="text/javascript" src="${ app.path }/js/bootstrap.min.js"></script>
	<%-- Plugin Templating pour JQuery --%>
	<script type="text/javascript" src="${ app.path }/js/jquery.template.min.js"></script>
</head>