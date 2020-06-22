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
import main.java.com.helpers.Constants;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOSource;
import org.jpos.transaction.AbortParticipant;
import org.jpos.transaction.Context;
import static org.jpos.transaction.TransactionConstants.ABORTED;
import static org.jpos.transaction.TransactionConstants.PREPARED;
import org.jpos.util.NameRegistrar;

/**
 *
 * @author ietechadmin
 */
public class FinancialTransactionQueryRemoteHostMFB implements AbortParticipant{
    private ChannelManager channelManager;
    @Override
    public int prepareForAbort(long id, Serializable context) { 
    return ABORTED;
    }

    @Override
    public int prepare(long l, Serializable srlzbl) {
        try{
            
            channelManager =  ((ChannelManager) NameRegistrar.getIfExists("manager"));//.get("jpos-bank-adaptor"));
            ISOMsg reqMsg = (ISOMsg) ((Context) srlzbl).get(Constants.REQUEST_KEY);
            
            channelManager.sendRequest(reqMsg, "mfbhandback", "jpos-client-mux");
            
            ISOMsg respMsg = channelManager.getResponse("mfbhandback");
            
            
            if(!respMsg.getString(39).equals("00") || respMsg ==null){
               return PREPARED;
            }
            
            System.out.println("Request Recieved Point mfb 1 " + new String(respMsg.pack()));
            //respMsg.setResponseMTI();
            ((Context) srlzbl).put(Constants.RESPONSE_KEY_MFB, respMsg);
            return PREPARED;
            
        }catch(Throwable t){
            t.printStackTrace();
            return ABORTED;
        }
    
    }

    @Override
    public void commit(long l, Serializable srlzbl) { }

    @Override
    public void abort(long l, Serializable srlzbl) { 
    
     
    }
     private void sendMessage(Context context) throws ISOException{
        ISOSource source = (ISOSource)context.get(Constants.RESOURCE_KEY);
        channelManager =  ((ChannelManager) NameRegistrar.getIfExists("manager"));
        ISOMsg reqMsg = (ISOMsg)context.get(Constants.REQUEST_KEY);
        
        channelManager.sendRequest(reqMsg, "mfbhandback", "jpos-client-mux");
            
        
        try {
            ISOMsg respMsg = channelManager.getResponse("mfbhandback");
            System.out.println("Request Recieved Balance Enquiry mfb " + new String(respMsg.pack()));
            source.send(respMsg);
        } catch (ISOException e) {
            e.printStackTrace();
        }
         catch (IOException e) {
            e.printStackTrace();
        }
        catch(Throwable t){
            t.printStackTrace();
            //return ABORTED;
        }
    }
}
