package com.me.sdp.cart.ShoppingCart;

/** <p> Description of the table <code>UserOrder</code>.
 *  Column Name and Table Name of  database table  <code>UserOrder</code> is mapped
 * as constants in this util.</p> 
   * 
  * Primary Key for this definition is  <br>
  <ul>
  * <li> {@link #ORDER_ID}
  * </ul>
 */
 
public final class USERORDER
{
    private USERORDER()
    {
    }
   
    /** Constant denoting the Table Name of this definition.
     */
    public static final String TABLE = "UserOrder" ;
    /**
                            * This column is an Primary Key for this Table definition. <br>
                            * Data Type of this field is <code>INTEGER</code>. <br>
                                          * This field is not nullable. <br>
                                */
    public static final String ORDER_ID= "ORDER_ID" ;

    /*
    * The index position of the column ORDER_ID in the table.
    */
    public static final int ORDER_ID_IDX = 1 ;

    /**
                            * Data Type of this field is <code>INTEGER</code>. <br>
                                          * This field is nullable. <br>
                                */
    public static final String USER_ID= "USER_ID" ;

    /*
    * The index position of the column USER_ID in the table.
    */
    public static final int USER_ID_IDX = 2 ;

    /**
                            * Data Type of this field is <code>DATETIME</code>. <br>
                                          * This field is nullable. <br>
                                */
    public static final String TIME_STAMP= "TIME_STAMP" ;

    /*
    * The index position of the column TIME_STAMP in the table.
    */
    public static final int TIME_STAMP_IDX = 3 ;

    /**
                            * Data Type of this field is <code>INTEGER</code>. <br>
                                          * This field is nullable. <br>
                                */
    public static final String ADDRESS_ID= "ADDRESS_ID" ;

    /*
    * The index position of the column ADDRESS_ID in the table.
    */
    public static final int ADDRESS_ID_IDX = 4 ;

}
