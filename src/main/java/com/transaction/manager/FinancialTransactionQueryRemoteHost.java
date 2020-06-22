/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.com.transaction.manager;

import java.io.Serializable;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javax.xml.transform.Transformer;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.jpos.transaction.AbortParticipant;
import org.jpos.util.NameRegistrar;
import main.java.com.transaction.manager.ChannelManager;
import main.java.com.helpers.Constants;
import main.java.com.helpers.XMLReader;
import main.java.com.helpers.XmlBuilder;
import static main.java.com.helpers.XmlBuilder.postXML;
import org.jpos.iso.ISOMsg;
import org.jpos.transaction.Context;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;


/**
 *
 * @author ietechadmin
 */
public class FinancialTransactionQueryRemoteHost  implements AbortParticipant{

    private ChannelManager channelManager;
    @Override
    public int prepareForAbort(long id, Serializable context) { 
    return ABORTED;
    }

    @Override
    public int prepare(long l, Serializable srlzbl) {
        try{
            ISOMsg reqMsgmfb_Response = (ISOMsg) ((Context) srlzbl).get(Constants.REQUEST_KEY);
            
            if(reqMsgmfb_Response.getString(3).contains("31")){
                return PREPARED;
            }
            
            ISOMsg respMFB_Error =(ISOMsg)((Context) srlzbl).get(Constants.RESPONSE_KEY_MFB);
            
            if(!respMFB_Error.getString(39).equals("00")){
                return PREPARED;
            }
            
            channelManager =  ((ChannelManager) NameRegistrar.getIfExists("manager"));//.get("jpos-bank-adaptor"));
            ISOMsg reqMsg = (ISOMsg) ((Context) srlzbl).get(Constants.REQUEST_KEY);
            
            ResourceBundle rb = ResourceBundle.getBundle("dist.cfg.config");
            String soap_url = rb.getString("soap_url");
            System.out.println("Url: URL YRLRL" +soap_url);
            String PAN = reqMsg.getString(2);
            String TransRefNo = reqMsg.getString(11);
            String DebitAccountNumber = reqMsg.getString(102);
            String CreditAccountNumber = "";
            String Narr = reqMsg.getString("127.3");
            String TranCode = reqMsg.getString(3);
            Integer amt = Integer.parseInt(reqMsg.getString(4));
            String amount =  amt.toString();
            System.out.println("Amount : " +amount);
//             
            Date d = new Date();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String value_date =  dateFormat.format(d);
            System.out.println("Date : " +value_date);
            Document doc  = XmlBuilder.buildSecurityUserXml(PAN, TransRefNo,DebitAccountNumber,CreditAccountNumber, Narr, TranCode, amount, value_date);
             
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer;            
            transformer = tf.newTransformer();

            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            String requestXML = writer.getBuffer().toString();  
            System.out.println("Request : " +requestXML);
            String retMessage = postXML( soap_url,requestXML);
            System.out.println("Response : " +retMessage);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(retMessage));

            Document docresponse = null;
            try {
                doc = db.parse(is);
            } catch (Exception ex) {
                Logger.getLogger(XMLReader.class.getName()).log(Level.SEVERE, null, ex);
            }

            String Result_PAN = doc.getElementsByTagName("PAN").item(0).getTextContent();
            String Result_FROMAcct = doc.getElementsByTagName("FromAcct").item(0).getTextContent();
            
            
            System.out.println("PAN Result = " + Result_PAN);
            System.out.println("FROM ACCOUNT Result = " + Result_FROMAcct);
            
//            
////            
////            if(requestXML != null){
////                SAXBuilder saxBuilder = new SAXBuilder();
////                Document docresult;
////                doc = (Document) saxBuilder.build(new StringReader(requestXML));
////            }
//            
            //String Xmlresponse = XmlBuilder.postXML(soap_url, requestXML);
        // System.out.println("Response : " +Xmlresponse);
//            //String retMessage = postXML( "http://localhost:19724/ZenithBankWS.asmx",requestXML);
//            XMLReader XMLReader = new XMLReader();
//            XMLReader.readXML(Xmlresponse);
//            System.out.println("PAM" +XMLReader.getPAN());
//            System.out.println("FROM ACCOUNT" +XMLReader.getFromAcct());
//            
            reqMsg.set(2, Result_PAN);
            reqMsg.set(102, Result_FROMAcct);
            
            channelManager.sendRequest(reqMsg, "bankhandback", "jpos-bank-mux");
            
            ISOMsg respMsg = channelManager.getResponse("bankhandback");
            
            
            if(respMsg ==null){
                
                return PREPARED;
            }
            
            System.out.println("Request Recieved Point 1 " + new String(respMsg.pack()));
            //respMsg.setResponseMTI();
            ((Context) srlzbl).put(Constants.RESPONSE_KEY, respMsg);
            return PREPARED;
            
        }catch(Exception t){
            t.printStackTrace();
            return ABORTED;
        }
    
    }

    @Override
    public void commit(long l, Serializable srlzbl) { }

    @Override
    public void abort(long l, Serializable srlzbl) { }
    
}
