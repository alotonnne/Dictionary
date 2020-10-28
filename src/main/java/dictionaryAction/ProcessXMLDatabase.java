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
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProcessXMLDatabase extends DictionaryManagement {
    File fileXML = new File("src/main/resources/data/Anh_Viet.xml");
    public static List<String> listTarget= new ArrayList<String>();
    public static HashMap<String, Meanings> listMeaning = new HashMap<String, Meanings>();
    public static HashMap<String ,String > listWord = new HashMap<>();
    Document document;
    public void readFile()
    {
        try{
            loadXMLFile();
            document.getDocumentElement().normalize();

            NodeList list = document.getElementsByTagName("record");
            for(int i = 0; i < list.getLength(); i++){
                Node node = list.item(i);
                if(node.getNodeType() == Node.ELEMENT_NODE){
                    Element elm = (Element)node;
                    String target;
                    // get word ENGLISH
                    target = elm.getElementsByTagName("word").item(0).getTextContent().toString();
                    listTarget.add(target);
                    String waitpro;
                    Meanings temp;
                    String explain;
                    waitpro = elm.getElementsByTagName("meaning").item(0).getTextContent().toString();
                    /*
                        String processing into 3 components
                            +   pronunciation
                            +   attrubute
                            +   listword
                            +   Key
                    */
                    String[] edit_1 = waitpro.split("\\*");
                    //Has attribute
                    if(edit_1.length > 1){
                        String[] edit_2 = edit_1[1].split("(?=-)", 2);
                        if(edit_2.length > 1){
                            temp = new Meanings(edit_1[0],edit_2[0], edit_2[1], target);
                            StringBuffer stringBuffer = new StringBuffer();
                            stringBuffer.append("\n");
                            stringBuffer.append(edit_1[0]);
                            stringBuffer.append(edit_2[0]);
                            stringBuffer.append(edit_2[1]);
                            explain = stringBuffer.toString();
                        }
                        else{
                            temp = new Meanings(edit_1[0],edit_2[0], "No define!!!", target);
                            StringBuffer stringBuffer = new StringBuffer();
                            stringBuffer.append("\n");
                            stringBuffer.append(edit_1[0]);
                            stringBuffer.append(edit_2[0]);
                            explain = stringBuffer.toString();
                        }
                        if(temp!= null)
                            listMeaning.put(target, temp);
                            listWord.put(target, explain);
                    }
                    //No attribute
                    else{
                        String[] edit_3 = waitpro.split("(?=-)", 2);
                        if(edit_3.length > 1)
                        {
                            temp = new Meanings(edit_3[0], "Not defined!!!", edit_3[1], target);
                            StringBuffer stringBuffer = new StringBuffer();
                            stringBuffer.append("\n");
                            stringBuffer.append(edit_3[0]);
                            stringBuffer.append(edit_3[1]);
                            explain = stringBuffer.toString();
                        }
                        else{
                            temp = new Meanings(edit_3[0], "Not defined!!!", "Not defined!!!", target);
                            StringBuffer stringBuffer = new StringBuffer();
                            stringBuffer.append("\n");
                            stringBuffer.append(edit_3[0]);
                            explain = stringBuffer.toString();
                        }
                        if(temp != null)
                            listMeaning.put(target,temp);
                    }
                }
            }
        }
        catch(Exception e){
            System.out.println("ReadFile Error!!!");
        }
    }
    public String lookup(String target)
    {
        return listWord.get(target);
    }
    public HashMap<String , String > getListWord()
    {
        return listWord;
    }

    public void addElement(Word newWord)
    {
        loadXMLFile();
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

        Element root = document.getDocumentElement();
        root.insertBefore(record, root.getElementsByTagName("dictionary").item(0));
        writeXMLFile();
    }

    public void loadXMLFile()
    {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            document  = dBuilder.parse(fileXML);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

    public void writeXMLFile() {
            try {
                DOMSource domSource = new DOMSource((Node) document);
                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty(OutputKeys.METHOD, "xml");
                transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

                FileWriter fileWriter = new FileWriter(fileXML);
                StreamResult streamResult = new StreamResult(fileWriter);
                Source dOMSource;

                // send DOM to file
                transformer.transform(domSource, streamResult);

            } catch (TransformerException te) {
                System.out.println(te.getMessage());
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            }
    }

}
