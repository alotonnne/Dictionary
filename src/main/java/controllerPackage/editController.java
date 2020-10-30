package controllerPackage;

import CommandLine.DictionaryManagement;
import CommandLine.Word;
import dictionaryAction.ProcessXMLDatabase;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class editController implements Initializable {
    DictionaryManagement dictionaryManagement  = new DictionaryManagement();
    ProcessXMLDatabase processXMLDatabase = new ProcessXMLDatabase();
    @FXML
    private Button backButton;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField targetField;

    @FXML
    private TextField explainField;

    @FXML
    private Button okButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dictionaryManagement.insertFromFile();
        if (targetField.getText().trim().equals(""))
            explainField.setDisable(true);
        targetField.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (targetField.getText().trim().equals(""))
                    explainField.setDisable(true);
                else explainField.setDisable(false);
            }
        });

        okButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (explainField.getText().trim().equals(""))
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Target or explain is NULL");
                    ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                    alert.show();
                }
                else
                {
                    if (processXMLDatabase.getListWord().containsKey(targetField.getText()))
                    {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Confirmation");
                        alert.setHeaderText("Are you sure to edit this word ?");
                        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                        ButtonType buttonTypeYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
                        ButtonType buttonTypeNo = new ButtonType("No", ButtonBar.ButtonData.NO);
                        alert.getButtonTypes().setAll( buttonTypeCancel, buttonTypeYes, buttonTypeNo);
                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.get() == buttonTypeYes)
                        {
                            dictionaryManagement.getNewD().edit(targetField.getText(), explainField.getText());
                            dictionaryManagement.dictionaryExportToFile();
                            Word newWord = new Word();
                            newWord.setWord_target(targetField.getText());
                            newWord.setWord_explain(explainField.getText());
                            processXMLDatabase.editElement(newWord);
                            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                            alert1.setTitle("Notification");
                            alert1.setHeaderText("Word edited to the Dictionary");
                            ButtonType buttonTypeOK = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                            alert1.show();
                        }
                    }
                    else
                    {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Notification");
                        alert.setHeaderText("Word doesn't exist in the Dictionary");
                        ButtonType buttonTypeOK = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                        alert.show();
                    }
                }
            }
        });
    }
    @FXML
    private void HandleOnClickBack() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/sample.fxml"));
        Stage stage = new Stage();
        stage.setScene( new Scene(root));
        stage.show();
    }

    @FXML
    private void HandleOnClickCancel()
    {
        targetField.clear();
        explainField.clear();
    }

}
