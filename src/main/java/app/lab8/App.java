package app.lab8;

import app.lab8.network.NetworkConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.NoSuchElementException;

public class App extends Application {
    public static NetworkConnection networkConnection;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("auth.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws IOException {

        if (args.length != 2) {
            System.out.println("Введите адрес сервера и порт");
            System.exit(1);
        }
        try {
            int port = Integer.parseInt(args[1]);
//            networkConnection = new NetworkConnection(args[0], port);
            launch();
        } catch (NumberFormatException e) {
            System.out.println("Порт должен быть числом");
            System.exit(1);
        } catch (NoSuchElementException e) {
            System.exit(0);
        }

    }
}