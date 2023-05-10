package app.lab8.controllers;

import app.lab8.App;
import app.lab8.Validator;
import app.lab8.common.networkStructures.Request;
import app.lab8.common.structureClasses.*;
import app.lab8.network.Container;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class CollectionInputController {

    @FXML
    private RadioButton addressButton;

    @FXML
    private TextField capacityInput;

    @FXML
    private TextField commentInput;

    @FXML
    private Label header;

    @FXML
    private TextField nameInput;

    @FXML
    private TextField priceInput;

    @FXML
    private RadioButton refundableButton;

    @FXML
    private Button sendButton;

    @FXML
    private TextField streetInput;

    @FXML
    private MenuButton ticketTypeMenue;

    @FXML
    private RadioButton venueButton;

    @FXML
    private TextField venueNameInput;

    @FXML
    private MenuButton venueTypeMenue;
    private boolean isRefundable;

    @FXML
    private TextField xInput;

    @FXML
    private TextField yInput;

    @FXML
    private TextField idInput;

    private String ticketType = null;
    private String venueType = null;

    private boolean venueFlag = false;
    private boolean addressFlag = false;

    private ResourceBundle resourceBundle;


    private void changeLanguage() {
        header.setText(resourceBundle.getString("ticketInput"));
        sendButton.setText(resourceBundle.getString("send"));
        nameInput.setPromptText(resourceBundle.getString("name"));
        xInput.setPromptText(resourceBundle.getString("x"));
        yInput.setPromptText(resourceBundle.getString("y"));
        priceInput.setPromptText(resourceBundle.getString("price"));
        commentInput.setPromptText(resourceBundle.getString("comment"));
        refundableButton.setText(resourceBundle.getString("refundable"));
        ticketTypeMenue.setText(resourceBundle.getString("type"));
        venueButton.setText(resourceBundle.getString("addVenue"));
        venueNameInput.setPromptText(resourceBundle.getString("venueName"));
        capacityInput.setPromptText(resourceBundle.getString("capacity"));
        venueTypeMenue.setText(resourceBundle.getString("type"));
        addressButton.setText(resourceBundle.getString("addAddress"));
        streetInput.setPromptText(resourceBundle.getString("street"));

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

    @FXML
    void initialize() {
        check();

        Duration duration = Duration.seconds(5);
        Timeline timeline = new Timeline(new KeyFrame(duration, event -> {
            check();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        if (Container.getTicketToUpdate() != null) {
            Ticket ticket = Container.getTicketToUpdate();
            idInput.setText(String.valueOf(ticket.getId()));
            nameInput.setText(ticket.getName());
            xInput.setText(String.valueOf(ticket.getCoordinates().getX()));
            yInput.setText(String.valueOf(ticket.getCoordinates().getY()));
            priceInput.setText(String.valueOf(ticket.getPrice()));
            commentInput.setText(ticket.getComment());
            refundableButton.setSelected(ticket.isRefundable());
            if (ticket.getType() != null) {
                ticketType = String.valueOf(ticket.getType());
            }
            Venue venue = ticket.getVenue();
            if (venue != null) {
                venueButton.setSelected(true);
                venueNameInput.setVisible(true);
                venueNameInput.setText(venue.getName());
                capacityInput.setVisible(true);
                capacityInput.setText(String.valueOf(venue.getCapacity()));
                if (venue.getType() != null) {
                    venueType = String.valueOf(venue.getType());
                }
                Address address = venue.getAddress();
                if (address != null) {
                    streetInput.setVisible(true);
                    addressButton.setSelected(true);
                    streetInput.setText(address.getStreet());
                }

            }

        }


        List<MenuItem> ticketItems = ticketTypeMenue.getItems();
        for (MenuItem item : ticketItems) {
            item.setOnAction(e -> {
                ticketType = item.getText();
            });
        }

        List<MenuItem> venueItems = venueTypeMenue.getItems();
        for (MenuItem item : venueItems) {
            item.setOnAction(e -> {
                venueType = item.getText();
            });
        }

    }

    private void closeStage(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private void getBack() {
        nameInput.setStyle("-fx-border-color: #33A64B");
        commentInput.setStyle("-fx-border-color: #33A64B");
        priceInput.setStyle("-fx-border-color: #33A64B");
        xInput.setStyle("-fx-border-color: #33A64B");
        yInput.setStyle("-fx-border-color: #33A64B");
        venueNameInput.setStyle("-fx-border-color: #33A64B");
        capacityInput.setStyle("-fx-border-color: #33A64B");
        streetInput.setStyle("-fx-border-color: #33A64B");
    }

    private Ticket parseTicket() {
        getBack();
        Ticket ticket = new Ticket();
        Validator validator = new Validator();
        boolean flag = false;
        if (!validator.isPriceOk(priceInput.getText())) {
            priceInput.setStyle("-fx-border-color: red");
            flag = true;
        }
        if (!validator.isCoordsOk(xInput.getText(), yInput.getText())) {
            if (!validator.isXOk(xInput.getText())) {
                xInput.setStyle("-fx-border-color: red");
            }
            if (!validator.isYOk(yInput.getText())) {
                yInput.setStyle("-fx-border-color: red");
            }
            flag = true;
        }

        if (nameInput.getText().isBlank()) {
            flag = true;
            nameInput.setStyle("-fx-border-color: red");
        }
        if (commentInput.getText().isBlank()) {
            flag = true;
            commentInput.setStyle("-fx-border-color: red");
        }

        if (venueFlag) {
            System.out.println("ТУТ");

            if (venueNameInput.getText().isBlank()) {
                venueNameInput.setStyle("-fx-border-color: red");
                flag = true;
            }
            if (!validator.isCapacityOk(capacityInput.getText())) {
                capacityInput.setStyle("-fx-border-color: red");
                flag = true;
            }
            Venue venue = new Venue();

            if (!flag) {

                venue.setName(venueNameInput.getText());
                venue.setCapacity(Long.parseLong(capacityInput.getText()));
                if (venueType != null) {
                    venue.setType(VenueType.valueOf(venueType));
                }
            }

            if (addressFlag) {
                Address address = new Address();

                if (streetInput.getText().isBlank()) {
                    streetInput.setStyle("-fx-border-color: red");
                    return null;
                }

                address.setStreet(streetInput.getText());
                venue.setAddress(address);
            }
            ticket.setVenue(venue);
        }

        if (flag) {
            return null;
        }
        getBack();

        ticket.setName(nameInput.getText());
        Coordinates coordinates = new Coordinates();
        coordinates.setX(Float.parseFloat(xInput.getText()));
        coordinates.setY(Integer.parseInt(yInput.getText()));
        ticket.setCoordinates(coordinates);
        ticket.setPrice(Double.valueOf(priceInput.getText()));
        ticket.setComment(commentInput.getText());
        ticket.setCreationDate(LocalDate.now());
        ticket.setRefundable(isRefundable);
        if (ticketType != null) {
            ticket.setType(TicketType.valueOf(ticketType));
        }
        ticket.setUser(Container.getUser());


        return ticket;
    }

    private Request makeRequest(ArrayList<String> commandWithArguments, Ticket ticket) {
        ArrayList<String> userData = new ArrayList<>();
        userData.add(Container.getUser());
        userData.add(Container.getPassword());
        return new Request(commandWithArguments, ticket, userData);
    }

    private void sendCollectionCommand(String command, String argument, Ticket ticket) throws Exception {
        ArrayList<String> commandWithArguments = new ArrayList<>();
        commandWithArguments.add(command);
        commandWithArguments.add(argument);
        Request request = makeRequest(commandWithArguments, ticket);
        App.networkConnection.connectionManage(request);

    }

    private void sendCollectionCommand(String command, Ticket ticket) throws Exception {
        ArrayList<String> commandWithArguments = new ArrayList<>();
        commandWithArguments.add(command);
        Request request = makeRequest(commandWithArguments, ticket);
        App.networkConnection.connectionManage(request);

    }

    @FXML
    void sendAdd(ActionEvent event) throws Exception {
        Ticket ticket = parseTicket();
        System.out.println(ticket);
        if (ticket != null) {
            sendCollectionCommand("add", ticket);
            closeStage(event);
        }

    }

    @FXML
    void sendAddIfMin(ActionEvent event) throws Exception {
        Ticket ticket = parseTicket();
        System.out.println(ticket);
        if (ticket != null) {
            sendCollectionCommand("add_if_min", ticket);
            closeStage(event);
        }

    }

    @FXML
    void sendRemoveGreater(ActionEvent event) throws Exception {
        Ticket ticket = parseTicket();
        System.out.println(ticket);
        if (ticket != null) {
            sendCollectionCommand("remove_greater", ticket);
            closeStage(event);
        }

    }

    @FXML
    void sendUpdate(ActionEvent event) throws Exception {
        idInput.setStyle("-fx-border-color: #33A64B");
        Ticket ticket = parseTicket();
        String argument = idInput.getText();
        try {
            int arg = Integer.parseInt(argument);
            if (ticket != null) {
                ticket.setId(Long.valueOf(arg));
                sendCollectionCommand("update", argument, ticket);
                closeStage(event);
            }
        } catch (Exception e) {
            idInput.setStyle("-fx-border-color: red");
        }


    }

    @FXML
    void manageAddress(ActionEvent event) {
        streetInput.setVisible(addressButton.isSelected());
        addressFlag = addressButton.isSelected();

    }

    @FXML
    void manageRefundable(ActionEvent event) {
        isRefundable = refundableButton.isSelected();

    }

    @FXML
    void manageVenue(ActionEvent event) {
        if (venueButton.isSelected()) {
            venueFlag = true;
            venueNameInput.setVisible(true);
            capacityInput.setVisible(true);
            venueTypeMenue.setVisible(true);
            addressButton.setVisible(true);
        } else {
            venueFlag = false;
            venueNameInput.setVisible(false);
            capacityInput.setVisible(false);
            venueTypeMenue.setVisible(false);
            addressButton.setVisible(false);
            streetInput.setVisible(false);
            addressButton.setSelected(false);
        }

    }

}
