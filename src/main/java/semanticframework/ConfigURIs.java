package semanticframework; /**
 * Created by Eudes on 21/08/2017.
 */

import java.io.*;
import java.util.*;


public class ConfigURIs {

    //Variável para armazenar a URI do endpoint
    private String endPointURI;
    //Variável para armazenar a URI do vocabulário
    private String vocabURI;
    //Arquivo para armazenar as propriedades de configuração de funcionamento do framework
    static Properties properties = new Properties();

    static List<String> vocabularios = null;
    static String endpoint = null;

    //Arquivo configurado com o caminho da pasta do usuário para gravar e ler as propriedades
    File file = new File(System.getProperty("user.home"), "config.properties");

    /*
    Método para atribuir o valor da URI do endpoint
    Este método deve ser chamado uma vez para determinar a URI do endpoint que armazenará o conteúdo
    */
    public void setEndpointURI(String endPointURI) {
        this.endpoint = (endPointURI);
        properties.setProperty("endpoint", endPointURI);
    }

    /*
    Método para atribuir o valor da URI de um vocabuário a ser usado
    Este método pode ser chamado diversas vezes para adicionar tantos vocabulários quanto se desejar
    */
    public void setVocabURI(String vocab, String vocabURI) {
        vocabularios.add(vocab);
        properties.setProperty(vocab, vocabURI);
    }

    /*
    Método para salvar todas as propriedades no arquivo "config.properties"
    Este método salva na pasta do usuário
     */
    /*public void saveProperties() {
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
    }*/

    public void saveProperties(){
        LoadWriteXMLFile writeXML = new LoadWriteXMLFile();
    }

    public Properties loadProperties(){

        //Variável para guardar os dados do arquivo lido
        InputStream input = null;

        try {
            //Lê a variável externa file que tem o caminho da pasta do usuário
            input = new FileInputStream(file);
            properties.load(input);
            /*
            * Captura todas as configurações do arquivo .properties enquanto houver
            */
            Set<String> propriedades = properties.stringPropertyNames();
            for (String p: propriedades){
                // get the property value and print it out
                System.out.println(p + "=" + properties.getProperty(p));
            }
//            System.out.println(System.getProperty("user.home"));
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return properties;
    }


}

