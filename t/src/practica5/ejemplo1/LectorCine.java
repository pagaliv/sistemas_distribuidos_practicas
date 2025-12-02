package practica5.ejemplo1;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;

public class LectorCine {
    public static void main(String args[])
    {
        try{
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(new File("peliculas.xml"));
                //obtener el nodo raiz
                Element peliculas= doc.getDocumentElement();
                //obtener todas las pelis
                NodeList pelis = peliculas.getElementsByTagName("pelicula");
                
                
            }catch(ParserConfigurationException e){
                e.printStackTrace();
            }catch(SAXException se){
                se.printStackTrace();
            }catch(IOException ioe){
                ioe.printStackTrace();
            }
        

    }
    
}
