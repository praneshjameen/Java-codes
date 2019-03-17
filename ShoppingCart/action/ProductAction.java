package com.me.sdp.cart.ShoppingCart.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.me.sdp.cart.ShoppingCart.ShoppingCartDA;
import com.me.sdp.cart.ShoppingCart.form.ProductForm;

public class ProductAction extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ShoppingCartDA dataAccess = new ShoppingCartDA();
		ArrayList<ProductForm> productList = dataAccess.getProduct();
		request.getSession().setAttribute("productList", productList);
		return mapping.findForward("success");
	}
}
