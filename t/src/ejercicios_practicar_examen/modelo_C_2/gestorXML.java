package ejercicios_practicar_examen.modelo_C_2;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class gestorXML {
    private static String nombre  = "metadatos.xml";
    
    public void iniciar(){
        File documento= new File(nombre);
        if(!documento.exists()){
            try{
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                Document doc = dbf.newDocumentBuilder().newDocument();
                Element raiz = doc.createElement("ficheros");
                doc.appendChild(raiz);
                guardarDoom(doc);
                
            }catch(IllegalAccessError iAE){
                iAE.printStackTrace();

            }catch(ParserConfigurationException pce){
                pce.printStackTrace();
            }
            
            
        }
    }
    public void guardarDoom(Document doc){
        try {
            Transformer trans = TransformerFactory.newInstance().newTransformer();
            trans.transform(new DOMSource(doc), new StreamResult(new File(nombre)) );

        } catch (TransformerException e) {
            e.printStackTrace();
            
        }
    }
    public void addFichero(String nom, int tam){
        Document doc = leerDOM();
        Element raiz = doc.getDocumentElement();
        Element fichero = doc.createElement("fichero");
        Element nombre = doc.createElement("nombre");
        nombre.setTextContent(nom);
        Element tama = doc.createElement("tama");
        tama.setTextContent(String.valueOf(tam));
        fichero.appendChild(nom)
        raiz.appendChild(fichero);


    }
    public Document leerDOM(){
        try{
             DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            return dbf.newDocumentBuilder().parse(new File(nombre));
        }catch( IOException ex){
            ex.printStackTrace();
        }catch(SAXException e){
            e.printStackTrace();
        }catch(ParserConfigurationException pe){
            pe.printStackTrace();
        }
        
        };
       
        
    }

    
}
