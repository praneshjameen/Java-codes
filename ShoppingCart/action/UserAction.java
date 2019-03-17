package com.me.sdp.cart.ShoppingCart.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.me.sdp.cart.ShoppingCart.ShoppingCartDA;
import com.me.sdp.cart.ShoppingCart.form.ProductForm;
import com.me.sdp.cart.ShoppingCart.form.UserForm;

public class UserAction extends DispatchAction {

	public ActionForward addToWishlist(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (validate(request)) {
			String id = request.getParameter("id");
			if (id != null) {
				Integer productId = Integer.parseInt(id);
				Integer userId = (Integer) request.getSession().getAttribute("userId");
				System.out.println("Product Id : " + productId);
				ShoppingCartDA dataAccess = new ShoppingCartDA();
				dataAccess.addToWishlist(userId, productId);
				return mapping.findForward("viewWishlist");
			} else {
				return mapping.findForward("success");
			}

		} else {
			return mapping.findForward("success");
		}

	}

	public ActionForward viewWishlist(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (validate(request)) {
			Integer userId = (Integer) request.getSession().getAttribute("userId");
			if (userId != null) {
				ShoppingCartDA dataAccess = new ShoppingCartDA();
				ArrayList<ProductForm> wishList = dataAccess.getWishList(userId);
				request.getSession().setAttribute("wishLists", wishList);
				// return null;
				return mapping.findForward("wishlist");
			} else {
				return mapping.findForward("success");
			}

		} else {
			return mapping.findForward("success");
		}

	}

	public ActionForward removeWishlist(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (validate(request)) {
			Integer userId = (Integer) request.getSession().getAttribute("userId");
			String id = request.getParameter("id");
			if (id != null && userId != null) {
				Integer productId = Integer.parseInt(id);
				ShoppingCartDA dataAccess = new ShoppingCartDA();
				dataAccess.removeWishList(userId, productId);
				return mapping.findForward("viewWishlist");
			} else
				return mapping.findForward("success");
		} else {
			return mapping.findForward("success");
		}

	}

	public ActionForward addToCart(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (validate(request)) {
			String id = request.getParameter("id");
			Integer userId = (Integer) request.getSession().getAttribute("userId");
			Boolean productState = (Boolean) request.getSession().getAttribute("productState");
			if (id != null && userId != null && productState != null) {
				Integer productId = Integer.parseInt(id);
				ShoppingCartDA dataAccess = new ShoppingCartDA();
				dataAccess.addToCart(userId, productId, productState);
				return mapping.findForward("viewCart");
			} else {
				return mapping.findForward("success");
			}
		} else {
			return mapping.findForward("success");
		}

	}

	public ActionForward removeFromCart(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (validate(request)) {
			String id = request.getParameter("id");
			Integer userId = (Integer) request.getSession().getAttribute("userId");
			if (id != null && userId != null) {
				Integer productId = Integer.parseInt(id);
				ShoppingCartDA dataAccess = new ShoppingCartDA();
				if (request.getParameter("deleteCart") == null) {
					Integer quantity = Integer.parseInt(request.getParameter("quantity"));
					if (quantity == 1) {
						dataAccess.removeCart(userId, productId, quantity);
					} else {
						dataAccess.removeFromCart(userId, productId);
					}
				} else {
					Integer quantity = Integer.parseInt(request.getParameter("deleteCart"));
					dataAccess.removeCart(userId, productId, quantity);
				}
				return mapping.findForward("viewCart");
			} else {
				return mapping.findForward("success");
			}
		} else {
			return mapping.findForward("success");
		}

	}

	public ActionForward viewCart(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (validate(request)) {
			Integer userId = (Integer) request.getSession().getAttribute("userId");
			if (userId != null) {
				ShoppingCartDA dataAccess = new ShoppingCartDA();
				ArrayList<ProductForm> cartLists = dataAccess.getCartList(userId);
				request.getSession().setAttribute("cartLists", cartLists);
				return mapping.findForward("cart");
			} else {
				return mapping.findForward("success");
			}
		} else {
			return mapping.findForward("success");
		}

	}

