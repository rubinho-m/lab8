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
import java.sql.Date;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
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

    @FXML
    private MenuButton languageButton;

    @FXML
    private Label header;



    @FXML
    private MenuItem russian;

    @FXML
    private MenuItem belarusian;

    @FXML
    private MenuItem greek;

    @FXML
    private MenuItem spanish;

    @FXML
    private Button infoButton;

    @FXML
    private Button showButton;

    @FXML
    private Button addButton;

    @FXML
    private Button clearButton;

    @FXML
    private Button helpButton;
    @FXML
    private Button updateButton;

    @FXML
    private Button addIfMinButton;

    @FXML
    private Button historyButton;

    @FXML
    private Button minByPriceButton;

    @FXML
    private Button printByVenuesButton;

    @FXML
    private Button removeGreaterButton;

    @FXML
    private MenuItem idFilter;

    @FXML
    private MenuItem nameFilter;

    @FXML
    private MenuItem xFilter;

    @FXML
    private MenuItem yFilter;

    @FXML
    private MenuItem priceFilter;

    @FXML
    private MenuItem dateFilter;

    @FXML
    private MenuItem commentFilter;

    @FXML
    private MenuItem refundableFilter;

    @FXML
    private MenuItem typeFilter;

    @FXML
    private MenuItem creatorFilter;

    @FXML
    private MenuItem venueFilter;

    @FXML
    private MenuItem capacityFilter;

    @FXML
    private MenuItem streetFilter;

    @FXML
    private MenuItem venueTypeFilter;


    private ObservableList<TableTicket> tableTickets = FXCollections.observableArrayList();
    private ResourceBundle resourceBundle;

    private Map<String, LocalDate> dates = new HashMap<>();
    private Map<String, Double> prices = new HashMap<>();

    private Map<String, Long> capacities = new HashMap<>();


    @FXML
    void initialize() throws Exception {
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


        List<MenuItem> languageItems = languageButton.getItems();
        for (MenuItem item : languageItems) {
            item.setOnAction(e -> {
                Container.setLanguage(item.getId());
                if (item.getId().equals("russian")) {
                    resourceBundle = ResourceBundle.getBundle("resources", new Locale("ru", "RU"));
                    changeLanguage();
                }
                if (item.getId().equals("belarusian")) {
                    resourceBundle = ResourceBundle.getBundle("resources", new Locale("be", "BY"));
                    changeLanguage();
                }
                if (item.getId().equals("spanish")) {
                    resourceBundle = ResourceBundle.getBundle("resources", new Locale("es", "HN"));
                    changeLanguage();
                }
                if (item.getId().equals("greek")) {
                    resourceBundle = ResourceBundle.getBundle("resources", new Locale("el", "GR"));
                    changeLanguage();
                }
                try {
                    boolean tableVisible = ticketTable.isVisible();
                    sendShow(e);
                    if (!tableVisible) {
                        ticketTable.setVisible(false);
                        filterInput.setVisible(false);
                        filterMenu.setVisible(false);
                        removeButton.setVisible(false);
                        resetButton.setVisible(false);
                        textDisplay.setVisible(true);
                    }
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }

            });
        }

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
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println(e);
            }
        });
        commentColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        commentColumn.setOnEditCommit(event -> {
            TableTicket ticket = event.getTableView().getItems().get(event.getTablePosition().getRow());
            ticket.setComment(event.getNewValue());
            try {
                sendCollectionCommand("update", ticket.getId(), getTicket(ticket));
                sendShow(new ActionEvent());
            } catch (Exception e) {
                System.out.println(e);
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
            if (!capacities.containsKey(event.getNewValue())) {

                String tmp = event.getNewValue();

                String output = "";
                for (char c : tmp.toCharArray()) {
                    if (Character.isDigit(c)) {
                        output += c;
                    }
                }

                capacities.put(event.getNewValue(), Long.valueOf(output));
            }
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

            String tmp = event.getNewValue();

            String output = "";
            for (char c : tmp.toCharArray()) {
                if (Character.isDigit(c)) {
                    output += c;
                }
            }
            if (!prices.containsKey(event.getNewValue())) {
                prices.put(event.getNewValue(), Double.valueOf(output));
            }
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
//
//        dateColumn.setCellFactory(TextFieldTableCell.forTableColumn());
//        dateColumn.setOnEditCommit(event -> {
//            TableTicket ticket = event.getTableView().getItems().get(event.getTablePosition().getRow());
//            ticket.setCreationDate(event.getNewValue());
//            try {
//                sendCollectionCommand("update", ticket.getId(), getTicket(ticket));
//                sendShow(new ActionEvent());
//            } catch (Exception e) {
//                try {
//                    sendShow(new ActionEvent());
//                } catch (Exception ignored) {
//
//                }
//            }
//        });


        List<MenuItem> filterItems = filterMenu.getItems();
        for (MenuItem item : filterItems) {
            item.setOnAction(e -> {
                filterType = item.getId();
                filterInput.setText("");
            });
        }


        FilteredList<TableTicket> filteredData = new FilteredList<>(tableTickets, p -> true);
        filterInput.textProperty().addListener((observable, oldValue, newValue) -> {

            filteredData.setPredicate(ticket -> {

                if (newValue.isBlank() || filterType.isBlank()) {
                    return true;
                }
                System.out.println(newValue + " " + ticket.getName());
                System.out.println();
                return Stream.of(
                        filterType.equals("idFilter") && String.valueOf(ticket.getId()).equals(newValue),
                        filterType.equals("nameFilter") && ticket.getName().contains(newValue),
                        filterType.equals("xFilter") && ticket.getX().equals(newValue),
                        filterType.equals("yFilter") && ticket.getY().equals(newValue),
                        filterType.equals("priceFilter") && ticket.getPrice().equals(newValue),
                        filterType.equals("dateFilter") && ticket.getCreationDate().contains(newValue),
                        filterType.equals("commentFilter") && ticket.getComment().contains(newValue),
                        filterType.equals("refundableFilter") && ticket.getRefundable().equals(newValue),
                        filterType.equals("typeFilter") && ticket.getType().equals(newValue),
                        filterType.equals("creatorFilter") && ticket.getUser().equals(newValue),
                        filterType.equals("venueFilter") && ticket.getVenueName().equals(newValue),
                        filterType.equals("capacityFilter") && ticket.getCapacity().equals(newValue),
                        filterType.equals("streetFilter") && ticket.getStreet().equals(newValue),
                        filterType.equals("venueTypeFilter") && ticket.getVenueType().equals(newValue)
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

    private void changeLanguage() {
        userLabel.setText(resourceBundle.getString("user") + ": " + Container.getUser());
        languageButton.setText(resourceBundle.getString("language"));
        russian.setText(resourceBundle.getString("russian"));
        belarusian.setText(resourceBundle.getString("belarusian"));
        greek.setText(resourceBundle.getString("greek"));
        spanish.setText(resourceBundle.getString("spanish"));
        header.setText(resourceBundle.getString("header"));
        infoButton.setText(resourceBundle.getString("info"));
        showButton.setText(resourceBundle.getString("show"));
        addButton.setText(resourceBundle.getString("add"));
        clearButton.setText(resourceBundle.getString("clear"));
        executeScriptButton.setText(resourceBundle.getString("execute_script"));
        helpButton.setText(resourceBundle.getString("help"));
        removeByIdButton.setText(resourceBundle.getString("remove_by_id"));
        updateButton.setText(resourceBundle.getString("update"));
        addIfMinButton.setText(resourceBundle.getString("add_if_min"));
        historyButton.setText(resourceBundle.getString("history"));
        minByPriceButton.setText(resourceBundle.getString("min_by_price"));
        printByVenuesButton.setText(resourceBundle.getString("print_by_venues"));
        filterButton.setText(resourceBundle.getString("filter_greater_than_price"));
        removeGreaterButton.setText(resourceBundle.getString("remove_greater"));
        idColumn.setText(resourceBundle.getString("id"));
        nameColumn.setText(resourceBundle.getString("name"));
        xColumn.setText(resourceBundle.getString("x"));
        yColumn.setText(resourceBundle.getString("y"));
        priceColumn.setText(resourceBundle.getString("price"));
        dateColumn.setText(resourceBundle.getString("date"));
        commentColumn.setText(resourceBundle.getString("comment"));
        refundableColumn.setText(resourceBundle.getString("refundable"));
        typeColumn.setText(resourceBundle.getString("type"));
        creatorColumn.setText(resourceBundle.getString("user"));
        venueColumn.setText(resourceBundle.getString("venueName"));
        capacityColumn.setText(resourceBundle.getString("capacity"));
        streetColumn.setText(resourceBundle.getString("street"));
        venueTypeColumn.setText(resourceBundle.getString("type"));
        removeButton.setText(resourceBundle.getString("remove"));
        filterMenu.setText(resourceBundle.getString("filter"));
        resetButton.setText(resourceBundle.getString("resetButton"));


        idFilter.setText(resourceBundle.getString("id"));
        nameFilter.setText(resourceBundle.getString("name"));
        xFilter.setText(resourceBundle.getString("x"));
        yFilter.setText(resourceBundle.getString("y"));
        priceFilter.setText(resourceBundle.getString("price"));
        dateFilter.setText(resourceBundle.getString("date"));
        commentFilter.setText(resourceBundle.getString("comment"));
        refundableFilter.setText(resourceBundle.getString("refundable"));
        typeFilter.setText(resourceBundle.getString("type"));
        creatorFilter.setText(resourceBundle.getString("user"));
        venueFilter.setText(resourceBundle.getString("venueName"));
        capacityFilter.setText(resourceBundle.getString("capacity"));
        streetFilter.setText(resourceBundle.getString("street"));
        venueTypeFilter.setText(resourceBundle.getString("type"));


    }

    private Ticket getTicket(TableTicket ticket) {
        Ticket returnTicket = new Ticket();
        returnTicket.setId(Long.valueOf(ticket.getId()));
        returnTicket.setName(ticket.getName());
        Coordinates coordinates = new Coordinates();
        coordinates.setX(Float.parseFloat(ticket.getX()));
        coordinates.setY(Integer.parseInt(ticket.getY()));
        returnTicket.setCoordinates(coordinates);

        returnTicket.setPrice(prices.get(ticket.getPrice()));


        returnTicket.setComment(ticket.getComment());
        returnTicket.setCreationDate(dates.get(ticket.getCreationDate()));
        returnTicket.setRefundable(Boolean.parseBoolean(ticket.getRefundable()));
        returnTicket.setUser(ticket.getUser());
        if (ticket.getType() != null && !ticket.getType().equals("null")) {
            returnTicket.setType(TicketType.valueOf(ticket.getType()));
        } else {
            returnTicket.setType(null);
        }
        Venue venue = new Venue();
        if (ticket.getVenueName() != null && !ticket.getVenueName().equals("null")) {
            venue.setId(ticket.getVenueId());
            venue.setName(ticket.getVenueName());
            if (ticket.getVenueType() != null && !ticket.getVenueType().equals("null")) {
                venue.setType(VenueType.valueOf(ticket.getVenueType()));
            } else {
                venue.setType(null);
            }
            if (ticket.getCapacity() != null && !ticket.getCapacity().equals("null")) {
                venue.setCapacity(capacities.get(ticket.getCapacity()));
//                venue.setCapacity(Long.parseLong(ticket.getCapacity()));
            }
            Address address = new Address();
            if (ticket.getStreet() != null && !ticket.getStreet().equals("null")) {
                address.setStreet(ticket.getStreet());
            }
            venue.setAddress(address);
            returnTicket.setVenue(venue);
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
    protected void showVisualisation() throws Exception {
        sendShow(new ActionEvent());
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("visualisation.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setX(100);
        stage.setY(0);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();


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
//        resourceBundle = ResourceBundle.getBundle("resources", new Locale("en", "US"));

        Locale locale = resourceBundle.getLocale();

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

        for (Ticket ticket : tickets) {
            TableTicket tableTicket = new TableTicket();

//            tableTicket.setId(String.format(locale, "%,d", ticket.getId()));
            tableTicket.setId(String.valueOf(ticket.getId()));
            tableTicket.setName(ticket.getName());
            tableTicket.setX(String.valueOf(ticket.getCoordinates().getX()));
//
            tableTicket.setY(String.valueOf(ticket.getCoordinates().getY()));

            double price = ticket.getPrice();
            NumberFormat nf = NumberFormat.getInstance(locale);
            String localPrice = nf.format(price);
            tableTicket.setPrice(localPrice);

            if (!prices.containsKey(localPrice)) {
                prices.put(localPrice, price);
            }


//            java.util.Date date = java.util.Date.from(ticket.getCreationDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
            int year = ticket.getCreationDate().getYear();
            int month = ticket.getCreationDate().getMonthValue();
            int day = ticket.getCreationDate().getDayOfMonth();

            Calendar calendar = Calendar.getInstance();
            calendar.set(year, (month - 1), day);

            java.util.Date date = calendar.getTime();


            tableTicket.setCreationDate(DateFormat.getDateInstance(DateFormat.MEDIUM, locale).format(date));
            if (!dates.containsKey(tableTicket.getCreationDate())) {
                dates.put(tableTicket.getCreationDate(), ticket.getCreationDate());
            }

//            tableTicket.setCreationDate(String.valueOf(ticket.getCreationDate()));
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
                tableTicket.setVenueId((int) ticket.getId());
                tableTicket.setVenueName(ticket.getVenue().getName());
                long capacity = ticket.getVenue().getCapacity();
                String localCapacity = nf.format(capacity);
                tableTicket.setCapacity(localCapacity);

                if (!capacities.containsKey(localCapacity)) {
                    capacities.put(localCapacity, capacity);
                }
//                tableTicket.setCapacity(String.valueOf(ticket.getVenue().getCapacity()));
                tableTicket.setVenueType(String.valueOf(ticket.getVenue().getType()));
                if (ticket.getVenue().getAddress() != null) {
                    tableTicket.setStreet(ticket.getVenue().getAddress().getStreet());
                } else {
                    tableTicket.setStreet(null);
                }
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
        Container.setTicketToUpdate(null);
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

    @FXML
    private void sortCollection(String id) {


    }


}