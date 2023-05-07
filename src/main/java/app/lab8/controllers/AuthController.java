package app.lab8.controllers;

import app.lab8.App;
import app.lab8.common.networkStructures.Request;
import app.lab8.network.Authentication;
import app.lab8.network.NetworkConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AuthController {
    @FXML
    private Button loginButton;

    @FXML
    private PasswordField passwordInput;

    @FXML
    private Label header;

    @FXML
    private Scene scene;

    @FXML
    private Button sendButton;

    @FXML
    private Button signUpButton;

    @FXML
    private TextField usernameInput;
    private String login;
    private String password;
    private String type;
    private Authentication authentication;

    private void visibleManage() {
        loginButton.setVisible(false);
        signUpButton.setVisible(false);
        sendButton.setVisible(true);
        usernameInput.setVisible(true);
        passwordInput.setVisible(true);
    }

    @FXML
    protected void logIn() {
        visibleManage();
        type = "auth";

    }

    @FXML
    protected void signUp() {
        visibleManage();
        type = "reg";

    }

    @FXML
    protected void changeScene(ActionEvent event) throws Exception {
        if (check(type)) {
            System.out.println("UHU");
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("main.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setX(0);
            stage.setY(-5);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } else {
            header.setText("ERROR");
        }

    }

    private boolean check(String type) throws Exception {
        login = usernameInput.getText();
        password = passwordInput.getText();
        authentication = new Authentication();
        authentication.setLogin(login);
        authentication.setPassword(authentication.encodePassword(password));
        authentication.setType(type);
        Request authRequest = authentication.getRequest();
        System.out.println(authRequest.getCommandWithArguments());
        NetworkConnection networkConnection = new NetworkConnection("localhost", 4546);
        networkConnection.connectionManage(authRequest);

        return (networkConnection.isRegFlag() || networkConnection.isAuthFlag());

    }
}