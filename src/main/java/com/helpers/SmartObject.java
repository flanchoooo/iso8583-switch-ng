/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.com.helpers;

import java.text.DecimalFormat;

/**
 *
 * @author ietechadmin
 */
public final class SmartObject {
    
//    
    public static void main(String[] args) throws Exception{
        String amount = "123"+"00";
        
        System.out.println("Rest " + leftPad(amount, 12, "0"));
       
    } 

    public static String rightPad(String input, int length, String fill){                   
        String pad = input.trim() + String.format("%"+length+"s", "").replace(" ", fill);
        return pad.substring(0, length);              
    } 
    public static String leftPad(String input, int length, String fill){            
        String pad = String.format("%"+length+"s", "").replace(" ", fill) + input.trim();
        return pad.substring(pad.length() - length, pad.length());
    }
   
}
