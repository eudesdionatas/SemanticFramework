package tests;


import org.apache.jena.rdf.model.Model;
import semanticframework.*;

/**
 * Created by Eudes on 02/09/2017.
 */
public class TesteConfigURIs {

    TesteConfigURIs(){}

    public static void main(String[] args) {

        //ConfigURIs config = new ConfigURIs();
        ConfigURIsXML config = new ConfigURIsXML();

//        config.setEndpointURI("http://localhost");
//        config.setVocabURI("geo", "http://www.w3.org/2003/01/geo/wgs84_pos#");
//        config.setVocabURI("rev", "http://purl.org/stuff/rev#");
//        config.setVocabURI("Schema.org", "http://schema.org/");
//        config.setProperty("lat", "geo");
//        config.setProperty("long","geo");
//        config.setProperty("comment", "rev");
//        config.setProperty("rating", "rev");
//        config.setProperty("name", "Schema.org");
        //config.saveConfig();
        Model model = config.createModelByPreviousConfig();
        model.createResource()
    }
}
