package dictionaryAction;

import CommandLine.DictionaryManagement;
import CommandLine.Word;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ProcessXMLDatabase extends DictionaryManagement {

    public static ArrayList<String> listTarget= new ArrayList<String>();
    public static HashMap<String ,String > listWord = new HashMap<>();
    DocumentBuilderFactory documentBuilderFactory;
    DocumentBuilder documentBuilder;
    public void readFile()
    {
        try{

            Document document = loadXMLFile();
            NodeList list = document.getElementsByTagName("record");
            for(int i = 0; i < list.getLength(); i++){
                Node node = list.item(i);
                if(node.getNodeType() == Node.ELEMENT_NODE){
                    Element elm = (Element)node;
                    String target;
                    // get word ENGLISH
                    target = elm.getElementsByTagName("word").item(0).getTextContent().toString();
                    listTarget.add(target);
                    String explain= elm.getElementsByTagName("meaning")
                            .item(0).getTextContent().toString();
                    listWord.put(target, explain);
                }
            }
        }
        catch(Exception e){
            System.out.println("ReadFile Error!!!");
        }
    }
    public HashMap<String , String > getListWord()
    {
        return listWord;
    }
    public ArrayList<String> getTarget()
    {
        return listTarget;
    }

    public void addElement(Word newWord)
    {
        Document document = loadXMLFile();
        Node dictionary = document.getFirstChild();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("@");  stringBuilder.append(newWord.getWord_target());
        stringBuilder.append("\n");  stringBuilder.append(newWord.getWord_explain());
        String explain = stringBuilder.toString();

        Element record = document.createElement("record");
        Element word = document.createElement("word");
        word.setTextContent(newWord.getWord_target());
        Element meaning = document.createElement("meaning");
        meaning.setTextContent(explain);

        record.appendChild(word);
        record.appendChild(meaning);

        dictionary.appendChild(record);
        writeXMLFile(document);
    }
    public void editElement(Word newWord)
    {
        Document document = loadXMLFile();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("@");  stringBuilder.append(newWord.getWord_target());
        stringBuilder.append("\n");  stringBuilder.append(newWord.getWord_explain());
        String explain = stringBuilder.toString();

        NodeList record = document.getElementsByTagName("record");
        for (int temp = 0; temp <record.getLength(); temp++)
        {
            Node node = record.item(temp);
            if(node.getNodeType() == Node.ELEMENT_NODE){
                Element element = (Element) record.item(temp);
                Element word = (Element) element.getElementsByTagName("word").item(0);
                String target = word.getTextContent();
                if (newWord.getWord_target().trim().equals(target))
                {
                    Element meaning = (Element) element.getElementsByTagName("meaning").item(0);
                    meaning.setTextContent(explain);
                }
            }

        }
        writeXMLFile(document);
    }

    public void deleteElement(String delTarget)
    {
        Document document = loadXMLFile();
        NodeList record = document.getElementsByTagName("record");
        for (int temp = 0; temp <record.getLength(); temp++)
        {
            Element element = (Element) record.item(temp);
            Element word = (Element) element.getElementsByTagName("word").item(0);
            String target = word.getTextContent();
            if (delTarget.trim().equals(target))
            {
                element.getParentNode().removeChild(element);
            }
        }
        writeXMLFile(document);
    }

    public Document loadXMLFile()
    {
        Document document1 = null;
        File fileXML = new File("src/main/resources/data/Anh_Viet.xml");
        try {
            documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document1 = documentBuilder.parse(fileXML);
            document1.getDocumentElement().normalize();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return document1;
    }

    public void writeXMLFile(Document document) {
        File fileXML = new File("src/main/resources/data/Anh_Viet.xml");
            try {
                DOMSource domSource = new DOMSource(document);
                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                StreamResult streamResult = new StreamResult(fileXML);
                // send DOM to file
                transformer.transform(domSource, streamResult);
            } catch (TransformerException te) {
                System.out.println(te.getMessage());
            }
    }

}
