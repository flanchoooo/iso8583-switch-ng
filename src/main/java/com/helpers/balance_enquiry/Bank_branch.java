/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.com.helpers.balance_enquiry;

/**
 *
 * @author IETECH
 */
public class Bank_branch {
    private String address = null;
  private String city;
  private float id;
  private String name = null;


 // Getter Methods 

  public String getAddress() {
    return address;
  }

  public String getCity() {
    return city;
  }

  public float getId() {
    return id;
  }

  public String getName() {
    return name;
  }

 // Setter Methods 

  public void setAddress( String address ) {
    this.address = address;
  }

  public void setCity( String city ) {
    this.city = city;
  }

  public void setId( float id ) {
    this.id = id;
  }

  public void setName( String name ) {
    this.name = name;
  }
}
