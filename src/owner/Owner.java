package owner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Owner {
    @FXML
    public Button doctor;

    @FXML
    public Button admin;

    @FXML
    public Button schedule;

    @FXML
    public Button payment;

    @FXML
    public Button complaints;

    @FXML
    public Button services;

    @FXML
    public void doctor(ActionEvent actionEvent) {
    }

    @FXML
    public void admin(ActionEvent actionEvent) {

    }

    @FXML
    public void schedule(ActionEvent actionEvent) {
        Stage Stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/administrator/schedule/schedule.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage.setTitle("График");
        Stage.setScene(new Scene(root, 600, 400));
        Stage.setResizable(false);
        Stage.centerOnScreen();
        Stage.show();
    }

    @FXML
    public void payment(ActionEvent actionEvent) {
        Stage Stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("payment/payment.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage.setTitle("График");
        Stage.setScene(new Scene(root, 671, 301));
        Stage.setResizable(false);
        Stage.centerOnScreen();
        Stage.show();
    }

    @FXML
    public void complaints(ActionEvent actionEvent) {
        Stage Stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/administrator/complaints/complaints.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage.setTitle("Жалобы");
        Stage.setScene(new Scene(root, 600, 513));
        Stage.setResizable(false);
        Stage.centerOnScreen();
        Stage.show();
    }

    @FXML
    public void services(ActionEvent actionEvent) {

    }
}