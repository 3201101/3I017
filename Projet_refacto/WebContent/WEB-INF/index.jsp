<!DOCTYPE html>
<html>
<head>
	<title></title>
	<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
</head>
<body>
	<header>
		<jsp:include page="navbar.jsp">
			<jsp:param name="login" value="false"/>
		</jsp:include>
	</header>
	<aside>
		<jsp:include page="sidebar.jsp">
			<jsp:param name="login" value="false"/>
		</jsp:include>
	</aside>
	<div>
		<div id="new">
			
		</div>
		<div id="flux">
			
		</div>
	</div>
</body>
</html>