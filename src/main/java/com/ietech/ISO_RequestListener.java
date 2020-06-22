/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.com.ietech;





import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.jpos.core.Configurable;
import org.jpos.core.Configuration;
import org.jpos.core.ConfigurationException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISORequestListener;
import org.jpos.iso.ISOSource;
import org.jpos.iso.ISOUtil;
import org.jpos.q2.Q2;
import org.jpos.space.Space;
import org.jpos.transaction.Context;
import main.java.com.helpers.Constants;
import main.java.com.helpers.SmartObject;
import main.java.com.helpers.XMLReader;
import main.java.com.helpers.XmlBuilder;
import static main.java.com.helpers.XmlBuilder.postXML;
import main.java.com.transaction.manager.*;
import org.jpos.iso.ISOException;
import org.jpos.space.SpaceFactory;
import org.jpos.util.NameRegistrar;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;


/**
 *
 * @author ietechadmin
 */
public class ISO_RequestListener implements ISORequestListener {
    
    private String queueName;
    private Space<String, Context> sp;
    private Long timeout = 120000L;
    private Configuration cfg;
    public ISO_RequestListener(){
        super();
    }
    
    public static void startQ2(){
      Q2 q2 = new Q2();
      q2.start();
      System.out.println("Starting Deploy");
    }
    
    public static void main(String[] args){
        
        startQ2();
        ISOUtil.sleep(5000);
    }
     @Override
    public boolean process (ISOSource isoSrc, ISOMsg isoMsg ) {
       
        
        try {
            System.out.println("Get MTI Value " + isoMsg.getMTI());
            if(isoMsg.getMTI().equals("0800"))
            {

                Executor executor = Executors.newSingleThreadExecutor();
                executor.execute(new NetworkManagerProcessor(isoMsg, isoSrc));
                
                return true;
            }
        } catch (ISOException ex) {
           System.out.println("Echo Exception " + ex);
        } 
        
        try 
        {
            if(isoMsg.getMTI().equals("0200")) // Balanace Enquiry
            {
                if(isoMsg.getString(3).contains("31") || isoMsg.getString(3).contains("38")){
                    
                    Executor executor = Executors.newSingleThreadExecutor();
                    executor.execute(new InquiryHostProcessor(isoMsg, isoSrc));
                    return true;
                }
            }
        } catch (ISOException ex) {
            Logger.getLogger(ISO_RequestListener.class.getName()).log(Level.SEVERE, null, ex);
        }
        try 
        {
            //Client Sends Reversal
            if(isoMsg.getMTI().equals("0420"))
            {
                ISOMsg MfbHostRequestReversal = (ISOMsg) isoMsg.clone();
              
                Executor executor = Executors.newSingleThreadExecutor();
                                
                ReversalProcessor worker = new ReversalProcessor(MfbHostRequestReversal, isoSrc);
                               
                executor.execute(worker);
                
                System.out.println("Awaiting Response For Reversal");
                
              
              // WaitForBothRequestToComplete(isoSrc,worker,countDownLatchReversal);
               
               return true;
                
            }
           
            if(isoMsg.getMTI().equals("0200"))
            {
                
                ISOMsg MfbHostRequest = (ISOMsg) isoMsg.clone();
                java.util.concurrent.CountDownLatch  countDownLatch = new java.util.concurrent.CountDownLatch(2);
                System.out.println("Awaiting Response");

                Executor executor = Executors.newSingleThreadExecutor();
                HostPorcessor worker = new HostPorcessor(MfbHostRequest, isoSrc, countDownLatch);
                executor.execute(worker);
                WaitForBothRequestToComplete(isoSrc,worker,countDownLatch);
               return true;
               
                

         }
        } catch (ISOException ex) {
            Logger.getLogger(ISO_RequestListener.class.getName()).log(Level.SEVERE, null, ex);
        }
        
           
           return true;           

    }

    private void WaitForBothRequestToComplete(ISOSource oSrc, HostPorcessor worker,
           CountDownLatch countDownLatch)
    {
        try
        {
             countDownLatch.await();
             
             System.out.println("Wait Completed");
        }
        catch(Exception ex)
        {
             System.out.println("waiting exception" + ex);
        }
       ISOMsg isoBridgeResp =  worker.getIsoRespMsg();
       System.out.println("Got here " + isoBridgeResp);
       if(isoBridgeResp != null)
       {
           ProcessRequestForResponseNotNull(isoBridgeResp,oSrc);
       }
    }
//     private void WaitForBothRequestToComplete(ISOSource oSrc, ReversalProcessor worker,
//           CountDownLatch countDownLatch)
//    {
//        try
//        {
//             countDownLatch.await();
//             
//             System.out.println("Wait Completed");
//        }
//        catch(Exception ex)
//        {
//             System.out.println("waiting exception" + ex);
//        }
//       ISOMsg isoBridgeResp =  worker.getIsoRespMsg();
//       System.out.println("Got here " + isoBridgeResp);
//       if(isoBridgeResp != null)
//       {
//           ProcessRequestForResponseNotNull(isoBridgeResp,oSrc);
//       }
//    }
    
   private void ProcessRequestForResponseNotNull(ISOMsg oBridgeResp, ISOSource oSrc) {
        try {
            System.out.println("Response Recieved " + new String(oBridgeResp.pack()));
            try 
            {
                oSrc.send(oBridgeResp);
            } catch (IOException ex) {
                Logger.getLogger(ISO_RequestListener.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ISOException ex) {
                Logger.getLogger(ISO_RequestListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ISOException ex) {
            Logger.getLogger(ISO_RequestListener.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    private String BuildField90(ISOMsg srcMsg) {
        try {
            String _MessageType=srcMsg.getMTI();
            String _OriginalSTAN = srcMsg.getString(11);
            String _xmissionDateTime = srcMsg.getString(7);
            
            
            String OriginalSTAN = SmartObject.leftPad(_OriginalSTAN, 6, "0");
            String xmissionDateTime = SmartObject.leftPad(_xmissionDateTime, 10, "0");
            String AcqInstCode = srcMsg.hasField(32) ? SmartObject.leftPad(srcMsg.getString(32), 11, "0") : SmartObject.leftPad("", 11, "0");
            String FwdInstCode = srcMsg.hasField(33) ?SmartObject.leftPad(srcMsg.getString(33), 11, "0") : SmartObject.leftPad("", 11, "0");
            
            String RespField90 = null;
            
            RespField90 = _MessageType + OriginalSTAN + xmissionDateTime + AcqInstCode + FwdInstCode;
            
            return RespField90;
        } catch (ISOException ex) {
            Logger.getLogger(ISO_RequestListener.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    
}
