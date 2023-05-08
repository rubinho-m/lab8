package app.lab8.controllers;

import app.lab8.App;
import app.lab8.common.networkStructures.Request;
import app.lab8.common.structureClasses.Ticket;
import app.lab8.network.Container;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.ArrayList;

public class MainController {

    @FXML
    private Button ticketButton;
    @FXML
    private Button executeScriptButton;

    @FXML
    private Button filterButton;

    @FXML
    private Button removeByIdButton;
    @FXML
    private Label userLabel;

    @FXML
    private TextArea textDisplay;

    @FXML
    void initialize() {
        userLabel.setText("User: " + Container.getUser());

    }

    @FXML
    protected void showVisualisation() {
        System.out.println("Визуализация");

    }


    private Request makeRequest(ArrayList<String> commandWithArguments, Ticket ticket) {
        ArrayList<String> userData = new ArrayList<>();
        userData.add(Container.getUser());
        userData.add(Container.getPassword());
        return new Request(commandWithArguments, ticket, userData);
    }

    private void sendCommandWithoutArgument(String command) throws Exception {
        textDisplay.setVisible(true);
        ArrayList<String> commandWithArguments = new ArrayList<>();
        commandWithArguments.add(command);
        Request request = makeRequest(commandWithArguments, null);
        App.networkConnection.connectionManage(request);
        textDisplay.setText(Container.getActualResponse());
    }

    @FXML
    void sendClear(ActionEvent event) throws Exception {
        sendCommandWithoutArgument("clear");

    }

    @FXML
    void sendHelp(ActionEvent event) throws Exception {
        sendCommandWithoutArgument("help");

    }

    @FXML
    void sendHistory(ActionEvent event) throws Exception {
        sendCommandWithoutArgument("history");

    }

    @FXML
    void sendInfo(ActionEvent event) throws Exception {
        sendCommandWithoutArgument("info");

    }

    @FXML
    void sendMinByPrice(ActionEvent event) throws Exception {
        sendCommandWithoutArgument("min_by_price");
    }

    @FXML
    void sendPrintVenues(ActionEvent event) throws Exception {
        sendCommandWithoutArgument("print_field_descending_venue");
    }

    @FXML
    private void showArgumentWindow(ActionEvent event, String window) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(String.format("%s.fxml", window)));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setX(0);
        stage.setY(0);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        textDisplay.setVisible(true);
        stage.setOnHiding(we -> {
            textDisplay.setVisible(true);
            textDisplay.setText(Container.getActualResponse());
        });

    }

    @FXML
    void showAdd(ActionEvent event) throws IOException {
        System.out.println("Add");
        showArgumentWindow(event, "add");

    }

    @FXML
    void showAddIfMin(ActionEvent event) throws IOException {
        System.out.println("Add if min");
        showArgumentWindow(event, "add_if_min");

    }

    @FXML
    void showUpdate(ActionEvent event) throws IOException {
        System.out.println("Update");
        showArgumentWindow(event, "update");

    }

    @FXML
    void showRemoveGreater(ActionEvent event) throws IOException {
        System.out.println("Remove Greater");
        showArgumentWindow(event, "remove_greater");

    }

    @FXML
    void showExecuteScript(ActionEvent event) throws IOException {
        System.out.println("Execute script");
        showArgumentWindow(event, "execute_script");

    }

    @FXML
    void showFilter(ActionEvent event) throws IOException {
        System.out.println("Filter");
        showArgumentWindow(event, "filter");
    }

    @FXML
    void showRemoveById(ActionEvent event) throws IOException {
        System.out.println("RemoveById");
        showArgumentWindow(event, "remove_by_id");
    }


}