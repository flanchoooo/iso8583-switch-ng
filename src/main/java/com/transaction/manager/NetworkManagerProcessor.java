/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.com.transaction.manager;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOSource;

/**
 *
 * @author ietechadmin
 */
public class NetworkManagerProcessor implements Runnable {

    private ISOMsg isoMsg;
    private ISOSource isoSrc;
    
    public NetworkManagerProcessor(ISOMsg isoMsg, ISOSource isoSrc){
        this.isoMsg = isoMsg;
        this.isoSrc = isoSrc;
    }
    @Override
    public void run() {
        try {
            this.isoMsg.setResponseMTI();
            this.isoMsg.set(39,"00");
            this.isoSrc.send(this.isoMsg);
        } catch (ISOException ex) {
            Logger.getLogger(NetworkManagerProcessor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(NetworkManagerProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
