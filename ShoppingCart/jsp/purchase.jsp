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
<title>Shopping Cart - Purchase Page</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
</head>
<body>
	<%@ include file="/WEB-INF/jspf/nav.jspf"%>
	<logic:empty name="purchaseLists">
		<div class="card" style="width: 50%; margin: auto;">
			<div class="card-body" style="text-align: center;">
				<h1>You have not selected any Items to purchase</h1>
				<a href="/products.do" class="btn btn-success">Shop</a>
			</div>
		</div>
	</logic:empty>
	<logic:notEmpty name="purchaseLists">
		<%
			Integer totalPrice = 0;
		%>
		<div class="card" style="width: 70%; margin: 2%;">
			<h3 class="card-header">Products</h3>
			<logic:iterate name="purchaseLists" id="purchaseItems">
				<bean:define id="productId" name="purchaseItems"
					property="productId" />
				<bean:define id="productQuantity" name="purchaseItems"
					property="productQuantity" />
				<bean:define id="productPrice" name="purchaseItems"
					property="productPrice" />
				<div class="card" style="width: 50%; margin: 2%;">
					<h5 class="card-header">
						<bean:write name="purchaseItems" property="productName" />
						(&#8377;
						<bean:write name="purchaseItems" property="productPrice" />
						)
					</h5>
					<div class="card-body" style="padding: 2%; font-size: large;">
						Quantity : <strong><bean:write name="purchaseItems"
								property="productQuantity" /></strong>

						<%
							Integer price = (Integer) pageContext.findAttribute("productPrice");
									Integer quantity = (Integer) pageContext.findAttribute("productQuantity");
									Integer result = price * quantity;
									totalPrice += result;
						%>
						Total : <strong><%="&#8377; " + result%></strong>

					</div>
				</div>
			</logic:iterate>
			<div class="card" style="width: 50%; margin: 2%;">

				<div class="card-body" style="padding: 2%; font-size: large;">
					Net Total Amount : &#8377; <strong><%=totalPrice%></strong>
				</div>
			</div>
		</div>

		<div class="card" style="width: 70%; margin: 2%;">
			<h3 class="card-header">Address</h3>
			<div class="card-body" style="padding: 2%; font-size: large;">
				<html:form action="/user.do?method=addAddress">
					<div class="form-group">
						<logic:notEmpty name="addressList">
							<label for="address">Add a Address if you don't want to
								use the existing</label>
						</logic:notEmpty>
						<logic:empty name="addressList">
							<label for="address">Add Address</label>
						</logic:empty>
						<html:text styleClass="form-control" styleId="address"
							property="address" />
					</div>
					<button onclick="return validate()" class="btn btn-success">Add
						Address</button>
				</html:form>
				<br>
				<html:form action="user.do?method=makePurchase">
					<div class="form-group">
						<div class="form-check">
							<logic:iterate name="addressList" id="address">
								<bean:define id="addressId" name="address" property="addressId" />
								<html:radio styleClass="form-check-input" value="${addressId }"
									styleId="${addressId }" property="addressId" />
								<label class="form-check-label" for="${addressId }"> <bean:write
										name="address" property="address" /></label>
								<br>
							</logic:iterate>
						</div>
					</div>
					<button onclick="return checkAddress()" class="btn btn-success">MAKE
						PAYMENT</button>
				</html:form>
			</div>
		</div>
	</logic:notEmpty>
	<script>
		function validate() {
			var address = document.getElementById("address").value;
			if (address == "") {
				alert("Address field cannot be empty");
				return false;
			} else {
				return true;
			}
		}
		function checkAddress() {
			if ($('input[name=addressId]:checked').length == 0) {
				alert("Address has to be selected");
				return false;
			} else {
				return true;
			}
		}
	</script>
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