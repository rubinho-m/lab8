package app.lab8.controllers;

import app.lab8.App;
import app.lab8.common.networkStructures.Request;
import app.lab8.network.Authentication;
import app.lab8.network.Container;
import app.lab8.network.NetworkConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class AuthController {
    @FXML
    private Button loginButton;

    @FXML
    private PasswordField passwordInput;

    @FXML
    private Label errorLabel;

    @FXML
    private Scene scene;

    @FXML
    private Label header;

    @FXML
    private Button sendButton;

    @FXML
    private Button signUpButton;

    @FXML
    private TextField usernameInput;

    @FXML
    MenuButton languageButton;

    @FXML
    private MenuItem russian;

    @FXML
    private MenuItem belarusian;

    @FXML
    private MenuItem greek;

    @FXML
    private MenuItem spanish;
    private String login;
    private String password;
    private String type;
    private Authentication authentication;

    private ResourceBundle resourceBundle;

    @FXML
    void initialize() {
        resourceBundle = ResourceBundle.getBundle("resources", new Locale("ru", "RU"));
        Container.setLanguage("russian");
        changeLanguage();

        List<MenuItem> languageItems = languageButton.getItems();
        for (MenuItem item : languageItems) {
            item.setOnAction(e -> {
                Container.setLanguage(item.getId());
                if (item.getId().equals("russian")){
                    resourceBundle = ResourceBundle.getBundle("resources", new Locale("ru", "RU"));
                    changeLanguage();
                }
                if (item.getId().equals("belarusian")){
                    resourceBundle = ResourceBundle.getBundle("resources", new Locale("be", "BY"));
                    changeLanguage();
                }
                if (item.getId().equals("spanish")){
                    resourceBundle = ResourceBundle.getBundle("resources", new Locale("es", "HN"));
                    changeLanguage();
                }
                if (item.getId().equals("greek")){
                    resourceBundle = ResourceBundle.getBundle("resources", new Locale("el", "GR"));
                    changeLanguage();
                }
            });
        }

    }


    private void changeLanguage(){
        loginButton.setText(resourceBundle.getString("logIn"));
        passwordInput.setPromptText(resourceBundle.getString("password"));
        header.setText(resourceBundle.getString("header"));
        sendButton.setText(resourceBundle.getString("send"));
        signUpButton.setText(resourceBundle.getString("signUp"));
        usernameInput.setPromptText(resourceBundle.getString("username"));
        languageButton.setText(resourceBundle.getString("language"));
        russian.setText(resourceBundle.getString("russian"));
        belarusian.setText(resourceBundle.getString("belarusian"));
        greek.setText(resourceBundle.getString("greek"));
        spanish.setText(resourceBundle.getString("spanish"));
    }

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
            Container.setUser(login);
            Container.setPassword(authentication.encodePassword(password));
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("main.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setX(0);
            stage.setY(-5);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } else {
            errorLabel.setVisible(true);
            errorLabel.setText(resourceBundle.getString(Container.getAuthResponse()));

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
        App.networkConnection.connectionManage(authRequest);

        return (App.networkConnection.isRegFlag() || App.networkConnection.isAuthFlag());

    }
}