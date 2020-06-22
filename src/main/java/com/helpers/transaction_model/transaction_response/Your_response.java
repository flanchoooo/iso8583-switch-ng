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
public class Your_response {
   private String account;
  private float amount;
  private String beneficiary_name;
  private String beneficiary_phone = null;
  private String cot_fee = null;
  private String created_at;
  private String deposit_slip_no = null;
  private String meta = null;
  private String payer_name = null;
  private String payer_phone = null;
  private String reference;
  Transaction_item Transaction_itemObject;
  private String type;


 // Getter Methods 

  public String getAccount() {
    return account;
  }

  public float getAmount() {
    return amount;
  }

  public String getBeneficiary_name() {
    return beneficiary_name;
  }

  public String getBeneficiary_phone() {
    return beneficiary_phone;
  }

  public String getCot_fee() {
    return cot_fee;
  }

  public String getCreated_at() {
    return created_at;
  }

  public String getDeposit_slip_no() {
    return deposit_slip_no;
  }

  public String getMeta() {
    return meta;
  }

  public String getPayer_name() {
    return payer_name;
  }

  public String getPayer_phone() {
    return payer_phone;
  }

  public String getReference() {
    return reference;
  }

  public Transaction_item getTransaction_item() {
    return Transaction_itemObject;
  }

  public String getType() {
    return type;
  }

 // Setter Methods 

  public void setAccount( String account ) {
    this.account = account;
  }

  public void setAmount( float amount ) {
    this.amount = amount;
  }

  public void setBeneficiary_name( String beneficiary_name ) {
    this.beneficiary_name = beneficiary_name;
  }

  public void setBeneficiary_phone( String beneficiary_phone ) {
    this.beneficiary_phone = beneficiary_phone;
  }

  public void setCot_fee( String cot_fee ) {
    this.cot_fee = cot_fee;
  }

  public void setCreated_at( String created_at ) {
    this.created_at = created_at;
  }

  public void setDeposit_slip_no( String deposit_slip_no ) {
    this.deposit_slip_no = deposit_slip_no;
  }

  public void setMeta( String meta ) {
    this.meta = meta;
  }

  public void setPayer_name( String payer_name ) {
    this.payer_name = payer_name;
  }

  public void setPayer_phone( String payer_phone ) {
    this.payer_phone = payer_phone;
  }

  public void setReference( String reference ) {
    this.reference = reference;
  }

  public void setTransaction_item( Transaction_item transaction_itemObject ) {
    this.Transaction_itemObject = transaction_itemObject;
  }

  public void setType( String type ) {
    this.type = type;
  }
}
