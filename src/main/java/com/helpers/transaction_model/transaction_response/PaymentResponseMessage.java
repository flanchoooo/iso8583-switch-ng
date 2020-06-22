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
public class PaymentResponseMessage {
   private String api_ref;
  private String code;
  private String message;
  private String status;
  Your_request Your_requestObject;
  Your_response Your_responseObject;
private String unique_code;

    public String getUnique_code() {
        return unique_code;
    }

    public void setUnique_code(String unique_code) {
        this.unique_code = unique_code;
    }

 // Getter Methods 

  public String getApi_ref() {
    return api_ref;
  }

  public String getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

  public String getStatus() {
    return status;
  }

  public Your_request getYour_request() {
    return Your_requestObject;
  }

  public Your_response getYour_response() {
    return Your_responseObject;
  }

 // Setter Methods 

  public void setApi_ref( String api_ref ) {
    this.api_ref = api_ref;
  }

  public void setCode( String code ) {
    this.code = code;
  }

  public void setMessage( String message ) {
    this.message = message;
  }

  public void setStatus( String status ) {
    this.status = status;
  }

  public void setYour_request( Your_request your_requestObject ) {
    this.Your_requestObject = your_requestObject;
  }

  public void setYour_response( Your_response your_responseObject ) {
    this.Your_responseObject = your_responseObject;
  }
}
