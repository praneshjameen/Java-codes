<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Shopping Cart - Home Page</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
<style>
h3 {
	text-align: center;
}
</style>
</head>
<body onload="activeTab('productsNav')">
	<%@ include file="/WEB-INF/jspf/nav.jspf"%>
	<logic:equal name="role" value="normal">

		<logic:redirect page="/products.do"></logic:redirect>
	</logic:equal>
	<logic:equal name="role" value="admin">
		<div class="card" style="width: 50%; margin: auto;">
			<div class="card-body">
				<h1 style="text-align: center;">
					Hello <bean:write name="username"/> - ADMINISTRATOR
				</h1>
			</div>

		</div>
	</logic:equal>
	<logic:iterate name="productList" id="product">
		<bean:define id="productid" name="product" property="productId" />
		<bean:define id="available" name="product" property="isAvailable" />
		<html:form action="admin.do?method=updateProduct&id=${productid }">
			<div class="card"
				style="width: 250px; float: left; margin: 2%; height: 275px;">
				<h3 class="card-header">
					<bean:write name="product" property="productName" />
				</h3>
				<div class="card-body">
					<h5>Specification:</h5>
					<bean:write name="product" property="productSpec" />
					<h6>
						&#8377;
						<html:text property="productPrice" name="product" />
					</h6>
					<h6>
						Quantity:
						<html:text style="width:100px;" property="productQuantity"
							name="product" />
					</h6>

					<logic:equal name="available" value="true">
						<button style="float: left; margin: 0.5%;" class="btn btn-info">Update</button>
						<a href="admin.do?method=removeProduct&id=${productid }"
							style="float: left; margin: 0.5%;" class="btn btn-danger">Remove</a>
					</logic:equal>
					<logic:equal name="available" value="false">
						<a href="admin.do?method=enableProduct&id=${productid }"
							style="float: left; margin: 0.5%;" class="btn btn-success">Add</a>
					</logic:equal>
				</div>
			</div>
		</html:form>
	</logic:iterate>

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