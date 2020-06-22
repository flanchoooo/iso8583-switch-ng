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
import java.io.StringReader;
import java.io.StringWriter;
import static java.lang.System.lineSeparator;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.util.Logger;
import org.jpos.util.NameRegistrar;

/**
 *
 * @author ietechadmin
 */
import java.util.concurrent.*;
import static java.util.stream.Collectors.joining;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import main.java.com.helpers.SmartObject;
import main.java.com.helpers.transaction_model.transaction_response.PaymentResponseMessage;
import main.java.com.helpers.XMLReader;
import main.java.com.helpers.XmlBuilder;
import static main.java.com.helpers.XmlBuilder.postXML;
import main.java.com.helpers.transaction_model.transaction_response.Your_request;
import main.java.com.ietech.ISO_RequestListener;
import org.jpos.iso.ISOSource;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class HostPorcessor implements Runnable {

   private ISOMsg isoMsg;
    private ISOSource isoSrc;
    private CountDownLatch countDown;
    private ISOMsg isoRespMsg;
    public HostPorcessor(ISOMsg isoMsg, ISOSource isoSrc, CountDownLatch countDown)
    {
        this.isoMsg = isoMsg;
        this.isoSrc = isoSrc;
        this.countDown = countDown;
                
    }
    public ISOMsg getIsoRespMsg()
    {
        //this.isoRespMsg = this.isoMsg;
        return this.isoMsg;
    }
    @Override
    public void run() {

        ResourceBundle rb = ResourceBundle.getBundle("dist.cfg.config");
        String api_endpoint = rb.getString("api_endpoint");
        String post_api = rb.getString("payment_api");
        String user_name = rb.getString("user_name");
        String password = rb.getString("password");
        String api_key = rb.getString("api_key");
        System.out.println("End point " + api_endpoint);
        System.out.println("Url: URL Payment API" + post_api);

        Your_request reqMsg = new Your_request();

        reqMsg.setMode_id(2);

        String narration = this.isoMsg.getString(37) + this.isoMsg.getString(43);

        reqMsg.setDescription(narration);
        reqMsg.setBeneficiary_name("Self");

        String Channel = this.isoMsg.getString(123).substring(13, 15);
        System.out.println("Channel Value" + Channel);
        if (Channel.equals("02")) {
            reqMsg.setChannel_id(1);
        } else if (Channel.equals("01")) {
            reqMsg.setChannel_id(3);
        } else if (Channel.equals("90")) {
            reqMsg.setChannel_id(5);
        }
        String field90 = BuildField90(this.isoMsg);
        
        reqMsg.setClient_reference(field90);

        reqMsg.setAmount(Integer.parseInt(String.valueOf(Integer.parseInt(this.isoMsg.getString(4))/100)));
        reqMsg.setAccount_number(this.isoMsg.getString(102));
        reqMsg.setType("DEBIT");

        Gson gson = new GsonBuilder().serializeNulls().create();
        String jsonInString = gson.toJson(reqMsg); //Convert object to Json string
        System.out.println("Json Converted " + jsonInString);
        PaymentResponseMessage respMsg = new PaymentResponseMessage();
        try {
            this.isoMsg.setResponseMTI();
//            this.isoMsg.set(39, "00");
//            this.isoSrc.send(isoMsg);
            //About to call endpoint
            URL url = new URL(api_endpoint + post_api);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Username", user_name);
            conn.setRequestProperty("Password", password);
            conn.setRequestProperty("API-Key", api_key);
            //conn.connect();
            OutputStream os = conn.getOutputStream();
            os.write(jsonInString.getBytes());
            os.flush();
            System.out.println("Response == " + conn.getResponseCode());
            try
            {
                
                BufferedReader br;
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                System.out.println("Not Successfull");
                java.io.InputStream errorstream = conn.getErrorStream();
                br = new BufferedReader(new InputStreamReader(errorstream));
            } else {
                System.out.println("Successfull");
                br = new BufferedReader(new InputStreamReader(
                        (conn.getInputStream())));
            }
            System.out.println("New Response ");

            
            String output;
            //System.out.println("Output from Server .... \n");
            String result = br.lines().collect(joining(lineSeparator()));
            System.out.println("Response from server " + result);

            respMsg = gson.fromJson(result, PaymentResponseMessage.class);
            System.out.println("Iso Source is not null");

            if (respMsg != null) {
                System.out.println("Resp Message is null");
                System.out.println("Response Code " + respMsg.getCode());
                switch (respMsg.getCode()) {
                    case "200":
                        this.isoMsg.set(39, "00");
                        //this.isoMsg.set(56, respMsg.getMessage());
                        break;
                    case "403":
                        if (respMsg.getUnique_code().equals("403-3")) {
                            this.isoMsg.set(39, "51");
                            //this.isoMsg.set(56, respMsg.getMessage());
                        } else {
                            this.isoMsg.set(39, "96");
                            //this.isoMsg.set(56, respMsg.getMessage());
                        }
                        break;
                    default:
                        this.isoMsg.set(39, "96");
                        //this.isoMsg.set(56, respMsg.getMessage());
                        break;
                }
                this.isoRespMsg = this.isoMsg;
            }
            conn.disconnect();
            }
            catch(Exception ex)
            {
                this.isoMsg.set(39, "96");
                this.isoRespMsg = this.isoMsg;
            }
            finally
            {
                this.countDown.countDown();
                return;
            }
            

        } catch (MalformedURLException e) {

            this.isoMsg.set(39, "96");
            this.isoRespMsg = this.isoMsg;

            System.out.println("Response Error I/O " + this.isoMsg.toString());
            e.printStackTrace();

        } catch (IOException e) {

             this.isoMsg.set(39, "96");
             this.isoRespMsg = this.isoMsg;

        } catch (ISOException ex) {
            this.isoMsg.set(39, "96");
            this.isoRespMsg = this.isoMsg;
            System.out.println("Response Error ISO " + this.isoMsg.toString());
        } finally {

            this.countDown.countDown();
        }

    }

    private String BuildField90(ISOMsg srcMsg) {
        try {
            String _MessageType = srcMsg.getMTI();
            String _OriginalSTAN = srcMsg.getString(11);
            String _xmissionDateTime = srcMsg.getString(7);

            //String MessageType = SmartObject.leftPad(_MessageType.substring(0, 2), 4, "0");
            String OriginalSTAN = SmartObject.leftPad(_OriginalSTAN, 6, "0");
            String xmissionDateTime = SmartObject.leftPad(_xmissionDateTime, 10, "0");
            String AcqInstCode = srcMsg.hasField(32) ? SmartObject.leftPad(srcMsg.getString(32), 11, "0") : SmartObject.leftPad("", 11, "0");
            String FwdInstCode = srcMsg.hasField(33) ? SmartObject.leftPad(srcMsg.getString(33), 11, "0") : SmartObject.leftPad("", 11, "0");

            String RespField90 = null;

            RespField90 = _MessageType + OriginalSTAN + xmissionDateTime + AcqInstCode + FwdInstCode;

            return RespField90;
        } catch (ISOException ex) {
            java.util.logging.Logger.getLogger(ISO_RequestListener.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
