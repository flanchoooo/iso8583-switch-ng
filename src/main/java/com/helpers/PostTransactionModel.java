/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.com.helpers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author IETECHADMIN
 */
public class PostTransactionModel {
     public static void main(String[] args) throws Exception{
        
        TestProp tsprop =new TestProp();
        tsprop.reqTransID = "123456789";
        tsprop.accountNo = "00110100001";
        tsprop.amount = 400;
       // tsprop.acqfiid = "3444";
        //tsprop.atm_Location ="Mushin";
        tsprop.bankCode = "10";
        tsprop.channel =1;
       // tsprop.issuerBIN ="1234567890";
        tsprop.narration ="Test";
       // tsprop.pan ="12345678909";
       // tsprop.stan ="45555";
        tsprop.tranCode ="01";
       // tsprop.issuerfiid ="1222";
       Gson gson = new GsonBuilder().serializeNulls().create();
        String jsonInString = gson.toJson(tsprop);
        
        System.out.println(" New Values" +jsonInString );
        
	try {

		URL url = new URL("http://62.173.32.98/eChannel/api/eChannelPro/creditPayment");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Authorization", "eCHANNEL dXNlcm5hbWU6JENXR1NlbmRNTkkkVjEkMTAwMDAkT0p1dFAzdDVBTDBiTjZQVU9WbHFOY3dVOUFNY1l1V2g1WlliMUVPL1VvVmNVR052");

		//String input = "{\"reqTransID\":100,\"accountNo\":\"00110100001\", \"amount\":100,\"bankCode\":10, \"narration\":10}";

		OutputStream os = conn.getOutputStream();
		os.write(jsonInString.getBytes());
		os.flush();

		if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
			throw new RuntimeException("Failed : HTTP error code : "
				+ conn.getResponseCode());
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));

		String output;
		System.out.println("Output from Server .... \n");
		while ((output = br.readLine()) != null) {
                    ResponseMessage respMsg = new ResponseMessage();
                    respMsg = gson.fromJson(output, ResponseMessage.class);
                    System.out.println(respMsg.getErrorText());
		}

		conn.disconnect();

	  } catch (MalformedURLException e) {

		e.printStackTrace();

	  } catch (IOException e) {

		e.printStackTrace();

	 }

	

        
        //System.out.println(" New Values" +jsonInString );
    }
}


