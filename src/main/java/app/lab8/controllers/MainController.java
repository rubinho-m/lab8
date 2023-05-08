package app.lab8.controllers;

import app.lab8.App;
import app.lab8.common.networkStructures.Request;
import app.lab8.common.structureClasses.Ticket;
import app.lab8.common.structureClasses.TicketType;
import app.lab8.network.Container;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Set;

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
    private TableView<TableTicket> ticketTable;

    @FXML
    private TableColumn<TableTicket, String> capacityColumn;

    @FXML
    private TableColumn<TableTicket, String> commentColumn;

    @FXML
    private TableColumn<TableTicket, String> creatorColumn;

    @FXML
    private TableColumn<TableTicket, LocalDate> dateColumn;

    @FXML
    private TableColumn<TableTicket, Long> idColumn;

    @FXML
    private TableColumn<TableTicket, String> nameColumn;

    @FXML
    private TableColumn<TableTicket, Double> priceColumn;

    @FXML
    private TableColumn<TableTicket, Boolean> refundableColumn;

    @FXML
    private TableColumn<TableTicket, String> streetColumn;

    @FXML
    private TableColumn<TableTicket, String> venueColumn;


    @FXML
    private TableColumn<TableTicket, TicketType> typeColumn;

    @FXML
    private TableColumn<TableTicket, String> venueTypeColumn;

    @FXML
    private TableColumn<TableTicket, String> xColumn;

    @FXML
    private TableColumn<TableTicket, String> yColumn;



    @FXML
    void initialize() {
        userLabel.setText("User: " + Container.getUser());

        idColumn.setCellValueFactory(new PropertyValueFactory<TableTicket, Long>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<TableTicket, String>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<TableTicket, Double>("price"));
        commentColumn.setCellValueFactory(new PropertyValueFactory<TableTicket, String>("comment"));
        xColumn.setCellValueFactory(new PropertyValueFactory<TableTicket, String>("x"));
        yColumn.setCellValueFactory(new PropertyValueFactory<TableTicket, String>("y"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<TableTicket, LocalDate>("creationDate"));
        refundableColumn.setCellValueFactory(new PropertyValueFactory<TableTicket, Boolean>("refundable"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<TableTicket, TicketType>("type"));
        creatorColumn.setCellValueFactory(new PropertyValueFactory<TableTicket, String>("user"));
        creatorColumn.setCellValueFactory(new PropertyValueFactory<TableTicket, String>("user"));
        venueColumn.setCellValueFactory(new PropertyValueFactory<TableTicket, String>("venueName"));
        venueColumn.setCellValueFactory(new PropertyValueFactory<TableTicket, String>("venueName"));
        capacityColumn.setCellValueFactory(new PropertyValueFactory<TableTicket, String>("capacity"));
        venueTypeColumn.setCellValueFactory(new PropertyValueFactory<TableTicket, String>("venueType"));
        streetColumn.setCellValueFactory(new PropertyValueFactory<TableTicket, String>("street"));




        Node node = textDisplay;

        Duration duration = Duration.seconds(1);
        Timeline timeline = new Timeline(new KeyFrame(duration, event -> {
            if (textDisplay.isVisible()) {
                textDisplay.setText(Container.getActualResponse());
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play(); //


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
        ticketTable.setVisible(false);
        textDisplay.setVisible(true);
        ArrayList<String> commandWithArguments = new ArrayList<>();
        commandWithArguments.add(command);
        Request request = makeRequest(commandWithArguments, null);
        App.networkConnection.connectionManage(request);
        if (!command.equals("show")) {
            textDisplay.setText(Container.getActualResponse());
        }
    }

    @FXML
    void sendShow(ActionEvent event) throws Exception {
        sendCommandWithoutArgument("show");
        ticketTable.setVisible(true);
        textDisplay.setVisible(false);
        Set<Ticket> tickets = Container.getTickets();
        ObservableList<TableTicket> tableTickets = FXCollections.observableArrayList();
        for (Ticket ticket : tickets) {
            TableTicket tableTicket = new TableTicket();
            tableTicket.setId(ticket.getId());
            tableTicket.setName(ticket.getName());
            tableTicket.setX(String.valueOf(ticket.getCoordinates().getX()));
            tableTicket.setY(String.valueOf(ticket.getCoordinates().getY()));
            tableTicket.setPrice(ticket.getPrice());
            tableTicket.setCreationDate(ticket.getCreationDate());
            tableTicket.setUser(ticket.getUser());
            tableTicket.setComment(ticket.getComment());
            tableTicket.setRefundable(ticket.isRefundable());
            tableTicket.setType(ticket.getType());
            if (ticket.getVenue() == null) {
                tableTicket.setVenueName(null);
                tableTicket.setCapacity(null);
                tableTicket.setVenueType(null);
                tableTicket.setStreet(null);
            } else {
                tableTicket.setVenueName(ticket.getVenue().getName());
                tableTicket.setCapacity(String.valueOf(ticket.getVenue().getCapacity()));
                tableTicket.setVenueType(String.valueOf(ticket.getVenue().getType()));
                tableTicket.setStreet(ticket.getVenue().getAddress().getStreet());
            }
            tableTickets.add(tableTicket);
        }
        ticketTable.setItems(tableTickets);
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
            ticketTable.setVisible(false);
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