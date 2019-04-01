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
import com.adventnet.ds.query.SortColumn;
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
		// E.g. select user_id from cartuser where username='prans'
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
		// e.g. insert into
		// cartuser(user_id,username,password,phone_number,is_privilege)
		// values(12,'vivek','vivek',1234,false)
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
		// e.g. select user_id,is_privilege from cartuser where username='prans';
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
		// e.g. select user_id,password from cartuser where username='prans';
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
			if (!password.equals(r.get(CARTUSER.PASSWORD))) {
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.password.not.availabe"));
				return errors;
			}
		}
		return errors;
	}

	public ActionErrors productValidate(String productName) throws DataAccessException {
		// e.g. select product_id from product where product_name='cap'
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
		// E.g. insert into
		// product(product_id,product_name,product_spec,product_price,product_quantity)
		// values(7,'bag','reebok',50,20)
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
		// e.g. select * from product order by product_name asc;
		ArrayList<ProductForm> productList = new ArrayList<>();
		SelectQuery sq = new SelectQueryImpl(new Table(PRODUCT.TABLE));
		Criteria c = (Criteria) null;
		Column allColumn = new Column(PRODUCT.TABLE, "*");
		Column column = new Column(PRODUCT.TABLE, PRODUCT.PRODUCT_NAME);
		SortColumn sortColumn = new SortColumn(column, true);
		sq.addSelectColumn(allColumn);
		sq.setCriteria(c);
		sq.addSortColumn(sortColumn);
		DataObject dob = pers.get(sq);
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

	public ArrayList<ProductForm> getProduct(Integer userId) throws DataAccessException {
		ArrayList<ProductForm> productForms = new ArrayList<ProductForm>();
		SelectQuery selectQuery = new SelectQueryImpl(new Table(PRODUCT.TABLE));
		// Columns
		Column productColumn = new Column(PRODUCT.TABLE, "*");
		Column cartColumn = new Column(CART.TABLE, "*");
		Column wishListColumn = new Column(USERWISHLIST.TABLE, "*");
		selectQuery.addSelectColumn(productColumn);
		selectQuery.addSelectColumn(cartColumn);
		selectQuery.addSelectColumn(wishListColumn);

		// Sort
		SortColumn sortColumn = new SortColumn(new Column(PRODUCT.TABLE, PRODUCT.PRODUCT_NAME), true);
		selectQuery.addSortColumn(sortColumn);

		// Criteria
		Criteria criteria1 = new Criteria(new Column(USERWISHLIST.TABLE, USERWISHLIST.USER_ID), userId,
				QueryConstants.EQUAL);
		Criteria criteria2 = new Criteria(new Column(PRODUCT.TABLE, PRODUCT.PRODUCT_ID),
				new Column(USERWISHLIST.TABLE, USERWISHLIST.PRODUCT_ID), QueryConstants.EQUAL);
		Criteria criteria3 = new Criteria(new Column(CART.TABLE, CART.USER_ID), userId, QueryConstants.EQUAL);
		Criteria criteria4 = new Criteria(new Column(PRODUCT.TABLE, PRODUCT.PRODUCT_ID),
				new Column(CART.TABLE, CART.PRODUCT_ID), QueryConstants.EQUAL);

		// joins
		Join join1 = new Join(PRODUCT.TABLE, USERWISHLIST.TABLE, criteria1.and(criteria2), Join.LEFT_JOIN);
		Join join2 = new Join(PRODUCT.TABLE, CART.TABLE, criteria3.and(criteria4), Join.LEFT_JOIN);
		selectQuery.addJoin(join1);
		selectQuery.addJoin(join2);

//		selectQuery.addJoin(join2);

		DataObject dob = pers.get(selectQuery);
		System.out.println(dob);
		Iterator it = dob.getRows(PRODUCT.TABLE);
		while (it.hasNext()) {
			Row r = (Row) it.next();
			// Product Details
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

			// User Wishlist Details
			Criteria criteria5 = new Criteria(new Column(USERWISHLIST.TABLE, USERWISHLIST.PRODUCT_ID), productId,
					QueryConstants.EQUAL);
			Integer wList = (Integer) dob.getValue(USERWISHLIST.TABLE, USERWISHLIST.PRODUCT_ID, criteria5);
			if (wList != null) {
				productForm.setwListAvailable(true);
			} else {
				productForm.setwListAvailable(false);
			}
			// Cart Details
			Criteria criteria6 = new Criteria(new Column(CART.TABLE, CART.PRODUCT_ID), productId, QueryConstants.EQUAL);
			Integer cart = (Integer) dob.getValue(CART.TABLE, CART.PRODUCT_ID, criteria6);
			if (cart != null) {
				Integer cartQuantity = (Integer) dob.getValue(CART.TABLE, CART.QUANTITY, criteria6);
				System.out.println("cart quantity " + cartQuantity);
				productForm.setCartAvailable(true);
				productForm.setCartQuantity(cartQuantity);
			} else {
				productForm.setCartAvailable(false);
				productForm.setCartQuantity(0);
			}
			System.out.println(wList + " " + cart);
			productForms.add(productForm);
		}
		return productForms;
	}

	public void updateProduct(ProductForm productForm) throws DataAccessException {
		// e.g. update product set product_quantity=12,product_price=49 where
		// product_id=7
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
		// e.g. update product set is_available=false where product_id=5
		UpdateQuery updateQuery = new UpdateQueryImpl(PRODUCT.TABLE);
		Criteria c = new Criteria(new Column(PRODUCT.TABLE, PRODUCT.PRODUCT_ID), id, QueryConstants.EQUAL);
		updateQuery.setCriteria(c);
		updateQuery.setUpdateColumn(PRODUCT.IS_AVAILABLE, false);
		pers.update(updateQuery);
	}

	public void enableProduct(Integer id) throws DataAccessException {
		// e.g. update product set is_available=true where product_id=5
		UpdateQuery updateQuery = new UpdateQueryImpl(PRODUCT.TABLE);
		Criteria c = new Criteria(new Column(PRODUCT.TABLE, PRODUCT.PRODUCT_ID), id, QueryConstants.EQUAL);
		updateQuery.setCriteria(c);
		updateQuery.setUpdateColumn(PRODUCT.IS_AVAILABLE, true);
		pers.update(updateQuery);
	}

	public void addToWishlist(Integer userId, Integer productId) throws DataAccessException {
		// e.g. insert into userwishlist(user_id,product_id) values(4,1)
		Row r = new Row(USERWISHLIST.TABLE);
		r.set(USERWISHLIST.PRODUCT_ID, productId);
		r.set(USERWISHLIST.USER_ID, userId);
		DataObject dob2 = new WritableDataObject();
		dob2.addRow(r);
		pers.add(dob2);
	}

	public ActionErrors checkWishlist(Integer userId, Integer productId) throws DataAccessException {
		// e.g. select user_id,product_id from userwishlist
		// where user_id=1 and product_id=4
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
		// e.g. select
		// product.product_name,product.product_id,product.product_quantity,product.is_available,
		// userwishlist.user_id,userwishlist.product_id
		// from product inner join userwishlist on product.product_id =
		// userwishlist.product_id
		// where user_id=4
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
		Column col6 = new Column(PRODUCT.TABLE, PRODUCT.IS_AVAILABLE);
		SortColumn sortColumn = new SortColumn(col1, true);
		List columns = Arrays.asList(col1, col2, col3, col4, col5, col6);
		sq.addSelectColumns(columns);
		sq.addSortColumn(sortColumn);
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
			Boolean isAvailable = (Boolean) r.get(PRODUCT.IS_AVAILABLE);
			System.out.println("ProductName: " + productName + " ProductId : " + productId);
			productForm.setProductName(productName);
			productForm.setProductId(productId);
			productForm.setProductQuantity(productQuantity);
			productForm.setIsAvailable(isAvailable);
			wishList.add(productForm);

		}
		return wishList;
	}

	public void removeWishList(Integer userId, Integer productId) throws DataAccessException {
		// e.g. delete from userwishlist where user_id=4 and product_id=1
		Criteria userCriteria = new Criteria(new Column(USERWISHLIST.TABLE, USERWISHLIST.USER_ID), userId,
				QueryConstants.EQUAL);
		Criteria productCriteria = new Criteria(new Column(USERWISHLIST.TABLE, USERWISHLIST.PRODUCT_ID), productId,
				QueryConstants.EQUAL);
		pers.delete(userCriteria.and(productCriteria));
	}

	public Integer checkQuantity(Integer productId, Integer userId) throws DataAccessException {
		// select
		// cart.user_id,cart.product_id,cart.quantity,product.product_id,product.product_quantity
		// from product left join cart on product.product_id = cart.product_id
		// and cart.user_id=4 where product.product_id=1
		SelectQuery sq = new SelectQueryImpl(new Table(PRODUCT.TABLE));
		Column cartCol1 = new Column(CART.TABLE, CART.USER_ID);
		Column cartCol2 = new Column(CART.TABLE, CART.PRODUCT_ID);
		Column cartCol3 = new Column(CART.TABLE, CART.QUANTITY);
		Column productCol1 = new Column(PRODUCT.TABLE, PRODUCT.PRODUCT_ID);
		Column productCol2 = new Column(PRODUCT.TABLE, PRODUCT.PRODUCT_QUANTITY);
		Criteria productCriteria1 = new Criteria(productCol1, cartCol2, QueryConstants.EQUAL);
		Criteria productCriteria2 = new Criteria(productCol1, productId, QueryConstants.EQUAL);
		Criteria userCriteria = new Criteria(cartCol1, userId, QueryConstants.EQUAL);
		List columns = Arrays.asList(cartCol1, cartCol2, cartCol3, productCol1, productCol2);
		Join join = new Join(PRODUCT.TABLE, CART.TABLE, productCriteria1.and(userCriteria), Join.LEFT_JOIN);
		sq.addSelectColumns(columns);
		sq.setCriteria(productCriteria2);
		sq.addJoin(join);
		DataObject cartDob = pers.get(sq);
		System.out.println(cartDob);
		Row r = cartDob.getRow(PRODUCT.TABLE);
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
		// e.g. update product set product_quantity=product_quantity-1 where
		// product_id=2
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
			// insert into cart(user_id,product_id,quantity) values(1,2,1)
			// Adding a new entry for the product into the user cart
			Row cartRow = new Row(CART.TABLE);
			cartRow.set(CART.USER_ID, userId);
			cartRow.set(CART.PRODUCT_ID, productId);
			cartRow.set(CART.QUANTITY, quantity);
			DataObject newDob = new WritableDataObject();
			newDob.addRow(cartRow);
			pers.add(newDob);

		} else {
			// update cart set quantity=quantity+1 where user_id=1 and product_id=2
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
		// e.g. select
		// cart.user_id,cart.product_id,cart.quantity,product.product_id,product.product_price,product.product_name,product.is_available
		// from product inner join cart on product.product_id = cart.product_id
		// where cart.user_id=1
		ArrayList<ProductForm> cartLists = new ArrayList<ProductForm>();
		SelectQuery sq = new SelectQueryImpl(new Table(PRODUCT.TABLE));
		Column cartCol1 = new Column(CART.TABLE, CART.USER_ID);
		Column cartCol2 = new Column(CART.TABLE, CART.PRODUCT_ID);
		Column cartCol3 = new Column(CART.TABLE, CART.QUANTITY);
		Column productCol1 = new Column(PRODUCT.TABLE, PRODUCT.PRODUCT_ID);
		Column productCol2 = new Column(PRODUCT.TABLE, PRODUCT.PRODUCT_PRICE);
		Column productCol3 = new Column(PRODUCT.TABLE, PRODUCT.PRODUCT_NAME);
		Column productCol4 = new Column(PRODUCT.TABLE, PRODUCT.IS_AVAILABLE);
		SortColumn sortColumn = new SortColumn(productCol3, true);
		List columns = Arrays.asList(cartCol1, cartCol2, cartCol3, productCol1, productCol2, productCol3, productCol4);
		Criteria userCriteria = new Criteria(cartCol1, userId, QueryConstants.EQUAL);
		Join join = new Join(PRODUCT.TABLE, CART.TABLE, new String[] { PRODUCT.PRODUCT_ID },
				new String[] { CART.PRODUCT_ID }, Join.INNER_JOIN);
		sq.addSelectColumns(columns);
		sq.addSortColumn(sortColumn);
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
			productForm.setIsAvailable((Boolean) productRow.get(PRODUCT.IS_AVAILABLE));
			cartLists.add(productForm);
		}
		System.out.println(cartLists);
		return cartLists;
	}

	public void removeFromCart(Integer userId, Integer productId) throws DataAccessException {
		// e.g. update product set product_quantity=product_quantity+1
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
		// e.g. update cart set quantity=quantity-1 where product_id=4 and user_id=5
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
		// e.g. update product set product_quantity=product_quantity+5
		// where product_id=4
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

		// e.g. delete from cart where product_id=4 and user_id=5
		// Remove the cart row
		Column cartCol1 = new Column(CART.TABLE, CART.USER_ID);
		Column cartCol2 = new Column(CART.TABLE, CART.PRODUCT_ID);
		Criteria userCriteria = new Criteria(cartCol1, userId, QueryConstants.EQUAL);
		Criteria productCriteria = new Criteria(cartCol2, productId, QueryConstants.EQUAL);
		pers.delete(userCriteria.and(productCriteria));

	}

	public void addAddress(Integer userId, String address) throws DataAccessException {
		// e.g. insert into useraddress values(7,1,'velacherry')
		DataObject dob = new WritableDataObject();
		Row addressRow = new Row(USERADDRESS.TABLE);
		addressRow.set(USERADDRESS.ADDRESS, address);
		addressRow.set(USERADDRESS.USER_ID, userId);
		dob.addRow(addressRow);
		pers.add(dob);
	}

	public ArrayList<UserForm> getAddressList(Integer userId) throws DataAccessException {
		// e.g. select * from useraddress where user_id=1
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
		// Order Id creation
		DataObject dob = new WritableDataObject();
		Row userOrderRow = new Row(USERORDER.TABLE);
		userOrderRow.set(USERORDER.USER_ID, userId);
		userOrderRow.set(USERORDER.ADDRESS_ID, addressId);
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		userOrderRow.set(USERORDER.TIME_STAMP, timestamp);
		dob.addRow(userOrderRow);
		dob = pers.add(dob);
		Integer orderId = (Integer) dob.getValue(USERORDER.TABLE, USERORDER.ORDER_ID,
				new Criteria(new Column(USERORDER.TABLE, USERORDER.USER_ID), userId, QueryConstants.EQUAL));
		Column col1 = new Column(CART.TABLE, CART.USER_ID);
		Column col2 = new Column(CART.TABLE, CART.PRODUCT_ID);
		Column col3 = new Column(CART.TABLE, CART.QUANTITY);
		Column col4 = new Column(PRODUCT.TABLE, PRODUCT.PRODUCT_ID);
		Column col5 = new Column(PRODUCT.TABLE, PRODUCT.IS_AVAILABLE);
		SelectQuery sq = new SelectQueryImpl(new Table(CART.TABLE));
		Criteria criteria1 = new Criteria(col1, userId, QueryConstants.EQUAL);
		Join join = new Join(CART.TABLE, PRODUCT.TABLE, new String[] { CART.PRODUCT_ID },
				new String[] { PRODUCT.PRODUCT_ID }, Join.INNER_JOIN);
		List columns = Arrays.asList(col1, col2, col3, col4, col5);
		sq.setCriteria(criteria1);
		sq.addSelectColumns(columns);
		sq.addJoin(join);
		DataObject dob1 = pers.get(sq);
		Iterator iterator = dob1.getRows(CART.TABLE);
		DataObject dob2 = new WritableDataObject();
		while (iterator.hasNext()) {
			Row cartRow = (Row) iterator.next();
			Integer productId = (Integer) cartRow.get(CART.PRODUCT_ID);
			Integer quantity = (Integer) cartRow.get(CART.QUANTITY);
			Boolean isAvailable = (Boolean) dob1.getValue(PRODUCT.TABLE, PRODUCT.IS_AVAILABLE,
					new Criteria(col4, productId, QueryConstants.EQUAL));
			if (isAvailable) {
				Row orderRow = new Row(PRODUCTORDER.TABLE);
				orderRow.set(PRODUCTORDER.ORDER_ID, orderId);
				orderRow.set(PRODUCTORDER.PRODUCT_QUANTITY, quantity);
				orderRow.set(PRODUCTORDER.PRODUCT_ID, productId);
				dob2.addRow(orderRow);
			}
		}
		pers.add(dob2);
		Criteria criteria2 = new Criteria(col1, userId, QueryConstants.EQUAL);
		pers.delete(criteria2);
	}

	public ArrayList<UserForm> getOrderList(Integer userId) throws DataAccessException {
//		select * from userorder 
//		inner join productorder on userorder.order_id = productorder.order_id and user_id=1  
//		inner join useraddress on userorder.address_id = useraddress.address_id 
//		inner join product on productorder.product_id = product.product_id

		ArrayList<UserForm> orderLists = new ArrayList<UserForm>();
		// Columns
		// UserOrder Columns
		Column col1 = new Column(USERORDER.TABLE, USERORDER.ORDER_ID);
		Column col2 = new Column(USERORDER.TABLE, USERORDER.ADDRESS_ID);
		Column col3 = new Column(USERORDER.TABLE, USERORDER.TIME_STAMP);
		Column col4 = new Column(USERORDER.TABLE, USERORDER.USER_ID);
		// ProductOrder Columns
		Column col5 = new Column(PRODUCTORDER.TABLE, PRODUCTORDER.ORDER_ID);
		Column col6 = new Column(PRODUCTORDER.TABLE, PRODUCTORDER.PRODUCT_ID);
		Column col7 = new Column(PRODUCTORDER.TABLE, PRODUCTORDER.IS_CANCELLED);
		Column col12 = new Column(PRODUCTORDER.TABLE, PRODUCTORDER.PRODUCT_QUANTITY);
		// Product columns
		Column col8 = new Column(PRODUCT.TABLE, PRODUCT.PRODUCT_ID);
		Column col9 = new Column(PRODUCT.TABLE, PRODUCT.PRODUCT_NAME);
		// Address Columns
		Column col10 = new Column(USERADDRESS.TABLE, USERADDRESS.ADDRESS_ID);
		Column col11 = new Column(USERADDRESS.TABLE, USERADDRESS.ADDRESS);
		List columns = Arrays.asList(col1, col2, col3, col4, col5, col6, col7, col8, col9, col10, col11, col12);
		// Sort Column
		SortColumn sortColumn1 = new SortColumn(col3, false);
		SortColumn sortColumn2 = new SortColumn(col9, true);

		// Crtierias
		Criteria c1 = new Criteria(col1, col5, QueryConstants.EQUAL);
		Criteria c2 = new Criteria(col4, userId, QueryConstants.EQUAL);

		// Joins
		Join join1 = new Join(USERORDER.TABLE, PRODUCTORDER.TABLE, c1.and(c2), Join.INNER_JOIN);
		Join join2 = new Join(USERORDER.TABLE, USERADDRESS.TABLE, new String[] { USERORDER.ADDRESS_ID },
				new String[] { USERADDRESS.ADDRESS_ID }, Join.INNER_JOIN);
		Join join3 = new Join(PRODUCTORDER.TABLE, PRODUCT.TABLE, new String[] { PRODUCTORDER.PRODUCT_ID },
				new String[] { PRODUCT.PRODUCT_ID }, Join.INNER_JOIN);

		SelectQuery sq = new SelectQueryImpl(new Table(USERORDER.TABLE));
		sq.addSelectColumns(columns);
		sq.addSortColumn(sortColumn1);
		sq.addSortColumn(sortColumn2);
		sq.setCriteria(c1);
		sq.addJoin(join1);
		sq.addJoin(join2);
		sq.addJoin(join3);
		DataObject dob = pers.get(sq);
		System.out.println(dob);
		Iterator userOrderIterator = dob.getRows(USERORDER.TABLE);
		while (userOrderIterator.hasNext()) {
			UserForm userForm = new UserForm();
			Row userOrderRow = (Row) userOrderIterator.next();
			System.out.println(userOrderRow);
			Integer orderId = (Integer) userOrderRow.get(USERORDER.ORDER_ID);
			Integer addressId = (Integer) userOrderRow.get(USERORDER.ADDRESS_ID);
			String address = (String) dob.getValue(USERADDRESS.TABLE, USERADDRESS.ADDRESS,
					new Criteria(col10, addressId, QueryConstants.EQUAL));
			System.out.println(address);
			Timestamp timestamp = (Timestamp) userOrderRow.get(USERORDER.TIME_STAMP);
			userForm.setOrderId(orderId);
			userForm.setAddress(address);
			userForm.setTimeStamp(timestamp);
			ArrayList<ProductForm> productForms = new ArrayList<ProductForm>();
			Iterator productIterator = dob.getRows(PRODUCTORDER.TABLE,
					new Criteria(col5, orderId, QueryConstants.EQUAL));
			while (productIterator.hasNext()) {
				Row productRow = (Row) productIterator.next();
				System.out.println(productRow);
				ProductForm productForm = new ProductForm();
				Integer productId = (Integer) productRow.get(PRODUCTORDER.PRODUCT_ID);
				String productName = (String) dob.getValue(PRODUCT.TABLE, PRODUCT.PRODUCT_NAME,
						new Criteria(col8, productId, QueryConstants.EQUAL));
				System.out.println(productName);
				Integer quantity = (Integer) productRow.get(PRODUCTORDER.PRODUCT_QUANTITY);
				Boolean isCancelled = (Boolean) productRow.get(PRODUCTORDER.IS_CANCELLED);
				productForm.setProductId(productId);
				productForm.setProductName(productName);
				productForm.setProductQuantity(quantity);
				productForm.setIsCancelled(isCancelled);
				productForms.add(productForm);
			}
			userForm.setProducts(productForms);
			orderLists.add(userForm);
		}
		return orderLists;
	}

	public void cancelItem(Integer orderId, Integer productId, Integer quantity) throws DataAccessException {
		// Cancelled status is updated in the order Table
		// e.g. update productorder set is_cancelled=true where order_id=1
		UpdateQuery uq1 = new UpdateQueryImpl(PRODUCTORDER.TABLE);
		Column col = new Column(PRODUCTORDER.TABLE, PRODUCTORDER.ORDER_ID);
		Column col1 = new Column(PRODUCTORDER.TABLE, PRODUCTORDER.PRODUCT_ID);
		Criteria c1 = new Criteria(col, orderId, QueryConstants.EQUAL);
		Criteria c2 = new Criteria(col1, productId, QueryConstants.EQUAL);
		Column decQuantity = Column.createOperation(operationType.SUBTRACT,
				Column.getColumn(PRODUCTORDER.TABLE, PRODUCTORDER.PRODUCT_QUANTITY), quantity);
		decQuantity.setType(Types.INTEGER);
		uq1.setCriteria(c1.and(c2));
		uq1.setUpdateColumn(PRODUCTORDER.IS_CANCELLED, true);
		uq1.setUpdateColumn(PRODUCTORDER.PRODUCT_QUANTITY, decQuantity);
		pers.update(uq1);
		// Incrementing in the product Table
		// e.g. update product set product_quantity=product_quantity+1 where
		// product_id=1
		UpdateQuery uq2 = new UpdateQueryImpl(PRODUCT.TABLE);
		Column incQuantity = Column.createOperation(operationType.ADD,
				Column.getColumn(PRODUCT.TABLE, PRODUCT.PRODUCT_QUANTITY), quantity);
		incQuantity.setType(Types.INTEGER);
		uq2.setUpdateColumn(PRODUCT.PRODUCT_QUANTITY, incQuantity);
		Column productCol1 = new Column(PRODUCT.TABLE, PRODUCT.PRODUCT_ID);
		Criteria c3 = new Criteria(productCol1, productId, QueryConstants.EQUAL);
		uq2.setCriteria(c3);
		pers.update(uq2);
	}

}
