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
public class BalanceEnquiry {
     private String api_ref;
  private float code;
  private String message;
  private String status;
  private String your_request = null;
  Your_response your_response;
  // Getter Methods 

  public String getApi_ref() {
    return api_ref;
  }

  public float getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

  public String getStatus() {
    return status;
  }

  public String getYour_request() {
    return your_request;
  }

  public Your_response getYour_response() {
    return your_response;
  }

 // Setter Methods 

  public void setApi_ref( String api_ref ) {
    this.api_ref = api_ref;
  }

  public void setCode( float code ) {
    this.code = code;
  }

  public void setMessage( String message ) {
    this.message = message;
  }

  public void setStatus( String status ) {
    this.status = status;
  }

  public void setYour_request( String your_request ) {
    this.your_request = your_request;
  }

  public void setYour_response( Your_response your_response ) {
    this.your_response = your_response;
  }
  
}
