/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.com.helpers.transaction_model.transaction_response;

/**
 *
 * @author Chikodi
 */
public class Your_request {
    private String account_number;
  private Integer amount;
  private String beneficiary_name;
  private Integer channel_id;
  private String description;
  private Integer mode_id;
  private String type;
  private String client_reference;

  public String getClient_reference() {
    return client_reference;
  }

    // Getter Methods
    public void setClient_reference(String client_reference) {
        this.client_reference = client_reference;
    }

    public String getAccount_number() {
        return account_number;
    }

  public float getAmount() {
    return amount;
  }

  public String getBeneficiary_name() {
    return beneficiary_name;
  }

  public float getChannel_id() {
    return channel_id;
  }

  public String getDescription() {
    return description;
  }

  public float getMode_id() {
    return mode_id;
  }

  public String getType() {
    return type;
  }

 // Setter Methods 

  public void setAccount_number( String account_number ) {
    this.account_number = account_number;
  }

  public void setAmount( Integer amount ) {
    this.amount = amount;
  }

  public void setBeneficiary_name( String beneficiary_name ) {
    this.beneficiary_name = beneficiary_name;
  }

  public void setChannel_id( Integer channel_id ) {
    this.channel_id = channel_id;
  }

  public void setDescription( String description ) {
    this.description = description;
  }

  public void setMode_id( Integer mode_id ) {
    this.mode_id = mode_id;
  }

  public void setType( String type ) {
    this.type = type;
  }
}
