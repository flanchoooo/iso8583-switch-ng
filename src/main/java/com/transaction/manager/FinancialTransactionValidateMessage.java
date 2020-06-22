/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.com.transaction.manager;

import java.io.IOException;
import java.io.Serializable;
import org.jpos.iso.ISOException;
import org.jpos.transaction.TransactionParticipant;
import main.java.com.helpers.Constants;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOSource;
import org.jpos.transaction.Context;

/**
 *
 * @author ietechadmin
 */
public class FinancialTransactionValidateMessage implements TransactionParticipant {

    @Override
    public int prepare(long l, Serializable srlzbl) {
    
        try{
            ISOMsg reqMsg = (ISOMsg) ((Context) srlzbl).get(Constants.REQUEST_KEY);
            return PREPARED;
//            String bit124 = reqMsg.getString(124);
//            String custId = bit124.substring(0,5).trim();
//            
//            if(custId != null  && custId.length() >0){
//                return PREPARED;
//            }
            
            
        }catch(Exception e){
            return ABORTED;
        }
       // return ABORTED;
    }

    @Override
    public void commit(long l, Serializable srlzbl) { }

    @Override
    public void abort(long l, Serializable srlzbl) {
        
        try{
            
            ISOSource requester = (ISOSource) ((Context) srlzbl).get(Constants.RESOURCE_KEY);
            ISOMsg reqMsg = (ISOMsg) ((Context) srlzbl).get(Constants.REQUEST_KEY);
            
            ISOMsg respMsg = (ISOMsg) reqMsg.clone();
            respMsg.setResponseMTI();
            respMsg.set(39,"01");
            requester.send(respMsg);
        }catch(ISOException e){
            
        }catch(IOException e){
            
        }
        
        
    }
    
}
