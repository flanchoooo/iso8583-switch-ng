/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.com.helpers.transaction_model.transaction_response;

/**
 *
 * @author IETECH
 */
public class Transaction_item {

    private String account_type = null;
  private String expense_category = null;
  private String icon_name;
  private float id;
  private String name;


 // Getter Methods 

  public String getAccount_type() {
    return account_type;
  }

  public String getExpense_category() {
    return expense_category;
  }

  public String getIcon_name() {
    return icon_name;
  }

  public float getId() {
    return id;
  }

  public String getName() {
    return name;
  }

 // Setter Methods 

  public void setAccount_type( String account_type ) {
    this.account_type = account_type;
  }

  public void setExpense_category( String expense_category ) {
    this.expense_category = expense_category;
  }

  public void setIcon_name( String icon_name ) {
    this.icon_name = icon_name;
  }

  public void setId( float id ) {
    this.id = id;
  }

  public void setName( String name ) {
    this.name = name;
  }
}
