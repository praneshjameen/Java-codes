package com.me.sdp.cart.ShoppingCart.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.adventnet.persistence.DataAccessException;
import com.me.sdp.cart.ShoppingCart.ShoppingCartDA;

public class LoginForm extends ActionForm {
	private String username;
	private String password;

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

	@Override
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors errors = null;
		if(getUsername()!=null) {
			ShoppingCartDA dataAccess = null;
			try {
				dataAccess = new ShoppingCartDA();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				System.out.println(getUsername());
				System.out.println(getPassword());
				errors = dataAccess.loginValidate(getUsername(), getPassword());
			} catch (DataAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
				return errors;
	}

}
