package com.me.sdp.cart.ShoppingCart;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.sql.Timestamp;
import java.sql.Types;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.adventnet.ds.query.Column;
import com.adventnet.ds.query.Criteria;
import com.adventnet.ds.query.Join;
import com.adventnet.ds.query.Operation.operationType;
import com.adventnet.ds.query.QueryConstants;
import com.adventnet.ds.query.SelectQuery;
import com.adventnet.ds.query.SelectQueryImpl;
import com.adventnet.ds.query.Table;
import com.adventnet.ds.query.UpdateQuery;
import com.adventnet.ds.query.UpdateQueryImpl;
import com.adventnet.mfw.bean.BeanUtil;
import com.adventnet.persistence.DataAccessException;
import com.adventnet.persistence.DataObject;
import com.adventnet.persistence.Persistence;
import com.adventnet.persistence.Row;
import com.adventnet.persistence.WritableDataObject;
import com.me.sdp.cart.ShoppingCart.form.ProductForm;
import com.me.sdp.cart.ShoppingCart.form.RegisterForm;
import com.me.sdp.cart.ShoppingCart.form.UserForm;

public class ShoppingCartDA {
	Persistence pers = null;

	public ShoppingCartDA() throws Exception {
		pers = (Persistence) BeanUtil.lookup("Persistence");
	}

