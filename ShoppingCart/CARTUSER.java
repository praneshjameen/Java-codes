package com.me.sdp.cart.ShoppingCart;

/** <p> Description of the table <code>CartUser</code>.
 *  Column Name and Table Name of  database table  <code>CartUser</code> is mapped
 * as constants in this util.</p> 
   * 
  * Primary Key for this definition is  <br>
  <ul>
  * <li> {@link #USER_ID}
  * </ul>
 */
 
public final class CARTUSER
{
    private CARTUSER()
    {
    }
   
    /** Constant denoting the Table Name of this definition.
     */
    public static final String TABLE = "CartUser" ;
    /**
                            * This column is an Primary Key for this Table definition. <br>
                            * Data Type of this field is <code>INTEGER</code>. <br>
                                          * This field is not nullable. <br>
                                */
    public static final String USER_ID= "USER_ID" ;

    /*
    * The index position of the column USER_ID in the table.
    */
    public static final int USER_ID_IDX = 1 ;

    /**
                            * Data Type of this field is <code>CHAR</code>. <br>
                     * Maximum length of this field value is <code>50</code>. <br>
                                   * This field is nullable. <br>
                                */
    public static final String USERNAME= "USERNAME" ;

    /*
    * The index position of the column USERNAME in the table.
    */
    public static final int USERNAME_IDX = 2 ;

    /**
                            * Data Type of this field is <code>CHAR</code>. <br>
                     * Maximum length of this field value is <code>50</code>. <br>
                                   * This field is nullable. <br>
                                */
    public static final String PASSWORD= "PASSWORD" ;

    /*
    * The index position of the column PASSWORD in the table.
    */
    public static final int PASSWORD_IDX = 3 ;

    /**
                            * Data Type of this field is <code>BIGINT</code>. <br>
                     * Maximum length of this field value is <code>20</code>. <br>
                                   * This field is nullable. <br>
                                */
    public static final String PHONE_NUMBER= "PHONE_NUMBER" ;

    /*
    * The index position of the column PHONE_NUMBER in the table.
    */
    public static final int PHONE_NUMBER_IDX = 4 ;

    /**
                            * Data Type of this field is <code>BOOLEAN</code>. <br>
                            * Default Value is <code>false</code>. <br>
                     * This field is not nullable. If value for field is not set default value "<code>false</code>" , 
       * will be taken.<br>
                         */
    public static final String IS_PRIVILEGE= "IS_PRIVILEGE" ;

    /*
    * The index position of the column IS_PRIVILEGE in the table.
    */
    public static final int IS_PRIVILEGE_IDX = 5 ;

}
