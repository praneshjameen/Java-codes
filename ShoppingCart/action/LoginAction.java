package com.me.sdp.cart.ShoppingCart.action;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.*;

import com.me.sdp.cart.ShoppingCart.ShoppingCartDA;
import com.me.sdp.cart.ShoppingCart.form.LoginForm;


public class LoginAction extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		LoginForm loginForm = (LoginForm) form;
		String username = loginForm.getUsername();
		if (request.getSession().getAttribute("role") != null) {
			String page = (String) request.getSession().getAttribute("role");
			if (page.equals("admin"))
				return mapping.findForward("adminHome");
			else
				return mapping.findForward("success");
		} else if (username == null) {
			return mapping.findForward("login");
		} else {
			request.getSession().invalidate();
			HttpSession session = request.getSession(true);
			session.setAttribute("username", username);
			ShoppingCartDA dataAccess=new ShoppingCartDA();
			Object[] objects=dataAccess.loginCheckRole(username);
			session.setAttribute("userId", (Integer)objects[1]);
			if ((Boolean)objects[0]) {
				session.setAttribute("role", "admin");
				return mapping.findForward("adminHome");
			} else {
				session.setAttribute("role", "normal");
				return mapping.findForward("success");
			}
		}

	}
}
