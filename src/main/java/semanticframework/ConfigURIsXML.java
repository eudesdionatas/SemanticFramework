package semanticframework;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Eudes on 02/09/2017.
 */
public class ConfigURIsXML {

    // Hashmap com as configurações de URIs
    HashMap<String, String> configuration = new HashMap<String, String>();

    // Manipulador de arquivos XML
    public static LoadWriteXMLFile xmlFile = new LoadWriteXMLFile();

    //Arquivo configurado com o caminho da pasta do usuário para gravar e ler as propriedades
    public static File file = new File(System.getProperty("user.home"), "config.xml");

    public void setEndpointURI(String endPointURI) {
        configuration.put("endpoint", endPointURI);
    }

    public void setVocabURI(String prefix, String vocabURI) {
        configuration.put(prefix, vocabURI);
    }

    public void saveConfig() {
        xmlFile.write(configuration);
    }

    //Método para criar um modelo de acordo com as configurações do arquivo XML
    public Model createModelByPreviousConfig() {
        this.configuration = xmlFile.getConfigurationByXML(file);
        Model model = ModelFactory.createDefaultModel();
        for (Map.Entry<String, String> myMap : configuration.entrySet()) {
            if(myMap.getValue().contains("http://") && myMap.getKey() != "endpoint"){
                model.setNsPrefix(myMap.getKey(), myMap.getValue());
            }
        }
        System.out.println(model.getNsPrefixMap());
        return model;
    }


    public void setProperty(String property, String vocabulary) {
        configuration.put(property,vocabulary);
    }
}
