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

<div class="modal fade" id="message_modal" tabindex="-1" role="dialog" aria-labelledby="message_modal_label">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h2 class="modal-title" id="message_modal_label"></h2>
			</div>
			<div class="modal-body">
				<p id="message_modal_body"></p>
				<a type="button" class="btn btn-default btn-block">J'aime ce message</a>
				<a type="button" class="btn btn-default btn-block" id="new_friend">J'aime cet auteur</a>
				<div class="panel panel-default">
					<div class="panel-heading">Commentaires</div>
					<div class="panel-body">
						<form id="message_modal_form">
							<div class="form-group">
								<input type="text" class="form-control" id="com" placeholder="Commentaire...">
							</div>
						</form>
					</div>
					<ul class="list-group" id="message_modal_coms">
					</ul>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Fermer</button>
			</div>
		</div>
	</div>
</div>

<script>

	function updatemsg() {
		$(".modmsg").on("click", function(){
			var msgid = this.id;
			$.ajax({
				type: "GET",
				url: "${ app.path }/api/messages",
				dataType: "json",
				data: {
					id: msgid
				},
				success: function(r) {
					$("#message_modal_body").text(r.message);
					$.ajax({
						type: "GET",
						url: "${ app.path }/api/users",
						dataType: "json",
						data: {
							username: r.author,
						},
						success: function(u){
							$("#new_friend").on("click", function(){
								$.ajax({
									type: "POST",
									url: "${ app.path }/api/follow",
									dataType: "json",
									data: {
										session: session,
										followed: u.username
									},
									success: function(r){
										if(r.error) {
											popalert("danger", r.error, r.status);
										}
										else {
											popalert("success", "Vous vous êtes fait un nouvel ami !");
										}
									},
									error: function(r, s, e){
										popalert("danger", e, s);	
									}
								});
							});
							$("#message_modal_label").html(u.prenom + " " + u.nom + " (" + u.username + ") a dit :");
							$("#message_modal_form").on("submit", function() {
								$.ajax({
									type: "POST",
									url: "${ app.path }/api/messages",
									dataType: "json",
									data: {
										session: session,
										message: $("#com").val(),
										title: "",
										limited: "",
										annonce: "",
										type:"normal",
										parent: msgid
									},
									success: function(r){
										if(r.error) {
											popalert("danger", r.error, r.status);
										}
										else {
											popalert("success", "Commentaire envoyÃ© !");
										}
									},
									error: function(r, s, e){
										popalert("danger", e, s);	
									}
								});
								return false;
							});
							$(function(){
								$.ajax({
									type: "GET",
									url: "${ app.path }/api/comments",
									dataType: "json",
									data: {
										parent: msgid
									},
									success: function(r) {
										r.messages.sort(function(a,b) { compare_m(b, a); });
										$.each(r.messages, function(i, m) {
											if(flux_min_date > m.date)
												flux_min_date = m.date;
											$.ajax({
												type: "GET",
												url: "${ app.path }/api/users",
												dataType: "json",
												data: {
													username: m.author,
												},
												success: function(r){
													$("#message_modal_coms").prepend("<li class=\"list-group-item\"><strong>" + r.prenom + " " + r.nom + " (" + r.username + ")</strong> " + m.message + "</li>");
												}
											});
										});
									}
								});
							});
							$("#message_modal").modal('show');
						}
					});
				}
			});
		});
	}

	var flux_max_date = 0;
	var flux_min_date = 0;
	flux_max_date = 0;
	flux_min_date = 0;


	function compare_m(a, b){
		if(a.date < b.date)
			return 1;
		if(a.date > b.date)
			return -1;
		return 0;
	}

	$(function(){
		$.getJSON("${ app.path }/api/messages", function(r) {
			var x = r.messages.sort(compare_m);
			$.each(x, function(i, m) {
				if(flux_max_date < m.date)
					flux_max_date = m.date;
				if(flux_min_date == 0)
					flux_min_date = m.date;
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
		updatemsg();
	});
//	$(function(){newrefresh();});

	function pmessage(m, u) {
		$("#flux").prepend("<li class=\"list-group-item media bg-" + m.type + " modmsg\" id=\"" + m.id + "\"><div class=\"media-left\"><img class=\"media-object avatar img-circle\" src=\"" + u.avatar + "\" alt=\"avatar\"/></div><div class=\"media-body\"><p><strong>" + u.prenom + " " + u.nom + "</strong> (@" + u.username + ")</p><p>" + m.message + "</p></div></li>");
	}

	function amessage(m, u) {
		$("#flux").append("<li class=\"list-group-item media bg-" + m.type + " modmsg\" id=\"" + m.id + "\"><div class=\"media-left\"><img class=\"media-object avatar img-circle\" src=\"" + u.avatar + "\" alt=\"avatar\"/></div><div class=\"media-body\"><p><strong>" + u.prenom + " " + u.nom + "</strong> (@" + u.username + ")</p><p>" + m.message + "</p></div></li>");
	}

	function newrefresh() {
		$.ajax({
			type: "GET",
			url: "${ app.path }/api/messages",
			dataType: "json",
			data: {
				reverse: "true",
				offset: flux_max_date,
				n: 0
			},
			success: function(x) {
				var y = x.messages
				y.sort(compare_m);
				$.each(y, function(i, m){
					if(flux_max_date < m.date)
						flux_max_date = m.date;
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
			}
		});
		updatemsg();
	}

	function oldrefresh() {
		$.ajax({
			type: "GET",
			url: "${ app.path }/api/messages",
			dataType: "json",
			data: {
				reverse: "false",
				offset: flux_max_date,
				n: 10
			},
			success: function(r) {
				r.messages.sort(function(a,b) { compare_m(b, a); });
				$.each(r.messages, function(i, m) {
					if(flux_min_date > m.date)
						flux_min_date = m.date;
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
			}
		});
		updatemsg();
	}

	$("#flux_refresh").on("click", newrefresh);
	$("#flux_more").on("click", oldrefresh);

	$(setInterval(newrefresh, 5000));
</script>