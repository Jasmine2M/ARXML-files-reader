
//package com.mycompany.assignment;

import java.io.*;

import java.util.ArrayList;
import java.util.Collections;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.NodeList;  
import org.w3c.dom.Node;  
import org.w3c.dom.Element; 


public class Assignment {

    public static void main(String[] args) {
        try{
        String fileName = args[0];
        
       File inputFile = new File(fileName);
       
       if (!(fileName.endsWith(".arxml")) ){
           throw new NotValidAutosarFileExecption ("Invalid autosar file!");
       }
         if(inputFile.length()==0){
             throw new EmptyAutosarFileException("file is empty!");
         }
            
          //Parsing XML using Java DOM Parser
       DocumentBuilderFactory dbF = DocumentBuilderFactory.newInstance();
       DocumentBuilder dB = dbF.newDocumentBuilder();
       Document d = dB.parse(inputFile);
       d.getDocumentElement().normalize();

 
    NodeList nList = d.getElementsByTagName("CONTAINER");  
   
    ArrayList<Container> arr = new ArrayList<>();
    
    for (int i = 0; i < nList.getLength(); i++) {
        Container c = new Container();
        Node nNode = nList.item(i);
       
        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
            Element e = (Element) nNode;
            c.setUiid(e.getAttribute("UUID"));
            c.setShortName(e.getElementsByTagName("SHORT-NAME")
                                 .item(0).getTextContent());
            c.setLongName(e.getElementsByTagName("LONG-NAME")
                                 .item(0).getTextContent());
            arr.add(c);
           
        }
    }
    Collections.sort(arr);
    
    //Writing XML file
    File outputFile = new File(inputFile.getName().substring(0,inputFile.getName().indexOf('.'))+"_mod.arxml");
    FileOutputStream os = new FileOutputStream(outputFile);
    os.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n".getBytes());
    os.write("<AUTOSAR>\n".getBytes());
    for (int i=0 ; i<arr.size();i++){
        os.write(arr.get(i).toString().getBytes());
    }
    os.write("</AUTOSAR>\n".getBytes());
    
    //System.out.println(outputFile);
    }
    catch (NotValidAutosarFileExecption e) {
    System.out.println(e);
    }
    catch (Exception e){ 
    e.printStackTrace();
    }
  }

}
class Container implements Comparable <Container> {
    private String uiid;
    private String shortName;
    private String longName;

    public String getUiid() {
        return uiid;
    }

    public void setUiid(String uiid) {
        this.uiid = uiid;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }
    @Override
    public String toString(){
        return "<CONTAINER UUID=\""+this.getUiid()+"\">\n" +
"<SHORT-NAME>"+this.shortName+"</SHORT-NAME>\n" +
"<LONG-NAME>"+this.longName+"</LONG-NAME>\n" +
"</CONTAINER>\n";
    }
     @Override
    public int compareTo(Container o) {
       return this.shortName .compareTo (((Container)o).shortName);
    }
    
}
class NotValidAutosarFileExecption extends Exception{
    public NotValidAutosarFileExecption(String message){
        super(message);
    }
}
class EmptyAutosarFileException extends ArithmeticException{
    public EmptyAutosarFileException(String message){
     super(message);   
    }
}


