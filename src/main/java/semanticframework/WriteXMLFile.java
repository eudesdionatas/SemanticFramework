package semanticframework;


import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class WriteXMLFile {

    /////////////////////////////////////////////////////////////////////////////
    //Método para escrever um arquivo XML de acordo com as configurações passadas
    public void writeXML(HashMap<String, String> configuration) {
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