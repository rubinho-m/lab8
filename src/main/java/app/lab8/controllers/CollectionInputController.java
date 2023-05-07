package app.lab8.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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

    private void closeStage(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void sendAdd(ActionEvent event) {
        System.out.println("Отправил добавление");
        closeStage(event);

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

    }

    @FXML
    void manageRefundable(ActionEvent event) {
        isRefundable = refundableButton.isSelected();

    }

    @FXML
    void manageVenue(ActionEvent event) {
        if (venueButton.isSelected()) {
            venueNameInput.setVisible(true);
            capacityInput.setVisible(true);
            venueTypeMenue.setVisible(true);
            addressButton.setVisible(true);
        } else {
            venueNameInput.setVisible(false);
            capacityInput.setVisible(false);
            venueTypeMenue.setVisible(false);
            addressButton.setVisible(false);
            streetInput.setVisible(false);
            addressButton.setSelected(false);
        }

    }

}
