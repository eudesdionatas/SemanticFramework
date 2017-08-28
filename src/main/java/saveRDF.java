import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import org.apache.jena.query.DatasetAccessor;
import org.apache.jena.query.DatasetAccessorFactory;
import org.apache.jena.rdf.model.*;
import org.apache.jena.vocabulary.VCARD;


/**
 * Created by Eudes on 21/08/2017.
 */

public class saveRDF {

    //Variável para manter as configurações de funcionamento do framework
    Config config = null;

    //Arquivo para armazenar as propriedades de configuração de funcionamento do framework
    Properties properties = null;

    //Arquivo configurado com o caminho da pasta do usuário para gravar e ler as propriedades
    File file = new File(System.getProperty("user.home"), "config.properties");

    //Variável com o endereço do endpoint
    public static final String URI = null;

    //Variável com o comentário a ser enriquecido semanticamente
    private String comment = null;

    //Método para configurar as propriedades dadas pelo usuário
    public void setConfig(){
        config = new Config();
        properties = config.loadProperties();
    }

    //Método para salvar um RDF
    public void saveRDF() {

        String endPointURI = properties.getProperty("endPointURI");
        String vocabURI = properties.getProperty("vocabURI");
        DatasetAccessor datasetAccessor = DatasetAccessorFactory.createHTTP(endPointURI);


        try {
            //Salva o arquivo na variável externa file que tem o caminho da pasta do usuário
            FileOutputStream fileOut = new FileOutputStream(file);
            properties.store(fileOut, "This file stores the URIs to the suitable work of framework");
            fileOut.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Model createModel() {
        Model model = ModelFactory.createDefaultModel();
        model.setNsPrefix("geo", properties.getProperty("GEO_URI"));
        Property longitude = ResourceFactory.createProperty(properties.getProperty("GEO_URI"), properties.getProperty("GEOProperty"));

        return model;
    }

  /*  public void asdf(){
        // some definitions
        String GEO_URI = properties.getProperty("GEO_URI");
        String latitude = "12.4565478";

        // create an empty Model
        Model model = ModelFactory.createDefaultModel();

        // create the resource
        Resource local1 = model.createResource(GEO_URI);

        // add the property
        johnSmith.addProperty(VCARD.FN, fullName);
    }

    public static void main(){

        Model model = createModel();
        model.createResource(properties.getProperty("GEO_URI"));

        datasetAccessor.add(GRAPH_URI, model);

        //model.write(System.out);

        return ResponseEntity.ok().build();

    }*/
}
