/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.com.helpers.balance_enquiry;

import java.util.ArrayList;

/**
 *
 * @author IETECH
 */
public class Your_response {
    private String account_number;
  Account_officer Account_officerObject;
  private String available_balance;
  ArrayList<Object> budget = new ArrayList<Object>();
  Category CategoryObject;
  private String commencement_date;
  private String created_at;
  private String customer;
  private String ledger_balance;
  ArrayList<Object> loans = new ArrayList<Object>();
  private String meta = null;
  private String minimum_balance;
  private String name;
  private String overdraft_fee;
  private String overdraft_limit;
  private String overdraft_rate;
  Overdraft_status Overdraft_statusObject;
  private String preference;
  private String previous_available_balance;
  private String previous_ledger_balance;
  ArrayList<Object> savings = new ArrayList<Object>();
  ArrayList<Object> signatories = new ArrayList<Object>();
  private String status;
  private String tenure = null;
  private String third_party_confirmation;
  ArrayList<Object> transactions = new ArrayList<Object>();
  Type TypeObject;


 // Getter Methods 

  public String getAccount_number() {
    return account_number;
  }

  public Account_officer getAccount_officer() {
    return Account_officerObject;
  }

  public String getAvailable_balance() {
    return available_balance;
  }

  public Category getCategory() {
    return CategoryObject;
  }

  public String getCommencement_date() {
    return commencement_date;
  }

  public String getCreated_at() {
    return created_at;
  }

  public String getCustomer() {
    return customer;
  }

  public String getLedger_balance() {
    return ledger_balance;
  }

  public String getMeta() {
    return meta;
  }

  public String getMinimum_balance() {
    return minimum_balance;
  }

  public String getName() {
    return name;
  }

  public String getOverdraft_fee() {
    return overdraft_fee;
  }

  public String getOverdraft_limit() {
    return overdraft_limit;
  }

  public String getOverdraft_rate() {
    return overdraft_rate;
  }

  public Overdraft_status getOverdraft_status() {
    return Overdraft_statusObject;
  }

  public String getPreference() {
    return preference;
  }

  public String getPrevious_available_balance() {
    return previous_available_balance;
  }

  public String getPrevious_ledger_balance() {
    return previous_ledger_balance;
  }

  public String getStatus() {
    return status;
  }

  public String getTenure() {
    return tenure;
  }

  public String getThird_party_confirmation() {
    return third_party_confirmation;
  }

  public Type getType() {
    return TypeObject;
  }

 // Setter Methods 

  public void setAccount_number( String account_number ) {
    this.account_number = account_number;
  }

  public void setAccount_officer( Account_officer account_officerObject ) {
    this.Account_officerObject = account_officerObject;
  }

  public void setAvailable_balance( String available_balance ) {
    this.available_balance = available_balance;
  }

  public void setCategory( Category categoryObject ) {
    this.CategoryObject = categoryObject;
  }

  public void setCommencement_date( String commencement_date ) {
    this.commencement_date = commencement_date;
  }

  public void setCreated_at( String created_at ) {
    this.created_at = created_at;
  }

  public void setCustomer( String customer ) {
    this.customer = customer;
  }

  public void setLedger_balance( String ledger_balance ) {
    this.ledger_balance = ledger_balance;
  }

  public void setMeta( String meta ) {
    this.meta = meta;
  }

  public void setMinimum_balance( String minimum_balance ) {
    this.minimum_balance = minimum_balance;
  }

  public void setName( String name ) {
    this.name = name;
  }

  public void setOverdraft_fee( String overdraft_fee ) {
    this.overdraft_fee = overdraft_fee;
  }

  public void setOverdraft_limit( String overdraft_limit ) {
    this.overdraft_limit = overdraft_limit;
  }

  public void setOverdraft_rate( String overdraft_rate ) {
    this.overdraft_rate = overdraft_rate;
  }

  public void setOverdraft_status( Overdraft_status overdraft_statusObject ) {
    this.Overdraft_statusObject = overdraft_statusObject;
  }

  public void setPreference( String preference ) {
    this.preference = preference;
  }

  public void setPrevious_available_balance( String previous_available_balance ) {
    this.previous_available_balance = previous_available_balance;
  }

  public void setPrevious_ledger_balance( String previous_ledger_balance ) {
    this.previous_ledger_balance = previous_ledger_balance;
  }

  public void setStatus( String status ) {
    this.status = status;
  }

  public void setTenure( String tenure ) {
    this.tenure = tenure;
  }

  public void setThird_party_confirmation( String third_party_confirmation ) {
    this.third_party_confirmation = third_party_confirmation;
  }

  public void setType( Type typeObject ) {
    this.TypeObject = typeObject;
  }
}