	public ActionErrors registerValidate(String username) throws DataAccessException {
		ActionErrors errors = new ActionErrors();

		Column col = Column.getColumn(CARTUSER.TABLE, CARTUSER.USER_ID);
		SelectQuery sq = new SelectQueryImpl(new Table(CARTUSER.TABLE));
		sq.addSelectColumn(col);
		Criteria c = new Criteria(new Column(CARTUSER.TABLE, CARTUSER.USERNAME), username, QueryConstants.EQUAL);
		sq.setCriteria(c);
		DataObject dob;
		dob = pers.get(sq);
		if (dob.getRow(CARTUSER.TABLE) != null) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.name.available"));
			return errors;
		}
		return errors;

	}

	public Integer register(RegisterForm registerForm) throws DataAccessException {
		DataObject dob = new WritableDataObject();
		Row r = new Row(CARTUSER.TABLE);
		r.set(CARTUSER.USERNAME, registerForm.getUsername());
		r.set(CARTUSER.PASSWORD, registerForm.getPassword());
		r.set(CARTUSER.PHONE_NUMBER, registerForm.getPhoneNumber());
		// if (registerForm.getRole().equals("Admin")) {
		if ("Admin".equals(registerForm.getRole())) {
			r.set(CARTUSER.IS_PRIVILEGE, true);
		} else {
			r.set(CARTUSER.IS_PRIVILEGE, false);
		}
		dob.addRow(r);
		dob = pers.add(dob);
		Criteria c = new Criteria(new Column(CARTUSER.TABLE, CARTUSER.USERNAME_IDX), registerForm.getUsername(),
				QueryConstants.EQUAL);
		Integer userId = (Integer) dob.getValue(CARTUSER.TABLE, CARTUSER.USER_ID, c);
		return userId;
	}

	public Object[] loginCheckRole(String username) throws DataAccessException {
		Column col = Column.getColumn(CARTUSER.TABLE, CARTUSER.USER_ID);
		Column col1 = Column.getColumn(CARTUSER.TABLE, CARTUSER.IS_PRIVILEGE);
		SelectQuery sq = new SelectQueryImpl(new Table(CARTUSER.TABLE));
		sq.addSelectColumn(col);
		sq.addSelectColumn(col1);
		Criteria c = new Criteria(new Column(CARTUSER.TABLE, CARTUSER.USERNAME), username, QueryConstants.EQUAL);
		sq.setCriteria(c);
		DataObject dob = pers.get(sq);
		Row row = dob.getRow(CARTUSER.TABLE);
		Boolean role = (Boolean) row.get(CARTUSER.IS_PRIVILEGE);
		Integer userId = (Integer) row.get(CARTUSER.USER_ID);
		return new Object[] { role, userId };
	}

	public ActionErrors loginValidate(String username, String password) throws DataAccessException {
		ActionErrors errors = new ActionErrors();
		Column col = Column.getColumn(CARTUSER.TABLE, CARTUSER.USER_ID);
		Column col1 = Column.getColumn(CARTUSER.TABLE, CARTUSER.PASSWORD);
		SelectQuery sq = new SelectQueryImpl(new Table(CARTUSER.TABLE));
		sq.addSelectColumn(col);
		sq.addSelectColumn(col1);
		Criteria c = new Criteria(new Column(CARTUSER.TABLE, CARTUSER.USERNAME), username, QueryConstants.EQUAL);
		sq.setCriteria(c);
		DataObject dob = pers.get(sq);
		Row r = dob.getRow(CARTUSER.TABLE);
		if (r == null) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.name.not.availabe"));
			return errors;
		} else {
			if (!r.get(CARTUSER.PASSWORD).equals(password)) {
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.password.not.availabe"));
				return errors;
			}
		}
		return errors;
	}

	public ActionErrors productValidate(String productName) throws DataAccessException {
		ActionErrors errors = new ActionErrors();
		Column col = Column.getColumn(PRODUCT.TABLE, PRODUCT.PRODUCT_ID);
		SelectQuery sq = new SelectQueryImpl(new Table(PRODUCT.TABLE));
		sq.addSelectColumn(col);
		Criteria c = new Criteria(new Column(PRODUCT.TABLE, PRODUCT.PRODUCT_NAME), productName, QueryConstants.EQUAL);
		sq.setCriteria(c);
		DataObject dob;
		dob = pers.get(sq);
		if (dob.getRow(PRODUCT.TABLE) != null) {
			System.out.println("Entering if");
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.product.available"));
			return errors;
		}
		return errors;
	}

	public void addProduct(ProductForm productForm) throws DataAccessException {
		Row r = new Row(PRODUCT.TABLE);
		r.set(PRODUCT.PRODUCT_NAME, productForm.getProductName());
		r.set(PRODUCT.PRODUCT_SPEC, productForm.getProductSpec());
		r.set(PRODUCT.PRODUCT_PRICE, productForm.getProductPrice());
		r.set(PRODUCT.PRODUCT_QUANTITY, productForm.getProductQuantity());
		DataObject dob = new WritableDataObject();
		dob.addRow(r);
		pers.add(dob);
	}

	public ArrayList<ProductForm> getProduct() throws DataAccessException {
		ArrayList<ProductForm> productList = new ArrayList<>();
		DataObject dob = pers.get(PRODUCT.TABLE, (Criteria) null);
		Iterator it = dob.getRows(PRODUCT.TABLE, (Criteria) null);
		while (it.hasNext()) {
			Row r = (Row) it.next();
			Integer productId = (Integer) r.get(PRODUCT.PRODUCT_ID);
			String productName = (String) r.get(PRODUCT.PRODUCT_NAME);
			String productSpec = (String) r.get(PRODUCT.PRODUCT_SPEC);
			Integer productPrice = (Integer) r.get(PRODUCT.PRODUCT_PRICE);
			Integer productQuantity = (Integer) r.get(PRODUCT.PRODUCT_QUANTITY);
			Boolean isAvailable = (Boolean) r.get(PRODUCT.IS_AVAILABLE);
			ProductForm productForm = new ProductForm();
			productForm.setProductId(productId);
			productForm.setProductName(productName);
			productForm.setProductPrice(productPrice);
			productForm.setProductQuantity(productQuantity);
			productForm.setProductSpec(productSpec);
			productForm.setIsAvailable(isAvailable);
			productList.add(productForm);
		}

		return productList;
	}

	public void updateProduct(ProductForm productForm) throws DataAccessException {
		Integer productId = productForm.getProductId();
		Integer productPrice = productForm.getProductPrice();
		Integer productQuantity = productForm.getProductQuantity();
		UpdateQuery updateQuery = new UpdateQueryImpl(PRODUCT.TABLE);
		Criteria c = new Criteria(new Column(PRODUCT.TABLE, PRODUCT.PRODUCT_ID), productId, QueryConstants.EQUAL);
		updateQuery.setCriteria(c);
		updateQuery.setUpdateColumn(PRODUCT.PRODUCT_PRICE, productPrice);
		updateQuery.setUpdateColumn(PRODUCT.PRODUCT_QUANTITY, productQuantity);
		pers.update(updateQuery);

	}

	public void disableProduct(Integer id) throws DataAccessException {
		UpdateQuery updateQuery = new UpdateQueryImpl(PRODUCT.TABLE);
		Criteria c = new Criteria(new Column(PRODUCT.TABLE, PRODUCT.PRODUCT_ID), id, QueryConstants.EQUAL);
		updateQuery.setCriteria(c);
		updateQuery.setUpdateColumn(PRODUCT.IS_AVAILABLE, false);
		pers.update(updateQuery);
	}

	public void enableProduct(Integer id) throws DataAccessException {
		UpdateQuery updateQuery = new UpdateQueryImpl(PRODUCT.TABLE);
		Criteria c = new Criteria(new Column(PRODUCT.TABLE, PRODUCT.PRODUCT_ID), id, QueryConstants.EQUAL);
		updateQuery.setCriteria(c);
		updateQuery.setUpdateColumn(PRODUCT.IS_AVAILABLE, true);
		pers.update(updateQuery);
	}

	public void addToWishlist(Integer userId, Integer productId) throws DataAccessException {
		Row r = new Row(USERWISHLIST.TABLE);
		r.set(USERWISHLIST.PRODUCT_ID, productId);
		r.set(USERWISHLIST.USER_ID, userId);
		DataObject dob2 = new WritableDataObject();
		dob2.addRow(r);
		pers.add(dob2);
	}

	public ActionErrors checkWishlist(Integer userId, Integer productId) throws DataAccessException {

		ActionErrors errors = new ActionErrors();
		SelectQuery sq = new SelectQueryImpl(new Table(USERWISHLIST.TABLE));
		Column col = new Column(USERWISHLIST.TABLE, USERWISHLIST.USER_ID);
		Column col2 = new Column(USERWISHLIST.TABLE, USERWISHLIST.PRODUCT_ID);
		Criteria c1 = new Criteria(new Column(USERWISHLIST.TABLE, USERWISHLIST.USER_ID), userId, QueryConstants.EQUAL);
		Criteria c2 = new Criteria(new Column(USERWISHLIST.TABLE, USERWISHLIST.PRODUCT_ID), productId,
				QueryConstants.EQUAL);
		sq.addSelectColumn(col);
		sq.addSelectColumn(col2);
		sq.setCriteria(c1.and(c2));
		DataObject dob = pers.get(sq);
		if (dob.getRow(USERWISHLIST.TABLE) != null) {
			errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wishlist.already.available"));
			System.out.println(errors);
		}
		return errors;

	}

	public ArrayList<ProductForm> getWishList(Integer userId) throws DataAccessException {
		ArrayList<ProductForm> wishList = new ArrayList<>();

		SelectQuery sq = new SelectQueryImpl(new Table(PRODUCT.TABLE));
		Criteria c = new Criteria(new Column(USERWISHLIST.TABLE, USERWISHLIST.USER_ID), userId, QueryConstants.EQUAL);
		Join join = new Join(PRODUCT.TABLE, USERWISHLIST.TABLE, new String[] { PRODUCT.PRODUCT_ID },
				new String[] { USERWISHLIST.PRODUCT_ID }, Join.INNER_JOIN);

		Column col1 = new Column(PRODUCT.TABLE, PRODUCT.PRODUCT_NAME);
		Column col2 = new Column(USERWISHLIST.TABLE, USERWISHLIST.USER_ID);
		Column col3 = new Column(USERWISHLIST.TABLE, USERWISHLIST.PRODUCT_ID);
		Column col4 = new Column(PRODUCT.TABLE, PRODUCT.PRODUCT_ID);
		Column col5 = new Column(PRODUCT.TABLE, PRODUCT.PRODUCT_QUANTITY);
		List columns = Arrays.asList(col1, col2, col3, col4, col5);
		sq.addSelectColumns(columns);
		sq.setCriteria(c);
		sq.addJoin(join);
		DataObject dob2 = pers.get(sq);
		System.out.println(dob2);
		Iterator it = dob2.getRows(PRODUCT.TABLE);
		while (it.hasNext()) {
			ProductForm productForm = new ProductForm();
			System.out.println("Enterting while");
			Row r = (Row) it.next();
			System.out.println(r);
			String productName = (String) r.get(PRODUCT.PRODUCT_NAME);
			Integer productId = (Integer) r.get(PRODUCT.PRODUCT_ID);
			Integer productQuantity = (Integer) r.get(PRODUCT.PRODUCT_QUANTITY);
			System.out.println("ProductName: " + productName + " ProductId : " + productId);
			productForm.setProductName(productName);
			productForm.setProductId(productId);
			productForm.setProductQuantity(productQuantity);
			wishList.add(productForm);

		}
		return wishList;
	}

	public void removeWishList(Integer userId, Integer productId) throws DataAccessException {
		Criteria userCriteria = new Criteria(new Column(USERWISHLIST.TABLE, USERWISHLIST.USER_ID), userId,
				QueryConstants.EQUAL);
		Criteria productCriteria = new Criteria(new Column(USERWISHLIST.TABLE, USERWISHLIST.PRODUCT_ID), productId,
				QueryConstants.EQUAL);
		pers.delete(userCriteria.and(productCriteria));
	}

	public Integer checkQuantity(Integer productId, Integer userId) throws DataAccessException {
		SelectQuery sq = new SelectQueryImpl(new Table(PRODUCT.TABLE));
		Column cartCol1 = new Column(CART.TABLE, CART.USER_ID);
		Column cartCol2 = new Column(CART.TABLE, CART.PRODUCT_ID);
		Column cartCol3 = new Column(CART.TABLE, CART.QUANTITY);
		Column productCol1 = new Column(PRODUCT.TABLE, PRODUCT.PRODUCT_ID);
		Column productCol2 = new Column(PRODUCT.TABLE, PRODUCT.PRODUCT_QUANTITY);
		Criteria productCriteria = new Criteria(productCol1, productId, QueryConstants.EQUAL);
		Criteria userCriteria = new Criteria(cartCol1, userId, QueryConstants.EQUAL);
		List columns = Arrays.asList(cartCol1, cartCol2, cartCol3, productCol1, productCol2);
		Join join = new Join(PRODUCT.TABLE, CART.TABLE, new String[] { PRODUCT.PRODUCT_ID },
				new String[] { CART.PRODUCT_ID }, Join.LEFT_JOIN);
		sq.addSelectColumns(columns);
		sq.setCriteria(userCriteria.or(productCriteria));
		sq.addJoin(join);
		DataObject cartDob = pers.get(sq);
		System.out.println(cartDob);
		Row r = cartDob.getRow(PRODUCT.TABLE, productCriteria);
		Integer quantity = (Integer) r.get(PRODUCT.PRODUCT_QUANTITY);
		if (quantity == 0) {
			return 0;
		}
		// checks whether the product already exists or not
		else {
			Criteria c = new Criteria(cartCol2, productId, QueryConstants.EQUAL);
			Row check = cartDob.getRow(CART.TABLE, c.and(userCriteria));
			// Does not exist
			if (check == null) {
				return 1;
			} else {
				return 2;
			}
		}
	}

	public void addToCart(Integer userId, Integer productId, Boolean productState) throws DataAccessException {

		// Decrementing the product quantity by 1
		UpdateQuery uq = new UpdateQueryImpl(PRODUCT.TABLE);
		Column decQuantity = Column.createOperation(operationType.SUBTRACT,
				Column.getColumn(PRODUCT.TABLE, PRODUCT.PRODUCT_QUANTITY), 1);
		decQuantity.setType(Types.INTEGER);
		uq.setUpdateColumn(PRODUCT.PRODUCT_QUANTITY, decQuantity);
		Column productCol1 = new Column(PRODUCT.TABLE, PRODUCT.PRODUCT_ID);
		Criteria c = new Criteria(productCol1, productId, QueryConstants.EQUAL);
		uq.setCriteria(c);
		pers.update(uq);

		Integer quantity = 1;
		if (!productState) {
			// Adding a new entry for the product into the user cart
			Row cartRow = new Row(CART.TABLE);
			cartRow.set(CART.USER_ID, userId);
			cartRow.set(CART.PRODUCT_ID, productId);
			cartRow.set(CART.QUANTITY, quantity);
			DataObject newDob = new WritableDataObject();
			newDob.addRow(cartRow);
			pers.add(newDob);

		} else {
			// Incrementing the product quantity by 1
			UpdateQuery cartUq = new UpdateQueryImpl(CART.TABLE);
			Column cartCol1 = new Column(CART.TABLE, CART.USER_ID);
			Column cartCol2 = new Column(CART.TABLE, CART.PRODUCT_ID);
			Criteria userCriteria = new Criteria(cartCol1, userId, QueryConstants.EQUAL);
			Criteria productCriteria = new Criteria(cartCol2, productId, QueryConstants.EQUAL);
			cartUq.setCriteria(userCriteria.and(productCriteria));

			Column incQuantity = Column.createOperation(operationType.ADD, Column.getColumn(CART.TABLE, CART.QUANTITY),
					1);
			incQuantity.setType(Types.INTEGER);
			cartUq.setUpdateColumn(CART.QUANTITY, incQuantity);
			pers.update(cartUq);

		}

	}

	public ArrayList<ProductForm> getCartList(Integer userId) throws DataAccessException {
		ArrayList<ProductForm> cartLists = new ArrayList<ProductForm>();
		SelectQuery sq = new SelectQueryImpl(new Table(PRODUCT.TABLE));
		Column cartCol1 = new Column(CART.TABLE, CART.USER_ID);
		Column cartCol2 = new Column(CART.TABLE, CART.PRODUCT_ID);
		Column cartCol3 = new Column(CART.TABLE, CART.QUANTITY);
		Column productCol1 = new Column(PRODUCT.TABLE, PRODUCT.PRODUCT_ID);
		Column productCol2 = new Column(PRODUCT.TABLE, PRODUCT.PRODUCT_PRICE);
		Column productCol3 = new Column(PRODUCT.TABLE, PRODUCT.PRODUCT_NAME);
		List columns = Arrays.asList(cartCol1, cartCol2, cartCol3, productCol1, productCol2, productCol3);
		Criteria userCriteria = new Criteria(cartCol1, userId, QueryConstants.EQUAL);
		Join join = new Join(PRODUCT.TABLE, CART.TABLE, new String[] { PRODUCT.PRODUCT_ID },
				new String[] { CART.PRODUCT_ID }, Join.INNER_JOIN);
		sq.addSelectColumns(columns);
		sq.setCriteria(userCriteria);
		sq.addJoin(join);
		DataObject dob = pers.get(sq);
		Iterator it = dob.getRows(CART.TABLE);
		while (it.hasNext()) {
			Row r = (Row) it.next();
			ProductForm productForm = new ProductForm();
			Integer productId = (Integer) r.get(CART.PRODUCT_ID);
			productForm.setProductId(productId);
			productForm.setProductQuantity((Integer) r.get(CART.QUANTITY));
			Criteria proCriteria = new Criteria(productCol1, productId, QueryConstants.EQUAL);
			Row productRow = dob.getRow(PRODUCT.TABLE, proCriteria);
			productForm.setProductPrice((Integer) productRow.get(PRODUCT.PRODUCT_PRICE));
			productForm.setProductName((String) productRow.get(PRODUCT.PRODUCT_NAME));
			cartLists.add(productForm);
		}
		System.out.println(cartLists);
		return cartLists;
	}

	public void removeFromCart(Integer userId, Integer productId) throws DataAccessException {
		// Incrementing in the Product Table
		UpdateQuery uq = new UpdateQueryImpl(PRODUCT.TABLE);
		Column incQuantity = Column.createOperation(operationType.ADD,
				Column.getColumn(PRODUCT.TABLE, PRODUCT.PRODUCT_QUANTITY), 1);
		incQuantity.setType(Types.INTEGER);
		uq.setUpdateColumn(PRODUCT.PRODUCT_QUANTITY, incQuantity);
		Column productCol1 = new Column(PRODUCT.TABLE, PRODUCT.PRODUCT_ID);
		Criteria c = new Criteria(productCol1, productId, QueryConstants.EQUAL);
		uq.setCriteria(c);
		pers.update(uq);
		// Decrementing in the cart Table
		UpdateQuery cartUq = new UpdateQueryImpl(CART.TABLE);
		Column cartCol1 = new Column(CART.TABLE, CART.USER_ID);
		Column cartCol2 = new Column(CART.TABLE, CART.PRODUCT_ID);
		Criteria userCriteria = new Criteria(cartCol1, userId, QueryConstants.EQUAL);
		Criteria productCriteria = new Criteria(cartCol2, productId, QueryConstants.EQUAL);
		cartUq.setCriteria(userCriteria.and(productCriteria));
		Column decQuantity = Column.createOperation(operationType.SUBTRACT, Column.getColumn(CART.TABLE, CART.QUANTITY),
				1);
		decQuantity.setType(Types.INTEGER);
		cartUq.setUpdateColumn(CART.QUANTITY, decQuantity);
		pers.update(cartUq);
	}

	public void removeCart(Integer userId, Integer productId, Integer quantity) throws DataAccessException {
		// Incrementing in the Product Table
		UpdateQuery uq = new UpdateQueryImpl(PRODUCT.TABLE);
		Column incQuantity = Column.createOperation(operationType.ADD,
				Column.getColumn(PRODUCT.TABLE, PRODUCT.PRODUCT_QUANTITY), quantity);
		incQuantity.setType(Types.INTEGER);
		uq.setUpdateColumn(PRODUCT.PRODUCT_QUANTITY, incQuantity);
		Column productCol1 = new Column(PRODUCT.TABLE, PRODUCT.PRODUCT_ID);
		Criteria c = new Criteria(productCol1, productId, QueryConstants.EQUAL);
		uq.setCriteria(c);
		pers.update(uq);

		// Remove the cart row
		Column cartCol1 = new Column(CART.TABLE, CART.USER_ID);
		Column cartCol2 = new Column(CART.TABLE, CART.PRODUCT_ID);
		Criteria userCriteria = new Criteria(cartCol1, userId, QueryConstants.EQUAL);
		Criteria productCriteria = new Criteria(cartCol2, productId, QueryConstants.EQUAL);
		pers.delete(userCriteria.and(productCriteria));

	}

	public void addAddress(Integer userId, String address) throws DataAccessException {
		DataObject dob = new WritableDataObject();
		Row addressRow = new Row(USERADDRESS.TABLE);
		addressRow.set(USERADDRESS.ADDRESS, address);
		addressRow.set(USERADDRESS.USER_ID, userId);
		dob.addRow(addressRow);
		pers.add(dob);
	}

	public ArrayList<UserForm> getAddressList(Integer userId) throws DataAccessException {
		ArrayList<UserForm> addressList = new ArrayList<UserForm>();
		SelectQuery sq = new SelectQueryImpl(new Table(USERADDRESS.TABLE));
		Column col1 = new Column(USERADDRESS.TABLE, USERADDRESS.ADDRESS_ID);
		Column col2 = new Column(USERADDRESS.TABLE, USERADDRESS.USER_ID);
		Column col3 = new Column(USERADDRESS.TABLE, USERADDRESS.ADDRESS);
		sq.addSelectColumn(col1);
		sq.addSelectColumn(col2);
		sq.addSelectColumn(col3);
		Criteria criteria = new Criteria(col2, userId, QueryConstants.EQUAL);
		sq.setCriteria(criteria);
		DataObject dob = pers.get(sq);
		Iterator it = dob.getRows(USERADDRESS.TABLE);
		while (it.hasNext()) {
			Row addressRow = (Row) it.next();
			Integer addressId = (Integer) addressRow.get(USERADDRESS.ADDRESS_ID);
			String address = (String) addressRow.get(USERADDRESS.ADDRESS);
			UserForm userForm = new UserForm();
			userForm.setAddress(address);
			userForm.setAddressId(addressId);
			addressList.add(userForm);
		}
		return addressList;
	}

	public void makePurchase(Integer userId, Integer addressId) throws DataAccessException {
		Column col1 = new Column(CART.TABLE, CART.USER_ID);
		Column col2 = new Column(CART.TABLE, CART.PRODUCT_ID);
		Column col3 = new Column(CART.TABLE, CART.QUANTITY);
		SelectQuery sq = new SelectQueryImpl(new Table(CART.TABLE));
		Criteria criteria1 = new Criteria(col1, userId, QueryConstants.EQUAL);
		sq.setCriteria(criteria1);
		sq.addSelectColumn(col1);
		sq.addSelectColumn(col2);
		sq.addSelectColumn(col3);
		DataObject dob1 = pers.get(sq);
		Iterator iterator = dob1.getRows(CART.TABLE);
		DataObject dob2 = new WritableDataObject();
		while (iterator.hasNext()) {
			Row cartRow = (Row) iterator.next();
			Integer productId = (Integer) cartRow.get(CART.PRODUCT_ID);
			Integer quantity = (Integer) cartRow.get(CART.QUANTITY);
			for (int i = 0; i < quantity; i++) {
				Row orderRow = new Row(PRODUCTORDER.TABLE);
				orderRow.set(PRODUCTORDER.USER_ID, userId);
				orderRow.set(PRODUCTORDER.PRODUCT_ID, productId);
				orderRow.set(PRODUCTORDER.ADDRESS_ID, addressId);
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				orderRow.set(PRODUCTORDER.TIME_STAMP, timestamp);
				dob2.addRow(orderRow);
			}
		}
		pers.add(dob2);
		Criteria criteria2 = new Criteria(col1, userId, QueryConstants.EQUAL);
		pers.delete(criteria2);
	}

	public ArrayList<UserForm> getOrderList(Integer userId) throws DataAccessException {
		ArrayList<UserForm> orderLists = new ArrayList<UserForm>();
		// Columns
		// ProductOrder Columns
		Column col1 = new Column(PRODUCTORDER.TABLE, PRODUCTORDER.ORDER_ID);
		Column col2 = new Column(PRODUCTORDER.TABLE, PRODUCTORDER.USER_ID);
		Column col3 = new Column(PRODUCTORDER.TABLE, PRODUCTORDER.PRODUCT_ID);
		Column col4 = new Column(PRODUCTORDER.TABLE, PRODUCTORDER.ADDRESS_ID);
		Column col5 = new Column(PRODUCTORDER.TABLE, PRODUCTORDER.TIME_STAMP);
		Column col6 = new Column(PRODUCTORDER.TABLE, PRODUCTORDER.IS_CANCELLED);
		// Product columns
		Column col7 = new Column(PRODUCT.TABLE, PRODUCT.PRODUCT_ID);
		Column col8 = new Column(PRODUCT.TABLE, PRODUCT.PRODUCT_NAME);
		// Address Columns
		Column col9 = new Column(USERADDRESS.TABLE, USERADDRESS.ADDRESS_ID);
		Column col10 = new Column(USERADDRESS.TABLE, USERADDRESS.ADDRESS);
		List columns = Arrays.asList(col1, col2, col3, col4, col5, col6, col7, col8, col9, col10);

		// Crtierias
		Criteria c1 = new Criteria(col2, userId, QueryConstants.EQUAL);

		// Joins
		Join join1 = new Join(PRODUCTORDER.TABLE, PRODUCT.TABLE, new String[] { PRODUCTORDER.PRODUCT_ID },
				new String[] { PRODUCT.PRODUCT_ID }, Join.INNER_JOIN);
		Join join2 = new Join(PRODUCTORDER.TABLE, USERADDRESS.TABLE, new String[] { PRODUCTORDER.ADDRESS_ID },
				new String[] { USERADDRESS.ADDRESS_ID }, Join.INNER_JOIN);

		SelectQuery sq = new SelectQueryImpl(new Table(PRODUCTORDER.TABLE));
		sq.addSelectColumns(columns);
		sq.setCriteria(c1);
		sq.addJoin(join1);
		sq.addJoin(join2);
		DataObject dob=pers.get(sq);
		Iterator orderIterator=dob.getRows(PRODUCTORDER.TABLE);
		while(orderIterator.hasNext()){
			UserForm order=new UserForm();
			Row rowOrder=(Row)orderIterator.next();
			Integer orderId=(Integer) rowOrder.get(PRODUCTORDER.ORDER_ID);
			Integer productId=(Integer) rowOrder.get(PRODUCTORDER.PRODUCT_ID);
			Integer addressId=(Integer) rowOrder.get(PRODUCTORDER.ADDRESS_ID);
			Boolean isCancelled=(Boolean) rowOrder.get(PRODUCTORDER.IS_CANCELLED);
			Timestamp timeStamp=(Timestamp) rowOrder.get(PRODUCTORDER.TIME_STAMP);
			
			//Criterias
			Criteria c2=new Criteria(col7,productId,QueryConstants.EQUAL);
			Criteria c3=new Criteria(col9,addressId,QueryConstants.EQUAL);
			
			String productName=(String) dob.getValue(PRODUCT.TABLE, PRODUCT.PRODUCT_NAME, c2);
			String address=(String) dob.getValue(USERADDRESS.TABLE, USERADDRESS.ADDRESS, c3);
			
			order.setOrderId(orderId);
			order.setProductName(productName);
			order.setIsCancelled(isCancelled);
			order.setTimeStamp(timeStamp);
			order.setProductName(productName);
			order.setAddress(address);
			orderLists.add(order);
		
		}
		return orderLists;
	}
	public void cancelItem(Integer orderId) throws DataAccessException {
		UpdateQuery uq=new UpdateQueryImpl(PRODUCTORDER.TABLE);
		Column col=new Column(PRODUCTORDER.TABLE,PRODUCTORDER.ORDER_ID);
		Criteria c=new Criteria(col,orderId,QueryConstants.EQUAL);
		uq.setCriteria(c);
		uq.setUpdateColumn(PRODUCTORDER.IS_CANCELLED, true);
		pers.update(uq);
	}

}
