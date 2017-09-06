package garbage;

import org.apache.jena.rdf.model.*;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Eudes on 22/08/2017.
 */
public class TesteSalvarPrimeiroRDF {

    // some definitions
    static String personURI = "http://somewhere/JohnSmith/";
    static String givenName    = "John";
    static String familyName   = "Smith";
    static String fullName     = givenName + " " + familyName;

    private static String comment = "comment 1";

    /*    public static void iniciar(){
        // create an empty Model
        Model model = ModelFactory.createDefaultModel();

        // create the resource
        Resource johnSmith =
                model.createResource(getResourceUri())
                        .addProperty(VCARD.FN, fullName)
                        .addProperty(VCARD.N,
                                model.createResource()
                                        .addProperty(VCARD.Given, givenName)
                                        .addProperty(VCARD.Family, familyName));

        try{
            OutputStream out = new FileOutputStream("asdf.rdf");
            model.write(out, "RDF/XML-ABBREV");
        }
        catch (Exception e){

        }
    }
    */

    public static void iniciar(){


        Model model = createModel();
        addAsResourceTo(model);
        // create an empty Model
        try{
            OutputStream out = new FileOutputStream("asdf.rdf");
            model.write(out, "RDF/XML-ABBREV");
        }
        catch (Exception e){

        }
    }

    //Atribui um UUID randômico ao novo recurso a ser inserido
    private static String getResourceUri() {
        return personURI + UUID.randomUUID();
    }

    private static Model createModel() {

        ConfigURIs config = new ConfigURIs();
        Properties properties = config.loadProperties();
        Set<String> propriedades = properties.stringPropertyNames();
        Model model = ModelFactory.createDefaultModel();
        for (String p: propriedades){
            // get the property value and print it out
            model.setNsPrefix(p, properties.getProperty(p));
        }
//        model.setNsPrefix("rev", REV.getURI());
//        model.setNsPrefix("schema", SchemaOrg.getURI());
        return model;
    }

    public static Resource addAsResourceTo(Model model) {
       // String comment = !(comment == null) ? this.comment : "";


        ConfigURIs config = new ConfigURIs();
        Properties properties = config.loadProperties();
        Set<String> propriedades = properties.stringPropertyNames();

        Resource resource = ResourceFactory.createResource(properties.getProperty("Schema.org") + "Place");
        Property p = ResourceFactory.createProperty(properties.getProperty("rev"), "comment");
        return model.createResource(getResourceUri(), resource)
                .addProperty(p, "teste testes testes");
//        return model.createResource(getResourceUri(), SchemaOrg.Place)
//                .addProperty(REV.comment, "teste testes testestes");
    }

    public static void main(String args[]){
        iniciar();
    }
}
