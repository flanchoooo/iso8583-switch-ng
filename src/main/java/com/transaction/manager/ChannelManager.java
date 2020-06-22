/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.com.transaction.manager;

import java.util.logging.Level;
import java.util.logging.Logger;
import main.java.com.helpers.SmartObject;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOResponseListener;
import org.jpos.iso.MUX;
import org.jpos.q2.QBeanSupport;
import org.jpos.q2.iso.QMUX;
import org.jpos.space.Space;
import org.jpos.space.SpaceFactory;
import org.jpos.util.NameRegistrar;
import org.jpos.util.NameRegistrar.NotFoundException;

/**
 *
 * @author ietechadmin
 */
public class ChannelManager extends QBeanSupport implements ISOResponseListener{
    private long MAX_TIME_OUT;
    private Space responseMap;
    
    
    @Override
    protected void initService() throws ISOException{
        log.info("initializing Channelmanager Service");
        
        this.responseMap = SpaceFactory.getSpace(); //(MUX) NameRegistrar.get("mux.jpos-bank-mux");
        this.MAX_TIME_OUT = cfg.getLong("timeout");
        NameRegistrar.register(getName(), this);
    }
    public void sendRequest(ISOMsg reqMsg, String handback, String muxName) {
        try{
            
            MUX mux = QMUX.getMUX(muxName);
             System.out.println("Time out " +MAX_TIME_OUT );
            System.out.println("Request sent " + new String(reqMsg.pack()));
            
            if(mux.isConnected()){
                mux.request(reqMsg, MAX_TIME_OUT, this, handback);
            }
            
            
        }catch(NotFoundException e){
            e.printStackTrace();
            
        }
        catch(ISOException e){
            e.printStackTrace();
            
        }
    
    }
    public ISOMsg getResponse(String handback) throws InterruptedException {
        //Thread.sleep(MAX_TIME_OUT);
        //SmartObject.maskCardNumber(handback, handback)
        return (ISOMsg) responseMap.in(handback, MAX_TIME_OUT);
    }
    
    @Override
    public void responseReceived(ISOMsg resp, Object handBack) {
        try {
            System.out.println("Request Recieved " + new String(resp.pack()));
        } catch (ISOException ex) {
            Logger.getLogger(ChannelManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        responseMap.out(String.valueOf(handBack), resp, MAX_TIME_OUT);

    }
    @Override
    public void expired(Object handBack) {
        System.out.println("Request " + handBack + " is timeout");
    }
    
//    public ISOMsg sendMsg (ISOMsg m) throws Exception{
//        
//        return sendMsg(m, mux, MAX_TIME_OUT);
//    }
//    
//    @SuppressWarnings({"unchecked"})
//    private ISOMsg sendMsg(ISOMsg msg, MUX mux, long time) throws Exception{
//        if(mux != null){
//            long start = System.currentTimeMillis();
//            mux.request(msg, 1);
//            String msgId = msg.getString(11);
//            Space<String, ISOMsg> space = SpaceFactory.getSpace("tspace:mySpace");
//            ISOMsg responseInSpace = space.in(msgId, 10000L);
//            long duration = System.currentTimeMillis() - start;
//            log.info("Response time (ms):" + duration);
//            return responseInSpace;
//        }
//        return null;
//    }
//
//    @Override
//    public void responseReceived(ISOMsg isomsg, Object o) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public void expired(Object o) {
//        ISOResponseListener.super.expired(o); //To change body of generated methods, choose Tools | Templates.
//    }
//    
    
}
