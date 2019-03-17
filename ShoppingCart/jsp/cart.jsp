<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<logic:equal name="role" value="admin">
	<logic:redirect page="/login.do"></logic:redirect>
</logic:equal>
<logic:notPresent name="username">
	<logic:redirect page="/products.do"></logic:redirect>
</logic:notPresent>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Shopping Cart</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
</head>
<body>
	<%@ include file="/WEB-INF/jspf/nav.jspf"%>
	<logic:empty name="cartLists">
		<div class="card" style="width: 50%; margin: auto;">
			<div class="card-body" style="text-align: center;">
				<h1>Your Cart is empty</h1>
			</div>
		</div>
	</logic:empty>
	<div style="color: red; text-align: center;">
		<html:errors />
	</div>
	<%
		Integer totalPrice = 0;
	%>
	<logic:iterate name="cartLists" id="cartItems">
		<bean:define id="productId" name="cartItems" property="productId" />
		<bean:define id="productQuantity" name="cartItems"
			property="productQuantity" />
		<bean:define id="productPrice" name="cartItems"
			property="productPrice" />
		<div class="card" style="width: 70%; margin: 2%;">
			<h3 class="card-header">
				<bean:write name="cartItems" property="productName" />
				(&#8377;
				<bean:write name="cartItems" property="productPrice" />
				)
			</h3>
			<div class="card-body"
				style="padding: 2%; font-size: large; font-weight: bold;">
				<a href="user.do?method=addToCart&id=${productId}"
					class="btn btn-success" style="margin: 1%;">+</a>
				<bean:write name="cartItems" property="productQuantity" />
				<a
					href="user.do?method=removeFromCart&id=${productId}&quantity=${productQuantity}"
					class="btn btn-danger" style="margin: 1%; margin-right: 4%;">-</a>
				<%
					Integer price = (Integer) pageContext.findAttribute("productPrice");
						Integer quantity = (Integer) pageContext.findAttribute("productQuantity");
						Integer result = price * quantity;
						totalPrice += result;
				%>
				<%="&#8377; " + result%>
				<a
					href="user.do?method=removeFromCart&id=${productId}&deleteCart=${productQuantity}"
					style="float: right;" class="btn btn-danger">Remove</a>
			</div>
		</div>
	</logic:iterate>
	<logic:notEmpty name="cartLists">
		<div class="card" style="width: 70%; margin: 2%;">

			<div class="card-body"
				style="padding: 2%; font-size: large; font-weight: bold;">
				Total Amount = &#8377;
				<%=totalPrice%></div>
		</div>

		<a href="user.do?method=viewPurchase" class="btn btn-success"
			style="margin: 2%">PURCHASE </a>
	</logic:notEmpty>
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