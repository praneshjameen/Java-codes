
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
<body onload="activeTab('ordersNav')">
	<%@ include file="/WEB-INF/jspf/nav.jspf"%>
	<logic:empty name="orderLists">
		<div class="card" style="width: 50%; margin: auto;">
			<div class="card-body" style="text-align: center;">
				<h1>Your have not purchased anything yet!</h1>
			</div>
		</div>
	</logic:empty>

	<logic:iterate name="orderLists" id="orderItem">
		<bean:define id="orderId" name="orderItem" property="orderId" />

		<div class="card" style="width: 60%; margin: 2%;">
			<h4 class="card-header">
				Order ID :
				<bean:write name="orderId" />
			</h4>
			<div class="card-body" style="font-weight: bold;">

				<p>
					Order Address : <span style="font-weight: normal;" x><bean:write
							name="orderItem" property="address" /></span>
				</p>

				<p>
					Order Time : <span style="font-weight: normal;"><bean:write
							name="orderItem" property="timeStamp" /></span>
				</p>
				<logic:iterate id="product" name="orderItem" property="products">
					<bean:define id="isCancelled" name="product" property="isCancelled" />
					<bean:define id="quantity" name="product"
						property="productQuantity" />
					<bean:define id="productId" name="product" property="productId" />
					<div class="card" style="width: 80%; margin: 2%;">
						<h6 class="card-header">
							<bean:write name="product" property="productName" />
							<div style="float: right;">
								<logic:notEqual name="quantity" value="0">
									<a id="cancel${orderId}${productId}"
										href="user.do?method=cancelItem&orderId=${orderId}&productId=${productId}&quantity=1"
										class="btn btn-danger">Cancel(1)</a>
								</logic:notEqual>
								<logic:equal name="quantity" value="0">
									<a class="btn btn-secondary">Cancelled</a>
								</logic:equal>
							</div>
						</h6>
						<div class="card-body">
							<logic:notEqual name="quantity" value="0">
							Quantity : <span style="font-weight: normal;" x>${quantity}</span>
							<logic:equal name="isCancelled" value="true">
							(Partially Cancelled)
							</logic:equal>
								<div class="form-inline form-group mb-2" style="float: right;">
									<select onchange="quantityValue(${orderId},${productId})"
										id="quantity${orderId}${productId}"
										class=" form-control form-control-sm">
										<%
											for (int i = 1; i <= (Integer) quantity; i++) {
										%>
										<option><%=i%></option>
										<%
											}
										%>
									</select>
								</div>
							</logic:notEqual>
							<logic:equal name="quantity" value="0">
							You've cancelled the entire order
							</logic:equal>
						</div>
					</div>
				</logic:iterate>
			</div>
		</div>
	</logic:iterate>
	<script>
	function quantityValue(id1,id2){
		var id=id1+''+id2;
		var i=$('#quantity'+id).val();
		console.log(i);
		$('#cancel'+id).text('Cancel('+i+')');
		var href='user.do?method=cancelItem&orderId='+id1+'&productId='+id2+'&quantity='+i;
		console.log(href);
		$('#cancel'+id).attr('href',href);
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