<article class="message panel panel-primary flux"/>
	<div class="panel-heading">
		<a href="#">
			Flux d'informations <span class="pull-right glyphicon glyphicon-refresh"></span>
		</a>
	</div>
	<ul class="list-group" id="flux">
	</ul>
	<script>
		$(function(){
			$.getJSON("http://li328.lip6.fr:8280/0ln/api/messages", function(r) {
				$.each(r.doc, function(i, m) {
					$("#flux").append();
				});
			});
		});
	</script>
	<div class="panel-footer">
		<a class="btn btn-link btn-block">Afficher plus d'informations</a>
	</div>
</article>