<article class="message panel panel-primary flux">
	<div class="panel-heading">
		<a href="#" id="flux_refresh">
			Flux d'informations <span class="pull-right glyphicon glyphicon-refresh"></span>
		</a>
	</div>
	<ul class="list-group" id="flux">
	</ul>
	<div class="panel-footer">
		<a class="btn btn-link btn-block" href="#" if="flux_more">Afficher plus d'informations</a>
	</div>
</article>

<script>
	var flux_max_id = 0;
	var flux_min_id = 0;
	$(function(){
		$.getJSON("${ app.path }/api/messages", function(r) {
			$.each(r.messages, function(i, m) {
				if(m.id > flux_max_id){
					flux_max_id = m.id;
					if(flux_min_id == 0)
						flux_min_id = m.id;
				}
				$.ajax({
					type: "GET",
					url: "${ app.path }/api/users",
					dataType: "json",
					data: {
						username: m.author,
					},
					success: function(r){
						pmessage(m, r);
					}
				});
			});
		});
	});

	function pmessage(m, u) {
		$("#flux").prepend("<li class=\"list-group-item media bg-" + m.type + "\"><div class=\"media-left\"><a href=\"#\"><img class=\"media-object avatar img-circle\" src=\"" + u.avatar + "\" alt=\"avatar\"/></a></div><div class=\"media-body\"><p><strong>" + u.prenom + " " + u.nom + "</strong> (@" + u.username + ")</p><p>" + m.message + "</p></div></li>");
	}

	function amessage(m, u) {
		$("#flux").append("<li class=\"list-group-item media bg-" + m.type + "\"><div class=\"media-left\"><a href=\"#\"><img class=\"media-object avatar img-circle\" src=\"" + u.avatar + "\" alt=\"avatar\"/></a></div><div class=\"media-body\"><p><strong>" + u.prenom + " " + u.nom + "</strong> (@" + u.username + ")</p><p>" + m.message + "</p></div></li>");
	}

	function newrefresh() {
		$.ajax({
			type: "GET",
			url: "${ app.path }/api/messages",
			dataType: "json",
			data: {
				reverse: "true",
				offset: flux_max_id,
				n: 0
			},
			success: function(r) {
				$.each(r.messages, function(i, m) {
					if(m.id > flux_max_id){
						flux_max_id = m.id;
					}
					$.ajax({
						type: "GET",
						url: "${ app.path }/api/users",
						dataType: "json",
						data: {
							username: m.author,
						},
						success: function(r){
							pmessage(m, r);
						}
					});
				});
			});	
		});
	}

	function oldrefresh() {
		$.ajax({
			type: "GET",
			url: "${ app.path }/api/messages",
			dataType: "json",
			data: {
				reverse: "false",
				offset: flux_max_id,
				n: 10
			},
			success: function(r) {
				$.each(r.messages, function(i, m) {
					flux_min_id = m.id;
					$.ajax({
						type: "GET",
						url: "${ app.path }/api/users",
						dataType: "json",
						data: {
							username: m.author,
						},
						success: function(r){
							amessage(m, r);
						}
					});
				});
			});	
		});
	}

	$("#flux_refresh").on("click", newrefresh());
	$("#flux_more").on("click", oldrefresh());
</script>