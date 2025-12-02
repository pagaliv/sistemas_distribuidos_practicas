package ejercicios_practicar_examen.modelo_C_2;

import java.io.File;


import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class gestorXML {
    private static String nombre  = "metadatos.xml";
    
    public void iniciar(){
        File documento= new File(nombre);
        if(!documento.exists()){
            try{
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                Document doc = dbf.newDocumentBuilder().newDocument();
                Element raiz = doc.createElement("ficheros");
            }catch(IllegalAccessError iAE){
                iAE.printStackTrace();

            }catch(ParserConfigurationException pce){
                pce.printStackTrace();
            }
            
            
        }
    }

    
}
