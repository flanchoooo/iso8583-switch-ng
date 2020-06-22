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

public class ReversalProcessor implements Runnable {

    private ISOMsg isoMsg;
    private ISOSource isoSrc;
    public ReversalProcessor(ISOMsg isoMsg, ISOSource isoSrc)
    {
        this.isoMsg = isoMsg;
        this.isoSrc = isoSrc;
                
    }

    @Override
    public void run() {
        ResourceBundle rb = ResourceBundle.getBundle("dist.cfg.config");
        String api_endpoint = rb.getString("api_endpoint");
        String reversal_api = rb.getString("reverse_payment_api");
        String user_name = rb.getString("user_name");
        String password = rb.getString("password");
        String api_key = rb.getString("api_key");
        System.out.println("End point " + api_endpoint);
        System.out.println("Url: URL Reversal API" + reversal_api);

        Your_request reqMsg = new Your_request();
        //String field90 = BuildField90(this.isoMsg);
        reqMsg.setClient_reference(this.isoMsg.getString(90));

        Gson gson = new GsonBuilder().serializeNulls().create();
        String jsonInString = gson.toJson(reqMsg); //Convert object to Json string
        System.out.println("Json Converted Reversal" + jsonInString);
        PaymentResponseMessage respMsg = new PaymentResponseMessage();
        try {
             this.isoMsg.setResponseMTI();
            //About to call endpoint
            URL url = new URL(api_endpoint + reversal_api);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-GB;     rv:1.9.2.13) Gecko/20101203 Firefox/3.6.13 (.NET CLR 3.5.30729)");
            conn.setRequestProperty("Username", user_name);
            conn.setRequestProperty("Password", password);
            conn.setRequestProperty("API-Key", api_key);
            
            conn.connect();
            OutputStream os = conn.getOutputStream();
            os.write(jsonInString.getBytes());
            os.flush();
            System.out.println("Response == " + conn.getResponseCode());
            
            BufferedReader br;
            if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                System.out.println("Not Successfull Reversal");
                java.io.InputStream errorstream = conn.getErrorStream();
                br = new BufferedReader(new InputStreamReader(errorstream));
            } else {
                System.out.println("Successfull Reversal");
                br = new BufferedReader(new InputStreamReader(
                        (conn.getInputStream())));
            }
            System.out.println("New Response Reversal");
            
            String output;
            
            String result = br.lines().collect(joining(lineSeparator()));
            
            System.out.println("Response from server " + result);

            respMsg = gson.fromJson(result, PaymentResponseMessage.class);
            System.out.println("Iso Source is not null");
            if (respMsg != null) {
                System.out.println("Resp Message is null");
                System.out.println("Response Code " + respMsg.getCode());
                switch (respMsg.getCode()) {
                    case "201":
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
                this.isoSrc.send(isoMsg);
            }
            conn.disconnect();

        } catch (MalformedURLException e) {

            e.printStackTrace();
            this.isoMsg.set(39, "96");
            try {
                this.isoSrc.send(isoMsg);
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(ReversalProcessor.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ISOException ex) {
                java.util.logging.Logger.getLogger(ReversalProcessor.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Response Error Malfunction " + this.isoMsg.toString());

        } catch (IOException e) {

            e.printStackTrace();
            this.isoMsg.set(39, "96");
            try {
                this.isoSrc.send(isoMsg);
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(ReversalProcessor.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ISOException ex) {
                java.util.logging.Logger.getLogger(ReversalProcessor.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Response Error I/O " + this.isoMsg.toString());

        } catch (ISOException ex) {
            ex.printStackTrace();
            this.isoMsg.set(39, "96");
            try {
                this.isoSrc.send(isoMsg);
            } catch (IOException ex1) {
                java.util.logging.Logger.getLogger(ReversalProcessor.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (ISOException ex1) {
                java.util.logging.Logger.getLogger(ReversalProcessor.class.getName()).log(Level.SEVERE, null, ex1);
            }
            System.out.println("Response Error ISO " + this.isoMsg.toString());
            java.util.logging.Logger.getLogger(HostPorcessor.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    private String BuildField90(ISOMsg srcMsg) {
        try {
            String _MessageType = srcMsg.getMTI();
            String _OriginalSTAN = srcMsg.getString(11);
            String _xmissionDateTime = srcMsg.getString(7);

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
