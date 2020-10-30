package controllerPackage;

import CommandLine.DictionaryManagement;
import dictionaryAction.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import javazoom.jl.decoder.JavaLayerException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;
import java.util.function.Predicate;


public class Controller  implements Initializable {
    private searchAction search = new searchAction();
    private DictionaryManagement dictionaryManagement = new DictionaryManagement();
    private ProcessXMLDatabase processXMLDatabase = new ProcessXMLDatabase();
    private HashMap<String, String> wordList = new HashMap<>();
    private ArrayList<String> targetTemp= new ArrayList<>();
    private ArrayList<String> explainTemp = new ArrayList<>();
    @FXML
    private TextField searchField;

    @FXML
    private Button searchButton;

    @FXML
    private TextField textSearchField;

    @FXML
    public ListView<String> listWord;

    @FXML
    private TextArea explainArea;

    @FXML
    private Button addButton;

    @FXML
    private Button saveButton;

    @FXML
    private Button editButton;

    @FXML
    private Button apiButton;

    @FXML
    private ObservableList list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dictionaryManagement.insertFromFile();
        processXMLDatabase.readFile();
        wordList = processXMLDatabase.getListWord();

        /**
         * set disable/enable cho button search
         */
        if (searchField.getText().trim().equals(""))
        {
            searchButton.setDisable(true);
            textSearchField.setDisable(true);
        }
        searchField.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (searchField.getText().trim().equals(""))
                    searchButton.setDisable(true);
                else searchButton.setDisable(false);
            }
        });

        /**
         * tao arrayList cho listview;
         */
        for (Map.Entry<String, String> item : wordList.entrySet())
        {
            targetTemp.add(item.getKey());
        }
        /**
         * tao arrayList cho textArea
         */
        for (Map.Entry<String, String> item : wordList.entrySet())
        {
            explainTemp.add(item.getValue());
        }
        listWord.getItems().addAll(targetTemp);
        //
        listWord.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                return new Mycell();
            }
        });
        searchField.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                List<String> matchedWords = new ArrayList<>(targetTemp);
                matchedWords.removeIf(new Predicate<String>() {
                    @Override
                    public boolean test(String target) {
                        if (target.startsWith(searchField.getText())) {
                            return false;
                        }
                        return true;
                    }
                });

                listWord.getItems().clear();
                listWord.getItems().addAll(matchedWords);
                listWord.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
                listWord.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                        searchField.setText(newValue);
                        textSearchField.setText(searchField.getText());
                        explainArea.setText(wordList.get(newValue));
                    }
                });
            }
        });

    }

    public static class Mycell extends ListCell<String> {
        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {
                setText(item);
            } else {
                setText(null);
            }
        }
    }

    /**
     * set text cho searchField, in ket qua tu searchField qua textSearchField
     */
    @FXML
    private void HandleOnClickButton()
    {
        System.out.println(searchField.getText());
        textSearchField.setText(searchField.getText());
        explainArea.setText(wordList.get(searchField.getText()));
    }

    /**
     *  tao text cho searchField tu listView
     */
    @FXML
    private  void HandleOnClickLineListView()
    {

        String selected = listWord.getSelectionModel().getSelectedItem();
        System.out.println(selected);
        searchField.setText(selected);
        textSearchField.setText(searchField.getText());
        explainArea.setText(wordList.get(searchField.getText()));
    }


    /**
     * tao scene add phan tu qua button AddButton
     * @throws IOException
     */
    @FXML
    private void directAddScene() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/addGui.fxml"));
        Stage stage = new Stage();
        stage.setScene( new Scene(root));
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                listWord.getItems().clear();
                processXMLDatabase.readFile();
                wordList = processXMLDatabase.getListWord();
                targetTemp = processXMLDatabase.getTarget();
                listWord.getItems().addAll(targetTemp);
                listWord.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
                    @Override
                    public ListCell<String> call(ListView<String> param) {
                        return new Mycell();
                    }
                });
            }
        });
        stage.show();
    }

    @FXML
    private void directDeleteScene() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/deleteGui.fxml"));
        Stage stage = new Stage();
        stage.setScene( new Scene(root));
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                listWord.getItems().clear();
                processXMLDatabase.readFile();
                wordList = processXMLDatabase.getListWord();
                targetTemp = processXMLDatabase.getTarget();
                listWord.getItems().addAll(targetTemp);
                listWord.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
                    @Override
                    public ListCell<String> call(ListView<String> param) {
                        return new Mycell();
                    }
                });
            }
        });
        stage.show();
    }

    @FXML
    private void directEditScene() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/editGui.fxml"));
        Stage stage = new Stage();
        stage.setScene( new Scene(root));
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                listWord.getItems().clear();
                processXMLDatabase.readFile();
                wordList = processXMLDatabase.getListWord();
                targetTemp = processXMLDatabase.getTarget();
                listWord.getItems().addAll(targetTemp);
                listWord.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
                    @Override
                    public ListCell<String> call(ListView<String> param) {
                        return new Mycell();
                    }
                });
            }
        });
        stage.show();
    }

    @FXML
    private void HandleOnClickSpeak(ActionEvent event){
        Speaker audio = Speaker.getInstance();
        InputStream sound = null;
        if (searchField.getText().trim().isEmpty()){
            try {
                sound = audio.getSpeaker("Enter English words in the search box", "en");
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                audio.play(sound);
            } catch (JavaLayerException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                sound = audio.getSpeaker(searchField.getText(), "en");
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                audio.play(sound);
            } catch (JavaLayerException e) {
                e.printStackTrace();
            }
        }
    }
}
