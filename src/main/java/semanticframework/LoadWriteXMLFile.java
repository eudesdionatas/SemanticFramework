package semanticframework;


import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.w3c.dom.*;

public class LoadWriteXMLFile {

    /////////////////////////////////////////////////////////////////////////////
    //Método para escrever um arquivo XML de acordo com as configurações passadas
    public void write(HashMap<String, String> configuration) {
        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // root elements
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("configuration");
            doc.appendChild(rootElement);

            //Percorre a lista de entradas de configuração para adicionar ao XML
            for (Map.Entry<String, String> myMap : configuration.entrySet()) {

                //Caso em que é um enpoint
                if (myMap.getKey() == "endpoint") {
                    Element element = doc.createElement(myMap.getKey());
                    element.appendChild(doc.createTextNode(myMap.getValue()));
                    rootElement.appendChild(element);
                }

                // Caso em que é uma propriedade
                // Verifica se o prefixo de um determinado vocabulário foi adicionado e adiciona a propriedade em caso positivo
                else if (configuration.containsKey(myMap.getValue())) {
                    Element element = doc.createElement("property");
                    element.appendChild(doc.createTextNode(myMap.getKey()));

                    // Pega o elemento que tiver o id igual ao valor da chave dentre os elementos da árove
                    Element elementFather = getElementById(doc, myMap.getValue());
                    elementFather.appendChild(element);

   /*               Percorre todos os elementos vocabulary e pega o que for igual à chave que está igual ao valor da
                    propriedade a ser adicionada
                    <configuration>
                        <vocabulary id="geo">
                            http://www.w3.org/2003/01/geo/wgs84_pos#
                            <property>lat</property>
                            <property>long</property>
                        </vocabulary>
                        <endpoint>http://localhost</endpoint>
                        <vocabulary id="rev">
                            http://purl.org/stuff/rev#
                            <property>rating</property>
                            <property>comment</property>
                        </vocabulary>
                        <vocabulary id="Schema.org">
                            http://schema.org/
                            <property>nome</property>
                        </vocabulary>
                    </configuration>

                    NodeList vocabularies = doc.getElementsByTagName("vocabulary");
                    for (int i = 0; i < vocabularies.getLength(); i++){
                        if (((Element)vocabularies.item(i)).getAttribute("id").equals(myMap.getValue())){
                            ((Element)vocabularies.item(i)).appendChild(element);
                        }
                    }
    */
                }
                //Caso em que não é um endpoint e não é uma propriedade, é um vocabulário
                else {
                    Element element = doc.createElement("vocabulary");
                    element.appendChild(doc.createTextNode(myMap.getValue()));
                    // set attribute to element
                    Attr attr = doc.createAttribute("id");
                    attr.setValue(myMap.getKey());
                    element.setAttributeNode(attr);
                    rootElement.appendChild(element);
                }
            }

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(System.getProperty("user.home"), "config.xml"));

            // Output to console for testing
            // StreamResult result = new StreamResult(System.out);

            transformer.transform(source, result);

            //System.out.println("File saved2");

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }

    //////////////////////////////////////////
    //Método para retornar um nó através de id
    public Element getElementById(Node doc, String id) {
        try {
            XPathFactory factory = XPathFactory.newInstance();
            XPath xpath = factory.newXPath();
            XPathExpression expr = xpath.compile("//*[@id = '" + id + "']");
            Element result = (Element) expr.evaluate(doc, XPathConstants.NODE);
            return result;
        } catch (XPathExpressionException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /////////////////////////////////////////////////////////////////////////////
    //Método para fazer a uma lista de entradas <chave, valor> de configurações
    public HashMap<String, String> getConfigurationByXML(File file) {

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
                    configuration.put(eElement.getAttribute("id"), eElement.getTextContent());
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
                        configuration.put(el.getTextContent(), eElement.getAttribute("id"));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return configuration;
    }

 //    private Model createModel() {
//        Model model = ModelFactory.createDefaultModel();
//        model.setNsPrefix("schema", SchemaOrg.getURI());
//        model.setNsPrefix("geo", GEO.getURI());
//        model.setNsPrefix("rev", REV.getURI());
//        model.setNsPrefix("tguide", TGUIDE.getURI());
//
//        return model;
//    }

}