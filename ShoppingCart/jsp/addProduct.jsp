<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Shopping Cart - Register page</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
</head>
<body onload="activeTab('addNav')">
	<%@ include file="/WEB-INF/jspf/nav.jspf"%>

	<div class="card" style="width: 50%; margin: auto;">
		<div class="card-body">
			<html:form action="/admin.do?method=addProduct">
				<div class="form-group">
					<label for="productName">Product Name</label>
					<html:text styleClass="form-control" styleId="productName"
						property="productName" />
				</div>
				<div class="form-group">
					<label for="productSpec">Product Specification</label>
					<html:text styleClass="form-control" styleId="productSpec"
						property="productSpec" />
				</div>
				<div class="form-group">
					<label for="productPrice">Product Price</label>
					<html:text styleClass="form-control" styleId="productPrice"
						property="productPrice" />
				</div>
				<div class="form-group">
					<label for="productQuantity">Product Quantity</label>
					<html:text styleClass="form-control" styleId="productQuantity"
						property="productQuantity" />
				</div>
				<button onclick="return validate()" class="btn btn-success">Add
					Product</button>
				<div style="color: red;">
					<html:errors />
				</div>

			</html:form>

		</div>
	</div>
	<script>
		function validate() {
			var productName = document.getElementById("productName").value;
			var productSpec = document.getElementById("productSpec").value;
			var productPrice = document.getElementById("productPrice").value;
			var productQuantity = document.getElementById("productQuantity").value;
			if (productName == "") {
				alert("Product name should be mentioned");
				return false;
			} else if (productSpec == "") {
				alert("Product specification should be mentioned");
				return false;
			} else if (productPrice == "") {
				alert("Product Price should be mentioned");
				return false;
			} else if (productQuantity == "") {
				alert("Product Quantity should be mentioned");
				return false;
			} else if (isNaN(productPrice)) {
				alert("Product Price should be a number");
				return false;
			} else if (isNaN(productQuantity)) {
				alert("Product Quantity should be a number");
				return false;
			} else if (Number(productPrice)<=0) {
				console.log(Number(productPrice));
				alert("Product price should not be zero or less");
				return false;
			} else if (Number(productQuantity )<=0) {
				alert("Product Quantity should not be zero or less");
				return false;
			}

			else {

				return true;
			}
			return false;
		}
	</script>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
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