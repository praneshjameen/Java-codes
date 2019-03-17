package com.me.sdp.cart.ShoppingCart;

/** <p> Description of the table <code>UserAddress</code>.
 *  Column Name and Table Name of  database table  <code>UserAddress</code> is mapped
 * as constants in this util.</p> 
   * 
  * Primary Key for this definition is  <br>
  <ul>
  * <li> {@link #ADDRESS_ID}
  * </ul>
 */
 
public final class USERADDRESS
{
    private USERADDRESS()
    {
    }
   
    /** Constant denoting the Table Name of this definition.
     */
    public static final String TABLE = "UserAddress" ;
    /**
                            * This column is an Primary Key for this Table definition. <br>
                            * Data Type of this field is <code>INTEGER</code>. <br>
                                          * This field is not nullable. <br>
                                */
    public static final String ADDRESS_ID= "ADDRESS_ID" ;

    /*
    * The index position of the column ADDRESS_ID in the table.
    */
    public static final int ADDRESS_ID_IDX = 1 ;

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
                            * Data Type of this field is <code>CHAR</code>. <br>
                                          * This field is nullable. <br>
                                */
    public static final String ADDRESS= "ADDRESS" ;

    /*
    * The index position of the column ADDRESS in the table.
    */
    public static final int ADDRESS_IDX = 3 ;

}
