/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.AgustRuiz.RecommenderSystem;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;

/**
 * XML Handler
 * @author Agustin Ruiz Linares <arl00029@red.ujaen.es>
 */
public class XMLConnectionHandler {

    private String path;
    private Document doc;
    private Boolean isOk;

    public XMLConnectionHandler(String path) {
        this.path = path;
        try {
            File fXmlFile = new File(this.path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            this.doc = dBuilder.parse(fXmlFile);
            this.doc.getDocumentElement().normalize();
            this.isOk = true;
        } catch (Exception e) {
            System.err.println("Fatal error: Can't read " + this.path);
            this.isOk = false;
        }
    }
    
    //public ReadDbConnection()
    
    public String getNode(String nodeName){
        if(this.isOk)
            return this.doc.getElementsByTagName(nodeName).item(0).getTextContent();
        else
            return "";
    }
    
    public Boolean isOK(){
        return this.isOk;
    }

}
