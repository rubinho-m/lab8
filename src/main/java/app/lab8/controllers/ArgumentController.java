package app.lab8.controllers;

import app.lab8.App;
import app.lab8.common.commands.ExecuteScriptCommand;
import app.lab8.common.dataStructures.ParsedString;
import app.lab8.common.networkStructures.Request;
import app.lab8.common.structureClasses.Ticket;
import app.lab8.network.Container;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.*;
import java.util.concurrent.CompletableFuture;

public class ArgumentController {


    @FXML
    private TextField usernameInput;

    @FXML
    private Label header;

    private ResourceBundle resourceBundle;

    private String type = "";

    @FXML
    void initialize() {
        check();
        Duration duration = Duration.seconds(5);
        Timeline timeline = new Timeline(new KeyFrame(duration, event -> {
            check();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();


    }

    private void check() {
        if (Container.getLanguage().equals("russian")) {
            resourceBundle = ResourceBundle.getBundle("resources", new Locale("ru", "RU"));
        }
        if (Container.getLanguage().equals("belarusian")) {
            resourceBundle = ResourceBundle.getBundle("resources", new Locale("be", "BY"));
        }
        if (Container.getLanguage().equals("spanish")) {
            resourceBundle = ResourceBundle.getBundle("resources", new Locale("es", "HN"));
        }
        if (Container.getLanguage().equals("greek")) {
            resourceBundle = ResourceBundle.getBundle("resources", new Locale("el", "GR"));
        }
        changeLanguage();
    }

    private void changeLanguage() {
        header.setText(resourceBundle.getString("argumentInput"));
        if (usernameInput.getPromptText().equals("Path") || type.equals("path")) {
            type = "path";
            usernameInput.setPromptText(resourceBundle.getString("path"));
        }
        if (usernameInput.getPromptText().equals("Price") || type.equals("price")) {
            type = "price";
            usernameInput.setPromptText(resourceBundle.getString("inputPrice"));
        }
        if (usernameInput.getPromptText().equals("Id") || type.equals("id")) {
            type = "id";
            usernameInput.setPromptText(resourceBundle.getString("id"));
        }


    }


    private void closeStage(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private void hideStage(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.hide();
    }

    private Request makeRequest(ArrayList<String> commandWithArguments) {
        ArrayList<String> userData = new ArrayList<>();
        userData.add(Container.getUser());
        userData.add(Container.getPassword());
        return new Request(commandWithArguments, null, userData);
    }

    private void sendCommandWithArgument(String command, String argument) throws Exception {
        ArrayList<String> commandWithArguments = new ArrayList<>();
        commandWithArguments.add(command);
        commandWithArguments.add(argument);
        Request request = makeRequest(commandWithArguments);
        App.networkConnection.connectionManage(request);

    }

    @FXML
    void sendScript(ActionEvent event) throws Exception {
//        sendCommandWithArgument("execute_script", usernameInput.getText());
        ArrayList<ParsedString<ArrayList<String>, Ticket>> firstToDoCommands = new ArrayList<>();
        try {
            System.out.println(usernameInput.getText());
            ExecuteScriptCommand executeScriptCommand = new ExecuteScriptCommand(usernameInput.getText());
            executeScriptCommand.execute();
            ArrayList<ParsedString<ArrayList<String>, Ticket>> nextCommand = executeScriptCommand.getNextCommand();
            firstToDoCommands = nextCommand;
        } catch (Exception e) {
            System.out.println("Incorrect path to script");
        }

        firstToDoCommands.removeAll(Collections.singleton(null));

        ArrayList<String> userData = new ArrayList<>();
        userData.add(Container.getUser());
        userData.add(Container.getPassword());


        final ArrayList<ParsedString<ArrayList<String>, Ticket>> threadCommand = firstToDoCommands;

        Thread thread = new Thread(() -> {
            for (ParsedString<ArrayList<String>, Ticket> ps : threadCommand) {
                Request request = new Request(ps.getArray(), ps.getTicket(), userData);
                try {
                    App.networkConnection.connectionManage(request);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }


        });
        thread.start();


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
