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
public class Account_officer {
    private String address;
  Bank_branch Bank_branchObject;
  private String created_at;
  private String email;
  private String first_name;
  private String gender;
  private String global_view = null;
  private String last_name;
  private String phone;
  private String staff_id;
  private String title;
  private String uid;


 // Getter Methods 

  public String getAddress() {
    return address;
  }

  public Bank_branch getBank_branch() {
    return Bank_branchObject;
  }

  public String getCreated_at() {
    return created_at;
  }

  public String getEmail() {
    return email;
  }

  public String getFirst_name() {
    return first_name;
  }

  public String getGender() {
    return gender;
  }

  public String getGlobal_view() {
    return global_view;
  }

  public String getLast_name() {
    return last_name;
  }

  public String getPhone() {
    return phone;
  }

  public String getStaff_id() {
    return staff_id;
  }

  public String getTitle() {
    return title;
  }

  public String getUid() {
    return uid;
  }

 // Setter Methods 

  public void setAddress( String address ) {
    this.address = address;
  }

  public void setBank_branch( Bank_branch bank_branchObject ) {
    this.Bank_branchObject = bank_branchObject;
  }

  public void setCreated_at( String created_at ) {
    this.created_at = created_at;
  }

  public void setEmail( String email ) {
    this.email = email;
  }

  public void setFirst_name( String first_name ) {
    this.first_name = first_name;
  }

  public void setGender( String gender ) {
    this.gender = gender;
  }

  public void setGlobal_view( String global_view ) {
    this.global_view = global_view;
  }

  public void setLast_name( String last_name ) {
    this.last_name = last_name;
  }

  public void setPhone( String phone ) {
    this.phone = phone;
  }

  public void setStaff_id( String staff_id ) {
    this.staff_id = staff_id;
  }

  public void setTitle( String title ) {
    this.title = title;
  }

  public void setUid( String uid ) {
    this.uid = uid;
  }
}
