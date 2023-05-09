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
import java.util.*;


public class VisualisationController {
    @FXML
    private Canvas plane;

    @FXML
    private Pane pane;
    private int deltaX = 580;
    private int deltaY = 420;

    private Set<Ticket> drawTickets;
    private volatile Set<Ticket> updatedTickets;

    private GraphicsContext gc;
    private Map<List<Integer>, Ticket> coordTickets = new HashMap<>();

    @FXML
    void initialize() throws InterruptedException {
        gc = plane.getGraphicsContext2D();

        drawTickets = Container.getTickets();
        updatedTickets = Container.getTickets();

        System.out.println(drawTickets.size());
        System.out.println(updatedTickets.size());



        for (Ticket ticket : drawTickets) {
            List<Integer> ticketCoords = new ArrayList<>();
            ticketCoords.add((int) (ticket.getCoordinates().getX() + deltaX));
            ticketCoords.add(ticket.getCoordinates().getY() + deltaY);
            coordTickets.put(ticketCoords, ticket);


            draw((int) (ticket.getCoordinates().getX() + deltaX), ticket.getCoordinates().getY() + deltaY, "00ff00");

        }

//        Ticket firstTicket = null;
//        Ticket secondTicket = null;
//
//        for (Ticket ticket: drawTickets){
//            if (ticket.getName().equals("k")){
//                firstTicket = ticket;
//                break;
//            }
//        }
//
//        for (Ticket ticket: Container.getTickets()){
//            if (ticket.getName().equals("k")){
//                secondTicket = ticket;
//                break;
//            }
//        }
//
//        System.out.println(firstTicket);
//        System.out.println("\n");
//        System.out.println(secondTicket);
//
//        System.out.println(firstTicket.equals(secondTicket));
//        System.out.println(drawTickets.contains(secondTicket));
//        System.out.println(Container.getTickets().contains(firstTicket));


        Duration duration = Duration.seconds(5);
        Timeline timeline = new Timeline(new KeyFrame(duration, event -> {
            check();
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
                        ticketCoords.add(ticket.getCoordinates().getY() + deltaY);
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


    private void erase(int x, int y) {
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
        gc.fillRect(x, y, 50, 20);

    }

    private void check() {
        System.out.println(updatedTickets.size());
        System.out.println(drawTickets.size());

        for (Ticket ticket : updatedTickets) {
            if (!drawTickets.contains(ticket)) { // есть в актуальном, но не нарисован
                System.out.println(11);
                draw((int) (ticket.getCoordinates().getX() + deltaX), ticket.getCoordinates().getY() + deltaY, "00ff00");
                drawTickets.add(ticket);
            }
        }
        Set<Ticket> ticketsForRemove = new HashSet<>();
        for (Ticket ticket : drawTickets) {
            if (!updatedTickets.contains(ticket)) { //нарисован, но уже нет в актуальном
                System.out.println(22);
                erase((int) ticket.getCoordinates().getX() + deltaX, ticket.getCoordinates().getY() + deltaY);
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

    private void draw(int startX, int startY, String color) {
        int width = 50;
        int height = 20;
        int finishX = startX + width;
        int finishY = startY + height;

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
                gc.setFill(Color.web(color));
                gc.fillRect(startX,
                        startY,
                        x.doubleValue(),
                        y.doubleValue()
                );
            }
        };

        timeline.setOnFinished(event -> {
            timer.stop();
            Rectangle rectangle = new Rectangle(startX, startY, width, height);
            rectangle.setFill(Color.web(color));
            rectangle.setOnMouseClicked(e -> {
                try {

                    List<Integer> coords = new ArrayList<>();
                    coords.add((int) rectangle.getX());
                    coords.add((int) rectangle.getY());
                    Ticket ticketToUpdate = coordTickets.get(coords);
                    Container.setTicketToUpdate(ticketToUpdate);
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
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("update.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setX(100);
        stage.setY(50);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();


    }
}
