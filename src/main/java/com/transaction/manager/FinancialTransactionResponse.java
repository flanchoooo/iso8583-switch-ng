/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.com.transaction.manager;

import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jpos.iso.ISOMsg;
import org.jpos.transaction.AbortParticipant;
import org.jpos.transaction.Context;
import main.java.com.helpers.Constants;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOSource;
import org.jpos.util.NameRegistrar;

/**
 *
 * @author ietechadmin
 */
public class FinancialTransactionResponse implements AbortParticipant {
    private ChannelManager channelManager;
    @Override
  public void abort(long id, Serializable context) { 
        try {
            sendMessage((Context)context);
        } catch (ISOException ex) {
            Logger.getLogger(FinancialTransactionResponse.class.getName()).log(Level.SEVERE, null, ex);
        }
  }
  @Override
  public void commit(long id, Serializable context) { 
//   try {
//        sendMessage((Context)context);
//    } catch (ISOException ex) {
//        Logger.getLogger(FinancialTransactionResponse.class.getName()).log(Level.SEVERE, null, ex);
//    }
  }
  @Override
  public int prepare(long id, Serializable context) {
      
      try {
            Context ctx = (Context)context;
            ISOMsg respMsg = (ISOMsg)ctx.get(Constants.RESPONSE_KEY), 
              respMsgmfb =(ISOMsg)ctx.get(Constants.RESPONSE_KEY_MFB);
            
            
            if(respMsgmfb == null){
                try{
            
                    ISOSource requester = (ISOSource) ((Context) context).get(Constants.RESOURCE_KEY);
                    ISOMsg reqMsg = (ISOMsg) ((Context) context).get(Constants.REQUEST_KEY);

                    //reqMsgISOMsg respMsgresp = (ISOMsg) reqMsg.clone();
                    
                    reqMsg.setResponseMTI();
                    reqMsg.set(39,"01");
                    requester.send(reqMsg);
                }catch(ISOException e){
                    e.printStackTrace();
                }catch(IOException e){
                     e.printStackTrace();
                }
            }
            if(!respMsgmfb.getString(39).equals("00")){
                ISOSource requester = (ISOSource) ((Context) context).get(Constants.RESOURCE_KEY);
                requester.send(respMsgmfb);

                return PREPARED;
            }
            if(respMsg == null){
                   ISOMsg msgResp = (ISOMsg)ctx.get(Constants.REQUEST_KEY);
                   if(msgResp.getString(3).contains("31")){

                       ISOSource requester = (ISOSource) ((Context) context).get(Constants.RESOURCE_KEY);
                       requester.send(respMsgmfb);

                       return PREPARED;
                   }
                   else{
                        if(respMsgmfb.getString(39).equals("00")){
                            channelManager =  ((ChannelManager) NameRegistrar.getIfExists("manager"));//.get("jpos-bank-adaptor"));
                            ISOMsg reqMsg =(ISOMsg) ((Context) context).get(Constants.REQUEST_KEY);
                            reqMsg.setMTI("0420");
                            channelManager.sendRequest(reqMsg, "mfbhandback", "jpos-client-mux");

                            ISOMsg reversalMsg = channelManager.getResponse("mfbhandback");

                            System.out.println("Request Recieved Point mfb 1 " + new String(reversalMsg.pack()));

                            try {
                                //reversal_cbs.setMTI("0420");                 
                               ISOSource source = (ISOSource)ctx.get(Constants.RESOURCE_KEY);
                               source.send(reversalMsg);

                            } catch (ISOException ex) {
                                Logger.getLogger(FinancialTransactionResponse.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            return PREPARED;
                        }
                   }
             }
            if(respMsgmfb.getString(39).equals("00") && !respMsg.getString(39).equals("00")){
                channelManager =  ((ChannelManager) NameRegistrar.getIfExists("manager"));//.get("jpos-bank-adaptor"));
                ISOMsg reqMsg = (ISOMsg) ((Context) context).get(Constants.REQUEST_KEY);
                reqMsg.setMTI("0420");
                channelManager.sendRequest(reqMsg, "mfbhandback", "jpos-client-mux");

                ISOMsg reversalMsg = channelManager.getResponse("mfbhandback");




                System.out.println("Request Recieved Point mfb 1 " + new String(reversalMsg.pack()));

                try {
                    //reversal_cbs.setMTI("0420");                 
                    ISOSource source = (ISOSource)ctx.get(Constants.RESOURCE_KEY);



                    source.send(reversalMsg);




                } catch (ISOException ex) {
                    Logger.getLogger(FinancialTransactionResponse.class.getName()).log(Level.SEVERE, null, ex);
                }
            
                return PREPARED;
            }
           
            if(respMsgmfb.getString(39).equals("00") && respMsg.getString(39).equals("00")){
                ISOSource requester = (ISOSource) ((Context) context).get(Constants.RESOURCE_KEY);
                requester.send(respMsgmfb);
                return PREPARED;
             }
            
   
            return PREPARED;
          } catch (Exception e) {
            e.printStackTrace();
            return ABORTED;
          }
      
      
      

  }
  private void sendMessage(Context context) throws ISOException{
        ISOSource source = (ISOSource)context.get(Constants.RESOURCE_KEY);
        ISOMsg msgResp = (ISOMsg)context.get(Constants.RESPONSE_KEY);
        try {
            source.send(msgResp);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ISOException e) {
            e.printStackTrace();
        }
    }
  @Override
  public int prepareForAbort(long id, Serializable context) {
    return ABORTED;
  }
}
