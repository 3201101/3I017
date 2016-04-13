<!doctype html>
<html>
<%-- HTML Head --%>
<jsp:include page="inc/head.jsp"/>

<body>
	<header class="col-md-12">
		<%-- Barre de navigation --%>
		<jsp:include page="inc/navbar.jsp"/>
	</header>
	<main class="col-md-offset-2 col-md-8" id="flux">
		<h1>Documentation de l'API</h1>
		<p>L'application Log é Rythmes est fournie avec une API respectant partiellement les spécifications REST. Cette page d'accueil présente les différents services implémentés par l'API.</p>
		<div class="panel-group" id="doc" role="tablist" aria-multiselectable="true">
		</div>
		<script>
			$(function(){
				$.getJSON("${ app.path }/api/index", function(r) {
					$.each(r.doc, function(i, item) {
						var verbetype = "primary";
						if(item.method == "POST") { verbetype = "success"; }
						else if(item.method == "DELETE") { verbetype = "error"; }
						else if(item.method == "PUT") { verbetype = "warning"; }
						var note = "";
						if(item.note.length > 0) { note = "<div class=\"alert alert-" + item.status + "\" role=\"alert\">" + item.note + "</div>"; }
						$("#doc").append("<div class=\"panel panel-default\"><div class=\"panel-heading\" role=\"tab\" id=\"title_" + item.id + "\"><h4 class=\"panel-title\"><a role=\"button\" data-toggle=\"collapse\" data-parent=\"#doc\" href=\"#item_" + item.id + "\" aria-expanded=\"false\" aria-controls=\"collapse" + item.id + "\"><span class=\"label label-" + verbetype + "\">" + item.method + "</span> " + item.uri + "</a></h4></div><div id=\"item_" + item.id + "\" class=\"panel-collapse collapse\" role=\"tabpanel\" aria-labelledby=\"title_" + item.id + "\"><div class=\"panel-body\">" + note + "<h4>Description</h4><p id=\"description\">" + item.description + "</p><h4>Paramétres</h4><ul>" + item.parameters + "</ul><h4>Format de sortie</h4><pre>" + item.example + "</pre></div></div></div>");
						// TODO form live test ?
					});
				});
			});
		</script>
	</main>
	<footer class="col-md-12">
		<%-- Barre d'informations --%>
		<jsp:include page="inc/footbar.jsp"/>
	</footer>
</body>
</html>