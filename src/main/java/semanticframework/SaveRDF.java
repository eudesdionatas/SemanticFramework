package semanticframework;

import org.apache.jena.rdf.model.Model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Created by Eudes on 04/09/2017.
 */
public class SaveRDF {


    public static void saveModel(Model model){
        //Arquivo configurado com o caminho da pasta do usuário para gravar e ler as propriedades
        File file = new File(System.getProperty("user.home"), "lugares.xml");
        try{
            OutputStream out = new FileOutputStream(file);
            model.write(out, "RDF/XML-ABBREV");
//            System.out.println("adfgsfg");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
