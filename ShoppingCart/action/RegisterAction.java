package com.me.sdp.cart.ShoppingCart.action;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.me.sdp.cart.ShoppingCart.ShoppingCartDA;
import com.me.sdp.cart.ShoppingCart.form.RegisterForm;

public class RegisterAction extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RegisterForm registerForm = (RegisterForm) form;
		String username = registerForm.getUsername();
		String role = registerForm.getRole();
		if (request.getSession().getAttribute("role") != null) {
			String page = (String) request.getSession().getAttribute("role");
			if ("admin".equals(page)) {
				return mapping.findForward("adminHome");
			} else {
				return mapping.findForward("success");
			}
		} else if (username == null) {
			return mapping.findForward("register");
		} else {
			request.getSession().invalidate();
			HttpSession session = request.getSession(true);
			session.setAttribute("username", username);
			
			ShoppingCartDA dataAccess = new ShoppingCartDA();
			Integer userId=dataAccess.register(registerForm);
			session.setAttribute("userId", userId);
			if ("Admin".equals(role)) {
				session.setAttribute("role", "admin");
				return mapping.findForward("adminHome");
			} else {
				session.setAttribute("role", "normal");
				return mapping.findForward("success");
			}

		}

	}

}
