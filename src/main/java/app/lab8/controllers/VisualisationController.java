package app.lab8.controllers;

import app.lab8.App;
import app.lab8.common.dataStructures.ParsedString;
import app.lab8.common.networkStructures.Request;
import app.lab8.common.structureClasses.Coordinates;
import app.lab8.common.structureClasses.Ticket;
import app.lab8.network.Container;
import javafx.animation.*;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.*;


public class VisualisationController {
    @FXML
    private Canvas plane;

    @FXML
    private Pane pane;
    private int deltaX = 580;
    private int deltaY = 420;

    private int coeff = 10;

    private Set<Ticket> drawTickets;
    private volatile Set<Ticket> updatedTickets;

    private GraphicsContext gc;
    private Map<List<Integer>, Ticket> coordTickets = new HashMap<>();

    private Map<String, String> colors = new HashMap<>();

    @FXML
    void initialize() throws Exception {
        gc = plane.getGraphicsContext2D();

        drawTickets = Container.getTickets();
        updatedTickets = Container.getTickets();

        for (Ticket ticket : drawTickets) {
            List<Integer> ticketCoords = new ArrayList<>();
            ticketCoords.add((int) (ticket.getCoordinates().getX() + deltaX));
            ticketCoords.add(-ticket.getCoordinates().getY() + deltaY);
            coordTickets.put(ticketCoords, ticket);
            if (!colors.containsKey(ticket.getUser())) {
                colors.put(ticket.getUser(), getColor(ticket.getUser()));
            }


            draw((int) (ticket.getCoordinates().getX() + deltaX), -ticket.getCoordinates().getY() + deltaY,
                    (int) ticket.getPrice(), colors.get(ticket.getUser()));

        }


        Duration duration = Duration.seconds(3);
        Timeline timeline = new Timeline(new KeyFrame(duration, event -> {
            try {
                check();
            } catch (Exception e){
                System.out.println(e);
            }
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        Thread thread = new Thread(() -> {
            Duration durationShow = Duration.seconds(1);
            Timeline timelineShow = new Timeline(new KeyFrame(durationShow, event -> {
                try {
                    sendShow();

                    updatedTickets = Container.getTickets();
                    for (Ticket ticket : updatedTickets) {
                        List<Integer> ticketCoords = new ArrayList<>();
                        ticketCoords.add((int) (ticket.getCoordinates().getX() + deltaX));
                        ticketCoords.add(-ticket.getCoordinates().getY() + deltaY);
                        coordTickets.put(ticketCoords, ticket);
                    }
                } catch (Exception ignored) {

                }
            }));

            timelineShow.setCycleCount(Timeline.INDEFINITE);
            timelineShow.play();
        });

        thread.start();


    }


    private String getColor(String user) throws Exception {
        ArrayList<String> commandWithArguments = new ArrayList<>();
        commandWithArguments.add("color");
        commandWithArguments.add(user);
        ArrayList<String> userData = new ArrayList<>();
        userData.add(Container.getUser());
        userData.add(Container.getPassword());
        Request request = new Request(commandWithArguments, null, userData);
        App.networkConnection.connectionManage(request);
        return Container.getLastResponse();
    }

    private void erase(int x, int y, int price) {
        int width = 50;
        int height = 20;
        Iterator<Node> iterator = pane.getChildren().iterator();
        while (iterator.hasNext()) {
            Node node = iterator.next();
            if (node instanceof Rectangle) {
                Rectangle rect = (Rectangle) node;
                if ((rect.getX() == x) && (rect.getY()) == y) {
                    iterator.remove();
                }
            }
        }

        gc.setFill(Color.web("D5E8D4"));
        int newWidth = width / coeff * price;
        int newHeight = height / coeff * price;
        gc.fillRect(x, y, newWidth, newHeight);

    }

    private void check() throws Exception {
        for (Ticket ticket : updatedTickets) {
            if (!drawTickets.contains(ticket)) { // есть в актуальном, но не нарисован
                System.out.println(11);
                if (!colors.containsKey(ticket.getUser())){
                    colors.put(ticket.getUser(), getColor(ticket.getUser()));
                }
                draw((int) (ticket.getCoordinates().getX() + deltaX), -ticket.getCoordinates().getY() + deltaY,
                        (int) ticket.getPrice(), colors.get(ticket.getUser()));
                drawTickets.add(ticket);
            }
        }
        Set<Ticket> ticketsForRemove = new HashSet<>();
        for (Ticket ticket : drawTickets) {
            if (!updatedTickets.contains(ticket)) { //нарисован, но уже нет в актуальном
                System.out.println(22);
                erase((int) ticket.getCoordinates().getX() + deltaX, -ticket.getCoordinates().getY() + deltaY, (int) ticket.getPrice());
                ticketsForRemove.add(ticket);
            }
        }
        for (Ticket ticket : ticketsForRemove) {
            drawTickets.remove(ticket);
        }


    }

    private synchronized void sendShow() throws Exception {

        ArrayList<String> commandWithArguments = new ArrayList<>();
        commandWithArguments.add("show");
        ArrayList<String> userData = new ArrayList<>();
        userData.add(Container.getUser());
        userData.add(Container.getPassword());
        Request request = new Request(commandWithArguments, null, userData);
        App.networkConnection.connectionManage(request);
    }

    private void draw(int startX, int startY, int price, String color) {
        int width = 50;
        int height = 20;

        int newWidth = width / coeff * price;
        int newHeight = height / coeff * price;

        int finishX = startX + newWidth;
        int finishY = startY + newHeight;

        DoubleProperty x = new SimpleDoubleProperty();
        DoubleProperty y = new SimpleDoubleProperty();

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0),
                        new KeyValue(x, 0),
                        new KeyValue(y, 0)
                ),
                new KeyFrame(Duration.seconds(3),
                        new KeyValue(x, finishX - startX),
                        new KeyValue(y, finishY - startY)
                )
        );
        timeline.setCycleCount(1);
        System.out.println("draw");


        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                gc.setFill(Color.web(color.strip()));
                gc.fillRect(startX,
                        startY,
                        x.doubleValue(),
                        y.doubleValue()
                );
            }
        };

        timeline.setOnFinished(event -> {
            timer.stop();
            Rectangle rectangle = new Rectangle(startX, startY, newWidth, newHeight);
            rectangle.setFill(Color.web(color.strip()));
            rectangle.setOnMouseClicked(e -> {
                try {

                    List<Integer> coords = new ArrayList<>();
                    coords.add((int) rectangle.getX());
                    coords.add((int) rectangle.getY());
                    Ticket ticketToUpdate = coordTickets.get(coords);
                    Container.setTicketToUpdate(ticketToUpdate);
                    System.out.println(1);
                    showUpdate();
                } catch (Exception ex) {
                    System.out.println(ex);

                }
            });
            pane.getChildren().add(rectangle);
        });


        timer.start();
        timeline.play();
    }


    @FXML
    private void showUpdate() throws IOException {
        try {
            System.out.println("Тут");
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("update.fxml"));
            System.out.println("A");
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setX(100);
            stage.setY(50);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println(e);
        }



    }
}
