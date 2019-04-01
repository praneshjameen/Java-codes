package com.me.sdp.cart.ShoppingCart.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.adventnet.persistence.DataAccessException;
import com.me.sdp.cart.ShoppingCart.ShoppingCartDA;

public class ProductForm extends ActionForm {
	private Integer productId;
	private String productName;
	private String productSpec;
	private Integer productPrice;
	private Integer productQuantity;
	private Boolean isAvailable;
	private Boolean wListAvailable;
	private Boolean cartAvailable;
	private Integer cartQuantity;
	private Boolean isCancelled;
	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductSpec() {
		return productSpec;
	}

	public void setProductSpec(String productSpec) {
		this.productSpec = productSpec;
	}

	public Integer getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(Integer productPrice) {
		this.productPrice = productPrice;
	}

	public Integer getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(Integer productQuantity) {
		this.productQuantity = productQuantity;
	}

	public Boolean getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(Boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	

	public Boolean getwListAvailable() {
		return wListAvailable;
	}

	public void setwListAvailable(Boolean wListAvailable) {
		this.wListAvailable = wListAvailable;
	}

	public Boolean getCartAvailable() {
		return cartAvailable;
	}

	public void setCartAvailable(Boolean cartAvailable) {
		this.cartAvailable = cartAvailable;
	}

	public Integer getCartQuantity() {
		return cartQuantity;
	}

	public void setCartQuantity(Integer cartQuantity) {
		this.cartQuantity = cartQuantity;
	}

	public Boolean getIsCancelled() {
		return isCancelled;
	}

	public void setIsCancelled(Boolean isCancelled) {
		this.isCancelled = isCancelled;
	}

	@Override
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors error = new ActionErrors();
		ShoppingCartDA dataAccess = null;
		if (getProductName() != null) {
			try {
				dataAccess = new ShoppingCartDA();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				error = dataAccess.productValidate(getProductName());
				return error;
			} catch (DataAccessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return error;
	}
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		this.productName=null;
		this.productSpec=null;
		this.productPrice=null;
		this.productQuantity=null;
		
		}

}
