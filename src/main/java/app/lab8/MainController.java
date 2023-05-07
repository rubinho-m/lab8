package app.lab8;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

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
    protected void showVisualisation() {
        System.out.println("Визуализация");

    }

    @FXML
    private void showArgumentWindow(ActionEvent event, String window) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Client.class.getResource(String.format("%s.fxml", window)));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setX(0);
        stage.setY(0);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

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