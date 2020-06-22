/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.com.helpers;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
/**
 *
 * @author ietechadmin
 */


public class XMLReader {
    private String _PAN;
    public String getPAN(){
        return _PAN;
    }
    private String _FromAcct;
    public String getFromAcct(){
        return _FromAcct;
    }
    public static String getCharacterDataFromElement(Element e) {
    Node child = e.getFirstChild();
    if (child instanceof CharacterData) {
       CharacterData cd = (CharacterData) child;
       return cd.getData();
    }
    return "?";
  }
    public void readXML(String xmlRecords) throws ParserConfigurationException, IOException {
     DocumentBuilderFactory dbf =
            DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        InputSource is = new InputSource();
        is.setCharacterStream(new StringReader(xmlRecords));

        Document doc = null;
        try {
            doc = db.parse(is);
        } catch (SAXException ex) {
            Logger.getLogger(XMLReader.class.getName()).log(Level.SEVERE, null, ex);
        }
       String totalAmountToBePaid = doc.getElementsByTagName("PAN").item(0).getTextContent();
        NodeList nodes = doc.getElementsByTagName("TransactionRecord");

        Element element = (Element) nodes.item(0);

        NodeList name = element.getElementsByTagName("PAN");
        Element line = (Element) name.item(0);
        System.out.println("Name: " + getCharacterDataFromElement(line));
        
        this._PAN = getCharacterDataFromElement(line);
        
        NodeList title = element.getElementsByTagName("FromAcct");
        line = (Element) title.item(0);
        System.out.println("Title: " + getCharacterDataFromElement(line));
        
        this._FromAcct = getCharacterDataFromElement(line);
    }
}
