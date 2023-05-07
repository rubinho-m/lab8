package app.lab8.controllers;

import app.lab8.App;
import app.lab8.common.networkStructures.Request;
import app.lab8.common.structureClasses.Ticket;
import app.lab8.network.Container;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ArgumentController {


    @FXML
    private TextField usernameInput;


    private void closeStage(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private Request makeRequest(ArrayList<String> commandWithArguments, Ticket ticket){
        ArrayList<String> userData = new ArrayList<>();
        userData.add(Container.getUser());
        userData.add(Container.getPassword());
        return new Request(commandWithArguments, ticket, userData);
    }
    private void sendCommandWithArgument(String command, String argument) throws Exception {
        ArrayList<String> commandWithArguments = new ArrayList<>();
        commandWithArguments.add(command);
        commandWithArguments.add(argument);
        Request request = makeRequest(commandWithArguments, null);
        App.networkConnection.connectionManage(request);

    }

    @FXML
    void sendScript(ActionEvent event) throws Exception {
        sendCommandWithArgument("execute_script", usernameInput.getText());
        closeStage(event);

    }

    @FXML
    void sendFilter(ActionEvent event) throws Exception {
        sendCommandWithArgument("filter_greater_than_price", usernameInput.getText());
        closeStage(event);

    }

    @FXML
    void sendRemoveById(ActionEvent event) throws Exception {
        sendCommandWithArgument("remove_by_id", usernameInput.getText());
        closeStage(event);
    }

}
