<head>
	<%-- Titre g�n�r� par les m�tadonn�es d'AppSettings et PageSettings --%>
	<title>${ app.name } - ${ page.name }</title>
	<%-- Feuille de style Bootstrap --%>
	<link rel="stylesheet" type="text/css" href="${ app.path }/css/bootstrap.min.css">
	<%-- Feuille de style sp�cifique � l'application (surcharge et ajouts sur Bootstrap) --%>
	<link rel="stylesheet" type="text/css" href="${ app.path }/css/style.css">
	<%-- Biblioth�que JQuery --%>
	<script type="text/javascript" src="${ app.path }/js/jquery.js"></script>
	<%-- Biblioth�que de fonctions li�s au rendu visuel propos� par Bootstrap --%>
	<script type="text/javascript" src="${ app.path }/js/bootstrap.min.js"></script>
	<%-- Plugin Templating pour JQuery --%>
	<script type="text/javascript" src="${ app.path }/js/jquery.template.min.js"></script>
</head>