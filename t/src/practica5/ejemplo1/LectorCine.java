package practica5.ejemplo1;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
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
            }catch(ParserConfigurationException e){
                e.printStackTrace();
            }catch(SAXException se){
                se.printStackTrace();
            }catch(IOException ioe){
                
            }
        

    }
    
}
