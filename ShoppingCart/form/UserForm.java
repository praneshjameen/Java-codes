package com.me.sdp.cart.ShoppingCart.form;

import java.sql.Timestamp;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.me.sdp.cart.ShoppingCart.ShoppingCartDA;

public class UserForm extends ActionForm {
	private String address;
	private Integer productId;
	private Integer addressId;
	private Integer orderId;
	private Timestamp timeStamp;
	private ArrayList<ProductForm> products;
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getAddressId() {
		return addressId;
	}

	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Timestamp getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Timestamp timeStamp) {
		this.timeStamp = timeStamp;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public ArrayList<ProductForm> getProducts() {
		return products;
	}

	public void setProducts(ArrayList<ProductForm> products) {
		this.products = products;
	}

	@Override
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors error = new ActionErrors();
		if ("addToWishlist".equals(request.getParameter("method"))) {
			// check user's wish list
			Integer userId = (Integer) request.getSession().getAttribute("userId");
			if (request.getParameter("id") != null && userId != null) {
				Integer productId = Integer.parseInt(request.getParameter("id"));
				ShoppingCartDA dataAccess = null;
				try {
					dataAccess = new ShoppingCartDA();
					error = dataAccess.checkWishlist(userId, productId);
					return error;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

		if ("addToCart".equals(request.getParameter("method")) || "buyNow".equals(request.getParameter("method"))) {
			// check product's quantity
			Integer userId = (Integer) request.getSession().getAttribute("userId");
			if (request.getParameter("id") != null && userId != null) {
				Integer productId = Integer.parseInt(request.getParameter("id"));
				ShoppingCartDA dataAccess = null;
				try {
					dataAccess = new ShoppingCartDA();
					Integer flag = dataAccess.checkQuantity(productId, userId);
					if (flag == 0) {
						error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.product.out.of.stock"));
						ArrayList<ProductForm> productList = dataAccess.getProduct(userId);
						request.setAttribute("productList", productList);
					} else if (flag == 1) {
						request.setAttribute("productState", false);
						System.out.println("Product does not exist in the cart");
					} else {
						request.setAttribute("productState", true);
						System.out.println("Product already exists in the user cart");
					}
					return error;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

		return error;
	}

}
