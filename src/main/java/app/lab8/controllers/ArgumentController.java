package app.lab8.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ArgumentController {


    @FXML
    private TextField usernameInput;


    private void closeStage(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void sendScript(ActionEvent event) {
        System.out.println("Отправил скрипт");
        System.out.println(usernameInput.getText());
        closeStage(event);

    }

    @FXML
    void sendFilter(ActionEvent event) {
        System.out.println("Отправил фильтр");
        System.out.println(usernameInput.getText());
        closeStage(event);

    }

    @FXML
    void sendRemoveById(ActionEvent event){
        System.out.println("Отправил удаление по id");
        System.out.println(usernameInput.getText());
        closeStage(event);
    }

}
