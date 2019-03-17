package com.me.sdp.cart.ShoppingCart;

/** <p> Description of the table <code>Product</code>.
 *  Column Name and Table Name of  database table  <code>Product</code> is mapped
 * as constants in this util.</p> 
   * 
  * Primary Key for this definition is  <br>
  <ul>
  * <li> {@link #PRODUCT_ID}
  * </ul>
 */
 
public final class PRODUCT
{
    private PRODUCT()
    {
    }
   
    /** Constant denoting the Table Name of this definition.
     */
    public static final String TABLE = "Product" ;
    /**
                            * This column is an Primary Key for this Table definition. <br>
                            * Data Type of this field is <code>INTEGER</code>. <br>
                                          * This field is not nullable. <br>
                                */
    public static final String PRODUCT_ID= "PRODUCT_ID" ;

    /*
    * The index position of the column PRODUCT_ID in the table.
    */
    public static final int PRODUCT_ID_IDX = 1 ;

    /**
                            * Data Type of this field is <code>CHAR</code>. <br>
                     * Maximum length of this field value is <code>50</code>. <br>
                                   * This field is nullable. <br>
                                */
    public static final String PRODUCT_NAME= "PRODUCT_NAME" ;

    /*
    * The index position of the column PRODUCT_NAME in the table.
    */
    public static final int PRODUCT_NAME_IDX = 2 ;

    /**
                            * Data Type of this field is <code>CHAR</code>. <br>
                     * Maximum length of this field value is <code>100</code>. <br>
                                   * This field is nullable. <br>
                                */
    public static final String PRODUCT_SPEC= "PRODUCT_SPEC" ;

    /*
    * The index position of the column PRODUCT_SPEC in the table.
    */
    public static final int PRODUCT_SPEC_IDX = 3 ;

    /**
                            * Data Type of this field is <code>INTEGER</code>. <br>
                     * Maximum length of this field value is <code>10</code>. <br>
                                   * This field is nullable. <br>
                                */
    public static final String PRODUCT_PRICE= "PRODUCT_PRICE" ;

    /*
    * The index position of the column PRODUCT_PRICE in the table.
    */
    public static final int PRODUCT_PRICE_IDX = 4 ;

    /**
                            * Data Type of this field is <code>INTEGER</code>. <br>
                     * Maximum length of this field value is <code>10</code>. <br>
                                   * This field is nullable. <br>
                                */
    public static final String PRODUCT_QUANTITY= "PRODUCT_QUANTITY" ;

    /*
    * The index position of the column PRODUCT_QUANTITY in the table.
    */
    public static final int PRODUCT_QUANTITY_IDX = 5 ;

    /**
                            * Data Type of this field is <code>BOOLEAN</code>. <br>
                            * Default Value is <code>true</code>. <br>
                     * This field is not nullable. If value for field is not set default value "<code>true</code>" , 
       * will be taken.<br>
                         */
    public static final String IS_AVAILABLE= "IS_AVAILABLE" ;

    /*
    * The index position of the column IS_AVAILABLE in the table.
    */
    public static final int IS_AVAILABLE_IDX = 6 ;

}
