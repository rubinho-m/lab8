package app.lab8.controllers;

import app.lab8.App;
import app.lab8.common.networkStructures.Request;
import app.lab8.common.structureClasses.*;
import app.lab8.network.Container;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class MainController {

    @FXML
    private Button ticketButton;

    @FXML
    private Button removeButton;
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
    private TableColumn<TableTicket, String> dateColumn;

    @FXML
    private TableColumn<TableTicket, String> idColumn;

    @FXML
    private TableColumn<TableTicket, String> nameColumn;

    @FXML
    private TableColumn<TableTicket, String> priceColumn;

    @FXML
    private TableColumn<TableTicket, String> refundableColumn;

    @FXML
    private TableColumn<TableTicket, String> streetColumn;

    @FXML
    private TableColumn<TableTicket, String> venueColumn;


    @FXML
    private TableColumn<TableTicket, String> typeColumn;

    @FXML
    private TableColumn<TableTicket, String> venueTypeColumn;

    @FXML
    private TableColumn<TableTicket, String> xColumn;

    @FXML
    private TableColumn<TableTicket, String> yColumn;

    @FXML
    private MenuButton filterMenu;

    @FXML
    private TextField filterInput;

    @FXML
    private Button resetButton;
    private String filterType = "";

    private ObservableList<TableTicket> tableTickets = FXCollections.observableArrayList();


    @FXML
    void initialize() {
        userLabel.setText("User: " + Container.getUser());

        idColumn.setCellValueFactory(new PropertyValueFactory<TableTicket, String>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<TableTicket, String>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<TableTicket, String>("price"));
        commentColumn.setCellValueFactory(new PropertyValueFactory<TableTicket, String>("comment"));
        xColumn.setCellValueFactory(new PropertyValueFactory<TableTicket, String>("x"));
        yColumn.setCellValueFactory(new PropertyValueFactory<TableTicket, String>("y"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<TableTicket, String>("creationDate"));
        refundableColumn.setCellValueFactory(new PropertyValueFactory<TableTicket, String>("refundable"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<TableTicket, String>("type"));
        creatorColumn.setCellValueFactory(new PropertyValueFactory<TableTicket, String>("user"));
        creatorColumn.setCellValueFactory(new PropertyValueFactory<TableTicket, String>("user"));
        venueColumn.setCellValueFactory(new PropertyValueFactory<TableTicket, String>("venueName"));
        venueColumn.setCellValueFactory(new PropertyValueFactory<TableTicket, String>("venueName"));
        capacityColumn.setCellValueFactory(new PropertyValueFactory<TableTicket, String>("capacity"));
        venueTypeColumn.setCellValueFactory(new PropertyValueFactory<TableTicket, String>("venueType"));
        streetColumn.setCellValueFactory(new PropertyValueFactory<TableTicket, String>("street"));

        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumn.setOnEditCommit(event -> {
            TableTicket ticket = event.getTableView().getItems().get(event.getTablePosition().getRow());
            ticket.setName(event.getNewValue());
            try {
                sendCollectionCommand("update", ticket.getId(), getTicket(ticket));
                sendShow(new ActionEvent());
            } catch (Exception ignored) {
            }
        });
        commentColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        commentColumn.setOnEditCommit(event -> {
            TableTicket ticket = event.getTableView().getItems().get(event.getTablePosition().getRow());
            ticket.setComment(event.getNewValue());
            try {
                sendCollectionCommand("update", ticket.getId(), getTicket(ticket));
                sendShow(new ActionEvent());
            } catch (Exception ignored) {
            }
        });
        refundableColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        refundableColumn.setOnEditCommit(event -> {
            TableTicket ticket = event.getTableView().getItems().get(event.getTablePosition().getRow());
            ticket.setRefundable(event.getNewValue());
            try {
                sendCollectionCommand("update", ticket.getId(), getTicket(ticket));
                sendShow(new ActionEvent());
            } catch (Exception ignored) {
            }
        });
        typeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        typeColumn.setOnEditCommit(event -> {
            TableTicket ticket = event.getTableView().getItems().get(event.getTablePosition().getRow());
            ticket.setType(event.getNewValue());
            try {
                sendCollectionCommand("update", ticket.getId(), getTicket(ticket));
                sendShow(new ActionEvent());
            } catch (Exception e) {

                try {
                    sendShow(new ActionEvent());
                } catch (Exception ignored) {

                }
            }
        });

        venueColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        venueColumn.setOnEditCommit(event -> {
            TableTicket ticket = event.getTableView().getItems().get(event.getTablePosition().getRow());
            ticket.setVenueName(event.getNewValue());
            try {
                sendCollectionCommand("update", ticket.getId(), getTicket(ticket));
                sendShow(new ActionEvent());
            } catch (Exception e) {
                try {
                    sendShow(new ActionEvent());
                } catch (Exception ignored) {

                }
            }
        });
        capacityColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        capacityColumn.setOnEditCommit(event -> {
            TableTicket ticket = event.getTableView().getItems().get(event.getTablePosition().getRow());
            ticket.setCapacity(event.getNewValue());
            try {
                sendCollectionCommand("update", ticket.getId(), getTicket(ticket));
                sendShow(new ActionEvent());
            } catch (Exception e) {
                try {
                    sendShow(new ActionEvent());
                } catch (Exception ignored) {

                }
            }
        });
        streetColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        streetColumn.setOnEditCommit(event -> {
            TableTicket ticket = event.getTableView().getItems().get(event.getTablePosition().getRow());
            ticket.setStreet(event.getNewValue());
            try {
                sendCollectionCommand("update", ticket.getId(), getTicket(ticket));
                sendShow(new ActionEvent());
            } catch (Exception e) {
                try {
                    sendShow(new ActionEvent());
                } catch (Exception ignored) {

                }
            }
        });
        venueTypeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        venueTypeColumn.setOnEditCommit(event -> {
            TableTicket ticket = event.getTableView().getItems().get(event.getTablePosition().getRow());
            ticket.setVenueType(event.getNewValue());
            try {
                sendCollectionCommand("update", ticket.getId(), getTicket(ticket));
                sendShow(new ActionEvent());
            } catch (Exception e) {
                try {
                    sendShow(new ActionEvent());
                } catch (Exception ignored) {

                }
            }
        });


        xColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        xColumn.setOnEditCommit(event -> {
            TableTicket ticket = event.getTableView().getItems().get(event.getTablePosition().getRow());
            ticket.setX(event.getNewValue());
            try {
                sendCollectionCommand("update", ticket.getId(), getTicket(ticket));
                sendShow(new ActionEvent());
            } catch (Exception e) {
                try {
                    sendShow(new ActionEvent());
                } catch (Exception ignored) {

                }
            }
        });

        yColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        yColumn.setOnEditCommit(event -> {
            TableTicket ticket = event.getTableView().getItems().get(event.getTablePosition().getRow());
            ticket.setY(event.getNewValue());
            try {
                sendCollectionCommand("update", ticket.getId(), getTicket(ticket));
                sendShow(new ActionEvent());
            } catch (Exception e) {
                try {
                    sendShow(new ActionEvent());
                } catch (Exception ignored) {

                }
            }
        });
        priceColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        priceColumn.setOnEditCommit(event -> {
            TableTicket ticket = event.getTableView().getItems().get(event.getTablePosition().getRow());
            ticket.setPrice(event.getNewValue());
            try {
                sendCollectionCommand("update", ticket.getId(), getTicket(ticket));
                sendShow(new ActionEvent());
            } catch (Exception e) {
                try {
                    sendShow(new ActionEvent());
                } catch (Exception ignored) {

                }
            }
        });

        dateColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        dateColumn.setOnEditCommit(event -> {
            TableTicket ticket = event.getTableView().getItems().get(event.getTablePosition().getRow());
            ticket.setCreationDate(event.getNewValue());
            try {
                sendCollectionCommand("update", ticket.getId(), getTicket(ticket));
                sendShow(new ActionEvent());
            } catch (Exception e) {
                try {
                    sendShow(new ActionEvent());
                } catch (Exception ignored) {

                }
            }
        });


        List<MenuItem> filterItems = filterMenu.getItems();
        for (MenuItem item : filterItems) {
            item.setOnAction(e -> {
                filterType = item.getText();
                filterInput.setText("");
            });
        }

        FilteredList<TableTicket> filteredData = new FilteredList<>(tableTickets, p -> true);
        filterInput.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(ticket -> {
                if (newValue.isBlank() || filterType.isBlank()) {
                    return true;
                }
                return Stream.of(
                        filterType.equals("id") && String.valueOf(ticket.getId()).equals(newValue),
                        filterType.equals("name") && ticket.getName().contains(newValue),
                        filterType.equals("x") && ticket.getX().equals(newValue),
                        filterType.equals("y") && ticket.getY().equals(newValue),
                        filterType.equals("price") && ticket.getPrice().equals(newValue),
                        filterType.equals("date") && ticket.getCreationDate().contains(newValue),
                        filterType.equals("comment") && ticket.getComment().contains(newValue),
                        filterType.equals("refundable") && ticket.getRefundable().equals(newValue),
                        filterType.equals("type") && ticket.getType().equals(newValue),
                        filterType.equals("creator") && ticket.getUser().equals(newValue),
                        filterType.equals("venue") && ticket.getVenueName().equals(newValue),
                        filterType.equals("capacity") && ticket.getCapacity().equals(newValue),
                        filterType.equals("street") && ticket.getStreet().equals(newValue),
                        filterType.equals("venue type") && ticket.getVenueType().equals(newValue)
                ).anyMatch(Boolean::booleanValue);
            });
        });
        SortedList<TableTicket> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(ticketTable.comparatorProperty());
        ticketTable.setItems(sortedData);


        Duration duration = Duration.seconds(1);
        Timeline timeline = new Timeline(new KeyFrame(duration, event -> {
            if (textDisplay.isVisible()) {
                textDisplay.setText(Container.getActualResponse());
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();


    }

    private Ticket getTicket(TableTicket ticket) {
        Ticket returnTicket = new Ticket();
        returnTicket.setId(Long.valueOf(ticket.getId()));
        returnTicket.setName(ticket.getName());
        Coordinates coordinates = new Coordinates();
        coordinates.setX(Float.parseFloat(ticket.getX()));
        coordinates.setY(Integer.parseInt(ticket.getY()));
        returnTicket.setCoordinates(coordinates);
        returnTicket.setPrice(Double.valueOf(ticket.getPrice()));
        returnTicket.setComment(ticket.getComment());
        returnTicket.setCreationDate(LocalDate.parse(ticket.getCreationDate()));
        returnTicket.setRefundable(Boolean.parseBoolean(ticket.getRefundable()));
        returnTicket.setUser(ticket.getUser());
        returnTicket.setType(TicketType.valueOf(ticket.getType()));
        Venue venue = new Venue();
        try {
            venue.setId(ticket.getVenueId());
            venue.setName(ticket.getVenueName());
            venue.setType(VenueType.valueOf(ticket.getVenueType()));
            venue.setCapacity(Long.parseLong(ticket.getCapacity()));
            Address address = new Address();
            address.setStreet(ticket.getStreet());
            venue.setAddress(address);
            returnTicket.setVenue(venue);
        } catch (NullPointerException ignored) {

        }

        return returnTicket;
    }

    private void sendCollectionCommand(String command, String argument, Ticket ticket) throws Exception {
        ArrayList<String> commandWithArguments = new ArrayList<>();
        commandWithArguments.add(command);
        commandWithArguments.add(argument);
        Request request = makeRequest(commandWithArguments, ticket);
        App.networkConnection.connectionManage(request);

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
        filterInput.setVisible(false);
        filterMenu.setVisible(false);
        removeButton.setVisible(false);
        resetButton.setVisible(false);
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
        removeButton.setVisible(true);
        resetButton.setVisible(true);
        textDisplay.setVisible(false);
        filterInput.setVisible(true);
        filterMenu.setVisible(true);
        ticketTable.refresh();
        Set<Ticket> tickets = Container.getTickets();
        tableTickets.clear();

        System.out.println(tickets.size());
        for (Ticket ticket : tickets) {
            TableTicket tableTicket = new TableTicket();
            tableTicket.setId(String.valueOf(ticket.getId()));
            tableTicket.setName(ticket.getName());
            tableTicket.setX(String.valueOf(ticket.getCoordinates().getX()));
            tableTicket.setY(String.valueOf(ticket.getCoordinates().getY()));
            tableTicket.setPrice(String.valueOf(ticket.getPrice()));
            tableTicket.setCreationDate(String.valueOf(ticket.getCreationDate()));
            tableTicket.setUser(ticket.getUser());
            tableTicket.setComment(ticket.getComment());
            tableTicket.setRefundable(String.valueOf(ticket.isRefundable()));
            tableTicket.setType(String.valueOf(ticket.getType()));
            if (ticket.getVenue() == null) {
                tableTicket.setVenueId(null);
                tableTicket.setVenueName(null);
                tableTicket.setCapacity(null);
                tableTicket.setVenueType(null);
                tableTicket.setStreet(null);
            } else {
                tableTicket.setVenueId(ticket.getVenue().getId());
                tableTicket.setVenueName(ticket.getVenue().getName());
                tableTicket.setCapacity(String.valueOf(ticket.getVenue().getCapacity()));
                tableTicket.setVenueType(String.valueOf(ticket.getVenue().getType()));
                tableTicket.setStreet(ticket.getVenue().getAddress().getStreet());
            }
            tableTickets.add(tableTicket);
        }
//        ticketTable.setItems(tableTickets);
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
    void removeSelected(ActionEvent event) throws Exception {
        SelectionModel<TableTicket> selectionModel = ticketTable.getSelectionModel();
        try {
            TableTicket selectedTicket = selectionModel.getSelectedItem();
            String id = selectedTicket.getId();
            sendCommandWithArgument("remove_by_id", String.valueOf(id));
            sendShow(event);
        } catch (NullPointerException ignored) {

        }


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
            filterInput.setVisible(false);
            filterMenu.setVisible(false);
            removeButton.setVisible(false);
            resetButton.setVisible(false);
            textDisplay.setText(Container.getActualResponse());
        });

    }

    @FXML
    void resetFilter(ActionEvent event) {
        filterType = "";
        filterInput.setText("");
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

    private void sendCommandWithArgument(String command, String argument) throws Exception {
        ArrayList<String> commandWithArguments = new ArrayList<>();
        commandWithArguments.add(command);
        commandWithArguments.add(argument);
        Request request = makeRequest(commandWithArguments, null);
        App.networkConnection.connectionManage(request);

    }


}