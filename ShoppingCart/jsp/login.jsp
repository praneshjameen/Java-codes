<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<% response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Shopping Cart - Login Page</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
</head>
<body onload="activeTab('loginNav')">
	<%@ include file="/WEB-INF/jspf/nav.jspf"%>
	<logic:notPresent name="role">
		<div class="card" style="width: 50%; margin: auto;">
			<div class="card-body">
				<html:form action="/login.do">
					<div class="form-group">
						<label for="username">User Name</label>
						<html:text styleClass="form-control" styleId="username"
							property="username" />
					</div>
					<div class="form-group">
						<label for="password">Password</label>
						<html:password styleClass="form-control" styleId="password"
							property="password" />
					</div>
					<button onclick="return validate()" class="btn btn-primary">Login</button>
					<div style="color: red;">
						<html:errors />
					</div>
				</html:form>
			</div>
		</div>
		<script>
			function validate() {
				var username = document.getElementById("username").value;
				var password = document.getElementById("password").value;
				var user = /^[A-Za-z0-9]\w{4,19}$/;
				if (!username.match(user)) {
					alert("UserName cane be of alphanumeric with 5 to 20 characters");
					return false;
				} else if (password.length<5 || password.length>20) {
					alert("Password must be of 5 to 20 characters");
					return false;
				} else {
					return true;
				}
			}
			
		</script>
	</logic:notPresent>

	<logic:present name="role">
		<logic:redirect page="/login.do"></logic:redirect>
	</logic:present>
	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
		integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
		crossorigin="anonymous"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
		integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
		crossorigin="anonymous"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
		integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
		crossorigin="anonymous"></script>

</body>
</html>