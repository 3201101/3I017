<!doctype html>
<html>
	<%-- HTML Head --%>
	<jsp:include page="inc/head.jsp">
		<jsp:param name="login" value="false"/>
	</jsp:include>

	<body>
		<header class="col-md-12">
			<%-- Barre de navigation --%>
			<jsp:include page="inc/navbar.jsp"/>
		</header>
		<aside class="col-lg-3 col-lg-offset-2 col-md-5" id="sidebar">
			<%-- Barre latérle --%>
			<jsp:include page="inc/sidebar.jsp"/>
		</aside>
		<main class="col-lg-5 col-md-7" id="flux">
			<%-- Affichage des messages --%>
			<jsp:include page="inc/flux.jsp"/>
		</main>
		<footer class="col-md-12">
			<%-- Barre d'informations --%>
			<jsp:include page="inc/footbar.jsp"/>
		</footer>
	</body>
</html>