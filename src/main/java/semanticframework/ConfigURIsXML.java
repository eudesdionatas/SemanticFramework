package semanticframework;

import org.apache.jena.rdf.model.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Eudes on 02/09/2017.
 */
public class ConfigURIsXML {

    private String value = null;

    // Hashmap com as configurações de URIs
    private HashMap<String, String> configuration = null;

    // Manipulador de arquivos XML
    public static WriteXMLFile xmlFile = new WriteXMLFile();

    //Arquivo configurado com o caminho da pasta do usuário para gravar e ler as propriedades
    public static File file = new File(System.getProperty("user.home"), "config.xml");

    public void setEndpointURI(String endPointURI) {
        configuration.put("endpoint", endPointURI);
    }

    public void setVocabURI(String prefix, String vocabURI) {
        configuration.put(prefix, vocabURI);
    }

    public void saveConfig() {
        xmlFile.writeXML(configuration);
    }

    //Método para criar um modelo de acordo com as configurações do arquivo XML
    public Model createModelByPreviousConfig() {
        this.configuration = getConfigurationByXML(file);
        Model model = ModelFactory.createDefaultModel();
        for (Map.Entry<String, String> myMap : configuration.entrySet()) {
            if(myMap.getValue().contains("http://") && myMap.getKey() != "endpoint"){
                model.setNsPrefix(myMap.getKey(), myMap.getValue());
            }
        }
//        System.out.println(model.getNsPrefixMap());
        return model;
    }

    /////////////////////////////////////////////////////////////////////////////
    //Método para fazer a uma lista de entradas <chave, valor> de configurações
    private HashMap<String, String> getConfigurationByXML(File file) {

        // Hashmap com as configurações de URIs
        HashMap<String, String> configuration = new HashMap<String, String>();

        try {
            File fXmlFile = new File(file.getPath());
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();
            //System.out.println("Root element: " + doc.getDocumentElement().getNodeName());

            Element eElement = null;

            NodeList endpoitnList = doc.getElementsByTagName("endpoint");
            Node endpoint = endpoitnList.item(0);
            eElement = (Element) endpoint;
            configuration.put("endpoint", endpoint.getTextContent());
            //Pega os elemtentos vocabulry
            NodeList nList = doc.getElementsByTagName("vocabulary");
            //System.out.println("----------------------------");

            //Para cada elemento vocabulary
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                //System.out.println("\nCurrent Element: " + nNode.getNodeName() + ": " + nNode.getTextContent());
                //Verifica se o elemento é do tipo nó
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    eElement = (Element) nNode;
                    //Pegue o id do vocabulário
                    //System.out.print("Element id : " + eElement.getAttribute("id"));
                    configuration.put(eElement.getAttribute("id"), eElement.getChildNodes().item(0).getNodeValue());
                }
                //Pega a lista de filhos do vocabulário, no caso, propriedades e põe em properties
                NodeList properties = nNode.getChildNodes();
                //System.out.print(": " + properties.getLength() + " childs");
                //Para cada propriedade filha do vocabulário
                for (int x = 0; x < properties.getLength(); x++) {
                    //Pegue um item da lista de nós filho e ponha em propertie
                    Node propertie = properties.item(x);
                    //System.out.print("\nCurrent Element :" + propertie.getNodeName() + " ");
                    //Verifica se o elemento é do tipo nó
                    if (propertie.getNodeType() == Node.ELEMENT_NODE) {
                        Element el = (Element) propertie;
                        //Pegue o valor da propriedade
                        //System.out.println("\n\tPropertie: " + el.getTextContent());
                        configuration.put(el.getChildNodes().item(0).getNodeValue(), eElement.getAttribute("id"));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return configuration;
    }

    public void setProperty(String property, String vocabulary) {
        configuration.put(property,vocabulary);
    }

//    private HashMap<String,String> getConfigurationByXML() {
//        return getConfigurationByXML(file);
//    }

    public Resource addResourceTo(Model model) {
        getConfigurationByXML(file);
        return model.createResource(getResourceUri(), getResource("Schema.org", "Place"))
                .addProperty(createProperty("Schema.org", "name"), "Nome do lugar")
                .addProperty(createProperty("geo", "latitude"), "45654.852")
                .addProperty(createProperty("geo", "logitude"), "32145.789")
                .addProperty(createProperty("rev", "comment"), "comentário legal")
                .addProperty(createProperty("rev", "rating"), "4.6");
    }

    private String getResourceUri() {
        return configuration.get("Schema.org") + UUID.randomUUID();
    }

    private Resource getResource(String baseUri, String resourceType){
        return ResourceFactory.createResource(configuration.get(baseUri) + resourceType);
    }

    private Property createProperty(String prefix, String property) {
        return ResourceFactory.createProperty(configuration.get(prefix), property);
    }
}
