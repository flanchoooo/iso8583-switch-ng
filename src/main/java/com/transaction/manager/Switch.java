/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.com.transaction.manager;

import java.io.Serializable;
import org.jpos.core.Configurable;
import org.jpos.core.Configuration;
import org.jpos.core.ConfigurationException;
import org.jpos.transaction.GroupSelector;
import main.java.com.helpers.Constants;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.transaction.Context;

/**
 *
 * @author ietechadmin
 */
public class Switch implements GroupSelector, Configurable {
    
    private Configuration cfg;

    @Override
    public String select(long l, Serializable srlzbl) {
        try{
            
            ISOMsg reqMsg = (ISOMsg) ((Context) srlzbl).get(Constants.REQUEST_KEY);
            System.out.println("initializing Channelmanager Service Selector");
            String selector = "";
        try {
            selector = cfg.get(reqMsg.getMTI());
             System.out.println("MTI " + selector);
        } catch (ISOException e) {
            e.printStackTrace();
        }
        return selector;
            
        }catch(Exception e){
            System.out.println("An error occured");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int prepare(long l, Serializable srlzbl) {
        return PREPARED | READONLY | NO_JOIN;
    }

    @Override
    public void commit(long l, Serializable srlzbl) { }

    @Override
    public void abort(long l, Serializable srlzbl) { }

    @Override
    public void setConfiguration(Configuration c) throws ConfigurationException {
        this.cfg = c;
    }
    
}
