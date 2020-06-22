/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.com.helpers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.ResourceBundle;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys; 
import javax.xml.transform.Transformer; 
import javax.xml.transform.TransformerConfigurationException; 
import javax.xml.transform.TransformerFactory; 
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXTransformerFactory; 
import javax.xml.transform.sax.TransformerHandler; 
import javax.xml.transform.stream.StreamResult; 
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 *
 * @author ietechadmin
 */
public class XmlBuilder {
    
//    public static void main(String[] args) throws Exception{
//        Document doc = buildSecurityUserXml("5061093456789078", "01124587", "6090100137", "203030030303","test","455","4444","20151010");
//        TransformerFactory tf = TransformerFactory.newInstance();
//        Transformer transformer;            
//        transformer = tf.newTransformer();
//        
//        StringWriter writer = new StringWriter();
//        transformer.transform(new DOMSource(doc), new StreamResult(writer));
//        String requestXML = writer.getBuffer().toString();  
//        ResourceBundle rb = ResourceBundle.getBundle("dist.cfg.config");
//            String soap_url = rb.getString("soap_url");
//        String retMessage = postXML( soap_url,requestXML);
//        XMLReader XMLReader = new XMLReader();
//        XMLReader.readXML(retMessage);
//        System.out.println("Hhhhhhhhhhhhhhhhhhhhhhhhh" +XMLReader.getFromAcct());
//        
//    }
//   
    public static Document buildSecurityUserXml(String PAN, String TransRefNo, String DebitAccountNumber ,
            String CreditAccountNumber,String Narr, String TranCode, String amt, String value_date) throws Exception
    {
        System.out.println("Value Date from Build XML "+value_date);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.newDocument();
      
        //Creating the Root Element         
         Element soapEnv = (Element) doc.createElement("soap:Envelope");
                soapEnv.setAttribute("xmlns:soap", "http://schemas.xmlsoap.org/soap/envelope/");
                soapEnv.setAttribute("xmlns:xsd", "http://www.w3.org/2001/XMLSchema");
                soapEnv.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
        
        
        doc.appendChild(soapEnv);
       
        Element nodeHeader = doc.createElement("soap:Header");
        soapEnv.appendChild(nodeHeader);
        
        Element nodeBody = doc.createElement("soap:Body");
        Element nodeGetAssessment = doc.createElement("TransactionPostingISO");
        nodeGetAssessment.setAttribute("xmlns", "http://tempuri.org/");
        
        Element nodeYear = doc.createElement("PAN");
        nodeYear.appendChild(doc.createTextNode(PAN));
        nodeGetAssessment.appendChild(nodeYear);
        
        Element nodeSerial = doc.createElement("TransRefNo");
        nodeSerial.appendChild(doc.createTextNode(TransRefNo));
        nodeGetAssessment.appendChild(nodeSerial);      
        
        Element nodeNumber = doc.createElement("DebitAccountNumber");
        nodeNumber.appendChild(doc.createTextNode(DebitAccountNumber));
        nodeGetAssessment.appendChild(nodeNumber);   
                
        Element crdaccount = doc.createElement("CreditAccountNumber");
        crdaccount.appendChild(doc.createTextNode(CreditAccountNumber));
        nodeGetAssessment.appendChild(crdaccount);

        Element narration = doc.createElement("Narration");
        narration.appendChild(doc.createTextNode(Narr));
        nodeGetAssessment.appendChild(narration);
        
        Element tran_code = doc.createElement("TranCode");
        tran_code.appendChild(doc.createTextNode(TranCode));
        nodeGetAssessment.appendChild(tran_code);       
        
        
        
        Element amount = doc.createElement("Amount");
        amount.appendChild(doc.createTextNode(amt));
        nodeGetAssessment.appendChild(amount);
        
        
        Element valuedate = doc.createElement("ValueDate");
        valuedate.appendChild(doc.createTextNode(value_date));
        nodeGetAssessment.appendChild(valuedate);
        
        nodeBody.appendChild(nodeGetAssessment);
        soapEnv.appendChild(nodeBody);
              
        return doc;
    
    }
    
    
    
   //Post XML
   public static String postXML(String soapurl, String xml) throws Exception {
       System.out.println("Url: " +soapurl);
    HttpURLConnection connection = null;
    OutputStreamWriter wout = null;
    BufferedReader br = null;
    String line;
    //L.info(">>>> " + xml);
    try 
    {
      URL u;
      u=  new URL(soapurl);
      URLConnection uc = u.openConnection();
//      HttpURLConnection urlc = (HttpURLConnection) uc;
////TrustModifier.relaxHostChecking(urlc);
//      urlc.setUseCaches(false);
//      urlc.setRequestProperty("User-Agent", "Polaris/1.4.1");
//      urlc.setRequestProperty("Connection", "Keep-Alive");
////      urlc.setConnectTimeout(5000);
//      urlc.setConnectTimeout(7000);
//      urlc.setReadTimeout(6 * 1000);
      //URLConnection uc = u.openConnection();
      connection = (HttpURLConnection) uc;
//      connection.setConnectTimeout(5000);
      connection.setConnectTimeout(7000);
      connection.setReadTimeout(60000);
      connection.setRequestProperty("Content-Length", String.valueOf(xml.length()));
      connection.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
      connection.setRequestProperty("SOAPAction", "http://tempuri.org/TransactionPostingISO");
      
      connection.setRequestMethod("POST");
      connection.setDoOutput(true);
      connection.setDoInput(true);
      wout = new OutputStreamWriter(connection.getOutputStream());      
      wout.write(xml);   
      
      wout.close();
      
      
// Read the response XML document
      connection.connect();
      System.out.println("http connection status :"+ connection.getResponseMessage());
      StringBuilder sb = new StringBuilder();
      br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      while ((line = br.readLine()) != null) 
      {
        sb.append(line);
      }
      System.out.println(sb.toString());
      return sb.toString();
    } 
    finally 
    {
      //close(wout);
      //close(br);
        //wout.flush();
        //wout.close();
        br.close();
    }

  }
}
//DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
//    InputSource is = new InputSource();
//    is.setCharacterStream(new StringReader(xmlRecords));
//
//    Document doc = db.parse(is);
//    NodeList nodes = doc.getElementsByTagName("employee");
//
//    for (int i = 0; i < nodes.getLength(); i++) {
//      Element element = (Element) nodes.item(i);
//
//      NodeList name = element.getElementsByTagName("name");
//      Element line = (Element) name.item(0);
//      System.out.println("Name: " + getCharacterDataFromElement(line));
//
//      NodeList title = element.getElementsByTagName("title");
//      line = (Element) title.item(0);
//      System.out.println("Title: " + getCharacterDataFromElement(line));
//    }
//
//  }
//
//  public static String getCharacterDataFromElement(Element e) {
//    Node child = e.getFirstChild();
//    if (child instanceof CharacterData) {
//      CharacterData cd = (CharacterData) child;
//      return cd.getData();
//    }
//    return "";
//  }
//}