package com.me.sdp.cart.ShoppingCart;

/** <p> Description of the table <code>ProductOrder</code>.
 *  Column Name and Table Name of  database table  <code>ProductOrder</code> is mapped
 * as constants in this util.</p> 
   * 
  * Primary Keys for this definition are  <br>
  <ul>
  * <li> {@link #ORDER_ID}
  * <li> {@link #PRODUCT_ID}
  * </ul>
 */
 
public final class PRODUCTORDER
{
    private PRODUCTORDER()
    {
    }
   
    /** Constant denoting the Table Name of this definition.
     */
    public static final String TABLE = "ProductOrder" ;
    /**
                            * This column is one of the Primary Key for this Table definition. <br>
                            * Data Type of this field is <code>INTEGER</code>. <br>
                                          * This field is nullable. <br>
                                */
    public static final String ORDER_ID= "ORDER_ID" ;

    /*
    * The index position of the column ORDER_ID in the table.
    */
    public static final int ORDER_ID_IDX = 1 ;

    /**
                            * This column is one of the Primary Key for this Table definition. <br>
                            * Data Type of this field is <code>INTEGER</code>. <br>
                                          * This field is nullable. <br>
                                */
    public static final String PRODUCT_ID= "PRODUCT_ID" ;

    /*
    * The index position of the column PRODUCT_ID in the table.
    */
    public static final int PRODUCT_ID_IDX = 2 ;

    /**
                            * Data Type of this field is <code>INTEGER</code>. <br>
                                          * This field is nullable. <br>
                                */
    public static final String PRODUCT_QUANTITY= "PRODUCT_QUANTITY" ;

    /*
    * The index position of the column PRODUCT_QUANTITY in the table.
    */
    public static final int PRODUCT_QUANTITY_IDX = 3 ;

    /**
                            * Data Type of this field is <code>BOOLEAN</code>. <br>
                            * Default Value is <code>false</code>. <br>
                            * This field is not nullable. <br>
                                */
    public static final String IS_CANCELLED= "IS_CANCELLED" ;

    /*
    * The index position of the column IS_CANCELLED in the table.
    */
    public static final int IS_CANCELLED_IDX = 4 ;

}
