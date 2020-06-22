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
import org.jpos.transaction.Context;
import org.jpos.transaction.TransactionParticipant;
import main.java.com.helpers.Constants;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOSource;
import static org.jpos.transaction.TransactionConstants.PREPARED;
/**
 *
 * @author ietechadmin
 */
public class NetworkManagementResponse implements TransactionParticipant{
    @Override
    public int prepare(long l, Serializable serializable) {
        Context ctx = (Context)serializable;
        //Set Response for Network Check
        ISOMsg respMsg = (ISOMsg)ctx.get(Constants.REQUEST_KEY);
        System.out.println("ddddddddddddddddd");
        try {
            respMsg.setResponseMTI();
        } catch (ISOException ex) {
            
            Logger.getLogger(NetworkManagementResponse.class.getName()).log(Level.SEVERE, null, ex);
        }
        respMsg.set(39,"00");
        ISOSource requester = (ISOSource) ((Context) ctx).get(Constants.RESOURCE_KEY);
        try {
            requester.send(respMsg);
        } catch (IOException ex) {
            Logger.getLogger(NetworkManagementResponse.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ISOException ex) {
            Logger.getLogger(NetworkManagementResponse.class.getName()).log(Level.SEVERE, null, ex);
        }
           
        //ctx.put(Constants.RESPONSE_KEY,respMsg);
        return PREPARED | NO_JOIN;
    }

    @Override
    public void commit(long l, Serializable serializable) {

    }

    @Override
    public void abort(long l, Serializable serializable) {
    }
}