	public ActionForward buyNow(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (validate(request)) {
			String id = request.getParameter("id");
			Integer userId = (Integer) request.getSession().getAttribute("userId");
			if (id != null && userId != null) {
				Integer productId = Integer.parseInt(id);
				ShoppingCartDA dataAccess = new ShoppingCartDA();
				Integer flag = dataAccess.checkQuantity(productId, userId);
				if (flag == 0) {
					return mapping.findForward("success");
				} else if (flag == 1) {
					dataAccess.addToCart(userId, productId, false);
				} else {
					dataAccess.addToCart(userId, productId, true);
				}
				return mapping.findForward("viewPurchase");
			} else
				return mapping.findForward("success");
		} else {
			return mapping.findForward("success");
		}

	}

	public ActionForward viewPurchase(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (validate(request)) {
			Integer userId = (Integer) request.getSession().getAttribute("userId");
			if (userId != null) {
				ShoppingCartDA dataAccess = new ShoppingCartDA();
				ArrayList<ProductForm> purchaseLists = dataAccess.getCartList(userId);
				request.getSession().setAttribute("purchaseLists", purchaseLists);
				ArrayList<UserForm> addressList = dataAccess.getAddressList(userId);
				request.getSession().setAttribute("addressList", addressList);
				return mapping.findForward("purchase");
			} else {
				return mapping.findForward("success");
			}
		} else {
			return mapping.findForward("success");
		}
	}

	public ActionForward addAddress(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (validate(request)) {
			UserForm userForm = (UserForm) form;
			String address = userForm.getAddress();
			Integer userId = (Integer) request.getSession().getAttribute("userId");
			if (userId != null && address != null) {
				ShoppingCartDA dataAccess = new ShoppingCartDA();
				dataAccess.addAddress(userId, address);
				return mapping.findForward("viewPurchase");
			} else {
				return mapping.findForward("success");
			}
		} else {

			return mapping.findForward("success");
		}
	}

	public ActionForward makePurchase(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (validate(request)) {
			UserForm userForm = (UserForm) form;
			Integer addressId = userForm.getAddressId();
			Integer userId = (Integer) request.getSession().getAttribute("userId");
			if (addressId != null && userId != null) {
				// push data to the productOrder Table
				// Remove details from the cart table
				ShoppingCartDA dataAccess = new ShoppingCartDA();
				dataAccess.makePurchase(userId, addressId);
				return mapping.findForward("viewOrders");
			} else {
				return mapping.findForward("success");
			}

		} else {
			return mapping.findForward("success");
		}
	}

	public ActionForward viewOrder(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (validate(request)) {
			Integer userId = (Integer) request.getSession().getAttribute("userId");
			if (userId != null) {
				ShoppingCartDA dataAccess=new ShoppingCartDA();
				ArrayList<UserForm> orderLists=dataAccess.getOrderList(userId);
				request.getSession().setAttribute("orderLists", orderLists);
				return mapping.findForward("orders");
			} else {
				return mapping.findForward("success");
			}
		} else {
			return mapping.findForward("success");
		}

	}

	public ActionForward cancelItem(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (validate(request)) {
			String id = request.getParameter("id");
			if (id != null) {
				Integer orderId = Integer.parseInt(id);
				ShoppingCartDA dataAccess=new ShoppingCartDA();
				dataAccess.cancelItem(orderId);
				return mapping.findForward("viewOrders");
			} else
				return mapping.findForward("success");
		} else {
			return mapping.findForward("success");
		}

	}

	public boolean validate(HttpServletRequest request) {
		if (request.getSession().getAttribute("role") == null)
			return false;
		else if (request.getSession().getAttribute("role").equals("admin"))
			return false;
		return true;
	}
}
