package app.lab8.controllers;

import app.lab8.App;
import app.lab8.Validator;
import app.lab8.common.networkStructures.Request;
import app.lab8.common.structureClasses.*;
import app.lab8.network.Container;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    private String ticketType = null;
    private String venueType = null;

    private boolean venueFlag = false;
    private boolean addressFlag = false;


    @FXML
    void initialize() {
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

        if (nameInput.getText().isBlank()){
            flag = true;
            nameInput.setStyle("-fx-border-color: red");
        }
        if (commentInput.getText().isBlank()){
            flag = true;
            commentInput.setStyle("-fx-border-color: red");
        }

        if (venueFlag){
            System.out.println("ТУТ");

            if (venueNameInput.getText().isBlank()){
                venueNameInput.setStyle("-fx-border-color: red");
                flag = true;
            }
            if (!validator.isCapacityOk(capacityInput.getText())){
                capacityInput.setStyle("-fx-border-color: red");
                flag = true;
            }
            Venue venue = new Venue();

            if (!flag){

                venue.setName(venueNameInput.getText());
                venue.setCapacity(Long.parseLong(capacityInput.getText()));
                if (venueType != null) {
                    venue.setType(VenueType.valueOf(venueType));
                }
            }

            if (addressFlag){
                Address address = new Address();

                if (streetInput.getText().isBlank()){
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
    void sendAddIfMin(ActionEvent event) {
        System.out.println("Отправил добавление min");
        closeStage(event);

    }

    @FXML
    void sendRemoveGreater(ActionEvent event) {
        System.out.println("Отправил удаление greater");
        closeStage(event);

    }

    @FXML
    void sendUpdate(ActionEvent event) {
        System.out.println("Отправил обновление");
        closeStage(event);

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
