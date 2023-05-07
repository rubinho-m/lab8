package app.lab8;

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

import java.io.IOException;

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
        System.out.println("Окно входа");

    }

    @FXML
    protected void signUp() {
        visibleManage();
        System.out.println("Окно регистрации");

    }
    @FXML
    protected void changeScene(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Client.class.getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setX(0);
        stage.setY(-5);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

    }
}