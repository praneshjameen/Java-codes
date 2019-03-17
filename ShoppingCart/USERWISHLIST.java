package com.me.sdp.cart.ShoppingCart;

/** <p> Description of the table <code>UserWishList</code>.
 *  Column Name and Table Name of  database table  <code>UserWishList</code> is mapped
 * as constants in this util.</p> 
   * 
  * Primary Keys for this definition are  <br>
  <ul>
  * <li> {@link #USER_ID}
  * <li> {@link #PRODUCT_ID}
  * </ul>
 */
 
public final class USERWISHLIST
{
    private USERWISHLIST()
    {
    }
   
    /** Constant denoting the Table Name of this definition.
     */
    public static final String TABLE = "UserWishList" ;
    /**
                            * This column is one of the Primary Key for this Table definition. <br>
                            * Data Type of this field is <code>INTEGER</code>. <br>
                                          * This field is not nullable. <br>
                                */
    public static final String PRODUCT_ID= "PRODUCT_ID" ;

    /*
    * The index position of the column PRODUCT_ID in the table.
    */
    public static final int PRODUCT_ID_IDX = 1 ;

    /**
                            * This column is one of the Primary Key for this Table definition. <br>
                            * Data Type of this field is <code>INTEGER</code>. <br>
                                          * This field is not nullable. <br>
                                */
    public static final String USER_ID= "USER_ID" ;

    /*
    * The index position of the column USER_ID in the table.
    */
    public static final int USER_ID_IDX = 2 ;

}
