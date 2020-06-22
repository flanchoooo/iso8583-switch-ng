/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.com.transaction.manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import static java.lang.System.lineSeparator;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import static java.util.stream.Collectors.joining;
import main.java.com.helpers.balance_enquiry.BalanceEnquiry;
import main.java.com.helpers.SmartObject;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOSource;
import org.jpos.util.NameRegistrar;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author ietechadmin
 */
public class InquiryHostProcessor implements Runnable  {
    private ISOMsg isoMsg;
    private ISOSource isoSrc;
   
    public InquiryHostProcessor(ISOMsg isoMsg, ISOSource isoSrc )
    {
        this.isoMsg = isoMsg;
        this.isoSrc =isoSrc;
        
    }

    @Override
    public void run() {
       
        ResourceBundle rb = ResourceBundle.getBundle("dist.cfg.config");
        String api_endpoint = rb.getString("api_endpoint");
        String balance_api = rb.getString("balance_enquiry_api");
        String user_name = rb.getString("user_name");
        String password = rb.getString("password");
        String api_key = rb.getString("api_key");
        System.out.println("End point " + api_endpoint);
        System.out.println("Balance Url: " + balance_api);
        balance_api = balance_api.replace("{account_no}", this.isoMsg.getString("102"));
        System.out.println("Replaced Url " +api_endpoint + balance_api);
        
         try {
                URL url = new URL(api_endpoint + balance_api);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Username", user_name);
                conn.setRequestProperty("Password", password);
                conn.setRequestProperty("API-Key", api_key);
		Gson gson = new GsonBuilder().serializeNulls().create();

                 System.out.println("Response code " + conn.getResponseCode());
                 
               if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(
                                    (conn.getInputStream())));

                    String output;
                    StringBuilder builder = new StringBuilder();
                   //System.out.println("Output from Server .... \n" +br.readLine());
                    String result = br.lines().collect(joining(lineSeparator()));
                    System.out.println("Response from server " + result);
                        BalanceEnquiry respMsg = new BalanceEnquiry();
                        respMsg = gson.fromJson(result, BalanceEnquiry.class);
                        if(this.isoSrc  != null)
                        {
                            System.out.println("Iso Source is not null");
                            if(respMsg == null)
                            {
                                this.isoMsg.setResponseMTI();
                                this.isoMsg.set(39,"96");
                                this.isoSrc.send(this.isoMsg);
                            }
                            else{
                                if(respMsg.getCode() == 200)
                                {
                                    NumberFormat nf = new DecimalFormat("#.####");
                                   
                                    String Balance = respMsg.getYour_response().getAvailable_balance();
                                    System.out.println("Balance First " + Balance);
                                    String AccountType = this.isoMsg.getString(3).substring(2,4);
                                    String LedgerAmountType = "01";
                                    
                                    String NetAvailableAmountType = "02";
                                    
                                    String UnclearedAmountType = "03";
                                    
                                    String CurrencyCode = this.isoMsg.getString(49);
                                    String AmountSign ="C";
                                    Double d1 = Double.parseDouble(Balance);
                                    String formattedAmount = nf.format(d1);
                                    System.out.println("Balance Formated" + formattedAmount);
                                    String AppendedBalance = SmartObject.leftPad(formattedAmount, 12, "0");
                                    System.out.println("Balance " + AppendedBalance);
                                    String LedgerBalance = AccountType + LedgerAmountType + CurrencyCode + AmountSign +  AppendedBalance;
                                    String NetAvailablceBalance = AccountType + NetAvailableAmountType + CurrencyCode + AmountSign+ AppendedBalance;
                                    String UnclearedBalance = AccountType + UnclearedAmountType + CurrencyCode + AmountSign +SmartObject.leftPad("0", 12, "0");
                                    
                                    String Bal = LedgerBalance + NetAvailablceBalance + UnclearedBalance;
                                    
                                    this.isoMsg.setResponseMTI();
                                    this.isoMsg.set(54, Bal);
                                    this.isoMsg.set(39,"00");
                                    this.isoSrc.send(this.isoMsg);
                                }
                                else{
                                    this.isoMsg.setResponseMTI();
                                    this.isoMsg.set(39,"01"); // get response code 
                                    this.isoSrc.send(this.isoMsg);
                                    
                                }

                            }
                                
                        }
                    
            }
            conn.disconnect();

	  } catch (MalformedURLException e) {

		e.printStackTrace();

	  } catch (IOException e) {

		e.printStackTrace();

	 } 
         catch (ISOException ex) {
            java.util.logging.Logger.getLogger(HostPorcessor.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        

            
    }
    
}
