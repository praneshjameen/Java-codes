package com.me.sdp.cart.ShoppingCart.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.me.sdp.cart.ShoppingCart.ShoppingCartDA;

public class RegisterForm extends ActionForm {

	private String username;
	private String password;
	private Long phoneNumber;
	private String role;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {

		ActionErrors errors = new ActionErrors();
		ShoppingCartDA dataAccess = null;
		try {
			dataAccess = new ShoppingCartDA();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			errors = dataAccess.registerValidate(getUsername());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (getRole() != null) {
			if ("Admin".equals(getRole())) {
				String key = request.getParameter("adminKey");
				System.out.println(key + " - key");
				if (!"9500159071".equals(key)) {
					errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.key"));
					System.out.println(errors);
					return errors;
				}

			}
		}

		return errors;
	}

}
