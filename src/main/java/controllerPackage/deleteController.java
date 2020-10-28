package controllerPackage;

import CommandLine.DictionaryManagement;
import CommandLine.Word;
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

public class deleteController implements Initializable {
    private DictionaryManagement dictionaryManagement = new DictionaryManagement();
    @FXML
    private TextField targetField;

    @FXML
    private Button backButton;

    @FXML
    private Button okButton;

    @FXML
    private Button cancelButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dictionaryManagement.insertFromFile();
        if (targetField.getText().trim().equals(""))
            okButton.setDisable(true);
        targetField.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (targetField.getText().trim().equals(""))
                    okButton.setDisable(true);
                else  okButton.setDisable(false);
            }
        });
        okButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String target = targetField.getText();
                if (targetField.getText().trim().equals(""))
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Target is NULL");
                    ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                    alert.show();
                }
                else
                {
                    if (dictionaryManagement.getNewD().getExplain(target) == true)
                    {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Confirmation");
                        alert.setHeaderText("Are you sure to detete this word ?");
                        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                        ButtonType buttonTypeApply = new ButtonType("Yes", ButtonBar.ButtonData.APPLY);
                        ButtonType buttonTypeNo = new ButtonType("No", ButtonBar.ButtonData.NO);
                        alert.getButtonTypes().setAll(buttonTypeApply, buttonTypeCancel, buttonTypeNo);
                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.get() == buttonTypeApply)
                        {
                            dictionaryManagement.getNewD().delete(target);
                            dictionaryManagement.dictionaryExportToFile();
                            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                            alert1.setTitle("Notification");
                            alert1.setHeaderText("Word deleted to the Dictionary");
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
    }
}
