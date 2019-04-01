<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<logic:equal name="role" value="admin">
	<logic:redirect page="/login.do"></logic:redirect>
</logic:equal>
<logic:notPresent name="productList">
	<logic:redirect page="/products.do"></logic:redirect>
</logic:notPresent>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Shopping Cart - Home Page</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
</head>
<body onload="activeTab('homeNav')">
	<%@ include file="/WEB-INF/jspf/nav.jspf"%>
	<div class="card" style="width: 50%; margin: auto;">
		<div class="card-body" style="text-align: center;">
			<h1>
				Hello
				<logic:notPresent name="role">Please login/Register to purchase orders !!</logic:notPresent>
				<logic:equal name="role" value="normal">
					<bean:write name="username" />
				</logic:equal>
			</h1>
			<div style="color: red;">
				<html:errors />
			</div>
		</div>

	</div>

	<logic:iterate name="productList" id="product">
	
		<bean:define id="productid" name="product" property="productId" />
		<bean:define id="available" name="product" property="isAvailable" />
		<bean:define id="productQuantity" name="product"
			property="productQuantity" />
		<logic:present name="username">
		<bean:define id="wListAvailable" name="product" property="wListAvailable"/>
		<bean:define id="cartAvailable" name="product" property="cartAvailable"/>
		<bean:define id="cartQuantity" name="product" property="cartQuantity" />
		</logic:present>
		
		
		<div class="card"
			style="width: 270px; float: left; margin: 2%; height: 270px;">
			<h3 class="card-header">
				<bean:write name="product" property="productName" />
			</h3>
			<div class="card-body">
				<logic:equal name="available" value="false">
					<a style="float: left; margin: 0.5%;" class="btn btn-secondary">Not
						Available</a>
				</logic:equal>
				<logic:equal name="available" value="true">
					<h5>Specification:</h5>
					<bean:write name="product" property="productSpec" />
					<h6>
						&#8377;
						<bean:write property="productPrice" name="product" />
					</h6>

					<h6>
						Quantity:
						<bean:write name="productQuantity" />
					</h6>
					<logic:present name="username">
						<logic:equal name="wListAvailable" value="false">
						<a href="user.do?method=addToWishlist&id=${productid}&page=1"
							style="float: left; margin: 0.5%;" class="btn btn-warning">+
							WList</a>
						</logic:equal>
						<logic:equal name="wListAvailable" value="true">
						<a href="user.do?method=removeWishlist&id=${productid}&page=1"
							style="float: left; margin: 0.5%;" class="btn btn-warning">-
							WList</a>
						</logic:equal>
						<logic:equal name="productQuantity" value="0">
							<a style="float: left; margin: 0.5%;" class="btn btn-secondary">Stock
								Out</a>
						</logic:equal>
						<logic:notEqual name="productQuantity" value="0">
							<logic:equal name="cartAvailable" value="true">
							<a href="user.do?method=addToCart&id=${productid}&page=1"
								style="float: left; margin: 0.5%;" class="btn btn-primary">+
								Cart(${cartQuantity})</a></logic:equal>
							<logic:equal name="cartAvailable" value="false">
							<a href="user.do?method=addToCart&id=${productid}&page=1"
								style="float: left; margin: 0.5%;" class="btn btn-primary">+
								Cart</a></logic:equal>
							
							<a href="user.do?method=buyNow&id=${productid}"
								style="float: left; margin: 0.5%;" class="btn btn-success">Buy</a>
						</logic:notEqual>
					</logic:present>
				</logic:equal>
			</div>
		</div>
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